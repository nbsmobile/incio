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
}

extension MembershipAssembler where Self: Assembler {
  func resolve() -> MembershipNavigator {
    return DefaultMembershipNavigator(assembler: self)
  }

  func resolve() -> LoginView {
    return LoginView(holder: resolve(), navigator: resolve())
  }

  func resolve() -> RegisterView {
    return RegisterView(holder: resolve())
  }
}
