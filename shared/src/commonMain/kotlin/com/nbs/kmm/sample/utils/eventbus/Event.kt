package com.nbs.kmm.sample.utils.eventbus

import com.nbs.kmm.sample.utils.AppTokenException
import com.nbs.kmm.sample.utils.UnauthorizedException

class Event {
    data class UserUnauthorized(val error: UnauthorizedException)

    data class AppTokenUnauthorized(val error: AppTokenException)
}