package org.koitharu.kotatsu.core.util.iterator

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class MappingIteratorTest {

	@Test
	fun mapsEachElement() {
		val iterator = MappingIterator(listOf(1, 2, 3).iterator()) { it * 10 }
		val result = ArrayList<Int>()
		while (iterator.hasNext()) {
			result.add(iterator.next())
		}
		assertEquals(listOf(10, 20, 30), result)
	}

	@Test
	fun emptyUpstream() {
		val iterator = MappingIterator(emptyList<Int>().iterator()) { it.toString() }
		assertFalse(iterator.hasNext())
	}

	@Test
	fun hasNextDelegatesToUpstream() {
		val iterator = MappingIterator(listOf("a").iterator()) { it.uppercase() }
		assertTrue(iterator.hasNext())
		assertEquals("A", iterator.next())
		assertFalse(iterator.hasNext())
	}
}
