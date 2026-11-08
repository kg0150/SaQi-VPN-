package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.SaqiVpnRepository
import com.example.ui.components.CarrierSelectorRow
import com.example.ui.components.ServerSelectorCard
import com.example.ui.components.TelemetryInfoRow
import com.example.ui.components.VpnShieldStatusWidget
import com.example.ui.theme.CyanGlow
import com.example.ui.theme.ElectricBlue
import com.example.ui.theme.NavyBorder
import com.example.ui.theme.NavyCardElevated
import com.example.ui.theme.NavyDark
import com.example.ui.theme.NavySurface
import com.example.ui.theme.NeonYellow
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextOnYellow
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.viewmodel.SaqiVpnUiState
import com.example.viewmodel.SaqiVpnViewModel

@Composable
fun HomeScreen(
    uiState: SaqiVpnUiState,
    viewModel: SaqiVpnViewModel,
    onOpenServerPicker: () -> Unit,
    onOpenTrialDialog: () -> Unit,
    onNavigatePackages: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val isArabic = uiState.isArabic

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Hero Card with Shield Illustration & Tagline
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = NavySurface),
                border = androidx.compose.foundation.BorderStroke(1.dp, NavyBorder),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
                    .testTag("hero_section")
            ) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    // Background Hero Graphic
                    Image(
                        painter = painterResource(id = R.drawable.saqi_vpn_hero),
                        contentDescription = "VPN Shield Illustration",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                            .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                    )

                    // Dark gradient overlay for text readability
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                            .background(
                                Brush.verticalGradient(
                                    listOf(
                                        NavyDark.copy(alpha = 0.3f),
                                        NavySurface.copy(alpha = 0.95f)
                                    )
                                )
                            )
                    )

                    // Hero Content
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .border(1.dp, CyanGlow, CircleShape)
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.saqi_vpn_icon),
                                    contentDescription = "Saqi VPN Logo",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = "SAQI VPN",
                                    fontSize = 17.sp,
                                    fontWeight = FontWeight.Black,
                                    color = CyanGlow,
                                    letterSpacing = 1.sp
                                )
                                Text(
                                    text = if (isArabic) "حماية فائقة • خوادم سعودية" else "Fast, Secure and Reliable VPN",
                                    fontSize = 11.sp,
                                    color = TextSecondary
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(30.dp))

                        Text(
                            text = if (isArabic) "إنترنت سريع، آمن ومستقر في السعودية" else "Fast, Secure & Reliable VPN",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = TextPrimary,
                            lineHeight = 24.sp
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = if (isArabic)
                                "مخصص لشبكات STC وموبايلي وزين وليبارا لتشغيل الألعاب والمكالمات وتخطي الحظر بدون تقطيع."
                            else
                                "Optimized for STC, Mobily, Zain & Lebara. Ultra-low ping, zero buffers & instant access.",
                            fontSize = 12.sp,
                            color = TextSecondary,
                            lineHeight = 16.sp
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        // Quick action CTA buttons
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            // Download App Button
                            Button(
                                onClick = { viewModel.openDownloadApp(context) },
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = ElectricBlue,
                                    contentColor = TextPrimary
                                ),
                                modifier = Modifier
                                    .weight(1f)
                                    .height(44.dp)
                                    .testTag("btn_download_app")
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Filled.Download,
                                        contentDescription = null,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = if (isArabic) "تحميل التطبيق" else "Download App",
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }

                            // Free Trial PIN Button
                            Button(
                                onClick = onOpenTrialDialog,
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = NeonYellow,
                                    contentColor = TextOnYellow
                                ),
                                modifier = Modifier
                                    .weight(1f)
                                    .height(44.dp)
                                    .testTag("btn_free_trial_hero")
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Filled.Key,
                                        contentDescription = null,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = if (isArabic) "رمز تجريبي (1 س)" else "Free Trial PIN",
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // Supported Carriers Selector
        item {
            CarrierSelectorRow(
                carriers = SaqiVpnRepository.supportedCarriers,
                selectedCarrier = uiState.selectedCarrier,
                isArabic = isArabic,
                onSelectCarrier = { viewModel.selectCarrier(it) }
            )
        }

        // VPN Power Shield Widget
        item {
            VpnShieldStatusWidget(
                status = uiState.vpnStatus,
                durationText = viewModel.formatDuration(uiState.connectionSeconds),
                isArabic = isArabic,
                onToggle = { viewModel.toggleConnection() }
            )
        }

        // Telemetry Data (Virtual IP & Speed)
        item {
            TelemetryInfoRow(
                ip = uiState.virtualIp,
                pingMs = uiState.pingMs,
                downloadMbps = uiState.downloadSpeedMbps,
                uploadMbps = uiState.uploadSpeedMbps,
                isArabic = isArabic
            )
        }

        // Server Selection Card
        item {
            ServerSelectorCard(
                currentServer = uiState.selectedServer,
                isArabic = isArabic,
                onClick = onOpenServerPicker
            )
        }

        // Quick Packages Banner CTA
        item {
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = NavyCardElevated),
                border = androidx.compose.foundation.BorderStroke(1.dp, CyanGlow.copy(alpha = 0.5f)),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("packages_quick_banner")
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(14.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(NeonYellow.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = null,
                            tint = NeonYellow,
                            modifier = Modifier.size(22.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = if (isArabic) "باقات شهرية تبدأ من 20 ريال فقط" else "Subscriptions start at 20 SAR",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                        Text(
                            text = if (isArabic) "1، 2، 3 و 6 أشهر مع تفعيل واتساب فوري" else "1, 2, 3 & 6 Months with instant WhatsApp PIN",
                            fontSize = 11.sp,
                            color = TextSecondary
                        )
                    }

                    Button(
                        onClick = onNavigatePackages,
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = CyanGlow, contentColor = NavyDark)
                    ) {
                        Text(
                            text = if (isArabic) "الأسعار" else "View",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}
