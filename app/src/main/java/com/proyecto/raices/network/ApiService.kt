package com.proyecto.raices.network

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.proyecto.raices.data.model.CantidadServiciosResponse
import com.proyecto.raices.data.model.CategoriaServicio
import com.proyecto.raices.data.model.GrupoViaje
import com.proyecto.raices.data.model.MensajeGrupo
import com.proyecto.raices.data.model.Reserva
import com.proyecto.raices.data.model.ServicioEntity
import com.proyecto.raices.data.model.Usuario
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @GET("servicios-obtener-por-id.php")
    suspend fun obtenerPorId(
        @Query("id") id: Int
    ): ServicioEntity

    @GET("servicios-listar-todos.php")
    suspend fun obtenerTodosLosServicios(): List<ServicioEntity>

    @GET("servicios-obtener-por-nombre-nombreanf.php")
    suspend fun obtenerTodosPorNombreOAnfitrion(
        @Query("query") query: String
    ): List<ServicioEntity>

    @GET("servicios-obtener-por-categoria.php")
    suspend fun obtenerTodosPorCategoria(
        @Query("categoria") categoria: String
    ): List<ServicioEntity>

    @GET("servicios-obtener-por-texto-categoria.php")
    suspend fun obtenerTodosPorTextoYCategoria(
        @Query("query") query: String,
        @Query("categoria") categoria: String
    ): List<ServicioEntity>

    @GET("servicios-obtener-por-correo-anfi.php")
    suspend fun obtenerTodosPorCorreoAnfi(
        @Query("correo_anfitrion") correo_anfitrion: String,
    ): List<ServicioEntity>

    @GET("servicios-obtener-cantidad.php")
    suspend fun obtenerCantidadServicios(): CantidadServiciosResponse

    @FormUrlEncoded
    @POST("servicios-guardar.php")
    suspend fun registrarServicios(
        @Field("nombre") nombre:String,
        @Field("descripcion") descripcion:String,
        @Field("categoria") categoria: CategoriaServicio,
        @Field("nombre_anfitrion") nombreAnfitrion:String,
        @Field("correo_anfitrion") correoAnfitrion:String,
        @Field("precio") precio:Double
    ): Map<String,String>

    @FormUrlEncoded
    @POST("servicios-actualizar.php")
    suspend fun actualizarServicios(
        @Field("id") id:Int,
        @Field("nombre") nombre:String,
        @Field("descripcion") descripcion:String,
        @Field("categoria") categoria: CategoriaServicio,
        @Field("precio") precio:Double
    ): Map<String,String>

    @POST("servicios-insertar-lista.php")
    suspend fun insertarLista(
        @Body servicios: List<ServicioEntity>
    ): Map<String, String>

    @FormUrlEncoded
    @POST("servicios-eliminar.php")
    suspend fun eliminarPorId(
        @Field("id") id: Int
    ): Map<String, String>

    @FormUrlEncoded
    @POST("reservas-insertar.php")
    suspend fun insertarReserva(
        @Field("servicioId") servicioId: Int,
        @Field("nombreServicio") nombreServicio: String,
        @Field("categoria") categoria: String,
        @Field("nombreAnfitrion") nombreAnfitrion: String,
        @Field("correoExplorador") correoExplorador: String,
        @Field("fechaReserva") fechaReserva: Long,
        @Field("personas") personas: Int,
        @Field("precioUnitario") precioUnitario: Double,
        @Field("notas") notas: String
    ): Map<String, String>

    @GET("reservas-obtener-por-correo.php")
    suspend fun obtenerReservas(
        @Query("correo") correo: String
    ): List<Reserva>

    @FormUrlEncoded
    @POST("reservas-cancelar.php")
    suspend fun cancelarReserva(
        @Field("id") id: Int,
        @Field("correoExplorador") correoExplorador: String
    ): Map<String, String>

    @GET("grupos-mis.php")
    suspend fun obtenerMisGrupos(@Query("correo") correo: String): List<GrupoViaje>

    @GET("grupos-otros.php")
    suspend fun obtenerOtrosGrupos(): List<GrupoViaje>

    @GET("mensajes.php")
    suspend fun obtenerMensajes(@Query("grupoId") grupoId: Int): List<MensajeGrupo>

    @FormUrlEncoded
    @POST("grupo-crear.php")
    suspend fun crearGrupo(
        @Field("nombre") nombre: String,
        @Field("descripcion") descripcion: String,
        @Field("correoCreador") correoCreador: String
    ): Map<String, String>

    @FormUrlEncoded
    @POST("grupo-unirse.php")
    suspend fun unirseGrupo(
        @Field("grupoId") grupoId: Int,
        @Field("correo") correo: String
    ): Map<String, String>

    @FormUrlEncoded
    @POST("grupo-salir.php")
    suspend fun salirGrupo(
        @Field("grupoId") grupoId: Int,
        @Field("correo") correo: String
    ): Map<String, String>

    @FormUrlEncoded
    @POST("mensaje-enviar.php")
    suspend fun enviarMensaje(
        @Field("grupoId") grupoId: Int,
        @Field("correoAutor") correoAutor: String,
        @Field("nombreAutor") nombreAutor: String,
        @Field("generoAutor") generoAutor: String,
        @Field("texto") texto: String
    ): Map<String, String>

