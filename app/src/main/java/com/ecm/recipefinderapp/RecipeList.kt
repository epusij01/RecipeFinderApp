package com.ecm.recipefinderapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException

class RecipeList : AppCompatActivity() {
    var volleyRequest: RequestQueue? = null
    var recipeList: ArrayList<Recipe>? = null
    var recipeAdapter: RecipeListAdapter? = null
    var layoutManager: RecyclerView.LayoutManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_list)

//        var urlString = "https://run.mocky.io/v3/03451ea4-2046-4f50-a875-e93b357f50d8"
        var url: String?

        var extras = intent.extras

        var ingredients = extras!!.getString("ingredients")
        var searchTerm = extras.getString("search")
        if (extras != null && !ingredients.equals("") && !searchTerm.equals("")) {
            var tempUrl = LEFT_LINK + ingredients + NEXTQ + searchTerm
            url = tempUrl
        } else {
            url = "https://edaman-recipe-search.p.rapidapi.com/search?q=chicken&r=roast"
        }

        volleyRequest = Volley.newRequestQueue(this)
        recipeList = ArrayList<Recipe>()
        getRecipe(url)
    }

    fun getRecipe(url: String) {
        val recipeReq = object : JsonObjectRequest(Method.GET, url, null,
            { response ->
                try {
                    val recipeArray = response.getJSONArray("hits")
                    for (i in 0 until recipeArray.length()) {
                        val recipeObject = recipeArray.getJSONObject(i).getJSONObject("recipe")
                        val title = recipeObject.getString("label")
                        val link = recipeObject.getString("url")
                        val thumbnail = recipeObject.getString("image")

                        Log.d("title", title.toString())

                        var ingredients: String = ""
                        val ingredientsArray = recipeObject.getJSONArray("ingredients")
                        for (d in 0 until ingredientsArray.length()) {
                            ingredients = ingredientsArray.getJSONObject(d).getString("text")
                        }
                        val recipe = Recipe()
                        recipe.title = title
                        recipe.link = link
                        recipe.thumbnail = thumbnail
                        recipe.ingredients = "Ingredients:  $ingredients"

                        recipeList!!.add(recipe)
                    }
                    recipeAdapter = RecipeListAdapter(recipeList!!, this)
                    layoutManager = LinearLayoutManager(this)

                    // Set up our recycler view
                    val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewId)
                    recyclerView.layoutManager = layoutManager
                    recyclerView.adapter = recipeAdapter

                    recipeAdapter!!.notifyDataSetChanged()
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            { error ->
                try {
                    Log.d("error", error.toString())
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }) {
            // Override the getHeaders() method to include the RapidAPI key and host
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["X-RapidAPI-Key"] = "bdf5346b62msh79680aa299b9bd9p15e888jsn0fbe6cde552f"
                headers["X-RapidAPI-Host"] = "edamam-recipe-search.p.rapidapi.com"
                return headers
            }
        }

        volleyRequest!!.add(recipeReq)
    }


//    fun getRecipe(url: String) {
//        var recipeReq = JsonObjectRequest(Request.Method.GET, url, null,
//            { response ->
//                try {
//
//                    var recipeArray = response.getJSONArray("hits")
//                    for (i in 0..recipeArray.length() - 1) {
//                        var recipeObject = recipeArray.getJSONObject(i).getJSONObject("recipe")
//                        var title = recipeObject.getString("label")
//                        var link = recipeObject.getString("url")
//                        var thumbnail = recipeObject.getString("image")
//
//                        Log.d("title", title.toString())
//
//                        var ingredients: String = ""
//                        var ingredientsArray = recipeObject.getJSONArray("ingredients")
//                        for (d in 0..ingredientsArray.length() - 1) {
//                            ingredients = ingredientsArray.getJSONObject(d).getString("text")
//                        }
//                        var recipe = Recipe()
//                        recipe.title = title
//                        recipe.link = link
//                        recipe.thumbnail = thumbnail
//                        recipe.ingredients = "Ingredients:  $ingredients"
//
//                        recipeList!!.add(recipe)
//                    }
//                    recipeAdapter = RecipeListAdapter(recipeList!!, this)
//                    layoutManager = LinearLayoutManager(this)
//
//                    //Set up our recycler view
//                    var recyclerView = findViewById<RecyclerView>(R.id.recyclerViewId)
//                    recyclerView.layoutManager = layoutManager
//                    recyclerView.adapter = recipeAdapter
//
//                    recipeAdapter!!.notifyDataSetChanged()
//
//
//                } catch (e: JSONException) {
//                    e.printStackTrace()
//                }
//            },
//            { error ->
//                try {
//                    Log.d("error", error.toString())
//
//                } catch (e: JSONException) {
//                    e.printStackTrace()
//                }
//            }){
//            override fun getHeaders(): MutableMap<String, String> {
//                val headers = HashMap<String, String>()
//                headers["X-RapidAPI-Key"] = "bdf5346b62msh79680aa299b9bd9p15e888jsn0fbe6cde552f"
//                headers["X-RapidAPI-Host"] = "edamam-recipe-search.p.rapidapi.com"
//                return headers
//            }
//
//        }
//
////
////            override fun getHeaders(): MutableMap<String, String> {
////                val headers = HashMap<String, String>()
////                headers["X-RapidAPI-Key"] = "bdf5346b62msh79680aa299b9bd9p15e888jsn0fbe6cde552f"
////                headers["X-RapidAPI-Host"] = "edamam-recipe-search.p.rapidapi.com"
////                return headers
////
////              }
////             }
////
//        volleyRequest!!.add(recipeReq)
//
//       }
//    }
//}
}


