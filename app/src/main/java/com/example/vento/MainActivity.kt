package com.example.vento

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.vento.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Configurar ViewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Establecer la Toolbar como ActionBar
        setSupportActionBar(binding.toolbar)

        // Obtener el NavHostFragment de forma explícita y su NavController
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as? NavHostFragment
            ?: throw IllegalStateException("NavHostFragment no encontrado en el layout")
        val navController = navHostFragment.navController

        // Configurar la ActionBar con el NavController
        setupActionBarWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as? NavHostFragment
            ?: throw IllegalStateException("NavHostFragment no encontrado en el layout")
        return navHostFragment.navController.navigateUp() || super.onSupportNavigateUp()
    }
}
