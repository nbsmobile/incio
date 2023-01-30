//
//  FeatureAssembler.swift
//  iosApp
//
//  Created by Uwais Alqadri on 1/30/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation

protocol FeatureAssembler {
  func resolve() -> WrapperHolder
  func resolve() -> RocketLaunchNavigator
  func resolve() -> RocketLaunchView
  func resolve() -> RocketLaunchViewModel
}

extension FeatureAssembler where Self: Assembler {
  func resolve() -> RocketLaunchNavigator {
    DefaultRocketLaunchNavigator(assembler: self)
  }

  func resolve() -> RocketLaunchView {
    RocketLaunchView(holder: resolve(), viewModel: resolve())
  }

  func resolve() -> RocketLaunchViewModel {
    RocketLaunchViewModel()
  }
}
