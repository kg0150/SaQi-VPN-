package com.example.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.SaqiVpnRepository
import com.example.model.CarrierNetwork
import com.example.model.VpnPackage
import com.example.model.VpnServer
import com.example.model.VpnStatus
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.random.Random

data class SaqiVpnUiState(
    val vpnStatus: VpnStatus = VpnStatus.DISCONNECTED,
    val selectedServer: VpnServer = SaqiVpnRepository.servers.first(),
    val selectedCarrier: CarrierNetwork = SaqiVpnRepository.supportedCarriers.first(),
    val connectionSeconds: Long = 0L,
    val downloadSpeedMbps: Float = 0f,
    val uploadSpeedMbps: Float = 0f,
    val virtualIp: String = "192.168.1.100 (Direct ISP)",
    val pingMs: Int = 14,
    val downloadAppUrl: String = SaqiVpnRepository.DEFAULT_DOWNLOAD_URL,
    val activePin: String = "",
    val isArabic: Boolean = false,
    val isTrialActive: Boolean = false,
    val trialTimeRemainingSeconds: Int = 3600,
    val lastNotification: String? = null
)

class SaqiVpnViewModel(application: Application) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow(SaqiVpnUiState())
    val uiState: StateFlow<SaqiVpnUiState> = _uiState.asStateFlow()

    private var connectionJob: Job? = null
    private var trialTimerJob: Job? = null

    fun toggleConnection() {
        when (_uiState.value.vpnStatus) {
            VpnStatus.DISCONNECTED -> startConnecting()
            VpnStatus.CONNECTING -> disconnect()
            VpnStatus.CONNECTED -> disconnect()
        }
    }

    private fun startConnecting() {
        connectionJob?.cancel()
        _uiState.update {
            it.copy(
                vpnStatus = VpnStatus.CONNECTING,
                lastNotification = if (it.isArabic) "جاري تأسيس نفق الاتصال المشفر..." else "Establishing encrypted tunnel..."
            )
        }

        connectionJob = viewModelScope.launch {
            delay(1200) // Realistic connection handshake simulation
            val server = _uiState.value.selectedServer
            val carrier = _uiState.value.selectedCarrier
            val generatedIp = "185.${Random.nextInt(100, 250)}.${Random.nextInt(10, 200)}.${Random.nextInt(2, 250)}"

            _uiState.update {
                it.copy(
                    vpnStatus = VpnStatus.CONNECTED,
                    virtualIp = generatedIp,
                    pingMs = (server.pingMs + Random.nextInt(-2, 4)).coerceAtLeast(8),
                    lastNotification = if (it.isArabic) "متصل بنجاح عبر شبكة ${carrier.nameAr}" else "Connected via ${carrier.nameEn}"
                )
            }

            // Connection metrics loop
            var seconds = 0L
            while (_uiState.value.vpnStatus == VpnStatus.CONNECTED) {
                delay(1000)
                seconds++
                val downSpeed = Random.nextDouble(58.0, 96.5).toFloat()
                val upSpeed = Random.nextDouble(25.0, 48.0).toFloat()
                _uiState.update {
                    it.copy(
                        connectionSeconds = seconds,
                        downloadSpeedMbps = downSpeed,
                        uploadSpeedMbps = upSpeed
                    )
                }
            }
        }
    }

    private fun disconnect() {
        connectionJob?.cancel()
        _uiState.update {
            it.copy(
                vpnStatus = VpnStatus.DISCONNECTED,
                connectionSeconds = 0L,
                downloadSpeedMbps = 0f,
                uploadSpeedMbps = 0f,
                virtualIp = "192.168.1.100 (Direct ISP)",
                lastNotification = if (it.isArabic) "تم قطع الاتصال" else "VPN Disconnected"
            )
        }
    }

    fun selectServer(server: VpnServer) {
        _uiState.update { it.copy(selectedServer = server) }
        if (_uiState.value.vpnStatus == VpnStatus.CONNECTED) {
            startConnecting()
        }
    }

    fun selectCarrier(carrier: CarrierNetwork) {
        _uiState.update { it.copy(selectedCarrier = carrier) }
        if (_uiState.value.vpnStatus == VpnStatus.CONNECTED) {
            startConnecting()
        }
    }

    fun toggleLanguage() {
        _uiState.update { it.copy(isArabic = !it.isArabic) }
    }

    fun updateDownloadUrl(newUrl: String) {
        if (newUrl.isNotBlank()) {
            _uiState.update { it.copy(downloadAppUrl = newUrl.trim()) }
        }
    }

    fun applyPin(pin: String) {
        _uiState.update {
            it.copy(
                activePin = pin.trim(),
                isTrialActive = true,
                trialTimeRemainingSeconds = 3600,
                lastNotification = if (it.isArabic) "تم تفعيل الرمز التجريبي بنجاح!" else "PIN Activated Successfully!"
            )
        }
        startTrialCountdown()
    }

    private fun startTrialCountdown() {
        trialTimerJob?.cancel()
        trialTimerJob = viewModelScope.launch {
            while (_uiState.value.isTrialActive && _uiState.value.trialTimeRemainingSeconds > 0) {
                delay(1000)
                _uiState.update {
                    it.copy(trialTimeRemainingSeconds = it.trialTimeRemainingSeconds - 1)
                }
            }
            if (_uiState.value.trialTimeRemainingSeconds <= 0) {
                _uiState.update {
                    it.copy(
                        isTrialActive = false,
                        lastNotification = if (it.isArabic) "انتهت فترة التجربة المجانية (ساعة)" else "1-Hour Free Trial expired"
                    )
                }
            }
        }
    }

    fun orderPackage(context: Context, pkg: VpnPackage) {
        SaqiVpnRepository.openWhatsApp(context, pkg.whatsAppOrderMessage)
    }

    fun requestFreeTrial(context: Context) {
        SaqiVpnRepository.openWhatsApp(context, SaqiVpnRepository.FREE_TRIAL_MESSAGE)
    }

    fun contactSupport(context: Context) {
        SaqiVpnRepository.openWhatsApp(context, SaqiVpnRepository.GENERAL_SUPPORT_MESSAGE)
    }

    fun openDownloadApp(context: Context) {
        SaqiVpnRepository.openUrl(context, _uiState.value.downloadAppUrl)
    }

    fun clearNotification() {
        _uiState.update { it.copy(lastNotification = null) }
    }

    fun formatDuration(totalSeconds: Long): String {
        val hours = totalSeconds / 3600
        val minutes = (totalSeconds % 3600) / 60
        val seconds = totalSeconds % 60
        return String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }

    fun formatTrialRemaining(secondsLeft: Int): String {
        val minutes = secondsLeft / 60
        val seconds = secondsLeft % 60
        return String.format("%02d:%02d", minutes, seconds)
    }
}
