package com.markettwits.waifupics.random.report.api

import com.markettwits.async.AsyncViewModel
import com.markettwits.async.RunAsync
import com.markettwits.async.dispatchersList
import com.markettwits.waifupics.random.cloud.nekoServiceCloudModule
import com.markettwits.waifupics.random.report.impl.ReportImageViewModelBase
import com.markettwits.waifupics.random.report.impl.ReportedImageMapper
import com.markettwits.waifupics.result.HandleNetworkResult
import com.markettwits.waifupics.result.NetworkExceptionMapper
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val reportImageModule = module {
    includes(nekoServiceCloudModule)
    singleOf(::ReportImageViewModelBase) bind ReportImageViewModel::class
    singleOf(::ReportedImageMapper)
    single<AsyncViewModel<Unit>> {
        AsyncViewModel.Base(RunAsync.Base(dispatchersList))
    }
    single<HandleNetworkResult> {
        HandleNetworkResult.Base(NetworkExceptionMapper())
    }
}