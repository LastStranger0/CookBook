package com.mtg.cookbook.view

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.mtg.cookbook.R
import com.mtg.cookbook.model.Category
import com.mtg.cookbook.model.Ingredients
import com.mtg.cookbook.model.Step
import com.mtg.cookbook.presenter.*
import io.realm.Realm
import io.realm.RealmList
import io.realm.kotlin.where
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.*


class AddActivity : AppCompatActivity(), IngredientDataBase, RecipeDataBase, Sender {
    companion object{
        const val MAIN_IMAGE_GALLERY = 1
        const val NOT_MAIN_IMAGE1 = 2
        const val NOT_MAIN_IMAGE2 = 3
        const val NOT_MAIN_IMAGE3 = 4
        const val NOT_MAIN_IMAGE4 = 5
        const val CATEGORY = 6
    }

    private lateinit var mainPhotoButton: ImageButton
    private lateinit var notMainPhoto1: ImageButton
    private lateinit var notMainPhoto2: ImageButton
    private lateinit var notMainPhoto3: ImageButton
    private lateinit var notMainPhoto4: ImageButton
    private lateinit var nameText: TextInputEditText
    private lateinit var descriptionText: TextInputEditText
    private lateinit var categoryLayout: TextInputLayout
    private lateinit var categoryText: TextInputEditText
    private lateinit var ingredients: RecyclerView
    private lateinit var addIngredient: ImageButton
    private lateinit var steps: RecyclerView
    private lateinit var addStep: ImageButton
    private lateinit var readyBtn: Button

    private lateinit var selectedMainImage: Bitmap
    private lateinit var selectedImage1: Bitmap
    private lateinit var selectedImage2: Bitmap
    private lateinit var selectedImage3: Bitmap
    private lateinit var selectedImage4: Bitmap
    private lateinit var ingredientAdapter: IngredientAdapter
    private lateinit var stepsAdapter: StepsAdapter
    private var ingredientList: MutableList<Ingredients> = mutableListOf()
    private var stepList: MutableList<Step> = mutableListOf()

    private var notMainPhotoChanged1 = false
    private var notMainPhotoChanged2 = false
    private var notMainPhotoChanged3 = false
    private var notMainPhotoChanged4 = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        initialize()
        val realm = Realm.getDefaultInstance()
        ingredientAdapter = IngredientAdapter(this, ingredientList, this)
        ingredients.adapter = ingredientAdapter

        stepsAdapter = StepsAdapter(this, stepList)
        steps.adapter = stepsAdapter

        addIngredient.setOnClickListener {
            ingredientList.add(Ingredients("0", "Set Ingredient"))
            ingredientAdapter.notifyItemInserted(ingredientList.size - 1)
        }

        addStep.setOnClickListener {
            stepList.add(Step(""))
            stepsAdapter.notifyItemInserted(stepList.size - 1)
        }

        mainPhotoButton.setOnClickListener{
            val imageIntent = Intent(Intent.ACTION_PICK)
            imageIntent.type = "image/*"
            startActivityForResult(imageIntent, MAIN_IMAGE_GALLERY)
        }

