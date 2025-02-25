package com.example.vento.admin

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vento.R
import com.example.vento.data.AppDatabase
import com.example.vento.data.entities.Categoria
import com.example.vento.data.entities.Producto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GestionarProductosFragment : Fragment() {

    private lateinit var adapter: ProductoAdapter
    private val productos = mutableListOf<Producto>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_gestion_productos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val etNombreProducto = view.findViewById<EditText>(R.id.etNombreProducto)
        val etPrecioProducto = view.findViewById<EditText>(R.id.etPrecioProducto)
        val etDescripcionProducto = view.findViewById<EditText>(R.id.etDescripcionProducto)
        val etNombreProductoEliminar = view.findViewById<EditText>(R.id.etNombreProductoEliminar)
        val etNombreCategoriaProducto = view.findViewById<EditText>(R.id.etNombreCategoriaProducto)

        val btnAgregarProducto = view.findViewById<Button>(R.id.btnAgregarProducto)
        val btnEliminarProducto = view.findViewById<Button>(R.id.btnEliminarProducto)
        val btnVolver = view.findViewById<Button>(R.id.btnVolver)
        val recyclerProductos = view.findViewById<RecyclerView>(R.id.recyclerProductos)

        val db = AppDatabase.getInstance(requireContext())
        val productoDAO = db.productoDAO()
        val categoriaDAO = db.categoriaDAO()

        adapter = ProductoAdapter(productos)
        recyclerProductos.layoutManager = LinearLayoutManager(requireContext())
        recyclerProductos.adapter = adapter

        // Función para actualizar la lista de productos
        fun actualizarLista() {
            CoroutineScope(Dispatchers.IO).launch {
                val productosActualizados = productoDAO.obtenerTodosLosProductos()
                withContext(Dispatchers.Main) {
                    productos.clear()
                    productos.addAll(productosActualizados)
                    adapter.notifyDataSetChanged()
                }
            }
        }

        // Agregar producto
        btnAgregarProducto.setOnClickListener {
            val nombre = etNombreProducto.text.toString().trim()
            val precio = etPrecioProducto.text.toString().toDoubleOrNull()
            val descripcion = etDescripcionProducto.text.toString().trim()
            val nombreCategoria = etNombreCategoriaProducto.text.toString().trim()

            if (nombre.isNotEmpty() && precio != null && descripcion.isNotEmpty() && nombreCategoria.isNotEmpty()) {
                CoroutineScope(Dispatchers.IO).launch {
                    val categoria: Categoria? = categoriaDAO.obtenerCategoriaPorNombre(nombreCategoria)

                    if (categoria != null) {
                        val nuevoProducto = Producto(
                            nombre = nombre,
                            precio = precio,
                            descripcion = descripcion,
                            categoriaId = categoria.id
                        )

                        val idProducto = productoDAO.insertarProducto(nuevoProducto)

                        withContext(Dispatchers.Main) {
                            if (idProducto > 0) {
                                Toast.makeText(requireContext(), "✅ Producto agregado", Toast.LENGTH_SHORT).show()
                                actualizarLista()
                            } else {
                                Toast.makeText(requireContext(), "⚠️ Error al agregar producto", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } else {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(requireContext(), "⚠️ La categoría '$nombreCategoria' no existe", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } else {
                Toast.makeText(requireContext(), "⚠️ Completa todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        // Eliminar producto
        btnEliminarProducto.setOnClickListener {
            val nombreAEliminar = etNombreProductoEliminar.text.toString().trim()

            if (nombreAEliminar.isNotEmpty()) {
                CoroutineScope(Dispatchers.IO).launch {
                    val productoAEliminar = productoDAO.obtenerProductoPorNombre(nombreAEliminar)

                    if (productoAEliminar != null) {
                        productoDAO.eliminarProducto(productoAEliminar)
                        withContext(Dispatchers.Main) {
                            Toast.makeText(requireContext(), "🗑 Producto eliminado", Toast.LENGTH_SHORT).show()
                            actualizarLista()
                        }
                    } else {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(requireContext(), "⚠️ Producto no encontrado", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } else {
                Toast.makeText(requireContext(), "⚠️ Ingresa el nombre del producto a eliminar", Toast.LENGTH_SHORT).show()
            }
        }

        // Botón volver
        btnVolver.setOnClickListener {
            findNavController().popBackStack()
        }

        actualizarLista()
    }
}
