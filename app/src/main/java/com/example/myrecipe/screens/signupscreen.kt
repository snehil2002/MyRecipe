package com.example.myrecipe.screens

import android.app.Activity
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myrecipe.R
import com.example.myrecipe.authdata.AuthState
import com.example.myrecipe.dataorexception.Resource
import com.example.myrecipe.viewmodel.authviewmodel.AuthViemodel
import com.google.android.gms.auth.api.identity.BeginSignInResult

@Composable fun signupscreen(navcontroller:NavController,authViemodel: AuthViemodel){

    val launcher= rememberLauncherForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) {
        if (it.resultCode==Activity.RESULT_OK){
            try {
                val credentials=authViemodel.oneTapClient.getSignInCredentialFromIntent(it.data)
                authViemodel.signInWithGoogle(credentials)



            }catch (e:Exception){
                Log.d("XEXE",e.message.toString())
            }
        }
        else if (it.resultCode==Activity.RESULT_CANCELED){
            Log.d("CACA","CANCELLED")
        }
        
    }

    fun launch(signInResult: BeginSignInResult){
        val intent=IntentSenderRequest.Builder(signInResult.pendingIntent.intentSender).build()
        launcher.launch(intent)
    }

    var authstate=authViemodel.authStateEnum.collectAsState()
    var oneTapSigninResponse=authViemodel.oneTapSignInResponse.collectAsState()
    var signinWithGoogleResponse=authViemodel.googleSignInResponse.collectAsState()




    Surface(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.new_background),
            contentDescription = "",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Bottom) {
            Text(text = "Welcome to", fontSize = 50.sp, fontStyle = FontStyle.Italic, modifier = Modifier.padding(horizontal = 20.dp), color = Color.White)
            Text(text = "MyRecipe", fontSize = 50.sp, fontWeight = FontWeight.Bold, fontStyle = FontStyle.Italic, modifier = Modifier.padding(horizontal = 20.dp), color = Color.White)
            Text(text = "Please sign-in to continue", fontSize = 20.sp, fontWeight = FontWeight.Light, color = Color.White, modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp))

            signinButton(title = "Continue with Google") {
                Log.d("SignIn", "Button clicked")
                authViemodel.oneTapSignIn()

            }



        }

    }



    when(oneTapSigninResponse.value){
        is Resource.Loading ->{
            Log.d("WWWW","WWWW")
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth(), color = Color(0xFFE54900))
        }
        is Resource.Success -> oneTapSigninResponse.value.data?.let {
            LaunchedEffect(key1 = it) {
                launch(it)
            }
        }
        else ->{
            Log.d("LELE",oneTapSigninResponse.value.message.toString())
        }
    }

    when(signinWithGoogleResponse.value){
        is Resource.Loading->{
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth(), color = Color(0xFFE54900))
        }
        is Resource.Success -> signinWithGoogleResponse.value.data?.let {
            navcontroller.navigate(RecipeScreens.HOMESCREEN.name){
                popUpTo(RecipeScreens.SPLASHSCREEN.name){
                    inclusive=false
                }
            }
            authViemodel.clearSignInResponse()
            Log.d("SCSC","SUCCESS")
        }
        else->{
            Log.d("ERER","ERER")
        }
    }



}