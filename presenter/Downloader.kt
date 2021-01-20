package com.mtg.cookbook.presenter

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query

interface Downloader {
    fun searchRecipe(name: String){
        var database = FirebaseDatabase.getInstance().getReference(Checking.RecipeGroup)
        var query = database.orderByChild("name")
    }
}