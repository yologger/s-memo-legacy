package com.yologger.simple_memo.data.mapper

import com.yologger.simple_memo.infrastructure.entity.MemoEntity
import com.yologger.simple_memo.presentation.model.Memo

class MemoEntityToMemoMapper {
    fun map(memoEntity: MemoEntity): Memo {
        return Memo(
                id = memoEntity.id,
                title = memoEntity.title,
                content = memoEntity.content,
                position = memoEntity.position
        )
    }
}
