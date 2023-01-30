//
//  UIScreen.swift
//  iosApp
//
//  Created by Uwais Alqadri on 1/30/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

extension UIScreen {
  static var safeAreaInsets: UIEdgeInsets? {
    return UIApplication.shared.windows.first?.safeAreaInsets
  }

  static var screenWidth: CGFloat {
    get {
      if UIDevice.current.orientation.isLandscape {
        return max(UIScreen.main.bounds.size.height, UIScreen.main.bounds.size.width)
      } else {
        return min(UIScreen.main.bounds.size.height, UIScreen.main.bounds.size.width)
      }
    }
  }

  static var screenHeight: CGFloat {
    get {
      if UIDevice.current.orientation.isLandscape {
        return max(UIScreen.main.bounds.size.width, UIScreen.main.bounds.size.height)
      } else {
        return max(UIScreen.main.bounds.size.width, UIScreen.main.bounds.size.height)
      }
    }
  }
}

