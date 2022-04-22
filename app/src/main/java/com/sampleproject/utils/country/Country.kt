package com.sampleproject.utils.country

import com.sampleproject.R

enum class Country(
    val countryTag: String,
    val countryCode: String,
    val countryName: Int,
    val rawMask: String,
    val countryNameTranslations: List<String>
) {
    RUSSIA(
        "russia",
        "+ 7",
        R.string.country_russia,
        "(___) ___-__-__",
        listOf("Россия", "Russia")
    ),
    UKRAINE(
        "ukraine",
        "+ 380",
        R.string.country_ukraine,
        "(___) ___-___",
        listOf("Украина", "Ukraine")
    ),
    BELARUS(
        "belarus",
        "+ 375",
        R.string.country_belarus,
        "(__) ___ __-__",
        listOf("Беларусь", "Belarus")
    ),
    KAZAKHSTAN(
        "kazakhstan",
        "+ 7",
        R.string.country_kazakhstan,
        "(___) ___-__-__",
        listOf("Казахстан", "Kazakhstan")
    ),
    USA(
        "usa",
        "+ 1",
        R.string.country_usa,
        "(___) ___-____",
        listOf("США", "USA")
    ),
    NO_COUNTRY(
        "",
        "",
        0,
        "",
        listOf("")
    );
}
