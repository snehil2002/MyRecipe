package com.example.myrecipe.screens


import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myrecipe.R
import com.example.myrecipe.viewmodel.recipeviewmodel.AllRecipeViewmodel
import com.example.myrecipe.viewmodel.recipeviewmodel.PopularViewmodel
import com.google.firebase.auth.FirebaseAuth

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun homescreen(navcontroller: NavController, popularViewmodel: PopularViewmodel, allRecipeViewmodel: AllRecipeViewmodel){

    var allrecipe=allRecipeViewmodel.allrecipe
    val username=FirebaseAuth.getInstance().currentUser?.displayName



    Scaffold(bottomBar = { bottomBar(ishome = true, onfavClick = {navcontroller.navigate(RecipeScreens.FAVOURITESCREEN.name)})}, backgroundColor = Color.White) {
        if (allRecipeViewmodel.isloading || popularViewmodel.isloading){
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth(), color = Color(0xFFE54900))
        }else
        {
           Column(modifier = Modifier.fillMaxSize()) {
               Row (modifier = Modifier
                   .fillMaxWidth()
                   .padding(start = 15.dp, top = 20.dp, bottom = 5.dp), verticalAlignment =Alignment.CenterVertically){
                   Image(painter = painterResource(id = R.drawable.hey1), contentDescription = "", modifier = Modifier.size(30.dp))
                   Text(text = "Hey ${username!!.split(" ")[0]}", fontSize = 22.sp, color = Color.Black, fontWeight = FontWeight.SemiBold)
                   
               }
               Text(
                   text = "Discover tasty and healthy recipe",
                   color = Color.Gray,
                   fontSize = 20.sp,
                   modifier =Modifier.padding(horizontal = 17.dp)
               )
               Surface(modifier= Modifier
                   .fillMaxWidth()
                   .height(76.dp)
                   .padding(horizontal = 17.dp, vertical = 15.dp)
                   .clickable { navcontroller.navigate(RecipeScreens.SEARCHSCREEN.name)}, shape = RoundedCornerShape(17.dp), color = Color(0xFFF2F7FD)
               ) {
                   Row(modifier=Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically) {
                       Icon(imageVector = Icons.Default.Search, contentDescription = "", modifier = Modifier.padding(horizontal = 10.dp), tint = Color.DarkGray)
                       Text(text = "Search any recipe", color = Color.Gray, fontSize = 20.sp)
                       

                   }

               }
               Text(text = "Popular Recipes", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Black, modifier = Modifier.padding(start = 15.dp, top = 18.dp))

               LazyRow (modifier = Modifier.padding(start = 10.dp, top = 8.dp)){

                   items(allrecipe!!){

                       imagecard(title = it.title, imagelink =it.image , ready = "25 min"){
                           navcontroller.navigate(RecipeScreens.INFOSCREEN.name)

                       }


                   }

               }
               Text(text = "All Recipes", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Black, modifier = Modifier.padding(start = 15.dp, top = 20.dp, bottom =10.dp))

               LazyColumn {
                   items(allrecipe!!){
                       imagerow(title = it.title, ready = "25 min", imagelink =it.image ){
                           navcontroller.navigate(RecipeScreens.INFOSCREEN.name)
                       }
                   }
               }




           } 
        }
        


    }


}