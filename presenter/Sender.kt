package com.mtg.cookbook.presenter

import android.util.Log
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.mtg.cookbook.model.Ingredients
import com.mtg.cookbook.model.Recipe

interface Sender: Adapter {
    fun sendRecipe(recipe: Recipe){
        var recipeFire = returnRecipeFire(recipe)
        Log.d("Sender", recipeFire.id)
        Log.d("Sender", recipeFire.Name)
        Log.d("Sender", recipeFire.MainImagePath)
        val firebaseStorage = FirebaseStorage.getInstance()
        val database = FirebaseDatabase.getInstance().getReference(Checking.RecipeGroup)
        val mainImageRef = recipeFire.let { firebaseStorage.getReference(it.MainImagePath) }
        for (i in 0 until recipe.ImagePaths.size){
            val notMainImageRef = recipe.ImagePaths[i]?.let { firebaseStorage.getReference(it) }
            if (notMainImageRef != null) {
                recipe.Images[i]?.let { notMainImageRef.putBytes(it) }?.continueWithTask { task->
                    if (!task.isSuccessful){
                        task.exception?.let {
                            throw it
                        }
                    }
                    notMainImageRef.downloadUrl
                }?.addOnCompleteListener {
                    if (it.isSuccessful){
                        var imagePath = recipeFire.ImagePaths.toMutableList()
                        imagePath[i] = it.result.toString()
                        recipeFire.ImagePaths = imagePath.toList()
                        Log.d("Sender", it.result.toString())
                    }
                }
            }
        }
        recipe.MainImage?.let { mainImageRef.putBytes(it) }?.continueWithTask { task->
            if (!task.isSuccessful){
                task.exception?.let {
                    throw it
                }
            }
            mainImageRef.downloadUrl
        }?.addOnCompleteListener {
            if (it.isSuccessful){
                recipeFire.MainImagePath = it.result.toString()
                Log.d("Sender", it.result.toString())
                database.push().setValue(recipeFire)
            }
        }

    }

    fun sendIngredient(ingredients: Ingredients){
        val ingredientsFire = returnIngredientFire(ingredients)
        val firebaseStorage = FirebaseStorage.getInstance()
        val database = FirebaseDatabase.getInstance().getReference(Checking.IngredientGroup)
        val mainImageRef = firebaseStorage.getReference(ingredients.ImagePath)
        ingredients.Image?.let { mainImageRef.putBytes(it) }?.continueWithTask {task->
            if (!task.isSuccessful){
                task.exception?.let {
                    throw it
                }
            }
            mainImageRef.downloadUrl
        }?.addOnCompleteListener {
            if (it.isSuccessful){
                ingredientsFire.ImagePath = it.result.toString()
                Log.d("Sender", it.result.toString())
                database.push().setValue(ingredientsFire).addOnSuccessListener {
                    Log.d("Sender", "sended")
                }
            }
        }

    }

}