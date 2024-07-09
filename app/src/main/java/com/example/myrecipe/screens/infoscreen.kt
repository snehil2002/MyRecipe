package com.example.myrecipe.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable fun infoscreen(navcontroller: NavController){

        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "", modifier = Modifier
            .padding(15.dp)
            .clickable { navcontroller.popBackStack() })
        Column(
            modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Information and Favourite Screen to be continued \n" +
                        "There are no image links in Information api",
                modifier = Modifier.padding(10.dp),
                fontSize = 18.sp
            )

        }

}