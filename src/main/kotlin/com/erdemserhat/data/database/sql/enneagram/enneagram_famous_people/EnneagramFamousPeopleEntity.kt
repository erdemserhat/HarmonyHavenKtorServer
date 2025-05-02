package com.erdemserhat.data.database.sql.enneagram.enneagram_famous_people

import kotlinx.serialization.Serializable
import org.ktorm.entity.Entity
import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.text
import org.ktorm.schema.varchar

object DBEnneagramFamousPeopleTable : Table<DBEnneagramFamoutPeopleEntity>("enneagram_famous_people") {
    val id = int("id").primaryKey().bindTo { it.id }
    val name = varchar("name").bindTo { it.name }
    val enneagramType = int("enneagram_type").bindTo { it.enneagramType }
    val typeDescription = text("type_description").bindTo { it.typeDescription }
    val description = text("description").bindTo { it.description }
    val imageUrl = varchar("image_url").bindTo { it.imageUrl }




}

interface DBEnneagramFamoutPeopleEntity : Entity<DBEnneagramFamoutPeopleEntity> {
    companion object : Entity.Factory<DBEnneagramFamoutPeopleEntity>()
    val id: Int
    val name: String
    val enneagramType: Int
    val typeDescription: String
    val description: String
    val imageUrl: String

}


