package br.com.lekramon.ratelimit.domain.model

data class LimitConfiguration(
	val daily: Long? = null,
	val weekly: Long? = null,
	val monthly: Long? = null,
) {

	init {
		require(daily == null || daily >= 0) { "Daily limit must not be negative." }
		require(weekly == null || weekly >= 0) { "Weekly limit must not be negative." }
		require(monthly == null || monthly >= 0) { "Monthly limit must not be negative." }
	}

	fun hasConfiguredLimit(): Boolean = daily != null || weekly != null || monthly != null
}
