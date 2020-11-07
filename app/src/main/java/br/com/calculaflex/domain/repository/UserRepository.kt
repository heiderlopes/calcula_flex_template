package br.com.calculaflex.domain.repository

import br.com.calculaflex.domain.entity.RequestState
import br.com.calculaflex.domain.entity.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun getUserLogged(): Flow<RequestState<User>>

}
