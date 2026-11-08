package com.example.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PowerSettingsNew
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.SignalCellularAlt
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.VpnKey
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.SaqiVpnRepository
import com.example.model.CarrierNetwork
import com.example.model.PaymentMethod
import com.example.model.VpnPackage
import com.example.model.VpnServer
import com.example.model.VpnStatus
import com.example.ui.theme.CyanDark
import com.example.ui.theme.CyanGlow
import com.example.ui.theme.ElectricBlue
import com.example.ui.theme.ElectricBlueLight
import com.example.ui.theme.NavyBorder
import com.example.ui.theme.NavyCardElevated
import com.example.ui.theme.NavyDark
import com.example.ui.theme.NavySurface
import com.example.ui.theme.NeonYellow
import com.example.ui.theme.StatusGreen
import com.example.ui.theme.StatusOrange
import com.example.ui.theme.StatusRed
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextOnCyan
import com.example.ui.theme.TextOnYellow
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.YellowAmber

@Composable
fun VpnShieldStatusWidget(
    status: VpnStatus,
    durationText: String,
    isArabic: Boolean,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = if (status == VpnStatus.CONNECTED || status == VpnStatus.CONNECTING) 1.12f else 1.02f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseScale"
    )

    val ringColor = when (status) {
        VpnStatus.CONNECTED -> StatusGreen
        VpnStatus.CONNECTING -> StatusOrange
        VpnStatus.DISCONNECTED -> ElectricBlue
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxWidth()
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(190.dp)
                .testTag("vpn_toggle_container")
        ) {
            // Outer glowing aura
            Box(
                modifier = Modifier
                    .size(185.dp)
                    .scale(pulseScale)
                    .clip(CircleShape)
                    .background(
                        Brush.radialGradient(
                            listOf(
                                ringColor.copy(alpha = if (status == VpnStatus.CONNECTED) 0.35f else 0.18f),
                                Color.Transparent
                            )
                        )
                    )
            )

            // Inner cyber ring
            Box(
                modifier = Modifier
                    .size(150.dp)
                    .border(
                        BorderStroke(
                            3.dp,
                            Brush.sweepGradient(
                                listOf(ringColor, CyanGlow, NeonYellow, ringColor)
                            )
                        ),
                        CircleShape
                    )
            )

            // Main interactive power button
            Surface(
                onClick = onToggle,
                shape = CircleShape,
                color = NavyCardElevated,
                border = BorderStroke(2.dp, ringColor.copy(alpha = 0.8f)),
                shadowElevation = 10.dp,
                modifier = Modifier
                    .size(126.dp)
                    .testTag("vpn_power_button")
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                listOf(
                                    NavyCardElevated,
                                    NavyDark
                                )
                            )
                        )
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = when (status) {
                                VpnStatus.CONNECTED -> Icons.Filled.Security
                                VpnStatus.CONNECTING -> Icons.Filled.Public
                                VpnStatus.DISCONNECTED -> Icons.Filled.PowerSettingsNew
                            },
                            contentDescription = "VPN Power Toggle",
                            tint = ringColor,
                            modifier = Modifier.size(46.dp)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = when (status) {
                                VpnStatus.CONNECTED -> if (isArabic) "متصل" else "CONNECTED"
                                VpnStatus.CONNECTING -> if (isArabic) "جاري الاتصال..." else "CONNECTING..."
                                VpnStatus.DISCONNECTED -> if (isArabic) "انقر للاتصال" else "TAP TO CONNECT"
                            },
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = ringColor,
                            letterSpacing = 0.5.sp
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Status description & duration
        if (status == VpnStatus.CONNECTED) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(StatusGreen)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "${if (isArabic) "مدة الاتصال:" else "Time Online:"} $durationText",
                    color = CyanGlow,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        } else {
            Text(
                text = if (isArabic) "حماية مشفرة 256-bit للإنترنت والمكالمات" else "256-Bit Encrypted High-Speed Tunnel",
                color = TextSecondary,
                fontSize = 12.sp
            )
        }
    }
}

