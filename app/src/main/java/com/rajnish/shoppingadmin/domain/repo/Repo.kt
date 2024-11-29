package com.rajnish.shoppingadmin.domain.repo

import android.net.Uri
import com.rajnish.shoppingadmin.common.ResultState
import com.rajnish.shoppingadmin.domain.model.BannerModels
import com.rajnish.shoppingadmin.domain.model.Category
import com.rajnish.shoppingadmin.domain.model.ProductDataModel
import kotlinx.coroutines.flow.Flow


interface Repo {
   suspend fun addCategory(category: Category): Flow<ResultState<String>>

   suspend fun getCategories(): Flow<ResultState<List<Category>>>

   suspend fun addProduct(productDataModel: ProductDataModel): Flow<ResultState<String>>

   suspend fun uploadImage(image:Uri):Flow<ResultState<String>>

   suspend fun uploadCategoryImage(imageUri: Uri): Flow<ResultState<String>>

   suspend fun uploadBannerImage(imageUri: Uri): Flow<ResultState<String>>

   suspend fun addBanner(bannerModels: BannerModels): Flow<ResultState<String>>



}