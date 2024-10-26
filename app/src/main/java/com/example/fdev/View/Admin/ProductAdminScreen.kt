package com.example.fdev.View.Admin

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.viewpager.widget.ViewPager
import com.example.fdev.R
import kotlinx.coroutines.launch
import java.time.format.TextStyle


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProductDetailsAdmin(navController: NavController) {

    val coroutineScope = rememberCoroutineScope()
    val pageCount = 3
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { pageCount })
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF0F0F0))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.Bottom
        ) {
            Box(
                modifier = Modifier
                    .height(455.dp)
                    .width(349.dp)
            ) {
                ViewPager(state = pagerState, modifier = Modifier.matchParentSize(), navController)
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxHeight()
                        .offset(x = (-25).dp)
                ) {
                    Box(
                        modifier = Modifier
                            .padding(vertical = 20.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color.White)
                            .padding(5.dp)
                            .clickable {
                                navController.popBackStack()
                            }
                    ) {
                        Image(
                            painterResource(id = R.drawable.back),
                            contentDescription = "Back image",
                            modifier = Modifier.size(30.dp)
                        )
                    }
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(30.dp))
                            .background(Color.White)
                            .padding(10.dp)
                    ) {
                        LeftBarSelector(currentPageIteration = pagerState.currentPage, onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(it)
                            }

                        })
                    }
                }
            }
        }
        Column(modifier = Modifier.padding(20.dp)) {

            Text(
                text = "Minimal Stand",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "$50",
                    fontSize = 30.sp
                )
            }
            Row(
                modifier = Modifier
                    .padding(vertical = 15.dp)
                    .clickable {

                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.star1),
                    contentDescription = "Star",
                    modifier = Modifier.size(25.dp)
                )
                Text(
                    text = "4.5", style = androidx.compose.ui.text.TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight(700)
                    ),
                    modifier = Modifier.padding(horizontal = 15.dp)
                )

                Text(
                    text = "(50 reviews)",
                )
            }
            Text(
                text = "Minimal Stand is made of by natural wood. The design that is very simple and minimal. This is truly one of the best furnitures in any family for now. With 3 different colors, you can easily select the best match for your home. ",
                maxLines = 3,
                fontSize = 14.sp,
                textAlign = TextAlign.Justify
            )
        }

        FooterDetails(
            isFavorite = true,
            onMarkPress = {
            },
            onAddToCartPress = {}, navController)
    }
}


@Composable
fun FooterDetails(isFavorite: Boolean, onMarkPress: () -> Unit, onAddToCartPress: () -> Unit, navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .offset(x = 0.dp, y = 0.dp)
            .padding(horizontal = 10.dp)
    ) {
        val color = if (isFavorite) Color.White else Color.White
        Box(
            modifier = Modifier
                .padding(start = 10.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Color.Black)
                .weight(1f)
                .height(60.dp)
                .clickable {

                },
            contentAlignment = Alignment.Center

        ) {
            Text(
                text = "Delete to cart",
                color = Color.White,
                fontSize = 20.sp
            )
        }
        Box(
            modifier = Modifier
                .padding(start = 10.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Color.Black)
                .weight(1f)
                .height(60.dp)
                .clickable {

                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Update to cart",
                color = Color.White,
                fontSize = 20.sp
            )
        }
    }
}



data class ProductColor(
    val backgroundColor: Color,
)

@Composable
fun LeftBarSelector(currentPageIteration: Int, onClick: (index: Int) -> Unit) {
    val productColorFirst = ProductColor(Color.White)
    val productColorSecond = ProductColor(Color(0xFFB4916C))
    val productColorThird = ProductColor(Color(0xFFE9CAA9))
    val listColor = listOf(productColorFirst, productColorSecond, productColorThird)
    LazyColumn {
        itemsIndexed(listColor) { index, item ->
            val colorBorder =
                if (currentPageIteration == index) Color(0xFF909090) else Color(0xFFF0F0F0)

            Surface(
                border = BorderStroke(5.dp, colorBorder),
                shape = CircleShape,
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .clickable {
                        onClick(index)
                    }) {
                Box(
                    modifier = Modifier
                        .padding(3.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .size(30.dp)
                        .background(item.backgroundColor)
                )
            }
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ViewPager(
    state: PagerState,
    modifier: Modifier, navController: NavController
) {
    HorizontalPager(
        state = state,
        modifier = modifier
    ) {
        Image(
            painterResource(id = R.drawable.a_1),
            contentDescription = "Image pager",
            modifier = Modifier
                .clip(
                    RoundedCornerShape(bottomStart = 50.dp)
                )
                .fillMaxSize()
                .clickable { navController.popBackStack() },
            contentScale = ContentScale.FillBounds,
        )
    }
}



@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewPro() {
    var navController = rememberNavController()
    ProductDetailsAdmin(navController)
}