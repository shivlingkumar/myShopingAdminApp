package com.rajnish.shoppingadmin.data.repoimal

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.rajnish.shoppingadmin.common.CATEGORY
import com.rajnish.shoppingadmin.common.ResultState
import com.rajnish.shoppingadmin.domain.model.BannerModels
import com.rajnish.shoppingadmin.domain.model.Category
import com.rajnish.shoppingadmin.domain.model.ProductDataModel
import com.rajnish.shoppingadmin.domain.repo.Repo
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RepoimPal @Inject constructor(
    private val FirebaseFirestore: FirebaseFirestore,
    private val FirebaseStorage: FirebaseStorage
) : Repo {
    override suspend fun addCategory(category: Category): Flow<ResultState<String>> = callbackFlow {
        trySend(ResultState.Loading)

        FirebaseFirestore.collection(CATEGORY)
            .add(category)
            .addOnSuccessListener { documentReference ->
                trySend(ResultState.Success("Category added successfully with ID: ${documentReference.id}"))
            }.addOnFailureListener { e ->
                trySend(ResultState.Error(e.message.toString()))
            }
        awaitClose {
            close()
        }

    }

    override suspend fun getCategories(): Flow<ResultState<List<Category>>> = callbackFlow {
        trySend(ResultState.Loading)
        FirebaseFirestore.collection(CATEGORY).get()
            .addOnSuccessListener { querySnapshot ->
                val categories = querySnapshot.documents.mapNotNull { document ->
                    document.toObject(Category::class.java)
                }
                trySend(ResultState.Success(categories))
            }.addOnFailureListener { e ->
                trySend(ResultState.Error(e.message.toString()))
            }
        awaitClose {
            close()
        }
    }


    override suspend fun addProduct(productDataModel: ProductDataModel): Flow<ResultState<String>> =
        callbackFlow {
            trySend(ResultState.Loading)

            FirebaseFirestore.collection("Products")
                .add(productDataModel)
                .addOnSuccessListener { documentReference ->
                    trySend(ResultState.Success("Product added successfully with ID: ${documentReference.id}"))

                    Log.d("testtag", "addProduct: ${productDataModel.name}")
                }.addOnFailureListener { e ->
                    trySend(ResultState.Error(e.message.toString()))
                    Log.e("RepoimPal", "addProduct: ${e.message}")
                }
            awaitClose {
                close()
            }
        }

    override suspend fun uploadImage(image: Uri): Flow<ResultState<String>> = callbackFlow {
        trySend(ResultState.Loading)

        FirebaseStorage.reference.child("Products/${System.currentTimeMillis()}")
            .putFile(image ?: Uri.EMPTY).addOnSuccessListener {
                it.storage.downloadUrl.addOnSuccessListener {
                    trySend(ResultState.Success(it.toString()))
                }
                if (it.error != null) {
                    trySend(ResultState.Error(it.error!!.message.toString()))
                }
            }
        //    .addOnFailureListener {
        //   trySend(ResultState.Error(it.message.toString()))
        //  }

        awaitClose {
            close()
        }
    }

    override suspend fun uploadCategoryImage(imageUri: Uri): Flow<ResultState<String>> = callbackFlow{
        trySend(ResultState.Loading)
        FirebaseStorage.reference.child("CategoryImage/${System.currentTimeMillis()}")
            .putFile(imageUri ?: Uri.EMPTY).addOnSuccessListener {
                it.storage.downloadUrl.addOnSuccessListener { imageUrl ->
                    trySend(ResultState.Success(imageUrl.toString()))

                }
                if (it.error != null) {
                    trySend(ResultState.Error(it.error!!.message.toString()))

                }

            }
        awaitClose {
            close()
        }

    }

    override suspend fun uploadBannerImage(imageUri: Uri): Flow<ResultState<String>> = callbackFlow {
        trySend(ResultState.Loading)

        FirebaseStorage.reference.child("BannerImage/${System.currentTimeMillis()}")
            .putFile(imageUri ?: Uri.EMPTY).addOnSuccessListener {
                it.storage.downloadUrl.addOnSuccessListener { imageUrl ->
                    trySend(ResultState.Success(imageUrl.toString()))
                }
                if (it.error != null) {
                    trySend(ResultState.Error(it.error!!.message.toString()))
                }
            }
        awaitClose {
            close()
        }
    }



    override suspend fun addBanner(bannerModels: BannerModels): Flow<ResultState<String>> = callbackFlow {

        trySend(ResultState.Loading)
        FirebaseFirestore.collection("Banner")
            .add(bannerModels).addOnSuccessListener {
                trySend(ResultState.Success("Banner added successfully"))
            }.addOnFailureListener{
                trySend(ResultState.Success("not add to banner"))
               trySend(ResultState.Error(it.message.toString()))

            }
        awaitClose{
            close()
        }
    }
}
