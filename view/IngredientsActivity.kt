package com.mtg.cookbook.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.mtg.cookbook.R

class IngredientsActivity : AppCompatActivity() {
    companion object {
        val RESULT_INGREDIENT = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ingredients)
    }

    fun IngredientStart(view: View) {
        val intent = Intent(this, ChooseIngredientActivity::class.java)
        startActivityForResult(intent,RESULT_INGREDIENT)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            RESULT_INGREDIENT -> {
                if (resultCode == RESULT_OK){

                }
            }
        }
    }
}