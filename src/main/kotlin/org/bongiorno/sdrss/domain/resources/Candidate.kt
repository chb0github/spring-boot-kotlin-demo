package org.bongiorno.sdrss.domain.resources

import java.time.Instant
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Candidate(override val date: Instant = Instant.now(),
                     override val name: String,
                     @Id @GeneratedValue private val id: Long = -1) : Post {

    override fun getId() = id
}