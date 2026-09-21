package org.koitharu.kotatsu.core.util.ext

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertSame
import org.junit.Assert.assertTrue
import org.junit.Test
import java.util.EnumSet

class CollectionsExtTest {

	private enum class TestEnum { A, B, C }

	@Test
	fun asArrayListReturnsSameInstance() {
		val list = arrayListOf(1, 2, 3)
		assertSame(list, list.asArrayList())
	}

	@Test
	fun asArrayListCopiesOtherCollections() {
		val set = setOf(1, 2, 3)
		val list = set.asArrayList()
		assertEquals(listOf(1, 2, 3), list)
	}

	@Test
	fun asEnumSetReturnsSameInstance() {
		val enumSet = EnumSet.of(TestEnum.A)
		assertSame(enumSet, enumSet.asEnumSet(TestEnum::class.java))
	}

	@Test
	fun asEnumSetCopiesOtherSets() {
		val set = setOf(TestEnum.A, TestEnum.C)
		val enumSet = set.asEnumSet(TestEnum::class.java)
		assertEquals(EnumSet.of(TestEnum.A, TestEnum.C), enumSet)
	}

	@Test
	fun findKeyByValue() {
		val map = mapOf("a" to 1, "b" to 2)
		assertEquals("b", map.findKeyByValue(2))
		assertNull(map.findKeyByValue(3))
	}

	@Test
	fun toListSorted() {
		val result = sequenceOf(3, 1, 2).toListSorted(naturalOrder())
		assertEquals(listOf(1, 2, 3), result)
	}

	@Test
	fun takeMostFrequent() {
		val list = listOf("a", "b", "a", "c", "a", "b")
		assertEquals(listOf("a", "b"), list.takeMostFrequent(2))
	}

	@Test
	fun takeMostFrequentWithLimitLargerThanSize() {
		val list = listOf("a", "a", "b")
		assertEquals(listOf("a", "b"), list.takeMostFrequent(5))
	}

	@Test
	fun toEnumSet() {
		assertEquals(EnumSet.noneOf(TestEnum::class.java), emptyList<TestEnum>().toEnumSet())
		assertEquals(EnumSet.of(TestEnum.A, TestEnum.B), listOf(TestEnum.B, TestEnum.A).toEnumSet())
	}

	@Test
	fun sortedByOrdinal() {
		val result = listOf(TestEnum.C, TestEnum.A, TestEnum.B).sortedByOrdinal()
		assertEquals(listOf(TestEnum.A, TestEnum.B, TestEnum.C), result)
	}

	@Test
	fun mapSortedByCountDescending() {
		val list = listOf(1, 2, 2, 3, 3, 3)
		assertEquals(listOf(3, 2, 1), list.mapSortedByCount { it })
	}

	@Test
	fun mapSortedByCountAscending() {
		val list = listOf(1, 2, 2, 3, 3, 3)
		assertEquals(listOf(1, 2, 3), list.mapSortedByCount(isDescending = false) { it })
	}

	@Test
	fun containsIgnoreCase() {
		val list = listOf<CharSequence?>("Hello", "World", null)
		assertTrue(list.contains("hello", ignoreCase = true))
		assertFalse(list.contains("hello", ignoreCase = false))
		assertTrue(list.contains(null, ignoreCase = true))
	}

	@Test
	fun indexOfContains() {
		val list = listOf<CharSequence?>("Hello", "World")
		assertEquals(1, list.indexOfContains("orl", ignoreCase = false))
		assertEquals(0, list.indexOfContains("HELL", ignoreCase = true))
		assertEquals(-1, list.indexOfContains("xyz", ignoreCase = true))
	}
}
