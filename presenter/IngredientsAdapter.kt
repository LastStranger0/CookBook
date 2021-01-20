package com.mtg.cookbook.presenter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import com.mtg.cookbook.model.Ingredients
import com.mtg.cookbook.R

class IngredientsAdapter(context: Context, var ingredient: MutableList<Ingredients>,
                         private var onIngredientListener: OnIngredientListener
): RecyclerView.Adapter<IngredientsAdapter.ViewHolder>() {
    class ViewHolder(view: View, var onIngredientListener: OnIngredientListener): RecyclerView.ViewHolder(view), View.OnClickListener{
        var Image: ImageView = view.findViewById(R.id.recipeImage)
        var Name: TextView = view.findViewById(R.id.recipeName)

        init {
            view.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            onIngredientListener.onIngredientClick(adapterPosition)
        }
    }

    private val layoutInflater:LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = layoutInflater.inflate(R.layout.activity_recipe, parent, false)
        return ViewHolder(view, onIngredientListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var ingredients = ingredient[position]
        holder.Image.setImageBitmap(ingredients.Image?.let { BitmapFactory.decodeByteArray(ingredients.Image, 0, it.size) })
        holder.Name.text = ingredients.Name
    }

    override fun getItemCount(): Int {
        return ingredient.size
    }

    interface OnIngredientListener{
        fun onIngredientClick(position: Int)
    }
}