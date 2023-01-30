//
//  RocketLaunchNavigator.swift
//  iosApp
//
//  Created by Uwais Alqadri on 1/30/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

protocol RocketLaunchNavigator {
  func navigateToRocketLaunch(from viewController: UIViewController)
  func navigateToRocketLaunch(window: UIWindow?)
}

struct DefaultRocketLaunchNavigator: RocketLaunchNavigator {
  private let assembler: Assembler

  init(assembler: Assembler) {
    self.assembler = assembler
  }

  func navigateToRocketLaunch(from viewController: UIViewController) {
    let view: RocketLaunchView = assembler.resolve()
    viewController.navigationController?.pushViewController(view.viewController, animated: true)
  }

  func navigateToRocketLaunch(window: UIWindow?) {
    let view: RocketLaunchView = assembler.resolve()
    window?.rootViewController = UINavigationController(rootViewController: view.viewController)
  }
}

