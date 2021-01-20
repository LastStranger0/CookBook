package com.mtg.cookbook.presenter

import com.mtg.cookbook.model.Ingredients
import java.io.IOException

class Checking {
    companion object {
        fun isOnline(): Boolean {
            val runtime = Runtime.getRuntime()
            try {
                val ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8")
                val exitValue = ipProcess.waitFor()
                return exitValue == 0
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            return false
        }
        const val RecipeGroup = "Recipe"
        const val IngredientGroup = "Ingredient"
    }
}