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
  @Published var uploadStory: DataState<KotlinBoolean> = .initiate

  @LazyKoin private var storyUseCase: StoryUseCase

  private var cancellables = Set<AnyCancellable>()

  func fetchAllStories(storyParam: GetStoryParam) {
    stories = .loading
    viewStatePublisher(
      for: storyUseCase.getAllStoriesNative(storyParam: storyParam),
      in: &cancellables
    ) { self.stories = $0 }
  }

  func doUploadStory(file: KotlinByteArray, description: String) {
    uploadStory = .loading
    viewStatePublisher(
      for: storyUseCase.uploadStoryNative(file: file, description: description),
      in: &cancellables
    ) { self.uploadStory = $0 }
  }
}
