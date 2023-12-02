//
//  StoryView.swift
//  iosApp
//
//  Created by Abdhi P (Work) on 01/02/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import Shared

struct StoryView: WrappedView {
  var holder: WrapperHolder
  var navigator: StoryNavigator

  @StateObject var viewModel: StoryViewModel

  init(holder: WrapperHolder, navigator: StoryNavigator, viewModel: StoryViewModel) {
    self.holder = holder
    self.navigator = navigator
    self._viewModel = StateObject(wrappedValue: viewModel)
  }

  func configureNavigationBar(viewController: UIViewController) {
    viewController.setNavigationBar(type: .add)
  }

  func rightNavigationBarButtonTapped() {
    guard let viewController = holder.viewController else { return }
    navigator.navigateToAddStory(from: viewController)
  }

  var body: some View {
    VStack {
      if case let .success(data) = viewModel.stories {
        ScrollView {
          ForEach(data, id: \.id) { item in
            StoryRow(item: item)
          }
        }
        .background(Color.gray.opacity(0.1))
      } else {
        Text("No data available...")
          .font(.headline)
          .foregroundColor(.gray)
      }
    }
    .onAppear {
      viewModel.fetchAllStories(storyParam: .init(page: 1, size: 10, isIncludeLocation: false))
    }
  }
}

struct StoryRow: View {

  let item: Story

  var body: some View {
    VStack(spacing: 12) {
      HStack {
        Image(uiImage: UIImage(systemName: "person")!)
          .padding(10)
          .background(Color.orange.opacity(0.2))
          .clipShape(Circle())

        Text(item.name)
          .font(.headline)
          .bold()
          .foregroundColor(.black)

        Spacer()
      }
      .padding(.horizontal, 8)

      VStack(alignment: .leading, spacing: 12) {
        CustomWebImage(
          urlImage: item.photoUrl,
          width: UIScreen.screenWidth,
          height: 348)

        Text(item.description_)
          .font(.caption)
          .bold()
          .foregroundColor(.black)
          .padding(.horizontal, 8)
      }
    }
    .padding(.vertical, 12)
    .background(Color.white)
  }
}
