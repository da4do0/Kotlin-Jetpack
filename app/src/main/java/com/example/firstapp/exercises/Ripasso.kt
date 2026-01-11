package com.example.firstapp.exercises

import androidx.annotation.Nullable
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlin.collections.listOf


data class Product(
    val id: Int,
    val name: String,
    val qtn: Int
)

@Composable
fun Ripasso(navController: NavController) {
    var name by remember { mutableStateOf("") }
    var qtn by remember { mutableStateOf(1) }

    var products by remember { mutableStateOf(listOf<Product>()) }
    var nextId by remember { mutableStateOf(1) }

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .background(Color(0xFF1E1E1E))
                .padding(paddingValues)
                .padding(4.dp)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                Text("Shopping List", color = Color.White, fontSize = 24.sp)
                Text("Totale: ${products.size} Prodotti", color = Color.White, fontSize = 20.sp)
            }
            LazyColumn(
                modifier = Modifier
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(
                    items = products,
                    key = { it.id }
                ) { product ->
                    FoodCard(product, onDelete = { id ->
                        products = products.filter { it.id != id }
                    })
                }
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    onValueChange = { name = it },
                    value = name,
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color(0xFF1E1E1E),
                        focusedContainerColor = Color(0xFF242424),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .weight(3f)
                        .border(1.dp, Color(0xFF404040), RoundedCornerShape(12.dp)),
                    placeholder = {
                        Text("Es: Pasta", color = Color.White, fontSize = 20.sp)
                    })
                Row(
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = {
                            if (qtn > 1) {
                                qtn -= 1;
                            }
                        },
                        modifier = Modifier
                            .width(36.dp)
                            .height(36.dp)
                            .padding(0.dp),
                        shape = RoundedCornerShape(6.dp),
                        contentPadding = PaddingValues(2.dp)
                    ) {
                        Text("-", fontSize = 28.sp)
                    }
                    Text(text = qtn.toString(), color = Color.White, fontSize = 20.sp)
                    Button(
                        onClick = {
                            qtn += 1;
                        },
                        modifier = Modifier
                            .width(36.dp)
                            .height(36.dp)
                            .padding(0.dp),
                        shape = RoundedCornerShape(6.dp),
                        contentPadding = PaddingValues(2.dp)
                    ) { Text("+", fontSize = 28.sp) }
                }
                Button(
                    onClick = {
                        if (name.isNotBlank()) {
                            products = products + Product(nextId, name, qtn);
                            name = "";
                            qtn = 1;
                            nextId++;
                        }
                    },
                    shape = RoundedCornerShape(6.dp),
                    contentPadding = PaddingValues(2.dp)
                ) { Text("Add", fontSize = 18.sp) }
            }
        }
    }
}

@Composable
fun FoodCard(product: Product, onDelete: (Int) -> Unit) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF242424)
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = product.name, color = Color.White, fontSize = 20.sp)
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = product.qtn.toString(),
                    color = Color.White,
                    fontSize = 20.sp
                )
                Button(onClick = {
                    onDelete(product.id)
                }) {
                    Text("X")
                }
            }
        }
    }
}