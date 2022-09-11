package com.colddelight.data.repository

import android.util.Log
import com.colddelight.data.local.dao.QnADao
import com.colddelight.domain.model.DomainReQnA
import com.colddelight.domain.repository.QnARepository

class QnARepositoryImpl(private val dao: QnADao
): QnARepository {
    override suspend fun getQnA(id: Int, a_id: Int): DomainReQnA {
        val data = dao.getQnA(id).toDomainQnA()
        val data2 = data.a_id.filter { it == a_id }
        val index = data.a_id.indexOf(data2[0])
        return DomainReQnA(
            data.answer[index],
            data.question,
            data.date[index],
            data.quote,
            data.quoteAuthor,
            data.id,
            a_id
        )
    }

    override suspend fun getAllQnA(): List<DomainReQnA> {
        //Todo 수식 바꾸기
        //{val1:a,val2:[xx,xx]},
        //   {val1:b,val2:[yy,yy]}
        //].map{a=it[0]; it[1].map{[a,it}}.flat
        val data = dao.getAllQnA().map { it.toDomainQnA() }.filter { it.date.isNotEmpty() }
        val resList = arrayListOf<DomainReQnA>()
        data.forEach {
            it.answer.forEachIndexed{ i,v->
                resList.add(DomainReQnA(v,it.question,it.date[i],it.quote,it.quoteAuthor,it.id,it.a_id[i]))
            }
        }
        resList.sortByDescending {
            it.a_id
        }
        return resList
    }
}