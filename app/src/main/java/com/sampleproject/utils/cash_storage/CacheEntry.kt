package com.sampleproject.utils.cash_storage

class CacheEntry<T>(
    val value: T
) {
    val createdAt = System.currentTimeMillis()
}
