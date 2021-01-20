package com.mtg.cookbook.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mtg.cookbook.R
import com.mtg.cookbook.model.Category
import io.realm.Realm
import io.realm.kotlin.where


class MainActivity : AppCompatActivity() {

    var likedFragment = Liked_fragment.newInstance()
    var caloriesFragment = CaloriesFragment.newInstance()
    var searchFragment = SearchFragment.newInstance()
    var addFragment = AddFragment.newInstance()
    var menuFragment = MenuFragment.newInstance()

    private var bottomNavigationViewListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when(item.itemId){
            R.id.to_favorite -> {
                loadFragment(likedFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.to_eating_menu -> {
                loadFragment(caloriesFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.to_search -> {
                loadFragment(searchFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.to_add -> {
                loadFragment(addFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.to_menu -> {
                loadFragment(menuFragment)
                return@OnNavigationItemSelectedListener true
            }
            else -> return@OnNavigationItemSelectedListener false
        }

    }

    private lateinit var bottomNavigationView: BottomNavigationView

    private fun setValues(){
        bottomNavigationView = findViewById(R.id.bottom_menu)
    }

        private fun loadFragment(fragment: Fragment) {
            val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
            ft.replace(R.id.mainFragment, fragment)
            ft.commit()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadFragment(Liked_fragment.newInstance())
        Realm.init(this)
        setValues()
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavigationViewListener)
        putAllCategory()
    }


    fun putAllCategory(){
        val realm = Realm.getDefaultInstance()
            realm.beginTransaction()
        realm.delete(Category::class.java)
            realm.copyToRealm(Category("Meat dishes", 10))
            realm.copyToRealm(Category("Beef stroganoff", 10))
            realm.copyToRealm(Category("Beshbarmak", 8))
            realm.copyToRealm(Category("Meatballs", 10))
            realm.copyToRealm(Category("Beefsteak",10))
            realm.copyToRealm(Category("Egg dishes", 4))
            realm.copyToRealm(Category("Boiled pork", 10))
            realm.copyToRealm(Category("Cabbage rolls", 5))
            realm.copyToRealm(Category("Mushroom", -5))
            realm.copyToRealm(Category("Casseroles", -3))
            realm.copyToRealm(Category("French meat", 8))
            realm.copyToRealm(Category("Vegetables", -10))
            realm.copyToRealm(Category("Omelet",-2))
            realm.copyToRealm(Category("Pudding", 0))
            realm.copyToRealm(Category("Scrambled eggs", 3))
            realm.copyToRealm(Category("Steak", 10))
            realm.copyToRealm(Category("Chicken tobacco",9))
            realm.copyToRealm(Category("Vinaigrette",-8))
            realm.copyToRealm(Category("Mushroom and vegetable snacks", -9))
            realm.copyToRealm(Category("Vegetable caviar", -7))
            realm.copyToRealm(Category("Broths", -6))
            realm.copyToRealm(Category("Pancakes", -2))
            realm.copyToRealm(Category("Cheesecakes", -1))
            realm.copyToRealm(Category("Pasta", 3))
            realm.copyToRealm(Category("Bread", 1))
            realm.commitTransaction()
    }
}