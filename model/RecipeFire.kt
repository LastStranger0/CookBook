package com.mtg.cookbook.model

import io.realm.RealmList

class RecipeFire(var id: String,
                 var Name: String,
                 var MainImagePath: String,
                 var Description: String,
                 var Category: String,
                 var Steps: List<Step>,
                 var Calories: Int,
                 var ImagePaths: List<String>,
                 var Ingredients: List<IngredientsFire>) {
}