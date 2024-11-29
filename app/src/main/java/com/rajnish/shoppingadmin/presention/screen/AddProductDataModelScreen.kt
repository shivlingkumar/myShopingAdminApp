
package com.rajnish.shoppingadmin.presention.screen

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.rajnish.shoppingadmin.domain.model.ProductDataModel
import javax.annotation.meta.When


@Composable
fun AddProductDataModelScreen(viewModel: MyViewModel = hiltViewModel()) {

    LaunchedEffect(key1 = true) {
        viewModel.getCategories()
    }

    val uploadProductImageState = viewModel.uploadProductImageState.collectAsState()
    val categoryState = viewModel.getCategoryState.collectAsState()
    val addProductState = viewModel.addProductState.collectAsState()

    val context = LocalContext.current

    // Form State
    var name by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var finalPrice by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var createBy by remember { mutableStateOf("") }
    var availableUnits by remember { mutableStateOf("") }
    var imageUrl by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    // Image Picker Launcher
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        uri?.let {
            viewModel.uploadProductImage(it)
            imageUri = it
        }
    }

    // Loading State

    when{
        addProductState.value.isLoading  || uploadProductImageState.value.isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
        }
    }



    // Handle Success/Error Toasts
     addProductState.value.isSuccess.isNotBlank() -> {
            Toast.makeText(context, addProductState.value.isSuccess, Toast.LENGTH_SHORT).show()
            viewModel.resetAddProductState()
            name = ""
            price = ""
            finalPrice = ""
            description = ""
            category = ""
            createBy = ""
            imageUri = null
            imageUrl = ""
            availableUnits = ""

         viewModel.resetAddProductState()
        }
        addProductState.value.isError.isNotBlank() ->{
            Toast.makeText(context, addProductState.value.isError, Toast.LENGTH_SHORT).show()
        }
        uploadProductImageState.value.isSuccess.isNotBlank() -> {
            imageUrl = uploadProductImageState.value.isSuccess
            Toast.makeText(context,uploadProductImageState.value.isSuccess, Toast.LENGTH_SHORT).show()
            viewModel.restImageProductUploadState()
        }
    }

    // Main UI
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
            ,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Image Section
        if (imageUri != null) {
            AsyncImage(
                model = imageUri,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(8.dp))
                    ,
                contentScale = ContentScale.Crop
            )
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .border(2.dp, Color.DarkGray),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable {
                            launcher.launch(
                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                            )
                        },

                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Image",
                        modifier = Modifier
                    )
                    Text(text = "Add Image")
                }
            }
        }
        Text(text = "Add Products",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.align(Alignment.Start)
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Form Fields
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Product Name") },
            modifier = Modifier.fillMaxWidth()
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = price,
                onValueChange = { price = it },
                label = { Text("Price") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f)
            )

            OutlinedTextField(
                value = finalPrice,
                onValueChange = { finalPrice = it },
                label = { Text("Final Price") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f)
            )
        }
        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = category,
            onValueChange = { category = it },
            label = { Text("Category") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = createBy,
            onValueChange = { createBy = it },
            label = { Text("Created By") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = availableUnits,
            onValueChange = { availableUnits = it },
            label = { Text("Available Units") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Submit Button
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                if (name.isNotEmpty() && price.isNotEmpty() && finalPrice.isNotEmpty() &&
                    description.isNotEmpty() && category.isNotEmpty() && createBy.isNotEmpty()
                ) {
                    Toast.makeText(context, "upload successfully", Toast.LENGTH_SHORT).show()
                    val product = ProductDataModel(
                        name = name,
                        price = price,
                        finalPrice = finalPrice,
                        description = description,
                        category = category,
                        imageUrl = imageUrl.takeIf { it.isNotEmpty() } ?: imageUri?.toString().orEmpty(),
                        createBy = createBy,
                        availableUnits = availableUnits.toIntOrNull() ?: 0,

                    )
                    viewModel.addProduct(product)
                } else {
                    Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
                }
            }
        ) {
            Text("Add Product")
        }
    }
}
