package com.example.myrecipe.viewmodel.authviewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myrecipe.authdata.AuthState
import com.example.myrecipe.dataorexception.Resource
import com.example.myrecipe.repository.AuthRepo
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.identity.SignInCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViemodel @Inject constructor(private val authRepo: AuthRepo,
    val oneTapClient: SignInClient):ViewModel() {

    val currentuser=getAuthState()

    private val _googleSignInResponse = MutableStateFlow<Resource<AuthResult>>(Resource.Success(null))
    val googleSignInResponse: StateFlow<Resource<AuthResult>> = _googleSignInResponse

    private val _signOutResponse = MutableStateFlow<Resource<Boolean>>(Resource.Success(null))
    val signOutResponse: StateFlow<Resource<Boolean>> = _signOutResponse

    private val _cuser = MutableStateFlow<FirebaseUser?>(null)
    val cuser: StateFlow<FirebaseUser?> = _cuser

    private val _isAuthenticated = MutableStateFlow(false)
    val isAuthenticated: StateFlow<Boolean> = _isAuthenticated

    private val _isAnonymous = MutableStateFlow(false)
    val isAnonymous: StateFlow<Boolean> = _isAnonymous

    private val _authStateEnum = MutableStateFlow<AuthState>(AuthState.SignedOut)
    val authStateEnum: StateFlow<AuthState> = _authStateEnum

    private val _oneTapSignInResponse = MutableStateFlow<Resource<BeginSignInResult>>(Resource.Success(null))
    val oneTapSignInResponse: StateFlow<Resource<BeginSignInResult>> = _oneTapSignInResponse




    init {
        getAuthState()
    }

    private fun getAuthState()=authRepo.getAuthState(viewModelScope)

    fun updateAuthState(user: FirebaseUser?) {
        _cuser.value = user
        _isAuthenticated.value = user != null
        _isAnonymous.value = user?.isAnonymous ?: false
        _authStateEnum.value = when {
            _isAuthenticated.value -> {
                if (_isAnonymous.value) AuthState.Authenticated else AuthState.SignedIn
            }
            else -> AuthState.SignedOut
        }
    }

    fun oneTapSignIn() = viewModelScope.launch {
        _oneTapSignInResponse.value = Resource.Loading()
        _oneTapSignInResponse.value = authRepo.oneTapSignin()
    }

    fun signInWithGoogle(credential: SignInCredential) = viewModelScope.launch {
        _googleSignInResponse.value = Resource.Loading()
        _googleSignInResponse.value = authRepo.signinWithGoogle(credential)
        updateAuthState(_googleSignInResponse.value.data?.user)
    }

    fun signOut() = viewModelScope.launch {
        _signOutResponse.value = Resource.Loading()
        _signOutResponse.value = authRepo.signout()
        if (_signOutResponse.value is Resource.Success) {
            updateAuthState(null)
        }
    }
    fun clearSignInResponse() {
        _googleSignInResponse.value = Resource.Success(null)
        _oneTapSignInResponse.value=Resource.Success(null)
    }
}
