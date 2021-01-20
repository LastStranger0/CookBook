package com.mtg.cookbook.model

import io.realm.RealmObject

open class Category(var category: String = "",
                    var weight: Int = 0): RealmObject() {
}