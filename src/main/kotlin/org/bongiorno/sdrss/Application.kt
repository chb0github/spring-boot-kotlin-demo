package org.bongiorno.sdrss

import org.springframework.boot.SpringApplication
import org.springframework.boot.actuate.autoconfigure.health.CompositeHealthIndicatorConfiguration
import org.springframework.boot.actuate.autoconfigure.jdbc.DataSourceHealthIndicatorAutoConfiguration
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.context.annotation.Profile
import org.springframework.data.rest.core.event.ValidatingRepositoryEventListener
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType
import org.springframework.validation.Validator

@SpringBootApplication
@EnableAutoConfiguration
class Application {

	@Configuration
	 class ValidationConfiguration(private val jsr303Validator: Validator) : RepositoryRestConfigurerAdapter() {
		override fun configureValidatingRepositoryEventListener(validatingListener: ValidatingRepositoryEventListener) {
			//bean validation always before save and create
			validatingListener.addValidator("beforeCreate", jsr303Validator)
			validatingListener.addValidator("beforeSave", jsr303Validator)

		}
	}

	@Bean
	@Profile("default")
	fun dataSource(): EmbeddedDatabase = EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).build()




}

fun main(args: Array<String>) {
	SpringApplication.run(Application::class.java, *args)
}
