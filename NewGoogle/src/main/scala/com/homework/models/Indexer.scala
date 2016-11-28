package com.homework.models

import java.io.{BufferedWriter, File, FileWriter}

import org.apache.log4j.{Level, FileAppender, SimpleLayout, Logger}

import scala.collection.{Set, mutable}
import scala.io.Source._
import java.util.regex.Pattern


class Indexer {
  private var booksIndex: mutable.HashMap[String, String] = _
  private var wordsIndex: mutable.HashMap[String, mutable.HashSet[String]] = _
  private val logger: Logger = Logger.getLogger(classOf[Indexer])


  private var hack: String = _

  def this(resourcePath: String) {
    this()
    hack = resourcePath.substring(0,resourcePath.length - 15) ++ "log.out"

    val layout: SimpleLayout = new SimpleLayout
    val appender: FileAppender = new FileAppender(layout, hack, false)
    logger.addAppender(appender)
    logger.setLevel(Level.DEBUG)


    booksIndex = new mutable.HashMap[String, String]
    wordsIndex = new mutable.HashMap[String, mutable.HashSet[String]]
    try getListOfFiles(resourcePath).foreach(initializeIndexer)

  }

  private def split(text: String): List[String] = Pattern.compile("\\W+", Pattern.UNICODE_CHARACTER_CLASS).split(text).toList.map(_.toLowerCase)

  private def getListOfFiles(dir: String): List[File] = {
    val d = new File(dir)
    if (d.exists && d.isDirectory) {
      d.listFiles filter(_.isFile) toList
    } else {
      List[File]()
    }
  }

  private def initializeIndexer(document: File) {
    //TODO: Прикрутить логгер
    //File f = new File("1.txt");
    //Logger logger = Logger.getLogger(Indexer.class.getName());
    //logger.info(f.getAbsolutePath());
    val documentName: String = document.getName
    val documentText: String = fromFile(document.getAbsolutePath).getLines.mkString
    val words: List[String] = split(documentText)

    words.foreach(word => {
        booksIndex.put(documentName, documentText)
        if (!wordsIndex.contains(word)) {
          wordsIndex.put(word, new mutable.HashSet[String])
        }
        wordsIndex(word) += documentName
      })
  }

  def getDocNames(searchText: String): Set[String] = {


    val searchWords: List[String] = split(searchText)
    val currentDocs: Set[String] = booksIndex.keySet
    val temp = currentDocs.filter(book =>
      searchWords.forall(word =>
        wordsIndex.getOrElse(word, mutable.HashSet()).contains(book)))

    if(temp.nonEmpty){
      //val fw = new FileWriter(hack, true)

      logger.debug("someone was looking for: " + searchText + "\n")
      //fw.write("someone was looking for: " + searchText + "\n") ;
      //fw.close()
    }
    temp
  }

  def getBook(name: String) = booksIndex(name)
}