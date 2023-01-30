//
//  View.swift
//  iosApp
//
//  Created by Uwais Alqadri on 1/30/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import SwiftUI

extension View {
  func hideKeyboard() {
    UIApplication.shared.sendAction(#selector(UIResponder.resignFirstResponder), to: nil, from: nil, for: nil)
  }

  func placeholder<Content: View>(
    when shouldShow: Bool,
    alignment: Alignment = .leading,
    @ViewBuilder placeholder: () -> Content) -> some View {

      ZStack(alignment: alignment) {
        placeholder().opacity(shouldShow ? 1 : 0)
        self
      }
    }

  var window: UIWindow? {
    return UIApplication.shared.connectedScenes
      .filter({$0.activationState == .foregroundActive})
      .compactMap({$0 as? UIWindowScene})
      .first?.windows
      .filter({$0.isKeyWindow}).first
  }
}

extension View {

  func hideSeparatorRow() -> some View {
    self.frame(maxWidth: .infinity, maxHeight: .infinity, alignment: .leading)
      .listRowInsets(EdgeInsets(top: -1, leading: -1, bottom: -1, trailing: -1))
      .background(Color(.systemBackground))
  }

  @inlinable func reverseMask<Mask: View> (alignment: Alignment = .center, @ViewBuilder _ mask: () -> Mask) -> some View {
    self.mask(
      ZStack {
        Rectangle()

        mask()
          .blendMode(.destinationOut)
      }
    )
  }
}
