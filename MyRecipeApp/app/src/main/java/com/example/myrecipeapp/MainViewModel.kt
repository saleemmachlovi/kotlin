package com.example.myrecipeapp

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.State
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

    private val _categories = mutableStateOf(RecipeState())
    val categoriesState: State<RecipeState> = _categories

    init {
        fetchCategories()
    }

    private fun fetchCategories() {
        viewModelScope.launch {
            try {
                val response = recipeService.getCategories()
                _categories.value = _categories.value.copy(
                    list = response.categories,
                    loading = false,
                    error = null
                )

            }catch (e : Exception){
                _categories.value = _categories.value.copy(
                    loading = false,
                    error = "Error fetching categories : ${e.message}"
                )
            }
        }
    }

    data class RecipeState(
        val loading: Boolean = true,
        val list: List<Category> = emptyList(),
        val error: String? = null
    )
}