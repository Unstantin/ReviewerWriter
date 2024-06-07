package com.example.reviewerwriter.ui.di

import com.example.reviewerwriter.data.AuthRepositoryImpl
import com.example.reviewerwriter.data.ReviewRepositoryImpl
import com.example.reviewerwriter.data.TagsRepositoryImpl
import com.example.reviewerwriter.domain.authUseCase.AuthRepository
import com.example.reviewerwriter.domain.authUseCase.LogUseCase
import com.example.reviewerwriter.domain.authUseCase.RegUseCase
import com.example.reviewerwriter.domain.reviewsUseCase.AddReviewUseCase
import com.example.reviewerwriter.domain.reviewsUseCase.ReviewsRepository
import com.example.reviewerwriter.domain.tagsUseCase.GetTagsUseCase
import com.example.reviewerwriter.domain.tagsUseCase.SetTagsUseCase
import com.example.reviewerwriter.domain.tagsUseCase.TagsRepository
import com.example.reviewerwriter.ui.authScreen.LoginViewModel
import com.example.reviewerwriter.ui.authScreen.RegistrationViewModel
import com.example.reviewerwriter.ui.createScreen.ReviewCreatingViewModel
import com.example.reviewerwriter.ui.mainScreen.MainBottomNavViewModel
import com.example.reviewerwriter.ui.mainScreen.MainViewModel
import com.example.reviewerwriter.ui.mainScreen.reviewView.ReviewCardViewModel
import com.example.reviewerwriter.ui.serviceScreen.ServiceViewModel
import com.example.reviewerwriter.ui.serviceScreen.TagsCriteriaViewModel
import com.example.reviewerwriter.ui.serviceScreen.criteria.CriteriaViewModel
import com.example.reviewerwriter.ui.serviceScreen.tags.TagsViewModel
import com.example.reviewerwriter.ui.utils.CriteriaData
import com.example.reviewerwriter.ui.utils.TokenStorage
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    // Repository
    single<AuthRepository> { AuthRepositoryImpl(get()) }
    single<TagsRepository> { TagsRepositoryImpl(get()) }
    single<ReviewsRepository> {ReviewRepositoryImpl(get())}

    // UseCase
    factory { LogUseCase(get()) }
    factory { RegUseCase(get()) }
    factory { GetTagsUseCase(get()) }
    factory { SetTagsUseCase(get()) }
    factory { AddReviewUseCase(get()) }

    // viewModel
    viewModel { LoginViewModel(get(), get(), get()) }
    viewModel { RegistrationViewModel(get(), get()) }
    single { MainBottomNavViewModel() }
    viewModel { MainViewModel() }
    viewModel { ReviewCreatingViewModel(get()) }
    viewModel { ServiceViewModel() }
    viewModel { TagsViewModel(get(), get()) }
    viewModel { CriteriaViewModel(get()) }
    viewModel { ReviewCardViewModel()}
    single { TagsCriteriaViewModel(get(), get())}
    //TokenStorage
    single { TokenStorage(get()) }
    // CriteriaData как синглтон
    single { CriteriaData() }
}
