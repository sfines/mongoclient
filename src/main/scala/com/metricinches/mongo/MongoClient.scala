package com.metricinches.mongo{

import com.mongodb.{MongoClient => MClient}
import scala.collection.convert.Wrappers.JListWrapper

class MongoClient(val host: String, val port: Int) {
    require(host != null, "You must  provide a hostname")
    private val underlying = new MClient(host, port)

    def this() = this("127.0.0.1", 27017)

    def version = underlying.getVersion

    def dropDB(name:String) = underlying.dropDatabase(name)
    def createDB(name:String) = DB(underlying.getDB(name))
    def db(name:String) = DB(underlying.getDB(name))
    def close():Unit = underlying.close()
    def listDatabases() = for( name <- JListWrapper(underlying.getDatabaseNames)) yield name
  }
}