@Composable
fun CarrierSelectorRow(
    carriers: List<CarrierNetwork>,
    selectedCarrier: CarrierNetwork,
    isArabic: Boolean,
    onSelectCarrier: (CarrierNetwork) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 4.dp, vertical = 6.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.SignalCellularAlt,
                contentDescription = null,
                tint = CyanGlow,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = if (isArabic) "الشبكات السعودية المدعومة" else "Supported Saudi Carriers",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = if (isArabic) "تخصيص تلقائي" else "Auto Optimized",
                fontSize = 11.sp,
                color = NeonYellow,
                fontWeight = FontWeight.Medium
            )
        }

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        ) {
            items(carriers) { carrier ->
                val isSelected = carrier.id == selectedCarrier.id
                Surface(
                    onClick = { onSelectCarrier(carrier) },
                    shape = RoundedCornerShape(12.dp),
                    color = if (isSelected) NavyCardElevated else NavySurface,
                    border = BorderStroke(
                        width = if (isSelected) 1.5.dp else 1.dp,
                        color = if (isSelected) CyanGlow else NavyBorder
                    ),
                    modifier = Modifier.testTag("carrier_${carrier.id}")
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(10.dp)
                                .clip(CircleShape)
                                .background(Color(carrier.colorHex))
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(
                                text = if (isArabic) carrier.nameAr else carrier.nameEn,
                                fontSize = 13.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                color = if (isSelected) TextPrimary else TextSecondary
                            )
                            Text(
                                text = carrier.tag,
                                fontSize = 10.sp,
                                color = if (isSelected) NeonYellow else TextMuted
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ServerSelectorCard(
    currentServer: VpnServer,
    isArabic: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(14.dp),
        color = NavySurface,
        border = BorderStroke(1.dp, NavyBorder),
        modifier = modifier
            .fillMaxWidth()
            .testTag("server_selector_card")
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(14.dp)
        ) {
            Text(
                text = currentServer.flagEmoji,
                fontSize = 28.sp,
                modifier = Modifier.padding(end = 12.dp)
            )

            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = currentServer.cityName,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    if (currentServer.isOptimizedForKsa) {
                        Spacer(modifier = Modifier.width(6.dp))
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(CyanGlow.copy(alpha = 0.2f))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "KSA VIP",
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                color = CyanGlow
                            )
                        }
                    }
                }
                Text(
                    text = "${currentServer.countryName} • ${if (isArabic) "حمل الخادم:" else "Server Load:"} ${currentServer.loadPercentage}%",
                    fontSize = 12.sp,
                    color = TextSecondary
                )
            }

            // Ping pill
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(
                        if (currentServer.pingMs < 30) StatusGreen.copy(alpha = 0.2f)
                        else ElectricBlue.copy(alpha = 0.2f)
                    )
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .clip(CircleShape)
                            .background(if (currentServer.pingMs < 30) StatusGreen else CyanGlow)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${currentServer.pingMs} ms",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (currentServer.pingMs < 30) StatusGreen else CyanGlow
                    )
                }
            }
        }
    }
}

@Composable
fun TelemetryInfoRow(
    ip: String,
    pingMs: Int,
    downloadMbps: Float,
    uploadMbps: Float,
    isArabic: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        // IP Box
        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = NavySurface),
            border = BorderStroke(1.dp, NavyBorder),
            modifier = Modifier.weight(1f)
        ) {
            Column(modifier = Modifier.padding(10.dp)) {
                Text(
                    text = if (isArabic) "عنوان الآي بي" else "VIRTUAL IP",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextMuted
                )
                Text(
                    text = ip,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = CyanGlow,
                    maxLines = 1
                )
            }
        }

        // Speed Box
        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = NavySurface),
            border = BorderStroke(1.dp, NavyBorder),
            modifier = Modifier.weight(1f)
        ) {
            Column(modifier = Modifier.padding(10.dp)) {
                Text(
                    text = if (isArabic) "السرعة الحالية" else "SPEED / LATENCY",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextMuted
                )
                Text(
                    text = if (downloadMbps > 0) "↓${downloadMbps.toInt()} ↑${uploadMbps.toInt()} Mbps" else "${pingMs}ms (Direct)",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = NeonYellow
                )
            }
        }
    }
}

