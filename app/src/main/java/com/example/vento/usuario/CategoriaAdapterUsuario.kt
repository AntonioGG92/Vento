package com.example.vento.usuario

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vento.R
import com.example.vento.data.entities.Categoria

class CategoriaAdapterUsuario(
    private val categorias: List<Categoria>,
    private val onCategoriaClick: (Categoria) -> Unit // Callback para manejar clics
) : RecyclerView.Adapter<CategoriaAdapterUsuario.CategoriaViewHolder>() {

    inner class CategoriaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombre: TextView = itemView.findViewById(R.id.textNombreCategoria)
        val descripcion: TextView = itemView.findViewById(R.id.textDescripcionCategoria)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    // Llamar al callback con la categoría seleccionada
                    onCategoriaClick(categorias[position])
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_categoria_usuario, parent, false)
        return CategoriaViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoriaViewHolder, position: Int) {
        val categoria = categorias[position]
        holder.nombre.text = categoria.nombre
        holder.descripcion.text = categoria.descripcion
    }

    override fun getItemCount(): Int = categorias.size
}

