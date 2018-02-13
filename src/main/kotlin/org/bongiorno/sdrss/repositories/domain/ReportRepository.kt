package org.bongiorno.sdrss.repositories.domain

import org.bongiorno.sdrss.domain.resources.Report
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ReportRepository : CrudRepository<Report, Long>

