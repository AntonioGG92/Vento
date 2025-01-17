package com.example.vento.productos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vento.R
import com.example.vento.data.DatabaseProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductosFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Infla el diseño del fragmento
        return inflater.inflate(R.layout.fragment_productos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerProductos)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Cargar productos desde la base de datos usando coroutines
        CoroutineScope(Dispatchers.IO).launch {
            val db = DatabaseProvider.getDatabase(requireContext())
            val productos = db.productoDAO().obtenerTodosLosProductos() // Asegúrate de que este método exista y funcione.

            withContext(Dispatchers.Main) {
                // Configura el adaptador con los productos obtenidos
                recyclerView.adapter = ProductosAdapter(productos)
            }
        }
    }
}
