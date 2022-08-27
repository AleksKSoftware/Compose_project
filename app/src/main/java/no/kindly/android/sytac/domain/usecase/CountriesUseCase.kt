package no.kindly.android.sytac.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import no.kindly.android.sytac.data.store.PopulationStore
import no.kindly.android.sytac.domain.entitiy.CountryDetails

/**
 * The use case which managing of the population data and convert to [].
 */

class CountriesUseCase(
    dispatcher: CoroutineDispatcher,
    private val populationStore: PopulationStore,
) : BaseUseCase(dispatcher) {

    suspend fun launch() =
        wrapResult { populationStore.getPopulationAndCountries().map { CountryDetails(it.name, it.population) } }
}