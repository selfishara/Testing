package com.selfishara.test.viewmodel

import androidx.lifecycle.ViewModel
import com.selfishara.test.model.Race
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CharacterViewModel : ViewModel() {

    private val _userName = MutableStateFlow("")
    val userName: StateFlow<String> = _userName.asStateFlow()

    private val _userNickName = MutableStateFlow("")
    val userNickName: StateFlow<String> = _userNickName.asStateFlow()

    private val _selectedRace = MutableStateFlow<Race?>(null)
    val selectedRace: StateFlow<Race?> = _selectedRace.asStateFlow()

    private val _expanded = MutableStateFlow(false)
    val expanded: StateFlow<Boolean> = _expanded.asStateFlow()

    private val _selectedGender = MutableStateFlow("")
    val selectedGender: StateFlow<String> = _selectedGender.asStateFlow()

    private val _selectedSkills = MutableStateFlow(setOf<String>())
    val selectedSkills: StateFlow<Set<String>> = _selectedSkills.asStateFlow()

    fun editUserName(name: String) {
        _userName.value = name
    }

    fun editUserNickName(nickName: String) {
        _userNickName.value = nickName
    }

    fun raceSelection(race: Race) {
        _selectedRace.value = race
    }

    fun dropDownExpansion(isExpanded: Boolean) {
        _expanded.value = isExpanded
    }

    fun genderSelection(gender: String) {
        _selectedGender.value = gender
    }

    fun skillSelectionChanged(skill: String) {
        _selectedSkills.value = if (_selectedSkills.value.contains(skill)) {
            _selectedSkills.value - skill
        } else {
            _selectedSkills.value + skill
        }
    }
}