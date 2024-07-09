package com.example.myrecipe.api


import com.example.myrecipe.dataorexception.Resource
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.auth.api.identity.SignInCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow

interface AuthInterface {
    fun getAuthState(viewModelScope: CoroutineScope):StateFlow<FirebaseUser?>

    suspend fun signout():Resource<Boolean>

    suspend fun oneTapSignin():Resource<BeginSignInResult>
    suspend fun signinWithGoogle(credential:SignInCredential):Resource<AuthResult>

}