package com.mtg.cookbook.presenter

import android.app.Instrumentation
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.mtg.cookbook.R
import com.mtg.cookbook.model.Ingredients
import com.mtg.cookbook.view.ChooseIngredientActivity

class IngredientAdapter(var context: Context, ingredient: MutableList<Ingredients>, var activity: AppCompatActivity): RecyclerView.Adapter<IngredientAdapter.ViewHolder>() {
    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        var numOfIngredients: TextInputEditText = view.findViewById(R.id.numText)
        var ingredientName: TextView = view.findViewById(R.id.ingredientBtn)
        var spinner: Spinner = view.findViewById(R.id.typeNum)

    }

    companion object{
        const val INGREDIENT_CALL = 123
    }
    private var layoutInflater: LayoutInflater = LayoutInflater.from(context)
    private var ingredients: MutableList<Ingredients> = ingredient
    private var typesOfNum: List<String> = arrayListOf("KG", "G", "ML", "L", "Items")
    var spinnerAdapter: ArrayAdapter<String>
    init {
        spinnerAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, typesOfNum)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = layoutInflater.inflate(R.layout.activity_ingredients, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var _ingredient = ingredients.get(position)
        holder.spinner.adapter = spinnerAdapter
        holder.ingredientName.text = _ingredient.Name
        holder.ingredientName.setOnClickListener {
            var intent = Intent(context, ChooseIngredientActivity::class.java)
            intent.putExtra("position", position)
            activity.startActivityForResult(intent, INGREDIENT_CALL)
        }
    }

    override fun getItemCount(): Int {
        return ingredients.size
    }

}