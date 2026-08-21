package org.koitharu.kotatsu.core.util

import org.junit.Assert.assertEquals
import org.junit.Test

class FileSizeTest {

	@Test
	fun convertBytesToKilobytes() {
		assertEquals(2L, FileSize.BYTES.convert(2048, FileSize.KILOBYTES))
	}

	@Test
	fun convertKilobytesToBytes() {
		assertEquals(2048L, FileSize.KILOBYTES.convert(2, FileSize.BYTES))
	}

	@Test
	fun convertMegabytesToKilobytes() {
		assertEquals(3072L, FileSize.MEGABYTES.convert(3, FileSize.KILOBYTES))
	}

	@Test
	fun convertKilobytesToMegabytes() {
		assertEquals(5L, FileSize.KILOBYTES.convert(5 * 1024, FileSize.MEGABYTES))
	}

	@Test
	fun convertSameUnitIsIdentity() {
		assertEquals(42L, FileSize.BYTES.convert(42, FileSize.BYTES))
		assertEquals(42L, FileSize.KILOBYTES.convert(42, FileSize.KILOBYTES))
		assertEquals(42L, FileSize.MEGABYTES.convert(42, FileSize.MEGABYTES))
	}

	@Test
	fun convertTruncatesFractionalResult() {
		assertEquals(0L, FileSize.BYTES.convert(1023, FileSize.KILOBYTES))
		assertEquals(1L, FileSize.BYTES.convert(2047, FileSize.KILOBYTES))
	}

	@Test
	fun convertZero() {
		assertEquals(0L, FileSize.MEGABYTES.convert(0, FileSize.BYTES))
	}
}
