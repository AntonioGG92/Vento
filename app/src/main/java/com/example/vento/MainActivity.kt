package com.example.vento

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.vento.categorias.CategoriasFragment
import com.example.vento.productos.ProductosFragment
import com.example.vento.perfil.PerfilFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // activity_main.xml es el layout

        // Configuración del BottomNavigationView
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        // Cargar el fragmento inicial (Categorías por defecto)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, CategoriasFragment())
            .commit()

        // Listener para los ítems del BottomNavigationView
        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_categorias -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, CategoriasFragment())
                        .commit()
                    true
                }

                R.id.menu_productos -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, ProductosFragment())
                        .commit()
                    true
                }

                R.id.menu_perfil -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, PerfilFragment())
                        .commit()
                    true
                }

                else -> false
            }
        }
    }
}