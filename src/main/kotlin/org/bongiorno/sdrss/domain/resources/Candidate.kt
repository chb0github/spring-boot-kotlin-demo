package org.bongiorno.sdrss.domain.resources

import java.time.Instant
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class Candidate(override val name: String,
                     override val date: Instant = Instant.now(),
                     @Id @GeneratedValue( strategy =  GenerationType.IDENTITY) val id: Long = -1) : Post {

    override fun getId() = id
}