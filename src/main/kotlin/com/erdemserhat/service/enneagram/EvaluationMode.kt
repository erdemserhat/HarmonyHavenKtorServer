package com.erdemserhat.service.enneagram

import com.erdemserhat.data.database.nosql.enneagram_type_descriptions.EnneagramTypeDescriptionCategory

enum class EvaluationMode(val mode: String) {
    BASIC("basic"),
    STANDARD("standard"),
    PROFESSIONAL("professional"),
}


fun EvaluationMode.toEnneagramTypeDescriptionCategory(): EnneagramTypeDescriptionCategory {
    return when(this){
        EvaluationMode.BASIC -> EnneagramTypeDescriptionCategory.BASIC
        EvaluationMode.STANDARD -> EnneagramTypeDescriptionCategory.STANDARD
        EvaluationMode.PROFESSIONAL -> EnneagramTypeDescriptionCategory.PROFESSIONAL
    }

}