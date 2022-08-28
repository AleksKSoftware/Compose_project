package no.kindly.android.sytac.ui.country.components

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import no.kindly.android.sytac.R
import no.kindly.android.sytac.domain.entitiy.CountryDetails
import no.kindly.android.sytac.ui.country.CountriesViewModel
import no.kindly.android.sytac.ui.country.UIState
import no.kindly.android.sytac.ui.theme.Blue200
import org.koin.androidx.compose.getViewModel


@Composable
fun ListScreen() {
    val viewModel = getViewModel<CountriesViewModel>()
    val uiState by viewModel.screenStates.collectAsState()
    Column(Modifier
        .fillMaxSize()
        .background(Blue200)) {
        Row {

            Button(
                onClick = { viewModel.sort(CountriesViewModel.SortBy.ABC) },
                modifier = Modifier
                    .wrapContentWidth()
                    .weight(1f)
            ) {
                Text(text = stringResource(id = R.string.sort_by_abc),
                    color = Color.White)
            }

            Button(
                onClick = { viewModel.sort(CountriesViewModel.SortBy.NUMBER_DESC) },
                modifier = Modifier
                    .wrapContentWidth()
                    .weight(1f)
            ) {

                Text(text = stringResource(id = R.string.sort_by_population_desc),
                    color = Color.White)
            }

            Button(
                onClick = { viewModel.sort(CountriesViewModel.SortBy.NUMBER_ASC) },
                modifier = Modifier
                    .wrapContentWidth()
                    .weight(1f)
            ) {

                Text(text = stringResource(id = R.string.sort_by_population_asc),
                    color = Color.White)
            }
        }
        when (uiState) {
            is UIState.LoadingState -> LoadingComponent()
            is UIState.ShowList -> {
                Column {
                    val listItems = (uiState as UIState.ShowList).list
                    Text(
                        text = stringResource(id = R.string.countries)+ listItems.size,
                        color = Color.White,
                        style = MaterialTheme.typography.caption,
                        fontSize = 18.sp,
                        modifier = Modifier
                            .padding(10.dp)
                    )
                    LazyColumn(
                        Modifier
                            .background(Blue200)
                            .fillMaxSize()
                            .weight(1f),
                        contentPadding = PaddingValues(end = 10.dp, start = 10.dp),
                    ) {
                        itemsIndexed(items = listItems, itemContent = { index, value ->
                            ItemCountry(
                                item = value, index = index + 1
                            )
                            Divider(color = Color.White, thickness = 1.dp)
                        }
                        )
                    }
                }
            }
            UIState.ErrorState -> Toast.makeText(LocalContext.current, R.string.error, Toast.LENGTH_SHORT).show()

        }
    }
}

@Composable
fun ItemCountry(
    item: CountryDetails,
    index: Int,
) {
    Row(
        Modifier
            .fillMaxWidth()
            .background(Blue200)
            .padding(
                top = 6.dp,
                bottom = 6.dp,

                )
    ) {
        Text(
            text = index.toString(),
            color = Color.White,
            style = MaterialTheme.typography.caption,
            fontSize = 14.sp,
            modifier = Modifier
                .padding(10.dp)
                .weight(1f)
        )
        Text(
            text = item.name,
            color = Color.White,
            style = MaterialTheme.typography.caption,
            fontSize = 14.sp,
            modifier = Modifier
                .padding(10.dp)
                .weight(2f)
        )
        Text(
            text = stringResource(id = R.string.population) + item.population.toString(),
            color = Color.White,
            style = MaterialTheme.typography.caption,
            fontSize = 14.sp,
            modifier = Modifier
                .padding(10.dp)
                .weight(2f)
        )

    }
}


@Composable
fun LoadingComponent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
    }
}

