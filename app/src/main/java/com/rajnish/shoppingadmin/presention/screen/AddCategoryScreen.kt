package com.rajnish.shoppingadmin.presention.screen

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.rajnish.shoppingadmin.domain.model.BannerModels
import com.rajnish.shoppingadmin.domain.model.Category
import okhttp3.internal.threadName

@Composable
fun AddCategoryScreen(viewModel: MyViewModel = hiltViewModel()) {


    var categoryName by remember { mutableStateOf("") }
    var categoryBy by remember { mutableStateOf("") }
    var categoryImageUrl by rememberSaveable { mutableStateOf<Uri?>(null) }
    var categoryImage by remember { mutableStateOf("") }


    val state = viewModel.addCategoryState.collectAsState()

    val context = LocalContext.current


    var bannerImageUrl by rememberSaveable { mutableStateOf<Uri?>(null) }
    var bannerImage by remember { mutableStateOf("") }
    var bannerName by remember { mutableStateOf("") }

    val uploadCategoryImageState = viewModel.uploadCategoryImageState.collectAsState()


    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia()) { uri ->
            uri?.let {
                viewModel.uploadCategoryImage(it)
                categoryImageUrl = uri

            }
        }

    val uploadbannerImageState = viewModel.upLoadBannerImageState.collectAsState()

    val addBannerState = viewModel.addBannerState.collectAsState()


    val launcher2 =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia()) { uri ->
            uri?.let {
                viewModel.uploadBannerImage(uri)
                bannerImageUrl = uri
            }

        }

  //  val uploadImageState = viewModel.uploadCategoryImageState.collectAsState()


    when {
        state.value.isLoading || uploadCategoryImageState.value.isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }


        state.value.isError.isNotBlank() -> {
            Toast.makeText(context, state.value.isError, Toast.LENGTH_SHORT).show()
            Text(text = state.value.isError)
        }

        uploadCategoryImageState.value.isError.isNotBlank() -> {
            Toast.makeText(context, uploadCategoryImageState.value.isError, Toast.LENGTH_SHORT)
                .show()
            Text(text = uploadCategoryImageState.value.isError)
        }

        state.value.isSuccess.isNotBlank() -> {
            Toast.makeText(context, state.value.isSuccess, Toast.LENGTH_SHORT).show()
            categoryName = ""
            categoryBy = ""
            categoryImageUrl = null
            categoryImage = ""

            viewModel.resetAddCategoryState()
        }

        uploadCategoryImageState.value.isSuccess.isNotBlank() -> {
            categoryImage = uploadCategoryImageState.value.isSuccess
            Toast.makeText(context, uploadCategoryImageState.value.isSuccess, Toast.LENGTH_SHORT)
                .show()
            viewModel.resetAddUploadCategoryState()
        }
    }




    when {
        uploadbannerImageState.value.isLoading || addBannerState.value.isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        uploadbannerImageState.value.isError.isNotBlank() || addBannerState.value.isError.isNotBlank() -> {
            Toast.makeText(context, uploadbannerImageState.value.isError, Toast.LENGTH_SHORT).show()
            Text(text = uploadbannerImageState.value.isError)
        }

        addBannerState.value.isSuccess.isNotBlank() -> {
            Toast.makeText(context, addBannerState.value.isSuccess, Toast.LENGTH_SHORT).show()
            viewModel.resetAddUploadCategoryState()

        }

        uploadbannerImageState.value.isSuccess.isNotBlank() -> {
            bannerImage = uploadbannerImageState.value.isSuccess
            Toast.makeText(context, uploadbannerImageState.value.isSuccess, Toast.LENGTH_SHORT)
                .show()
            viewModel.resetUploadBannerState()

        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {


        if (categoryImageUrl != null) {
            AsyncImage(
                model = categoryImageUrl, contentDescription = "Product Image",
                modifier = Modifier
                    .fillMaxSize()
                    .height(150.dp)
                    .clip(shape = RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .height(150.dp)
                    .background(
                        color = androidx.compose.ui.graphics.Color.LightGray,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .clip(shape = RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.clickable {
                        launcher.launch(
                            PickVisualMediaRequest(
                                ActivityResultContracts.PickVisualMedia.ImageOnly
                            )
                        )
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Image Icon",
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Add a Photo")
                }
            }
        }


        Text(
            text = "Add New Category",
            style = androidx.compose.ui.text.TextStyle(),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = categoryName,
            onValueChange = { categoryName = it },
            label = { Text("Category Name") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = categoryBy,
            onValueChange = { categoryBy = it },
            label = { Text("Category By") },
            singleLine = true,
            modifier = Modifier.fillMaxSize()
        )
        Spacer(modifier = Modifier.height(16.dp))



        Button(
            onClick = {
                if (categoryName.isNotEmpty() && categoryBy.isNotEmpty()
                    ) {
                    val  catodaryitem = Category(
                        name = categoryName,
                        categoryBy = categoryBy,
                        imageUrl = categoryImage.takeIf { it.isNotEmpty() } ?: categoryImageUrl?.toString().orEmpty()
                    )
                  viewModel.addCategory(catodaryitem)
                } else {
                    Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
                }
            }
        ) {
            Text(text = "Add Category")
        }


        Spacer(modifier = Modifier.height(16.dp)) // Add spacing between elements

        Text(
            text = "Add Banner",
            style = androidx.compose.ui.text.TextStyle(),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (bannerImageUrl != null) {
            AsyncImage(
                model = bannerImageUrl,
                contentDescription = "Banner Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .clip(shape = RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .background(
                        color = androidx.compose.ui.graphics.Color.DarkGray,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .clip(shape = RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.clickable {
                        launcher2.launch(
                            PickVisualMediaRequest(
                                ActivityResultContracts.PickVisualMedia.ImageOnly
                            )
                        )
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Banner Image Icon",
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Add a Banner Photo")
                }
            }
        }


        OutlinedTextField(
            value = bannerName,
            onValueChange = { bannerName = it },
            label = { Text("Banner Name") },
            singleLine = true,
            modifier = Modifier.fillMaxSize()
        )

        Button(
            onClick = {
                if (bannerName.isNotEmpty()) {
                    val bannerItem = BannerModels (
                        name = bannerName,
                        imageUrl = bannerImage.takeIf { it.isNotEmpty() } ?: bannerImageUrl?.toString().orEmpty()
                    )
                    viewModel.addBannerImage(bannerItem)
                } else {
                    Toast.makeText(context, "Please fill all fields for the banner", Toast.LENGTH_SHORT).show()
                }
            }
        ) {
            Text(text = "Add Banner")
        }
    }
}
