package com.example.myrecipe.recipedata.allrecipe

data class AllRecipe(
    val number: Int,
    val offset: Int,
    val results: List<Result>,
    val totalResults: Int
)