@Composable
fun PackageCard(
    pkg: VpnPackage,
    isArabic: Boolean,
    onOrderWhatsApp: () -> Unit,
    modifier: Modifier = Modifier
) {
    val borderColor = when {
        pkg.isBestValue -> NeonYellow
        pkg.isPopular -> CyanGlow
        else -> NavyBorder
    }

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (pkg.isBestValue || pkg.isPopular) NavyCardElevated else NavySurface
        ),
        border = BorderStroke(if (pkg.isBestValue || pkg.isPopular) 2.dp else 1.dp, borderColor),
        elevation = CardDefaults.cardElevation(defaultElevation = if (pkg.isPopular) 8.dp else 2.dp),
        modifier = modifier
            .fillMaxWidth()
            .testTag("package_${pkg.id}")
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            // Badges row
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = if (isArabic) pkg.titleAr else pkg.titleEn,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = TextPrimary
                )

                if (pkg.isBestValue) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(NeonYellow)
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    ) {
                        Text(
                            text = if (isArabic) "أفضل توفير" else "BEST VALUE",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextOnYellow
                        )
                    }
                } else if (pkg.isPopular) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(CyanGlow)
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    ) {
                        Text(
                            text = if (isArabic) "الأكثر طلباً" else "MOST POPULAR",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextOnCyan
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Pricing display
            Row(
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "${pkg.priceSar}",
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Black,
                    color = if (pkg.isBestValue) NeonYellow else CyanGlow
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "SAR",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary,
                    modifier = Modifier.padding(bottom = 6.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "/ ${pkg.durationMonths} ${if (isArabic) "شهر" else if (pkg.durationMonths == 1) "Month" else "Months"}",
                    fontSize = 13.sp,
                    color = TextSecondary,
                    modifier = Modifier.padding(bottom = 6.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Features list
            val features = if (isArabic) pkg.featuresAr else pkg.featuresEn
            features.forEach { feature ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 3.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.CheckCircle,
                        contentDescription = null,
                        tint = if (pkg.isBestValue) NeonYellow else CyanGlow,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = feature,
                        fontSize = 12.sp,
                        color = TextSecondary
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Order via WhatsApp Button
            Button(
                onClick = onOrderWhatsApp,
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (pkg.isBestValue) NeonYellow else ElectricBlue,
                    contentColor = if (pkg.isBestValue) TextOnYellow else TextPrimary
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .testTag("order_whatsapp_${pkg.id}")
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Send,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (isArabic) "طلب الآن عبر واتساب (${pkg.priceSar} ريال)" else "Order Now via WhatsApp (${pkg.priceSar} SAR)",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun HowItWorksCard(
    stepNumber: Int,
    title: String,
    description: String,
    icon: ImageVector,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(14.dp),
        color = NavySurface,
        border = BorderStroke(1.dp, NavyBorder),
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.Top,
            modifier = Modifier.padding(16.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.radialGradient(
                            listOf(ElectricBlue, NavyCardElevated)
                        )
                    )
                    .border(1.5.dp, CyanGlow, CircleShape)
            ) {
                Text(
                    text = "$stepNumber",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Black,
                    color = CyanGlow
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = NeonYellow,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = title,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = description,
                    fontSize = 13.sp,
                    color = TextSecondary,
                    lineHeight = 18.sp
                )
            }
        }
    }
}

@Composable
fun PaymentMethodCard(
    method: PaymentMethod,
    isArabic: Boolean,
    onSelectMethod: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        onClick = onSelectMethod,
        shape = RoundedCornerShape(14.dp),
        color = NavySurface,
        border = BorderStroke(1.dp, NavyBorder),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = if (isArabic) method.titleAr else method.titleEn,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(NavyCardElevated)
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = if (isArabic) method.categoryAr else method.categoryEn,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = CyanGlow
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = if (isArabic) method.instructionsAr else method.instructionsEn,
                fontSize = 13.sp,
                color = TextSecondary,
                lineHeight = 18.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                method.availableOptions.forEach { opt ->
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(NavyDark)
                            .border(0.5.dp, NavyBorder, RoundedCornerShape(6.dp))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = opt,
                            fontSize = 10.sp,
                            color = NeonYellow
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TrialPinDialog(
    isOpen: Boolean,
    isArabic: Boolean,
    activePin: String,
    trialSecondsLeft: Int,
    onRequestWhatsApp: () -> Unit,
    onApplyPin: (String) -> Unit,
    onDismiss: () -> Unit
) {
    if (!isOpen) return

    var pinInput by remember { mutableStateOf(activePin) }
    val clipboardManager = LocalClipboardManager.current

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Filled.VpnKey,
                    contentDescription = null,
                    tint = NeonYellow,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (isArabic) "رمز تجريبي مجاني (1 ساعة)" else "1-Hour Free Trial PIN",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = TextPrimary
                )
            }
        },
        text = {
            Column {
                Text(
                    text = if (isArabic)
                        "احصل على رمز تجريبي مجاني لتجربة Saqi VPN بدون أي تكلفة لمدة ساعة كاملة على شبكات STC وموبايلي وزين وليبارا."
                    else
                        "Get a free trial PIN to test Saqi VPN at maximum speed for 1 hour on STC, Mobily, Zain, or Lebara.",
                    fontSize = 13.sp,
                    color = TextSecondary
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Request via WhatsApp button
                Button(
                    onClick = onRequestWhatsApp,
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = NeonYellow, contentColor = TextOnYellow),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(imageVector = Icons.AutoMirrored.Filled.Send, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (isArabic) "طلب الرمز عبر واتساب (+966545328482)" else "Request PIN on WhatsApp",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = if (isArabic) "أو أدخل رمز PIN المستلم:" else "Or enter your received PIN:",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextPrimary
                )

                Spacer(modifier = Modifier.height(6.dp))

                OutlinedTextField(
                    value = pinInput,
                    onValueChange = { pinInput = it },
                    placeholder = { Text("e.g. SAQI-TRIAL-8821") },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = CyanGlow,
                        unfocusedBorderColor = NavyBorder,
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                if (trialSecondsLeft < 3600 && trialSecondsLeft > 0) {
                    Spacer(modifier = Modifier.height(8.dp))
                    val min = trialSecondsLeft / 60
                    val sec = trialSecondsLeft % 60
                    Text(
                        text = if (isArabic) "الوقت التجريبي المتبقي: $min دقيقة و $sec ثانية" else "Active trial time remaining: $min m $sec s",
                        color = StatusGreen,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (pinInput.isNotBlank()) {
                        onApplyPin(pinInput)
                        onDismiss()
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = ElectricBlue),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(if (isArabic) "تفعيل الرمز" else "Activate PIN")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(if (isArabic) "إغلاق" else "Close", color = TextSecondary)
            }
        },
        containerColor = NavySurface,
        shape = RoundedCornerShape(18.dp)
    )
}

@Composable
fun ConfigDownloadUrlDialog(
    isOpen: Boolean,
    currentUrl: String,
    isArabic: Boolean,
    onSaveUrl: (String) -> Unit,
    onDismiss: () -> Unit
) {
    if (!isOpen) return

    var urlInput by remember { mutableStateOf(currentUrl) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Filled.Edit,
                    contentDescription = null,
                    tint = CyanGlow,
                    modifier = Modifier.size(22.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (isArabic) "تعديل رابط تحميل التطبيق" else "Configure Download App URL",
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp,
                    color = TextPrimary
                )
            }
        },
        text = {
            Column {
                Text(
                    text = if (isArabic)
                        "يمكنك تحديد أو تحديث رابط تنزيل ملف APK الخاص بتطبيق Saqi VPN في مكان واحد:"
                    else
                        "You can configure or change the direct download URL for the Saqi VPN APK:",
                    fontSize = 13.sp,
                    color = TextSecondary
                )
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    value = urlInput,
                    onValueChange = { urlInput = it },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = CyanGlow,
                        unfocusedBorderColor = NavyBorder,
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onSaveUrl(urlInput)
                    onDismiss()
                },
                colors = ButtonDefaults.buttonColors(containerColor = ElectricBlue),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(if (isArabic) "حفظ" else "Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(if (isArabic) "إلغاء" else "Cancel", color = TextSecondary)
            }
        },
        containerColor = NavySurface,
        shape = RoundedCornerShape(18.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServerSelectionBottomSheet(
    servers: List<VpnServer>,
    selectedServer: VpnServer,
    isArabic: Boolean,
    onSelectServer: (VpnServer) -> Unit,
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = NavySurface,
        scrimColor = NavyDark.copy(alpha = 0.7f)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 12.dp)
        ) {
            Text(
                text = if (isArabic) "اختر خادم الاتصال" else "Select VPN Server Location",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )
            Text(
                text = if (isArabic) "خوادم محسنة للاتصالات السعودية والألعاب" else "Optimized for Saudi carriers & low-latency gaming",
                fontSize = 12.sp,
                color = TextSecondary
            )

            Spacer(modifier = Modifier.height(14.dp))

            servers.forEach { srv ->
                val isSelected = srv.id == selectedServer.id
                Surface(
                    onClick = {
                        onSelectServer(srv)
                        onDismiss()
                    },
                    shape = RoundedCornerShape(12.dp),
                    color = if (isSelected) NavyCardElevated else NavyDark,
                    border = BorderStroke(1.dp, if (isSelected) CyanGlow else NavyBorder),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(14.dp)
                    ) {
                        Text(text = srv.flagEmoji, fontSize = 24.sp)
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = srv.cityName,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                            Text(
                                text = srv.countryName,
                                fontSize = 12.sp,
                                color = TextSecondary
                            )
                        }
                        Text(
                            text = "${srv.pingMs} ms",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (srv.pingMs < 30) StatusGreen else CyanGlow
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
