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

  @State var usernameValue = ""
  @State var emailValue = ""
  @State var passwordValue = ""
  @State var confirmPasswordValue = ""

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

          SecureField("••••••••••••••••••", text: $confirmPasswordValue)
            .font(.callout)
            .padding(.vertical, 10)
            .padding(.horizontal, 12)
            .overlay(RoundedRectangle(cornerRadius: 8)
              .stroke(Color.gray, lineWidth: 1)
            )
        }

        Spacer()

        Button {

        } label: {
          Text("Daftar")
            .font(.callout)
            .bold()
            .foregroundColor(.white)
        }
        .frame(maxWidth: .infinity)
        .frame(height: 44)
        .background(Color.orange)
        .cornerRadius(8)
      }
      .padding(16)
      .padding(.vertical, 16)
    }
  }
}

struct RegisterView_Previews: PreviewProvider {
  static var previews: some View {
    let assembler = AppAssembler()
    RegisterView(holder: assembler.resolve())
  }
}
