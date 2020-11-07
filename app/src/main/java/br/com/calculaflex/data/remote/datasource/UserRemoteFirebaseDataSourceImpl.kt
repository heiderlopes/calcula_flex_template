package br.com.calculaflex.data.remote.datasource

import br.com.calculaflex.domain.entity.NewUser
import br.com.calculaflex.domain.entity.RequestState
import br.com.calculaflex.domain.entity.User
import br.com.calculaflex.domain.entity.UserLogin
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.tasks.await

@ExperimentalCoroutinesApi
class UserRemoteFirebaseDataSourceImpl(
    private val firebaseAuth: FirebaseAuth
) : UserRemoteDataSource {

    override suspend fun getUserLogged(): RequestState<User> {
        firebaseAuth.currentUser?.reload()
        val firebaseUser = firebaseAuth.currentUser
        return if(firebaseUser == null) {
            RequestState.Error(Exception("Usuário deslogado"))
        } else {
            RequestState.Success(User(firebaseUser.displayName?: "Não identificado"))
        }
    }

    override suspend fun doLogin(userLogin: UserLogin): RequestState<User> {
        firebaseAuth.signInWithEmailAndPassword(userLogin.email, userLogin.password).await()
        val firebaseUser = firebaseAuth.currentUser
        return if(firebaseUser?.email != null) {
            RequestState.Success(User(firebaseUser.displayName?: "Não identificado"))
        } else {
            RequestState.Error(Exception("Usuario ou senha inválida"))
        }
    }

    override suspend fun resetPassword(email: String): RequestState<String> {
        return try{
            firebaseAuth.sendPasswordResetEmail(email).await()
            RequestState.Success("Verifique sua caixa de e-mail")
        } catch (e: java.lang.Exception) {
            RequestState.Error(e)
        }
    }

    override suspend fun create(newUser: NewUser): RequestState<User> {
        return try{
            firebaseAuth.createUserWithEmailAndPassword(newUser.email, newUser.password).await()
            RequestState.Success(User(newUser.name))
        } catch (e: java.lang.Exception) {
            RequestState.Error(e)
        }
    }
}
