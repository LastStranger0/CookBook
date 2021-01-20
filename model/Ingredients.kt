package com.mtg.cookbook.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Ingredients(
    var ID: String = "",
    var Name: String = "",
    var Calories: Int = 0,
    var Image: ByteArray? = null,
    var ImagePath: String = "",
    var Description: String = "",
): RealmObject() {

}