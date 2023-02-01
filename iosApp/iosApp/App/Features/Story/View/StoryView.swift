//
//  StoryView.swift
//  iosApp
//
//  Created by Abdhi P (Work) on 01/02/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct StoryView: WrappedView {
  var holder: WrapperHolder
  var navigator: StoryNavigator

  func configureNavigationBar(viewController: UIViewController) {
    viewController.setNavigationBar(type: .add)
  }

  func rightNavigationBarButtonTapped() {
    guard let viewController = holder.viewController else { return }
    navigator.navigateToAddStory(from: viewController)
  }

  var body: some View {
    VStack {
      ScrollView {
        ForEach(1...5, id: \.self) { index in
          StoryRow()
        }
      }
    }
    .background(Color.gray.opacity(0.1))
  }
}

struct StoryRow: View {

  var body: some View {
    VStack(spacing: 12) {
      HStack {
        Image(uiImage: UIImage(systemName: "person")!)
          .padding(10)
          .background(Color.orange.opacity(0.2))
          .clipShape(Circle())

        Text("Name")
          .font(.headline)
          .bold()
          .foregroundColor(.black)

        Spacer()
      }
      .padding(.horizontal, 8)

      VStack(spacing: 12) {
        CustomWebImage(urlImage: "https://trilogi.ac.id/universitas/wp-content/uploads/2017/07/dummy-img.png")
          .frame(height: 348)
          .frame(width: 128)
          .frame(maxWidth: .infinity)

        Text("Lorem ipsum is placeholder text commonly used in the graphic, print, and publishing industries")
          .font(.caption)
          .foregroundColor(.black)
          .padding(.horizontal, 8)
      }
    }
    .padding(.vertical, 12)
    .background(Color.white)
  }
}
