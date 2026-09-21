package org.koitharu.kotatsu.core.util

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class AlphanumComparatorTest {

	private val comparator = AlphanumComparator()

	@Test
	fun numericChunksAreComparedNumerically() {
		assertTrue(comparator.compare("file2", "file10") < 0)
		assertTrue(comparator.compare("file10", "file2") > 0)
	}

	@Test
	fun equalStringsCompareToZero() {
		assertEquals(0, comparator.compare("chapter 1", "chapter 1"))
	}

	@Test
	fun plainStringsAreComparedLexicographically() {
		assertTrue(comparator.compare("apple", "banana") < 0)
		assertTrue(comparator.compare("banana", "apple") > 0)
	}

	@Test
	fun shorterPrefixComesFirst() {
		assertTrue(comparator.compare("file", "file1") < 0)
	}

	@Test
	fun nullValuesCompareToZero() {
		assertEquals(0, comparator.compare(null, "a"))
		assertEquals(0, comparator.compare("a", null))
		assertEquals(0, comparator.compare(null, null))
	}

	@Test
	fun sortingChapterNames() {
		val list = listOf("Vol.1 Ch.10", "Vol.1 Ch.2", "Vol.1 Ch.1", "Vol.2 Ch.1")
		val sorted = list.sortedWith(comparator)
		assertEquals(
			listOf("Vol.1 Ch.1", "Vol.1 Ch.2", "Vol.1 Ch.10", "Vol.2 Ch.1"),
			sorted,
		)
	}

	@Test
	fun longerNumericChunkIsGreater() {
		assertTrue(comparator.compare("img001", "img2") > 0)
		assertTrue(comparator.compare("img010", "img2") > 0)
	}
}
