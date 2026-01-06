package com.example.firstapp.exercises

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.firstapp.R

data class Todo(val id: Int, val testo: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Todo(navController: NavHostController){
    var testoTemp by remember { mutableStateOf("") }
    var todos by remember { mutableStateOf(listOf<Todo>()) }
    var nextId by remember { mutableStateOf(1) }

    val buttonColor = if (testoTemp.isEmpty()) Color(0xFFB0B0B0) else Color(0xFF1E2732);

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Esercizio 1 - Todo App") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Text("Indietro")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF2196F3),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFF1E1E1E))
                .padding(8.dp)
        ) {

            LazyColumn(
                modifier = Modifier
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(
                    items = todos,
                    key = { it.id }
                ) { todo ->
                    Card(
                        modifier = Modifier.fillMaxWidth().background(Color(0xFF1A1A1A)),
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = todo.testo,
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .weight(1f),
                                fontSize = 16.sp
                            )
                            Button(
                                onClick = {
                                    todos = todos.filter { it.id != todo.id }
                                },
                                modifier = Modifier
                                    .padding(4.dp, 2.dp).fillMaxHeight(),
                                shape = RoundedCornerShape(0.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFF1DB954)
                                )
                            ) {
                                Text("✓")
                            }
                        }
                    }
                }
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.height(50.dp)
            ) {
                TextField(
                    onValueChange = { testoTemp = it },
                    value = testoTemp,
                    shape = RoundedCornerShape(12.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFFB0B0B0),
                        unfocusedContainerColor = Color(0xFFB0B0B0),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    placeholder = {
                        Text(
                            "Inserisci un nuovo",
                            color = Color.Black
                        )
                    })

                Button(
                    onClick = {
                        if (!testoTemp.isEmpty()) {
                            todos = todos + Todo(nextId, testoTemp);
                            nextId++;
                            testoTemp = ""
                        }
                    },
                    shape = RoundedCornerShape(6.dp),
                    modifier = Modifier
                        .padding(2.dp)
                        .fillMaxHeight(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = buttonColor
                    )
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.plus),
                        contentDescription = "Segno di spunta",
                        modifier = Modifier.size(24.dp),
                        tint = Color.LightGray
                    )
                }
            }
        }
    }
}