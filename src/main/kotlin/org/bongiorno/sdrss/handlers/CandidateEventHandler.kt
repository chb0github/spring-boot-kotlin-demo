package org.bongiorno.sdrss.handlers

import org.bongiorno.sdrss.domain.resources.Candidate
import org.springframework.data.rest.core.annotation.HandleBeforeCreate
import org.springframework.data.rest.core.annotation.RepositoryEventHandler
import org.springframework.stereotype.Component

@Component
@RepositoryEventHandler
class CandidateEventHandler {
    // 2 different ways to handle events
    @HandleBeforeCreate
    fun handleBeforeCreate(c: Candidate) = println(" about to create candidate: $c")
}