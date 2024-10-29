import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vistring.view.MethodTraceGroupListView
import com.vistring.view.MethodTraceViewModel


@Composable
@Preview
fun App() {
    MaterialTheme {
        val vm = viewModel {
            MethodTraceViewModel()
        }
        Scaffold(
            floatingActionButton = {
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
        ) {
            MethodTraceGroupListView()
        }
    }
}

fun main() = application {
    Window(
        title = "MethodTrace By ViString",
        onCloseRequest = ::exitApplication,
    ) {
        App()
    }
}
