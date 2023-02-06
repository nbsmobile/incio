//
//  PrefView.swift
//  iosApp
//
//  Created by Uwais Alqadri on 2/4/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct PrefView: WrappedView {

  @State private var value = SessionManager.getString(key: "your_pref") ?? ""

  var holder: WrapperHolder

  var body: some View {
    VStack {
      TextField("Input anything", text: $value)
        .font(.callout)
        .padding(.vertical, 10)
        .padding(.horizontal, 12)
        .keyboardType(.emailAddress)
        .autocorrectionDisabled(true)
        .autocapitalization(.none)
        .overlay(RoundedRectangle(cornerRadius: 8)
          .stroke(Color.gray, lineWidth: 1)
        )

      HStack {
        Button {
          SessionManager.saveString(key: "your_pref", value: "")
          value = ""
        } label: {
          Text("Delete")
            .font(.callout)
            .bold()
            .foregroundColor(.white)
        }
        .frame(maxWidth: .infinity)
        .frame(height: 44)
        .background(Color.orange)
        .cornerRadius(8)

        Button {
          SessionManager.saveString(key: "your_pref", value: value)
        } label: {
          Text("Save")
            .font(.callout)
            .bold()
            .foregroundColor(.white)
        }
        .frame(maxWidth: .infinity)
        .frame(height: 44)
        .background(Color.orange)
        .cornerRadius(8)
      }

      Spacer()

    }.padding(.horizontal, 16)
  }
}
