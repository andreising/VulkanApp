package com.andreisingeleytsev.vulkanapp.ui.utils

sealed class UIEvents(){
    data class OnNavigate(val route: String): UIEvents()
    object OnBack: UIEvents()
    object Roll: UIEvents()
}
