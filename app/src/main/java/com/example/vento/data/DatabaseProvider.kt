package com.example.vento.data

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.vento.data.dao.UsuarioDAO
import com.example.vento.data.entities.Usuario
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object DatabaseProvider {
    @Volatile
    private var INSTANCE: AppDatabase? = null

    /**
     * Devuelve una instancia única de AppDatabase.
     */
    fun getDatabase(context: Context): AppDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "vento_database"
            ).addCallback(DatabaseCallback(context)) // Añade un callback para datos iniciales
                .build()
            INSTANCE = instance
            instance
        }
    }

    /**
     * Callback para ejecutar lógica adicional cuando se crea la base de datos.
     */
    private class DatabaseCallback(
        private val context: Context
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            // Inserta datos iniciales (por ejemplo, un usuario administrador)
            CoroutineScope(Dispatchers.IO).launch {
                val dao: UsuarioDAO = getDatabase(context).usuarioDAO()
                val admin = Usuario(
                    id = 1,
                    nombre = "Admin",
                    correo = "admin@vento.com",
                    password = "admin123",
                    isAdmin = true
                )
                dao.insertarUsuario(admin)
            }
        }
    }
}
