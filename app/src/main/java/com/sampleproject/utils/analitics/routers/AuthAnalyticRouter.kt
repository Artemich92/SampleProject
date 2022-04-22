package com.sampleproject.utils.analitics.routers

import com.sampleproject.utils.analitics.AUTH_BASE_ACTION
import com.sampleproject.utils.analitics.AUTH_CATEGORY
import com.sampleproject.utils.analitics.AnalyticEvent
import com.sampleproject.utils.analitics.AnalyticMessage
import com.sampleproject.utils.analitics.AnalyticMessage.AnalyticsLogInEvent
import com.sampleproject.utils.analitics.AnalyticMessageHandler

class AuthAnalyticRouter : AnalyticMessageHandler {

    override fun handleAnalyticMessage(message: AnalyticMessage): AnalyticEvent? {
        return when (message) {

            is AnalyticsLogInEvent -> AnalyticEvent(
                AUTH_CATEGORY,
                AUTH_BASE_ACTION,
                "Успешная авторизация пользователя"
            )

            //Пример эвента, с мапой
            /*is AnalyticsLogInEvent -> {
                AnalyticEvent(
                    AUTH_CATEGORY,
                    AUTH_BASE_ACTION,
                    mapOf(
                        "id" to message.id,
                        "ClickTime" to message.clickTime
                    )
                )
            }*/

            else -> return null
        }
    }
}
