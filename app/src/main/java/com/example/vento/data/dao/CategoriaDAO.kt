package com.example.vento.data.dao

import androidx.room.*
import com.example.vento.data.entities.Categoria

@Dao
interface CategoriaDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarCategoria(categoria: Categoria)

    @Query("SELECT * FROM categorias")
    suspend fun obtenerTodasLasCategorias(): List<Categoria>

    @Query("SELECT * FROM categorias WHERE id = :id")
    suspend fun obtenerCategoriaPorId(id: Int): Categoria?

    @Update
    suspend fun actualizarCategoria(categoria: Categoria)

    @Delete
    suspend fun eliminarCategoria(categoria: Categoria)

    @Query("SELECT * FROM categorias WHERE nombre = :nombre LIMIT 1")
    suspend fun obtenerCategoriaPorNombre(nombre: String): Categoria?
}
