package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.SaqiVpnRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class ExampleRobolectricTest {

    @Test
    fun `read string from context`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("Saqi VPN", appName)
    }

    @Test
    fun `verify saqi vpn packages pricing and whatsapp number`() {
        val packages = SaqiVpnRepository.packages
        assertEquals(4, packages.size)
        assertEquals(20, packages[0].priceSar)
        assertEquals(40, packages[1].priceSar)
        assertEquals(60, packages[2].priceSar)
        assertEquals(120, packages[3].priceSar)

        assertEquals("+966545328482", SaqiVpnRepository.WHATSAPP_DISPLAY)
        assertTrue(packages[0].whatsAppOrderMessage.contains("20 SAR"))
        assertTrue(packages[1].whatsAppOrderMessage.contains("40 SAR"))
        assertTrue(packages[2].whatsAppOrderMessage.contains("60 SAR"))
        assertTrue(packages[3].whatsAppOrderMessage.contains("120 SAR"))
    }
}
