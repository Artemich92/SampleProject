package com.sampleproject.utils.api

import com.sampleproject.utils.api.core.Answer
import com.sampleproject.utils.api.core.ErrorCode
import com.sampleproject.utils.api.core.ErrorCode.AllAttemptsUsedError
import com.sampleproject.utils.api.core.ErrorCode.AuthorizationError
import com.sampleproject.utils.api.core.ErrorCode.CPContainsMedicalConsultation
import com.sampleproject.utils.api.core.ErrorCode.EmptyResponseError
import com.sampleproject.utils.api.core.ErrorCode.ExternalError
import com.sampleproject.utils.api.core.ErrorCode.InternalError
import com.sampleproject.utils.api.core.ErrorCode.RecordNotFoundError
import com.sampleproject.utils.api.core.ServerErrorResponse
import com.sampleproject.utils.helpers.fromJson
import okhttp3.ResponseBody
import retrofit2.Response

abstract class BaseService {

    protected suspend fun <T : Any> apiCall(call: suspend () -> Response<T>): Answer<T> {
        val response: Response<T> = try {
            call.invoke()
        } catch (e: Exception) {
            return Answer.failure(ex = e, code = InternalError)
        }

        return if (response.isSuccessful) {
            if (response.body() == null) {
                Answer.failure(EmptyResponseError)
            } else {
                Answer.success(response.body()!!)
            }
        } else {
            Answer.failure(parseError(response.code(), response.errorBody()))
        }
    }

    private fun parseError(
        code: Int,
        body: ResponseBody?
    ): Answer.Failure {
        val response = body?.string().fromJson<ServerErrorResponse>()

        return if (response != null) {
            val message = response.error?.message ?: "No message"
            Answer.Failure(
                UnknownException,
                code.getErrorCode(),
                message
            )
        } else {
            Answer.Failure(
                UnknownException,
                ExternalError,
                ""
            )
        }
    }

    private fun Int.getErrorCode(): ErrorCode {
        return when (this) {
            401 -> AuthorizationError
            403 -> CPContainsMedicalConsultation
            404 -> RecordNotFoundError
            405 -> AllAttemptsUsedError
            422 -> ExternalError
            else -> ExternalError
        }
    }
}

object UnknownException : Exception()
