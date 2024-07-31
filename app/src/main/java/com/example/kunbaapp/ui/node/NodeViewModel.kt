package com.example.kunbaapp.ui.node

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kunbaapp.data.models.dto.NodeDto
import com.example.kunbaapp.data.models.dto.NodeDtos.AddNodeDto
import com.example.kunbaapp.data.models.dto.NodeDtos.UpdateNodeDto
import com.example.kunbaapp.data.models.dto.timelineDtos.TimelineObject
import com.example.kunbaapp.data.models.dto.timelineDtos.NodeTimelineDto
import com.example.kunbaapp.data.models.dto.timelineDtos.TempTimelineObject
import com.example.kunbaapp.data.models.entity.Favorite
import com.example.kunbaapp.data.repository.contract.IApiRepository
import com.example.kunbaapp.data.repository.contract.IDatabaseRepository
import com.example.kunbaapp.ui.poc.Item
import com.example.kunbaapp.ui.poc.ItemDetails
import com.example.kunbaapp.ui.poc.toItem
import com.example.kunbaapp.utils.EntityType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NodeViewModel(
    savedStateHandle: SavedStateHandle,
    private val apiRepository: IApiRepository,
    private val databaseRepository: IDatabaseRepository
) : ViewModel() {
    val nodeIdFromUrl: Int = checkNotNull(
        savedStateHandle[
            NodeDestination.ID_ARG
        ]
    )
    /*
    val uniqueIdFromUrl: String = checkNotNull(
        savedStateHandle[
            NodeDestination.UniqueId_ARG
        ]
    )
     */

    private val _uiState = MutableStateFlow<NodeUiState>(NodeUiState())
    val uiState: StateFlow<NodeUiState> = _uiState

    var itemUiState by mutableStateOf(NodeUiState())
        private set

    private fun getNode() {
        viewModelScope.launch {
            val response = apiRepository.fetchNode(nodeIdFromUrl)
            val result = response.body()
            Log.d("URL", nodeIdFromUrl.toString())
            if (result != null) {
                _uiState.update {
                    it.copy(
                        node = result,
                        updateNodeDto = result.toUpdateNodeDto()
                    )
                }
                //itemUiState = NodeUiState(node = result)

                getFamilyTimeline()
                isFavoriteExist()
            }
        }
    }
    fun toggleFavoriteButton(id: Int) {
        Log.d("Favorite", id.toString())
        viewModelScope.launch(Dispatchers.IO) {
            val favoriteFromDb =
                databaseRepository.getFavoriteByTypeAndRefId(type = EntityType.Node, refId = id)
            Log.d("Favorite", favoriteFromDb.toString())
            if (favoriteFromDb != null) {
                databaseRepository.removeFavorite(favoriteFromDb)
                Log.d("Favorite", "Removed from Favorites")
                _uiState.update {
                    it.copy(
                        isFavorite = false
                    )
                }
            }
            else {
                val favorite = Favorite(
                    id = 0,
                    type = EntityType.Node,
                    refId = id,
                    displayText = "Node: ${id}"
                )
                databaseRepository.addFavorite(favorite)
                Log.d("Favorite", "Added to Favorites")
                _uiState.update {
                    it.copy(
                        isFavorite = true
                    )
                }
            }
        }
    }

    private fun isFavoriteExist(){
        viewModelScope.launch(Dispatchers.IO) {
            val favoriteFromDb = databaseRepository.getFavoriteByTypeAndRefId(
                type = EntityType.Node,
                refId = nodeIdFromUrl
            )
            if(favoriteFromDb != null)
            {
                _uiState.update {
                    it.copy(
                        isFavorite = true
                    )
                }
            }
            else
            {
                _uiState.update {
                    it.copy(
                        isFavorite = false
                    )
                }
            }
        }
    }

    fun setUniqueId(value: String){
        _uiState.update {
            it.copy(
                uniqueId = value
            )
        }
    }

    fun updateAddNodeDto(addNodeDto: AddNodeDto){
        _uiState.update {
            it.copy(
                addNodeDto = addNodeDto
            )
        }
    }

    fun saveNode() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = apiRepository.addNode(_uiState.value.addNodeDto)
            val result = response.body()
            Log.d("New Node", result.toString())
            /*
            if(response.isSuccessful && result != null)
            {
                _uiState.update {
                    it.copy(
                        node = result
                    )
                }
            }
             */
        }
    }

    fun getFamilyTimeline() {
        viewModelScope.launch {
            val response = apiRepository.getFamilyTimeLine(_uiState.value.node)
            val result = response.body()

            if(response.isSuccessful && result != null)
            {
                _uiState.update {
                    it.copy(
                        nodeTimelineDtos = result
                    )
                }
                val nodeStage : MutableList<TimelineObject> = mutableListOf()
                _uiState.value.nodeTimelineDtos.forEach {
                    val temp = it
                    val fullName = "${it.firstName} ${it.lastName}"
                        /*
                        val objectList : List<TempTimelineObject> =
                            listOf(
                                TempTimelineObject(
                                    id= 1,
                                    name= "Roli"
                                ),
                                TempTimelineObject(
                                    id= 2,
                                    name= "Rohit"
                                ),
                                TempTimelineObject(
                                    id= 3,
                                    name= "Mohit"
                                ),
                            )

                         */

                    val tempTimelineObject = TempTimelineObject(
                        id = it.nodeId,
                        name = fullName
                    )
                    var temp1 = TimelineObject(
                        initiator = tempTimelineObject,
                    )
                    /*
                    if(it.firstName == "Ravindra ")
                    {
                        temp1 = TimelineObject(
                            initiator = tempTimelineObject,
                            children = objectList
                        )
                    }
                     */
                    nodeStage.add(
                        temp1
                    )
                }
                _uiState.update {
                    it.copy(
                        nodeStage = nodeStage
                    )
                }
            }
        }
    }

    fun addPartner(){
        Log.d("Partner", "Init")
        viewModelScope.launch {
            val response = apiRepository.addPartner(nodeIdFromUrl)
            val result = response.body()
            Log.d("Partner", result.toString())
            /*
            if(response.isSuccessful && result != null)
            {
                _uiState.update {
                    it.copy(
                        partnerId = result.nodeId
                    )
                }
            }

             */
        }
        Log.d("Partner", "End")
    }

    fun addParents(){
        viewModelScope.launch {
            val response = apiRepository.addParents(nodeIdFromUrl)
            val result = response.body()
            Log.d("Partner", result.toString())
        }
    }

    fun addSibling(){
        viewModelScope.launch {
            val response = apiRepository.addSibling(nodeIdFromUrl)
            Log.d("Partner", response.body().toString())
            val result = response.body()

        }
    }


    fun addChild() {
        viewModelScope.launch {
            val response = apiRepository.addChild(nodeIdFromUrl)
            Log.d("Child", response.body().toString())
            val result = response.body()
        }
    }

    fun updateNode(updateNodeDto: UpdateNodeDto) {
        viewModelScope.launch {
            Log.d("UpdateNodeDto", "Before: ${updateNodeDto.toString()}")
            Log.d("UpdateNodeDto", "IsValid: ${_uiState.value.isEntryValid.toString()}")
            if (_uiState.value.isEntryValid) {
                val item = _uiState.value.updateNodeDto.toNodeDto()

                // TODO: Send converted item to API or DB
                Log.d("UpdateNodeDto", "After: ${item.toString()}")
            }
            /*
            val nodeDto = NodeDto(
                nodeId = updateNode.nodeId,
                rootId = updateNode.rootId,
                familyId = updateNode.familyId,
                firstName = updateNode.firstName?: "",
                lastName = updateNode.lastName?:"",
                dateOfBirth = updateNode.dateOfBirth?:"",
                placeOfBirth = updateNode.placeOfBirth?:"",
                image_Url = updateNode.image_Url?:""
            )
            if(_uiState.value.node != NodeDto()) {
                val updatedNode = apiRepository.updateNode(nodeIdFromUrl, updateNode.toNodeDto())
                if(updatedNode.isSuccessful && updatedNode.body() != null) {
                    _uiState.update {
                        it.copy(
                            node = updatedNode.body()?: NodeDto()
                        )
                    }
                }
            }
             */
        }

    }

    fun updateNodeDto(updateNodeDto: UpdateNodeDto){
        _uiState.update {
            it.copy(
                updateNodeDto = updateNodeDto,
                isEntryValid = validateInput(updateNodeDto)
            )
        }
    }

    private fun validateInput(uiState: UpdateNodeDto = _uiState.value.updateNodeDto): Boolean {
        return with(uiState) {
            firstName.isNotBlank() && lastName.isNotBlank() && gender.isNotBlank()
                    && dateOfBirth.isNotBlank() && placeOfBirth.isNotBlank() && image_Url.isNotBlank()
        }
    }
    init {
        getNode()
        //getFavoritesFromDb()

    }
}


