package br.com.calculaflex.domain.usecases

import br.com.calculaflex.data.remote.datasource.UserRemoteDataSource
import br.com.calculaflex.domain.entity.RequestState
import br.com.calculaflex.domain.entity.User
import kotlinx.coroutines.flow.Flow

class GetUserLoggedUseCase(
    private val userRemoteDataSource: UserRemoteDataSource
) {

    suspend fun getUserLogged(): Flow<RequestState<User>> = userRemoteDataSource.getUserLogged()

}