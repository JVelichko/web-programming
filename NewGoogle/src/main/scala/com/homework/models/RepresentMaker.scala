package com.homework.models

import java.util.regex.Pattern
import scala.collection.Set

class RepresentMaker()
{
  final private val bias: Integer = 10
  val resourcePath: String = "C:\\Users\\admin\\Git\\web-programming\\NewGoogle\\src\\main\\resources"
  val indexer: Indexer = IndexerScalaTrait.getInstance(resourcePath)

  def getDocNames(searchText: String): Array[String] = {
    indexer.getDocNames(searchText).toList.toArray
  }

  def getTextInDocs(searchText: String): Array[String] = {
    def split(text: String): List[String] = Pattern.compile("\\W+", Pattern.UNICODE_CHARACTER_CLASS).split(text).toList.map(_.toLowerCase)

    val searchWords: List[String] = split(searchText)
    val docNames: Set[String] = indexer.getDocNames(searchText)

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