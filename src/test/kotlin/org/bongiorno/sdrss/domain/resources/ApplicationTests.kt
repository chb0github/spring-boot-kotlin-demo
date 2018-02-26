package org.bongiorno.sdrss.domain.resources

import com.fasterxml.jackson.annotation.JsonProperty
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
import org.springframework.http.HttpStatus
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

	@Test
	fun testInvalidPayload() {
		val input = Employee("Christian", "not and email")

		val response = restTemplate
				.postForEntity("/employees", input, ValidationFailure::class.java)

		assertThat(response.statusCode).isEqualTo(HttpStatus.BAD_REQUEST)

		val expected = ValidationFailure(listOf(ValidationError("Employee", "email", input.email))))

		assertThat(response.body).isEqualTo(expected)


	}

	class ValidationFailure(@JsonProperty("errors")
							val errors: List<ValidationError>)

	class ValidationError(
			var entity: String? = null,
			var property: String? = null,
			// don't care what the message is
			//		public String message;
			var invalidValue: Any? = null)

}
