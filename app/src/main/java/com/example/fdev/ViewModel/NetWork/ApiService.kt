package com.example.fdev.ViewModel.NetWork


import com.example.fdev.model.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {
    @GET("/contact/get-list-Contact")
    suspend fun getContactList(): Response<List<ContactMailResponse>>


    @GET("/product/get-list-Product")
    suspend fun getProductList(): Response<List<ProductResponse>>


    @GET("/cart/get-list-cart")
    suspend fun getCart(@Query("userName") userName: String): Response<CartResponse>


    @GET("/product/search-product")
    suspend fun searchProduct(@Query("name") name: String): Response<List<ProductResponse>>


    @POST("/cart/add-to-cart")
    suspend fun addToCart(@Body request: AddToCartRequest): Response<CartResponse>

    @POST("/contact/post-list-Contact")
    suspend fun createContact(@Body contact: ContactMailRequest): Response<ContactMailResponse>

    @DELETE("/cart/remove-from-cart")
    suspend fun removeFromCart(@Query("userName") userName: String, @Query("productId") productId: String): Response<CartResponse>


    // hiển thị comment
    @GET("/review/get-reviews/{productId}")
    suspend fun getReviews(@Path("productId") productId: String): Response<List<ReviewResponse>>

    @POST("/review/add-review")
    suspend fun addReview(@Body reviewRequest: ReviewRequest): Response<ReviewResponse>

    // Add Product Admin

    @POST("/product/add-product")
    suspend fun addProduct(@Body product: ProductAdminRequest) : Response<ProductAdminResponse>

    @DELETE("/product/delete-product/{id}")
    suspend fun deleteProduct(@Path("id") id: String): Response<Void>

}
