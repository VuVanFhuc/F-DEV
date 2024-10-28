import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.example.fdev.R

import com.example.fdev.model.Product
import com.example.fdev.view.downloadImage


@Composable
fun LayoutProductScreen(navController: NavHostController) {
    val cartViewModel : CartViewModel = viewModel()
    val favouriteViewModel : FavouriteViewModel = viewModel()
    val scrollState = rememberScrollState()
    val product = navController.previousBackStackEntry?.savedStateHandle?.get<Product>("product")
    val context = LocalContext.current


    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp) // Thêm padding cho toàn bộ Column
    ) {
        // Phần hiển thị hình ảnh sản phẩm và nút quay lại
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(455.dp)
                .padding(top = 30.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            product?.let {
                Image(
                    painter = rememberImagePainter(data = it.image),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(start = 16.dp) // Thay đổi padding để cải thiện khoảng cách
                        .fillMaxWidth() // Đảm bảo hình ảnh chiếm toàn bộ chiều rộng
                        .height(300.dp) // Giảm chiều cao để phù hợp hơn với bố cục
                        .clip(RoundedCornerShape(bottomStart = 50.dp)),
                    contentScale = ContentScale.Crop
                )
            }


            // Nút quay lại


        }


        // Phần hiển thị thông tin sản phẩm
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 20.dp)
        ) {
            product?.let {
                // Tên sản phẩm
                Text(
                    text = it.name,
                    fontWeight = FontWeight(500),
                    fontSize = 24.sp,
                    modifier = Modifier.padding(bottom = 4.dp) // Khoảng cách dưới
                )


                // Giá sản phẩm
                Text(
                    text = "$${it.price}",
                    fontSize = 30.sp,
                    fontWeight = FontWeight(700),
                    modifier = Modifier.padding(top = 10.dp, bottom = 25.dp) // Khoảng cách trên và dưới
                )


                // Đánh giá
                Row(
                    modifier = Modifier.padding(top = 25.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.sta),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = "4.5",
                        fontSize = 18.sp,
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight(700),
                        modifier = Modifier.padding(start = 7.dp)
                    )
                    Text(
                        text = "(50 reviews)",
                        fontSize = 14.sp,
                        fontFamily = FontFamily.Serif,
                        color = Color(0xff808080),
                        modifier = Modifier.padding(start = 10.dp)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = "Xem đánh giá",
                        fontSize = 17.sp,
                        modifier = Modifier
                            .padding(end = 1.dp)
                            .clickable { navController.navigate("REVIEW/${product?.id}") }
                    )
                    Image(
                        painterResource(id = R.drawable.right_black),
                        contentDescription = null,
                        modifier = Modifier
                            .size(20.dp)
                            .padding(end = 10.dp)
                            .clickable {navController.navigate("REVIEW") },
                        contentScale = ContentScale.FillBounds,
                    )

                }


                // Mô tả sản phẩm
                Text(
                    text = "${it.description}",
                    modifier = Modifier.padding(top = 20.dp),
                    textAlign = TextAlign.Justify,
                    fontSize = 17.sp,
                    color = Color(0xff606060),
                    lineHeight = 22.sp
                )
            }


            // Nút thêm vào giỏ hàng và nút yêu thích
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    onClick = {
                        product?.let {
                            favouriteViewModel.addToFavourite(it, quantity = 1)
                            Toast.makeText(context, "Favourite added to successfully", Toast.LENGTH_LONG).show()
                        } ?: run {
                            Toast.makeText(context, "Favourite not found", Toast.LENGTH_SHORT).show()
                        }/*TODO*/ },
                    modifier = Modifier
                        .size(60.dp)
                        .background(color = Color(0xffF0F0F0), shape = RoundedCornerShape(10.dp))
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.favourite),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                }


                Button(
                    onClick = {
                        product?.let {
                            cartViewModel.addToCart(it, quantity = 1) // Thay đổi để truyền sản phẩm
                            Toast.makeText(context, "Product added to cart successfully", Toast.LENGTH_LONG).show()
                        } ?: run {
                            Toast.makeText(context, "Product not found", Toast.LENGTH_SHORT).show()
                            if (it.price.toDouble() == 0.0) {
                                // In ra URL hình ảnh trong Logcat
                                Log.d("Download", "Downloading image from URL: ${it.image}")
                                if (checkAndRequestPermissions(context)) {
                                    downloadImage(context, it.image)
                                } else {
                                    Toast.makeText(context, "Permission denied", Toast.LENGTH_LONG).show()
                                }
                            } else {
                                // Logic thêm vào giỏ hàng
                                cartViewModel.addToCart(it)
                                Toast.makeText(context, "Product added to cart successfully", Toast.LENGTH_LONG).show()
                                navController.navigate("CART")
                            }
                        }
                    },
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                        .height(60.dp)
                        .shadow(elevation = 2.dp, shape = RoundedCornerShape(10.dp)),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xff242424))
                ) {
                    Text(
                        text = "Add to cart",
                        color = Color.White
                        text = if (product?.price?.toDouble() == 0.0) "Download" else "Add to cart",
                        fontFamily = FontFamily.Serif
                    )
                }
            }
        }
    }
}




@Composable
fun CustomRadioButton(
    selected: Boolean,
    onClick: () -> Unit,
    outerColor: Color,
    innerColor: Color
) {
    Box(
        modifier = Modifier
            .size(24.dp)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(20.dp)
                .background(color = outerColor, shape = CircleShape)
        )
        if (selected) {
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .background(color = innerColor, shape = CircleShape)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ProductScreen() {
    LayoutProductScreen(navController = rememberNavController())
}

