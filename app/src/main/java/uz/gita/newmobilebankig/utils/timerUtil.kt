package uz.gita.newmobilebankig.utils

fun Int.arrangeTime(): String {
    val minute = this / 60
    val second = if (this > 60) this - 60 else if (this == 60) 0 else this
    val stringSecond = if (second >= 10) second.toString() else "0$second"
    return "0$minute:$stringSecond"
}

