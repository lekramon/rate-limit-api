package br.com.lekramon.ratelimit.domain.model

data class RateLimitConfiguration(
	val enabled: Boolean,
	val dailyLimit: Long,
	val weeklyLimit: Long,
	val monthlyLimit: Long,
) {

	init {
		require(dailyLimit >= 0) { "Daily limit must not be negative." }
		require(weeklyLimit >= 0) { "Weekly limit must not be negative." }
		require(monthlyLimit >= 0) { "Monthly limit must not be negative." }
	}
}
