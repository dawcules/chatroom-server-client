package com.example.chatclient

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.util.Log
import android.view.*
import android.widget.EditText

const val EXTRA_MESSAGE = "com.example.chatclient.MESSAGE"
//Oletusnäkymä, käyttäjä kirjautuu chattiserverille
class MainActivity : AppCompatActivity() {


    val tag = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE) // UI siirtyy näppäimistön auetessa
        this.requestWindowFeature(Window.FEATURE_NO_TITLE) // Title palkki pois
        setContentView(R.layout.activity_main)
        Log.d(tag, "onCreate(Bundle) called")
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun regUsername(view: View) {
        //Käyttäjänimi viedään seuraavaan Activityyn
        val editText = findViewById<EditText>(R.id.editText)
        val uname = editText.text.toString()
        val intent = Intent(this, Messages::class.java).apply {
            putExtra(EXTRA_MESSAGE, uname)
        }
        startActivity(intent)
    }
}
