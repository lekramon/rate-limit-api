package br.com.lekramon.ratelimit.domain.model

import br.com.lekramon.ratelimit.domain.enums.ReservationStatus
import java.time.Instant

data class Reservation(
	val reservationId: String,
	val processType: ProcessType,
	val amount: Long,
	val status: ReservationStatus,
	val dailyWindowKey: String,
	val weeklyWindowKey: String,
	val monthlyWindowKey: String,
	val createdAt: Instant,
	val expiresAt: Instant,
) {

	init {
		require(reservationId.isNotBlank()) { "Reservation id must not be blank." }
		require(amount > 0) { "Reservation amount must be greater than zero." }
		require(dailyWindowKey.isNotBlank()) { "Daily window key must not be blank." }
		require(weeklyWindowKey.isNotBlank()) { "Weekly window key must not be blank." }
		require(monthlyWindowKey.isNotBlank()) { "Monthly window key must not be blank." }
		require(expiresAt.isAfter(createdAt)) { "Reservation expiration must be after creation." }
	}
}
