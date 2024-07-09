package com.example.myrecipe.recipedata.recipeinfo

data class AnalyzedInstruction(
    val name: String,
    val steps: List<Step>
)