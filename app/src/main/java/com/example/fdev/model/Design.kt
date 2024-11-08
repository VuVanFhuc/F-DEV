package com.example.fdev.model

// Model cho yêu cầu thêm mới sản phẩm (DesignRequest)
data class DesignRequest(
    val name: String,             // Tên sản phẩm
    val price: Double,            // Giá sản phẩm
    val description: String,      // Mô tả sản phẩm
    val imageUri: String,         // Đường dẫn ảnh (imageUri) dưới dạng String
    val type: String?             // Loại sản phẩm (không bắt buộc)
)

// Model cho phản hồi khi lấy danh sách sản phẩm (DesignResponse)
data class DesignResponse(
    val id: String,               // ID sản phẩm
    val name: String,             // Tên sản phẩm
    val price: Double,            // Giá sản phẩm
    val description: String,      // Mô tả sản phẩm
    val image: String,            // Đường dẫn ảnh sản phẩm
    val type: String?             // Loại sản phẩm
)
data class DesignResponseWrapper(
    val data: List<DesignResponse>  // Dữ liệu của thiết kế
)