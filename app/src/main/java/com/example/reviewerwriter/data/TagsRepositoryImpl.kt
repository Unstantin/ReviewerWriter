package com.example.reviewerwriter.data

import com.example.reviewerwriter.data.network.RetrofitFactory
import com.example.reviewerwriter.domain.entites.CriteriaEntity
import com.example.reviewerwriter.domain.entites.SaveTagsEntity
import com.example.reviewerwriter.domain.entites.Status
import com.example.reviewerwriter.domain.entites.TagEntity
import com.example.reviewerwriter.domain.tagsUseCase.TagsRepository
import com.example.reviewerwriter.ui.utils.TokenStorage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TagsRepositoryImpl(private val tokenStorage: TokenStorage) : TagsRepository {

    private val mainApi = RetrofitFactory.getMainApi()
    override fun getTags(callback: (Status<SaveTagsEntity>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                if (tokenStorage.getToken() != null) {
                    val response = mainApi.getTags("Bearer ${tokenStorage.getToken()}")
                    if (response.isSuccessful) {
                        // Создание списка для хранения тегов
                        val tagEntities = arrayListOf<TagEntity>()
                        val tags = response.body()?.tags
                        tags?.forEach { tag ->
                            val criteria: ArrayList<CriteriaEntity> = arrayListOf()
                            tag.criteria?.forEach {
                                criteria.add(
                                    CriteriaEntity(
                                        name = it.name,
                                        value = it.value
                                    )
                                )
                            }
                            tagEntities.add(
                                TagEntity(
                                    name = tag.name!!,
                                    criteria = criteria
                                )
                            )
                        }
                        val saveTagsEntity = SaveTagsEntity(tags = tagEntities)

                        callback(Status(200, saveTagsEntity, null))
                    }
                    else {
                        // Возвращение ошибки, если ответ не успешен
                        callback(Status(-1, null, Exception(response.errorBody()?.string())))
                    }
                }
                else{
                    callback(Status(-1, null,Exception("Токен отсутствует")))
                }

            } catch (e: Exception) {
            callback(Status(-1, null, e))
            }
        }
    }

    override fun setTags(tagDto: SaveTagsEntity, callback: (Status<Unit>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                if (tokenStorage.getToken() != null) {
                    val response = mainApi.setTags("Bearer ${tokenStorage.getToken()}", tagDto)
                    if (response.isSuccessful) {
                        callback(Status(response.code(), null, null))
                    } else {
                        callback(
                            Status(
                                response.code(),
                                null,
                                Exception(response.errorBody()?.string())
                            )
                        )
                    }
                }
                else{
                    callback(Status(-1, null,Exception("Токен отсутствует")))
                }
            } catch (e: Exception) {
                callback(Status(-1, null, e))
            }
        }
    }
}