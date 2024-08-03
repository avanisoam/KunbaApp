package com.example.kunbaapp.ui.poc

import com.example.kunbaapp.data.models.entity.FamilyDbo
import com.example.kunbaapp.data.models.entity.NodeDbo

val nodeList = listOf(
    NodeDbo(
        nodeId = 1,
        rootId = 1,
        familyId = 1,
        firstName = "Me",
        lastName = "Singh",
        gender = 'M',
        dateOfBirth = "2017-03-02 10:10:10",
        placeOfBirth = "Gurgaon",
        image_Url = "Image Url"
    ),


    NodeDbo(
        nodeId = 2,
        rootId = 1,
        familyId = null,
        firstName = "Father of Me",
        lastName = "Singh",
        gender = 'M',
        dateOfBirth = "2017-03-02 10:10:10",
        placeOfBirth = "Gurgaon",
        image_Url = "Image Url"
    ),


    NodeDbo(
        nodeId = 3,
        rootId = 0,
        familyId = 100,
        firstName = "Mother of Me",
        lastName = "Singh",
        gender = 'F',
        dateOfBirth = "2017-03-02 10:10:10",
        placeOfBirth = "Bhiwani",
        image_Url = "Image Url"
    ),


    NodeDbo(
        nodeId = 4,
        rootId = 0,
        familyId = null,
        firstName = "Wife of Me",
        lastName = "Singh",
        gender = 'F',
        dateOfBirth = "2017-03-02 10:10:10",
        placeOfBirth = "Bhiwani1",
        image_Url = "Image Url"
    ),

    NodeDbo(
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
)

val familyList = listOf(
    FamilyDbo(
        familyId = 1,
        fatherInfo = nodeList[1],
        motherInfo = nodeList[2],
        children = nodeList.filter {
            it.familyId == 1 && it.nodeId != 1
        }
    ),
    FamilyDbo(
        familyId = 2,
        fatherInfo = nodeList[1],
        motherInfo = nodeList[2],
        children = nodeList.filter {
            it.familyId == 1 && it.nodeId != 1
        }
    ),
    FamilyDbo(
        familyId = 3,
        fatherInfo = nodeList[1],
        motherInfo = nodeList[2],
        children = nodeList.filter {
            it.familyId == 1 && it.nodeId != 1
        }
    ),
    FamilyDbo(
        familyId = 4,
        fatherInfo = nodeList[1],
        motherInfo = nodeList[2],
        children = nodeList.filter {
            it.familyId == 1 && it.nodeId != 1
        }
    )
)

