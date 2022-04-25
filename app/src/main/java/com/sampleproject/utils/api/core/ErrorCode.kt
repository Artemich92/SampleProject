package com.sampleproject.utils.api.core

//Для разных проектов набор будет разным
enum class ErrorCode {
    AuthorizationError,
    RecordNotFoundError,
    AllAttemptsUsedError,
    InternalError,
    ExternalError,
    EmptyResponseError,
    CPContainsMedicalConsultation,
}
