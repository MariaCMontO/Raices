package com.proyecto.raices.data.sharedPreferences

import android.content.Context

class SharedPreferencesManager(context: Context) {
    private val sharedPref = context.getSharedPreferences("credenciales", Context.MODE_PRIVATE)

    fun guardarDatos(usuario: String, pass: String) {
        val editor = sharedPref.edit()

        editor.putString("user", usuario)
        editor.putString("pass", pass)

        editor.apply()
    }

    fun cargarDatos(): Pair<String, String> {
        val user = sharedPref.getString("user", "No existe la información") ?: ""
        val pass = sharedPref.getString("pass", "No existe la información") ?: ""

        return Pair(user, pass)
    }

    fun limpiarDatos() {
        val editor = sharedPref.edit()

        editor.clear()
        editor.apply()
    }

}