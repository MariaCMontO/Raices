package com.proyecto.raices.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import com.proyecto.raices.data.model.Usuario
import com.proyecto.raices.data.sharedPreferences.SharedPreferencesManager
import com.proyecto.raices.network.RetrofitClient
import kotlinx.coroutines.launch

class viewmodelUsuarios(application: Application) : AndroidViewModel(application) {

    private val sharedPrefManager = SharedPreferencesManager(application)
    private val api = RetrofitClient.api

    var usuarioActivo by mutableStateOf<Usuario?>(null)
        private set

    // Último mensaje del servidor / de error de conexión (por si quieres mostrarlo en la UI)
    var mensaje by mutableStateOf("")
        private set

    init {
        viewModelScope.launch {
            // 1) Sesión guardada -> login automático contra el servidor
            try {
                val (correoGuardado, passGuardado) = sharedPrefManager.cargarDatos()
                if (correoGuardado.isNotEmpty() && passGuardado.isNotEmpty()) {
                    val respuesta = api.loginUsuario(correoGuardado, passGuardado)
                    usuarioActivo = respuesta.usuarioDeLogin()
                }
            } catch (e: Exception) {
                // Sin conexión: se queda sin sesión y la app irá al login
                mensaje = "Error de conexión al restaurar sesión"
            }

            // 2) Usuarios de prueba si la tabla está vacía
            try {
                if (api.obtenerCantidadUsuarios().cantidad == 0) {
                    listOf(
                        Usuario(
                            nombre = "Anfitrión", telefono = "123", correo = "anfi@correo.com",
                            contraseña = "Anfi123", direccion = "Calle 5 #14-98",
                            intereses = emptyList(), oficio = "", tipoUsuario = "ANFITRION",
                            sobreMi = "", lenguas = mutableListOf(), genero = ""
                        ),
                        Usuario(
                            nombre = "Explorador", telefono = "123", correo = "explo@correo.com",
                            contraseña = "Explo123", direccion = "Calle 5 #14-98",
                            intereses = emptyList(), oficio = "", tipoUsuario = "EXPLORADOR",
                            sobreMi = "", lenguas = mutableListOf(), genero = ""
                        ),
                        Usuario(
                            nombre = "ADMIN", telefono = "123", correo = "admin@correo.com",
                            contraseña = "Admin123", direccion = "Calle 5 #14-98",
                            intereses = emptyList(), oficio = "", tipoUsuario = "ADMIN",
                            sobreMi = "", lenguas = mutableListOf(), genero = ""
                        )
                    ).forEach { enviarRegistro(it) }
                }
            } catch (e: Exception) {
                mensaje = "Error de conexión al verificar usuarios"
            }
        }
    }


    // ---------- Conversión JSON de MySQL <-> Usuario (sin clases extra) ----------

    private fun JsonObject.texto(clave: String): String {
        val e = get(clave)
        return if (e == null || e.isJsonNull) "" else e.asString
    }

    private fun textoALista(texto: String): List<String> {
        if (texto.isBlank()) return emptyList()
        return try {
            Gson().fromJson<List<String>>(texto, object : TypeToken<List<String>>() {}.type)
                ?: emptyList()
        } catch (e: Exception) {
            texto.split(",").map { it.trim() }.filter { it.isNotEmpty() }
        }
    }

    private fun listaATexto(lista: List<String>?): String =
        Gson().toJson(lista ?: emptyList<String>())

    // La columna en MySQL se llama "contrasena" (sin ñ)
    private fun JsonObject.aUsuario(): Usuario = Usuario(
        id = texto("id").toIntOrNull() ?: 0,
        nombre = texto("nombre"),
        telefono = texto("telefono"),
        correo = texto("correo"),
        contraseña = texto("contrasena"),
        direccion = texto("direccion"),
        intereses = textoALista(texto("intereses")),
        oficio = texto("oficio"),
        tipoUsuario = texto("tipoUsuario"),
        sobreMi = texto("sobreMi"),
        lenguas = textoALista(texto("lenguas")),
        genero = texto("genero")
    )

    // El endpoint devuelve la fila o null si el correo no existe
    private fun JsonElement?.comoUsuarioONull(): Usuario? =
        if (this != null && this.isJsonObject) this.asJsonObject.aUsuario() else null

    // Login: {"mensaje":"OK","usuario":{...}} o {"mensaje":"ERROR"}
    private fun JsonObject.usuarioDeLogin(): Usuario? {
        val ok = get("mensaje")?.takeIf { !it.isJsonNull }?.asString == "OK"
        val u = get("usuario")
        return if (ok && u != null && u.isJsonObject) u.asJsonObject.aUsuario() else null
    }

    // Un solo lugar que arma la llamada de registro (la usan init y agregarUsuario)
    private suspend fun enviarRegistro(usuario: Usuario): Map<String, String> =
        api.registrarUsuario(
            nombre = usuario.nombre,
            telefono = usuario.telefono,
            correo = usuario.correo,
            contrasena = usuario.contraseña,
            direccion = usuario.direccion,
            tipoUsuario = usuario.tipoUsuario,
            oficio = usuario.oficio.orEmpty(),
            sobreMi = usuario.sobreMi,
            lenguas = listaATexto(usuario.lenguas),
            genero = usuario.genero
        )

