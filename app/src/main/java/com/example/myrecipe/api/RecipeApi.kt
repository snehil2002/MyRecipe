package com.example.myrecipe.api

import com.example.myrecipe.recipedata.allrecipe.AllRecipe
import com.example.myrecipe.recipedata.autosearch.AutoSearch
import com.example.myrecipe.recipedata.popular.Popular
import com.example.myrecipe.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RecipeApi {

@GET("random")
suspend fun getPopularRecipe(@Query("apiKey") apiKey:String=Constants.apikey):Popular

@GET("complexSearch")
suspend fun getAllRecipe(@Query("apiKey") apiKey: String=Constants.apikey):AllRecipe

@GET("autocomplete") suspend fun getSearchResults(
    @Query("apiKey") apiKey:String=Constants.apikey,
    @Query("number") number:Int=5,
    @Query("query") query:String
):AutoSearch
}