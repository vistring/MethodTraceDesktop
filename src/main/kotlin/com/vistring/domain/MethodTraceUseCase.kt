package com.vistring.domain

import com.vistring.model.MethodTraceGroupInfoDto
import com.vistring.network.AppApi
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

interface MethodTraceUseCase {

    companion object {

        const val PAGE_SIZE = 10

    }

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

class MethodTraceUseCaseImpl : MethodTraceUseCase {

    private val scope = MainScope()

    override val currentPageState = MutableStateFlow(value = 1)

    override val currentPageListState = MutableStateFlow<List<MethodTraceGroupInfoDto>>(value = emptyList())

    override fun refresh() {
        scope.launch {
            runCatching {
                currentPageState.emit(value = 1)
                currentPageListState.emit(
                    value = AppApi.requestGroupInfo(
                        pageNumber = 1,
                        pageSize = MethodTraceUseCase.PAGE_SIZE,
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
                    pageNumber = nextPageNumber,
                    pageSize = MethodTraceUseCase.PAGE_SIZE,
                ),
            )
        }
    }

    override fun nextPage() {
        scope.launch {
            val nextPageNumber = currentPageState.first() + 1
            val newDataList = AppApi.requestGroupInfo(
                pageNumber = nextPageNumber,
                pageSize = MethodTraceUseCase.PAGE_SIZE,
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