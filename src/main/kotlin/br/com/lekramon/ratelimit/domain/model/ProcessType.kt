package br.com.lekramon.ratelimit.domain.model

@JvmInline
value class ProcessType(val value: String) {

	init {
		require(value.isNotBlank()) { "Process type must not be blank." }
		require(value == value.trim()) { "Process type must not have leading or trailing spaces." }
	}

	val parameterKey: String
		get() = "faturar.rateLimit.$value"

	val processKey: String
		get() = "PROCESS#$value"

	override fun toString(): String = value
}
