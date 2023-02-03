//
//  RocketLaunchView.swift
//  iosApp
//
//  Created by Uwais Alqadri on 1/30/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct RocketLaunchView: WrappedView {

  var holder: WrapperHolder

  @StateObject var viewModel: RocketLaunchViewModel

  init(holder: WrapperHolder, viewModel: RocketLaunchViewModel) {
    self.holder = holder
    self._viewModel = StateObject(wrappedValue: viewModel)

    viewModel.getAllStories(storyParam: .init(page: 1, size: 10, isIncludeLocation: false))
  }

  var body: some View {
    if case .loading = viewModel.rocketLaunch {
      ActivityIndicator(isAnimating: .constant(true), style: .medium, color: .gray)
    }

    if case let .failed(error) = viewModel.rocketLaunch {
      Text(error.apiError?.message ?? "")
        .bold()
        .padding(10)
        .background(Color.gray)
        .cornerRadius(16)
    }

    if case let .success(data) = viewModel.rocketLaunch {
      List(data, id: \.self) { item in
        Text(item.missionName)
          .bold()
          .padding(10)
          .background(Color.gray)
          .cornerRadius(16)
      }

      Button(action: {
        viewModel.doRemoveRocketLaunches()
      }) {
        Text("Remove All Launches from Database")
          .multilineTextAlignment(.center)
      }
    }
  }

}
