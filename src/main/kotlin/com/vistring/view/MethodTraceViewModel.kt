package com.vistring.view

import androidx.lifecycle.ViewModel
import com.vistring.domain.MethodTraceUseCase
import com.vistring.domain.MethodTraceUseCaseImpl

class MethodTraceViewModel(
    private val useCase: MethodTraceUseCase = MethodTraceUseCaseImpl()
): ViewModel(), MethodTraceUseCase by useCase {

    override fun onCleared() {
        super.onCleared()
        useCase.destroy()
    }

}