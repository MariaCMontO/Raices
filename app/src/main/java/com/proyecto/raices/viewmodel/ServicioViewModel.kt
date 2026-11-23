package com.proyecto.raices.viewmodel

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.proyecto.raices.data.model.CategoriaServicio
import com.proyecto.raices.data.model.ServicioEntity
import com.proyecto.raices.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ServicioViewModel(application: Application) : AndroidViewModel(application) {

//    private val dao = DatabaseProvider
//        .getDatabase(application)
//        .servicioDao()

    val categoriasStr = CategoriaServicio.entries.map { it.tipo }

    var textoBusqueda by mutableStateOf("")
        private set

    var categoriaBusqueda by mutableStateOf("")
        private set

    var listaServiciosFiltrada by mutableStateOf<List<ServicioEntity>>(emptyList())
        private set

//    var listaServiciosPorCorreoAnfitrion by mutableStateOf<List<ServicioEntity>>(emptyList())
//        private set
    private var _listaServiciosPorCorreoAnfitrion = MutableStateFlow<List<ServicioEntity>>(emptyList())
    val listaServiciosPorCorreoAnfitrion: StateFlow<List<ServicioEntity>> = _listaServiciosPorCorreoAnfitrion.asStateFlow()


    var servicioSeleccionado by mutableStateOf<ServicioEntity?>(null)
        private set

    //val listaServicios = dao.obtenerTodos()

    var mensaje = mutableStateOf("")

    init {
        viewModelScope.launch {
            //val cantidad = dao.obtenerCantidadServicios()
            val cantidad = RetrofitClient.api.obtenerCantidadServicios()

            if (cantidad.cantidad == 0) {
                val lista = listOf(
                    ServicioEntity(
                        nombre = "Artesanias Tipicas",
                        descripcion = "descripcion",
                        categoria = CategoriaServicio.ARTESANIAS,
                        nombreAnfitrion = "ANFI",
                        correoAnfitrion = "anfi@correo.com",
                        precio = 500.00
                    ),
                    ServicioEntity(
                        nombre = "Platos Quindianos",
                        descripcion = "descripcion",
                        categoria = CategoriaServicio.GASTRONOMIA,
                        nombreAnfitrion = "ANFI",
                        correoAnfitrion = "anfi@correo.com",
                        precio = 500.00
                    ),
                    ServicioEntity(
                        nombre = "Fundación de Salento",
                        descripcion = "descripcion",
                        categoria = CategoriaServicio.HISTORIA,
                        nombreAnfitrion = "ANFI",
                        correoAnfitrion = "anfi@correo.com",
                        precio = 500.00
                    ),
                    ServicioEntity(
                        nombre = "Pomadas a base de hierbas",
                        descripcion = "descripcion",
                        categoria = CategoriaServicio.MEDICINA,
                        nombreAnfitrion = "ANFI",
                        correoAnfitrion = "anfi@correo.com",
                        precio = 500.00
                    ),
                    ServicioEntity(
                        nombre = "Tratamiento y cuidados del café",
                        descripcion = "descripcion",
                        categoria = CategoriaServicio.AGRICULTURA,
                        nombreAnfitrion = "ANFI",
                        correoAnfitrion = "anfi@correo.com",
                        precio = 500.00
                    ),
                    ServicioEntity(
                        nombre = "Caminata a Morrogacho",
                        descripcion = "descripcion",
                        categoria = CategoriaServicio.RECORRIDOS,
                        nombreAnfitrion = "ANFI",
                        correoAnfitrion = "anfi@correo.com",
                        precio = 500.00
                    ),
                    ServicioEntity(
                        nombre = "Artesanias ADMIN",
                        descripcion = "descripcion",
                        categoria = CategoriaServicio.ARTESANIAS,
                        nombreAnfitrion = "ADMIN",
                        correoAnfitrion = "admin@correo.com",
                        precio = 500.00
                    ),
                    ServicioEntity(
                        nombre = "Platos Cuyabros ADMIN",
                        descripcion = "descripcion",
                        categoria = CategoriaServicio.GASTRONOMIA,
                        nombreAnfitrion = "ADMIN",
                        correoAnfitrion = "admin@correo.com",
                        precio = 500.00
                    ),
                    ServicioEntity(
                        nombre = "Historia Indigena ADMIN",
                        descripcion = "descripcion",
                        categoria = CategoriaServicio.HISTORIA,
                        nombreAnfitrion = "ADMIN",
                        correoAnfitrion = "admin@correo.com",
                        precio = 500.00
                    ),
                    ServicioEntity(
                        nombre = "Pomadas del ADMIN",
                        descripcion = "descripcion",
                        categoria = CategoriaServicio.MEDICINA,
                        nombreAnfitrion = "ADMIN",
                        correoAnfitrion = "admin@correo.com",
                        precio = 500.00
                    ),
                    ServicioEntity(
                        nombre = "Cultivo de aguacate ADMIN",
                        descripcion = "descripcion",
                        categoria = CategoriaServicio.AGRICULTURA,
                        nombreAnfitrion = "ADMIN",
                        correoAnfitrion = "admin@correo.com",
                        precio = 500.00
                    ),
                    ServicioEntity(
                        nombre = "Caminata ADMIN",
                        descripcion = "descripcion",
                        categoria = CategoriaServicio.RECORRIDOS,
                        nombreAnfitrion = "ADMIN",
                        correoAnfitrion = "admin@correo.com",
                        precio = 500.00
                    )
                )
                //dao.insertarLista(lista)
                RetrofitClient.api.insertarLista(lista)
            }
            //cargarDatos()
        }
    }

//    fun cargarDatos() {
//        viewModelScope.launch {
//            try {
//                val respuesta = RetrofitClient.api.obtenerTodosLosServicios()
//                _listaServicios.value = respuesta
//
//            } catch (e: Exception) {
//                _listaServicios.value = emptyList()
//            }
//        }
//    }

    fun transformarACategoriaEnum(categoriaStr: String): CategoriaServicio? {

        return CategoriaServicio.entries.find { it.tipo == categoriaStr }
    }

    fun registrar(nuevoServicio: ServicioEntity) {
        viewModelScope.launch {
            try {
                val respuesta = RetrofitClient.api.registrarServicios(
                    nuevoServicio.nombre,
                    nuevoServicio.descripcion,
                    nuevoServicio.categoria,
                    nuevoServicio.nombreAnfitrion,
                    nuevoServicio.correoAnfitrion,
                    nuevoServicio.precio,
                )
                mensaje.value = respuesta["mensaje"].toString()

                if (respuesta["mensaje"] == "OK") {
                    _listaServiciosPorCorreoAnfitrion.value = RetrofitClient.api.obtenerTodosPorCorreoAnfi(nuevoServicio.correoAnfitrion);
                }
            } catch (e: Exception) {
                Log.e("Raices", "Error al registrar", e)
                mensaje.value = "Error de conexión al registrar"
            }
        }
    }

    fun actualizar(servicioActualizado: ServicioEntity) {
        viewModelScope.launch {
            //dao.actualizar(servicio)
            try {
                mensaje.value = RetrofitClient.api.actualizarServicios(
                    servicioActualizado.id,
                    servicioActualizado.nombre,
                    servicioActualizado.descripcion,
                    servicioActualizado.categoria,
                    servicioActualizado.precio,
                )["mensaje"].toString()

                _listaServiciosPorCorreoAnfitrion.update { listaActual ->
                    listaActual.map { if (it.id == servicioActualizado.id) servicioActualizado else it }
                }

            } catch (e: Exception){
                mensaje.value = "Error de conexión al registrar"
            }
        }
    }

    fun eliminar(id: Int) {
        viewModelScope.launch {
            //dao.eliminar(servicio)

            try {
                mensaje.value = RetrofitClient.api.eliminarPorId(id)["mensaje"].toString();

                _listaServiciosPorCorreoAnfitrion.update { listaActual ->
                    listaActual.filterNot { it.id == id }
                }

            } catch (e: Exception) {
                mensaje.value = "Error al eliminar"
            }
        }
    }

    fun obtenerPorId(id: Int) {
        viewModelScope.launch {
            //servicioSeleccionado = dao.obtenerPorId(id)
            RetrofitClient.api.obtenerPorId(id);
        }
    }

    fun obtenerTodosPorCorreoAnfitrion(correoAnfitrion: String, rol: String) {
        viewModelScope.launch {
            if (rol == "ADMIN") {
                //listaServiciosPorCorreoAnfitrion = dao.obtenerTodosAsList()
                _listaServiciosPorCorreoAnfitrion.value = RetrofitClient.api.obtenerTodosLosServicios();
            } else {
                //listaServiciosPorCorreoAnfitrion = dao.obtenerTodosPorCorreoAnfitrion(correoAnfitrion)
                _listaServiciosPorCorreoAnfitrion.value = RetrofitClient.api.obtenerTodosPorCorreoAnfi(correoAnfitrion);
            }
        }
    }


    fun aplicarBusqueda(texto: String = textoBusqueda, categoria: String = categoriaBusqueda) {
        val categoriaEnum = CategoriaServicio.entries.find { it.tipo == categoria };

        textoBusqueda = texto;
        categoriaBusqueda = categoria;

        viewModelScope.launch {
            val textoVacio = texto.isBlank();
            val categoriaVacia = categoria.isBlank();

            listaServiciosFiltrada = when {
                categoriaVacia && textoVacio -> {
                    //dao.obtenerTodosAsList()
                    RetrofitClient.api.obtenerTodosLosServicios();
                }

                categoriaVacia && !textoVacio -> {
                    //dao.obtenerTodosPorNombreOAnfitrion(textoBusqueda)
                    RetrofitClient.api.obtenerTodosPorNombreOAnfitrion(textoBusqueda);
                }

                !categoriaVacia && textoVacio -> {
                    //dao.obtenerTodosPorCategoria(categoriaEnum!!)
                    RetrofitClient.api.obtenerTodosPorCategoria(categoriaEnum!!.tipo);
                }

                else -> {
                    //dao.obtenerTodosPorTextoYCategoria(textoBusqueda, categoriaEnum!!)
                    RetrofitClient.api.obtenerTodosPorTextoYCategoria(textoBusqueda, categoriaEnum!!.tipo);
                }
            }
        }
    }

    fun limpiarFiltros() {
        textoBusqueda = ""
        categoriaBusqueda = ""
    }

}