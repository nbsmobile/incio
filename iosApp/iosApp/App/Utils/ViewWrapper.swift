//
//  ViewWrapper.swift
//
//  Created by Asep Mulyana on 21/10/22.
//  Copyright © 2022 Nusantara Beta Studio All rights reserved.
//

import UIKit
import SwiftUI

class WrapperHolder {
    weak var viewController: UIViewController?
}

protocol WrappedView: View {
  var holder: WrapperHolder { get set }

  func configureNavigationBar(viewController: UIViewController)
}

extension WrappedView {
  var viewController: UIViewController {
    let viewController = ViewWrapper(rootView: self)
    self.holder.viewController = viewController
    return viewController
  }

  func configureNavigationBar(viewController: UIViewController) {

  }
}

class ViewWrapper<SomeView>: UIHostingController<SomeView> where SomeView: WrappedView {
  override func viewWillAppear(_ animated: Bool) {
    super.viewWillAppear(animated)
    self.rootView.configureNavigationBar(viewController: self)
  }
}

struct DataStateModifier<TheValue>: ViewModifier {
  var state: DataState<TheValue>
  var onSuccess: ((TheValue) -> Void)?
  var onFailed: ((Error) -> Void)?

  init(state: DataState<TheValue>,
       onSuccess: ((TheValue) -> Void)? = nil,
       onFailed: ((Error) -> Void)? = nil) {
    self.state = state
    self.onSuccess = onSuccess
    self.onFailed = onFailed
  }

  func body(content: Content) -> some View {
    switch state {
    case .loading:
      content
    case .success(let data):
      let _ = onSuccess?(data)
      content
    case .failed(let error):
      let _ = onFailed?(error)
      content
    default:
      content
    }
  }
}

struct PublishedDataStateModifier<TheValue>: ViewModifier {

  var data: Published<DataState<TheValue>>.Publisher
  var onSuccess: ((TheValue) -> Void)?
  var onLoading: ((Bool) -> Void)?
  var onEmpty: (() -> Void)?
  var onFailed: ((Error) -> Void)?

  func body(content: Content) -> some View {
    content
      .onAppear()
      .onReceive(data) { state in
        switch state {
        case .success(let value):
          onLoading?(false)
          onSuccess?(value)
        case .loading:
          onLoading?(true)
        case .empty:
          onLoading?(false)
          onEmpty?()
        case .failed(let error):
          onLoading?(false)
          onFailed?(error)
        default:
          break
        }
      }
  }
}

extension View {
  func loading() -> some View {
    modifier(LoadingModifier())
  }

  func dataState<TheValue>(state: DataState<TheValue>,
                           onSuccess: ((TheValue) -> Void)? = nil,
                           onFailed: ((Error) -> Void)? = nil) -> some View {
    modifier(DataStateModifier(state: state,
                               onSuccess: onSuccess,
                               onFailed: onFailed))
  }

  func dataState<TheValue>(
    _ data: Published<DataState<TheValue>>.Publisher,
    onSuccess: ((TheValue) -> Void)? = nil,
    onLoading: ((Bool) -> Void)? = nil,
    onFailed: ((Error) -> Void)? = nil
  ) -> some View {
    modifier(PublishedDataStateModifier(data: data, onSuccess: onSuccess, onLoading: onLoading, onEmpty: nil, onFailed: onFailed))
  }
}

struct MyModifier_Previews: PreviewProvider {
  static var previews: some View {
    let state: DataState<Bool> = .loading
    Text("Hello, world!")
      .modifier(DataStateModifier(state: state))
  }
}

