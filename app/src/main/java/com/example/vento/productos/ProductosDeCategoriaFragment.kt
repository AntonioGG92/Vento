package com.example.vento.productos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vento.R
import com.example.vento.data.AppDatabase
import com.example.vento.data.entities.Producto
import kotlinx.coroutines.launch

class ProductosDeCategoriaFragment : Fragment() {

    private var categoriaId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            categoriaId = it.getInt("categoriaId", 0)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_productos_de_categoria, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerProductos = view.findViewById<RecyclerView>(R.id.recyclerProductos)
        recyclerProductos.layoutManager = LinearLayoutManager(requireContext())

        val db = AppDatabase.getInstance(requireContext())
        val productoDAO = db.productoDAO()

        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val productos: List<Producto> = productoDAO.obtenerProductosPorCategoria(categoriaId)
                if (productos.isNotEmpty()) {
                    recyclerProductos.adapter = ProductosAdapter(productos)
                } else {
                    Toast.makeText(requireContext(), "No hay productos en esta categoría", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(requireContext(), "Error al cargar los productos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
