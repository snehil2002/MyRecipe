package com.example.myrecipe.repository

import android.util.Log
import com.example.myrecipe.api.RecipeApi
import com.example.myrecipe.dataorexception.Resource
import com.example.myrecipe.recipedata.allrecipe.Result
import com.example.myrecipe.recipedata.autosearch.AutoSearchItem
import com.example.myrecipe.recipedata.popular.Recipe
import javax.inject.Inject

class RecipeRepo @Inject constructor(private val recipeApi: RecipeApi) {
    suspend fun getAllRecipe():Resource<List<Result>>{
        return try {
            Resource.Loading(true)
            val allrecipe=recipeApi.getAllRecipe().results
            if (allrecipe.isNotEmpty())
                Resource.Loading(false)
            Log.d("AAAA","BBBB")
            Resource.Success(data = allrecipe)



        }catch (e:Exception){
            Log.d("CCCC","DDDD")
            Resource.Error(message = e.message)

        }
    }

    suspend fun getpopular():Resource<List<Recipe>>{
        return try {
            Resource.Loading(true)
            val popular=recipeApi.getPopularRecipe().recipes
            if (popular.isNotEmpty())
                Resource.Loading(false)
            Resource.Success(data = popular)

        }catch (e:Exception){
            Resource.Error(message = e.message)
        }
    }

    suspend fun getSearchResults(query:String):Resource<List<AutoSearchItem>>{
        return try {
            Resource.Loading(true)
            val searchresults=recipeApi.getSearchResults(query = query).toList()
            if (searchresults.isNotEmpty())
                Resource.Loading(false)
            Resource.Success(data = searchresults)
        }catch (e:Exception){
            Resource.Error(message = e.message)
        }
    }
}