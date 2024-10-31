package com.vistring.module.detail.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController

@Composable
fun MethodTraceDetailView(
    navController: NavHostController,
    appId: String,
    methodFullName: String,
    methodFullNameMd5: String,
) {

    val vm = viewModel {
        MethodTraceDetailViewModel(
            appId = appId,
            methodFullNameMd5 = methodFullNameMd5,
        )
    }

    val stackTraceFormatStr by vm.stackTraceFormatStrState.collectAsState(initial = null)

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.popBackStack()
                },
            ) {
                Text(
                    text = "返回",
                )
            }
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
        ) {

            Text(
                modifier = Modifier
                    .padding(
                        vertical = 12.dp,
                    )
                    .fillMaxWidth()
                    .wrapContentHeight(),
                text = methodFullName,
                style = MaterialTheme.typography.h6.copy(
                    color = MaterialTheme.colors.onBackground,
                ),
                textAlign = TextAlign.Center,
            )

            SelectionContainer {
                Text(
                    modifier = Modifier
                        .align(
                            alignment = androidx.compose.ui.Alignment.Start,
                        )
                        .padding(
                            horizontal = 24.dp,
                            vertical = 16.dp,
                        ).verticalScroll(
                            state = rememberScrollState(),
                        ),
                    text = stackTraceFormatStr.orEmpty(),
                    style = MaterialTheme.typography.body1.copy(
                        color = MaterialTheme.colors.error,
                    ),
                )
            }

        }
    }
}