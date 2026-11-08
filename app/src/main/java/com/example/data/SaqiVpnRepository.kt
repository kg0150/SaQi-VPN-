package com.example.data

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import com.example.model.CarrierNetwork
import com.example.model.FaqItem
import com.example.model.HowItWorksStep
import com.example.model.PaymentMethod
import com.example.model.VpnPackage
import com.example.model.VpnServer
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

object SaqiVpnRepository {

    const val BRAND_NAME = "Saqi VPN"
    const val WHATSAPP_DISPLAY = "+966545328482"
    const val WHATSAPP_RAW_PHONE = "966545328482"
    const val DEFAULT_DOWNLOAD_URL = "https://saqivpn.com/download/saqi-vpn.apk"
    const val FREE_TRIAL_MESSAGE = "Hello Saqi VPN, I would like to request a 1-Hour Free Trial PIN for Saqi VPN."
    const val GENERAL_SUPPORT_MESSAGE = "Hello Saqi VPN, I need assistance with Saqi VPN service in Saudi Arabia."

    val packages: List<VpnPackage> = listOf(
        VpnPackage(
            id = "pkg_1m",
            durationMonths = 1,
            titleEn = "1 Month Access",
            titleAr = "باقة شهر واحد",
            priceSar = 20,
            isPopular = false,
            isBestValue = false,
            featuresEn = listOf(
                "Unlimited High-Speed Bandwidth",
                "STC, Mobily, Zain & Lebara Supported",
                "High-Speed Saudi & Global Servers",
                "Ultra-Low Ping for Gaming & Calls",
                "Instant PIN Activation via WhatsApp"
            ),
            featuresAr = listOf(
                "سعة إنترنت غير محدودة وسريعة",
                "يدعم شبكات STC وموبايلي وزين وليبارا",
                "خوادم فائقة السرعة في السعودية والعالم",
                "بنج منخفض جداً للألعاب والمكالمات",
                "تفعيل فوري لرمز PIN عبر الواتساب"
            ),
            whatsAppOrderMessage = "Hello Saqi VPN, I want to order the 1 Month package for 20 SAR."
        ),
        VpnPackage(
            id = "pkg_2m",
            durationMonths = 2,
            titleEn = "2 Months Access",
            titleAr = "باقة شهرين",
            priceSar = 40,
            isPopular = false,
            isBestValue = false,
            featuresEn = listOf(
                "60 Days Continuous High Speed",
                "Optimized Saudi Telecom Payloads",
                "Zero Logging Policy & 256-bit AES",
                "Multi-Device Support",
                "24/7 Priority WhatsApp Support"
            ),
            featuresAr = listOf(
                "٦٠ يوماً من السرعة العالية المستمرة",
                "إعدادات مخصصة لشبكات الاتصالات السعودية",
                "تشفير عالي الأمان بدون حفظ سجلات",
                "دعم لتشغيل متعدد الأجهزة",
                "دعم فني عبر الواتساب على مدار الساعة"
            ),
            whatsAppOrderMessage = "Hello Saqi VPN, I want to order the 2 Months package for 40 SAR."
        ),
        VpnPackage(
            id = "pkg_3m",
            durationMonths = 3,
            titleEn = "3 Months Access",
            titleAr = "باقة 3 أشهر",
            priceSar = 60,
            isPopular = true,
            isBestValue = false,
            featuresEn = listOf(
                "Most Popular Choice in KSA",
                "90 Days High Speed & Stability",
                "All Supported Networks (STC/Mobily/Zain/Lebara)",
                "Full HD & 4K Streaming Bypass",
                "Dedicated WhatsApp VIP Support"
            ),
            featuresAr = listOf(
                "الخيار الأكثر طلباً في السعودية",
                "٩٠ يوماً من الثبات والسرعة الفائقة",
                "جميع الشبكات (STC، موبايلي، زين، ليبارا)",
                "فتح البث بدقة HD و 4K بدون تقطيع",
                "دعم خاص لكبار العملاء عبر الواتساب"
            ),
            whatsAppOrderMessage = "Hello Saqi VPN, I want to order the 3 Months package for 60 SAR."
        ),
        VpnPackage(
            id = "pkg_6m",
            durationMonths = 6,
            titleEn = "6 Months Access",
            titleAr = "باقة 6 أشهر",
            priceSar = 120,
            isPopular = false,
            isBestValue = true,
            featuresEn = listOf(
                "Best Value Long-Term Pass",
                "180 Days Guaranteed Uptime",
                "VIP High-Bandwidth Tunneling",
                "Exclusive High-Speed Gateway Nodes",
                "Fastest Resolution for Network Block Updates"
            ),
            featuresAr = listOf(
                "أفضل قيمة للاشتراك طويل الأمد",
                "١٨٠ يوماً من الاتصال المضمون المستقر",
                "أنفاق اتصال VIP فائقة السرعة",
                "خوادم مخصصة ذات سعة عالية جداً",
                "تحديث مستمر للإعدادات لتخطي أي حظر"
            ),
            whatsAppOrderMessage = "Hello Saqi VPN, I want to order the 6 Months package for 120 SAR."
        )
    )

