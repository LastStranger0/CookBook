package com.mtg.cookbook.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView
import com.mtg.cookbook.model.Ingredients
import com.mtg.cookbook.presenter.IngredientDataBase
import com.mtg.cookbook.presenter.IngredientsAdapter
import com.mtg.cookbook.R

class ChooseIngredientActivity : AppCompatActivity(), IngredientDataBase, IngredientsAdapter.OnIngredientListener {
    lateinit var searchIngredientText: TextInputEditText
    lateinit var searchIngredientBtn: FloatingActionButton
    lateinit var progressBar: ProgressBar
    lateinit var progressText: MaterialTextView
    lateinit var addBtn: FloatingActionButton
    lateinit var nothingText: MaterialTextView
    lateinit var ingredients: RecyclerView
    lateinit var ingredientsAdapter: IngredientsAdapter
    lateinit var ingredientList: MutableList<Ingredients>
    var pos = 999999
    val ADD_INGREDIENT_CODE = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_ingredient)
        initialize()

        var arguments = intent.extras
        if (arguments != null){
            pos = intent.getIntExtra("position", 999999)
        }

        searchIngredientBtn.setOnClickListener{
            nothingText.visibility = View.INVISIBLE
            LoadingView(true)
            ingredientList.clear()
            var results = SearchIngredientByName(searchIngredientText.text.toString())
            ingredientList.addAll(results)
            LoadingView(false)
            if (results.size == 0){
                nothingText.visibility = View.VISIBLE
            }
            for (i in 0 until ingredientList.size){
                Log.d("ChooseIngredient", ingredientList[i].Name)
                ingredientsAdapter.notifyItemInserted(i)
            }
        }
        addBtn.setOnClickListener{
            var addIngrIntent = Intent(this, AddIngredientActivity::class.java)
            startActivityForResult(addIngrIntent, ADD_INGREDIENT_CODE)
        }

    }

    fun initialize(){
        searchIngredientText = findViewById(R.id.searchIngredientText)
        searchIngredientBtn = findViewById(R.id.searchIngredientBtn)
        progressBar = findViewById(R.id.searchIngredientProgressBar)
        progressText = findViewById(R.id.textLoadingIngredient)
        addBtn = findViewById(R.id.addIngredientBtn)
        nothingText = findViewById(R.id.nothingFound)
        ingredients = findViewById(R.id.ingredientSearchList)
        ingredientList = mutableListOf()
        ingredientsAdapter = IngredientsAdapter(this, ingredientList, this)
        ingredients.adapter = ingredientsAdapter
    }

    fun LoadingView(viewable: Boolean){
        if (viewable){
            progressBar.visibility = View.VISIBLE
            progressText.visibility = View.VISIBLE
        }
        else {
            progressBar.visibility = View.INVISIBLE
            progressText.visibility = View.INVISIBLE
        }
    }

    override fun onIngredientClick(position: Int) {
        var intent = Intent(this, IngredientsActivity::class.java)
        intent.putExtra("id", ingredientList[position].ID)
        intent.putExtra("name", ingredientList[position].Name)
        if (pos!= 999999) {
            intent.putExtra("position", pos)
            Log.d("ChooseActivity", "$pos")
        }
        else{
            intent.putExtra("position", 0)
            Log.d("ChooseActivity", "position doesn't changed")
        }
        setResult(RESULT_OK, intent)
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode){
            ADD_INGREDIENT_CODE -> {
                if(resultCode == RESULT_OK){
                    var intent = Intent(this, IngredientsActivity::class.java)
                    if (data != null) {
                        Log.d("ChooseActivity", data.getStringExtra("id"))
                        intent.putExtra("id", data.getStringExtra("id"))
                        intent.putExtra("name", data.getStringExtra("name"))
                        intent.putExtra("position", pos)
                        setResult(RESULT_OK, intent)
                        finish()
                    }
                }
            }
        }
    }
}