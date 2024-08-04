package com.example.kunbaapp.ui.poc

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kunbaapp.data.models.entity.FamilyDbo
import com.example.kunbaapp.data.models.entity.NodeDbo
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class KunbaNotificationsViewModel: ViewModel() {

    init {
        quickNotifications()
        //summarizedNotifications()
    }

    private fun quickNotifications(){
        viewModelScope.launch {
            val nodesActivity: Flow<NodeDbo> = flowOf(*nodeList.toTypedArray()).onEach {
                //delay(50)
                //delay(75)
                delay(25)
            }
            val familyActivity: Flow<FamilyDbo> = flowOf(*familyList.toTypedArray()).onEach {
                //delay(100)
                delay(50)
            }

            merge(nodesActivity, familyActivity).collect {
                when(it) {
                    is NodeDbo -> {
                        Log.i("Quick Notification", "${it.nodeId} commented on your post: ${it.firstName}")
                    }
                    is FamilyDbo -> {
                        Log.i("Quick Notification", "${it.familyId} reacted with ${it.fatherInfo?.firstName} to your post: ${it.motherInfo?.firstName}")
                    }

                }
            }
        }
    }

    private fun summarizedNotifications() {
        viewModelScope.launch {
            val nodesActivity: Flow<NodeDbo> = flowOf(*nodeList.toTypedArray()).onEach {
                delay(50)
            }
            val familyActivity: Flow<FamilyDbo> = flowOf(*familyList.toTypedArray()).onEach {
                delay(200)
            }

            /*
            nodesActivity.combine(familyActivity) { node, fam ->
               Log.i("Quick Notification", "${node.nodeId} reacted with ${node.firstName} on ${node.lastName}\n" +
                        "and ${fam.familyId} commented ${fam.fatherInfo.firstName} on ${fam.motherInfo.firstName}")

            }.collect()

             */
            familyActivity.combine(nodesActivity) { fam, node ->
                Log.i("Quick Notification", "${node.nodeId} reacted with ${node.firstName} on ${node.lastName}\n" +
                        "and ${fam.familyId} commented ${fam.fatherInfo?.firstName} on ${fam.motherInfo?.firstName}")

            }.collect()
        }
    }
}