    val supportedCarriers: List<CarrierNetwork> = listOf(
        CarrierNetwork(
            id = "stc",
            nameEn = "STC (Sawa)",
            nameAr = "الاتصالات السعودية (STC)",
            tag = "5G / 4G",
            colorHex = 0xFF4F008C, // STC Purple
            descriptionEn = "Optimized for Sawa packages and 5G/4G high speed browsing.",
            descriptionAr = "مخصص لباقات سوا وشبكات 5G و 4G لتصفح فائق السرعة."
        ),
        CarrierNetwork(
            id = "mobily",
            nameEn = "Mobily",
            nameAr = "موبايلي",
            tag = "Fast 5G",
            colorHex = 0xFF00A3E0, // Mobily Blue
            descriptionEn = "Special low-latency payload for Mobily prepaid and postpaid lines.",
            descriptionAr = "إعدادات خاصة لباقات موبايلي مسبقة الدفع والمفوترة."
        ),
        CarrierNetwork(
            id = "zain",
            nameEn = "Zain KSA",
            nameAr = "زين السعودية",
            tag = "Low Ping",
            colorHex = 0xFF00B08B, // Zain Teal
            descriptionEn = "Dedicated UDP & TCP routing for Zain unlimited social packages.",
            descriptionAr = "توجيه سريع ومباشر لباقات زين للتواصل الاجتماعي والإنترنت."
        ),
        CarrierNetwork(
            id = "lebara",
            nameEn = "Lebara KSA",
            nameAr = "ليبارا السعودية",
            tag = "Ultra Stable",
            colorHex = 0xFF0066B3, // Lebara Blue
            descriptionEn = "High-efficiency proxy protocol tuned for Lebara network lines.",
            descriptionAr = "بروتوكول عالي الكفاءة مخصص لشرائح وشبكات ليبارا."
        )
    )

    val servers: List<VpnServer> = listOf(
        VpnServer("sa_riyadh", "Saudi Arabia", "Riyadh Ultra-Fast", "🇸🇦", 12, true, 34),
        VpnServer("sa_jeddah", "Saudi Arabia", "Jeddah Direct", "🇸🇦", 16, true, 42),
        VpnServer("ae_dubai", "United Arab Emirates", "Dubai Low Latency", "🇦🇪", 24, true, 51),
        VpnServer("de_frankfurt", "Germany", "Frankfurt Shield", "🇩🇪", 68, false, 65),
        VpnServer("gb_london", "United Kingdom", "London Cyber", "🇬🇧", 74, false, 58),
        VpnServer("sg_singapore", "Singapore", "Singapore Hub", "🇸🇬", 85, false, 48),
        VpnServer("us_ny", "United States", "New York Streaming", "🇺🇸", 118, false, 72)
    )

