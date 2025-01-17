package com.example.vento.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "productos")
data class Producto(
    @PrimaryKey(autoGenerate = true) val id: Int = 0, // ID único autogenerado
    val nombre: String, // Nombre del producto
    val descripcion: String, // Descripción del producto
    val precio: Double, // Precio del producto
    val categoriaId: Int // Relación con la tabla de categorías
)
