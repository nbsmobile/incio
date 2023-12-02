//
//  AddStoryView.swift
//  iosApp
//
//  Created by Abdhi P (Work) on 01/02/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import Shared

struct AddStoryView: WrappedView {

  @StateObject private var viewModel: StoryViewModel

  var holder: WrapperHolder

  @State var inputImage: UIImage? = nil
  @State var descriptionValue = ""

  @State private var isShowImagePicker = false

  init(holder: WrapperHolder, viewModel: StoryViewModel) {
    self.holder = holder
    self._viewModel = StateObject(wrappedValue: viewModel)
  }

  func configureNavigationBar(viewController: UIViewController) {
    viewController.setNavigationBar(type: .backAndTitle(title: "Add Story"))
  }

  var body: some View {
    VStack(spacing: 16) {

      Button {
        isShowImagePicker.toggle()
      } label: {
        if let image = inputImage {
          Image(uiImage: image)
            .resizable()
            .frame(width: 328, height: 328)
            .overlay(
              RoundedRectangle(cornerRadius: 12)
                .stroke(Color.gray, style: .init(lineWidth: 1, dash: [3]))
            )
        } else {
          ZStack {
            VStack(spacing: 10) {
              Image(uiImage: UIImage(systemName: "camera.fill")!)
                .renderingMode(.template)
                .foregroundColor(.gray)

              Text("Masukkan Foto")
                .font(.caption)
                .foregroundColor(.gray)
            }
          }
          .frame(width: 328, height: 328)
          .overlay(
            RoundedRectangle(cornerRadius: 12)
              .stroke(Color.gray, style: .init(lineWidth: 1, dash: [3]))
          )
        }
      }


      VStack(alignment: .leading, spacing: 8) {
        Text("Deskripsi")
          .font(.caption)
          .bold()
          .foregroundColor(.black)

        TextEditor(text: $descriptionValue)
          .frame(height: 100)
          .overlay(RoundedRectangle(cornerRadius: 8)
            .stroke(Color.gray, lineWidth: 1)
          )
          .autocorrectionDisabled(true)
          .autocapitalization(.none)
      }

      Spacer()

      if case .loading = viewModel.uploadStory {
        ActivityIndicator(isAnimating: .constant(true), style: .medium, color: .gray)
      } else {
        Button {
          if let imageData = inputImage?.jpegData(compressionQuality: 1) {
            viewModel.doUploadStory(file: IOSPlatform().toByteArray(nsData: imageData), description: descriptionValue)
          }
        } label: {
          Text("Post")
            .font(.callout)
            .bold()
            .foregroundColor(.white)
        }
        .frame(maxWidth: .infinity)
        .frame(height: 44)
        .background(Color.orange)
        .cornerRadius(8)
      }
    }
    .padding(16)
    .sheet(isPresented: $isShowImagePicker) {
      ImagePicker(image: $inputImage, imageName: .constant(""))
    }
    .dataState(
      viewModel.$uploadStory,
      onSuccess: { data in
        holder.viewController?.navigationController?.popViewController(animated: true)
      },
      onFailed: { print($0.apiError?.errorMessage ?? "") }
    )
  }
}
