package uz.gita.newmobilebankig.utils

import androidx.viewbinding.ViewBinding

fun <T : ViewBinding> T.scope(block : T.() ->Unit) {
    block(this)
}