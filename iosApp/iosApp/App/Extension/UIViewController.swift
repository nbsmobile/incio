//
//  UIViewController.swift
//  iosApp
//
//  Created by Abdhi P (Work) on 01/02/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import UIKit

enum NavigationBarType {
  case title(title: String)
  case backAndTitle(title: String?)
  case hidden
  case add
}

protocol NavigationBarButtonHandler {
  func leftNavigationBarButtonTapped(sender: UIBarButtonItem?)
  func rightNavigationBarButtonTapped(sender: UIBarButtonItem?)
}

extension UIViewController {
  private func setDefaultNavigationTheme() {
    navigationController?.setNavigationBarHidden(false, animated: true)
    navigationController?.interactivePopGestureRecognizer?.delegate = nil
    navigationController?.navigationBar.isTranslucent = false
    navigationController?.navigationBar.setBackgroundImage(UIImage(), for: .default)
    navigationController?.navigationBar.backgroundColor = .orange
    navigationController?.navigationBar.barTintColor = .white
    navigationController?.navigationBar.titleTextAttributes = [
      //      NSAttributedString.Key.font: Font.Poppins.h6HeadingSemibold,
      NSAttributedString.Key.foregroundColor: UIColor.white
    ]

    if #available(iOS 13.0, *) {
      let appearance = UINavigationBarAppearance()
      appearance.configureWithOpaqueBackground()
      appearance.backgroundColor = .orange
      appearance.shadowColor = .clear
      appearance.titleTextAttributes = [
        //        NSAttributedString.Key.font: Font.Poppins.h6HeadingSemibold,
        NSAttributedString.Key.foregroundColor: UIColor.white
      ]
      navigationController?.navigationBar.tintColor = .white
      navigationController?.navigationBar.standardAppearance = appearance
      navigationController?.navigationBar.scrollEdgeAppearance = navigationController?.navigationBar.standardAppearance
    }
  }

  func setNavigationBar(type: NavigationBarType) {
    setDefaultNavigationTheme()

    switch type {
    case .title(let title):
      self.title = title
    case .backAndTitle(let title):
      self.title = title
      navigationItem.rightBarButtonItem = nil
    case .hidden:
      if #available(iOS 16.0, *) {
        navigationController?.additionalSafeAreaInsets.top = -25
      }

      navigationController?.setNavigationBarHidden(true, animated: false)
      navigationItem.leftBarButtonItem = nil
      navigationItem.hidesBackButton = true
      navigationController?.navigationBar.prefersLargeTitles = false
      navigationController?.navigationItem.largeTitleDisplayMode = .never
      navigationController?.setToolbarHidden(true, animated: false)
      navigationController?.hidesBottomBarWhenPushed = true
      navigationController?.hidesBarsWhenVerticallyCompact = true
      navigationController?.hidesBarsOnSwipe = true
      navigationController?.isNavigationBarHidden = true
    case .add:
      navigationController?.setNavigationBarHidden(false, animated: true)
      navigationItem.rightBarButtonItem = createRightButton(icon: UIImage(systemName: "plus")!)
    }
  }
}

extension UIViewController {
  private func createCenterLogo(icon: UIImage) -> UIView {
    let imageView = UIImageView(image: icon)
    imageView.contentMode = .scaleAspectFit
    return imageView
  }

  private func createLeftButton(icon: UIImage) -> UIBarButtonItem {
    let button = UIButton(type: .system)
    button.setImage(icon.withRenderingMode(.alwaysOriginal), for: .normal)
    button.addTarget(self, action: #selector(self.leftNavigationBarButtonTapped), for: .touchUpInside)

    let barButton = UIBarButtonItem(customView: button)
    barButton.tintColor = .white
    barButton.customView?.translatesAutoresizingMaskIntoConstraints = false
    barButton.customView?.heightAnchor.constraint(equalToConstant: 24).isActive = true
    barButton.customView?.widthAnchor.constraint(equalToConstant: 24).isActive = true
    return barButton
  }

  private func createRightButton(icon: UIImage) -> UIBarButtonItem {
    let button = UIButton(type: .system)
    button.setImage(icon, for: .normal)
    button.tintColor = .white
    button.addTarget(self, action: #selector(self.rightNavigationBarButtonTapped), for: .touchUpInside)

    let barButton = UIBarButtonItem(customView: button)
    barButton.customView?.translatesAutoresizingMaskIntoConstraints = false
    barButton.customView?.heightAnchor.constraint(equalToConstant: 24).isActive = true
    barButton.customView?.widthAnchor.constraint(equalToConstant: 24).isActive = true
    return barButton
  }

  private func createLabel(title: String, font: UIFont) -> UIBarButtonItem {
    let titleLabel = UILabel()
    titleLabel.font = font
    titleLabel.text = title
    titleLabel.textColor = .white
    return UIBarButtonItem(customView: titleLabel)
  }
}

extension UIViewController: NavigationBarButtonHandler {
  @objc func rightNavigationBarButtonTapped(sender: UIBarButtonItem?) {
  }

  @objc func leftNavigationBarButtonTapped(sender: UIBarButtonItem?) {
  }
}