data class NodeUiState(
    val node: NodeDto = NodeDto(),
    //val favoritesFromDb: List<Favorite> = listOf(),
    val isFavorite: Boolean = false,
    val uniqueId: String = "",
    val addNodeDto : AddNodeDto = AddNodeDto(),
    val isEntryValid: Boolean = false,
    val nodeTimelineDtos : List<NodeTimelineDto> = listOf(),
    val nodeStage : List<TimelineObject> = listOf(),
    val updateNodeDto: UpdateNodeDto = UpdateNodeDto(),
)

fun NodeDto.toUpdateNodeDto(): UpdateNodeDto = UpdateNodeDto(
    nodeId = nodeId,
    rootId = rootId,
    familyId = familyId.toString(),
    firstName = firstName,
    lastName = lastName,
    gender = gender.toString(),
    dateOfBirth = dateOfBirth?:"",
    placeOfBirth = placeOfBirth?:"",
    image_Url = image_Url?:""
)

fun UpdateNodeDto.toNodeDto(): NodeDto = NodeDto(
    nodeId = nodeId,
    rootId = rootId,
    familyId = familyId.toIntOrNull(),
    firstName = firstName,
    lastName = lastName,
    gender = gender.toCharArray()[0],
    dateOfBirth = dateOfBirth,
    placeOfBirth = placeOfBirth,
    image_Url = image_Url
)