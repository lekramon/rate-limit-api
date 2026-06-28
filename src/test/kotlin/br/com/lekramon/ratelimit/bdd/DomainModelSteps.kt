package br.com.lekramon.ratelimit.bdd

import br.com.lekramon.ratelimit.domain.model.LimitWindow
import br.com.lekramon.ratelimit.domain.model.ProcessType
import br.com.lekramon.ratelimit.domain.model.RateLimitConfiguration
import br.com.lekramon.ratelimit.domain.model.RateLimitDecision
import br.com.lekramon.ratelimit.domain.model.Reservation
import br.com.lekramon.ratelimit.domain.enums.PeriodType
import br.com.lekramon.ratelimit.domain.enums.ReservationStatus
import io.cucumber.java.pt.Dado
import io.cucumber.java.pt.E
import io.cucumber.java.pt.Então
import io.cucumber.java.pt.Quando
import java.time.Instant
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

class DomainModelSteps {

	private var windowLimit: Long = 0
	private var windowResult: Result<LimitWindow>? = null
	private var processType: ProcessType? = null
	private var parameterKey: String? = null
	private var reservationStatusNames: Set<String> = emptySet()
	private var configuration: RateLimitConfiguration? = null
	private var reservation: Reservation? = null
	private var decision: RateLimitDecision? = null

	@Dado("uma janela diária com limite {int}")
	fun dailyWindowWithLimit(limit: Int) {
		windowLimit = limit.toLong()
	}

	@Quando("disponível for {int}, reservado {int} e consumido {int}")
	fun createWindowWithCounters(available: Int, reserved: Int, consumed: Int) {
		windowResult = runCatching {
			LimitWindow(
				processType = ProcessType("parce-compras"),
				periodType = PeriodType.DAILY,
				period = "2026-06-28",
				limit = windowLimit,
				available = available.toLong(),
				reserved = reserved.toLong(),
				consumed = consumed.toLong(),
				expiresAt = Instant.parse("2026-06-29T00:00:00Z"),
			)
		}
	}

	@Então("a janela deve ser válida")
	fun windowMustBeValid() {
		assertTrue(windowResult?.isSuccess == true)
	}

	@Então("a janela deve ser rejeitada")
	fun windowMustBeRejected() {
		assertTrue(windowResult?.isFailure == true)
	}

	@Dado("o processType {string}")
	fun processType(value: String) {
		processType = ProcessType(value)
	}

	@Quando("a chave do parâmetro for montada")
	fun buildParameterKey() {
		parameterKey = processType?.parameterKey
	}

	@Então("a chave deve ser {string}")
	fun parameterKeyMustBe(expected: String) {
		assertEquals(expected, parameterKey)
	}

	@Quando("eu consultar os status possíveis da reserva")
	fun reservationStatusesAreRequested() {
		reservationStatusNames = ReservationStatus.entries.map { it.name }.toSet()
	}

	@Então("os status devem ser {string}, {string}, {string} e {string}")
	fun statusesMustBe(first: String, second: String, third: String, fourth: String) {
		assertEquals(setOf(first, second, third, fourth), reservationStatusNames)
	}

	@Quando("eu criar uma configuração habilitada com limite diário {int}, semanal {int} e mensal {int}")
	fun createEnabledConfiguration(dailyLimit: Int, weeklyLimit: Int, monthlyLimit: Int) {
		configuration = RateLimitConfiguration(
			enabled = true,
			dailyLimit = dailyLimit.toLong(),
			weeklyLimit = weeklyLimit.toLong(),
			monthlyLimit = monthlyLimit.toLong(),
		)
	}

	@Quando("eu criar uma configuração habilitada com limite diário {int} e mensal {int}")
	fun createEnabledConfigurationWithDailyAndMonthly(dailyLimit: Int, monthlyLimit: Int) {
		configuration = RateLimitConfiguration(
			enabled = true,
			dailyLimit = dailyLimit.toLong(),
			monthlyLimit = monthlyLimit.toLong(),
		)
	}

	@Quando("eu criar uma configuração habilitada somente com limite mensal {int}")
	fun createEnabledConfigurationWithOnlyMonthly(monthlyLimit: Int) {
		configuration = RateLimitConfiguration(
			enabled = true,
			monthlyLimit = monthlyLimit.toLong(),
		)
	}

	@Então("a configuração deve estar habilitada")
	fun configurationMustBeEnabled() {
		assertTrue(configuration?.enabled == true)
	}

	@E("os limites devem ser diário {int}, semanal {int} e mensal {int}")
	fun configurationLimitsMustBe(dailyLimit: Int, weeklyLimit: Int, monthlyLimit: Int) {
		assertEquals(dailyLimit.toLong(), configuration?.dailyLimit)
		assertEquals(weeklyLimit.toLong(), configuration?.weeklyLimit)
		assertEquals(monthlyLimit.toLong(), configuration?.monthlyLimit)
	}

	@E("os limites devem ser diário {int}, semanal não configurado e mensal {int}")
	fun configurationLimitsMustHaveDailyAndMonthly(dailyLimit: Int, monthlyLimit: Int) {
		assertEquals(dailyLimit.toLong(), configuration?.dailyLimit)
		assertNull(configuration?.weeklyLimit)
		assertEquals(monthlyLimit.toLong(), configuration?.monthlyLimit)
	}

	@E("os limites diário e semanal não devem estar configurados")
	fun dailyAndWeeklyLimitsMustNotBeConfigured() {
		assertNull(configuration?.dailyLimit)
		assertNull(configuration?.weeklyLimit)
	}

	@E("o limite mensal deve ser {int}")
	fun monthlyLimitMustBe(monthlyLimit: Int) {
		assertEquals(monthlyLimit.toLong(), configuration?.monthlyLimit)
	}

	@Quando("eu criar uma reserva pendente de {int} unidade para o processType {string}")
	fun createPendingReservation(amount: Int, processType: String) {
		reservation = Reservation(
			reservationId = "res_123",
			processType = ProcessType(processType),
			amount = amount.toLong(),
			status = ReservationStatus.PENDING,
			dailyWindowKey = "LIMIT_WINDOW#DAILY#2026-06-28",
			weeklyWindowKey = "LIMIT_WINDOW#WEEKLY#2026-W26",
			monthlyWindowKey = "LIMIT_WINDOW#MONTHLY#2026-06",
			createdAt = Instant.parse("2026-06-28T00:00:00Z"),
			expiresAt = Instant.parse("2026-06-28T00:05:00Z"),
		)
	}

	@Então("a reserva deve ter status {string}")
	fun reservationStatusMustBe(status: String) {
		assertEquals(status, reservation?.status?.name)
	}

	@E("o valor reservado deve ser {int}")
	fun reservationAmountMustBe(amount: Int) {
		assertEquals(amount.toLong(), reservation?.amount)
	}

	@Quando("uma decisão permitir postagem sem reservationId por {string}")
	fun createAllowedDecisionWithoutReservation(reason: String) {
		decision = RateLimitDecision(
			allowed = true,
			reservationId = null,
			reason = reason,
		)
	}

	@Então("a decisão deve permitir a postagem")
	fun decisionMustAllowPosting() {
		assertTrue(decision?.allowed == true)
	}

	@E("não deve conter reservationId")
	fun decisionMustNotHaveReservationId() {
		assertNull(decision?.reservationId)
	}
}
