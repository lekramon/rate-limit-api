package br.com.lekramon.ratelimit.bdd

import io.cucumber.java.pt.Então
import io.cucumber.java.pt.Quando
import kotlin.test.assertNotNull
import kotlin.test.assertTrue
import org.springframework.context.ConfigurableApplicationContext

class ApplicationContextSteps(
	private val applicationContext: ConfigurableApplicationContext,
) {

	@Quando("a aplicação for iniciada")
	fun applicationTestStarted() {
		assertTrue(applicationContext.isActive)
	}

	@Então("o contexto Spring deve subir sem erros")
	fun springContextStartsWithoutErrors() {
		assertNotNull(applicationContext)
	}
}
