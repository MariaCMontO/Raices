package com.proyecto.raices.data.repository

import com.proyecto.raices.data.model.Usuario

object UsuarioRepository {

    var listaUsuarios = mutableListOf<Usuario>(
        Usuario("Administrador", "123","admin@correo.com","Admin123","Calle 5 #14-98", mutableListOf(), "", "ADMIN","",mutableListOf(),"")
    )

}