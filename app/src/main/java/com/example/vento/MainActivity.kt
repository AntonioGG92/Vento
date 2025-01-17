package com.example.vento

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        try {
            val navController = findNavController(R.id.navHostFragment)
            setupActionBarWithNavController(navController)
            Log.d("MainActivity", "NavController configurado correctamente")
        } catch (e: Exception) {
            Log.e("MainActivity", "Error al configurar NavController: ${e.message}")
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.navHostFragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
