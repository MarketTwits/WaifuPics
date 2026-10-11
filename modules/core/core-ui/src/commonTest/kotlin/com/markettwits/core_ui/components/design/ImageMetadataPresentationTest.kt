package com.markettwits.core_ui.components.design

import kotlin.test.*

class ImageMetadataPresentationTest {
    @Test fun paletteRejectsIncompleteColorsAndCopiesCanonicalHex() {
        val swatches = imagePaletteSwatches(listOf(emptyList(), listOf(1, 2), listOf(0, 128, 255),
            listOf(-1, 128, 999, 123), listOf(255, 255, 255)))
        assertEquals(listOf("#0080FF", "#FFFFFF"), swatches.map { it.hex })
    }
    @Test fun onlyProvidedWebSourcesOfferOpenAndCopyActions() {
        for (source in listOf("", "https://", "javascript:alert(1)", "file:///photo", "example.com", "https://example.com/bad path")) {
            assertNull(imageSourceHost(source))
        }
        assertEquals("example.com", imageSourceHost("https://www.example.com/art?id=12#source"))
        assertEquals("example.com", imageSourceHost("http://example.com/art"))
    }
}
