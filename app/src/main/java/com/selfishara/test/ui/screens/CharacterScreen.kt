package com.selfishara.test.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.selfishara.test.model.Character
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
    val savedCharacters by viewModel.savedCharacters.collectAsState()

    var selectedTab by remember { mutableStateOf(0) }

    val forgeBackgroundColor = RaceUiMapper.getRaceBackgroundColor(selectedRace)
    val neutralArmyBackground = Color(0xFFF3F4F8)
    val backgroundColor = if (selectedTab == 0) forgeBackgroundColor else neutralArmyBackground

    val imageRes = RaceUiMapper.getRaceImageRes(selectedRace)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .testTag("screenBackground")
    ) {
        when (selectedTab) {
            0 -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 20.dp, vertical = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    HeaderSection()

                    Spacer(modifier = Modifier.height(20.dp))

                    MainCharacterCard {
                        NameSection(
                            userName = userName,
                            userNickName = userNickName,
                            onNameChange = viewModel::editUserName,
                            onNicknameChange = viewModel::editUserNickName
                        )

                        Spacer(modifier = Modifier.height(18.dp))

                        RaceSection(
                            selectedRace = selectedRace,
                            expanded = expanded,
                            onExpandedChange = viewModel::dropDownExpansion,
                            onRaceSelected = { race ->
                                viewModel.raceSelection(race)
                                viewModel.dropDownExpansion(false)
                            }
                        )

                        Spacer(modifier = Modifier.height(18.dp))

                        CharacterPreviewSection(
                            selectedRace = selectedRace,
                            imageRes = imageRes
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        HorizontalDivider()

                        Spacer(modifier = Modifier.height(16.dp))

                        GenderSection(
                            selectedGender = selectedGender,
                            onGenderSelected = viewModel::genderSelection
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        HorizontalDivider()

                        Spacer(modifier = Modifier.height(16.dp))

                        SkillsSection(
                            selectedSkills = selectedSkills,
                            onSkillClick = viewModel::skillSelectionChanged
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        SaveCharacterSection(
                            onSaveClick = viewModel::saveCharacter
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        SecondaryNavigationButton(
                            text = "View My Army",
                            onClick = { selectedTab = 1 }
                        )
                    }
                }
            }

            1 -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 20.dp, vertical = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    SecondaryNavigationButton(
                        text = "Back to Forge Hero",
                        onClick = { selectedTab = 0 }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "My Army",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "All the heroes you have forged",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    SavedCharactersSection(
                        characters = savedCharacters,
                        onDeleteCharacter = viewModel::deleteCharacter
                    )
                }
            }
        }
    }
}

@Composable
private fun HeaderSection() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.22f))
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text(
                text = "RPG Creator",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Create your RPG character",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.testTag("screenTitle")
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Choose a race, define a style and build your character sheet",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
private fun MainCharacterCard(
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(28.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.94f)
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            content()
        }
    }
}

@Composable
private fun SectionTitle(
    title: String,
    subtitle: String? = null
) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )
        if (subtitle != null) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }
    }
}

@Composable
private fun NameSection(
    userName: String,
    userNickName: String,
    onNameChange: (String) -> Unit,
    onNicknameChange: (String) -> Unit
) {
    SectionTitle(
        title = "Identity",
        subtitle = "Give your character a name and a nickname"
    )

    Spacer(modifier = Modifier.height(12.dp))

    OutlinedTextField(
        value = userName,
        onValueChange = onNameChange,
        label = { Text("Name") },
        modifier = Modifier
            .fillMaxWidth()
            .testTag("nameField"),
        singleLine = true,
        shape = RoundedCornerShape(18.dp)
    )

    Spacer(modifier = Modifier.height(12.dp))

    OutlinedTextField(
        value = userNickName,
        onValueChange = onNicknameChange,
        label = { Text("Nickname") },
        modifier = Modifier
            .fillMaxWidth()
            .testTag("nicknameField"),
        singleLine = true,
        shape = RoundedCornerShape(18.dp)
    )
}

@Composable
private fun RaceSection(
    selectedRace: Race?,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    onRaceSelected: (Race) -> Unit
) {
    SectionTitle(
        title = "Race",
        subtitle = "Your race changes the background and character image"
    )

    Spacer(modifier = Modifier.height(12.dp))

    RaceDropdown(
        selectedRace = selectedRace,
        expanded = expanded,
        onExpandedChange = onExpandedChange,
        onRaceSelected = onRaceSelected
    )
}

