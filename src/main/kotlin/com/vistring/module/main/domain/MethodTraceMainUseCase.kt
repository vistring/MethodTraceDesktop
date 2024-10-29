package com.vistring.module.main.domain

import com.vistring.model.MethodTraceGroupInfoDto
import com.vistring.network.AppApi
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

interface MethodTraceMainUseCase {

    companion object {

        const val PAGE_SIZE = 10
        const val APP_NAME_BLINK = "Blink"
        const val APP_NAME_MAGPIE = "Magpie"
        const val APP_NAME_VS_METHOD_TRACE_DEMO = "VSMethodTraceDemo"

    }

    val appSelectedState: StateFlow<String>

    val currentPageState: StateFlow<Int>

    val currentPageListState: StateFlow<List<MethodTraceGroupInfoDto>>

    /**
     * 刷新数据
     */
    fun refresh()

    /**
     * 上一页
     */
    fun prePage()

    /**
     * 下一页
     */
    fun nextPage()

    fun destroy()

}

class MethodTraceMainUseCaseImpl : MethodTraceMainUseCase {

    private val scope = MainScope()

    override val appSelectedState = MutableStateFlow(value = MethodTraceMainUseCase.APP_NAME_VS_METHOD_TRACE_DEMO)

    override val currentPageState = MutableStateFlow(value = 1)

    override val currentPageListState = MutableStateFlow<List<MethodTraceGroupInfoDto>>(value = emptyList())

    override fun refresh() {
        scope.launch {
            runCatching {
                currentPageState.emit(value = 1)
                currentPageListState.emit(
                    value = AppApi.requestGroupInfo(
                        appId = appSelectedState.first(),
                        pageNumber = 1,
                        pageSize = MethodTraceMainUseCase.PAGE_SIZE,
                    ),
                )
            }
        }
    }

    override fun prePage() {
        scope.launch {
            val nextPageNumber = (currentPageState.first() - 1).coerceAtLeast(minimumValue = 1)
            currentPageState.emit(
                value = nextPageNumber,
            )
            currentPageListState.emit(
                value = AppApi.requestGroupInfo(
                    appId = appSelectedState.first(),
                    pageNumber = nextPageNumber,
                    pageSize = MethodTraceMainUseCase.PAGE_SIZE,
                ),
            )
        }
    }

    override fun nextPage() {
        scope.launch {
            val nextPageNumber = currentPageState.first() + 1
            val newDataList = AppApi.requestGroupInfo(
                appId = appSelectedState.first(),
                pageNumber = nextPageNumber,
                pageSize = MethodTraceMainUseCase.PAGE_SIZE,
            )
            if (newDataList.isNotEmpty()) {
                currentPageListState.emit(
                    value = newDataList,
                )
                currentPageState.emit(
                    value = nextPageNumber,
                )
            }
        }
    }

    override fun destroy() {
        scope.cancel()
    }


    init {
        refresh()
    }

}