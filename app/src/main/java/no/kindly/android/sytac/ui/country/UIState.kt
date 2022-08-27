package no.kindly.android.sytac.ui.country

import no.kindly.android.sytac.domain.entitiy.CountryDetails

/**
 * UI states of the countries list
 */

sealed class UIState {
    object LoadingState : UIState()
    data class ShowList(val list: List<CountryDetails>) : UIState()
    object ErrorState : UIState()
}
