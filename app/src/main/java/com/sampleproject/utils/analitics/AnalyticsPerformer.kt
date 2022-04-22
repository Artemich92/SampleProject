package com.sampleproject.utils.analitics

interface AnalyticsPerformer {
    fun sendAnalyticsEvent(event: AnalyticEvent)
}
