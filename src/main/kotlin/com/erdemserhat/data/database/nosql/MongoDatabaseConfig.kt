package com.erdemserhat.data.database.nosql

import com.mongodb.kotlin.client.coroutine.MongoClient
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId


object MongoDatabaseConfig {

    lateinit var mongoDatabase: MongoDatabase


    fun init(connectionUrl:String, databaseName:String) {
        val mongoClient = MongoClient.create(connectionUrl)
        mongoDatabase = mongoClient.getDatabase(databaseName)

    }
}


