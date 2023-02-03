package com.nbs.kmm.sample.di.feature

import com.nbs.kmm.sample.data.membership.MembershipDataStore
import com.nbs.kmm.sample.data.membership.MembershipRepository
import com.nbs.kmm.sample.data.membership.remote.MembershipApi
import com.nbs.kmm.sample.data.membership.remote.MembershipApiClient
import com.nbs.kmm.sample.di.NETWORK_CLIENT
import com.nbs.kmm.sample.domain.membership.MembershipInteractor
import com.nbs.kmm.sample.domain.membership.MembershipUseCase
import org.koin.core.qualifier.named
import org.koin.dsl.module

val membershipModule = module {
    single<MembershipApiClient> { MembershipApi(get(named(NETWORK_CLIENT))) }

    single<MembershipRepository> { MembershipDataStore(get()) }

    single<MembershipUseCase> { MembershipInteractor(get(), get()) }
}