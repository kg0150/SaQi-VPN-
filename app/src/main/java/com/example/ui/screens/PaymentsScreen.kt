package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Payment
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.SaqiVpnRepository
import com.example.ui.components.PaymentMethodCard
import com.example.ui.theme.CyanGlow
import com.example.ui.theme.ElectricBlue
import com.example.ui.theme.NavyBorder
import com.example.ui.theme.NavyCardElevated
import com.example.ui.theme.NavyDark
import com.example.ui.theme.NavySurface
import com.example.ui.theme.NeonYellow
import com.example.ui.theme.StatusGreen
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextOnYellow
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.viewmodel.SaqiVpnUiState
import com.example.viewmodel.SaqiVpnViewModel

@Composable
fun PaymentsScreen(
    uiState: SaqiVpnUiState,
    viewModel: SaqiVpnViewModel,
    onOpenConfigDialog: () -> Unit,
    onOpenTrialDialog: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current
    val isArabic = uiState.isArabic
    var copiedWhatsApp by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
            ) {
                Text(
                    text = if (isArabic) "طرق الدفع والدعم الفني" else "Payment Methods & Support",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = TextPrimary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = if (isArabic)
                        "وسائل دفع محلية مرنة ومتاحة في جميع أنحاء المملكة العربية السعودية."
                    else
                        "Convenient local payment options available across Saudi Arabia.",
                    fontSize = 13.sp,
                    color = TextSecondary
                )
            }
        }

        // WhatsApp Official Card
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = NavyCardElevated),
                border = BorderStroke(1.5.dp, StatusGreen),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("official_whatsapp_card")
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .size(38.dp)
                                    .clip(CircleShape)
                                    .background(StatusGreen.copy(alpha = 0.2f))
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Chat,
                                    contentDescription = null,
                                    tint = StatusGreen,
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = if (isArabic) "خدمة عملاء الواتساب المباشرة" else "Official WhatsApp Hotline",
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TextPrimary
                                )
                                Text(
                                    text = SaqiVpnRepository.WHATSAPP_DISPLAY,
                                    fontSize = 13.sp,
                                    color = StatusGreen,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }

                        IconButton(
                            onClick = {
                                clipboardManager.setText(AnnotatedString(SaqiVpnRepository.WHATSAPP_DISPLAY))
                                copiedWhatsApp = true
                            }
                        ) {
                            Icon(
                                imageVector = if (copiedWhatsApp) Icons.Filled.Check else Icons.Filled.ContentCopy,
                                contentDescription = "Copy WhatsApp Number",
                                tint = if (copiedWhatsApp) StatusGreen else CyanGlow
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = if (isArabic)
                            "تواصل معنا لإرسال كود الدفع، الحصول على رقم الآيبان للتحويل البنكي، أو طلب كود PIN فوري."
                        else
                            "Contact us directly to submit payment codes, get bank transfer IBAN, or receive your instant subscription PIN.",
                        fontSize = 12.sp,
                        color = TextSecondary,
                        lineHeight = 16.sp
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = { viewModel.contactSupport(context) },
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = StatusGreen,
                            contentColor = NavyDark
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(44.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.Send,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = if (isArabic) "فتح محادثة واتساب الآن" else "Open WhatsApp Chat Now",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }

        // 4 Payment Methods
        items(SaqiVpnRepository.paymentMethods) { method ->
            PaymentMethodCard(
                method = method,
                isArabic = isArabic,
                onSelectMethod = {
                    val prompt = if (isArabic)
                        "مرحباً Saqi VPN، أريد الدفع عن طريق: ${method.titleAr}"
                    else
                        "Hello Saqi VPN, I want to pay via: ${method.titleEn}"
                    SaqiVpnRepository.openWhatsApp(context, prompt)
                }
            )
        }

        // App Download URL Configurator Card (Requirement 8)
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = NavySurface),
                border = BorderStroke(1.dp, NavyBorder),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("download_url_config_card")
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Filled.Download,
                                contentDescription = null,
                                tint = CyanGlow,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = if (isArabic) "رابط تحميل التطبيق القابل للتخصيص" else "Configurable Download App URL",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                        }

                        IconButton(onClick = onOpenConfigDialog) {
                            Icon(
                                imageVector = Icons.Filled.Edit,
                                contentDescription = "Edit Download URL",
                                tint = CyanGlow
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = uiState.downloadAppUrl,
                        fontSize = 12.sp,
                        color = NeonYellow,
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Button(
                            onClick = { viewModel.openDownloadApp(context) },
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = ElectricBlue),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = if (isArabic) "تجربة التحميل" else "Test Download",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        OutlinedButton(
                            onClick = onOpenConfigDialog,
                            shape = RoundedCornerShape(8.dp),
                            border = BorderStroke(1.dp, CyanGlow),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = if (isArabic) "تعديل الرابط" else "Change URL",
                                fontSize = 12.sp,
                                color = CyanGlow
                            )
                        }
                    }
                }
            }
        }

        // Active PIN verification / Free Trial manager
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = NavySurface),
                border = BorderStroke(1.dp, NavyBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Filled.Key,
                            contentDescription = null,
                            tint = NeonYellow,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (isArabic) "إدارة رمز التفعيل (PIN)" else "Manage Subscription PIN",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    if (uiState.activePin.isNotBlank()) {
                        Text(
                            text = "${if (isArabic) "الرمز المفعل حالياً:" else "Current Active PIN:"} ${uiState.activePin}",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = StatusGreen
                        )
                        if (uiState.isTrialActive) {
                            val remainingFormatted = viewModel.formatTrialRemaining(uiState.trialTimeRemainingSeconds)
                            Text(
                                text = "${if (isArabic) "الوقت المتبقي للتجربة:" else "Trial Time Left:"} $remainingFormatted",
                                fontSize = 11.sp,
                                color = NeonYellow
                            )
                        }
                    } else {
                        Text(
                            text = if (isArabic)
                                "لم تقم بتفعيل رمز PIN بعد. اطلب كود التجربة المجانية أو كود الاشتراك لتفعيله هنا."
                            else
                                "No active PIN yet. Request a 1-Hour Free Trial or enter your package PIN.",
                            fontSize = 12.sp,
                            color = TextSecondary
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Button(
                        onClick = onOpenTrialDialog,
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = NavyCardElevated, contentColor = CyanGlow),
                        border = BorderStroke(1.dp, CyanGlow),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = if (isArabic) "إدخال أو طلب رمز تجريبي PIN" else "Enter / Request Trial PIN",
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
