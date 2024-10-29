package com.example.fdev.ViewModel.NetWork

import com.example.fdev.model.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("/contact/get-list-Contact")
    suspend fun getContactList(): Response<List<ContactMailResponse>>

    @GET("/product/get-list-Product")
    suspend fun getProductList(): Response<List<ProductResponse>>

    @GET("/cart/get-list-cart")
    suspend fun getCart(@Query("userName") userName: String): Response<CartResponse>

    @GET("/favourite/get-list-favourite")
    suspend fun getFavourite(@Query("userName") userName: String): Response<FavouriteResponse>

    @GET("/product/search-product")
    suspend fun searchProduct(@Query("name") name: String): Response<List<ProductResponse>>

    @POST("/cart/add-to-cart")
    suspend fun addToCart(@Body request: AddToCartRequest): Response<CartResponse>

    // Thêm sản phẩm vào yêu thích
    @POST("/favourite/add-to-favourite")
    suspend fun addToFavourite(@Body request: AddToFavouriteRequest): Response<FavouriteResponse>

    // Gửi dữ liệu liên hệ mới (Contact) lên server
    @POST("/contact/post-list-Contact")
    suspend fun createContact(@Body contact: ContactMailRequest): Response<ContactMailResponse>

    @DELETE("/cart/remove-from-cart/{userName}/{productName}")
    suspend fun removeFromCart(
        @Path("userName") userName: String,
        @Path("productName") productName: String
    ): Response<CartResponse>

    // hiển thị comment
    @GET("/review/get-reviews/{productId}")
    suspend fun getReviews(@Path("productId") productId: String): Response<List<ReviewResponse>>

    @POST("/review/add-review")
    suspend fun addReview(@Body reviewRequest: ReviewRequest): Response<ReviewResponse>
    //===========DELETE===========

    @DELETE("/favourite/remove-from-favourite/{userName}/{productName}")
    suspend fun removeFromFavourite(
        @Path("userName") userName: String,
        @Path("productName") productName: String
    ): Response<FavouriteResponse>

    @POST("/cart/add-all-from-favourite")
    suspend fun addAllFromFavourite(@Body request: AddAllFromFavouriteRequest): Response<CartResponse>

    // Add Product Admin

    @POST("/product/add-product")
    suspend fun addProduct(@Body product: ProductAdminRequest): Response<ProductAdminResponse>

    @DELETE("/product/delete-product/{id}")
    suspend fun deleteProduct(@Path("id") id: String): Response<Void>

    // Cập nhật sản phẩm
    @PUT("/product/update-product/{id}")
    suspend fun updateProduct(
        @Path("id") id: String,
        @Body product: ProductAdminRequest
    ): Response<ProductAdminResponse>

    //    payment
    @POST("payment")
    fun addPayment(@Body payment: PaymentRequest): Call<PaymentResponse>

    @GET("payment/{userId}")
    fun getPayments(@Path("userId") userId: String): Call<PaymentResponse>
}

