package com.metricinches.mongo

import com.mongodb._

  /**
   * Created with IntelliJ IDEA.
   * User: datak_000
   * Date: 6/27/13
   * Time: 7:32 PM
   * To change this template use File | Settings | File Templates.
   */
  class MongoClient(val host: String, val port: Int) {
    require(host != null, "You must  provide a hostname")
    private val underlying = new Mongo(host, port)

    def this() = this("127.0.0.1", 27017)

    def version = underlying.getVersion

    def dropDB(name:String) = underlying.dropDatabase(name);
    def createDB(name:String) = DB(underlying.getDB(name))
    def db(name:String) = DB(underlying.getDB(name));

  }
