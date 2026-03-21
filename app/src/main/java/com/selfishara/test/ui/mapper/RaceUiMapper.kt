package com.selfishara.test.ui.mapper

import androidx.compose.ui.graphics.Color
import com.selfishara.test.R
import com.selfishara.test.model.Race

object RaceUiMapper {

    fun getRaceBackgroundColor(race: Race?): Color {
        return when (race) {
            Race.HUMAN -> Color(0xFFD9D9D9)
            Race.HOBBIT -> Color(0xFFC8E6C9)
            Race.DWARF -> Color(0xFFF8BBD0)
            Race.ELF -> Color(0xFFB3E5FC)
            Race.ORC -> Color(0xFF1E3A5F)
            null -> Color(0xFFF5F5F5)
        }
    }

    fun getRaceImageRes(race: Race?): Int? {
        return when (race) {
            Race.HUMAN -> R.drawable.human
            Race.HOBBIT -> R.drawable.hobbit
            Race.DWARF -> R.drawable.dwarf
            Race.ELF -> R.drawable.elf
            Race.ORC -> R.drawable.orc
            null -> null
        }
    }
}