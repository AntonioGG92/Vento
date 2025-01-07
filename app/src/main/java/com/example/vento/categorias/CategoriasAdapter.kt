package com.example.vento.categorias

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vento.R

// Modelo de datos para la categoría
data class Categoria(val nombre: String, val descripcion: String, val icono: Int)

// Adaptador para el RecyclerView
class CategoriaAdapter(private val categorias: List<Categoria>) :
    RecyclerView.Adapter<CategoriaAdapter.CategoriaViewHolder>() {

    // ViewHolder para cada elemento de la lista
    inner class CategoriaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val iconCategoria: ImageView = itemView.findViewById(R.id.iconCategoria)
        val textNombreCategoria: TextView = itemView.findViewById(R.id.textNombreCategoria)
        val textDescripcionCategoria: TextView = itemView.findViewById(R.id.textDescripcionCategoria)
    }

    // Crear la vista para cada ítem del RecyclerView
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_categoria, parent, false) // Infla el layout para cada ítem
        return CategoriaViewHolder(view)
    }

    // Vincular los datos con la vista (ViewHolder)
    override fun onBindViewHolder(holder: CategoriaViewHolder, position: Int) {
        val categoria = categorias[position]
        holder.iconCategoria.setImageResource(categoria.icono) // Establece el icono
        holder.textNombreCategoria.text = categoria.nombre // Establece el nombre
        holder.textDescripcionCategoria.text = categoria.descripcion // Establece la descripción
    }

    // Tamaño de la lista
    override fun getItemCount(): Int = categorias.size
}
