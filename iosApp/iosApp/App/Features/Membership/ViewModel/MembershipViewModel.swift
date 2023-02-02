//
//  MembershipViewModel.swift
//  iosApp
//
//  Created by Abdhi P (Work) on 02/02/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Combine
import Shared
import SwiftUI

class MembershipViewModel: ObservableObject {
  @Published var register: DataState<KotlinBoolean> = .initiate
  @Published var login: DataState<Login> = .initiate

  @LazyKoin private var membershipUseCase: MembershipUseCase

  private var cancellables = Set<AnyCancellable>()

  func doRegister(registerParam: RegisterParam) {
    register = .loading
    viewStatePublisher(
      for: membershipUseCase.registerNative(registerParam: registerParam),
      in: &cancellables
    ) { self.register = $0 }
  }

  func doLogin(email: String, password: String) {
    login = .loading
    viewStatePublisher(
      for: membershipUseCase.loginNative(email: email, password: password),
      in: &cancellables
    ) { self.login = $0 }
  }
}
