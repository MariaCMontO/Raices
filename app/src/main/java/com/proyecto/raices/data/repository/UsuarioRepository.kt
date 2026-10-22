package com.proyecto.raices.data.repository

import com.proyecto.raices.data.model.Usuario

object UsuarioRepository {

    var listaUsuarios = mutableListOf<Usuario>(
        Usuario("Anfitrión", "123","anfi@correo.com","Anfi123","Calle 5 #14-98", mutableListOf(), "", "ANFITRION","",mutableListOf(),""),
        Usuario("Explorador", "123","explo@correo.com","Explo123","Calle 5 #14-98", mutableListOf(), "", "EXPLORADOR","",mutableListOf(),""),

    )

}