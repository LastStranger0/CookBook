package com.mtg.cookbook.presenter

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mtg.cookbook.model.Recipe
import com.mtg.cookbook.R
import com.mtg.cookbook.model.Category

class RecipeAdapter(context: Context, recipe: List<Recipe>, var onRecipeListener: OnRecipeListener): RecyclerView.Adapter<RecipeAdapter.ViewHolder>() {

    private var layoutInflater: LayoutInflater = LayoutInflater.from(context)
    private var recipes: List<Recipe> = recipe

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = layoutInflater.inflate(R.layout.activity_recipe, parent, false)
        return ViewHolder(view, onRecipeListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recipe = recipes[position]
        var category = recipe.Category ?: Category()
        holder.recipeName.text = recipe.Name
        holder.recipeAbout.text = recipe.Description
        holder.recipeCategory.text = category.category
        holder.recipeImage.setImageBitmap(recipe.MainImage?.let { BitmapFactory.decodeByteArray(recipe.MainImage, 0, it.size) })
    }

    override fun getItemCount(): Int {
        return recipes.size
    }

    class ViewHolder(view: View, val onRecipeListener: OnRecipeListener): RecyclerView.ViewHolder(view), View.OnClickListener{
        var recipeName: TextView = view.findViewById(R.id.recipeName)
        var recipeImage: ImageView = view.findViewById(R.id.recipeImage)
        var recipeAbout: TextView = view.findViewById(R.id.recipeAbout)
        var recipeCategory: TextView = view.findViewById(R.id.recipeCategory)
        init {
            view.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            onRecipeListener.onRecipeClick(adapterPosition)
        }

    }

    interface OnRecipeListener{
        fun onRecipeClick(position: Int)
    }
}