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
import com.example.kunbaapp.data.models.dto.V2.NewNodeDto
import com.example.kunbaapp.data.models.dto.timelineDtos.TimelineObject
import com.example.kunbaapp.data.models.dto.timelineDtos.NodeTimelineDto
import com.example.kunbaapp.data.models.dto.timelineDtos.TempTimelineObject
import com.example.kunbaapp.data.models.entity.Favorite
import com.example.kunbaapp.data.models.entity.NodeDbo
import com.example.kunbaapp.data.models.entity.RootRegisterDbo
import com.example.kunbaapp.data.repository.OfflineApiRepository
import com.example.kunbaapp.data.repository.contract.IApiRepository
import com.example.kunbaapp.data.repository.contract.IDatabaseRepository
import com.example.kunbaapp.data.repository.contract.IOfflineApiRepository
import com.example.kunbaapp.ui.family.toNodeDbo
import com.example.kunbaapp.ui.family.toNodeDto
import com.example.kunbaapp.ui.home.toRootRegisterDbo
import com.example.kunbaapp.ui.poc.Item
import com.example.kunbaapp.ui.poc.ItemDetails
import com.example.kunbaapp.ui.poc.toItem
import com.example.kunbaapp.ui.rootDetail.RootDetailUiState
import com.example.kunbaapp.utils.EntityType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NodeViewModel(
    savedStateHandle: SavedStateHandle,
    private val apiRepository: IApiRepository,
    private val databaseRepository: IDatabaseRepository,
    private val offlineApiRepository: IOfflineApiRepository
) : ViewModel() {
    val nodeIdFromUrl: Int = checkNotNull(
        savedStateHandle[
            NodeDestination.ID_ARG
        ]
    )

    private val _uiState = MutableStateFlow<NodeUiState>(NodeUiState())
    val uiState: StateFlow<NodeUiState> = _uiState

    val uiStateNodesDb : Flow<NodeUiState> = offlineApiRepository.getNodeV1(nodeIdFromUrl)
        .map {
            Log.d("FLOW-DB",it.toString())
            NodeUiState(
                nodeV2 = NewNodeDto(
                    individual = it?.toNodeDto() ?: NodeDto(),

                ),//it?.toNodeDto()?: NodeDto()
                nodeStage = getFamilyTimelineV1(),
                //updateNodeDto = it?.toUpdateNodeDto() ?: UpdateNodeDto()
               // isFavorite = isFavoriteExistV1()

            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = NodeUiState()
        )

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
            Log.d("Sibling", response.body().toString())
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

                val response = apiRepository.updateNode(nodeIdFromUrl, item)
                Log.d("UpdateNodeDto", "After: ${response.body().toString()}")
                val result = response.body()
                // TODO: Send converted item to API or DB
                Log.d("UpdateNodeDto", "After: ${item.toString()}")
            }
        }

    }

    fun updateNodeDto(updateNodeDto: UpdateNodeDto){

        _uiState.update {
            it.copy(
                updateNodeDto = updateNodeDto,
                isEntryValid = validateInput(updateNodeDto),
                selectedEntity = updateNodeDto.gender
            )
        }

    /*
        uiStateNodesDb.map {
            it.copy(
                updateNodeDto = updateNodeDto
            )
        }

     */
    }

    private fun validateInput(uiState: UpdateNodeDto = _uiState.value.updateNodeDto): Boolean {
        return with(uiState) {
            firstName.isNotBlank() && lastName.isNotBlank() && gender.isNotBlank()
                    && dateOfBirth.isNotBlank() && placeOfBirth.isNotBlank() && image_Url.isNotBlank()
        }
    }

    private fun checkAndSyncNodeData() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = offlineApiRepository.getNode(nodeIdFromUrl)
            if(response == null)
            {
                // Call Api and fill the table root_register
                //val nodeFromApi = apiRepository.fetchNode(nodeIdFromUrl)
                val nodeFromApi = apiRepository.fetchNodeV2(nodeIdFromUrl)
                val result = nodeFromApi.body()
                if(nodeFromApi.isSuccessful && result != null)
                {
                    offlineApiRepository.addNode(result.toNodeDbo())

                }
            }
        }
    }

    private fun getFamilyTimelineV1() : List<TimelineObject> {
        val nodeStage : MutableList<TimelineObject> = mutableListOf()
        viewModelScope.launch {
            val response = apiRepository.fetchNodeV2(nodeIdFromUrl)
            val result = response.body()
            Log.d("URL", nodeIdFromUrl.toString())
            if (result != null) {

               result.ancestors?.forEach {
                   nodeStage.add(
                       TimelineObject(
                           initiator = it.toTempTimelineObject(),
                           //children = result.ancestors.map { it.toTempTimelineObject() }
                       )
                   )
               }
            }
        }
        return nodeStage
    }

    fun convertNodeV2ToUpdateNodeDto(nodeDto: NodeDto) : UpdateNodeDto{
        val updateNode = nodeDto.toUpdateNodeDto()
        return  updateNode
    }
    init {
        checkAndSyncNodeData()
        isFavoriteExist()
    }
}


data class NodeUiState(
    val node: NodeDto = NodeDto(),
    val nodeV2: NewNodeDto = NewNodeDto(),
    val isFavorite: Boolean = false,
    val uniqueId: String = "",
    val addNodeDto : AddNodeDto = AddNodeDto(),
    val isEntryValid: Boolean = false,
    val nodeTimelineDtos : List<NodeTimelineDto> = listOf(),
    val nodeStage : List<TimelineObject> = listOf(),
    val updateNodeDto: UpdateNodeDto = UpdateNodeDto(),
    val selectedEntity: String = "",
    val listOfNodesDbo: List<NodeDbo> = listOf()
)

fun NodeDto.toUpdateNodeDto(): UpdateNodeDto = UpdateNodeDto(
    nodeId = nodeId,
    rootId = rootId,
    familyId = familyId.toString(),
    firstName = firstName?:"",
    lastName = lastName?:"",
    gender = gender.toString()?:"",
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

fun NewNodeDto.toNodeDbo(): NodeDbo = NodeDbo(
    nodeId = individual.nodeId,
    rootId = individual.rootId,
    familyId = individual.familyId,
    firstName = individual.firstName,
    lastName = individual.lastName,
    gender = individual.gender,
    dateOfBirth = individual.dateOfBirth,
    placeOfBirth = individual.placeOfBirth,
    image_Url = individual.image_Url
)

fun NewNodeDto.toTimelineObject(): TimelineObject = TimelineObject(
    initiator = individual.toTempTimelineObject(),
    children = ancestors?.map { it.toTempTimelineObject() } ?: listOf()
)

fun NodeDto.toTempTimelineObject(): TempTimelineObject = TempTimelineObject(
    id= nodeId,
    name = "$firstName $lastName"
)

fun NodeTimelineDto.toTempTimelineObject(): TempTimelineObject = TempTimelineObject(
    id= nodeId,
    name = "$firstName $lastName"
)

fun NodeDbo.toUpdateNodeDto() : UpdateNodeDto = UpdateNodeDto(
    nodeId = nodeId,
    rootId = rootId,
    familyId = familyId.toString(),
    firstName = firstName?: "",
    lastName = lastName ?: "",
    gender = gender.toString(),
    dateOfBirth = dateOfBirth?: "",
    placeOfBirth = placeOfBirth ?: "",
    image_Url = image_Url ?: ""
)
