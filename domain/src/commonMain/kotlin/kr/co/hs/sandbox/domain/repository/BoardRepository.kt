package kr.co.hs.sandbox.domain.repository

import kotlinx.coroutines.flow.Flow
import kr.co.hs.sandbox.domain.entity.BoardEntity

interface BoardRepository {
    fun flowOfCreateBoardItem(entity: BoardEntity): Flow<BoardEntity>
    fun flowOfGetAllBoard(): Flow<BoardEntity>
    fun flowOfGetBoard(title: String): Flow<BoardEntity>
}