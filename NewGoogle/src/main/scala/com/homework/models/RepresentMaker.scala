package com.homework.models

import java.util.regex.Pattern

import scala.collection.Set

class RepresentMaker()
{
  final private val bias: Integer = 10
  val resourcePath: String = getClass.getResource("/crutch.txt").getPath
  //println(resourcePath.substring(0,resourcePath.length - 18))
  val indexer: Indexer = IndexerScalaTrait.getInstance(resourcePath.substring(0,resourcePath.length - 11))

  def getDocNames(searchText: String): Set[String] = {
    indexer.getDocNames(searchText)
  }

  def getTextInDocs(docNames: Set[String], searchText: String): Array[String] = {
    def split(text: String): List[String] = Pattern.compile("\\W+", Pattern.UNICODE_CHARACTER_CLASS).split(text).toList.map(_.toLowerCase)

    val searchWords: List[String] = split(searchText)
    //val docNames: Set[String] = indexer.getDocNames(searchText)

    makeRepresent(
      docNames.map(doc => {
        val text: String = indexer.getBook(doc)
        searchWords.map(word => {
          val (l,r) = substring(text, word)
          text.substring(l,r)
        })
      })
    )
  }

  private def substring(text: String, word: String): (Integer, Integer) = {
    //TODO: del
    var result = text.indexOf(" " + word + " ")
    if (result == -1) result = text.indexOf(" " + word + ",")
    if (result == -1) result = text.indexOf(" " + word + ".")
    if (result == -1) result = text.indexOf(word)
    (Math.max(0, result - bias), result + bias + word.length)
  }

  private def makeRepresent(parts: Set[List[String]]): Array[String] = {
    parts.map(texts =>
      texts.map("{" + _ + "}, ").foldLeft("")(_+_)
    ).toList.toArray
  }
}