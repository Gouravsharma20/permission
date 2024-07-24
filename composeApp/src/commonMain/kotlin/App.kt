import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.internal.composableLambdaInstance
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.icerock.moko.permissions.PermissionState
import dev.icerock.moko.permissions.compose.BindEffect
import dev.icerock.moko.permissions.compose.rememberPermissionsControllerFactory
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import permission.composeapp.generated.resources.Res
import permission.composeapp.generated.resources.compose_multiplatform

@Composable
@Preview
fun App() {
    MaterialTheme {
        val factory = rememberPermissionsControllerFactory()
        val controller = remember (factory){
            factory.createPermissionsController()
        }
        BindEffect(controller)
        val viewModel = viewModel {
            PermissionViewModel(controller)
        }
        Column (modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally){
            when (viewModel.state) {
                PermissionState.Granted -> Text("Permission Granted Successfully")
                PermissionState.DeniedAlways -> {
                    Text(
                        "Permission was permanently declined"
                    )
                    Button(onClick = {controller.openAppSettings()
                    }) {
                        Text("Please open App settings")
                    }
                }
                else -> {
                    Button(
                        onClick = {
                            viewModel.ProvideOrRequestAudioPermission()
                        }
                    ) {
                        Text("Requesting Permission")
                    }
                }
            }
        }
    }
}