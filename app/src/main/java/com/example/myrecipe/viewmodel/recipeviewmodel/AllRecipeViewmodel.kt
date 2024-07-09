package com.example.myrecipe.viewmodel.recipeviewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myrecipe.dataorexception.Resource
import com.example.myrecipe.recipedata.allrecipe.Result
import com.example.myrecipe.repository.RecipeRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllRecipeViewmodel @Inject constructor(private val repo: RecipeRepo):ViewModel() {
    var isloading by mutableStateOf(true)
    var allrecipe:List<Result>? by mutableStateOf(listOf())

    init {
        getAllRecipe()
    }

    fun getAllRecipe(){
        viewModelScope.launch(Dispatchers.Default) {
            when(val response=repo.getAllRecipe()){
                is Resource.Success->{
                    allrecipe= response.data!!
                    Log.d("FFFF","YYYY")
                    if (!allrecipe.isNullOrEmpty())
                        isloading=false
                }
                is Resource.Error->{
                    isloading=false
                    Log.d("EEEE","UUUU")
                }
                else->{
                    isloading=false
                    Log.d("SSSS","TTTT")
                }
        }
        }

    }

}