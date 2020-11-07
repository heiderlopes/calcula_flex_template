package br.com.calculaflex.data.remote.datasource

import br.com.calculaflex.domain.entity.RequestState
import br.com.calculaflex.domain.entity.User
import kotlinx.coroutines.flow.Flow

interface UserRemoteDataSource {

    suspend fun getUserLogged(): Flow<RequestState<User>>

}