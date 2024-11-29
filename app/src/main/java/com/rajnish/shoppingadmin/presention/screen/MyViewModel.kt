package com.rajnish.shoppingadmin.presention.screen

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rajnish.shoppingadmin.common.ResultState
import com.rajnish.shoppingadmin.domain.model.BannerModels
import com.rajnish.shoppingadmin.domain.model.Category
import com.rajnish.shoppingadmin.domain.model.ProductDataModel
import com.rajnish.shoppingadmin.domain.repo.Repo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(
    private val repo: Repo
) : ViewModel() {


    private val _addCategoryState = MutableStateFlow(AddCategoryState())
    val addCategoryState = _addCategoryState.asStateFlow()

    private val _getCategoryState = MutableStateFlow(GetCategoryState())
    val getCategoryState = _getCategoryState.asStateFlow()

    private val _addProductState = MutableStateFlow(AddProductState())
    val addProductState = _addProductState.asStateFlow()

    private val _uploadProductImageState = MutableStateFlow(UploadProductImageState())
    val uploadProductImageState = _uploadProductImageState.asStateFlow()

    private val _uploadCategoryImageState = MutableStateFlow(UploadCategoryImageState())
    val uploadCategoryImageState = _uploadCategoryImageState.asStateFlow()

    private val _upLoadBannerImageState = MutableStateFlow(UpLoadBannerImageState())
    val upLoadBannerImageState = _upLoadBannerImageState.asStateFlow()

    private val _addBannerState = MutableStateFlow(AddBannerState())
    val addBannerState = _addBannerState.asStateFlow()


//    private val _getProductState = MutableStateFlow(GetProductState())
  //  val getProductState = _getProductState.asStateFlow()




    fun restImageProductUploadState() {
        _uploadProductImageState.value = UploadProductImageState()
    }

    fun resetAddCategoryState() {
       _addCategoryState.value = AddCategoryState()
    }
    fun resetAddUploadCategoryState() {
        _uploadCategoryImageState.value = UploadCategoryImageState()
    }
    fun resetUploadBannerState() {
        _upLoadBannerImageState.value = UpLoadBannerImageState()
    }



    fun addBannerImage(bannerModels: BannerModels){
        viewModelScope.launch {
            repo.addBanner(bannerModels).collectLatest {
                when(it){
                    is ResultState.Loading ->{
                        _addBannerState.value = AddBannerState(isLoading = true)
                    }
                    is ResultState.Success->{
                        _addBannerState.value = AddBannerState(isSuccess = it.data)
                    }
                    is ResultState.Error->{
                        _addBannerState.value = AddBannerState(isError =  it.message)
                    }

                }

            }
        }
    }


     fun uploadBannerImage(imageUri: Uri){
         viewModelScope.launch {
             repo.uploadBannerImage(imageUri = imageUri).collectLatest {
                 when(it){
                     is ResultState.Loading ->{
                         _upLoadBannerImageState.value = UpLoadBannerImageState(isLoading = true)
                     }
                     is ResultState.Success ->{
                         _upLoadBannerImageState.value = UpLoadBannerImageState(isSuccess = it.data)
                     }
                     is ResultState.Error ->{
                         _upLoadBannerImageState.value = UpLoadBannerImageState(isError = it.message)
                     }
                 }
             }
         }
     }

    fun uploadCategoryImage(imageUri: Uri) {
        viewModelScope.launch {
            repo.uploadCategoryImage(imageUri = imageUri).collectLatest {
                when (it) {
                    is ResultState.Loading -> {
                        _uploadCategoryImageState.value = UploadCategoryImageState(isLoading = true)
                    }
                    is ResultState.Success -> {
                        _uploadCategoryImageState.value =
                            UploadCategoryImageState(isSuccess = it.data)
                    }
                    is ResultState.Error -> {
                        _uploadCategoryImageState.value =
                            UploadCategoryImageState(isError = it.message)
                    }
                }

            }

        }

    }

    fun uploadProductImage(imageUri: Uri) {
        viewModelScope.launch {
            repo.uploadImage(image = imageUri).collectLatest {
                when (it) {
                    is ResultState.Loading -> {
                        _uploadProductImageState.value = UploadProductImageState(isLoading = true)
                    }
                    is ResultState.Success -> {
                        _uploadProductImageState.value =
                            UploadProductImageState(isSuccess = it.data)
                    }

                    is ResultState.Error -> {
                        _uploadProductImageState.value =
                            UploadProductImageState(isError = it.message)
                    }
                }
            }

        }

    }

    fun addCategory(category: Category) {
        viewModelScope.launch {
            repo.addCategory(category).collectLatest {
                when (it) {
                    is ResultState.Loading -> {
                        _addCategoryState.value = AddCategoryState(isLoading = true)
                    }

                    is ResultState.Success -> {
                        _addCategoryState.value = AddCategoryState(isSuccess = it.data)
                    }

                    is ResultState.Error -> {
                        _addCategoryState.value = AddCategoryState(isError = it.message)
                    }
                }
            }
        }
    }

    fun getCategories() {
        viewModelScope.launch {
            repo.getCategories().collectLatest {
                when (it) {
                    is ResultState.Loading -> {
                        _getCategoryState.value = GetCategoryState(isLoading = true)
                    }
                    is ResultState.Success -> {
                        _getCategoryState.value = GetCategoryState(isSuccess = it.data)
                    }
                    is ResultState.Error -> {
                        _getCategoryState.value = GetCategoryState(isError = it.message)
                    }
                }
            }
        }
    }

    fun resetAddProductState() {
        _addProductState.value = AddProductState()
    }

    fun addProduct(productDataModel: ProductDataModel) {
        viewModelScope.launch {
            repo.addProduct(productDataModel = productDataModel).collectLatest {

                when (it) {
                    is ResultState.Loading -> {
                        _addProductState.value = AddProductState(isLoading = true)
                    }

                    is ResultState.Success -> {
                        _addProductState.value = AddProductState(isSuccess = it.data)
                    }

                    is ResultState.Error -> {
                        _addProductState.value = AddProductState(isError = it.message)
                    }
                }
            }
        }
    }



}

data class AddCategoryState(
    val isLoading: Boolean = false,
    val isSuccess: String = "",
    //val data :String,
    val isError: String = ""

)


data class GetCategoryState(
    val isLoading: Boolean = false,
    val isSuccess: List<Category> = emptyList(),
    val isError: String = ""
)

data class AddProductState(
    val isLoading: Boolean = false,
    val isSuccess: String = "",
    val isError: String = ""
) {

}

data class UploadProductImageState(
    val isLoading: Boolean = false,
    val isSuccess: String = "",
    val isError: String = ""
)

data class GetProductState(
    val isLoading: Boolean = false,
    val isSuccess: List<ProductDataModel> = emptyList(),
    val isError: String = ""
)

data class UploadCategoryImageState(
    val isLoading: Boolean = false,
    val isSuccess: String = "",
    val isError: String = ""
)
data class UpLoadBannerImageState(
    val isLoading: Boolean = false,
    val isSuccess: String = "",
    val isError: String = ""
)

data class AddBannerState(
    val isLoading: Boolean = false,
    val isSuccess: String = "",
    val isError: String = ""
)




