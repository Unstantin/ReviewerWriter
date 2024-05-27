package com.example.reviewerwriter.data

import com.example.reviewerwriter.data.dto.GetTagDto
import com.example.reviewerwriter.data.network.RetrofitFactory
import com.example.reviewerwriter.domain.etites.CriteriaEntity
import com.example.reviewerwriter.domain.etites.SaveTagsEntity
import com.example.reviewerwriter.domain.etites.Status
import com.example.reviewerwriter.domain.etites.TagEntity
import com.example.reviewerwriter.domain.tagsUseCase.TagsRepository
import com.example.reviewerwriter.ui.utils.TokenStorage
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TagsRepositoryImpl(private val tokenStorage: TokenStorage) : TagsRepository {

    private val mainApi = RetrofitFactory.getMainApi()
    private val gson = Gson()
    override fun getTags(callback: (Status<SaveTagsEntity>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                if (tokenStorage.getToken() != null) {
                    val response = mainApi.getTags("Bearer ${tokenStorage.getToken()}")
                    if (response.isSuccessful) {
                        val jsonString =
                            response.body()?.toString() // Преобразование тела ответа в строку
                        val getTagDto = gson.fromJson(jsonString, GetTagDto::class.java)
                        // Создание списка для хранения тегов
                        val tagEntities = mutableListOf<TagEntity>()

                        // Проход по списку тегов и добавление не-null тегов в список tagEntities
                        getTagDto.tags?.forEach { tag ->
                            tag?.let {
                                val criteriaEntities = it.criteria?.mapNotNull { criteria ->
                                    criteria.name?.let { name ->
                                        CriteriaEntity(
                                            name = name,
                                            value = criteria.value
                                        )
                                    }
                                }
                                if (criteriaEntities != null && criteriaEntities.isNotEmpty()) {
                                    val tagEntity = TagEntity(
                                        name = it.name ?: "",
                                        criteria = criteriaEntities
                                    )

                                    tagEntities.add(tagEntity)
                                }
                            }
                        }

                        val saveTagsEntity = SaveTagsEntity(tags = tagEntities)

                        callback(Status(0, saveTagsEntity, null))
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

    override fun setTags(tagDto: SaveTagsEntity, callbaсk: (Status<Unit>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                if (tokenStorage.getToken() != null) {
                    val response = mainApi.setTags("Bearer ${tokenStorage.getToken()}", tagDto)
                    if (response.isSuccessful) {
                        callbaсk(Status(response.code(), null, null))
                    } else {
                        callbaсk(
                            Status(
                                response.code(),
                                null,
                                Exception(response.errorBody()?.string())
                            )
                        )
                    }
                }
                else{
                    callbaсk(Status(-1, null,Exception("Токен отсутствует")))
                }
            } catch (e: Exception) {
                callbaсk(Status(-1, null, e))
            }
        }
    }
}