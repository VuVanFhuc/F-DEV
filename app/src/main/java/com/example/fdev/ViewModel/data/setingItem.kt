package com.example.fdev.ViewModel.data


import com.example.fdev.R
import com.example.fdev.model.Profile
import com.example.fdev.View.User.MainActivity.Router


val setingItem = listOf(
    Profile("Hóa đơn của tôi", "Đã có 10 đơn hàng", R.drawable.right_black, Router.CART.name),
    Profile("Cài đặt", "Thông báo, Mật khẩu, FAQ, Liên hệ,...", R.drawable.right_black, Router.SETTING.name),
    Profile("FAQ", "Help Center", R.drawable.right_black, Router.HELP.name),
    Profile("Contact Us", "Đang phát triển", R.drawable.right_black, null.toString()),
    Profile("Privacy & Terms", "Đang phát triển", R.drawable.right_black, null.toString())
)
