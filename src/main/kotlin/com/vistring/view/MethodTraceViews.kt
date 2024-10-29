package com.vistring.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vistring.model.MethodTraceGroupInfoDto

@Composable
fun MethodTraceGroupItemView(
    item: MethodTraceGroupInfoDto,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 24.dp, vertical = 12.dp),
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
    ) {
        Text(
            text = "主线程: ",
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
                style = MaterialTheme.typography.body2,
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
                style = MaterialTheme.typography.body2,
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
fun MethodTraceGroupListView() {

    val vm = viewModel {
        MethodTraceViewModel()
    }

    val currentPage by vm.currentPageState.collectAsState(initial = 1)
    val datalist by vm.currentPageListState.collectAsState(initial = emptyList())

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
    } else {
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
                    MethodTraceGroupItemView(item = item)
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

}