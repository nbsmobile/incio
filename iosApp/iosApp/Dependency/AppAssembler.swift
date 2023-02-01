//
//  AppAssembler.swift
//  iosApp
//
//  Created by Uwais Alqadri on 1/30/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation

protocol Assembler: FeatureAssembler, MembershipAssembler {}

class AppAssembler: Assembler {}

extension Assembler {
  func resolve() -> WrapperHolder {
    WrapperHolder()
  }
}
