package br.com.lekramon.ratelimit.domain.model

import br.com.lekramon.ratelimit.domain.enums.PeriodType
import java.time.Instant

data class LimitWindow(
	val processType: ProcessType,
	val periodType: PeriodType,
	val period: String,
	val limit: Long,
	val available: Long,
	val reserved: Long,
	val consumed: Long,
	val expiresAt: Instant,
) {

	init {
		require(period.isNotBlank()) { "Period must not be blank." }
		require(limit >= 0) { "Limit must not be negative." }
		require(available >= 0) { "Available amount must not be negative." }
		require(reserved >= 0) { "Reserved amount must not be negative." }
		require(consumed >= 0) { "Consumed amount must not be negative." }
		require(available + reserved + consumed == limit) {
			"Available, reserved and consumed amounts must add up to limit."
		}
	}
}
