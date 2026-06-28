package br.com.lekramon.ratelimit.bdd

import br.com.lekramon.ratelimit.RateLimitApplication
import io.cucumber.spring.CucumberContextConfiguration
import org.springframework.boot.test.context.SpringBootTest

@CucumberContextConfiguration
@SpringBootTest(classes = [RateLimitApplication::class])
class CucumberSpringConfiguration
