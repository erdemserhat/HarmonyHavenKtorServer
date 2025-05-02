package com.erdemserhat.data.database.nosql.enneagram_type_descriptions

import com.erdemserhat.data.database.nosql.enneagram_extra_type_description.EnneagramExtraTypeDescriptionCategory
import com.erdemserhat.data.database.nosql.enneagram_famous_people.EnneagramType
import com.erdemserhat.data.database.nosql.enneagram_question.EnneagramQuestionCategory

enum class EnneagramTypeDescriptionCategory(
    val category: String
) {
    BASIC("basic"),
    STANDARD("standard"),
    PROFESSIONAL("professional"),
}

fun EnneagramTypeDescriptionCategory.toEnneagramExtraTypeDescriptionCategory(): EnneagramExtraTypeDescriptionCategory {
    when(this){
        EnneagramTypeDescriptionCategory.BASIC -> return EnneagramExtraTypeDescriptionCategory.BASIC
        EnneagramTypeDescriptionCategory.STANDARD -> return EnneagramExtraTypeDescriptionCategory.STANDARD
        EnneagramTypeDescriptionCategory.PROFESSIONAL -> return EnneagramExtraTypeDescriptionCategory.PROFESSIONAL
        else -> return EnneagramExtraTypeDescriptionCategory.BASIC
    }
}
