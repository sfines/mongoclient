package com.metricinches.mongo

import com.mongodb.{DBCollection => MongoDBCollection, DBCursor, DBObject}
  import scala.language.postfixOps

  class DBCollection( override val underlying: MongoDBCollection) extends ReadOnly

  trait ReadOnly {
    val underlying: MongoDBCollection
    def name = underlying.getName
    def fullName = underlying.getFullName

    def find(query:Query):DBCursor = {
      def applyOptions(cursor:DBCursor,option:QueryOption):DBCursor = {
        option match {
          case Skip(skip, next) => applyOptions( cursor.skip(skip), next)
          case Sort(sorting, next) => applyOptions( cursor.sort(sorting), next)
          case Limit(limit, next) => applyOptions( cursor.limit(limit), next)
          case NoOption => cursor
        }
      }
      applyOptions(find(query.q), query.option)
    }

    def find(doc: DBObject):DBCursor = underlying find doc
    def findOne( doc : DBObject) = underlying findOne doc
    def findOne = underlying.findOne
    def getCount(doc: DBObject) = underlying getCount doc
  }

  trait Administrable extends ReadOnly{
    def drop {
      underlying.drop
    }

    def dropIndexes {
      underlying.dropIndexes
    }
  }

  trait Updatable extends ReadOnly{
   def -=(doc: DBObject): Unit = underlying remove doc
   def +=(doc: DBObject): Unit = underlying save doc
  }

  trait Memoizer extends ReadOnly{
    val history = scala.collection.mutable.Map[Int, DBObject]()
    override def findOne = {
      history getOrElseUpdate( -1, {super.findOne} )
    }

    override def findOne( doc: DBObject ) = {
      history getOrElseUpdate(doc.hashCode(), {super.findOne(doc)})
    }
  }

  trait LocaleAware extends ReadOnly{
    import java.util.Locale

    override def findOne( doc: DBObject)= {
      doc.put("locale", Locale.getDefault.getLanguage)
      super.findOne(doc)
    }

    override def find( doc: DBObject) = {
      doc.put("locale", Locale.getDefault.getLanguage)
      super.find(doc)
    }
  }

