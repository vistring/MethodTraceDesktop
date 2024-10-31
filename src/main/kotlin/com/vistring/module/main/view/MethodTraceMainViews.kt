package com.vistring.module.main.view

import AppScreen
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.vistring.model.MethodTraceGroupInfoDto
import com.vistring.module.main.domain.MethodTraceMainUseCase

@Composable
fun MethodTraceGroupItemView(
    modifier: Modifier,
    item: MethodTraceGroupInfoDto,
) {
    Row(
        modifier = modifier,
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
    ) {
        Text(
            text = "(${item.count}) 主线程: ",
            style = MaterialTheme.typography.subtitle2,
            color = MaterialTheme.colors.onBackground.copy(
                alpha = 0.5f,
            ),
        )
        Text(
            text = item.methodFullName,
            style = MaterialTheme.typography.subtitle2,
            color = MaterialTheme.colors.onBackground,
        )
        Spacer(
            modifier = Modifier.weight(weight = 1f, fill = true)
        )
        Column(
            horizontalAlignment = androidx.compose.ui.Alignment.End,
        ) {
            Text(
                text = "methodCost: ${item.minMethodCost}ms ~ ${item.maxMethodCost}ms",
                style = MaterialTheme.typography.body2.copy(
                    fontSize = MaterialTheme.typography.body2.fontSize.times(0.9f),
                ),
                color = if (item.avgMethodCost >= 20) {
                    MaterialTheme.colors.error
                } else {
                    MaterialTheme.colors.onBackground
                },
            )
            Spacer(
                modifier = Modifier.height(4.dp)
            )
            Text(
                text = "totalMethodCost: ${item.minMethodTotalCost}ms ~ ${item.maxMethodTotalCost}ms",
                style = MaterialTheme.typography.body2.copy(
                    fontSize = MaterialTheme.typography.body2.fontSize.times(0.9f),
                ),
                color = if (item.avgMethodTotalCost >= 20) {
                    MaterialTheme.colors.error
                } else {
                    MaterialTheme.colors.onBackground
                },
            )
        }
    }
}

/**
 * 设计图来源：https://github.com/zhengcx/MethodTraceMan/blob/master/methodtraceman.png
 */
@Composable
fun MethodTraceGroupListView(
    navController: NavHostController,
) {

    val vm = viewModel {
        MethodTraceMainViewModel()
    }

    val appSelected by vm.appSelectedState.collectAsState(initial = MethodTraceMainUseCase.APP_NAME_VS_METHOD_TRACE_DEMO)
    val currentPage by vm.currentPageState.collectAsState(initial = 1)
    val datalist by vm.currentPageListState.collectAsState(initial = emptyList())

    var isShowSelectApp by remember {
        mutableStateOf(value = false)
    }

    Scaffold(
        floatingActionButton = {
            Column {
                FloatingActionButton(
                    onClick = {
                        isShowSelectApp = true
                    },
                ) {
                    Icon(
                        Icons.Filled.Menu,
                        contentDescription = "Menu",
                    )
                }
                Spacer(
                    modifier = Modifier.height(12.dp)
                )
                FloatingActionButton(
                    onClick = {
                        vm.refresh()
                    },
                ) {
                    Icon(
                        Icons.Filled.Refresh,
                        contentDescription = "Refresh",
                    )
                }
            }
        }
    ) {

        // select app

        if (currentPage <= 1 && datalist.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                contentAlignment = androidx.compose.ui.Alignment.Center,
            ) {
                Text(
                    text = "暂无数据",
                    style = MaterialTheme.typography.h6,
                    color = MaterialTheme.colors.onBackground,
                )
            }
        } else // 占位
        {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(weight = 1f),
                ) {
                    itemsIndexed(items = datalist) { index, item ->
                        if (index > 0) {
                            Divider()
                        }
                        MethodTraceGroupItemView(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .clickable {
                                    navController.navigate(
                                        route = AppScreen.Detail.withArgs(
                                            appSelected,
                                            item.methodFullName,
                                            item.methodFullNameMd5,
                                        ),
                                    )
                                }
                                .padding(horizontal = 24.dp, vertical = 12.dp),
                            item = item,
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .wrapContentSize(),
                    verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
                ) {
                    if (currentPage > 1) {
                        Button(
                            onClick = {
                                vm.prePage()
                            },
                        ) {
                            Text(
                                text = "上一页",
                            )
                        }
                        Spacer(
                            modifier = Modifier.width(width = 12.dp)
                        )
                    }
                    Button(
                        onClick = {
                            vm.nextPage()
                        },
                    ) {
                        Text(
                            text = "下一页",
                        )
                    }
                }
                Spacer(
                    modifier = Modifier.height(height = 24.dp)
                )
            }
        }

        if (isShowSelectApp) {
            Dialog(
                onDismissRequest = {
                    isShowSelectApp = false
                },
            ) {
                Column(
                    modifier = Modifier
                        .width(
                            intrinsicSize = IntrinsicSize.Max,
                        )
                        .wrapContentHeight()
                        .clip(
                            shape = MaterialTheme.shapes.medium,
                        )
                        .background(
                            color = MaterialTheme.colors.surface,
                        ),
                    horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
                ) {
                    MethodTraceMainUseCase
                        .APP_NAME_LIST
                        .forEachIndexed { index, item ->
                            if (index > 0) {
                                Divider()
                            }
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight()
                                    .clickable {
                                        isShowSelectApp = false
                                        vm.selectApp(appName = item)
                                    }
                                    .padding(horizontal = 16.dp, vertical = 8.dp),
                                text = item,
                                style = MaterialTheme.typography.h5,
                                textAlign = TextAlign.Center,
                            )
                        }
                }
            }
        }

    }

}