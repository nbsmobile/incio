//
//  ImagePicker.swift
//
//  Created by Uwais Alqadri on 12/5/22.
//  Copyright © 2022 Nusantara Beta Studio. All rights reserved.
//

import PhotosUI
import SwiftUI
import UIKit

struct ImagePicker: UIViewControllerRepresentable {
  @Binding var image: UIImage?
  @Binding var imageName: String

  func makeUIViewController(context: Context) -> PHPickerViewController {
    var config = PHPickerConfiguration()
    config.filter = .images
    let picker = PHPickerViewController(configuration: config)
    picker.delegate = context.coordinator
    return picker
  }

  func updateUIViewController(_ uiViewController: PHPickerViewController, context: Context) {

  }

  func makeCoordinator() -> Coordinator {
    Coordinator(self)
  }

  class Coordinator: NSObject, PHPickerViewControllerDelegate {
    let parent: ImagePicker

    init(_ parent: ImagePicker) {
      self.parent = parent
    }

    func picker(_ picker: PHPickerViewController, didFinishPicking results: [PHPickerResult]) {
      picker.dismiss(animated: true) {
        guard let provider = results.first?.itemProvider else { return }

        provider.loadFileRepresentation(forTypeIdentifier: "public.item") { (url, error) in
          if error != nil {
            print("error \(error!)");
          } else {
            if let url = url {
              let filename = url.lastPathComponent;
              self.parent.imageName = filename
            }
          }
        }

        if provider.canLoadObject(ofClass: UIImage.self) {
          provider.loadObject(ofClass: UIImage.self) { image, _ in
            self.parent.image = image as? UIImage
          }
        }
      }
    }
  }
}

