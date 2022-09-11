package com.colddelight.data.repository

import android.util.Log
import com.colddelight.data.local.dao.QuestionDao
import com.colddelight.data.local.entity.QuestionEntity
import com.colddelight.domain.model.DomainQuestion
import com.colddelight.domain.repository.QuestionRepository

class QuestionRepositoryImpl(
    private val dao: QuestionDao
):QuestionRepository{
    override suspend fun getQuestion(id: Int): DomainQuestion{
        runCatching {
            dao.getQuestion(id).toDomainQuestion()
        }.onSuccess {
            //TODO 질문 테스트 데이터 삭제하기
//            val testData = DomainQuestion(
//                "최근에 새로 시작한게 있나요 있다면 무엇인가요 최근에 새로 시작한게 있나요 있다면 무엇인가요",
//                "나를 죽이지 못하는 고통은 날더 성장시킨다. 나를 죽이지 못하는 고통은 날더 성장시킨다. 나를 죽이지 못하는 고통은 날더 성장시킨다.",
//                "임마누엘 칸트 임마누엘 칸트",3)
//            Log.e("TAG", "질문 글자수 : ${testData.question.length}", )
//            Log.e("TAG", "글귀 글자수 : ${testData.quote.length}", )
//            Log.e("TAG", "작자 글자수 : ${testData.quoteAuthor.length}", )
//            return  testData
            return it
        }.onFailure {
        }.also {
            return DomainQuestion("무언가 잘못되었습니다", "", "", -1)
        }
    }

    override suspend fun getAllQuestion(): List<DomainQuestion> {
        return dao.getAllQuestion().map { it.toDomainQuestion() }
    }

}