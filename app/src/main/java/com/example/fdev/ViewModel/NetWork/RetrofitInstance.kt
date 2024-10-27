package com.example.fdev.ViewModel.NetWork


import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val api = Retrofit.Builder()
        .baseUrl("http://192.168.61.144:3001/") // Địa chỉ IP máy tính và cổng gốc
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(PaymentApiService::class.java)
}
