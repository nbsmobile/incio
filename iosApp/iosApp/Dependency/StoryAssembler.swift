// 
//  StoryAssembler.swift
//  iosApp
//
//  Created by Abdhi P (Work) on 01/02/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation

protocol StoryAssembler {
  func resolve() -> StoryNavigator
  func resolve() -> StoryView
  func resolve() -> AddStoryView

  func resolve() -> StoryViewModel
}

extension StoryAssembler where Self: Assembler {
  func resolve() -> StoryNavigator {
    return DefaultStoryNavigator(assembler: self)
  }

  func resolve() -> StoryView {
    return StoryView(holder: resolve(), navigator: resolve(), viewModel: resolve())
  }

  func resolve() -> AddStoryView {
    return AddStoryView(holder: resolve())
  }

  func resolve() -> StoryViewModel {
    return StoryViewModel()
  }
}
