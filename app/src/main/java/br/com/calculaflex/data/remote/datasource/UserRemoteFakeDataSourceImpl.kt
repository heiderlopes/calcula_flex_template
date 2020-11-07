package br.com.calculaflex.data.remote.datasource

import br.com.calculaflex.domain.entity.RequestState
import br.com.calculaflex.domain.entity.User
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

@ExperimentalCoroutinesApi
class UserRemoteFakeDataSourceImpl : UserRemoteDataSource {

    //Usamos callbackflow para produzirmos em um buffer os elementos que serão consumidos
    override suspend fun getUserLogged(): Flow<RequestState<User>> = callbackFlow {

        delay(2000)

        //Emite um resultado de sucesso contendo um usuario
        //offer(RequestState.Success(User("Heider")))

        offer(RequestState.Error(Exception("Sessão expirada")))

        //Se a UI que esta consumindo o canal não estiver mais ativa (a viewmodel passou pelo
        //onCleared e destruiu o collect do flow) encerra para não gerar leaks.
        awaitClose()

    }


}