        notMainPhoto1.setOnClickListener{
            val imageIntent = Intent(Intent.ACTION_PICK)
            imageIntent.type = "image/*"
            startActivityForResult(imageIntent, NOT_MAIN_IMAGE1)
        }
        notMainPhoto2.setOnClickListener{
            val imageIntent = Intent(Intent.ACTION_PICK)
            imageIntent.type = "image/*"
            startActivityForResult(imageIntent, NOT_MAIN_IMAGE2)
        }
        notMainPhoto3.setOnClickListener{
            val imageIntent = Intent(Intent.ACTION_PICK)
            imageIntent.type = "image/*"
            startActivityForResult(imageIntent, NOT_MAIN_IMAGE3)
        }
        notMainPhoto4.setOnClickListener{
            val imageIntent = Intent(Intent.ACTION_PICK)
            imageIntent.type = "image/*"
            startActivityForResult(imageIntent, NOT_MAIN_IMAGE4)
        }
        var calories = 0
        val allIngredient: RealmList<Ingredients> = RealmList()
        for (i in 0 until ingredientList.size) {
            allIngredient.add(ingredientList[i])
        }
        stepList = stepsAdapter.steps
        val allSteps: RealmList<Step> = RealmList()
        for (i in 0 until stepList.size){
            allSteps.add(stepList[i])
        }
        for (i in 0 until ingredientList.size){
            calories += ingredientList[i].Calories
        }
        categoryLayout.setOnClickListener{
            val intent = Intent(this, SetCategoryActivity::class.java)
            startActivityForResult(intent, CATEGORY)
        }
        readyBtn.setOnClickListener {
            if(AddIngredientActivity.setError(nameText, this)
                && AddIngredientActivity.setError(descriptionText, this)
                && AddIngredientActivity.setError(categoryText, this)
                && AddIngredientActivity.setErrorImage(mainPhotoButton, this)){
                val mainStream = ByteArrayOutputStream()
                selectedMainImage.compress(Bitmap.CompressFormat.PNG, 100, mainStream)
                val notMainStream1 = ByteArrayOutputStream()
                if (notMainPhotoChanged1)
                    selectedImage1.compress(Bitmap.CompressFormat.PNG, 100, notMainStream1)
                val notMainStream2 = ByteArrayOutputStream()
                if (notMainPhotoChanged2)
                    selectedImage2.compress(Bitmap.CompressFormat.PNG, 100, notMainStream2)
                val notMainStream3 = ByteArrayOutputStream()
                if (notMainPhotoChanged3)
                    selectedImage3.compress(Bitmap.CompressFormat.PNG, 100, notMainStream3)
                val notMainStream4 = ByteArrayOutputStream()
                if (notMainPhotoChanged4)
                    selectedImage4.compress(Bitmap.CompressFormat.PNG, 100, notMainStream4)

                val images = RealmList<ByteArray>()
                val imagePath = RealmList<String>()
                if (notMainPhotoChanged1){
                    images.add(notMainStream1.toByteArray())
                    imagePath.add("recipe/${UUID.randomUUID()}.png")
                }
                if (notMainPhotoChanged2){
                    images.add(notMainStream2.toByteArray())
                    imagePath.add("recipe/${UUID.randomUUID()}.png")
                }
                if (notMainPhotoChanged3){
                    images.add(notMainStream3.toByteArray())
                    imagePath.add("recipe/${UUID.randomUUID()}.png")
                }
                if (notMainPhotoChanged4){
                    images.add(notMainStream4.toByteArray())
                    imagePath.add("recipe/${UUID.randomUUID()}.png")
                }

                val cat = realm.where<Category>().equalTo("category", categoryText.text.toString()).findFirst()?: Category()
                Log.d("ADDDDD", "${cat.category} + ${cat.weight}")
                val id = addRecipe(nameText.text.toString(),
                        mainStream.toByteArray(),
                        "recipe/${UUID.randomUUID()}.png",
                        categoryText.text.toString(),
                        descriptionText.text.toString(),
                        images,
                        allSteps,
                        calories,
                        imagePath,
                        allIngredient,
                        cat.weight)
                val intent = Intent(this, AddFragment::class.java)
                Log.d("Checking online", "online")
                    if (id != null) {
                        Log.d("AddActivity", searchRecipeByID(id).id)
                        val recipe = searchRecipeByID(id)
                        sendRecipe(recipe)
                        Log.d("Checking online", "sent")
                    }
                intent.putExtra("id", id)
                setResult(RESULT_OK, intent)
                finish()
            }
        }
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, imageReturnedIntent: Intent?) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent)
        when(requestCode){
            MAIN_IMAGE_GALLERY -> {
                if (resultCode == RESULT_OK) {
                    val selectedImage = imageReturnedIntent!!.data
                    try {
                        selectedMainImage = MediaStore.Images.Media.getBitmap(contentResolver, selectedImage)
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                    mainPhotoButton.setImageBitmap(selectedMainImage)
                }
            }
            NOT_MAIN_IMAGE1 -> {
                val selectedImage = imageReturnedIntent!!.data
                try {
                    selectedImage1 = MediaStore.Images.Media.getBitmap(contentResolver, selectedImage)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                notMainPhotoChanged1 = true
                notMainPhoto1.setImageBitmap(selectedImage1)
            }
            NOT_MAIN_IMAGE2 -> {
                val selectedImage = imageReturnedIntent!!.data
                try {
                    selectedImage2 = MediaStore.Images.Media.getBitmap(contentResolver, selectedImage)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                notMainPhotoChanged2 = true
                notMainPhoto2.setImageBitmap(selectedImage2)
            }
            NOT_MAIN_IMAGE3 -> {
                val selectedImage = imageReturnedIntent!!.data
                try {
                    selectedImage3 = MediaStore.Images.Media.getBitmap(contentResolver, selectedImage)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                notMainPhotoChanged3 = true
                notMainPhoto3.setImageBitmap(selectedImage3)
            }
            NOT_MAIN_IMAGE4 -> {
                val selectedImage = imageReturnedIntent!!.data
                try {
                    selectedImage4 = MediaStore.Images.Media.getBitmap(contentResolver, selectedImage)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                notMainPhotoChanged4 = true
                notMainPhoto4.setImageBitmap(selectedImage4)
            }
            IngredientAdapter.INGREDIENT_CALL -> {
                if (resultCode == RESULT_OK) {
                    val position = imageReturnedIntent?.getIntExtra("position", 0)
                    val ingredient: Ingredients? =
                            imageReturnedIntent?.let { SearchIngredientByID(it.getStringExtra("id")) }
                    if (imageReturnedIntent != null) {
                        Log.d("AddActivity", imageReturnedIntent.getStringExtra("id"))
                    }
                    if (ingredient != null) {
                        ingredientList[position!!] = ingredient
                    }
                    if (position != null) {
                        ingredientAdapter.notifyItemChanged(position)
                    }
                }
            }
            CATEGORY ->{
                if (resultCode == RESULT_OK){
                    val CategoryName = imageReturnedIntent?.getStringExtra("category")
                    categoryText.setText(CategoryName)
                }
            }
        }
    }

    private fun initialize(){
        mainPhotoButton = findViewById(R.id.mainPhotoButton)
        notMainPhoto1 = findViewById(R.id.notMainPhoto1)
        notMainPhoto2 = findViewById(R.id.notMainPhoto2)
        notMainPhoto3 = findViewById(R.id.notMainPhoto3)
        notMainPhoto4 = findViewById(R.id.notMainPhoto4)
        nameText = findViewById(R.id.nameText)
        descriptionText = findViewById(R.id.descriptionText)
        categoryLayout = findViewById(R.id.categoryLayout)
        categoryText = findViewById(R.id.categoryText)
        ingredients = findViewById(R.id.ingredients)
        addIngredient = findViewById(R.id.addIngredient)
        steps = findViewById(R.id.steps)
        addStep = findViewById(R.id.addStep)
        readyBtn = findViewById(R.id.readyBtn)
    }
}