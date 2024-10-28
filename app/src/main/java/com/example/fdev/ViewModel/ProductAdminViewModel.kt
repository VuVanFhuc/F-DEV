package com.example.fdev.viewmodel

import RetrofitService
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fdev.model.Product
import com.example.fdev.model.ProductAdminRequest
import com.example.fdev.model.ProductAdminResponse
import kotlinx.coroutines.launch

class ProductAdminViewModel : ViewModel() {
    val productResponse: MutableLiveData<ProductAdminResponse?> = MutableLiveData()
    val errorMessage: MutableLiveData<String> = MutableLiveData()
    val deleteSuccess: MutableLiveData<Boolean> = MutableLiveData()

    val products: MutableLiveData<List<Product>> = MutableLiveData(listOf())

    private val apiService = RetrofitService().fdevApiService

    fun addProduct(product: ProductAdminRequest) {
        viewModelScope.launch {
            try {
                val response = apiService.addProduct(product)
                if (response.isSuccessful && response.body() != null) {
                    productResponse.postValue(response.body())
                } else {
                    errorMessage.postValue("Error: ${response.message()}")
                }
            } catch (e: Exception) {
                errorMessage.postValue("Exception: ${e.localizedMessage}")
            }
        }
    }
//
    fun deleteProduct(productId: String) {
        viewModelScope.launch {
            try {
                val response = apiService.deleteProduct(productId)
                if (response.isSuccessful) {
                    deleteSuccess.postValue(true)
                    // Xóa sản phẩm khỏi danh sách hiện tại nếu API xóa thành công
                    products.value = products.value?.filterNot { it.id == productId }
                } else {
                    errorMessage.postValue("Error: ${response.message()}")
                }
            } catch (e: Exception) {
                errorMessage.postValue("Exception: ${e.localizedMessage}")
            }
        }
    }
}
