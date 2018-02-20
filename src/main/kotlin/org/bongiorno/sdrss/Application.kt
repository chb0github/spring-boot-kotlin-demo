package org.bongiorno.sdrss

import org.bongiorno.sdrss.domain.resources.Candidate
import org.bongiorno.sdrss.repositories.domain.CandidateRepository
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.rest.core.event.ValidatingRepositoryEventListener
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType
import org.springframework.validation.Validator
import javax.sql.DataSource

@SpringBootApplication
@EnableAutoConfiguration
class Application {


	@Configuration
	 class ValidationConfiguration(private val jsr303Validator: Validator) : RepositoryRestConfigurerAdapter() {
		override fun configureValidatingRepositoryEventListener(validatingListener: ValidatingRepositoryEventListener?) {
			//bean validation always before save and create
			validatingListener!!.addValidator("beforeCreate", jsr303Validator)
			validatingListener.addValidator("beforeSave", jsr303Validator)

		}
	}

	@Bean
	fun dataSource(): DataSource {
		// no need shutdown, EmbeddedDatabaseFactoryBean will take care of this
		return EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.HSQL).build()
	}
}

fun main(args: Array<String>) {
	SpringApplication.run(Application::class.java, *args)
}
