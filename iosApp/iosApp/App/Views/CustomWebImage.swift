//
//  CustomWebImage.swift
//  iosApp
//
//  Created by Abdhi P (Work) on 02/02/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import SDWebImageSwiftUI

struct CustomWebImage: View {

  let urlImage: String
  var contentMode: ContentMode = .fill
  var width: CGFloat? = nil
  var height: CGFloat? = nil

  var body: some View {
    WebImage(url: URL(string: urlImage))
      .resizable()
      .frame(width: width, height: height)
      .aspectRatio(contentMode: contentMode)
      .scaledToFill()
  }
}

