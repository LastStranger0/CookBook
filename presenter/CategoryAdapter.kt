package com.mtg.cookbook.presenter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import com.mtg.cookbook.R
import com.mtg.cookbook.model.Category
import kotlinx.android.synthetic.main.activity_category.view.*

class CategoryAdapter(context: Context, category: MutableList<Category>,onCategoryListenr: OnCategoryListener): RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {
    class ViewHolder(view: View, onCategoryListenr: OnCategoryListener): RecyclerView.ViewHolder(view), View.OnClickListener{
        var categoryName = view.findViewById<MaterialTextView>(R.id.categoryName)
        private var onCategoryListener = onCategoryListenr
        init {
            view.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            onCategoryListener.onCategoryClick(adapterPosition)
        }
    }

    private var layoutInflater: LayoutInflater = LayoutInflater.from(context)
    private var categoryList = category
    private var onCategoryListener = onCategoryListenr

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = layoutInflater.inflate(R.layout.activity_category, parent, false)
        return ViewHolder(view, onCategoryListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var categor = categoryList[position]
        holder.categoryName.text = categor.category

    }

    override fun getItemCount(): Int {
        return categoryList.size
    }
    interface OnCategoryListener{
        fun onCategoryClick(position: Int)
    }
}