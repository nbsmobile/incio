//
//  AppDelegate.swift
//
//  Created by Asep Mulyana on 03/01/20.
//  Copyright © 2020 Asep Mulyana. All rights reserved.
//

import UIKit
import netfox

@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate {

  var window: UIWindow?
  private let assembler = AppAssembler()

  private lazy var navigator: MembershipNavigator = {
    assembler.resolve()
  }()

  var orientationLock = UIInterfaceOrientationMask.portrait

  func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {

    KoinApplication.start()
    setupEnvironment()

    window = UIWindow(frame: UIScreen.main.bounds)
    if SessionManager.isLoggedIn() {
      navigator.navigateToStory(window: window)
    } else {
      navigator.navigateToLogin(window: window)
    }
    window?.makeKeyAndVisible()

    return true
  }

  func setupEnvironment() {
    print("versi \(getVersion())")
    NFX.sharedInstance().start()
  }

  func application(_ application: UIApplication, supportedInterfaceOrientationsFor window: UIWindow?) -> UIInterfaceOrientationMask {
    return self.orientationLock
  }
}

func getVersion() -> String {
  let dictionary = Bundle.main.infoDictionary!
  let version = dictionary["CFBundleShortVersionString"] as! String
  let build = dictionary["CFBundleVersion"] as! String
  return "\(version) (\(build))"
}
