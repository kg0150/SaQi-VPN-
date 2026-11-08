# 🛡️ Saqi VPN — Android Application

Fast, secure, and reliable Android VPN client application and mobile brand portal, custom-optimized for Saudi Arabia telecommunication networks (**STC**, **Mobily**, **Zain KSA**, and **Lebara KSA**).

---

## 📋 Project Specifications

| Property | Value |
| :--- | :--- |
| **Application Name** | Saqi VPN |
| **Application ID** | `com.aistudio.saqivpn.kxmpzq` |
| **Project Type** | Native Android (Kotlin + Jetpack Compose) |
| **Gradle Version** | `9.3.1` |
| **Android Gradle Plugin (AGP)** | `9.1.1` |
| **Kotlin Version** | `2.2.10` |
| **Compile SDK / Target SDK** | `36` (Android 15 / 16 preview) |
| **Minimum Supported Android (Min SDK)** | `24` (Android 7.0+) |
| **Java JDK Version** | `21` (Eclipse Temurin) |
| **Build Configuration** | Standard Android Gradle with Compose M3 |
| **Official WhatsApp Line** | `+966545328482` |

---

## 🚀 Key Features

1. **Carrier-Specific Optimizations:**
   - **STC (Sawa):** 5G / 4G high-speed streaming and browsing.
   - **Mobily:** Low-latency routing for prepaid and postpaid lines.
   - **Zain KSA:** Dedicated UDP & TCP tunneling for social packages.
   - **Lebara KSA:** Tuned proxy configurations.

2. **Subscription Plans & Direct WhatsApp Ordering:**
   - **1 Month:** 20 SAR
   - **2 Months:** 40 SAR
   - **3 Months:** 60 SAR *(Most Popular)*
   - **6 Months:** 120 SAR *(Best Value)*
   - Automated pre-filled WhatsApp order messages.

3. **1-Hour Free Trial PIN:**
   - Instant trial code request directly via WhatsApp with an active countdown timer.

4. **Multiple Payment Methods:**
   - Voucher Card, Mobile Recharge (Sawa / Mobily / Zain), Zain Digital Voucher, and Bank Transfer / STC Pay.

5. **Bilingual UI:**
   - Full English and Arabic (`عربي`) interface toggle.

---

## 🛠️ Building the Debug APK Locally

Run the following command in the project root:

```bash
# Using Gradle wrapper
./gradlew :app:assembleDebug

# Or using installed Gradle 9.3.1+
gradle :app:assembleDebug
```

The compiled APK will be generated at:
```
app/build/outputs/apk/debug/app-debug.apk
```

---

## 🤖 GitHub Actions CI/CD (Automatic APK Build & Release)

The project includes an automated GitHub Actions workflow in `.github/workflows/build-apk.yml`.

### Workflow Triggers:
1. **Push to `main` branch:** Automatically compiles the Debug APK and creates/updates a GitHub Release.
2. **Version tags (`v*`):** Automatically compiles and publishes a tagged release (e.g. `v1.0.0`).
3. **Manual Trigger (`workflow_dispatch`):** Allows building anytime with a custom version tag from the GitHub Actions tab.

---

## 📲 How to Connect to GitHub and Get Your APK

### Step 1: Push Project to GitHub
1. Create a new repository on your GitHub account (e.g., `saqi-vpn-android`).
2. If using the AI Studio Git/Export panel:
   - Click the **Git / GitHub** icon or Export menu in AI Studio.
   - Connect your GitHub account and push this branch to your repository.
3. Or using terminal:
   ```bash
   git init
   git add .
   git commit -m "Initial Saqi VPN Android release"
   git branch -M main
   git remote add origin https://github.com/<YOUR_USERNAME>/saqi-vpn-android.git
   git push -u origin main
   ```

### Step 2: Trigger GitHub Actions Build
- As soon as you push to `main`, the **"Build and Publish Saqi VPN Debug APK"** workflow will start automatically.
- To run manually:
  1. Open your repository on GitHub.
  2. Click on the **Actions** tab.
  3. In the left sidebar, select **"Build and Publish Saqi VPN Debug APK"**.
  4. Click **Run workflow** ➔ specify your version (e.g. `v1.0.0`) ➔ Click **Run workflow**.

### Step 3: Download the APK
You can download the generated APK in two ways:
- **Option A (GitHub Releases - Recommended):**
  1. Go to your repository's homepage.
  2. On the right sidebar, click **Releases**.
  3. Under the latest release (e.g., `v1.0.0`), download **`Saqi-VPN-v1.0.0-debug.apk`**.
- **Option B (Actions Artifacts):**
  1. Click the **Actions** tab.
  2. Click on the latest workflow run.
  3. Scroll down to the **Artifacts** section at the bottom.
  4. Click **`Saqi-VPN-Debug-APK`** to download the zip file containing the APK.

### Step 4: Install on Your Android Phone
1. Transfer the `.apk` file to your phone (via USB, WhatsApp, Google Drive, or by downloading it directly from the GitHub Releases page on your phone's browser).
2. Tap the downloaded `Saqi-VPN-v1.0.0-debug.apk` file.
3. If Android prompts *"For your security, your phone is not allowed to install unknown apps from this source"*, tap **Settings** and toggle **Allow from this source**.
4. Tap **Install** ➔ Tap **Open** to launch Saqi VPN.

---

## 🔄 Publishing Future Updates
When you make changes to the app:
1. Update `versionCode` and `versionName` in `app/build.gradle.kts` if incrementing the app version.
2. Commit and push your changes to `main`, or create a new Git tag:
   ```bash
   git tag v1.0.1
   git push origin v1.0.1
   ```
3. GitHub Actions will automatically compile the new build and publish `Saqi-VPN-v1.0.1-debug.apk`.
