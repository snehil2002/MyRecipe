package com.example.myrecipe.repository

import android.util.Log
import com.example.myrecipe.api.AuthInterface
import com.example.myrecipe.dataorexception.Resource
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.identity.SignInCredential
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Named

class AuthRepo @Inject constructor(
    private val auth:FirebaseAuth,
    private var oneTapClient: SignInClient,
    @Named("signinRequest") private var signinRequest: BeginSignInRequest
):AuthInterface {
    override fun getAuthState(viewModelScope: CoroutineScope)= callbackFlow{
        val authStateListner = AuthStateListener {
            trySend(it.currentUser)
            }
        auth.addAuthStateListener(authStateListner)
        awaitClose{
            auth.removeAuthStateListener(authStateListner)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(),auth.currentUser)


    override suspend fun signout(): Resource<Boolean> {
        return try {
            auth.signOut()
            Resource.Success(true)

        }catch (e:Exception){
            Resource.Error(message = e.message)

        }
    }

    override suspend fun oneTapSignin(): Resource<BeginSignInResult> {
        Log.d("SignIn", "AuthRepo oneTapSignin called")
        return try {
            Log.d("SignIn", "oneTapSignin success")

            val signinResult=oneTapClient.beginSignIn(signinRequest).await()
            Resource.Success(signinResult)

        }catch (e:Exception){
            try {
                val signupResult=oneTapClient.beginSignIn(signinRequest).await()
                Resource.Success(signupResult)


            }catch (e:Exception){
            Log.d("SignIn", "oneTapSignin Error")
            Resource.Error(message = e.message)}

        }
    }

    override suspend fun signinWithGoogle(credential: SignInCredential): Resource<AuthResult> {

        val googleCredential=GoogleAuthProvider.getCredential(credential.googleIdToken,null)

        return authenticateUser(googleCredential)
    }


    private suspend fun authSignin(credential: AuthCredential):Resource<AuthResult>{
        return try {
            val authResult=auth.signInWithCredential(credential).await()
            Resource.Success(authResult)

        }catch (e:Exception){
            Resource.Error(message = e.message)
        }
    }
    private suspend fun authLink(credential: AuthCredential):Resource<AuthResult>{
        return try {
            val authResult=auth.currentUser?.linkWithCredential(credential)?.await()
            Resource.Success(authResult)

        }catch (e:Exception){
            Resource.Error(message = e.message)
        }
    }

    private suspend fun authenticateUser(credential: AuthCredential):Resource<AuthResult>{
        return if(auth.currentUser!=null)
            authLink(credential)
        else
            authSignin(credential)
    }
}