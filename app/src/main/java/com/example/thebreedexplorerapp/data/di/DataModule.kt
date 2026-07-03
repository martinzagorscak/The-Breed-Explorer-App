package com.example.thebreedexplorerapp.data.di

import android.util.Log
import com.example.thebreedexplorerapp.data.api.DogApi
import com.example.thebreedexplorerapp.data.api.DogApiImpl
import com.example.thebreedexplorerapp.data.repository.DogRepository
import com.example.thebreedexplorerapp.data.repository.DogRepositoryImpl
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.core.qualifier.named
import org.koin.dsl.module
import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.CoroutineContext

const val BACKGROUND_SCOPE = "backgroundScope"
const val EXCEPTION_HANDLER = "exceptionHandler"

val dataModule = module {
    // background concurrency
    single<CoroutineScope>(named(BACKGROUND_SCOPE)) {
        CoroutineScope(Dispatchers.IO + SupervisorJob() + get(named(EXCEPTION_HANDLER)))
    }
    factory<CoroutineContext>(named(EXCEPTION_HANDLER)) {
        object : AbstractCoroutineContextElement(CoroutineExceptionHandler), CoroutineExceptionHandler {
            override fun handleException(context: CoroutineContext, exception: Throwable) {
                Log.e("APP_BACKGROUND_SCOPE", "Exception, $exception was thrown inside context: $context")
            }

        }
    }

    // TODO ktor client
    single<DogApi> { DogApiImpl() }
    single<DogRepository> {
        DogRepositoryImpl(
            dogApi = get(),
            scope = get(named(BACKGROUND_SCOPE)),
        )
    }
}
