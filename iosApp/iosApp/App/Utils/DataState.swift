//
//  DataState.swift
//  iosApp
//
//  Created by Uwais Alqadri on 1/30/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation

enum DataState<T>: Equatable {
  static func == (lhs: DataState<T>, rhs: DataState<T>) -> Bool {
    return true
  }

  case initiate
  case loading
  case empty
  case failed(error: Error)
  case success(data: T)

  var value: T? {
    if case .success(let data) = self {
      return data
    }
    return nil
  }
}

extension DataState {
  var isLoading: Bool {
    get {
      if case .loading = self {
        return true
      }
      return false
    }
    set {
      if newValue {
        self = .loading
      }
    }
  }
}
