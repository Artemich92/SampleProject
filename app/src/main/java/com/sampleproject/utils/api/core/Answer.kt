package com.sampleproject.utils.api.core

import com.sampleproject.utils.api.core.Answer.Companion
import com.sampleproject.utils.api.core.Answer.Failure

@Suppress("unchecked_cast")
class Answer<out T>(val value: Any?) {

    val isSuccess: Boolean
        get() = value !is Failure

    val isFailure: Boolean
        get() = value is Failure

    fun getOrNull(): T? = when {
        isFailure -> null
        else -> value as T
    }

    fun errorOrNull(): Failure? = when (value) {
        is Failure -> value
        else -> null
    }

    override fun toString(): String =
        when (value) {
            is Failure -> value.toString()
            else -> "Success($value)"
        }

    companion object {
        fun <T> success(value: T): Answer<T> = Answer(value)

        fun <T> failure(ex: Throwable, code: ErrorCode, message: String = ""): Answer<T> =
            Answer(createFailure(ex, code, message))

        fun <T> failure(code: ErrorCode, message: String = ""): Answer<T> =
            Answer(createFailure(code, message))

        fun <T> failure(error: Failure?): Answer<T> = Answer(error)
    }

    class Failure(val exception: Throwable, val code: ErrorCode, val message: String) {
        override fun equals(other: Any?): Boolean = other is Failure && exception == other.exception
        override fun hashCode(): Int = exception.hashCode()
        override fun toString(): String = "Failure($exception) code(${code}) message(${message})"
    }
}

private fun createFailure(exception: Throwable, code: ErrorCode, message: String): Any =
    Failure(exception, code, message)

private fun createFailure(code: ErrorCode, message: String): Any = Failure(NoException(), code, message)

@Suppress("unchecked_cast")
inline fun <R, T> Answer<T>.fold(
    onSuccess: (value: T) -> R,
    onFailure: (exception: Failure) -> R
): R {
    return when (val error = errorOrNull()) {
        null -> onSuccess(value as T)
        else -> onFailure(error)
    }
}

@Suppress("unchecked_cast")
inline fun <R, T> Answer<T>.map(transform: (value: T) -> R): Answer<R> {
    return when {
        isSuccess -> Companion.success(transform(value as T))
        else -> Answer(value)
    }
}

inline fun <T> Answer<T>.onFailure(action: (error: Failure) -> Unit): Answer<T> {
    errorOrNull()?.let { action(it) }
    return this
}

@Suppress("unchecked_cast")
inline fun <T> Answer<T>.onSuccess(action: (value: T) -> Unit): Answer<T> {
    if (isSuccess) action(value as T)
    return this
}

@Suppress("unchecked_cast")
fun <T> Answer<T>.getOrThrow(): T {
    throwOnFailure()
    return value as T
}

fun Answer<*>.throwOnFailure() {
    if (value is Failure) throw value.exception
}

@Suppress("unchecked_cast")
inline fun <R, T : R> Answer<T>.getOrElse(onFailure: (error: Failure) -> R): R {
    return when (val exception = errorOrNull()) {
        null -> value as T
        else -> onFailure(exception)
    }
}

@Suppress("unchecked_cast")
fun <R, T : R> Answer<T>.getOrDefault(defaultValue: R): R {
    if (isFailure) return defaultValue
    return value as T
}

suspend fun <T> retry(count: Int, operation: suspend () -> Answer<T>): Answer<T> {
    check(count > 0)

    var lastAnswer: Answer<T>? = null
    repeat(count) {
        val result = operation()
        if (result.isSuccess) return result
        lastAnswer = result
    }
    return lastAnswer!!
}
