// 
//  MembershipAssembler.swift
//  iosApp
//
//  Created by Abdhi P (Work) on 01/02/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation

protocol MembershipAssembler {
  func resolve() -> MembershipNavigator

  func resolve() -> LoginView
  func resolve() -> RegisterView
  func resolve() -> MainView
  func resolve() -> PrefView

  func resolve() -> MembershipViewModel
}

extension MembershipAssembler where Self: Assembler {
  func resolve() -> MembershipNavigator {
    return DefaultMembershipNavigator(assembler: self)
  }

  func resolve() -> LoginView {
    return LoginView(holder: resolve(), navigator: resolve(), viewModel: resolve())
  }

  func resolve() -> RegisterView {
    return RegisterView(holder: resolve(), navigator: resolve(), viewModel: resolve())
  }

  func resolve() -> MainView {
    MainView(holder: resolve(), navigator: resolve())
  }

  func resolve() -> PrefView {
    PrefView(holder: resolve())
  }

  func resolve() -> MembershipViewModel {
    return MembershipViewModel()
  }
}
