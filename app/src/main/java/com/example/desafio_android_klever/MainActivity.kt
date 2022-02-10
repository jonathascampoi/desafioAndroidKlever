package com.example.desafio_android_klever

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.app_toolbar

class MainActivity : AppCompatActivity() {
    private lateinit var navController : NavController
    private lateinit var appBarConfiguration :AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(app_toolbar)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        navController.addOnDestinationChangedListener{_,_,_ ->
            supportActionBar?.setHomeAsUpIndicator(R.drawable.icon_my_back)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        TODO("trexo de codigo original com erro => return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()")
        return navController.navigateUp() || super.onSupportNavigateUp()

    }
}