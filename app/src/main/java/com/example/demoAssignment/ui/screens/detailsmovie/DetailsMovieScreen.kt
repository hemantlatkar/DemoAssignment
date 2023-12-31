package com.example.demoassignment.presentation.ui.screens.detailsmovie

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.demoassignment.presentation.navigation.NavRoute
import com.example.demoassignment.presentation.ui.theme.TestTheme
import com.example.demoassignment.presentation.utils.APIConstants.MOVIEDB_BASE_IMAGE_URL
import com.google.accompanist.insets.navigationBarsHeight
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer

@Composable
fun DetailsMovieScreen(navController: NavController, movieId: String) {

    val mViewModel = hiltViewModel<DetailsMovieViewModel>()

    val scrollState = rememberScrollState()
    val movieDetails = mViewModel.movieDetails.collectAsState().value

    LaunchedEffect(key1 = Unit, block = {
        mViewModel.getMovieDetailsById(movieId = movieId.toInt())
    })

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(state = scrollState),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TopAppBar(
                    title = { Text(text = movieDetails.title) },
                    navigationIcon = if (navController.previousBackStackEntry != null) {
                        {
                            IconButton(onClick = { navController.navigateUp() }) {
                                Icon(
                                    imageVector = Icons.Filled.ArrowBack,
                                    contentDescription = "Back"
                                )
                            }
                        }
                    } else {
                        null
                    }

                )
                Card(
                    modifier = Modifier
                        .fillMaxHeight()
                        .aspectRatio(0.66f)
                        .padding(16.dp),
                    elevation = 10.dp,
                    backgroundColor = Color.White,
                    shape = MaterialTheme.shapes.medium
                ) {
                    Image(
                        painter = rememberImagePainter(
                            MOVIEDB_BASE_IMAGE_URL + movieDetails.poster_path
                        ),
                        contentDescription = "",
                        modifier = Modifier.fillMaxSize().size(250.dp).placeholder(
                            visible = false,
                            highlight = PlaceholderHighlight.shimmer(),
                            shape = TestTheme.shapes.small,
                            color = if (isSystemInDarkTheme()) Color.DarkGray else Color.LightGray
                        ),
                        contentScale = ContentScale.Crop
                    )
                }

                Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                    Text(
                        text = movieDetails.original_title,
                        modifier = Modifier.padding(vertical = 4.dp),
                        style = MaterialTheme.typography.h5,
                        color = colors.primaryVariant
                    )

                    Row(modifier = Modifier.padding(horizontal = 4.dp), horizontalArrangement = Arrangement.Start) {
                        Text(
                            text = "Type : ",
                            style = MaterialTheme.typography.body1,
                            color = colors.primaryVariant,
                            fontWeight = FontWeight.Bold
                        )
                        if(movieDetails.genreMS != null && movieDetails.genreMS.size > 0) {
                            Text(
                                text = movieDetails.genreMS.map { it.name }.toString().removePrefix("[")
                                    .removeSuffix("]"),
                                style = MaterialTheme.typography.body1,
                                color = colors.primaryVariant
                            )
                        } else if(movieDetails.genres != null && movieDetails.genres.size > 0) {
                            Text(
                                text = movieDetails.genres.map { it.name }.toString().removePrefix("[")
                                    .removeSuffix("]"),
                                style = MaterialTheme.typography.body1,
                                color = colors.primaryVariant
                            )
                        }
                    }
                    if (movieDetails.adult)
                        Row(modifier = Modifier.padding(horizontal = 4.dp), horizontalArrangement = Arrangement.Start) {
                        Text(
                            text = "Adult : ",
                            style = MaterialTheme.typography.body1,
                            color = colors.primaryVariant,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Yes",
                            style = MaterialTheme.typography.body1,
                            color = colors.primaryVariant
                        )
                    }

                    Row(modifier = Modifier.padding(horizontal = 4.dp), horizontalArrangement = Arrangement.Start) {
                        Text(
                            text = "Original Language : ",
                            style = MaterialTheme.typography.body1,
                            color = colors.primaryVariant,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = movieDetails.original_language,
                            style = MaterialTheme.typography.body1,
                            color = colors.primaryVariant
                        )
                    }

                        Row(modifier = Modifier.padding(horizontal = 4.dp), horizontalArrangement = Arrangement.Start) {
                            Text(
                                text = "Release Date : ",
                                style = MaterialTheme.typography.body1,
                                fontWeight = FontWeight.Bold,
                                color = colors.primaryVariant
                            )
                            Text(
                                text = "${movieDetails.release_date} (${movieDetails.status})",
                                style = MaterialTheme.typography.body1,
                                color = colors.primaryVariant
                            )
                        }

                            Row(modifier = Modifier.padding(horizontal = 4.dp), horizontalArrangement = Arrangement.Start) {
                                Text(
                                    text = "Rating : ",
                                    style = MaterialTheme.typography.body1,
                                    color = colors.primaryVariant,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "${movieDetails.vote_average.toInt()}/10",
                                    style = MaterialTheme.typography.body1,
                                    color = colors.primaryVariant
                                )
                            }

                    Text(
                        text = movieDetails.overview,
                        textAlign = TextAlign.Justify,
                        modifier = Modifier.padding(4.dp),
                        style = MaterialTheme.typography.body2,
                        color = colors.primaryVariant
                    )
                    Spacer(
                        modifier = Modifier
                            .navigationBarsHeight()
                            .fillMaxWidth()
                    )
                }

}
            })
}
