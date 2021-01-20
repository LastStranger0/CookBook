package com.mtg.cookbook.presenter

import com.google.firebase.database.FirebaseDatabase
import com.mtg.cookbook.model.Ingredients
import com.mtg.cookbook.model.Recipe
import io.realm.Realm
import io.realm.RealmResults
import io.realm.kotlin.where
import java.util.*

interface IngredientDataBase {
    fun SearchIngredientByName(name: String): RealmResults<Ingredients>{
        val realm = Realm.getDefaultInstance()
        return realm.where<Ingredients>().equalTo("Name", name).findAll()
    }

    fun SearchIngredientByID(id: String): Ingredients?{
        val realm = Realm.getDefaultInstance()
        return realm.where<Ingredients>().equalTo("ID", id).findFirst()
    }

    fun AddIngredient(name: String, calories: Int, image: ByteArray, imagePath: String, description: String): String{
        val realm = Realm.getDefaultInstance()
        val ingredients = Ingredients()
        val database = FirebaseDatabase.getInstance().getReference(Checking.IngredientGroup)
        val id = UUID.randomUUID().toString()
        ingredients.ID = id
        ingredients.Name = name
        ingredients.Calories = calories
        ingredients.Description = description
        ingredients.Image = image
        ingredients.ImagePath = imagePath
        realm.beginTransaction()
        realm.copyToRealm(ingredients)
        realm.commitTransaction()
        return id
    }
}