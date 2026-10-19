package com.vertice.app.core.result

import com.vertice.app.data.remote.fake.FakeApiException
import kotlinx.coroutines.CancellationException
import java.io.IOException

suspend fun <T> runResult(block: suspend () -> T): VerticeResult<T> = try {
    VerticeResult.Ok(block())
} catch (e: CancellationException) {
    throw e
} catch (e: FakeApiException) {
    VerticeResult.Err(e.code.toErrorCode(), e.message, e)
} catch (e: IOException) {
    VerticeResult.Err(ErrorCode.NETWORK, "Sem conexão. Verifique sua internet e tente novamente.", e)
} catch (e: Exception) {
    VerticeResult.Err(ErrorCode.UNKNOWN, e.message ?: "Algo deu errado. Tente novamente.", e)
}

private fun String.toErrorCode(): ErrorCode = when (this) {
    "UNAUTHORIZED" -> ErrorCode.UNAUTHORIZED
    "VALIDATION" -> ErrorCode.VALIDATION
    "NOT_FOUND" -> ErrorCode.NOT_FOUND
    "CONFLICT" -> ErrorCode.CONFLICT
    "RATE_LIMITED" -> ErrorCode.RATE_LIMITED
    "SERVER" -> ErrorCode.SERVER
    else -> ErrorCode.UNKNOWN
}

fun <T> Result<T>.toVertice(): VerticeResult<T> = fold(
    onSuccess = { VerticeResult.Ok(it) },
    onFailure = { VerticeResult.Err(ErrorCode.UNKNOWN, it.message ?: "Erro desconhecido", it) },
)

inline fun <T, R> VerticeResult<T>.fold(onOk: (T) -> R, onErr: (VerticeResult.Err) -> R): R = when (this) {
    is VerticeResult.Ok -> onOk(value)
    is VerticeResult.Err -> onErr(this)
}

fun <T> VerticeResult<T>.getOrNull(): T? = (this as? VerticeResult.Ok)?.value

fun VerticeResult.Err.toHumanMessage(): String = message
