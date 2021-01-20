package com.mtg.cookbook.model

import android.net.Uri
import io.realm.RealmObject

open class Step(var text: String = ""): RealmObject() {
}