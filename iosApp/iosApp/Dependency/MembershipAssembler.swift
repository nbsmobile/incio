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

  func resolve() -> MembershipViewModel {
    return MembershipViewModel()
  }
}
