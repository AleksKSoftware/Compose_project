package no.kindly.android.sytac.data.store

import android.content.Context
import no.kindly.android.sytac.R
import no.kindly.android.sytac.convertToPair
import no.kindly.android.sytac.data.entity.CountryDetailsResponse

/**
 * The class is responsible for the source of data
 */
class PopulationStore(private val ctx: Context) {

    private val list: List<CountryDetailsResponse>? = null

    fun getPopulationAndCountries(): List<CountryDetailsResponse> =
        if (list.isNullOrEmpty()) {
            val countries = convertToPair(R.raw.countries, ctx)
            convertToPair(R.raw.population, ctx).mapNotNull { populationMap ->
                countries.getOrDefault(populationMap.key, null)
                    ?.let {
                        CountryDetailsResponse(name = it,
                            population = populationMap.value.replace(",", "").toInt())
                    }
            }
        } else {
            list
        }

}

