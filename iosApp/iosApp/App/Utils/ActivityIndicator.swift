//
//  ActivityIndicator.swift
//  iosApp
//
//  Created by Uwais Alqadri on 1/30/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct ActivityIndicator: UIViewRepresentable {

  @Binding var isAnimating: Bool
  let style: UIActivityIndicatorView.Style
  let color: UIColor

  func makeUIView(context: UIViewRepresentableContext<ActivityIndicator>) -> UIActivityIndicatorView {
    let activityIndicator = UIActivityIndicatorView(style: style)
    activityIndicator.hidesWhenStopped = true
    activityIndicator.color = color
    return activityIndicator
  }

  func updateUIView(_ activityIndicatorView: UIActivityIndicatorView, context: UIViewRepresentableContext<ActivityIndicator>) {
    isAnimating ? activityIndicatorView.startAnimating() : activityIndicatorView.stopAnimating()
  }
}

