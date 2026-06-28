package br.com.lekramon.ratelimit.bdd

import io.cucumber.java.pt.Então
import io.cucumber.java.pt.Quando
import kotlin.test.assertTrue
import org.springframework.context.ConfigurableApplicationContext

class ApplicationContextSteps(
	private val applicationContext: ConfigurableApplicationContext,
) {

	@Quando("a aplicação for iniciada")
	fun applicationTestStarted() {
		// O contexto já foi iniciado pelo Spring/Cucumber antes da execução deste step.
	}

	@Então("o contexto Spring deve subir sem erros")
	fun springContextStartsWithoutErrors() {
		assertTrue(applicationContext.isActive)
		assertTrue(applicationContext.beanDefinitionCount > 0)
	}
}
