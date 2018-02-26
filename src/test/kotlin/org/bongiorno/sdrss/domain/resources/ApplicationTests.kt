package org.bongiorno.sdrss.domain.resources

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.core.ParameterizedTypeReference
import org.springframework.hateoas.Resource
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment= WebEnvironment.RANDOM_PORT)
class ApplicationTests(@Autowired private val restTemplate: TestRestTemplate) {

	@Test
	fun `fetch a hateos object`() {
		val expected = Employee("Christian","christian.bongiorno@sterlingts.com")
		// a java legacy
		val type: ParameterizedTypeReference<Resource<Employee>> = object : ParameterizedTypeReference<Resource<Employee>>() {}
		val actual:ResponseEntity<Resource<Employee>> =
				restTemplate.exchange("/employees/1", HttpMethod.GET, null, type)

		assertThat(actual.body!!.content).isEqualTo(expected)

	}

}
