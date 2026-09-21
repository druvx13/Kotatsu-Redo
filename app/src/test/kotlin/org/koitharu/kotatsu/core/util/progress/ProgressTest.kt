package org.koitharu.kotatsu.core.util.progress

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ProgressTest {

	@Test
	fun percentComputation() {
		assertEquals(0.5f, Progress(1, 2).percent, 0.0001f)
		assertEquals(0f, Progress(0, 0).percent, 0.0001f)
	}

	@Test
	fun emptyAndFullFlags() {
		val empty = Progress(0, 10)
		assertTrue(empty.isEmpty)
		assertFalse(empty.isFull)
		val full = Progress(10, 10)
		assertFalse(full.isEmpty)
		assertTrue(full.isFull)
	}

	@Test
	fun indeterminateFlag() {
		assertTrue(Progress.INDETERMINATE.isIndeterminate)
		assertFalse(Progress(0, 10).isIndeterminate)
	}

	@Test
	fun incrementStopsAtTotal() {
		val progress = Progress(9, 10).inc()
		assertEquals(Progress(10, 10), progress)
		assertEquals(Progress(10, 10), progress.inc())
	}

	@Test
	fun decrementStopsAtZero() {
		val progress = Progress(1, 10).dec()
		assertEquals(Progress(0, 10), progress)
		assertEquals(Progress(0, 10), progress.dec())
	}

	@Test
	fun compareBySameTotal() {
		assertTrue(Progress(1, 10) < Progress(2, 10))
		assertEquals(0, Progress(5, 10).compareTo(Progress(5, 10)))
	}

	@Test
	fun compareByPercentWithDifferentTotals() {
		assertTrue(Progress(1, 4) < Progress(1, 2))
		assertEquals(0, Progress(1, 2).compareTo(Progress(2, 4)))
	}

	@Test
	fun plusCombinesNestedProgress() {
		val combined = Progress(2, 4) + Progress(1, 2)
		assertEquals(Progress(5, 8), combined)
		assertEquals(0.625f, combined.percent, 0.0001f)
	}

	@Test
	fun percentString() {
		assertEquals("50", Progress(1, 2).percentSting())
		assertEquals("0", Progress(0, 0).percentSting())
	}
}
