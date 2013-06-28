package com.metricinches.mongo

import com.mongodb.DBObject

sealed trait QueryOption

case object NoOption extends QueryOption;

case class Sort(sorting: DBObject, anotherOption: QueryOption) extends QueryOption

case class Skip(number: Int, anotherOption: QueryOption) extends QueryOption

case class Limit(limit: Int, anotherOption: QueryOption) extends QueryOption

case class Query(q: DBObject, option: QueryOption = NoOption) {
 def sort(sorting:DBObject) = Query(q, Sort(sorting, option))
 def skip(skip:Int) = Query(q, Skip(skip, option))
 def limit(limit:Int) = Query(q, Limit(limit, option))
}

