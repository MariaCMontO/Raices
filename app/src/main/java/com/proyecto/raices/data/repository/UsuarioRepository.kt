package com.proyecto.raices.data.repository

import com.proyecto.raices.data.model.Usuario

object UsuarioRepository {

    var listaUsuarios = mutableListOf<Usuario>(
        Usuario(1,nombre="Anfitrión", telefono="123",correo="anfi@correo.com",contraseña="Anfi123",direccion="Calle 5 #14-98", intereses=emptyList(), oficio="", tipoUsuario = "ANFITRION",sobreMi="",lenguas=mutableListOf(),genero=""),
        Usuario(2,nombre="Explorador", telefono="123",correo="explo@correo.com",contraseña="Explo123",direccion="Calle 5 #14-98", intereses=emptyList(), oficio="", tipoUsuario ="EXPLORADOR",sobreMi="",lenguas=mutableListOf(),genero=""),

    )

}