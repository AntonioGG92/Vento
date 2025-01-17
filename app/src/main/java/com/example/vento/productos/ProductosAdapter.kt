package com.example.vento.productos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vento.R
import com.example.vento.data.entities.Producto

class ProductosAdapter(
    private val productos: List<Producto>
) : RecyclerView.Adapter<ProductosAdapter.ProductoViewHolder>() {

    // Clase ViewHolder que contiene las referencias a las vistas
    inner class ProductoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNombreProducto: TextView = itemView.findViewById(R.id.tvNombreProducto)
        val tvDescripcionProducto: TextView = itemView.findViewById(R.id.tvDescripcionProducto)
        val tvPrecioProducto: TextView = itemView.findViewById(R.id.tvPrecioProducto)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_producto, parent, false)
        return ProductoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val producto = productos[position]
        holder.tvNombreProducto.text = producto.nombre
        holder.tvDescripcionProducto.text = producto.descripcion
        holder.tvPrecioProducto.text = producto.precio.toString()
    }

    override fun getItemCount(): Int = productos.size
}
