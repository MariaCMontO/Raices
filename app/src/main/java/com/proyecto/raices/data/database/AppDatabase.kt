package com.proyecto.raices.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.proyecto.raices.data.dao.ServicioDao
import com.proyecto.raices.data.model.ServicioEntity

@Database(
    entities = [
        ServicioEntity::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun servicioDao(): ServicioDao
}