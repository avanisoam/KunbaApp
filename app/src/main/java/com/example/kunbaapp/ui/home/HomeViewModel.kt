package com.example.kunbaapp.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kunbaapp.data.models.dto.RootDetailDto
import com.example.kunbaapp.data.models.dto.RootRegisterDto
import com.example.kunbaapp.data.models.entity.FamilyDbo
import com.example.kunbaapp.data.models.entity.Favorite
import com.example.kunbaapp.data.models.entity.NodeDbo
import com.example.kunbaapp.data.models.entity.RootRegisterDbo
import com.example.kunbaapp.data.repository.contract.IApiRepository
import com.example.kunbaapp.data.repository.contract.IDatabaseRepository
import com.example.kunbaapp.data.repository.contract.IOfflineApiRepository
import com.example.kunbaapp.ui.family.FamilyUiState
import com.example.kunbaapp.ui.node.NodeUiState
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

class HomeViewModel(
    private val apiRepository: IApiRepository,
    private val databaseRepository: IDatabaseRepository,
    private val offlineApiRepository: IOfflineApiRepository
): ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState

    private fun getRoots() {
        viewModelScope.launch(Dispatchers.IO){
            val response = apiRepository.fetchRoots()
            val result = response.body()
            if(response.isSuccessful || result != null)
            {
                _uiState.update {
                    it.copy(
                        roots = result!!
                    )
                }
            }
        }
    }

    val uiStateDb : Flow<HomeUiState> = offlineApiRepository.getRootRegisters()
        .map {
            HomeUiState(
                roots = it.map {rootDbo ->
                    rootDbo.toRootRegisterDto()
                }
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeUiState()
        )



    /*
    val uiStateFamilyDb : Flow<FamilyUiState> = offlineApiRepository.getFamilyV1(1)
        .map {
            FamilyUiState(
                familyDbo = it ?: FamilyDbo(0, NodeDbo(), NodeDbo(), listOf())

            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = FamilyUiState()
        )

     */

    val uiStateFamiliesDb : Flow<FamilyUiState> = offlineApiRepository.getFamilies()
        .map {
            FamilyUiState(
                listOfFamilies = it

            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = FamilyUiState()
        )

    val uiStateNodesDb : Flow<NodeUiState> = offlineApiRepository.getNodes()
        .map {
            Log.d("FLOW-DB",it.toString())
            NodeUiState(
                listOfNodesDbo = it

            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = NodeUiState()
        )

    val uiStateRootDetailDb : Flow<RootDetailUiState> = offlineApiRepository.fetchRootDetailFlow(1)
        .map {
            Log.d("FLOW-DB",it.toString())
            RootDetailUiState(
                rootDetailDbo = it

            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = RootDetailUiState()
        )
    private fun getFavoritesFromDb() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = databaseRepository.getFavoriteByType(EntityType.Root)

            _uiState.update {
                it.copy(
                    favoritesRootIds = response.map{
                        it.refId
                    }.toList()
                )
            }
        }
    }

    fun toggleFavoriteButton(id: Int) {
        Log.d("Favorite - root", id.toString())
        viewModelScope.launch(Dispatchers.IO) {
            val favoriteFromDb =
                databaseRepository.getFavoriteByTypeAndRefId(type = EntityType.Root, refId = id)
            //Log.d("Favorite - root", favoriteFromDb.toString())
            if (favoriteFromDb != null) {
                databaseRepository.removeFavorite(favoriteFromDb)
                Log.d("Favorite - root", "Removed from Favorites")

            }
            else {
                val favorite = Favorite(
                    id = 0,
                    type = EntityType.Root,
                    refId = id,
                    displayText = "Root: ${id}"
                )
                databaseRepository.addFavorite(favorite)
                Log.d("Favorite - root", "Added to Favorites")
            }
        }
    }

    fun getRootsFromDb(){
        viewModelScope.launch {
            val response = offlineApiRepository.getRootRegisters()
            Log.d("OfflineDb", response.toString())
        }
    }

    fun loadData() {
        viewModelScope.launch(Dispatchers.IO) {
            val root1 = RootRegisterDbo(
                rootId = 1,
                rootName = "Sawai Singh"
            )
            offlineApiRepository.addRootRegister(root1)

            val node1 = NodeDbo(
                nodeId = 1,
                rootId = 1,
                familyId = 1,
                firstName = "Me",
                lastName = "Singh",
                gender = 'M',
                dateOfBirth = "2017-03-02 10:10:10",
                placeOfBirth = "Gurgaon",
                image_Url = "Image Url"
            )
            offlineApiRepository.addNode(node1)

            val node2 = NodeDbo(
                nodeId = 2,
                rootId = 1,
                familyId = null,
                firstName = "Father of Me",
                lastName = "Singh",
                gender = 'M',
                dateOfBirth = "2017-03-02 10:10:10",
                placeOfBirth = "Gurgaon",
                image_Url = "Image Url"
            )
            offlineApiRepository.addNode(node2)

            val node3 = NodeDbo(
                nodeId = 3,
                rootId = 0,
                familyId = 100,
                firstName = "Mother of Me",
                lastName = "Singh",
                gender = 'F',
                dateOfBirth = "2017-03-02 10:10:10",
                placeOfBirth = "Bhiwani",
                image_Url = "Image Url"
            )
            offlineApiRepository.addNode(node3)

            val node4 = NodeDbo(
                nodeId = 4,
                rootId = 0,
                familyId = null,
                firstName = "Wife of Me",
                lastName = "Singh",
                gender = 'F',
                dateOfBirth = "2017-03-02 10:10:10",
                placeOfBirth = "Bhiwani1",
                image_Url = "Image Url"
            )
            offlineApiRepository.addNode(node4)

            val node5 = NodeDbo(
                nodeId = 5,
                rootId = 1,
                familyId = 1,
                firstName = "Sibling of Me",
                lastName = "Singh",
                gender = 'M',
                dateOfBirth = "2017-03-02 10:10:10",
                placeOfBirth = "Gurgaon",
                image_Url = "Image Url"
            )
            offlineApiRepository.addNode(node5)

            val children : List<NodeDbo> = listOf(node1,node5)

            val family1 = FamilyDbo(
                familyId = 1,
                fatherInfo = node2,
                motherInfo = node3,
                children = children
            )

            offlineApiRepository.addFamily(family1)
        }
    }

    init {
        //getRoots()
        //getFavoritesFromDb()
        //loadData()
    }
}

data class HomeUiState(
    val roots: List<RootRegisterDto> = listOf(),
    val favoritesRootIds: List<Int> = listOf(),
    //val isFavorite: Boolean = false
)

fun RootRegisterDbo.toRootRegisterDto() : RootRegisterDto = RootRegisterDto(
    rootId = rootId,
    rootName = rootName
)