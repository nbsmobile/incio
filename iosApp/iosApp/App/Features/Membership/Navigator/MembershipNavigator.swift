// 
//  MembershipNavigator.swift
//  iosApp
//
//  Created by Abdhi P (Work) on 01/02/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import UIKit

protocol MembershipNavigator {
  func navigateToMain(window: UIWindow?)
  func navigateToLogin(window: UIWindow?)
  func navigateToRegister(from viewController: UIViewController)
  func navigateToStory(window: UIWindow?)

  func navigateToMain(from viewController: UIViewController)
  func navigateToLogin(from viewController: UIViewController)
  func navigateToPref(from viewController: UIViewController)
  func navigateToRocketLaunch(from viewController: UIViewController)
  func navigateToStory(from viewController: UIViewController)
}

struct DefaultMembershipNavigator: MembershipNavigator {
  private let assembler: Assembler

  init(assembler: Assembler) {
    self.assembler = assembler
  }

  func navigateToMain(window: UIWindow?) {
    let view: MainView = assembler.resolve()
    window?.rootViewController = UINavigationController(rootViewController: view.viewController)
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

  func navigateToMain(from viewController: UIViewController) {
    let view: MainView = assembler.resolve()
    viewController.navigationController?.pushViewController(view.viewController, animated: true)
  }

  func navigateToLogin(from viewController: UIViewController) {
    let view: LoginView = assembler.resolve()
    viewController.navigationController?.pushViewController(view.viewController, animated: true)
  }

  func navigateToPref(from viewController: UIViewController) {
    let view: PrefView = assembler.resolve()
    viewController.navigationController?.pushViewController(view.viewController, animated: true)
  }

  func navigateToRocketLaunch(from viewController: UIViewController) {
    let navigator: RocketLaunchNavigator = assembler.resolve()
    navigator.navigateToRocketLaunch(from: viewController)
  }

  func navigateToStory(from viewController: UIViewController) {
    let navigator: StoryNavigator = assembler.resolve()
    navigator.navigateToStory(from: viewController)
  }
}
