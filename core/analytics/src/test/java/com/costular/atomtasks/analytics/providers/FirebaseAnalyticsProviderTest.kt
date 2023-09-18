package com.costular.atomtasks.analytics.providers

import com.costular.atomtasks.analytics.TrackingEvent
import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class FirebaseAnalyticsProviderTest {

    lateinit var sut: FirebaseAnalyticsProvider

    @Before
    fun setUp() {
        sut = FirebaseAnalyticsProvider()
    }

    @Test
    fun `should parse attributes as bundle correctly`() {
        val trackingEvent = TrackingEvent(
            name = "test",
            attributes = mapOf(
                "first" to 1,
                "second" to "two",
                "third" to true,
                "four" to 123L,
            )
        )

        val bundle = trackingEvent.paramsAsBundle()

        Truth.assertThat(bundle!!.getString("first")).isEqualTo("1")
        Truth.assertThat(bundle.getString("second")).isEqualTo("two")
        Truth.assertThat(bundle.getString("third")).isEqualTo("true")
        Truth.assertThat(bundle.getString("four")).isEqualTo("123")
    }
}
