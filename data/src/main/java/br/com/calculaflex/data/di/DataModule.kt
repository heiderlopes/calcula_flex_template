package br.com.calculaflex.data.di

import br.com.calculaflex.data.repository.AppRepositoryImpl
import br.com.calculaflex.data.repository.CarRepositoryImpl
import br.com.calculaflex.data.repository.UserRepositoryImpl
import br.com.calculaflex.domain.repository.AppRepository
import br.com.calculaflex.domain.repository.CarRepository
import br.com.calculaflex.domain.repository.UserRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.dsl.module

@ExperimentalCoroutinesApi
private val repositoryModule = module {

    factory<AppRepository> {
        AppRepositoryImpl(
            appRemoteDataSource = get()
        )
    }

    factory<CarRepository> {
        CarRepositoryImpl(
            carRemoteDataSource = get()
        )
    }

    factory<UserRepository> {
        UserRepositoryImpl(
            userRemoteDataSource = get()
        )
    }
}

@ExperimentalCoroutinesApi
val dataModules = listOf(remoteDataSourceModule, repositoryModule)