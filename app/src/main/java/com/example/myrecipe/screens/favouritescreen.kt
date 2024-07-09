package com.example.myrecipe.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable fun favouritescreen(navcontroller:NavController){
    Scaffold(bottomBar = { bottomBar(ishome = false, onhomeClick = {navcontroller.navigate(RecipeScreens.HOMESCREEN.name)})}) {
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Information and Favourite Screen to be continued \n" +
                        "There are no image links in Information api", modifier = Modifier.padding(10.dp), fontSize = 18.sp
            )

        }

    }
}