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

  @State var emailValue = ""
  @State var passwordValue = ""

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

        Spacer()

        VStack(spacing: 16) {
          Button {

          } label: {
            Text("Masuk")
              .font(.callout)
              .bold()
              .foregroundColor(.white)
          }
          .frame(maxWidth: .infinity)
          .frame(height: 44)
          .background(Color.orange)
          .cornerRadius(8)

          Text("Belum punya akun?")
            .font(.callout)
            .bold()
            .foregroundColor(.black)

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
  }
}

struct LoginView_Previews: PreviewProvider {
  static var previews: some View {
    let assembler = AppAssembler()
    LoginView(holder: assembler.resolve(), navigator: assembler.resolve())
  }
}
