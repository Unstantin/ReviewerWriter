package com.example.reviewerwriter.ui.utils

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class CriteriaData(
    private val _criteriaDto: MutableStateFlow<List<String>> = MutableStateFlow(emptyList())
) {
    val criteriaDto: StateFlow<List<String>> = _criteriaDto.asStateFlow()
    fun addCriteria(criteria: String) {
        if (criteria !in _criteriaDto.value) {
            _criteriaDto.value = _criteriaDto.value + criteria
        }
    }
    fun removeCriteria(criteria: String) {
        _criteriaDto.value = _criteriaDto.value.filter { it != criteria }
    }
}