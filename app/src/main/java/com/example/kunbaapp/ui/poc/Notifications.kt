package com.example.kunbaapp.ui.poc

import androidx.compose.runtime.Composable
import org.koin.androidx.compose.getViewModel

@Composable
fun Notifications(
    //viewModel: NotificationsViewModel = getViewModel<NotificationsViewModel>()
    viewModel: KunbaNotificationsViewModel = getViewModel<KunbaNotificationsViewModel>()
) {

}