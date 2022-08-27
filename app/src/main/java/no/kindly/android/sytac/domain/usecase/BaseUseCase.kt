package no.kindly.android.sytac.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

open class BaseUseCase(
    private val dispatcher: CoroutineDispatcher,
) {
    protected suspend fun <T : Any> wrapResult(block: suspend () -> T) = withContext(dispatcher) {
        try {
            Result.success(block.invoke())
        } catch (expected: Exception) {
            expected.printStackTrace()
            Result.failure(expected)
        }
    }
}
