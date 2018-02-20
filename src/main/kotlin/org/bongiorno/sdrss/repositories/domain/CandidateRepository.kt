package org.bongiorno.sdrss.repositories.domain

import org.bongiorno.sdrss.domain.resources.Candidate
import org.springframework.data.repository.CrudRepository
import org.springframework.security.access.prepost.PostAuthorize
import org.springframework.security.access.prepost.PostFilter
import java.util.*


interface CandidateRepository : CrudRepository<Candidate, Long> {
    @PostFilter("hasPermission(filterObject, 'READ')")
    override fun findAll(): Iterable<Candidate>

    @PostAuthorize("hasPermission(returnObject, 'WRITE')")
    override fun findById(id: Long): Optional<Candidate>
}
