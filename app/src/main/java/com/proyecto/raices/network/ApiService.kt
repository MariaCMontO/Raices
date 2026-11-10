package com.proyecto.raices.network

import com.proyecto.raices.data.model.CantidadServiciosResponse
import com.proyecto.raices.data.model.CategoriaServicio
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

}