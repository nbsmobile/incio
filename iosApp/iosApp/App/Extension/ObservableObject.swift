//
//  ObservableObject.swift
//  iosApp
//
//  Created by Uwais Alqadri on 1/30/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Combine
import KMPNativeCoroutinesCombine
import KMPNativeCoroutinesCore

extension ObservableObject {
  func viewStatePublisher<Output, Failure: Error, Unit>(
    for nativeSuspend: @escaping NativeSuspend<NativeFlow<Output, Failure, Unit>, Failure, Unit>,
    in cancellables: inout Set<AnyCancellable>,
    onViewStatable: @escaping (DataState<Output>) -> Void
  ) {
    createPublisher(for: nativeSuspend)
      .receive(on: DispatchQueue.main)
//      .retry(3.times, if: \.isSessionExpired)
      .sink { completion in
        switch completion {
        case .finished: ()
        case .failure(let error):
          onViewStatable(.failed(error: error))
        }
      } receiveValue: { value in
        onViewStatable(.success(data: value))
      }.store(in: &cancellables)

  }

//  func viewStatePublisher<Output, Failure: Error, Unit>(
//    for nativeSuspend: @escaping NativeSuspend<NativeFlow<Output, Failure, Unit>, Failure, Unit>,
//    in cancellables: inout Set<AnyCancellable>,
//    onViewStatable: @escaping (DataState<Output>) -> Void,
//    onSuccess: @escaping () -> Void
//  ) {
//    createPublisher(for: nativeSuspend)
//      .receive(on: DispatchQueue.main)
//      .sink { completion in
//        switch completion {
//        case .finished: ()
//        case .failure(let error):
//          onViewStatable(.failed(error: error))
//        }
//      } receiveValue: { value in
//        onSuccess()
//        onViewStatable(.success(data: value))
//      }.store(in: &cancellables)
//  }
}

extension Publishers {
  struct RetryIf<P: Publisher>: Publisher {
    typealias Output = P.Output
    typealias Failure = P.Failure

    let publisher: P
    let times: Int
    let condition: (P.Failure) -> Bool

    func receive<S>(subscriber: S) where S : Subscriber, Failure == S.Failure, Output == S.Input {
      guard times > 0 else { return publisher.receive(subscriber: subscriber) }

      publisher.catch { (error: P.Failure) -> AnyPublisher<Output, Failure> in
        if condition(error)  {
          return RetryIf(publisher: publisher, times: times - 1, condition: condition).eraseToAnyPublisher()
        } else {
          return Fail(error: error).eraseToAnyPublisher()
        }
      }.receive(subscriber: subscriber)
    }
  }
}

extension Publisher {
  func retry(times: Int, if condition: @escaping (Failure) -> Bool) -> Publishers.RetryIf<Self> {
    Publishers.RetryIf(publisher: self, times: times, condition: condition)
  }
}

extension Publisher {
  func retry(_ times: Int, if condition: @escaping (Failure) -> Bool) -> Publishers.RetryIf<Self> {
    Publishers.RetryIf(publisher: self, times: times, condition: condition)
  }

  func retry(_ times: Int, unless condition: @escaping (Failure) -> Bool) -> Publishers.RetryIf<Self> {
    retry(times, if: { !condition($0) })
  }
}

