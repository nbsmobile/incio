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

  var body: some View {
    WebImage(url: URL(string: urlImage))
      .resizable()
      .aspectRatio(contentMode: contentMode)
      .scaledToFill()
  }
}

