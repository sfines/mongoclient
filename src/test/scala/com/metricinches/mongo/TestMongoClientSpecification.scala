package com.metricinches.mongo
import org.specs2.mutable._
import org.specs2.matcher.Matchers._

class TestMongoClientSpecification extends Specification{
  "connect to mongo server" should {
    "allow a connection" in {
      val mongoDb = new MongoClient("localhost", 27017)
      mongoDb must not beNull

      mongoDb.version must be matching("2.*")
      for( name <- mongoDb.listDatabases()) println(name)
      mongoDb.close()
    }

    "allow a database to be created" in {
      val mongoDB = new MongoClient()
      val testDB = mongoDB.createDB("SpecsTestDB")
      testDB must not beNull

      val testCollection = testDB.readOnlyCollection("scalatestColl")
      testCollection must not beNull

    }
  }
}
