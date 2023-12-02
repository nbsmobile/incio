//
//  RocketLaunchView.swift
//  iosApp
//
//  Created by Uwais Alqadri on 1/30/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import Shared

struct RocketLaunchView: WrappedView {

  var holder: WrapperHolder

  @StateObject var viewModel: RocketLaunchViewModel
  @State var rockets = [RocketLaunch]()

  init(holder: WrapperHolder, viewModel: RocketLaunchViewModel) {
    self.holder = holder
    self._viewModel = StateObject(wrappedValue: viewModel)

    viewModel.fetchRocketLaunches()
  }

  var body: some View {
    VStack(spacing: 0) {
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
        List(rockets, id: \.self) { item in
          Text(item.missionName)
            .bold()
            .padding(10)
            .background(Color.orange)
            .cornerRadius(16)
        }.onAppear {
          rockets = data
        }

        Button(action: {
          viewModel.doRemoveRocketLaunches()
        }) {
          Text("Remove All Launches from Database")
            .multilineTextAlignment(.center)
            .foregroundColor(.white)
        }
        .frame(maxWidth: .infinity)
        .frame(height: 44)
        .background(Color.orange)
        .dataState(
          viewModel.$removeRocketLaunch,
          onSuccess: { _ in
            rockets = []
          },
          onFailed: { print($0.apiError?.errorMessage ?? "") }
        )
      }
    }
  }

}
