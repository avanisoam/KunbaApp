package com.example.kunbaapp.data.models.entity

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

@ProvidedTypeConverter
class NodeTypeConvertor {
    @TypeConverter
    fun nodeToString(nodeDbo: NodeDbo):String{
        return "${nodeDbo.nodeId},${nodeDbo.rootId},${nodeDbo.familyId},${nodeDbo.firstName},${nodeDbo.lastName},${nodeDbo.gender},${nodeDbo.dateOfBirth},${nodeDbo.placeOfBirth},${nodeDbo.image_Url}"
    }

    @TypeConverter
    fun stringToNode(nodeDbo: String): NodeDbo{
        return nodeDbo.split(',').let { sourceArray ->
            NodeDbo(
                nodeId = sourceArray[0].toInt(),
                rootId = sourceArray[1].toInt(),
                familyId = sourceArray[2].toIntOrNull(),
                firstName = sourceArray[3],
                lastName = sourceArray[4],
                gender = sourceArray[5].toCharArray()[0],
                dateOfBirth = sourceArray[6],
                placeOfBirth = sourceArray[7],
                image_Url = sourceArray[8]
            )
        }
    }

    /*
    @TypeConverter
    fun nodeToListString(nodeDboList: List<NodeDbo>):String{

        val tempList: MutableList<String> = mutableListOf()

        nodeDboList.forEach {nodeDbo ->
            tempList.add(
            "${nodeDbo.nodeId},${nodeDbo.rootId},${nodeDbo.familyId},${nodeDbo.firstName},${nodeDbo.lastName},${nodeDbo.gender},${nodeDbo.dateOfBirth},${nodeDbo.placeOfBirth},${nodeDbo.image_Url}"
            )
        }
        return tempList.toString()
    }


    @TypeConverter
    fun listOfStringToListNode(nodeDbos: List<String>): List<NodeDbo>{
        val tempList: MutableList<NodeDbo> = mutableListOf()
        nodeDbos.forEach {nodeDbo ->
            nodeDbo.split(',').let { sourceArray ->
               val t = NodeDbo(
                    nodeId = sourceArray[0].toInt(),
                    rootId = sourceArray[1].toInt(),
                    familyId = sourceArray[2].toIntOrNull(),
                    firstName = sourceArray[3],
                    lastName = sourceArray[4],
                    gender = sourceArray[5].toCharArray()[0],
                    dateOfBirth = sourceArray[6],
                    placeOfBirth = sourceArray[7],
                    image_Url = sourceArray[8]
                )
                tempList.add(t)
            }

        }
        return tempList.toList()
    }

     */

    @TypeConverter
    fun toNodeList(data: String): List<NodeDbo> {
        val listType = object : TypeToken<ArrayList<NodeDbo>>() {}.type
        return GsonBuilder().create().fromJson(data, listType)
    }

    @TypeConverter
    fun toNodeString(breed:  List<NodeDbo>): String {
        return GsonBuilder().create().toJson(breed)
    }


}