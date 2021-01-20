package com.mtg.cookbook.presenter

import android.util.Log
import com.mtg.cookbook.model.*

interface Adapter {
    fun returnIngredientFire(ingredient: Ingredients): IngredientsFire{
        return IngredientsFire(ingredient.ID, ingredient.Name,
        ingredient.Calories, ingredient.ImagePath, ingredient.Description)
    }

    fun returnRecipeFire(recipe: Recipe): RecipeFire{
        var steps = mutableListOf<Step>()
        steps.addAll(recipe.Steps)
        var step = steps.toList()
        var imagePaths = mutableListOf<String>()
        imagePaths.addAll(recipe.ImagePaths)
        var imagePath = imagePaths.toList()
        var ingredients = mutableListOf<IngredientsFire>()
        for (i in 0 until recipe.Ingredients.size){
            ingredients.add(returnIngredientFire(recipe.Ingredients[i]!!))
        }
        var ingredient = ingredients.toList()
        var category = recipe.Category ?: Category()
        var recipeFire = RecipeFire(recipe.id, recipe.Name, recipe.MainImagePath, recipe.Description, category.category,
        step, recipe.Calories, imagePath, ingredient)
        Log.d("Adapter", recipeFire.id)
        Log.d("Adapter", recipeFire.Name)
        Log.d("Adapter", recipeFire.MainImagePath)
        Log.d("Adapter", recipeFire.Description)
        Log.d("Adapter", recipeFire.Category)
        Log.d("Adapter", recipeFire.Calories.toString())
        return recipeFire
    }
}