package no.kindly.android.sytac.ui.di

import android.app.Application
import android.content.res.Resources
import kotlinx.coroutines.Dispatchers
import no.kindly.android.sytac.data.store.PopulationStore
import no.kindly.android.sytac.domain.usecase.CountriesUseCase
import no.kindly.android.sytac.ui.country.CountriesViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.bind
import org.koin.dsl.module

private val frameworkModule = module {
    single { Dispatchers.IO }
    single { androidContext().resources } bind Resources::class
}

private val dataSourceModule = module {
    single { PopulationStore(get()) }
}

private val useCaseModule = module {
    factory { CountriesUseCase(get(), get()) }
}

private val vmModule = module {
    viewModel { CountriesViewModel(get()) }
}

fun setupDependency(application: Application) {
    startKoin {
        androidContext(application)
        modules(
            frameworkModule,
            vmModule,
            dataSourceModule,
            useCaseModule
        )
    }
}
