package org.bongiorno.sdrss.domain.resources

import java.time.Instant
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Report(override val date: Instant,
                  override val name: String,
                  @Id @GeneratedValue override val id: Long = -1) : Post {
    override fun getId() = id
}