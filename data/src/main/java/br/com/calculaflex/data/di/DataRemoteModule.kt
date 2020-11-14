package br.com.calculaflex.data.di

import br.com.calculaflex.data.remote.datasource.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.dsl.module

@ExperimentalCoroutinesApi
val remoteDataSourceModule = module {


    factory<AppRemoteDataSource> {
        AppRemoteFirebaseDataSourceImpl()
    }

    factory<CarRemoteDataSource> {
        CarRemoteDataSourceImpl(
            firebaseFirestore = get()
        )
    }

    single { Firebase.firestore }

    single { FirebaseAuth.getInstance() }

    factory<UserRemoteDataSource> {
        UserRemoteFirebaseDataSourceImpl(
            firebaseAuth = get(),
            firebaseFirestore = get()
        )
    }
}