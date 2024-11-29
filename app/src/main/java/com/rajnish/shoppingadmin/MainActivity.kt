package com.rajnish.shoppingadmin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rajnish.shoppingadmin.presention.screen.AddCategoryScreen
import com.rajnish.shoppingadmin.presention.screen.AddProductDataModelScreen
import com.rajnish.shoppingadmin.presention.screen.MyViewModel
import com.rajnish.shoppingadmin.ui.theme.ShoppingAdminTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShoppingAdminTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        MainScreen(viewModel)
                    }
                }
            }
        }
    }
}

@Composable
fun MainScreen(viewModel: MyViewModel) {
    var currentScreen by remember { mutableStateOf("Home") } // Manage the current screen state

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (currentScreen) {
                "Home" -> HomeScreen(
                    onAddCategoryClick = { currentScreen = "AddCategory" },
                    onAddProductClick = { currentScreen = "AddProduct" }
                )

                "AddCategory" -> AddCategoryScreen()
                "AddProduct" -> AddProductDataModelScreen()
            }
        }
    }
}

@Composable
fun HomeScreen(
    onAddCategoryClick: () -> Unit,
    onAddProductClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = onAddCategoryClick) {
            Text(text = "Add Category and Banner")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onAddProductClick) {
            Text(text = "Add Product Data Model")
        }
    }
}
