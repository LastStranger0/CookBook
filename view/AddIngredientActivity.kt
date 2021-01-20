package com.mtg.cookbook.view

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.mtg.cookbook.R
import com.mtg.cookbook.presenter.Checking
import com.mtg.cookbook.presenter.Downloader
import com.mtg.cookbook.presenter.IngredientDataBase
import com.mtg.cookbook.presenter.Sender
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.*

class AddIngredientActivity : AppCompatActivity(), IngredientDataBase, Sender {
    
    private lateinit var photo: ImageButton
    private lateinit var name: TextInputEditText
    private lateinit var description: TextInputEditText
    private lateinit var calories: TextInputEditText
    private lateinit var ready: Button
    private lateinit var uri: Bitmap
    private lateinit var imageUri: Uri
    
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_ingredient)
        initialise()


        ready.setOnClickListener{
            if(setError(name, this) && setError(description, this)
                && setError(calories, this) && setErrorImage(photo, this)){
                val intent = Intent(this, ChooseIngredientActivity::class.java)
                val stream = ByteArrayOutputStream()
                uri.compress(Bitmap.CompressFormat.PNG, 100, stream)

                val id = AddIngredient(name.text.toString(), calories.text.toString().toInt(),stream.toByteArray(),
                    "ingredient/${UUID.randomUUID()}.png", description.text.toString())
                SearchIngredientByID(id)?.let { it1 -> sendIngredient(it1) }

                intent.putExtra("id",
                        id)
                intent.putExtra("name", SearchIngredientByID(id)?.Name)
                setResult(RESULT_OK, intent)
                finish()
            }
        }

        photo.setOnClickListener{
            val imageIntent = Intent(Intent.ACTION_PICK)
            imageIntent.type = "image/*"
            startActivityForResult(imageIntent, AddActivity.MAIN_IMAGE_GALLERY)
        }
    }
    
    private fun initialise(){
        photo = findViewById(R.id.addIngrPhoto)
        name = findViewById(R.id.addIngrName)
        description = findViewById(R.id.addIngrDescription)
        calories = findViewById(R.id.addIngrCalories)
        ready = findViewById(R.id.addIngrReady)
    }


    companion object{
        fun cleartext(text: String, position: Int): String{
            return text.substring(0, position) + if(position+1 >= text.length) {
                text.substring(position + 1)
            }
            else
            {
                ""
            }
        }

        fun setError(textField: TextInputEditText, context: Context): Boolean{
            var text = textField.text.toString()
            if(textField.text.toString() == ""){
                textField.error = context.getString(R.string.errorEmpty)
                return false
            }
            return true
        }

        fun setErrorImage(image: ImageButton, context: Context): Boolean{
            if(image.drawable == context.getDrawable(R.drawable.ic_baseline_add_24)){
                return false
            }
            return true
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, imageReturnedIntent: Intent?) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent)
        when(requestCode){
            AddActivity.MAIN_IMAGE_GALLERY -> {
                if (resultCode == RESULT_OK) {
                    imageUri = imageReturnedIntent!!.data!!
                    try {
                        uri = MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                    photo.setImageBitmap(uri)
                }
            }

        }
    }
}