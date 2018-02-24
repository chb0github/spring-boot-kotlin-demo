package org.bongiorno.sdrss.repositories.domain

import org.bongiorno.sdrss.domain.resources.Report
import org.springframework.data.repository.CrudRepository

interface ReportRepository : CrudRepository<Report, Long>

