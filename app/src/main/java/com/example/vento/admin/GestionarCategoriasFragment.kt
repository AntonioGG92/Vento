package com.example.vento.admin

import android.os.Bundle
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GestionarCategoriasFragment : Fragment() {

    private lateinit var adapter: CategoriaAdapter
    private val categorias = mutableListOf<Categoria>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_gestion_categorias, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val etNombreCategoria = view.findViewById<EditText>(R.id.etNombreCategoria)
        val etDescripcionCategoria = view.findViewById<EditText>(R.id.etDescripcionCategoria)
        val btnAgregarCategoria = view.findViewById<Button>(R.id.btnAgregarCategoria)
        val btnEliminarCategoria = view.findViewById<Button>(R.id.btnEliminarCategoria)
        val btnVolver = view.findViewById<Button>(R.id.btnVolver) // Botón volver
        val recyclerCategorias = view.findViewById<RecyclerView>(R.id.recyclerCategorias)

        val db = AppDatabase.getInstance(requireContext())
        val categoriaDAO = db.categoriaDAO()

        adapter = CategoriaAdapter(categorias)
        recyclerCategorias.layoutManager = LinearLayoutManager(requireContext())
        recyclerCategorias.adapter = adapter

        // Función para actualizar la lista
        fun actualizarLista() {
            CoroutineScope(Dispatchers.IO).launch {
                val categoriasActualizadas = categoriaDAO.obtenerTodasLasCategorias()
                categorias.clear()
                categorias.addAll(categoriasActualizadas)
                requireActivity().runOnUiThread {
                    adapter.notifyDataSetChanged()
                }
            }
        }

        // Agregar categoría
        btnAgregarCategoria.setOnClickListener {
            val nombre = etNombreCategoria.text.toString()
            val descripcion = etDescripcionCategoria.text.toString()

            if (nombre.isNotEmpty() && descripcion.isNotEmpty()) {
                val nuevaCategoria = Categoria(nombre = nombre, descripcion = descripcion)
                CoroutineScope(Dispatchers.IO).launch {
                    categoriaDAO.insertarCategoria(nuevaCategoria)
                    requireActivity().runOnUiThread {
                        Toast.makeText(requireContext(), "Categoría agregada", Toast.LENGTH_SHORT).show()
                        actualizarLista()
                    }
                }
            } else {
                Toast.makeText(requireContext(), "Completa todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        // Eliminar categoría
        btnEliminarCategoria.setOnClickListener {
            val nombre = etNombreCategoria.text.toString()

            if (nombre.isNotEmpty()) {
                CoroutineScope(Dispatchers.IO).launch {
                    val categoriaAEliminar = categoriaDAO.obtenerCategoriaPorNombre(nombre)
                    if (categoriaAEliminar != null) {
                        categoriaDAO.eliminarCategoria(categoriaAEliminar)
                        requireActivity().runOnUiThread {
                            Toast.makeText(requireContext(), "Categoría eliminada", Toast.LENGTH_SHORT).show()
                            actualizarLista()
                        }
                    } else {
                        requireActivity().runOnUiThread {
                            Toast.makeText(requireContext(), "Categoría no encontrada", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } else {
                Toast.makeText(requireContext(), "Por favor, ingresa el nombre de la categoría a eliminar", Toast.LENGTH_SHORT).show()
            }
        }

        // Botón volver
        btnVolver.setOnClickListener {
            findNavController().popBackStack()
        }

        actualizarLista()
    }
}
