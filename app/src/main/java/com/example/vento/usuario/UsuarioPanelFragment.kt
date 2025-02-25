package com.example.vento.usuario

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vento.R
import com.example.vento.data.AppDatabase
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

        val recyclerCategoriasUsuario = view.findViewById<RecyclerView>(R.id.recyclerCategoriasUsuario)
        val textNoCategorias = view.findViewById<TextView>(R.id.textNoCategorias)

        recyclerCategoriasUsuario.layoutManager = LinearLayoutManager(requireContext())

        val db = AppDatabase.getInstance(requireContext())
        val categoriaDAO = db.categoriaDAO()

        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val categorias = categoriaDAO.obtenerTodasLasCategorias()
                if (categorias.isNotEmpty()) {
                    textNoCategorias.visibility = View.GONE
                    recyclerCategoriasUsuario.visibility = View.VISIBLE
                    recyclerCategoriasUsuario.adapter = CategoriaAdapterUsuario(categorias) { categoria ->

                        val bundle = Bundle().apply {
                            putInt("categoriaId", categoria.id)
                        }

                        findNavController().navigate(R.id.productosDeCategoriaFragment, bundle)
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
