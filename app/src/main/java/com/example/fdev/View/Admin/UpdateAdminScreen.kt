package com.example.fdev.View.Admin

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.fdev.R
import com.example.fdev.ViewModel.ProductAdminViewModel
import com.example.fdev.model.ProductAdminRequest

@Composable
fun UpdateProductScreenAdmin(

    productId: String,
    productName: String,
    productPrice: String,
    productDescription: String,
    productType: String,
    productAdminViewModel: ProductAdminViewModel, navController: NavHostController
) {
    var name by remember { mutableStateOf(productName) }
    var price by remember { mutableStateOf(productPrice) }
    var description by remember { mutableStateOf(productDescription) }
    var imageUrl by remember { mutableStateOf("") }
    val context= LocalContext.current
    Column(
        modifier = Modifier
            .padding(20.dp, top = 65.dp, end = 20.dp)
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painterResource(id = R.drawable.left_black),
                contentDescription = "Back",
                modifier = Modifier
                    .size(25.dp)
                    .clickable { navController.popBackStack() }, // Quay lại màn hình trước
                contentScale = ContentScale.FillBounds,
            )
            Text(
                text = "UPDATE PRODUCT",
                modifier = Modifier.align(Alignment.CenterVertically),
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Product Name
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Product") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Product Price
        OutlinedTextField(
            value = price,
            onValueChange = { price = it },
            label = { Text("Price") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Product Description
        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Describe") },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 4
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Product Image URL
        OutlinedTextField(
            value = imageUrl,
            onValueChange = { imageUrl = it },
            label = { Text("Image") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(52.dp))

        // Save Button
        Button(
            onClick = {
                // Xử lý khi bấm nút Save, gửi dữ liệu lên server
                val productRequest = ProductAdminRequest(
                    name = name,
                    price = price.toDoubleOrNull() ?: 0.0,
                    description = description,
                    image = imageUrl,
                    type = productType // Truyền loại sản phẩm
                )
                productAdminViewModel.updateProduct(productId, productRequest)
                navController.navigate("CONGRATSADMIN")
            },
            modifier = Modifier.size(290.dp, 50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xff242424)
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = "SAVE PRODUCT",
                fontSize = 16.sp
            )
        }
    }
}
