package org.bongiorno.sdrss.handlers

import org.bongiorno.sdrss.domain.resources.Employee
import org.bongiorno.sdrss.domain.resources.Report
import org.springframework.data.rest.core.annotation.HandleAfterCreate
import org.springframework.data.rest.core.annotation.HandleBeforeCreate
import org.springframework.data.rest.core.annotation.RepositoryEventHandler
import org.springframework.stereotype.Component

@Component
@RepositoryEventHandler
class EmployeeEventHandler {
    // 2 different ways to handle events
    @HandleBeforeCreate
    fun handleBeforeCreate(e: Employee) = println(" about to create candidate: $e")

    @HandleAfterCreate
    fun beforeCreatingReport(r: Report) = println(" How about your put this in a queue or on a websocket?: $r")
}