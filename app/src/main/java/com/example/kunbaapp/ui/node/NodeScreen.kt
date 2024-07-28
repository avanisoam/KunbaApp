package com.example.kunbaapp.ui.node

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Call
import androidx.compose.material.icons.rounded.Create
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import com.example.kunbaapp.R
import com.example.kunbaapp.data.models.dto.timelineDtos.NodeStage
import com.example.kunbaapp.ui.family.FamilyDestination
import com.example.kunbaapp.ui.navigation.NavigationDestination
import com.example.kunbaapp.ui.shared.CustomFloatingButton
import com.example.kunbaapp.ui.shared.FABItem
import com.example.kunbaapp.ui.shared.KunbaAppTopBar
import com.example.kunbaapp.ui.shared.NodeItem
import com.example.kunbaapp.ui.shared.Nodes.AddNodeBody
import com.example.kunbaapp.ui.shared.PopupDialog
import com.example.kunbaapp.ui.shared.Timeline.LazyTimelineKunba
import org.koin.androidx.compose.getViewModel

object NodeDestination : NavigationDestination {
    override val route = "node"
    @StringRes
    override val titleRes = R.string.node
    const val ID_ARG = "nodeId"
    val routeWithArgs = "$route/{$ID_ARG}"
    const val UniqueId_ARG = "uniqueId"
    val routeWithStringArgs = "$route/{$UniqueId_ARG}"
}

@Composable
fun NodeScreen(
    navigateToFamilyScreen: (Int) -> Unit,
    navigateUp: () -> Unit,
    navigateToHome: () -> Unit,
    navigateToNodeScreen: (Int) -> Unit,
    viewModel: NodeViewModel = getViewModel<NodeViewModel>()
) {
    val uiState by viewModel.uiState.collectAsState()

    //Text(text = uiState.node.toString())
    val context = LocalContext.current
    Scaffold(
        topBar = {
            KunbaAppTopBar(
                canNavigateBack = true,
                title = NodeDestination.titleRes,
                navigateUp = navigateUp,
                isFavorite = uiState.isFavorite,
                toggleFavorite = {viewModel.toggleFavoriteButton(viewModel.nodeIdFromUrl)},
                showFavorite = true
            )
        },
        floatingActionButton = {
            val itemList = listOf(
                FABItem(icon = Icons.Rounded.Home, text = "Home",uniqueId="open_home"),
                FABItem(icon = Icons.Rounded.Add, text = "Add father",uniqueId="add_father"),
                FABItem(icon = Icons.Rounded.Add, text = "Add mother",uniqueId="add_mother"),
                FABItem(icon = Icons.Rounded.Add, text = "Add son",uniqueId="add_son"),
                FABItem(icon = Icons.Rounded.Add, text = "Add daughter",uniqueId="add_daughter"),
                FABItem(icon = Icons.Rounded.Add, text = "Add brother",uniqueId="add_brother"),
                FABItem(icon = Icons.Rounded.Add, text = "Add sister",uniqueId="add_sister"),
                FABItem(icon = Icons.Rounded.Add, text = "Add spouse",uniqueId="add_spouse"),
                FABItem(icon = Icons.Rounded.Add, text = "Add half-sibling",uniqueId="add_half_sibling"),
            )
            val openAlertDialog = remember { mutableStateOf(false) }
            if(openAlertDialog.value)
            {
                PopupDialog(
                    onDismissRequest = { openAlertDialog.value = false },
                    onConfirmation = {  openAlertDialog.value = false
                        println("Confirmation registered") // Add logic here to handle confirmation. },
                    },
                    dialogTitle = "Alert dialog example",
                    dialogText = "This is an example of an alert dialog with buttons.",
                    icon = Icons.Rounded.AccountCircle,
                )
            }
            CustomFloatingButton(
                items = itemList,
                selectedItemId = uiState.uniqueId,
                onItemClick = {item ->

                    when(item.uniqueId) {
                        "open_home" ->  {
                            // navigating to Home
                            navigateToHome()
                        }//Toast.makeText(context, "call clicked", Toast.LENGTH_SHORT).show()
                        "add_father" -> {viewModel.setUniqueId(item.uniqueId)}//openAlertDialog.value=true//Toast.makeText(context, "create clicked", Toast.LENGTH_SHORT).show()
                        "add_mother" -> {viewModel.setUniqueId(item.uniqueId)}//Toast.makeText(context, "account clicked", Toast.LENGTH_SHORT).show()
                        "add_son" -> Toast.makeText(context, "account clicked", Toast.LENGTH_SHORT).show()
                        "add_daughter" -> Toast.makeText(context, "account clicked", Toast.LENGTH_SHORT).show()
                        "add_brother" -> Toast.makeText(context, "account clicked", Toast.LENGTH_SHORT).show()
                        "add_sister" -> Toast.makeText(context, "account clicked", Toast.LENGTH_SHORT).show()
                        "add_spouse" -> Toast.makeText(context, "account clicked", Toast.LENGTH_SHORT).show()
                        "add_half_sibling" -> Toast.makeText(context, "account clicked", Toast.LENGTH_SHORT).show()
                    }

                }
            )
            /*
            FloatingActionButton(
                onClick = navigateToHome,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(20.dp)
            ) {
                Icon(
                    //imageVector = if(uiState.isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    imageVector = Icons.Filled.Add,
                    contentDescription = stringResource(R.string.go_to_favorite)
                )
            }
             */
        }
    ) {innerPadding ->
        when(uiState.uniqueId)
        {
            "" -> {
                //KunbaFamilyTime(timelineStages = uiState.nodeStage)
                Column(modifier = Modifier.padding(innerPadding)) {
                    NodeItem(
                        node = uiState.node,
                        onItemClick = {navigateToFamilyScreen(it)},
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    LazyTimelineKunba(
                        stages= uiState.nodeStage,
                        onClick = {navigateToNodeScreen(it)}
                    )
                }

                /*
                LazyColumn(modifier = Modifier.padding(innerPadding)) {
                    item {
                        NodeItem(
                            node = uiState.node,
                            onItemClick = {navigateToFamilyScreen(it)},
                        )
                    }

                    item {
                        Text(text = uiState.node.toString())
                    }
                    item {
                        Text(text = "Unique Id: ${uiState.uniqueId}")
                    }
                    item{
                        //Text(text = uiState.nodeTimelineDtos.toString())
                        KunbaFamilyTime(timelineStages = uiState.nodeStage)
                    }
                }

                 */
            }
            "add_mother" -> {
                Text(
                    text = "Add Node Form: TODO",
                    modifier = Modifier.padding(innerPadding)
                )
            }
            "add_father" -> {
                /*
                Text(
                    text = "Add Father Form",
                    modifier = Modifier.padding(innerPadding)
                )
                 */
                AddNodeBody(
                    addNode = uiState.addNodeDto,
                    onItemValueChange = {viewModel.updateAddNodeDto(it)},
                    onSaveClick = { viewModel.saveNode() },
                    isEntryValid = uiState.isEntryValid,
                    modifier = Modifier.padding(innerPadding)
                )
            }

        }
    }
}