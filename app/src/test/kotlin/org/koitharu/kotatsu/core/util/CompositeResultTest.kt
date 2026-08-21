package org.koitharu.kotatsu.core.util

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class CompositeResultTest {

	@Test
	fun emptyResult() {
		val result = CompositeResult.EMPTY
		assertTrue(result.isEmpty)
		assertTrue(result.isAllSuccess)
		assertFalse(result.isAllFailed)
		assertEquals(0, result.size)
	}

	@Test
	fun singleSuccess() {
		val result = CompositeResult.success()
		assertFalse(result.isEmpty)
		assertTrue(result.isAllSuccess)
		assertFalse(result.isAllFailed)
		assertEquals(1, result.size)
		assertTrue(result.failures.isEmpty())
	}

	@Test
	fun singleFailure() {
		val error = RuntimeException("test")
		val result = CompositeResult.failure(error)
		assertFalse(result.isEmpty)
		assertFalse(result.isAllSuccess)
		assertTrue(result.isAllFailed)
		assertEquals(1, result.size)
		assertEquals(listOf<Throwable>(error), result.failures)
	}

	@Test
	fun plusKotlinResult() {
		val error = RuntimeException("test")
		val result = CompositeResult.EMPTY + Result.success(Unit) + Result.failure<Unit>(error)
		assertEquals(2, result.size)
		assertFalse(result.isAllSuccess)
		assertFalse(result.isAllFailed)
		assertEquals(listOf<Throwable>(error), result.failures)
	}

	@Test
	fun plusCompositeResult() {
		val error = RuntimeException("test")
		val result = CompositeResult.success() + CompositeResult.failure(error)
		assertEquals(2, result.size)
		assertFalse(result.isAllSuccess)
		assertFalse(result.isAllFailed)
		assertEquals(listOf<Throwable>(error), result.failures)
	}

	@Test
	fun equalsAndHashCode() {
		assertEquals(CompositeResult.EMPTY, CompositeResult.EMPTY + CompositeResult.EMPTY)
		assertEquals(CompositeResult.success(), CompositeResult.EMPTY + Result.success(Unit))
		assertEquals(
			(CompositeResult.EMPTY + Result.success(Unit)).hashCode(),
			CompositeResult.success().hashCode(),
		)
	}
}
