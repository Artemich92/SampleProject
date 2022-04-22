package com.sampleproject.utils.analitics

interface AnalyticMessageHandler {
    fun handleAnalyticMessage(message: AnalyticMessage): AnalyticEvent?
}
