package com.sampleproject.utils.helpers

import com.sampleproject.utils.country.Country
import com.sampleproject.utils.country.Country.BELARUS
import com.sampleproject.utils.country.Country.KAZAKHSTAN
import com.sampleproject.utils.country.Country.RUSSIA
import com.sampleproject.utils.country.Country.UKRAINE
import com.sampleproject.utils.country.Country.USA

fun getPhone(code: String?, phone: String?): Long {
    if (code.isNullOrBlank() || phone.isNullOrBlank()) return 0
    val codeNumber = code.filter { it.isDigit() }
    val phoneNumber = phone.filter { it.isDigit() }
    return (codeNumber + phoneNumber).toLong()
}

fun getCountryByName(countryName: String?): Country? {
    val country: Country? = when (countryName) {
        RUSSIA.countryTag -> RUSSIA
        UKRAINE.countryTag -> UKRAINE
        BELARUS.countryTag -> BELARUS
        KAZAKHSTAN.countryTag -> KAZAKHSTAN
        USA.countryTag -> USA
        else -> null
    }
    return country
}
