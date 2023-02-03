// 
//  MembershipNavigator.swift
//  iosApp
//
//  Created by Abdhi P (Work) on 01/02/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import UIKit

protocol MembershipNavigator {
  func navigateToLogin(window: UIWindow?)
  func navigateToRegister(from viewController: UIViewController)
  func navigateToStory(window: UIWindow?)
}

struct DefaultMembershipNavigator: MembershipNavigator {
  private let assembler: Assembler

  init(assembler: Assembler) {
    self.assembler = assembler
  }

  func navigateToLogin(window: UIWindow?) {
    let view: LoginView = assembler.resolve()
    window?.rootViewController = UINavigationController(rootViewController: view.viewController)
  }

  func navigateToRegister(from viewController: UIViewController) {
    let view: RegisterView = assembler.resolve()
    viewController.navigationController?.pushViewController(view.viewController, animated: true)
  }

  func navigateToStory(window: UIWindow?) {
    let navigator: StoryNavigator = assembler.resolve()
    navigator.navigateToStory(window: window)
  }
}
