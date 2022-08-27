package no.kindly.android.sytac.ui.country

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import no.kindly.android.sytac.domain.entitiy.CountryDetails
import no.kindly.android.sytac.domain.usecase.CountriesUseCase

/**
 * ViewModel is communicator between different [CountriesViewModel] of an Activity.
 */

class CountriesViewModel(private val countriesUseCase: CountriesUseCase) : ViewModel() {

    private val mutableStateFlow = MutableStateFlow<UIState>(UIState.LoadingState)

    val screenStates: StateFlow<UIState> = mutableStateFlow

    private val list = mutableListOf<CountryDetails>()

    init {
        getCountriesList()
    }

    private fun getCountriesList() =
        viewModelScope.launch {
            countriesUseCase.launch().fold(
                onSuccess = {
                    mutableStateFlow.value = UIState.ShowList(list = it)
                    if (list.isEmpty()) {
                        list.addAll(it)
                    }
                },
                onFailure = { mutableStateFlow.value = UIState.ErrorState }
            )
        }

    fun sort(sortParam: SortBy) {
        when (sortParam) {
            SortBy.ABC -> mutableStateFlow.value = UIState.ShowList(list = list.sortedBy { it.name })
            SortBy.NUMBER_ASC -> mutableStateFlow.value = UIState.ShowList(list = list.sortedBy { it.population })
            SortBy.NUMBER_DESC -> mutableStateFlow.value =
                UIState.ShowList(list = list.sortedBy { it.population }.reversed())
        }
    }

    enum class SortBy { ABC, NUMBER_ASC, NUMBER_DESC }
}
