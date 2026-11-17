package com.proyecto.raices.network

import com.proyecto.raices.data.model.CantidadServiciosResponse
import com.proyecto.raices.data.model.CategoriaServicio
import com.proyecto.raices.data.model.GrupoViaje
import com.proyecto.raices.data.model.MensajeGrupo
import com.proyecto.raices.data.model.Reserva
import com.proyecto.raices.data.model.ServicioEntity
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @GET("servicios-consultar-por-id.php")
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

    @GET("grupos_mis.php")
    suspend fun obtenerMisGrupos(@Query("correo") correo: String): List<GrupoViaje>

    @GET("grupos_otros.php")
    suspend fun obtenerOtrosGrupos(@Query("correo") correo: String): List<GrupoViaje>

    @GET("mensajes.php")
    suspend fun obtenerMensajes(@Query("grupo_id") grupoId: Int): List<MensajeGrupo>

    @FormUrlEncoded
    @POST("grupo_crear.php")
    suspend fun crearGrupo(
        @Field("nombre") nombre: String,
        @Field("descripcion") descripcion: String,
        @Field("correoCreador") correoCreador: String
    ): Map<String, String>

    @FormUrlEncoded
    @POST("grupo_unirse.php")
    suspend fun unirseGrupo(
        @Field("grupoId") grupoId: Int,
        @Field("correo") correo: String
    ): Map<String, String>

    @FormUrlEncoded
    @POST("grupo_salir.php")
    suspend fun salirGrupo(
        @Field("grupoId") grupoId: Int,
        @Field("correo") correo: String
    ): Map<String, String>

    @FormUrlEncoded
    @POST("mensaje_enviar.php")
    suspend fun enviarMensaje(
        @Field("grupoId") grupoId: Int,
        @Field("correoAutor") correoAutor: String,
        @Field("nombreAutor") nombreAutor: String,
        @Field("generoAutor") generoAutor: String,
        @Field("texto") texto: String
    ): Map<String, String>

}