package com.example.model

/**
 * Saqi VPN Package Plan definition.
 */
data class VpnPackage(
    val id: String,
    val durationMonths: Int,
    val titleEn: String,
    val titleAr: String,
    val priceSar: Int,
    val isPopular: Boolean = false,
    val isBestValue: Boolean = false,
    val featuresEn: List<String>,
    val featuresAr: List<String>,
    val whatsAppOrderMessage: String
)

/**
 * Server node available in Saqi VPN network.
 */
data class VpnServer(
    val id: String,
    val countryName: String,
    val cityName: String,
    val flagEmoji: String,
    val pingMs: Int,
    val isOptimizedForKsa: Boolean = true,
    val loadPercentage: Int
)

/**
 * Supported Saudi telecommunications carriers.
 */
data class CarrierNetwork(
    val id: String,
    val nameEn: String,
    val nameAr: String,
    val tag: String,
    val colorHex: Long,
    val descriptionEn: String,
    val descriptionAr: String,
    val is5GReady: Boolean = true
)

/**
 * Accepted payment methods.
 */
data class PaymentMethod(
    val id: String,
    val titleEn: String,
    val titleAr: String,
    val categoryEn: String,
    val categoryAr: String,
    val iconName: String,
    val instructionsEn: String,
    val instructionsAr: String,
    val availableOptions: List<String>
)

/**
 * How it works tutorial step.
 */
data class HowItWorksStep(
    val stepNumber: Int,
    val titleEn: String,
    val titleAr: String,
    val descriptionEn: String,
    val descriptionAr: String,
    val iconName: String
)

/**
 * Frequently asked question.
 */
data class FaqItem(
    val questionEn: String,
    val questionAr: String,
    val answerEn: String,
    val answerAr: String
)

/**
 * Connection states for Saqi VPN client.
 */
enum class VpnStatus {
    DISCONNECTED,
    CONNECTING,
    CONNECTED
}
