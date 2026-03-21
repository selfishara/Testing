package com.selfishara.test.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.selfishara.test.model.Race
import com.selfishara.test.ui.mapper.RaceUiMapper
import com.selfishara.test.viewmodel.CharacterViewModel

@Composable
fun CharacterScreen(
    viewModel: CharacterViewModel = viewModel()
) {
    val userName by viewModel.userName.collectAsState()
    val userNickName by viewModel.userNickName.collectAsState()
    val selectedRace by viewModel.selectedRace.collectAsState()
    val expanded by viewModel.expanded.collectAsState()
    val selectedGender by viewModel.selectedGender.collectAsState()
    val selectedSkills by viewModel.selectedSkills.collectAsState()

    val backgroundColor = RaceUiMapper.getRaceBackgroundColor(selectedRace)
    val imageRes = RaceUiMapper.getRaceImageRes(selectedRace)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .testTag("screenBackground")
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Create your RPG character",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.testTag("screenTitle")
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Choose your race, gender and skills",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(20.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    OutlinedTextField(
                        value = userName,
                        onValueChange = { viewModel.editUserName(it) },
                        label = { Text("Name") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("nameField"),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = userNickName,
                        onValueChange = { viewModel.editUserNickName(it) },
                        label = { Text("Nickname") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("nicknameField"),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    RaceDropdown(
                        selectedRace = selectedRace,
                        expanded = expanded,
                        onExpandedChange = { viewModel.dropDownExpansion(it) },
                        onRaceSelected = { race ->
                            viewModel.raceSelection(race)
                            viewModel.dropDownExpansion(false)
                        }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    if (imageRes != null) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("raceImageCard"),
                            shape = RoundedCornerShape(20.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Image(
                                    painter = painterResource(id = imageRes),
                                    contentDescription = "raceImage",
                                    modifier = Modifier
                                        .height(180.dp)
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(18.dp))
                                        .testTag("raceImage")
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    Divider()

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Gender",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    GenderSelector(
                        selectedGender = selectedGender,
                        onGenderSelected = viewModel::genderSelection
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Divider()

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Skills",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    SkillsSelector(
                        selectedSkills = selectedSkills,
                        onSkillClick = viewModel::skillSelectionChanged
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RaceDropdown(
    selectedRace: Race?,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    onRaceSelected: (Race) -> Unit
) {
    val raceText = selectedRace?.displayName ?: ""

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { onExpandedChange(!expanded) },
        modifier = Modifier
            .fillMaxWidth()
            .testTag("raceDropdownBox")
    ) {
        OutlinedTextField(
            value = raceText,
            onValueChange = {},
            readOnly = true,
            label = { Text("Race") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
                .testTag("raceDropdownField")
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { onExpandedChange(false) },
            modifier = Modifier.testTag("raceDropdownMenu")
        ) {
            Race.entries.forEach { race ->
                DropdownMenuItem(
                    text = { Text(race.displayName) },
                    onClick = { onRaceSelected(race) },
                    modifier = Modifier.testTag("raceOption_${race.displayName}")
                )
            }
        }
    }
}

@Composable
private fun GenderSelector(
    selectedGender: String,
    onGenderSelected: (String) -> Unit
) {
    Column {
        GenderRow(
            label = "Male",
            selected = selectedGender == "Male",
            tag = "genderMale",
            onClick = { onGenderSelected("Male") }
        )

        GenderRow(
            label = "Female",
            selected = selectedGender == "Female",
            tag = "genderFemale",
            onClick = { onGenderSelected("Female") }
        )

        GenderRow(
            label = "Non-binary",
            selected = selectedGender == "Non-binary",
            tag = "genderNonBinary",
            onClick = { onGenderSelected("Non-binary") }
        )
    }
}

@Composable
private fun GenderRow(
    label: String,
    selected: Boolean,
    tag: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .selectable(
                selected = selected,
                onClick = onClick,
                role = Role.RadioButton
            )
            .testTag(tag)
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected,
            onClick = null
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = label)
    }
}

@Composable
private fun SkillsSelector(
    selectedSkills: Set<String>,
    onSkillClick: (String) -> Unit
) {
    Column {
        SkillRow(
            skill = "Strength",
            checked = selectedSkills.contains("Strength"),
            tag = "skillStrength",
            onToggle = { onSkillClick("Strength") }
        )

        SkillRow(
            skill = "Magic",
            checked = selectedSkills.contains("Magic"),
            tag = "skillMagic",
            onToggle = { onSkillClick("Magic") }
        )

        SkillRow(
            skill = "Stealth",
            checked = selectedSkills.contains("Stealth"),
            tag = "skillStealth",
            onToggle = { onSkillClick("Stealth") }
        )

        SkillRow(
            skill = "Archery",
            checked = selectedSkills.contains("Archery"),
            tag = "skillArchery",
            onToggle = { onSkillClick("Archery") }
        )
    }
}

@Composable
private fun SkillRow(
    skill: String,
    checked: Boolean,
    tag: String,
    onToggle: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = { onToggle() },
            modifier = Modifier.testTag(tag)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = skill)
    }
}
