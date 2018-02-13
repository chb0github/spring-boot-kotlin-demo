package org.bongiorno.sdrss.repositories.domain

import org.bongiorno.sdrss.domain.resources.Candidate
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CandidateRepository : CrudRepository<Candidate, Long> {
    fun findByName(name: String): Iterable<Candidate>

}
