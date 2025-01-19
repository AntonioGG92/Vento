package com.example.vento.usuario

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vento.R
import com.example.vento.data.AppDatabase
import com.example.vento.usuario.CategoriaAdapterUsuario
import kotlinx.coroutines.launch

class UsuarioPanelFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_usuario_panel, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Obtén las referencias del RecyclerView y TextView
        val recyclerCategoriasUsuario = view.findViewById<RecyclerView>(R.id.recyclerCategoriasUsuario)
        val textNoCategorias = view.findViewById<TextView>(R.id.textNoCategorias)

        // Configura el RecyclerView
        recyclerCategoriasUsuario.layoutManager = LinearLayoutManager(requireContext())

        // Cargar las categorías desde la base de datos
        val db = AppDatabase.getInstance(requireContext())
        val categoriaDAO = db.categoriaDAO()

        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val categorias = categoriaDAO.obtenerTodasLasCategorias()
                if (categorias.isNotEmpty()) {
                    textNoCategorias.visibility = View.GONE
                    recyclerCategoriasUsuario.visibility = View.VISIBLE
                    recyclerCategoriasUsuario.adapter = CategoriaAdapterUsuario(categorias) { categoria ->
                        Toast.makeText(requireContext(), "Seleccionaste: ${categoria.nombre}", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    textNoCategorias.visibility = View.VISIBLE
                    recyclerCategoriasUsuario.visibility = View.GONE
                }
            } catch (e: Exception) {
                e.printStackTrace()
                textNoCategorias.visibility = View.VISIBLE
                textNoCategorias.text = getString(R.string.error_cargar_categorias)
            }
        }
    }
}
