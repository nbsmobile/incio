//
//  SessionManager.swift
//  iosApp
//
//  Created by Uwais Alqadri on 2/1/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import Shared

struct SessionManager {

  @LazyKoin static var accountManager: AccountManager

  @LazyKoin static var preferenceManager: PreferenceManager

  static func isLoggedIn() -> Bool {
    accountManager.isLoggedIn()
  }

  static func logout() {
    accountManager.logout()
  }

  static func getProfile() -> UserData {
    accountManager.getUserData()
  }

  static func getBoolean(key: String, defaultValue: Bool = false) -> Bool {
    preferenceManager.getBoolean(key: key, default: defaultValue)
  }

  static func saveBoolean(key: String, value: Bool) {
    preferenceManager.saveBoolean(key: key, value: value)
  }

  static func getInt(key: String) -> Int? {
    preferenceManager.getInt(key: key) as? Int
  }

  static func saveInt(key: String, value: Int) {
    preferenceManager.saveInt(key: key, value: Int32(value))
  }

  static func getString(key: String) -> String? {
    preferenceManager.getString(key: key)
  }

  static func saveString(key: String, value: String) {
    preferenceManager.saveString(key: key, value: value)
  }

  static func getLong(key: String) -> Int64? {
    preferenceManager.getLong(key: key) as? Int64
  }

  static func saveLong(key: String, value: Int64) {
    preferenceManager.saveLong(key: key, value: value)
  }

  static func getFloat(key: String) -> Float? {
    preferenceManager.getFloat(key: key) as? Float
  }

  static func saveFloat(key: String, value: Float) {
    preferenceManager.saveFloat(key: key, value: value)
  }

  static func clear() {
    preferenceManager.clear()
  }
}
