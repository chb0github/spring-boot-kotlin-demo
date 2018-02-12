package org.bongiorno.sdrss.controllers

import org.bongiorno.sdrss.repositories.CandidateRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class CustomerController(private val repository: CandidateRepository) {

	@GetMapping("/custom")
	fun findAll() = repository.findAll()

	@GetMapping("/health/")
	fun findByName(@RequestParam deep:Boolean) = "health! $deep"
}