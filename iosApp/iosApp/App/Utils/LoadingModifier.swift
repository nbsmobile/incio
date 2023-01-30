//
//  LoadingModifier.swift
//
//  Created by Asep Mulyana on 22/10/22.
//  Copyright © 2022 Nusantara Beta Studio All rights reserved.
//

import SwiftUI

struct LoadingModifier: ViewModifier {
  func body(content: Content) -> some View {
    ZStack(alignment: .center) {
      VStack(alignment: .center) {
        ProgressView()
          .progressViewStyle(
            CircularProgressViewStyle(
              tint: .white
            )
          )
          .padding(40)
      }
      .background(Color(white: 0, opacity: 0.6))
      .cornerRadius(20)

      content
    }
//    .allowsHitTesting(false)
  }
}

struct LoadingModifier_Previews: PreviewProvider {
  static var previews: some View {
    Text("Hello, world!")
      .modifier(LoadingModifier())
  }
}
