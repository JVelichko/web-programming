package com.homework.models

class JsonPackage {
  private var action: String = null
  private var docsName: Array[String] = null
  private var textInDocs: Array[String] = null

  def this(action: String, docsName: Array[String], textInDocs: Array[String]) {
    this()
    this.action = action
    this.docsName = docsName
    this.textInDocs = textInDocs
  }

  def this(action: String) {
    this()
    this.action = action
  }
}
