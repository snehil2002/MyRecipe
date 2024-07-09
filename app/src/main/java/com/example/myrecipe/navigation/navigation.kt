package com.example.myrecipe.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myrecipe.screens.RecipeScreens
import com.example.myrecipe.screens.favouritescreen
import com.example.myrecipe.screens.homescreen
import com.example.myrecipe.screens.infoscreen
import com.example.myrecipe.screens.searchscreen
import com.example.myrecipe.screens.signupscreen
import com.example.myrecipe.screens.splashscreen
import com.example.myrecipe.viewmodel.authviewmodel.AuthViemodel
import com.example.myrecipe.viewmodel.recipeviewmodel.AllRecipeViewmodel
import com.example.myrecipe.viewmodel.recipeviewmodel.PopularViewmodel
import com.example.myrecipe.viewmodel.recipeviewmodel.Searchviewmodel

@Composable
fun navigation(){
    val navcontroller= rememberNavController()
    val allRecipeViewmodel= hiltViewModel<AllRecipeViewmodel>()
    val popularViewmodel= hiltViewModel<PopularViewmodel>()
    val searchviewmodel= hiltViewModel<Searchviewmodel>()
    val authViemodel= hiltViewModel<AuthViemodel>()

    NavHost(navController =navcontroller , startDestination = RecipeScreens.SPLASHSCREEN.name){

        composable(route=RecipeScreens.SPLASHSCREEN.name){
            splashscreen(navcontroller = navcontroller, authViemodel = authViemodel)

        }
        composable(route=RecipeScreens.HOMESCREEN.name){
            homescreen(navcontroller = navcontroller,allRecipeViewmodel=allRecipeViewmodel, popularViewmodel = popularViewmodel)
        }
        composable(route=RecipeScreens.FAVOURITESCREEN.name){
            favouritescreen(navcontroller = navcontroller)

        }
        composable(route=RecipeScreens.SEARCHSCREEN.name){
            searchscreen(navcontroller = navcontroller, searchviewmodel = searchviewmodel)
        }
        composable(route=RecipeScreens.SIGNUPSCREEN.name){
            signupscreen(navcontroller = navcontroller, authViemodel = authViemodel)
        }
        composable(route=RecipeScreens.INFOSCREEN.name){
            infoscreen(navcontroller = navcontroller)

        }

    }
}