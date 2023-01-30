//
//  Error.swift
//
//  Created by Uwais Alqadri on 11/10/22.
//  Copyright © 2022 Nusantara Beta Studio. All rights reserved.
//

import Foundation
import Shared

extension Error {
  var apiError: ApiError? {
    (self as NSError).userInfo["KotlinException"] as? ApiError
  }

  var isApiError: Bool {
    (self as NSError).userInfo["KotlinException"] is ApiError
  }

  var isNoInternet: Bool {
    self.localizedDescription.contains("offline")
  }
}

