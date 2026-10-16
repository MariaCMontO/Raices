package com.proyecto.raices.ui.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Label
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.proyecto.raices.R
import com.proyecto.raices.data.model.Usuario
import com.proyecto.raices.viewmodel.viewmodelUsuarios
import org.intellij.lang.annotations.JdkConstants


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroExplorador(navController: NavController, viewmodel: viewmodelUsuarios){

    var nombre by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var contraseña by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }
    var intereses by remember { mutableStateOf(emptyList<String>()) }

    Column(
        modifier=Modifier
            .background(color = Color(0xFFDDD6CD))
            .padding(25.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier=Modifier.fillMaxWidth(),
            colors = androidx.compose.material3.CardDefaults.cardColors(containerColor = Color(0xFFFFF8EF)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(
                modifier=Modifier.padding(16.dp)

            ) {
                Image(
                    painter = painterResource(id= R.drawable.logoraices),
                    contentDescription = "Logo raíces",
                    modifier=Modifier.align(Alignment.CenterHorizontally)

                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    modifier=Modifier.align(Alignment.CenterHorizontally),
                    text="Unirse Como Explorador",
                    fontSize = 21.sp,
                    fontWeight = FontWeight.W600
                )
                Spacer(modifier = Modifier.height(20.dp))
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            width = 2.dp,
                            color = Color(0xFF87736B),
                            shape = RoundedCornerShape(10.dp)
                        ),
                    shape = RoundedCornerShape(10.dp),
                    colors = androidx.compose.material3.CardDefaults.cardColors(containerColor = Color(0xFFFFF8EF))
                ){
                    Column(
                        modifier= Modifier.padding(20.dp, 35.dp)
                    ) {
                        Text(
                            fontSize = 16.sp,
                            text="Nombre Completo")
                        OutlinedTextField(
                            value=nombre,
                            label = {Text(text="Ingresa tu Nombre Completo", fontSize = 14.sp)},
                            onValueChange ={ nombre = it },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedBorderColor = Color(0xFF87736B),
                                unfocusedContainerColor = Color(0xFFFFFFFF)
                            )
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            fontSize = 16.sp,
                            text="Correo Electronico")
                        OutlinedTextField(
                            value=correo,
                            label = {Text(text="ejemplo@correo.com", fontSize = 14.sp)},
                            onValueChange ={ correo = it },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedBorderColor = Color(0xFF87736B),
                                unfocusedContainerColor = Color(0xFFFFFFFF)

                            )
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            fontSize = 16.sp,
                            text="Contraseña")
                        OutlinedTextField(
                            value=contraseña,
                            label = {Text(text="Crea una contraseña", fontSize = 14.sp)},
                            onValueChange ={ contraseña = it },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedBorderColor = Color(0xFF87736B),
                                unfocusedContainerColor = Color(0xFFFFFFFF)
                            )
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            fontSize = 16.sp,
                            text="Telefono")
                        OutlinedTextField(
                            value=telefono,
                            label = {Text(text="Telefono", fontSize = 14.sp)},
                            onValueChange ={ telefono = it },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedBorderColor = Color(0xFF87736B),
                                unfocusedContainerColor = Color(0xFFFFFFFF)
                            )
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            fontSize = 16.sp,
                            text="Direccion")
                        OutlinedTextField(
                            value=direccion,
                            label = {Text(text="Direccion", fontSize = 14.sp)},
                            onValueChange ={ direccion = it },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedBorderColor = Color(0xFF87736B),
                                unfocusedContainerColor = Color(0xFFFFFFFF)
                            )
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            fontSize = 16.sp,
                            text="Intereses")
                        InteresesChips(
                            intereses = intereses,
                            onInteresesChange = { intereses = it })
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            modifier=Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                            containerColor =   Color(0xFF823B18)
                            ),
                            onClick = {
                                val nuevoUsuario= Usuario(nombre,telefono,correo, contraseña,direccion,
                                    intereses as MutableList<String>?,"","EXPLORADOR")
                                viewmodel.agregarUsuario(nuevoUsuario)
                            }
                        ) {
                            Text("Comienza mi aventura ->")
                        }
                    }
                }
            }
            }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InteresesChips(intereses:List<String>, onInteresesChange: (List<String>) -> Unit) {
    val opciones = listOf("Deporte", "Música", "Tecnología", "Arte", "Viajes")

    FlowRow (

    ) {
        opciones.forEach { opcion ->
            val selected = intereses.contains(opcion)

            FilterChip(
                selected = selected,
                onClick = {
                    val nuevos = if (selected) intereses - opcion else intereses + opcion
                    onInteresesChange(nuevos)
                },
                label = { Text(opcion) },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = Color(0xFFE2E2E2),
                    containerColor = Color(0xFFFFF8EF),
                )
            )
        }
    }
}
