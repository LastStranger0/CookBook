package com.mtg.cookbook.presenter

import com.google.firebase.database.FirebaseDatabase
import com.mtg.cookbook.model.Category
import com.mtg.cookbook.model.Ingredients
import com.mtg.cookbook.model.Recipe
import com.mtg.cookbook.model.Step
import io.realm.Realm
import io.realm.RealmList
import io.realm.kotlin.where
import java.util.*

interface RecipeDataBase {
    fun searchRecipeByID(id: String): Recipe {
        val realm = Realm.getDefaultInstance()
        return realm.where<Recipe>().equalTo("id", id).findFirst() ?: Recipe()
    }

    fun addRecipe(name: String, mainImage: ByteArray, mainImagePath: String, category: String, definition: String, images: RealmList<ByteArray>,
                  steps: RealmList<Step>, calories: Int, imagePath: RealmList<String>, ingredients: RealmList<Ingredients>, weight: Int): String?{
        val realm = Realm.getDefaultInstance()
        var id = UUID.randomUUID().toString()
        var recipe = Recipe()

        recipe.id = id
        recipe.Name = name
        recipe.MainImage = mainImage
        recipe.Description = definition
        recipe.MainImagePath = mainImagePath
        recipe.Category = findCategoryByName(category)
        recipe.Calories = calories
        recipe.Images = images
        recipe.ImagePaths = imagePath
        recipe.Steps = steps
        recipe.Ingredients = ingredients
        recipe.weight = weight
        realm.beginTransaction()
        realm.copyToRealm(recipe)
        realm.commitTransaction()
        return id
    }
    fun findCategoryByName(name: String ): Category{
        val realm = Realm.getDefaultInstance()
        return realm.where<Category>().equalTo("category", name).findFirst() ?: Category()
    }
}