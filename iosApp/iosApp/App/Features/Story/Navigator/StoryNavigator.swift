// 
//  StoryNavigator.swift
//  iosApp
//
//  Created by Abdhi P (Work) on 01/02/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import UIKit

protocol StoryNavigator {
  func navigateToStory(window: UIWindow?)
  func navigateToAddStory(from viewController: UIViewController)
}

struct DefaultStoryNavigator: StoryNavigator {
  private let assembler: Assembler

  init(assembler: Assembler) {
    self.assembler = assembler
  }

  func navigateToStory(window: UIWindow?) {
    let view: StoryView = assembler.resolve()
    window?.rootViewController = UINavigationController(rootViewController: view.viewController)
  }

  func navigateToAddStory(from viewController: UIViewController) {
    let view: AddStoryView = assembler.resolve()
    viewController.navigationController?.pushViewController(view.viewController, animated: true)
  }
}