//    @FormUrlEncoded
//    @POST("usuarios-registro.php")
//    suspend fun registrarUsuario(
//        @Field("nombre") nombre: String,
//        @Field("telefono") telefono: String,
//        @Field("correo") correo: String,
//        @Field("contrasena") contrasena: String,
//        @Field("direccion") direccion: String,
//        @Field("intereses") intereses: List<String>?,
//        @Field("oficio") oficio: String?,
//        @Field("tipoUsuario") tipoUsuario: String,
//        @Field("sobreMi") sobreMi: String?,
//        @Field("lenguas") lenguas: List<String>?,
//        @Field("genero") genero: String,
//    ): Map<String, String>

    @FormUrlEncoded
    @POST("usuarios-login.php")
    suspend fun loginUsuario(
        @Field("correo") correo: String,
        @Field("contrasena") contrasena: String
    ): JsonObject

    @FormUrlEncoded
    @POST("usuarios-buscar-por-correo.php")
    suspend fun obtenerUsuarioPorCorreo(
        @Field("correo") correo: String
    ): JsonElement?

    @GET("usuarios-obtener-cantidad.php")
    suspend fun obtenerCantidadUsuarios(): CantidadServiciosResponse

    @FormUrlEncoded
    @POST("usuarios-registro.php")
    suspend fun registrarUsuario(
        @Field("nombre") nombre: String,
        @Field("telefono") telefono: String,
        @Field("correo") correo: String,
        @Field("contrasena") contrasena: String,
        @Field("direccion") direccion: String,
        @Field("tipoUsuario") tipoUsuario: String,
        @Field("oficio") oficio: String,
        @Field("sobreMi") sobreMi: String,
        @Field("lenguas") lenguas: String,
        @Field("genero") genero: String
    ): Map<String, String>

    @FormUrlEncoded
    @POST("usuarios-actualizar-anfitrion.php")
    suspend fun actualizarAnfitrion(
        @Field("correo") correo: String,
        @Field("nombre") nombre: String,
        @Field("telefono") telefono: String,
        @Field("direccion") direccion: String,
        @Field("oficio") oficio: String,
        @Field("sobreMi") sobreMi: String,
        @Field("lenguas") lenguas: String,
        @Field("genero") genero: String
    ): Map<String, String>

    @FormUrlEncoded
    @POST("usuarios-actualizar-explorador.php")
    suspend fun actualizarExplorador(
        @Field("correo") correo: String,
        @Field("nombre") nombre: String,
        @Field("telefono") telefono: String,
        @Field("direccion") direccion: String,
        @Field("intereses") intereses: String,
        @Field("sobreMi") sobreMi: String,
        @Field("lenguas") lenguas: String,
        @Field("genero") genero: String
    ): Map<String, String>

    @FormUrlEncoded
    @POST("usuarios-cambiar-contrasena.php")
    suspend fun cambiarContrasena(
        @Field("correo") correo: String,
        @Field("contrasenaActual") contrasenaActual: String,
        @Field("contrasenaNueva") contrasenaNueva: String
    ): Map<String, String>

    @FormUrlEncoded
    @POST("usuarios-eliminar.php")
    suspend fun eliminarUsuario(
        @Field("correo") correo: String
    ): Map<String, String>

}