    val paymentMethods: List<PaymentMethod> = listOf(
        PaymentMethod(
            id = "voucher_card",
            titleEn = "Voucher Card",
            titleAr = "بطاقة قسيمة Saqi",
            categoryEn = "Official PIN",
            categoryAr = "رمز رسمي",
            iconName = "ConfirmationNumber",
            instructionsEn = "Purchase an official Saqi VPN voucher code from authorized agents. Share the voucher number on WhatsApp to get your instant PIN activated.",
            instructionsAr = "اشترِ كود قسيمة Saqi VPN من الموزعين المعتمدين، ثم أرسل رقم القسيمة عبر الواتساب لتفعيل حسابك فوراً.",
            availableOptions = listOf("Saqi Digital Code", "Agent Voucher", "Instant Activation")
        ),
        PaymentMethod(
            id = "mobile_recharge",
            titleEn = "Mobile Recharge",
            titleAr = "بطاقات شحن الجوال",
            categoryEn = "STC / Mobily / Zain",
            categoryAr = "سوا / موبايلي / زين",
            iconName = "Smartphone",
            instructionsEn = "You can pay using standard mobile recharge cards (STC Sawa, Mobily, Zain, or Lebara scratch codes). Send the recharge PIN code to WhatsApp.",
            instructionsAr = "يمكنك الدفع عبر بطاقات شحن الرصيد العادية (سوا، موبايلي، زين، أو ليبارا). أرسل رقم كود الشحن عبر الواتساب ليتم التفعيل مباشرة.",
            availableOptions = listOf("STC Sawa Card", "Mobily Recharge", "Zain Card", "Lebara Scratch")
        ),
        PaymentMethod(
            id = "zain_voucher",
            titleEn = "Zain Voucher",
            titleAr = "قسائم زين الإلكترونية",
            categoryEn = "Zain Digital",
            categoryAr = "رصيد زين",
            iconName = "ReceiptLong",
            instructionsEn = "Dedicated support for Zain prepaid electronic vouchers and digital balance codes. Instant automatic verification on WhatsApp.",
            instructionsAr = "دعم كامل لقسائم زين الإلكترونية وأكواد الرصيد الرقمي، مع معالجة وتأكيد فوري عبر الواتساب.",
            availableOptions = listOf("Zain Quick Top-up", "Digital Code", "POS Voucher")
        ),
        PaymentMethod(
            id = "bank_transfer",
            titleEn = "Bank Transfer",
            titleAr = "تحويل بنكي ومحافظ رقمية",
            categoryEn = "Instant KSA Banks",
            categoryAr = "بنوك ومحافظ سعودية",
            iconName = "AccountBalance",
            instructionsEn = "Direct local bank transfer via Al Rajhi Bank, SNB (AlAhli), STC Pay, or Urpay. Message us on WhatsApp to receive current IBAN and payment receipt verification.",
            instructionsAr = "تحويل محلي مباشر عبر بنك الراجحي، الأهلي SNB، إس تي سي باي STC Pay، أو يورباي Urpay. تواصل عبر الواتساب للحصول على رقم الآيبان والتفعيل الفوري.",
            availableOptions = listOf("Al Rajhi Bank", "SNB Bank", "STC Pay", "Urpay")
        )
    )

    val howItWorksSteps: List<HowItWorksStep> = listOf(
        HowItWorksStep(
            stepNumber = 1,
            titleEn = "Download App",
            titleAr = "تحميل التطبيق",
            descriptionEn = "Install the latest Saqi VPN application on your Android smartphone, tablet, or device.",
            descriptionAr = "قم بتثبيت أحدث إصدار من تطبيق Saqi VPN على هاتفك الذكي أو جهازك.",
            iconName = "Download"
        ),
        HowItWorksStep(
            stepNumber = 2,
            titleEn = "Contact Us",
            titleAr = "تواصل معنا",
            descriptionEn = "Click the WhatsApp button to message our official support (+966545328482) or request a 1-Hour Free Trial PIN.",
            descriptionAr = "انقر على زر الواتساب لمراسلتنا (+966545328482) لطلب كود تجريبي مجاني لمدة ساعة أو طلب باقة.",
            iconName = "Chat"
        ),
        HowItWorksStep(
            stepNumber = 3,
            titleEn = "Send Payment",
            titleAr = "إرسال الدفع",
            descriptionEn = "Select your convenient payment method: Mobile Recharge Card, Voucher PIN, or Bank Transfer / STC Pay.",
            descriptionAr = "اختر وسيلة الدفع المناسبة لك: بطاقة شحن رصيد، قسيمة، أو تحويل بنكي / STC Pay.",
            iconName = "CreditCard"
        ),
        HowItWorksStep(
            stepNumber = 4,
            titleEn = "Receive PIN & Connect",
            titleAr = "استلام الرمز والاتصال",
            descriptionEn = "Receive your unique access PIN on WhatsApp, enter it into the app, and enjoy unrestricted high-speed internet!",
            descriptionAr = "استلم كود التفعيل PIN عبر الواتساب، أدخله في التطبيق واستمتع بإنترنت سريع وغير مقيد!",
            iconName = "VpnKey"
        )
    )

