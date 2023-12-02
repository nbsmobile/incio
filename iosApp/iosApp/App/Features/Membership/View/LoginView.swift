//
//  LoginView.swift
//  iosApp
//
//  Created by Abdhi P (Work) on 01/02/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct LoginView: WrappedView {
  var holder: WrapperHolder
  var navigator: MembershipNavigator

  @StateObject var viewModel: MembershipViewModel

  @State var emailValue = ""
  @State var passwordValue = ""
  @State var errorValue = ""

  var isValid: Bool {
    !emailValue.isEmpty && !passwordValue.isEmpty && passwordValue.count >= 6
  }

  init(holder: WrapperHolder, navigator: MembershipNavigator, viewModel: MembershipViewModel) {
    self.holder = holder
    self.navigator = navigator
    self._viewModel = StateObject(wrappedValue: viewModel)
  }

  var body: some View {
    VStack {
      VStack(alignment: .center, spacing: 16) {
        Text("Login")
          .font(.largeTitle)
          .bold()
          .foregroundColor(.black)

        Spacer()
          .frame(height: 24)

        VStack(alignment: .leading, spacing: 8) {
          Text("Email")
            .font(.subheadline)
            .bold()
            .foregroundColor(.black)

          TextField("sample@gmail.com", text: $emailValue)
            .font(.callout)
            .padding(.vertical, 10)
            .padding(.horizontal, 12)
            .keyboardType(.emailAddress)
            .autocorrectionDisabled(true)
            .autocapitalization(.none)
            .overlay(RoundedRectangle(cornerRadius: 8)
              .stroke(Color.gray, lineWidth: 1)
            )
        }

        VStack(alignment: .leading, spacing: 8) {
          Text("Kata Sandi")
            .font(.subheadline)
            .bold()
            .foregroundColor(.black)

          VStack(alignment: .leading, spacing: 2) {
            SecureField("••••••••••••••••••", text: $passwordValue)
              .font(.callout)
              .padding(.vertical, 10)
              .padding(.horizontal, 12)
              .autocorrectionDisabled(true)
              .autocapitalization(.none)
              .overlay(RoundedRectangle(cornerRadius: 8)
                .stroke(Color.gray, lineWidth: 1)
              )

            if !errorValue.isEmpty {
              Text(errorValue)
                .font(.footnote)
                .foregroundColor(.red)
            }
          }
        }

        Spacer()

        VStack(spacing: 16) {
          if case .loading = viewModel.login {
            ActivityIndicator(isAnimating: .constant(true), style: .medium, color: .gray)
          } else {
            Button {
              errorValue.removeAll()
              viewModel.doLogin(email: emailValue.lowercased(), password: passwordValue)
            } label: {
              Text("Masuk")
                .font(.callout)
                .bold()
                .foregroundColor(.white)
            }
            .frame(maxWidth: .infinity)
            .frame(height: 44)
            .background(!isValid ? Color.gray : Color.orange)
            .cornerRadius(8)
            .disabled(!isValid)
          }

          Text("Belum punya akun?")
            .font(.callout)
            .bold()
            .foregroundColor(.gray)

          Button {
            guard let viewController = holder.viewController else { return }
            navigator.navigateToRegister(from: viewController)
          } label: {
            Text("Daftar")
              .font(.callout)
              .bold()
              .foregroundColor(.orange)
          }
          .frame(maxWidth: .infinity)
          .frame(height: 44)
          .background(Color.white)
          .overlay(RoundedRectangle(cornerRadius: 8)
            .stroke(Color.orange, lineWidth: 1)
          )
        }
      }
      .padding(16)
      .padding(.vertical, 16)
    }
    .dataState(
      viewModel.$login,
      onSuccess: { data in
        guard let viewController = holder.viewController else { return }
        navigator.navigateToStory(window: viewController.view.window)
      },
      onFailed: { error in
        errorValue = error.apiError?.errorMessage ?? error.localizedDescription
        print("Error: \(error.localizedDescription)")
      }
    )
  }
}

struct LoginView_Previews: PreviewProvider {
  static var previews: some View {
    let assembler = AppAssembler()
    LoginView(holder: assembler.resolve(), navigator: assembler.resolve(), viewModel: assembler.resolve())
  }
}
