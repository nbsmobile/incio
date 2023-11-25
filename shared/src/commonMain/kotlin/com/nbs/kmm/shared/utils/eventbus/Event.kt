package com.nbs.kmm.shared.utils.eventbus

import com.nbs.kmm.shared.utils.AppTokenException
import com.nbs.kmm.shared.utils.UnauthorizedException

class Event {
    data class UserUnauthorized(val error: UnauthorizedException)

    data class AppTokenUnauthorized(val error: AppTokenException)
}