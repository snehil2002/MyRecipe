package com.example.myrecipe.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.myrecipe.R

@Composable fun imagecard(title:String, ready:String, imagelink:String,onclick: () -> Unit){
    Surface(modifier = Modifier
        .height(185.dp)
        .width(185.dp)
        .padding(7.dp).clickable { onclick() }, shape = RoundedCornerShape(20.dp), color = Color.Transparent
    ) {
        Image(painter = rememberImagePainter(data = imagelink),
            contentDescription ="",
            modifier = Modifier
                .fillMaxSize()
                .clickable { },
            contentScale = ContentScale.Crop )
        
        Box(modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color.Transparent,
                        Color.Black.copy(alpha = 0.9f)
                    ), startY = 200f, endY = Float.POSITIVE_INFINITY
                )
            ).clickable { onclick() })
        Column(modifier= Modifier
            .fillMaxSize()
            .padding(15.dp), verticalArrangement =Arrangement.Bottom) {
            Text(
                text = title,
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "Ready in $ready",
                color = Color.LightGray,
                fontSize = 15.sp,modifier = Modifier.padding(top = 5.dp)

            )

        }


    }

}

@Composable fun bottomBar(ishome:Boolean,onhomeClick:()->Unit={},onfavClick:()->Unit={}){
    Surface(color = Color.White,modifier = Modifier
        .fillMaxWidth()
        .height(60.dp)
        .drawBehind {
            drawLine(
                color = Color(0xFFE7F0F8),
                start = Offset(0f, 0f),
                end = Offset(x = size.width, y = 0f),
                strokeWidth = 4.dp.toPx()
            )
        }) {
        Row (modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween){
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
                .padding(start = 70.dp)
                .clickable { onhomeClick() }) {
                Icon(painter = painterResource(id = if (ishome)R.drawable.homefill else R.drawable.homeborder), contentDescription = "",modifier =Modifier.size(30.dp), tint = if (ishome)Color(0xFFE54900) else Color.Gray)
                Text(text = "Home", fontSize = 15.sp, modifier = Modifier.padding(top = 3.dp), color = if (ishome)Color(0xFFE54900) else Color.Gray)
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
                .padding(end = 60.dp)
                .clickable { onfavClick() }) {
                Icon(imageVector = if (!ishome)Icons.Default.Favorite else Icons.Default.FavoriteBorder, contentDescription = "", modifier =Modifier.size(30.dp), tint = if (!ishome)Color(0xFFE54900) else Color.Gray)
                Text(text = "Favourite", fontSize = 15.sp, modifier = Modifier.padding(top=3.dp), color = if (!ishome)Color(0xFFE54900) else Color.Gray)
            }

            
        }

    }}

@Composable fun imagerow(title: String , ready:String,imagelink: String,onclick: () -> Unit){
        Surface(modifier = Modifier
            .fillMaxWidth()
            .height(125.dp)
            .padding(horizontal = 15.dp, vertical = 5.dp).clickable { onclick() }, color = Color.White, shape = RoundedCornerShape(20.dp), border = BorderStroke(width = 1.dp, color = Color(0xFFE7F0F8))
        ) {
            Row(modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically) {
                Image(painter = rememberImagePainter(data = imagelink), contentDescription ="", modifier = Modifier
                    .fillMaxHeight()
                    .clip(
                        RoundedCornerShape(topStart = 20.dp, bottomStart = 20.dp)
                    )
                    .width(120.dp), contentScale = ContentScale.FillHeight )
                Column(modifier = Modifier.padding(start = 15.dp, end = 5.dp)) {
                    Text(
                        text = title,
                        color = Color.Black,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 17.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = "Ready in $ready",
                        color = Color.Gray,
                        fontSize = 15.sp, modifier = Modifier.padding(top = 5.dp)
                        )

                }



            }

        }

    }

@Composable fun bottomsheet(title:String,onback:()->Unit={}){
    Surface(modifier = Modifier
        .fillMaxWidth()
        .height(670.dp)
    ) {


        Column(modifier = Modifier.fillMaxSize()) {
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "", modifier = Modifier
                    .padding(15.dp)
                    .clickable { onback() })
                Text(text = title, fontSize = 23.sp, fontWeight = FontWeight.SemiBold)
                
                
            }
            
            Spacer(modifier = Modifier.height(200.dp))
            Text(
                text = "Information and Favourite Screen to be continued \n" +
                        "There are no image links in Information api", modifier = Modifier.padding(10.dp), fontSize = 18.sp
            )

        }
    }
}

@Composable fun signinButton(title: String,onclick:()->Unit){
    Surface(modifier = Modifier
        .fillMaxWidth()
        .height(100.dp)
        .padding(20.dp).clickable { onclick() }, color = Color(0xFFE54900), shape = RoundedCornerShape(10.dp)
    ) {
        Row(modifier = Modifier.fillMaxSize(), horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically) {
            Icon(painter = painterResource(id = R.drawable.icongoogle), contentDescription ="", tint = Color.White, modifier = Modifier.size(25.dp) )
            Spacer(modifier = Modifier.width(20.dp))
            Text(text = title, fontWeight = FontWeight.SemiBold, fontSize = 20.sp,color = Color.White)


        }

    }


}
