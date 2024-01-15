package com.example.autoservice

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.ActionBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.autoservice.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupNavView()
        setupActionBar()
    }

    fun setupNavView(){
        var setOfScreens: Set<Int>
        val bottomNavigationView: BottomNavigationView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        if (!User.isUserMechanic(this)){
            setOfScreens = setOf(
                R.id.navigation_orders,
                R.id.navigation_mechanics,
                R.id.navigation_profile
            )
            binding.bottomNavViewForMechanic.visibility = View.GONE
            bottomNavigationView = binding.bottomNavView
        }
        else{
            setOfScreens = setOf(
                R.id.navigation_orders,
                R.id.navigation_profile
            )
            binding.bottomNavView.visibility = View.GONE
            bottomNavigationView = binding.bottomNavViewForMechanic
        }
        val appBarConfiguration = AppBarConfiguration(
            setOfScreens
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        bottomNavigationView.setupWithNavController(navController)

        bottomNavigationView.itemIconTintList = null
    }

    fun setupActionBar(){
        val actionBar: ActionBar? = supportActionBar;
        actionBar?.setBackgroundDrawable(ColorDrawable(getColor(R.color.action_bar_background_color)))
    }
}