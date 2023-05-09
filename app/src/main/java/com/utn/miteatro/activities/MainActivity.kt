package com.utn.miteatro.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.utn.miteatro.R
import com.utn.miteatro.entities.User

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavView : BottomNavigationView
    private lateinit var navHostFragment: NavHostFragment

    lateinit var userData : User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        bottomNavView = findViewById(R.id.bottom_bar)

        userData = intent.getParcelableExtra<User>("UserData")!!

        Log.d("Estado", "MainActivity")

        NavigationUI.setupWithNavController(bottomNavView, navHostFragment.navController)
    }
}