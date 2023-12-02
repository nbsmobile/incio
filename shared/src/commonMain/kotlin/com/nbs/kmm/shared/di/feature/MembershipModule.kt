package com.nbs.kmm.shared.di.feature

import com.nbs.kmm.shared.data.membership.MembershipDataStore
import com.nbs.kmm.shared.data.membership.MembershipRepository
import com.nbs.kmm.shared.data.membership.remote.MembershipApi
import com.nbs.kmm.shared.data.membership.remote.MembershipApiClient
import com.nbs.kmm.shared.di.NETWORK_CLIENT
import com.nbs.kmm.shared.domain.membership.MembershipInteractor
import com.nbs.kmm.shared.domain.membership.MembershipUseCase
import org.koin.core.qualifier.named
import org.koin.dsl.module

val membershipModule = module {
    single<MembershipApiClient> { MembershipApi(get(named(NETWORK_CLIENT))) }

    single<MembershipRepository> { MembershipDataStore(get()) }

    single<MembershipUseCase> { MembershipInteractor(get(), get()) }
}