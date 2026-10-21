package com.proyecto.raices.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.proyecto.raices.R
import com.proyecto.raices.data.model.CategoriaServicio
import com.proyecto.raices.data.model.ServicioEntity

@Composable
fun ItemServicio(
    servicio: ServicioEntity,
    onClick: () -> Unit,
    tipoUsuario: String
) {
    val categoria = servicio.categoria
    val imagen = when (categoria) {
        CategoriaServicio.HISTORIA -> {
            R.drawable.historia
        }

        CategoriaServicio.MEDICINA -> {
            R.drawable.medicina
        }

        CategoriaServicio.ARTESANIAS -> {
            R.drawable.artesanias
        }

        CategoriaServicio.RECORRIDOS -> {
            R.drawable.recorridos
        }

        CategoriaServicio.AGRICULTURA -> {
            R.drawable.agricultura
        }

        else -> {
            R.drawable.gastronomia
        }
    }

    Card(
        modifier = Modifier.padding(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFFFFF)
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(bottom = 0.dp)
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = imagen),
                contentDescription = "Imagen Servicio",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = "Imagen",
                    style = MaterialTheme.typography.titleMedium,
                    fontSize = 13.sp
                )

                Text(
                    modifier = Modifier.padding(8.dp),
                    text = "Anfitrión: ${servicio.nombreAnfitrion}",
                    style = MaterialTheme.typography.titleMedium,
                    fontSize = 13.sp
                )
            }

            Text(
                modifier = Modifier
                    .padding(0.dp)
                    .padding(start = 8.dp),
                text = "Nombre: ${servicio.nombre}",
                style = MaterialTheme.typography.titleMedium,
                fontSize = 16.sp
            )
            Text(
                modifier = Modifier
                    .padding(0.dp)
                    .padding(start = 8.dp),
                text = "Categoría: ${servicio.categoria.tipo}",
                style = MaterialTheme.typography.titleMedium,
                fontSize = 13.sp
            )
            Text(
                modifier = Modifier
                    .padding(0.dp)
                    .padding(start = 8.dp),
                text = "Descripción: ${servicio.descripcion}",
                style = MaterialTheme.typography.titleMedium,
                fontSize = 13.sp
            )

            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = "$${servicio.precio} COP / pers.",
                    style = MaterialTheme.typography.titleMedium,
                    fontSize = 20.sp
                )
                Spacer(modifier = Modifier.width(12.dp))

                Button(
                    onClick = { onClick() },
                    modifier = Modifier
                        .width(110.dp)
                        .height(40.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color(0xFFA0522D) ,
                        contentColor = Color.White
                    )
                ) {
                    val texto = if (tipoUsuario == "EXPLORADOR") "Reservar" else "Ver"
                    Text(texto)
                }
            }
        }
    }
}