package com.example.kunbaapp.ui.shared.Timeline

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun BoxExample() {
    Box(Modifier.fillMaxSize()) {
        Text("This text is drawn first", modifier = Modifier.align(Alignment.TopCenter))
        Box(
            Modifier
                .align(Alignment.TopCenter)
                .fillMaxHeight()
                .width(
                    50.dp
                )
                .background(Color.Blue)
        ){
            Text(text = "Text Inside the box")
        }
        Text("This text is drawn last", modifier = Modifier.align(Alignment.Center))
        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(12.dp),
            onClick = {}
        ) {
            Text("+")
        }
    }
}


@Composable
fun BoxExampleV1() {
    Box(Modifier.fillMaxSize()) {
        //Text("This text is drawn first", modifier = Modifier.align(Alignment.TopCenter))
        Box(modifier = Modifier.align(Alignment.TopCenter)) {
            Column{
                Card(
                    modifier = Modifier
                        .fillMaxWidth(0.65f)
                        //.align(Alignment.CenterStart)
                        //.align(Alignment.CenterEnd)
                        .clickable { },
                    colors = CardDefaults.cardColors(Color.Green)
                ) {
                    //val fullName = "${hiringStage.initiator.firstName} ${hiringStage.initiator.lastName}"
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(12.dp),
                        text = "Hello World hkhkhskhfkhlkfhlshflshlsfhlshflshflhslfhlkfkjsdjsjasdks eued dhjshjhj jhjdhjdhj sjhdjh",
                        textAlign = TextAlign.Start,//getTextAlign(hiringStage),
                        //style = getTextStyle(hiringStage),
                        //fontWeight = getFontWeight(hiringStage),
                        //color = getTextColor(hiringStage)
                    )
                }
            }
        }

        Box(
            Modifier
                .align(Alignment.TopCenter)
                .fillMaxHeight()
                .width(
                    50.dp
                )
                .background(Color.Blue)
        ){
            Text(text = "Text Inside the box")
        }

        //Text("This text is drawn last", modifier = Modifier.align(Alignment.Center))
        Box(modifier = Modifier.align(Alignment.Center)) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.65f)
                    //.align(Alignment.CenterStart)
                    .align(Alignment.CenterEnd)
                    .clickable { },
                colors = CardDefaults.cardColors(Color.Yellow)
            ) {
                //val fullName = "${hiringStage.initiator.firstName} ${hiringStage.initiator.lastName}"
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(12.dp),
                    text = "Hello World",
                    textAlign = TextAlign.Start,//getTextAlign(hiringStage),
                    //style = getTextStyle(hiringStage),
                    //fontWeight = getFontWeight(hiringStage),
                    //color = getTextColor(hiringStage)
                )
            }
        }
        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(12.dp),
            onClick = {}
        ) {
            Text("+")
        }
    }
}