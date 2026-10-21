package com.proyecto.raices.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.proyecto.raices.data.database.DatabaseProvider
import com.proyecto.raices.data.model.CategoriaServicio
import com.proyecto.raices.data.model.ServicioEntity
import kotlinx.coroutines.launch

class ServicioViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = DatabaseProvider
        .getDatabase(application)
        .servicioDao()

    val categoriasStr = CategoriaServicio.entries.map { it.tipo }

    var textoBusqueda by mutableStateOf("")
        private set

    var categoriaBusqueda by mutableStateOf("")
        private set

    var listaServiciosFiltrada by mutableStateOf<List<ServicioEntity>>(emptyList())
        private set

    var listaServiciosPorCorreoAnfitrion by mutableStateOf<List<ServicioEntity>>(emptyList())
        private set

    var servicioSeleccionado by mutableStateOf<ServicioEntity?>(null)
        private set

    val listaServicios = dao.obtenerTodos()

    init {
        viewModelScope.launch {
            val cantidad = dao.obtenerCantidadServicios()

            if (cantidad == 0) {
                val lista = listOf(
                    ServicioEntity(
                        nombre = "Servicio1",
                        descripcion = "descripcion",
                        categoria = CategoriaServicio.ARTESANIAS,
                        nombreAnfitrion = "Bryan",
                        correoAnfitrion = "correo",
                        precio = 500.00
                    ),
                    ServicioEntity(
                        nombre = "Servicio2",
                        descripcion = "descripcion",
                        categoria = CategoriaServicio.GASTRONOMIA,
                        nombreAnfitrion = "Bryan",
                        correoAnfitrion = "correo",
                        precio = 500.00
                    ),
                    ServicioEntity(
                        nombre = "Servicio3",
                        descripcion = "descripcion",
                        categoria = CategoriaServicio.HISTORIA,
                        nombreAnfitrion = "Bryan",
                        correoAnfitrion = "correo",
                        precio = 500.00
                    ),
                    ServicioEntity(
                        nombre = "Servicio4",
                        descripcion = "descripcion",
                        categoria = CategoriaServicio.MEDICINA,
                        nombreAnfitrion = "Bryan",
                        correoAnfitrion = "correo",
                        precio = 500.00
                    ),
                    ServicioEntity(
                        nombre = "Servicio5",
                        descripcion = "descripcion",
                        categoria = CategoriaServicio.AGRICULTURA,
                        nombreAnfitrion = "Bryan",
                        correoAnfitrion = "correo",
                        precio = 500.00
                    ),
                    ServicioEntity(
                        nombre = "Servicio6",
                        descripcion = "descripcion",
                        categoria = CategoriaServicio.RECORRIDOS,
                        nombreAnfitrion = "Bryan",
                        correoAnfitrion = "correo",
                        precio = 500.00
                    )
                )
                dao.insertarLista(lista)
            }
        }
    }

    fun insertar(servicio: ServicioEntity) {
        viewModelScope.launch {
            dao.insertar(servicio)
        }
    }

    fun actualizar(servicio: ServicioEntity) {
        viewModelScope.launch {
            dao.actualizar(servicio)
        }
    }

    fun eliminar(servicio: ServicioEntity) {
        viewModelScope.launch {
            dao.eliminar(servicio)
        }
    }

    fun obtenerPorId(id: Int) {
        viewModelScope.launch {
            servicioSeleccionado = dao.obtenerPorId(id)
        }
    }

    fun obtenerTodosPorCorreoAnfitrion(correoAnfitrion: String) {
        viewModelScope.launch {
            listaServiciosPorCorreoAnfitrion = dao.obtenerTodosPorCorreoAnfitrion(correoAnfitrion)
        }
    }

    fun aplicarBusqueda(texto: String = textoBusqueda, categoria: String = categoriaBusqueda) {
        val categoriaEnum = CategoriaServicio.entries.find { it.tipo == categoria }

        textoBusqueda = texto
        categoriaBusqueda = categoria

        viewModelScope.launch {
            val textoVacio = texto.isBlank()
            val categoriaVacia = categoria.isBlank()

            listaServiciosFiltrada = when {
                categoriaVacia && textoVacio -> {
                    dao.obtenerTodosAsList()
                }

                categoriaVacia && !textoVacio -> {
                    dao.obtenerTodosPorNombreOAnfitrion(textoBusqueda)
                }

                !categoriaVacia && textoVacio -> {
                    dao.obtenerTodosPorCategoria(categoriaEnum!!)
                }

                else -> {
                    dao.obtenerTodosPorTextoYCategoria(textoBusqueda, categoriaEnum!!)
                }
            }
        }
    }

    fun limpiarFiltros() {
        textoBusqueda = ""
        categoriaBusqueda = ""
    }

}