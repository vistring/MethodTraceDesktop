package com.vistring.module.detail.view

import androidx.lifecycle.ViewModel
import com.vistring.module.detail.domain.MethodTraceDetailUseCase
import com.vistring.module.detail.domain.MethodTraceDetailUseCaseImpl

class MethodTraceDetailViewModel(
    val appId: String,
    val methodFullNameMd5: String,
    private val useCase: MethodTraceDetailUseCase = MethodTraceDetailUseCaseImpl(
        appId = appId,
        methodFullNameMd5 = methodFullNameMd5,
    ),
) : ViewModel(), MethodTraceDetailUseCase by useCase {

    override fun onCleared() {
        super.onCleared()
        useCase.destroy()
    }

}