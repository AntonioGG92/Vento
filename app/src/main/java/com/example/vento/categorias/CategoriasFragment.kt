package com.example.vento.categorias

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vento.R

class CategoriasFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar el diseño del fragmento
        return inflater.inflate(R.layout.fragment_categorias, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configurar el RecyclerView
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerCategorias)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Lista de Categorías
        val categorias = listOf(
            Categoria("Juguetes Educativos", "Aprender mientras juegan.", R.drawable.ic_toy),
            Categoria("Figuras de Acción", "Los héroes favoritos.", R.drawable.ic_action_figure),
            Categoria("Juegos de Mesa", "Diversión para toda la familia.", R.drawable.ic_board_game)
        )

        recyclerView.adapter = CategoriaAdapter(categorias)
    }
}
