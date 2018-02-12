package org.bongiorno.sdrss

import org.bongiorno.sdrss.domain.resources.Candidate
import org.bongiorno.sdrss.repositories.CandidateRepository
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.rest.core.event.ValidatingRepositoryEventListener
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter
import org.springframework.validation.Validator

@SpringBootApplication
@EnableAutoConfiguration
class Application {

	private val log = LoggerFactory.getLogger(Application::class.java)

	@Bean
	fun init(candidates: CandidateRepository) = CommandLineRunner {
			// save a couple of customers
		candidates.save(Candidate(name = "Christian"))
		candidates.save(Candidate(name = "pj"))
		candidates.save(Candidate(name= "Joe"))

	}

	@Configuration
	open class ValidationConfiguration(private val jsr303Validator: Validator) : RepositoryRestConfigurerAdapter() {
		override fun configureValidatingRepositoryEventListener(validatingListener: ValidatingRepositoryEventListener?) {
			//bean validation always before save and create
			validatingListener!!.addValidator("beforeCreate", jsr303Validator)
			validatingListener.addValidator("beforeSave", jsr303Validator)

		}
	}

}

fun main(args: Array<String>) {
	SpringApplication.run(Application::class.java, *args)
}
