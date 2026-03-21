package com.selfishara.test

import androidx.compose.ui.test.assertDoesNotExist
import androidx.compose.ui.test.assertExists
import androidx.compose.ui.test.assertIsOff
import androidx.compose.ui.test.assertIsOn
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.selfishara.test.ui.screens.CharacterScreen
import com.selfishara.test.viewmodel.CharacterViewModel
import org.junit.Rule
import org.junit.Test

class CharacterScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private fun setCharacterScreen() {
        composeTestRule.setContent {
            CharacterScreen(viewModel = CharacterViewModel())
        }
    }

    @Test
    fun form_initial_state_is_correct() {
        setCharacterScreen()

        composeTestRule.onNodeWithTag("nameField").assertExists().assertTextContains("")
        composeTestRule.onNodeWithTag("nicknameField").assertExists().assertTextContains("")
        composeTestRule.onNodeWithTag("raceDropdownField").assertExists().assertTextContains("")
        composeTestRule.onNodeWithTag("genderMale").assertExists()
        composeTestRule.onNodeWithTag("genderFemale").assertExists()
        composeTestRule.onNodeWithTag("genderNonBinary").assertExists()
        composeTestRule.onNodeWithTag("skillStrength").assertExists().assertIsOff()
        composeTestRule.onNodeWithTag("skillMagic").assertExists().assertIsOff()
        composeTestRule.onNodeWithTag("skillStealth").assertExists().assertIsOff()
        composeTestRule.onNodeWithTag("skillArchery").assertExists().assertIsOff()
        composeTestRule.onNodeWithTag("raceImage").assertDoesNotExist()
        composeTestRule.onNodeWithContentDescription("raceImage").assertDoesNotExist()
    }

    @Test
    fun user_can_enter_name_and_nickname() {
        setCharacterScreen()

        composeTestRule.onNodeWithTag("nameField").performTextInput("Sara")
        composeTestRule.onNodeWithTag("nicknameField").performTextInput("selfishara")

        composeTestRule.onNodeWithTag("nameField").assertTextContains("Sara")
        composeTestRule.onNodeWithTag("nicknameField").assertTextContains("selfishara")
    }

    @Test
    fun race_selection_updates_image() {
        setCharacterScreen()

        composeTestRule.onNodeWithTag("raceDropdownField").performClick()
        composeTestRule.onNodeWithText("Elf").performClick()

        composeTestRule.onNodeWithTag("raceDropdownField").assertTextContains("Elf")
        composeTestRule.onNodeWithTag("raceImage").assertExists()
        composeTestRule.onNodeWithContentDescription("raceImage").assertExists()
    }

    @Test
    fun dropdown_expands_on_click() {
        setCharacterScreen()

        composeTestRule.onNodeWithTag("raceDropdownField").performClick()

        composeTestRule.onNodeWithText("Human").assertExists()
    }

    @Test
    fun gender_selection_is_updated() {
        setCharacterScreen()

        composeTestRule.onNodeWithTag("genderFemale").performClick()

        composeTestRule.onNodeWithTag("genderFemale").assertIsSelected()
    }

    @Test
    fun skill_selection_toggle_updates_correctly() {
        setCharacterScreen()

        composeTestRule.onNodeWithTag("skillMagic").assertIsOff()
        composeTestRule.onNodeWithTag("skillMagic").performClick()
        composeTestRule.onNodeWithTag("skillMagic").assertIsOn()
        composeTestRule.onNodeWithTag("skillMagic").performClick()
        composeTestRule.onNodeWithTag("skillMagic").assertIsOff()
    }

    @Test
    fun full_form_flow_works_correctly() {
        setCharacterScreen()

        composeTestRule.onNodeWithTag("nameField").performTextInput("Sara")
        composeTestRule.onNodeWithTag("nicknameField").performTextInput("ShadowFox")

        composeTestRule.onNodeWithTag("raceDropdownField").performClick()
        composeTestRule.onNodeWithText("Hobbit").performClick()

        composeTestRule.onNodeWithTag("genderNonBinary").performClick()

        composeTestRule.onNodeWithTag("skillMagic").performClick()
        composeTestRule.onNodeWithTag("skillArchery").performClick()

        composeTestRule.onNodeWithTag("nameField").assertTextContains("Sara")
        composeTestRule.onNodeWithTag("nicknameField").assertTextContains("ShadowFox")
        composeTestRule.onNodeWithTag("raceDropdownField").assertTextContains("Hobbit")
        composeTestRule.onNodeWithTag("genderNonBinary").assertIsSelected()
        composeTestRule.onNodeWithTag("skillMagic").assertIsOn()
        composeTestRule.onNodeWithTag("skillArchery").assertIsOn()
        composeTestRule.onNodeWithTag("raceImage").assertExists()
    }
}