package org.bongiorno.sdrss.domain.security

import org.springframework.hateoas.Identifiable
import javax.persistence.*

@Entity
@Table(name = "groups")
data class Group(val name: String = "",
                 @ManyToMany val authorities: Collection<Authority> = setOf(),
                 @ManyToMany val members: Collection<User> = setOf(),
                 @Id @GeneratedValue private val id: Long = -1) : Identifiable<Long> {

    override fun getId() = id

}
