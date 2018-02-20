package org.bongiorno.sdrss.repositories.domain

import org.bongiorno.sdrss.domain.resources.Report
import org.springframework.data.repository.CrudRepository
import org.springframework.security.access.prepost.PostAuthorize
import org.springframework.security.access.prepost.PostFilter
import java.util.*

interface ReportRepository : CrudRepository<Report, Long> {

    @PostFilter("hasPermission(filterObject, 'READ')")
    override fun findAll(): Iterable<Report>

    @PostAuthorize("hasPermission(returnObject, 'WRITE')")
    override fun findById(id: Long): Optional<Report>
}

