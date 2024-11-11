package com.example.fdev.View.Design

import DesignViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType.Companion.Uri
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.fdev.model.DesignResponse
import com.example.fdev.R

@Composable
fun HomeDesignScreen(navController: NavHostController, viewModel: DesignViewModel = viewModel()) {
    val context = LocalContext.current
    val designs by viewModel.designResponse.observeAsState(emptyList())
    val errorMessage by viewModel.errorMessage.observeAsState("")

    // Fetch designs when the screen is first displayed
    LaunchedEffect(Unit) {
        viewModel.getDesigns()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        // Title Row (with search and profile icons)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { /* Handle Search */ }) {
                Icon(
                    painter = painterResource(id = R.drawable.search_anh),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp, 20.dp),
                    tint = Color(0xff808080)
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Make home",
                    style = TextStyle(fontSize = 15.sp, color = Color(0xff909090))
                )
                Text(
                    text = "DESIGN",
                    style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold)
                )
            }

            IconButton(onClick = { navController.navigate("PROFILE") }) {
                Icon(
                    painter = painterResource(id = R.drawable.person),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp, 20.dp),
                    tint = Color(0xff808080)
                )
            }
        }

        // Display Designs in a grid
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.weight(1f) // Ensure the grid takes available space
        ) {
            items(designs) { design ->
                DesignCard(design = design, navController = navController)
            }
        }
    }

    // Place the FloatingActionButton outside the Column to ensure it stays in the bottom right corner
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(50.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        FloatingActionButton(
            onClick = { navController.navigate("ADDDESIGN") },
            modifier = Modifier.size(60.dp),
            shape = CircleShape,
            containerColor = Color(0xFF7B1FA2),
            contentColor = Color.White
        ) {
            Icon(
                painter = painterResource(id = R.drawable.add),
                contentDescription = "Add",
                modifier = Modifier.size(24.dp)
            )
        }
    }
}
@Composable
fun DesignCard(design: DesignResponse, navController: NavHostController) {
    val imagePainter = rememberImagePainter(design.image)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(end = 25.dp, bottom = 15.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomEnd
        ) {
            Image(
                modifier = Modifier
                    .width(200.dp)
                    .height(250.dp)
                    .clip(shape = RoundedCornerShape(15.dp))
                    .clickable {
                        navController.currentBackStackEntry?.savedStateHandle?.set("design", design)
                        // Set design data in savedStateHandle and navigate to ProductDesigner
                        navController.navigate("PRODUCTDESIGNER")
                    },
                painter = imagePainter,
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
        }

        Text(
            text = design.name,
            modifier = Modifier.padding(top = 10.dp),
            fontSize = 15.sp,
            fontFamily = FontFamily.Serif,
            color = Color(0xff606060)
        )
        Text(
            text = "$" + design.price.toString(),
            modifier = Modifier.padding(top = 5.dp),
            fontSize = 15.sp,
            fontFamily = FontFamily.Serif,
            color = Color(0xff303030),
            fontWeight = FontWeight(700)
        )
    }
}
