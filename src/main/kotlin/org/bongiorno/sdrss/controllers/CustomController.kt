package org.bongiorno.sdrss.controllers

import org.springframework.boot.actuate.health.CompositeHealthIndicator
import org.springframework.boot.actuate.health.Health
import org.springframework.boot.actuate.health.HealthIndicator
import org.springframework.boot.actuate.health.OrderedHealthAggregator
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
class CustomController(private val indicators:Map<String,HealthIndicator>) {

	private var customRepo = mapOf<String,Any>()

	@GetMapping("/custom/{id}")
	fun getCustom(@PathVariable id:String) = customRepo[id]


	@PostMapping("/custom")
	fun acceptCustom(@RequestBody body:CustomPayload):Any {
		val result  = object {
			val id:String = UUID.randomUUID().toString()
			val name = body.name
			val age = body.age
		}
        customRepo += (result.id to result)
        return result
	}

	@GetMapping("/health")
	fun getHealth(@RequestParam deep:Boolean) = when(deep) {
        true -> CompositeHealthIndicator(OrderedHealthAggregator(),indicators).health()
        else -> Health.up().build()
    }

	data class CustomPayload(val name:String, val age:Int)
}