    val faqs: List<FaqItem> = listOf(
        FaqItem(
            questionEn = "How does the 1-Hour Free Trial PIN work?",
            questionAr = "كيف يعمل الرمز التجريبي المجاني لمدة ساعة؟",
            answerEn = "Simply click 'Free Trial PIN' to open WhatsApp with our official number (+966545328482). Our team will provide you with a complimentary 1-hour PIN to test high-speed connection on STC, Mobily, Zain, or Lebara with zero commitment.",
            answerAr = "ببساطة اضغط على 'رمز تجريبي مجاني' لتفتح محادثة الواتساب مع رقمنا الرسمي (+966545328482). سيزودك فريقنا برمز تجريبي مجاني لمدة ساعة لتجربة السرعة والثبات على شبكتك دون أي التزام."
        ),
        FaqItem(
            questionEn = "Which Saudi networks are supported?",
            questionAr = "ما هي الشبكات السعودية المدعومة؟",
            answerEn = "Saqi VPN is custom-optimized for STC (Sawa), Mobily, Zain KSA, and Lebara. It works seamlessly on 5G, 4G, and fiber home connections.",
            answerAr = "تطبيق Saqi VPN مصمم ومخصص للعمل بأعلى كفاءة على شبكات الاتصالات السعودية (سوا STC)، وموبايلي، وزين، وليبارا، ويدعم شبكات 5G و 4G والألياف."
        ),
        FaqItem(
            questionEn = "Can I pay using a regular mobile recharge card?",
            questionAr = "هل يمكنني الدفع باستخدام كرت شحن جوال عادي؟",
            answerEn = "Yes! You can buy standard Sawa, Mobily, or Zain scratch/recharge vouchers from any grocery or store and send the code to our WhatsApp for instant activation.",
            answerAr = "نعم بكل تأكيد! يمكنك شراء بطاقة شحن عادية (سوا، موبايلي، أو زين) من أي بقالة أو متجر وإرسال رقم الكود عبر الواتساب لتفعيل حسابك فوراً."
        ),
        FaqItem(
            questionEn = "How fast is Saqi VPN for gaming and calls?",
            questionAr = "ما مدى سرعة Saqi VPN للألعاب والمكالمات؟",
            answerEn = "Our ultra-low latency Saudi & regional nodes provide ping as low as 12ms, ideal for PUBG, Free Fire, WhatsApp calling, IMO, and HD streaming.",
            answerAr = "توفر خوادمنا المحلية والإقليمية سرعة فائقة مع بنج منخفض يصل إلى 12 ملي ثانية، مما يجعله مثالياً لألعاب مثل ببجي وفري فاير ومكالمات الواتساب والبث المباشر."
        )
    )

    fun openWhatsApp(context: Context, message: String) {
        try {
            val encodedMessage = URLEncoder.encode(message, StandardCharsets.UTF_8.toString())
            val uri = Uri.parse("https://api.whatsapp.com/send?phone=$WHATSAPP_RAW_PHONE&text=$encodedMessage")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            intent.setPackage("com.whatsapp")
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        } catch (_: Exception) {
            // Fallback to browser or business WhatsApp or standard view
            try {
                val encodedMessage = URLEncoder.encode(message, StandardCharsets.UTF_8.toString())
                val browserUri = Uri.parse("https://wa.me/$WHATSAPP_RAW_PHONE?text=$encodedMessage")
                val fallbackIntent = Intent(Intent.ACTION_VIEW, browserUri)
                fallbackIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(fallbackIntent)
            } catch (e: Exception) {
                Toast.makeText(context, "Could not open WhatsApp. Contact: $WHATSAPP_DISPLAY", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun openUrl(context: Context, url: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "Unable to open link: $url", Toast.LENGTH_SHORT).show()
        }
    }
}
