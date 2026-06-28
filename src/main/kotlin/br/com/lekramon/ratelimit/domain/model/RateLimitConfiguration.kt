package br.com.lekramon.ratelimit.domain.model

data class RateLimitConfiguration(
	val enabled: Boolean,
	val dailyLimit: Long? = null,
	val weeklyLimit: Long? = null,
	val monthlyLimit: Long? = null,
) {

	init {
		require(dailyLimit == null || dailyLimit >= 0) { "Daily limit must not be negative." }
		require(weeklyLimit == null || weeklyLimit >= 0) { "Weekly limit must not be negative." }
		require(monthlyLimit == null || monthlyLimit >= 0) { "Monthly limit must not be negative." }
	}
}
