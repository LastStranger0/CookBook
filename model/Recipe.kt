package com.mtg.cookbook.model

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey


open class Recipe(var id: String = "",
                  var Name: String = "",
                  var MainImage: ByteArray? = null,
                  var MainImagePath: String = "",
                  var Description: String = "",
                  var Category: Category? = null,
                  var Images: RealmList<ByteArray> = RealmList(),
                  var Steps: RealmList<Step> = RealmList(),
                  var Calories: Int = 0,
                  var ImagePaths: RealmList<String> = RealmList(),
                  var Ingredients: RealmList<Ingredients> = RealmList(),
                  var weight: Int = 0
): RealmObject() {

}