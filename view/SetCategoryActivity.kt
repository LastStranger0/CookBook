package com.mtg.cookbook.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import com.mtg.cookbook.R
import com.mtg.cookbook.model.Category
import com.mtg.cookbook.presenter.CategoryAdapter
import io.realm.Realm
import io.realm.kotlin.where

class SetCategoryActivity : AppCompatActivity(), CategoryAdapter.OnCategoryListener {

    lateinit var categoryView: RecyclerView
    lateinit var categories: MutableList<Category>
    lateinit var categoryAdapter: CategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_category)
        var realm = Realm.getDefaultInstance()
        categoryView = findViewById(R.id.categoryList)
        categories = mutableListOf()
        categories = realm.where<Category>().findAll()
        categoryAdapter = CategoryAdapter(this, categories, this)
        categoryView.adapter = categoryAdapter
    }

    override fun onCategoryClick(position: Int) {
        Log.d("Category", "click!")
        var intent = Intent(this, AddActivity::class.java)
        intent.putExtra("category", categories[position].category)
        setResult(RESULT_OK, intent)
        finish()
    }
}