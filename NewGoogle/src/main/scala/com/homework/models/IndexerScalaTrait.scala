package com.homework.models

object IndexerScalaTrait {
    @volatile
    private var instance: Indexer = _

    def getInstance(resourcePath: String): Indexer = {
      if (instance == null) {
        instance = new Indexer(resourcePath)
      }
      instance
    }
}
