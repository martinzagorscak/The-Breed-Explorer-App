package com.example.thebreedexplorerapp.data.di

import android.util.Log
import com.example.thebreedexplorerapp.data.api.DogApi
import com.example.thebreedexplorerapp.data.api.DogApiImpl
import com.example.thebreedexplorerapp.data.client.defaultHttpClient
import com.example.thebreedexplorerapp.data.repository.DogRepository
import com.example.thebreedexplorerapp.data.repository.DogRepositoryImpl
import io.ktor.client.HttpClient
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.serialization.json.Json
import org.koin.core.qualifier.named
import org.koin.dsl.module
import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.CoroutineContext

const val BACKGROUND_SCOPE = "backgroundScope"
const val EXCEPTION_HANDLER = "exceptionHandler"
const val HTTP_CLIENT = "httpClient"

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

    single<HttpClient>(named(HTTP_CLIENT)) {
        defaultHttpClient(
            json = Json {
                ignoreUnknownKeys = true
                isLenient = true
            },
        )
    }

    single<DogApi> { DogApiImpl(client = get(named(HTTP_CLIENT))) }
    single<DogRepository> {
        DogRepositoryImpl(
            dogApi = get(),
            scope = get(named(BACKGROUND_SCOPE)),
        )
    }
}
