package br.com.lekramon.ratelimit.domain.model

data class RateLimitConfiguration(
	val enabled: Boolean,
	val limit: LimitConfiguration? = null,
) {

	init {
		require(!enabled || limit != null) { "Enabled rate limit must have limit configuration." }
		require(!enabled || limit?.hasConfiguredLimit() == true) {
			"Enabled rate limit must have at least one configured limit."
		}
	}
}
