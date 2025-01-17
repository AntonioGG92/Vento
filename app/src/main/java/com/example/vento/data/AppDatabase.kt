package com.example.vento.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.vento.data.dao.CategoriaDAO
import com.example.vento.data.dao.ProductoDAO
import com.example.vento.data.dao.UsuarioDAO
import com.example.vento.data.entities.Categoria
import com.example.vento.data.entities.Producto
import com.example.vento.data.entities.Usuario
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [Usuario::class, Producto::class, Categoria::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun usuarioDAO(): UsuarioDAO
    abstract fun productoDAO(): ProductoDAO
    abstract fun categoriaDAO(): CategoriaDAO

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
                    .addCallback(AppDatabaseCallback(context)) // Callback para datos iniciales
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    /**
     * Callback para inicializar datos predeterminados al crear la base de datos.
     */
    private class AppDatabaseCallback(
        private val context: Context
    ) : Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            // Insertar datos iniciales (ejemplo: usuario administrador)
            CoroutineScope(Dispatchers.IO).launch {
                val dao = getInstance(context).usuarioDAO()
                dao.insertarUsuario(
                    Usuario(
                        id = 1,
                        nombre = "Admin",
                        correo = "admin@vento.com",
                        password = "admin123",
                        isAdmin = true
                    )
                )
            }
        }
    }
}
