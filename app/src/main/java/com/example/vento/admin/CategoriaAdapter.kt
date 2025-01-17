package com.example.vento.admin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vento.R
import com.example.vento.data.entities.Categoria

class CategoriaAdapter(private var categorias: MutableList<Categoria>) :
    RecyclerView.Adapter<CategoriaAdapter.CategoriaViewHolder>() {

    inner class CategoriaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textNombreCategoria: TextView = itemView.findViewById(R.id.textNombreCategoria)
        val textDescripcionCategoria: TextView = itemView.findViewById(R.id.textDescripcionCategoria)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_categoria, parent, false)
        return CategoriaViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoriaViewHolder, position: Int) {
        val categoria = categorias[position]
        holder.textNombreCategoria.text = categoria.nombre
        holder.textDescripcionCategoria.text = categoria.descripcion
    }

    override fun getItemCount(): Int = categorias.size

    fun actualizarCategorias(nuevasCategorias: List<Categoria>) {
        categorias.clear()
        categorias.addAll(nuevasCategorias)
        notifyDataSetChanged()
    }
}
