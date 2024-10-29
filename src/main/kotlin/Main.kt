import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.vistring.module.detail.view.MethodTraceDetailView
import com.vistring.module.main.view.MethodTraceGroupListView


@Composable
@Preview
fun App() {
    val navController = rememberNavController()
    MaterialTheme {
        NavHost(
            navController = navController,
            startDestination = AppScreen.Main.name,
        ) {
            composable(route = AppScreen.Main.name) {
                MethodTraceGroupListView(
                    navController = navController,
                )
            }
            composable(
                route = AppScreen.Detail.name + "/{appId}/{methodFullName}/{methodFullNameMd5}",
                arguments = listOf(
                    navArgument("appId") {
                        type = NavType.StringType
                        defaultValue = ""
                    },
                    navArgument("methodFullName") {
                        type = NavType.StringType
                        defaultValue = ""
                    },
                    navArgument("methodFullNameMd5") {
                        type = NavType.StringType
                        defaultValue = ""
                    },
                ),
            ) { entry ->
                MethodTraceDetailView(
                    navController = navController,
                    appId = entry.arguments?.getString("appId").orEmpty(),
                    methodFullName = entry.arguments?.getString("methodFullName").orEmpty(),
                    methodFullNameMd5 = entry.arguments?.getString("methodFullNameMd5").orEmpty(),
                )
            }
        }
    }
}

fun main() = application {
    Window(
        state = rememberWindowState(
            position = WindowPosition(alignment = Alignment.Center),
            size = DpSize(1400.dp, 800.dp),
        ),
        title = "MethodTrace By ViString",
        onCloseRequest = ::exitApplication,
    ) {
        App()
    }
}
