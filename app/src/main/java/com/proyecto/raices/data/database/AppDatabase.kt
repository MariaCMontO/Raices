package com.proyecto.raices.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.proyecto.raices.data.dao.GrupoViajeDao
import com.proyecto.raices.data.dao.ReservaDao
import com.proyecto.raices.data.dao.ServicioDao
import com.proyecto.raices.data.dao.UsuariosDao
import com.proyecto.raices.data.model.GrupoViaje
import com.proyecto.raices.data.model.MensajeGrupo
import com.proyecto.raices.data.model.MiembroGrupo
import com.proyecto.raices.data.model.Reserva
import com.proyecto.raices.data.model.ServicioEntity
import com.proyecto.raices.data.model.Usuario

@Database(
    entities = [
        ServicioEntity::class, Usuario::class, GrupoViaje::class, MiembroGrupo::class, MensajeGrupo::class, Reserva::class
    ],
    version = 5
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun servicioDao(): ServicioDao
    abstract fun usuariosDao(): UsuariosDao
    abstract fun grupoViajeDao(): GrupoViajeDao
    abstract fun reservaDao(): ReservaDao

}