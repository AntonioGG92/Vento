package com.example.vento.data.dao

import androidx.room.*
import com.example.vento.data.entities.Usuario

@Dao
interface UsuarioDAO {

    // Insertar un usuario, reemplazando en caso de conflicto
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarUsuario(usuario: Usuario)

    // Consultar un usuario por correo y contraseña
    @Query("SELECT * FROM usuarios WHERE correo = :correo AND password = :password")
    suspend fun autenticarUsuario(correo: String, password: String): Usuario?

    // Obtener todos los usuarios administradores
    @Query("SELECT * FROM usuarios WHERE isAdmin = 1")
    suspend fun obtenerAdministradores(): List<Usuario>

    // Obtener todos los usuarios normales
    @Query("SELECT * FROM usuarios WHERE isAdmin = 0")
    suspend fun obtenerUsuarios(): List<Usuario>

    // Actualizar un usuario existente
    @Update
    suspend fun actualizarUsuario(usuario: Usuario)

    // Eliminar un usuario específico
    @Delete
    suspend fun eliminarUsuario(usuario: Usuario)

    // Agregar consulta para verificar si un usuario ya existe por correo
    @Query("SELECT COUNT(*) FROM usuarios WHERE correo = :correo")
    suspend fun existeUsuario(correo: String): Int

    // Obtener todos los usuarios (incluidos administradores y normales)
    @Query("SELECT * FROM usuarios")
    suspend fun obtenerTodosLosUsuarios(): List<Usuario>
}
