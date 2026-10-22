package com.proyecto.raices.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.proyecto.raices.data.dao.ServicioDao
import com.proyecto.raices.data.dao.UsuariosDao
import com.proyecto.raices.data.model.ServicioEntity
import com.proyecto.raices.data.model.Usuario

@Database(
    entities = [
        ServicioEntity::class, Usuario::class
    ],
    version = 2
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun servicioDao(): ServicioDao
    abstract fun usuariosDao(): UsuariosDao

}