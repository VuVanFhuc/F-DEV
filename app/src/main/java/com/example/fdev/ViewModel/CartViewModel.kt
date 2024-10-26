import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fdev.View.FavouriteItem
import com.example.fdev.model.AddAllFromFavouriteRequest
import com.example.fdev.model.AddToCartRequest
import com.example.fdev.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.google.firebase.auth.FirebaseAuth

class CartViewModel : ViewModel() {
    private val apiService = RetrofitService().fdevApiService
    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems
    private val _totalPrice = MutableLiveData<Double>()
    val totalPrice: LiveData<Double> = _totalPrice


    // Hàm lấy giỏ hàng từ MongoDB qua API
    fun getCartItems() {
        val currentUser = FirebaseAuth.getInstance().currentUser
        val userName = currentUser?.displayName ?: ""

        if (userName.isNotBlank()) {
            viewModelScope.launch {
                try {
                    val response = apiService.getCart(userName)
                    if (response.isSuccessful) {
                        response.body()?.let { cartResponse ->
                            _cartItems.value = cartResponse.data.products.mapNotNull { cartProduct ->
                                CartItem(
                                    product = cartProduct.product,
                                    name = cartProduct.name ?: "Unknown Product",
                                    price = cartProduct.price ?: 0.0,
                                    image = cartProduct.image ?: ""
                                )
                            }
                            updateTotalPrice() // Cập nhật tổng giá trị giỏ hàng sau khi lấy sản phẩm
                        }
                    } else {
                        Log.e("CartViewModel", "Error getting cart: ${response.code()} - ${response.message()}")
                    }
                } catch (e: Exception) {
                    Log.e("CartViewModel", "API call failed: ${e.message}", e)
                }
            }
        } else {
            Log.e("CartViewModel", "UserName is blank.")
        }
    }

    // Thêm tất cả sản phẩm yêu thích vào giỏ hàng
    fun addAllFavoritesToCart(favoriteItems: List<FavouriteItem>) {
        val currentUser  = FirebaseAuth.getInstance().currentUser
        val userName = currentUser ?.displayName ?: ""

        if (userName.isNotBlank()) {
            val request = AddAllFromFavouriteRequest(userName)

            viewModelScope.launch {
                try {
                    val response = apiService.addAllFromFavourite(request)
                    if (response.isSuccessful) {
                        getCartItems() // Gọi lại hàm lấy giỏ hàng để cập nhật
                    } else {
                        Log.e("CartViewModel", "Error adding all favorites to cart: ${response.code()} - ${response.message()}")
                    }
                } catch (e: Exception) {
                    Log.e("CartViewModel", "API call failed: ${e.message}", e)
                }
            }
        } else {
            Log.e("CartViewModel", "User Name is blank.")
        }
    }

    // Xóa sản phẩm khỏi giỏ hàng và cập nhật UI
    fun removeFromCart(productName: String): Boolean {
        val currentUser = FirebaseAuth.getInstance().currentUser
        val userName = currentUser?.displayName ?: ""

        if (userName.isNotBlank()) {
            viewModelScope.launch {
                try {
                    val response = apiService.removeFromCart(userName, productName)
                    if (response.isSuccessful) {
                        _cartItems.value = _cartItems.value.filter { it.name != productName }
                        updateTotalPrice() // Cập nhật tổng giá trị giỏ hàng
                    } else {
                        Log.e("CartViewModel", "Error removing from cart: ${response.code()} - ${response.message()}")
                    }
                } catch (e: Exception) {
                    Log.e("CartViewModel", "API call failed: ${e.message}", e)
                }
            }
            return true
        }
        return false
    }

    // Cập nhật tổng giá trị giỏ hàng
    fun updateTotalPrice() {
        _totalPrice.value = _cartItems.value.sumOf { it.price.toDouble() }
    }

    // Thêm sản phẩm vào giỏ hàng và cập nhật UI
    fun addToCart(product: Product, quantity: Int = 1) {
        val currentUser = FirebaseAuth.getInstance().currentUser
        val userName = currentUser?.displayName ?: ""

        if (userName.isNotBlank()) {
            viewModelScope.launch {
                try {
                    if (!product.id.isNullOrBlank()) {
                        val request = AddToCartRequest(userName, product.id, quantity)
                        val response = apiService.addToCart(request)
                        if (response.isSuccessful) {
                            _cartItems.value = _cartItems.value + CartItem(
                                product = product,
                                name = product.name,
                                price = product.price,
                                image = product.image
                            )
                            updateTotalPrice() // Cập nhật tổng giá trị giỏ hàng
                        } else {
                            Log.e("CartViewModel", "Error adding to cart: ${response.code()} - ${response.message()}")
                        }
                    } else {
                        Log.e("CartViewModel", "Product ID is null or blank.")
                    }
                } catch (e: Exception) {
                    Log.e("CartViewModel", "API call failed: ${e.message}", e)
                }
            }
        } else {
            Log.e("CartViewModel", "UserName is blank.")
        }
    }
}
