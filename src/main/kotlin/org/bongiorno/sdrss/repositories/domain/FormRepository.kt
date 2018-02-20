package org.bongiorno.sdrss.repositories.domain

import org.bongiorno.sdrss.domain.resources.Form
import org.springframework.data.repository.CrudRepository
import org.springframework.security.access.prepost.PostAuthorize
import org.springframework.security.access.prepost.PostFilter
import java.util.*

interface FormRepository : CrudRepository<Form, Long> {


    @PostFilter("hasPermission(filterObject, 'READ')")
    override fun findAll(): Iterable<Form>

    @PostAuthorize("hasPermission(returnObject, 'WRITE')")
    override fun findById(id: Long): Optional<Form>
}


