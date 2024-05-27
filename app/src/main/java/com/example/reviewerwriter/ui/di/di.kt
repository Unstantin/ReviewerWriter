package com.example.reviewerwriter.ui.di

import com.example.reviewerwriter.data.AuthRepositoryImpl
import com.example.reviewerwriter.data.TagsRepositoryImpl
import com.example.reviewerwriter.domain.authUseCase.AuthRepository
import com.example.reviewerwriter.domain.authUseCase.LogUseCase
import com.example.reviewerwriter.domain.authUseCase.RegUseCase
import com.example.reviewerwriter.domain.tagsUseCase.GetTagsUseCase
import com.example.reviewerwriter.domain.tagsUseCase.SetTagsUseCase
import com.example.reviewerwriter.domain.tagsUseCase.TagsRepository
import com.example.reviewerwriter.ui.authScreen.LoginViewModel
import com.example.reviewerwriter.ui.authScreen.RegistrationViewModel
import com.example.reviewerwriter.ui.createScreen.ReviewCreatingViewModel
import com.example.reviewerwriter.ui.mainScreen.MainBottomNavViewModel
import com.example.reviewerwriter.ui.mainScreen.MainViewModel
import com.example.reviewerwriter.ui.serviceScreen.ServiceViewModel
import com.example.reviewerwriter.ui.serviceScreen.criteria.CriteriaViewModel
import com.example.reviewerwriter.ui.serviceScreen.tags.TagsViewModel
import com.example.reviewerwriter.ui.utils.TokenStorage
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    // Repository
    single<AuthRepository> { AuthRepositoryImpl(get()) }
    single<TagsRepository> { TagsRepositoryImpl(get()) }

    // UseCase
    factory { LogUseCase(get()) }
    factory { RegUseCase(get()) }
    factory { GetTagsUseCase(get()) }
    factory { SetTagsUseCase(get()) }

    // viewModel
    viewModel { LoginViewModel(get(), get()) }
    viewModel { RegistrationViewModel(get(), get()) }
    viewModel { MainBottomNavViewModel() }
    viewModel { MainViewModel() }
    viewModel { ReviewCreatingViewModel() }
    viewModel { ServiceViewModel() }
    viewModel { TagsViewModel(get()) }
    viewModel { CriteriaViewModel() }

    // Добавление TokenStorage в качестве singleton
    single { TokenStorage(get()) }
}
