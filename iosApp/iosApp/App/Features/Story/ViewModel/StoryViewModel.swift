//
//  StoryViewModel.swift
//  iosApp
//
//  Created by Abdhi P (Work) on 02/02/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Combine
import Shared
import SwiftUI

class StoryViewModel: ObservableObject {

  @Published var stories: DataState<[Story]> = .initiate

  @LazyKoin private var storyUseCase: StoryUseCase

  private var cancellables = Set<AnyCancellable>()

  func getAllStories(storyParam: GetStoryParam) {
    stories = .loading
    viewStatePublisher(
      for: storyUseCase.getAllStoriesNative(storyParam: storyParam),
      in: &cancellables
    ) { self.stories = $0 }
  }
}
