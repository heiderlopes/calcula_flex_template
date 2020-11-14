package br.com.calculaflex

import android.app.Application
import br.com.calculaflex.presentation.di.featureModules
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

@ExperimentalCoroutinesApi
class CalculaFlexApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {

            androidContext(this@CalculaFlexApplication)
            //inicializa os modulos
            featureModules.forEach {
                modules(it)
            }
        }
    }
}