package com.example.myrecipe.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.example.myrecipe.R
import com.example.myrecipe.viewmodel.authviewmodel.AuthViemodel
import kotlinx.coroutines.delay


@Composable fun splashscreen(navcontroller: NavController,authViemodel: AuthViemodel) {
    val currentuser=authViemodel.currentuser.collectAsState().value
    authViemodel.updateAuthState(currentuser)
    LaunchedEffect(key1 = true) {
        delay(1000L)

        if (authViemodel.isAuthenticated.value)
            navcontroller.navigate(RecipeScreens.HOMESCREEN.name)
        else
            navcontroller.navigate(RecipeScreens.SIGNUPSCREEN.name)

    }






    Image(
        painter = painterResource(id = R.drawable.new_background),
        contentDescription = "",
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
    )
}



