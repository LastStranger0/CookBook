package com.mtg.cookbook.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mtg.cookbook.R
import com.mtg.cookbook.model.Category
import com.mtg.cookbook.model.Recipe
import com.mtg.cookbook.presenter.RecipeAdapter
import com.mtg.cookbook.presenter.RecipeDataBase
import io.realm.Realm
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_recipe.*


class Liked_fragment : Fragment(), RecipeAdapter.OnRecipeListener, RecipeDataBase {

    lateinit var likedList: RecyclerView
    lateinit var recipesList: MutableList<Recipe>
    lateinit var recipeAdapter: RecipeAdapter
    lateinit var mcontext: Context
    var weights = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mcontext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var realm = Realm.getDefaultInstance()
        var view = inflater.inflate(R.layout.fragment_liked, container, false)
        likedList = view.findViewById(R.id.likedList)
        recipesList = mutableListOf()
        recipesList.addAll(realm.where<Recipe>().findAll())
        recipeAdapter = RecipeAdapter(mcontext, recipesList, this)
        likedList.adapter = recipeAdapter
        return view
    }

    companion object {
        fun newInstance():Liked_fragment{
            return Liked_fragment()
        }
    }

    override fun onRecipeClick(position: Int) {
        Log.d("Liked", "click!!!!")
        var realm = Realm.getDefaultInstance()
        var recipeList: MutableList<Recipe> = realm.where<Recipe>().findAll()
        Log.d("Weight", recipeList[position].Name)
        var recipe = searchRecipeByID(recipeList[position].id)
        var category = recipe.Category?.let { findCategoryByName(it.category) }
        if (category != null) {
            Log.d("Weight", "${category.weight}")
        }
        weights += recipe.weight
        var tempResult = recipesList
        Log.d("Weight", "$weights")
        var recipelistSize = recipeList.size
        if (weights > 20){
            var i = 0
            while (i<recipesList.size){
                if (recipesList[i].weight < -5){
                    recipesList.removeAt(i)
                    recipeAdapter.notifyItemRemoved(i)
                    recipelistSize--
                }
                i++
            }

            for (i in 0 until recipeList.size){
                if(recipeList[i].weight > -5){
                    var search = false
                    for (j in 0 until recipesList.size){
                        if (recipesList[j] == recipeList[i]){
                            search = true
                        }
                    }
                    if (!search){
                        recipesList.add(recipesList.size, recipeList[i])
                        recipelistSize++
                        recipeAdapter.notifyItemInserted(recipesList.size-1)
                    }
                }
            }
        }
        else if (weights > 30){
            var i = 0
            while (i<recipesList.size){
                if (recipesList[i].weight < -2){
                    recipesList.removeAt(i)
                    recipeAdapter.notifyItemRemoved(i)
                    recipelistSize--
                }
                i++
            }

            for (i in 0 until recipeList.size){
                if(recipeList[i].weight > -2){
                    var search = false
                    for (j in 0 until recipesList.size){
                        if (recipesList[j] == recipeList[i]){
                            search = true
                        }
                    }
                    if (!search){
                        recipesList.add(recipeList[i])
                        recipelistSize++
                        recipeAdapter.notifyItemInserted(recipesList.size-1)
                    }
                }
            }
        }
        else if (weights > 50){
            var i = 0
            while (i<recipesList.size){
                if (recipesList[i].weight < 0){
                    recipesList.removeAt(i)
                    recipeAdapter.notifyItemRemoved(i)
                    recipelistSize--
                }
                i++
            }

            for (i in 0 until recipeList.size){
                if(recipeList[i].weight > 0){
                    var search = false
                    for (j in 0 until recipesList.size){
                        if (recipesList[j] == recipeList[i]){
                            search = true
                        }
                    }
                    if (!search){
                        recipesList.add(recipeList[i])
                        recipelistSize++
                        recipeAdapter.notifyItemInserted(recipesList.size-1)
                    }
                }
            }
        }
        else if (weights < -20){
            var i = 0
            while (i<recipesList.size){
                if (recipesList[i].weight > 5){
                    recipesList.removeAt(i)
                    recipeAdapter.notifyItemRemoved(i)
                    recipelistSize--
                }
                i++
            }

            for (i in 0 until recipeList.size){
                if(recipeList[i].weight < 5){
                    var search = false
                    for (j in 0 until recipesList.size){
                        if (recipesList[j] == recipeList[i]){
                            search = true
                        }
                    }
                    if (!search){
                        recipesList.add(recipeList[i])
                        recipelistSize++
                        recipeAdapter.notifyItemInserted(recipesList.size-1)
                    }
                }
            }
        }
        else if (weights < -30){
            var i = 0
            while (i<recipesList.size){
                if (recipesList[i].weight > 2){
                    recipesList.removeAt(i)
                    recipeAdapter.notifyItemRemoved(i)
                    recipelistSize--
                }
                i++
            }

            for (i in 0 until recipeList.size){
                if(recipeList[i].weight < 2){
                    var search = false
                    for (j in 0 until recipesList.size){
                        if (recipesList[j] == recipeList[i]){
                            search = true
                        }
                    }
                    if (!search){
                        recipesList.add(recipeList[i])
                        recipelistSize++
                        recipeAdapter.notifyItemInserted(recipesList.size-1)
                    }
                }
            }
        }
        else if (weights < -50){
            var i = 0
            while (i<recipesList.size){
                if (recipesList[i].weight > 0){
                    recipesList.removeAt(i)
                    recipeAdapter.notifyItemRemoved(i)
                    recipelistSize--
                }
                i++
            }
            for (i in 0 until recipeList.size){
                if(recipeList[i].weight < 0){
                    var search = false
                    for (j in 0 until recipesList.size){
                        if (recipesList[j] == recipeList[i]){
                            search = true
                        }
                    }
                    if (!search){
                        recipesList.add(recipeList[i])
                        recipelistSize++
                        recipeAdapter.notifyItemInserted(recipesList.size-1)
                    }
                }
            }
        }
    }
}