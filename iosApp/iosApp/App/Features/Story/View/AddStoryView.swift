//
//  AddStoryView.swift
//  iosApp
//
//  Created by Abdhi P (Work) on 01/02/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct AddStoryView: WrappedView {
  var holder: WrapperHolder

  @State var descriptionValue = ""

  func configureNavigationBar(viewController: UIViewController) {
    viewController.setNavigationBar(type: .backAndTitle(title: "Add Story"))
  }

  var body: some View {
    VStack(spacing: 16) {

      Button {

      } label: {
        if let image = UIImage(systemName: "") {
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
      }

      Spacer()

      Button {

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
    .padding(16)
  }
}

struct AddStoryView_Previews: PreviewProvider {
  static var previews: some View {
    let assembler = AppAssembler()
    AddStoryView(holder: assembler.resolve())
  }
}
