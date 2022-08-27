package no.kindly.android.sytac.domain

sealed class Result<out T : Any> {

    data class Success<out T : Any>(val data: T) : Result<T>()
    data class Failure(val error: Error) : Result<Nothing>()

    fun fold(onSuccess: (T) -> Unit, onFailure: (Error) -> Unit) {
        when (this) {
            is Success -> onSuccess(data)
            is Failure -> onFailure(error)
        }
    }
}
