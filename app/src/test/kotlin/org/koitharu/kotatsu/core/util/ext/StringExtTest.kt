package org.koitharu.kotatsu.core.util.ext

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class StringExtTest {

	@Test
	fun toUUIDOrNullValid() {
		val uuid = "123e4567-e89b-12d3-a456-426614174000".toUUIDOrNull()
		assertNotNull(uuid)
		assertEquals("123e4567-e89b-12d3-a456-426614174000", uuid.toString())
	}

	@Test
	fun toUUIDOrNullInvalid() {
		assertNull("not-a-uuid".toUUIDOrNull())
	}

	@Test
	fun transliterateCyrillic() {
		assertEquals("privet", "привет".transliterate(false))
		assertEquals("CHasi", "Часы".transliterate(false))
	}

	@Test
	fun transliterateKeepsMissingChars() {
		assertEquals("abc-123", "abc-123".transliterate(false))
	}

	@Test
	fun transliterateSkipsMissingChars() {
		assertEquals("mir", "мир abc".transliterate(true))
	}

	@Test
	fun toFileNameSafe() {
		assertEquals("One_Piece", "One Piece".toFileNameSafe())
		assertEquals("privet_mir", "привет мир".toFileNameSafe())
		assertEquals("a_b_c", "a/b\\c".toFileNameSafe())
	}

	@Test
	fun sanitizeRemovesReplacementChars() {
		assertEquals("abc", "a\uFFFDb\uFFF0c".sanitize().toString())
		assertEquals("plain", "plain".sanitize().toString())
	}

	@Test
	fun isReplacementChar() {
		assertTrue('\uFFFD'.isReplacement())
		assertFalse('a'.isReplacement())
	}

	@Test
	fun isHttpUrl() {
		assertTrue("https://example.com".isHttpUrl())
		assertTrue("HTTP://example.com".isHttpUrl())
		assertFalse("ftp://example.com".isHttpUrl())
		assertFalse("example.com".isHttpUrl())
	}
}
