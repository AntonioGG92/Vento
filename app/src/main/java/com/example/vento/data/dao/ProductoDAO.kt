package com.example.vento.data.dao

import androidx.room.*
import com.example.vento.data.entities.Producto

@Dao
interface ProductoDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarProducto(producto: Producto)

    @Query("SELECT * FROM productos WHERE categoriaId = :categoriaId")
    suspend fun obtenerProductosPorCategoria(categoriaId: Int): List<Producto>

    @Query("SELECT * FROM productos")
    suspend fun obtenerTodosLosProductos(): List<Producto>

    @Update
    suspend fun actualizarProducto(producto: Producto)

    @Delete
    suspend fun eliminarProducto(producto: Producto)

    @Query("SELECT * FROM productos WHERE nombre = :nombre")
    suspend fun obtenerProductoPorNombre(nombre: String): Producto?
}
