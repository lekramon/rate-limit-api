package br.com.lekramon.ratelimit.domain.model

data class RateLimitDecision(
	val allowed: Boolean,
	val reservationId: String?,
	val reason: String,
) {

	init {
		require(reason.isNotBlank()) { "Decision reason must not be blank." }
		require(reservationId == null || reservationId.isNotBlank()) {
			"Reservation id must not be blank when present."
		}
	}
}
