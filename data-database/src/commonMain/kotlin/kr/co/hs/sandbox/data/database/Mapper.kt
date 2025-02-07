package kr.co.hs.sandbox.data.database

import kr.co.hs.sandbox.data.database.cache.Board
import kr.co.hs.sandbox.domain.entity.BoardEntity

object Mapper {
    fun Board.toDomain(): BoardEntity = object : BoardEntity {
        override val title: String = boardTitle
        override val contents: String = boardContents
    }
}