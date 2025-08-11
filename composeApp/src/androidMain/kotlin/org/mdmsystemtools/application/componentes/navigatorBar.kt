import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Album
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.PlaylistAddCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.mdmsystemtools.application.Telas.CadastrosScreen
import org.mdmsystemtools.application.Telas.CameraScreen
import org.mdmsystemtools.application.Telas.DetalhesScreen
import org.mdmsystemtools.application.Telas.ReuniaoScreen

enum class Destination(
  val route: String,
  val label: String,
  val icon: ImageVector,
  val contentDescription: String
) {
  CADASTROS("Cadastros", "cadatros", Icons.Default.People, "Cadastros"),
  CAMERA("Camera", "Camera", Icons.Default.CameraAlt, "camera"),
  REUNIAO("Reuniao", "Reuniao", Icons.Default.CalendarToday, "Reuniao"),
}

@Composable
fun AppNavHost(
  navController: NavHostController,
  startDestination: Destination,
  modifier: Modifier = Modifier
) {
  NavHost(
    navController,
    startDestination = startDestination.route
  ) {
    Destination.entries.forEach { destination ->
      composable(destination.route) {
        when (destination) {
          Destination.CADASTROS -> CadastrosScreen()
          Destination.CAMERA -> CameraScreen()
          Destination.REUNIAO -> ReuniaoScreen()
        }
      }
    }
  }
}


@Composable
fun NavigatorBarWidget(modifier: Modifier = Modifier, bottomBar: () -> Unit) {
  val navController = rememberNavController()
  val startDestination = Destination.CADASTROS
  var selectedDestination by rememberSaveable { mutableIntStateOf(startDestination.ordinal) }

  Scaffold(modifier = modifier, bottomBar = {
    NavigationBar(windowInsets = NavigationBarDefaults.windowInsets) {
      Destination.entries.forEachIndexed { index, destination ->
        NavigationBarItem(
          selected = selectedDestination == index,
          onClick = {
            navController.navigate(route = destination.route)
            selectedDestination = index
          },
          icon = {
            Icon(
              destination.icon,
              contentDescription = destination.contentDescription
            )
          },
          label = { Text(destination.label) }
        )
      }
    }
  }
  ) { contentPadding ->
    AppNavHost(navController, startDestination, modifier = Modifier.padding(contentPadding))
  }
}
