package com.ecm.recipefinderapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var searchButton = findViewById<Button>(R.id.searchButton)
        searchButton.setOnClickListener {
            var intent = Intent(this, RecipeList::class.java)
            var ingredients = findViewById<EditText>(R.id.ingredientsEdt).text.toString().trim()
            var searchTerm = findViewById<EditText>(R.id.searchTermEdt).text.toString().trim()
            intent.putExtra("ingredients", ingredients)
            intent.putExtra("search", searchTerm)
            startActivity(intent)
//            startActivity(Intent(this, RecipeList::class.java))

        }
    }
}