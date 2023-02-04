//
//  MainView.swift
//  iosApp
//
//  Created by Uwais Alqadri on 2/4/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct MainView: WrappedView {
  var holder: WrapperHolder
  let navigator: MembershipNavigator

  var body: some View {
    VStack(spacing: 10) {
      Button {
        guard let viewController = holder.viewController else { return }
        navigator.navigateToPref(from: viewController)
      } label: {
        Text("Preference")
          .font(.callout)
          .bold()
          .foregroundColor(.white)
      }
      .frame(maxWidth: .infinity)
      .frame(height: 44)
      .background(Color.orange)
      .cornerRadius(8)

      Button {
        guard let viewController = holder.viewController else { return }
        navigator.navigateToRocketLaunch(from: viewController)
      } label: {
        Text("Rocket Launch")
          .font(.callout)
          .bold()
          .foregroundColor(.white)
      }
      .frame(maxWidth: .infinity)
      .frame(height: 44)
      .background(Color.orange)
      .cornerRadius(8)

      Button {
        guard let viewController = holder.viewController else { return }
        if SessionManager.isLoggedIn() {
          navigator.navigateToStory(from: viewController)
        } else {
          navigator.navigateToLogin(from: viewController)
        }
      } label: {
        Text("Story")
          .font(.callout)
          .bold()
          .foregroundColor(.white)
      }
      .frame(maxWidth: .infinity)
      .frame(height: 44)
      .background(Color.orange)
      .cornerRadius(8)
    }.padding(.horizontal, 20)
  }
}
