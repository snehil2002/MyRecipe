package com.example.myrecipe.viewmodel.recipeviewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myrecipe.dataorexception.Resource
import com.example.myrecipe.recipedata.allrecipe.Result
import com.example.myrecipe.recipedata.popular.Recipe
import com.example.myrecipe.repository.RecipeRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PopularViewmodel @Inject constructor(private val repo: RecipeRepo): ViewModel() {
    var isloading by mutableStateOf(true)
    var popular:List<Recipe>? by mutableStateOf(listOf())

    init {
        getPopularRecipe()
    }

    fun getPopularRecipe(){
        viewModelScope.launch(Dispatchers.Default) {
            when(val response=repo.getpopular()){
                is Resource.Success->{
                    popular=response.data!!
                    if (!popular.isNullOrEmpty())
                        isloading=false
                }
                is Resource.Error->{
                    isloading=false
                    Log.d("2EEEE","2UUUU")
                }
                else->{
                    isloading=false
                    Log.d("2SSSS","2TTTT")
                }
            }
        }

    }

}