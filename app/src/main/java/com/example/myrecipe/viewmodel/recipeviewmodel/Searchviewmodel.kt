package com.example.myrecipe.viewmodel.recipeviewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myrecipe.dataorexception.Resource
import com.example.myrecipe.recipedata.autosearch.AutoSearchItem
import com.example.myrecipe.repository.RecipeRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class Searchviewmodel @Inject constructor(private val repo: RecipeRepo):ViewModel() {
 var isloading by mutableStateOf(true)
    var searchresults:List<AutoSearchItem> by mutableStateOf(listOf())

    init {
        getSearchResults("")
    }

    fun getSearchResults(query :String){
        viewModelScope.launch(Dispatchers.Default) {
            when(val results=repo.getSearchResults(query = query)){
                is Resource.Success->{
                    searchresults=results.data!!
                        isloading=false
                }
                is Resource.Error->{
                    isloading=false
                }
                else->{
                    isloading=false
                }
            }

        }

    }
}