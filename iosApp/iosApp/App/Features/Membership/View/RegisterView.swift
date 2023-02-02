//
//  RegisterView.swift
//  iosApp
//
//  Created by Abdhi P (Work) on 01/02/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct RegisterView: WrappedView {
  var holder: WrapperHolder
  var navigator: MembershipNavigator

  @StateObject var viewModel: MembershipViewModel

  @State var usernameValue = ""
  @State var emailValue = ""
  @State var passwordValue = ""
  @State var confirmPasswordValue = ""

  var isValid: Bool {
    !usernameValue.isEmpty && !emailValue.isEmpty && !passwordValue.isEmpty && passwordValue.count >= 6 && passwordValue == confirmPasswordValue
  }

  init(holder: WrapperHolder, navigator: MembershipNavigator, viewModel: MembershipViewModel) {
    self.holder = holder
    self.navigator = navigator
    self._viewModel = StateObject(wrappedValue: viewModel)
  }

  var body: some View {
    VStack {
      VStack(alignment: .center, spacing: 16) {
        Text("Resigter")
          .font(.largeTitle)
          .bold()
          .foregroundColor(.black)

        Spacer()
          .frame(height: 24)

        VStack(alignment: .leading, spacing: 8) {
          Text("Username")
            .font(.subheadline)
            .bold()
            .foregroundColor(.black)

          TextField("Team Sample", text: $usernameValue)
            .font(.callout)
            .padding(.vertical, 10)
            .padding(.horizontal, 12)
            .overlay(RoundedRectangle(cornerRadius: 8)
              .stroke(Color.gray, lineWidth: 1)
            )
        }

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
            .textCase(.lowercase)
            .overlay(RoundedRectangle(cornerRadius: 8)
              .stroke(Color.gray, lineWidth: 1)
            )
        }

        VStack(alignment: .leading, spacing: 8) {
          Text("Kata Sandi")
            .font(.subheadline)
            .bold()
            .foregroundColor(.black)

          SecureField("••••••••••••••••••", text: $passwordValue)
            .font(.callout)
            .padding(.vertical, 10)
            .padding(.horizontal, 12)
            .overlay(RoundedRectangle(cornerRadius: 8)
              .stroke(Color.gray, lineWidth: 1)
            )
        }

        VStack(alignment: .leading, spacing: 8) {
          Text("Konfirmasi Kata Sandi")
            .font(.subheadline)
            .bold()
            .foregroundColor(.black)

          VStack(alignment: .leading, spacing: 2) {
            SecureField("••••••••••••••••••", text: $confirmPasswordValue)
              .font(.callout)
              .padding(.vertical, 10)
              .padding(.horizontal, 12)
              .overlay(RoundedRectangle(cornerRadius: 8)
                .stroke(Color.gray, lineWidth: 1)
              )

            if passwordValue != confirmPasswordValue {
              Text("Kata Sandi tidak sama...")
                .font(.footnote)
                .foregroundColor(.red)
            }
          }
        }

        Spacer()

        if case .loading = viewModel.register {
          ActivityIndicator(isAnimating: .constant(true), style: .medium, color: .gray)
        } else {
          Button {
            viewModel.doRegister(registerParam: .init(name: usernameValue, email: emailValue, password: passwordValue))
          } label: {
            Text("Daftar")
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
      }
      .padding(16)
      .padding(.vertical, 16)
    }
    .dataState(
      viewModel.$register,
      onSuccess: { data in
        guard let viewController = holder.viewController else { return }
        viewController.navigationController?.popToRootViewController(animated: true)
      },
      onFailed: { error in
        print("Error: \(error.localizedDescription)")
      }
    )
  }
}

struct RegisterView_Previews: PreviewProvider {
  static var previews: some View {
    let assembler = AppAssembler()
    RegisterView(holder: assembler.resolve(), navigator: assembler.resolve(), viewModel: assembler.resolve())
  }
}
