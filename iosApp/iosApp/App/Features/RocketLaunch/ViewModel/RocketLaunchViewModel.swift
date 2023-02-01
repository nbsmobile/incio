//
//  RocketLaunchViewModel.swift
//  iosApp
//
//  Created by Uwais Alqadri on 1/30/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Combine
import Shared
import SwiftUI

class RocketLaunchViewModel: ObservableObject {

  @Published var rocketLaunch: DataState<[RocketLaunch]> = .initiate
  @Published var stories: DataState<[Story]> = .initiate
  @Published var removeRocketLaunch: DataState<KotlinBoolean> = .initiate

  @LazyKoin private var rocketLaunchUseCase: RocketLaunchUseCase
  @LazyKoin private var storyUseCase: StoryUseCase

  private var cancellables = Set<AnyCancellable>()

  func getRocketLaunches() {
    rocketLaunch = .loading
    viewStatePublisher(
      for: rocketLaunchUseCase.getRocketLaunchesNative(),
      in: &cancellables
    ) { self.rocketLaunch = $0 }
  }

  func getAllStories(storyParam: GetStoryParam) {
    stories = .loading
    viewStatePublisher(
      for: storyUseCase.getAllStoriesNative(storyParam: storyParam),
      in: &cancellables
    ) { self.stories = $0 }
  }


  func doRemoveRocketLaunches() {
    removeRocketLaunch = .loading
    viewStatePublisher(
      for: rocketLaunchUseCase.removeRocketLaunchesNative(),
      in: &cancellables
    ) { self.removeRocketLaunch = $0 }
  }
}

