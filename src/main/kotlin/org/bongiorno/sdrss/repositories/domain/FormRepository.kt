package org.bongiorno.sdrss.repositories.domain

import org.bongiorno.sdrss.domain.resources.Form
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface FormRepository : CrudRepository<Form, Long>

