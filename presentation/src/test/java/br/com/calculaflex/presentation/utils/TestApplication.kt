package br.com.calculaflex.presentation.utils

import android.app.Application
import br.com.calculaflex.domain.entity.RequestState
import br.com.calculaflex.domain.usecases.GetMinAppVersionUseCase
import br.com.calculaflex.presentation.di.featureModules
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import org.koin.android.ext.koin.androidContext
import org.koin.core.KoinApplication
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.dsl.module

class TestApplication : Application() {

    private var getMinAppVersionUseCase : GetMinAppVersionUseCase = mockk(relaxed = true)

    companion object {
        var isPicassoInitialized: Boolean = false
    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@TestApplication)
            featureModules.forEach {
                modules(it)
            }
            mock()
        }

        if (!isPicassoInitialized) {
            isPicassoInitialized = true
            val picasso = Picasso.Builder(this).build()
            Picasso.setSingletonInstance(picasso)
        }
    }

    private fun mock() {
        coEvery { getMinAppVersionUseCase.getMinVersionApp() } returns RequestState.Success(1)

        mockKoin()
    }

    private fun mockKoin() {
        loadKoinModules(module {
            factory(override = true) { getMinAppVersionUseCase }
        })
    }
}