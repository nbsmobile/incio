package com.nbs.kmm.sample.utils.eventbus

import com.badoo.reaktive.observable.*
import com.badoo.reaktive.scheduler.mainScheduler
import com.badoo.reaktive.subject.publish.PublishSubject

class EventBus {
    companion object {
        val publisher: PublishSubject<Any> = PublishSubject()

        inline fun <reified T> subscribe(): Observable<T> {
            return publisher.filter {
                it is T
            }.map {
                it as T
            }
        }

        fun wrappedUserUnauthorized(): ObservableWrapper<Event.UserUnauthorized> {
            return subscribe<Event.UserUnauthorized>()
                .observeOn(mainScheduler)
                .wrap()
        }

        fun wrappedAppTokenUnauthorized(): ObservableWrapper<Event.AppTokenUnauthorized> {
            return subscribe<Event.AppTokenUnauthorized>()
                .observeOn(mainScheduler)
                .wrap()
        }

        fun post(event: Any) {
            publisher.onNext(event)
        }
    }
}