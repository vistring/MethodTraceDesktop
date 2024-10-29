package com.vistring.module.detail.domain

import com.vistring.model.MethodInfoDto
import com.vistring.network.AppApi
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

interface MethodTraceDetailUseCase {

    val methodInfoDetailState: StateFlow<MethodInfoDto?>

    // Hot State
    val stackTraceFormatStrState: Flow<String?>

    fun destroy()

}

class MethodTraceDetailUseCaseImpl(
    val appId: String,
    val methodFullNameMd5: String,
) : MethodTraceDetailUseCase {

    private val scope = MainScope()

    override val methodInfoDetailState = MutableStateFlow<MethodInfoDto?>(
        value = null,
    )

    override val stackTraceFormatStrState = methodInfoDetailState
        .map { methodInfo ->
            methodInfo
                ?.stackTraceStr
                ?.split(";")
                ?.joinToString(
                    separator = "\n",
                )
        }

    private fun loadMethodTraceDetail() {
        scope.launch {
            kotlin.runCatching {
                methodInfoDetailState.emit(
                    value = AppApi.requestDetailInfo(
                        appId = appId,
                        methodFullNameMd5 = methodFullNameMd5,
                    )
                )
            }
        }
    }

    override fun destroy() {
        scope.cancel()
    }

    init {
        loadMethodTraceDetail()
    }

}