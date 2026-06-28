package br.com.lekramon.ratelimit.bdd

import io.cucumber.java.pt.Então
import io.cucumber.java.pt.Quando
import kotlin.test.assertNotNull
import kotlin.test.assertTrue
import org.springframework.context.ApplicationContext

class ApplicationContextSteps(
	private val applicationContext: ApplicationContext,
) {

	@Quando("a aplicação de testes for iniciada")
	fun applicationTestStarted() {
		assertTrue(applicationContext.beanDefinitionCount > 0)
	}

	@Então("o contexto Spring deve subir sem erros")
	fun springContextStartsWithoutErrors() {
		assertNotNull(applicationContext)
	}
}
