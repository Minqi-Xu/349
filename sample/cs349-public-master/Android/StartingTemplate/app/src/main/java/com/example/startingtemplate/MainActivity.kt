package com.example.startingtemplate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*

import androidx.activity.viewModels
import androidx.navigation.Navigation.findNavController

class MainActivity : AppCompatActivity() {

    // create view model using delegation
    private val viewModel: MyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // layout is defined in "res/layouts/activity_main.xml"
        // (it must have a NavHostFragment element)
        setContentView(R.layout.activity_main)

        // observe viewModel properties here

        viewModel.property.observe(this) {
            println("observe property $it")

            // update UI here
        }

        // if you need the navController in activity (for bottom nav, etc.)
//        val navController = findNavController(findViewById(R.id.navhostfragment))
    }

    // region Material ActionBar options menu setup and events

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // uncomment line below to add ActionBar options menu
//        menuInflater.inflate(R.menu.action_menu, menu)
        // (menu is defined in "res/menus/action_menu.xml")
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.actionName -> {
                // update viewModel for this action
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    //endregion
}