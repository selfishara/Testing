package com.selfishara.test

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.selfishara.test.model.Race
import com.selfishara.test.viewmodel.CharacterViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CharacterViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: CharacterViewModel

    @Before
    fun setup() {
        viewModel = CharacterViewModel()
    }

    @Test
    fun initialState() {
        assertEquals("", viewModel.userName.value)
        assertEquals("", viewModel.userNickName.value)
        assertEquals(null, viewModel.selectedRace.value)
        assertFalse(viewModel.expanded.value)
        assertEquals("", viewModel.selectedGender.value)
        assertTrue(viewModel.selectedSkills.value.isEmpty())
    }

    @Test
    fun editUserName_validInput_updatesUserName() {
        viewModel.editUserName("Sara")
        assertEquals("Sara", viewModel.userName.value)
    }

    @Test
    fun editUserNickName_validInput_updatesUserNickName() {
        viewModel.editUserNickName("selfishara")
        assertEquals("selfishara", viewModel.userNickName.value)
    }

    @Test
    fun raceSelection_changeValue_updateRace_Background_Image() {
        viewModel.raceSelection(Race.ELF)
        assertEquals(Race.ELF, viewModel.selectedRace.value)
    }

    @Test
    fun dropDownExpansion_changeValue_updatesExpandedValue() {
        viewModel.dropDownExpansion(true)
        assertTrue(viewModel.expanded.value)

        viewModel.dropDownExpansion(false)
        assertFalse(viewModel.expanded.value)
    }

    @Test
    fun genderSelection_changeValue_updatesSelectedGender() {
        viewModel.genderSelection("Female")
        assertEquals("Female", viewModel.selectedGender.value)
    }

    @Test
    fun skillSelectionChanged_changedValue_updatesSkillsSelection() {
        viewModel.skillSelectionChanged("Magic")
        assertTrue(viewModel.selectedSkills.value.contains("Magic"))

        viewModel.skillSelectionChanged("Magic")
        assertFalse(viewModel.selectedSkills.value.contains("Magic"))
    }

    @Test
    fun deleteCharacter_existingCharacter_removesCharacterFromList() {
        viewModel.editUserName("Sara")
        viewModel.editUserNickName("Shadow")
        viewModel.raceSelection(Race.ELF)
        viewModel.saveCharacter()

        val savedCharacter = viewModel.savedCharacters.value.first()

        viewModel.deleteCharacter(savedCharacter)

        assertTrue(viewModel.savedCharacters.value.isEmpty())
    }
}