    fun agregarUsuario(usuario: Usuario) {
        viewModelScope.launch {
            try {
                // Igual que antes: si el correo ya existe, no se inserta
                if (api.obtenerUsuarioPorCorreo(usuario.correo).comoUsuarioONull() != null) {
                    mensaje = "El correo ya está registrado"
                    return@launch
                }
                mensaje = enviarRegistro(usuario)["mensaje"].toString()
            } catch (e: Exception) {
                mensaje = "Error de conexión al registrar"
            }
        }
    }

    fun eliminarUsuario(usuario: Usuario) {
        viewModelScope.launch {
            try {
                val respuesta = api.eliminarUsuario(usuario.correo)
                mensaje = respuesta["mensaje"].toString()

                if (respuesta["mensaje"] == "OK" && usuarioActivo?.correo == usuario.correo) {
                    usuarioActivo = null
                    sharedPrefManager.limpiarDatos()
                }
            } catch (e: Exception) {
                mensaje = "Error de conexión al eliminar"
            }
        }
    }

    fun actualizarPersona(usuarioActualizado: Usuario) {
        viewModelScope.launch {
            try {
                val actual = usuarioActivo

                // 1) ¿Cambió la contraseña? Va por su propio endpoint, que pide la actual.
                val cambioContrasena = actual != null &&
                        actual.correo == usuarioActualizado.correo &&
                        actual.contraseña != usuarioActualizado.contraseña

                if (cambioContrasena) {
                    val r = api.cambiarContrasena(
                        correo = usuarioActualizado.correo,
                        contrasenaActual = actual!!.contraseña,
                        contrasenaNueva = usuarioActualizado.contraseña
                    )
                    if (r["mensaje"] != "OK") {
                        mensaje = r["mensaje"].toString()
                        return@launch
                    }
                }

                // 2) Resto del perfil: un endpoint por tipo de usuario
                val respuesta = when (usuarioActualizado.tipoUsuario) {
                    "ANFITRION" -> api.actualizarAnfitrion(
                        correo = usuarioActualizado.correo,
                        nombre = usuarioActualizado.nombre,
                        telefono = usuarioActualizado.telefono,
                        direccion = usuarioActualizado.direccion,
                        oficio = usuarioActualizado.oficio.orEmpty(),
                        sobreMi = usuarioActualizado.sobreMi,
                        lenguas = listaATexto(usuarioActualizado.lenguas),
                        genero = usuarioActualizado.genero
                    )
                    "EXPLORADOR" -> api.actualizarExplorador(
                        correo = usuarioActualizado.correo,
                        nombre = usuarioActualizado.nombre,
                        telefono = usuarioActualizado.telefono,
                        direccion = usuarioActualizado.direccion,
                        intereses = listaATexto(usuarioActualizado.intereses),
                        sobreMi = usuarioActualizado.sobreMi,
                        lenguas = listaATexto(usuarioActualizado.lenguas),
                        genero = usuarioActualizado.genero
                    )
                    // No hay endpoint para ADMIN (solo se pudo cambiar la contraseña, arriba)
                    else -> mapOf("mensaje" to if (cambioContrasena) "OK" else "ERROR: tipo sin endpoint")
                }

                mensaje = respuesta["mensaje"].toString()

                if (respuesta["mensaje"] == "OK" && actual?.correo == usuarioActualizado.correo) {
                    usuarioActivo = usuarioActualizado
                    sharedPrefManager.guardarDatos(
                        usuarioActualizado.correo,
                        usuarioActualizado.contraseña
                    )
                }
            } catch (e: Exception) {
                mensaje = "Error de conexión al actualizar"
            }
        }
    }

    suspend fun buscarPorCorreo(correo: String): Usuario? {
        return try {
            api.obtenerUsuarioPorCorreo(correo).comoUsuarioONull()
        } catch (e: Exception) {
            null
        }
    }

    fun login(
        correo: String,
        contrasena: String,
        rol: String,
        onResult: (Boolean) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val respuesta = api.loginUsuario(correo, contrasena)

                usuarioActivo = respuesta.usuarioDeLogin()
                val exito = usuarioActivo != null

                if (exito && usuarioActivo?.tipoUsuario == rol) {
                    sharedPrefManager.guardarDatos(correo, contrasena)
                } else if (exito && rol == "ANFITRION" && usuarioActivo?.tipoUsuario == "ADMIN") {
                    sharedPrefManager.guardarDatos(correo, contrasena)
                }
                onResult(exito)
            } catch (e: Exception) {
                mensaje = "Error de conexión al iniciar sesión"
                onResult(false)
            }
        }
    }

    fun cerrarSesion() {
        usuarioActivo = null
        sharedPrefManager.limpiarDatos()
    }
}