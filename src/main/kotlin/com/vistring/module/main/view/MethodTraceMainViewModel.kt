package com.vistring.module.main.view

import androidx.lifecycle.ViewModel
import com.vistring.module.main.domain.MethodTraceMainUseCase
import com.vistring.module.main.domain.MethodTraceMainUseCaseImpl

class MethodTraceMainViewModel(
    private val useCase: MethodTraceMainUseCase = MethodTraceMainUseCaseImpl()
): ViewModel(), MethodTraceMainUseCase by useCase {

    override fun onCleared() {
        super.onCleared()
        useCase.destroy()
    }

}