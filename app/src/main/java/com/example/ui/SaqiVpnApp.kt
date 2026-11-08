package com.example.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.R
import com.example.data.SaqiVpnRepository
import com.example.model.VpnStatus
import com.example.ui.components.ConfigDownloadUrlDialog
import com.example.ui.components.ServerSelectionBottomSheet
import com.example.ui.components.TrialPinDialog
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.HowItWorksScreen
import com.example.ui.screens.PackagesScreen
import com.example.ui.screens.PaymentsScreen
import com.example.ui.theme.CyanGlow
import com.example.ui.theme.ElectricBlue
import com.example.ui.theme.NavyBorder
import com.example.ui.theme.NavyCardElevated
import com.example.ui.theme.NavyDark
import com.example.ui.theme.NavySurface
import com.example.ui.theme.NeonYellow
import com.example.ui.theme.StatusGreen
import com.example.ui.theme.StatusOrange
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.viewmodel.SaqiVpnViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SaqiVpnApp(
    viewModel: SaqiVpnViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }

    var selectedTab by remember { mutableIntStateOf(0) }
    var showServerPicker by remember { mutableStateOf(false) }
    var showTrialDialog by remember { mutableStateOf(false) }
    var showConfigUrlDialog by remember { mutableStateOf(false) }

    val isArabic = uiState.isArabic

    // Toast/Snackbar notifications
    LaunchedEffect(uiState.lastNotification) {
        uiState.lastNotification?.let { msg ->
            snackbarHostState.showSnackbar(msg)
            viewModel.clearNotification()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.testTag("app_bar_title")
                    ) {
                        Box(
                            modifier = Modifier
                                .size(30.dp)
                                .clip(CircleShape)
                                .border(1.dp, CyanGlow, CircleShape)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.saqi_vpn_icon),
                                contentDescription = "Saqi VPN",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "SAQI VPN",
                            fontWeight = FontWeight.Black,
                            fontSize = 17.sp,
                            color = CyanGlow,
                            letterSpacing = 1.sp
                        )
                    }
                },
                navigationIcon = {
                    // Status dot
                    Box(
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(
                                when (uiState.vpnStatus) {
                                    VpnStatus.CONNECTED -> StatusGreen
                                    VpnStatus.CONNECTING -> StatusOrange
                                    VpnStatus.DISCONNECTED -> ElectricBlue
                                }
                            )
                    )
                },
                actions = {
                    // Language switch button (English / Arabic)
                    IconButton(
                        onClick = { viewModel.toggleLanguage() },
                        modifier = Modifier.testTag("btn_language_toggle")
                    ) {
                        Text(
                            text = if (isArabic) "EN" else "عربي",
                            color = NeonYellow,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                    }

                    // Direct WhatsApp button
                    IconButton(
                        onClick = { viewModel.contactSupport(context) },
                        modifier = Modifier.testTag("btn_appbar_whatsapp")
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Chat,
                            contentDescription = "WhatsApp Support",
                            tint = StatusGreen
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = NavyDark
                )
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = NavySurface,
                contentColor = TextPrimary,
                modifier = Modifier
                    .windowInsetsPadding(WindowInsets.navigationBars)
                    .testTag("bottom_navigation_bar")
            ) {
                // Tab 0: Home / VPN
                NavigationBarItem(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.Security,
                            contentDescription = "VPN"
                        )
                    },
                    label = {
                        Text(
                            text = if (isArabic) "الرئيسية" else "VPN",
                            fontSize = 11.sp,
                            fontWeight = if (selectedTab == 0) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = CyanGlow,
                        selectedTextColor = CyanGlow,
                        unselectedIconColor = TextMuted,
                        unselectedTextColor = TextMuted,
                        indicatorColor = NavyCardElevated
                    )
                )

                // Tab 1: Packages
                NavigationBarItem(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    icon = {
                        BadgedBox(
                            badge = {
                                Badge(containerColor = NeonYellow) {
                                    Text("20 SAR", fontSize = 8.sp, color = NavyDark, fontWeight = FontWeight.Bold)
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.LocalOffer,
                                contentDescription = "Packages"
                            )
                        }
                    },
                    label = {
                        Text(
                            text = if (isArabic) "الباقات" else "Packages",
                            fontSize = 11.sp,
                            fontWeight = if (selectedTab == 1) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = CyanGlow,
                        selectedTextColor = CyanGlow,
                        unselectedIconColor = TextMuted,
                        unselectedTextColor = TextMuted,
                        indicatorColor = NavyCardElevated
                    )
                )

                // Tab 2: How It Works
                NavigationBarItem(
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 },
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.Info,
                            contentDescription = "How It Works"
                        )
                    },
                    label = {
                        Text(
                            text = if (isArabic) "الخطوات" else "How It Works",
                            fontSize = 11.sp,
                            fontWeight = if (selectedTab == 2) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = CyanGlow,
                        selectedTextColor = CyanGlow,
                        unselectedIconColor = TextMuted,
                        unselectedTextColor = TextMuted,
                        indicatorColor = NavyCardElevated
                    )
                )

                // Tab 3: Payments & Support
                NavigationBarItem(
                    selected = selectedTab == 3,
                    onClick = { selectedTab = 3 },
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.AccountBalance,
                            contentDescription = "Payments"
                        )
                    },
                    label = {
                        Text(
                            text = if (isArabic) "الدفع" else "Payments",
                            fontSize = 11.sp,
                            fontWeight = if (selectedTab == 3) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = CyanGlow,
                        selectedTextColor = CyanGlow,
                        unselectedIconColor = TextMuted,
                        unselectedTextColor = TextMuted,
                        indicatorColor = NavyCardElevated
                    )
                )
            }
        },
        containerColor = NavyDark
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (selectedTab) {
                0 -> HomeScreen(
                    uiState = uiState,
                    viewModel = viewModel,
                    onOpenServerPicker = { showServerPicker = true },
                    onOpenTrialDialog = { showTrialDialog = true },
                    onNavigatePackages = { selectedTab = 1 }
                )
                1 -> PackagesScreen(
                    uiState = uiState,
                    viewModel = viewModel,
                    onOpenTrialDialog = { showTrialDialog = true }
                )
                2 -> HowItWorksScreen(
                    uiState = uiState,
                    viewModel = viewModel
                )
                3 -> PaymentsScreen(
                    uiState = uiState,
                    viewModel = viewModel,
                    onOpenConfigDialog = { showConfigUrlDialog = true },
                    onOpenTrialDialog = { showTrialDialog = true }
                )
            }

            // Server Selection Bottom Sheet
            if (showServerPicker) {
                ServerSelectionBottomSheet(
                    servers = SaqiVpnRepository.servers,
                    selectedServer = uiState.selectedServer,
                    isArabic = isArabic,
                    onSelectServer = { viewModel.selectServer(it) },
                    onDismiss = { showServerPicker = false }
                )
            }

            // 1-Hour Free Trial PIN Dialog
            TrialPinDialog(
                isOpen = showTrialDialog,
                isArabic = isArabic,
                activePin = uiState.activePin,
                trialSecondsLeft = uiState.trialTimeRemainingSeconds,
                onRequestWhatsApp = {
                    viewModel.requestFreeTrial(context)
                },
                onApplyPin = { pin ->
                    viewModel.applyPin(pin)
                },
                onDismiss = { showTrialDialog = false }
            )

            // Configurable App Download URL Dialog
            ConfigDownloadUrlDialog(
                isOpen = showConfigUrlDialog,
                currentUrl = uiState.downloadAppUrl,
                isArabic = isArabic,
                onSaveUrl = { newUrl ->
                    viewModel.updateDownloadUrl(newUrl)
                },
                onDismiss = { showConfigUrlDialog = false }
            )
        }
    }
}
