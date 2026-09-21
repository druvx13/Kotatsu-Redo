package org.koitharu.kotatsu.core.util.ext

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import org.koitharu.kotatsu.core.ui.model.DateTimeAgo
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.temporal.ChronoUnit

class DateExtTest {

	@Test
	fun justNow() {
		assertEquals(DateTimeAgo.JustNow, calculateTimeAgo(Instant.now()))
	}

	@Test
	fun yesterday() {
		val instant = Instant.now().minus(1, ChronoUnit.DAYS)
		assertEquals(DateTimeAgo.Yesterday, calculateTimeAgo(instant))
	}

	@Test
	fun daysAgo() {
		val instant = Instant.now().minus(3, ChronoUnit.DAYS)
		assertEquals(DateTimeAgo.DaysAgo(3), calculateTimeAgo(instant))
	}

	@Test
	fun monthsAgo() {
		val localDate = LocalDate.now().minusMonths(2)
		val instant = localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()
		assertEquals(DateTimeAgo.MonthsAgo(2), calculateTimeAgo(instant, showMonths = true))
	}

	@Test
	fun absoluteDateWhenMonthsDisabled() {
		val localDate = LocalDate.now().minusMonths(2)
		val instant = localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()
		assertEquals(DateTimeAgo.Absolute(localDate), calculateTimeAgo(instant, showMonths = false))
	}

	@Test
	fun futureDateReturnsNull() {
		val instant = Instant.now().plus(2, ChronoUnit.DAYS)
		assertNull(calculateTimeAgo(instant))
	}

	@Test
	fun toInstantOrNull() {
		assertNull(0L.toInstantOrNull())
		assertEquals(Instant.ofEpochMilli(1000), 1000L.toInstantOrNull())
	}
}
