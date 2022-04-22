package com.sampleproject.utils.analitics

sealed interface AnalyticMessage {
    //Эвенты авторизации
    object AnalyticsLogInEvent : AnalyticMessage
}
