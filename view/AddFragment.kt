package com.mtg.cookbook.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mtg.cookbook.model.Recipe
import com.mtg.cookbook.presenter.RecipeAdapter
import com.mtg.cookbook.R
import com.mtg.cookbook.model.Category
import com.mtg.cookbook.presenter.RecipeDataBase
import io.realm.Realm

class AddFragment : Fragment(), RecipeAdapter.OnRecipeListener, RecipeDataBase {

    private lateinit var addingBtn: FloatingActionButton
    private lateinit var added: RecyclerView
    private lateinit var mcontext: Context
    private lateinit var addedList: MutableList<Recipe>
    private lateinit var addedAdapter: RecipeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mcontext = context
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_add, container, false)
        addingBtn = view.findViewById(R.id.addingButton)

        addingBtn.setOnClickListener{
            var intent = Intent(activity, AddActivity::class.java)
            startActivityForResult(intent, REQUEST_ACCESS)
        }
        addedList = mutableListOf()
        added = view.findViewById(R.id.added)
        addedAdapter = RecipeAdapter(mcontext, addedList, this)
        added.adapter = addedAdapter
        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        var realm = Realm.getDefaultInstance()
        when(requestCode){
            REQUEST_ACCESS ->{
                Log.d(TAG, "сообщение пришло")
                if (data != null) {
                    addedList.add(searchRecipeByID(data.getStringExtra("id")))
                    var res = addedList[addedList.size-1].Category?: Category()
                    Log.d(TAG, "${res.category} + ${res.weight}")
                    addedAdapter.notifyItemInserted(addedList.size-1)
                }

            }
        }

    }


    companion object {
        fun newInstance() =
                AddFragment()
        val REQUEST_ACCESS = 1
        val TAG = "myLogs"
    }

    override fun onRecipeClick(position: Int) {

    }
}