@Composable
private fun CharacterPreviewSection(
    selectedRace: Race?,
    imageRes: Int?
) {
    val raceName = selectedRace?.displayName ?: "No race selected"

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.72f)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Character Preview",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = raceName,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.DarkGray
            )

            if (imageRes != null) {
                Spacer(modifier = Modifier.height(14.dp))

                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = "raceImage",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(210.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .testTag("raceImage")
                )
            }
        }
    }
}

@Composable
private fun GenderSection(
    selectedGender: String,
    onGenderSelected: (String) -> Unit
) {
    SectionTitle(
        title = "Gender",
        subtitle = "Choose the identity that best matches your character"
    )

    Spacer(modifier = Modifier.height(10.dp))

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
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .testTag(tag),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (selected) Color(0xFFEAF4FF) else Color(0xFFF8F8F8)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .selectable(
                    selected = selected,
                    onClick = onClick,
                    role = Role.RadioButton
                )
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = selected,
                onClick = null
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
private fun SkillsSection(
    selectedSkills: Set<String>,
    onSkillClick: (String) -> Unit
) {
    SectionTitle(
        title = "Skills",
        subtitle = "Select the abilities that define your character"
    )

    Spacer(modifier = Modifier.height(10.dp))

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

@Composable
private fun SkillRow(
    skill: String,
    checked: Boolean,
    tag: String,
    onToggle: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (checked) Color(0xFFEFF8E8) else Color(0xFFF8F8F8)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = checked,
                onCheckedChange = { onToggle() },
                modifier = Modifier.testTag(tag)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = skill,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
private fun SaveCharacterSection(
    onSaveClick: () -> Unit
) {
    Button(
        onClick = onSaveClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .testTag("saveCharacterButton"),
        shape = RoundedCornerShape(18.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF5E60CE)
        )
    ) {
        Text(
            text = "Save Character",
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
private fun SecondaryNavigationButton(
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp),
        shape = RoundedCornerShape(18.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = Color(0xFF5E60CE)
        )
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
private fun SavedCharactersSection(
    characters: List<Character>,
    onDeleteCharacter: (Character) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(28.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.94f)
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            SectionTitle(
                title = "Saved Characters",
                subtitle = "Here you can see all created characters"
            )

            Spacer(modifier = Modifier.height(12.dp))

            if (characters.isEmpty()) {
                Text(
                    text = "Your army is empty... for now.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            } else {
                characters.forEachIndexed { index, character ->
                    SavedCharacterItem(
                        index = index + 1,
                        character = character,
                        onDeleteClick = { onDeleteCharacter(character) }
                    )

                    if (index < characters.lastIndex) {
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun SavedCharacterItem(
    index: Int,
    character: Character,
    onDeleteClick: () -> Unit
) {
    val raceText = character.race?.displayName ?: "Unknown"
    val skillsText = if (character.skills.isEmpty()) "No skills selected" else character.skills.joinToString()

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF7F7FB)
        )
    ) {
        Column(
            modifier = Modifier.padding(14.dp)
        ) {
            Text(
                text = "Character $index",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(text = "Name: ${character.name}")
            Text(text = "Nickname: ${character.nickName}")
            Text(text = "Race: $raceText")
            Text(text = "Gender: ${character.gender.ifBlank { "Not selected" }}")
            Text(text = "Skills: $skillsText")

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = onDeleteClick,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFC94F4F)
                )
            ) {
                Text("Delete")
            }
        }
    }
}

@Composable
private fun RaceDropdown(
    selectedRace: Race?,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    onRaceSelected: (Race) -> Unit
) {
    val raceText = selectedRace?.displayName ?: ""

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onExpandedChange(!expanded) }
            .testTag("raceDropdownField")
    ) {
        OutlinedTextField(
            value = raceText,
            onValueChange = {},
            readOnly = true,
            enabled = false,
            label = { Text("Race") },
            trailingIcon = {
                Text(if (expanded) "▲" else "▼")
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp)
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { onExpandedChange(false) },
            modifier = Modifier.fillMaxWidth(0.92f)
        ) {
            Race.entries.forEach { race ->
                DropdownMenuItem(
                    text = { Text(race.displayName) },
                    onClick = {
                        onRaceSelected(race)
                        onExpandedChange(false)
                    }
                )
            }
        }
    }
}