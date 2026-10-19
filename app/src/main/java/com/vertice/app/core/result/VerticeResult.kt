package com.vertice.app.core.result


sealed interface VerticeResult<out T> {
    data class Ok<T>(val value: T) : VerticeResult<T>
    data class Err(val code: ErrorCode, val message: String, val cause: Throwable? = null) : VerticeResult<Nothing>
}

enum class ErrorCode {
    NETWORK,
    UNAUTHORIZED,
    VALIDATION,
    NOT_FOUND,
    CONFLICT,
    RATE_LIMITED,
    SERVER,
    UNKNOWN,
}

inline fun <T, R> VerticeResult<T>.map(transform: (T) -> R): VerticeResult<R> = when (this) {
    is VerticeResult.Ok -> VerticeResult.Ok(transform(value))
    is VerticeResult.Err -> this
}

inline fun <T> VerticeResult<T>.onOk(block: (T) -> Unit): VerticeResult<T> {
    if (this is VerticeResult.Ok) block(value)
    return this
}

inline fun <T> VerticeResult<T>.onErr(block: (VerticeResult.Err) -> Unit): VerticeResult<T> {
    if (this is VerticeResult.Err) block(this)
    return this
}

fun <T> VerticeResult<T>.valueOrNull(): T? = (this as? VerticeResult.Ok)?.value
