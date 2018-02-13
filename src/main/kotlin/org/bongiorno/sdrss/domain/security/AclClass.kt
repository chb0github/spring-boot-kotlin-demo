package org.bongiorno.sdrss.domain.security

import org.springframework.hateoas.Identifiable
import javax.persistence.*

@Entity
@Table(name = "acl_class")
data class AclClass(@Column(name = "class") val clazz: Class<*>) : Identifiable<Long> {

    @Id @GeneratedValue private val id: Long? = null

    override fun getId(): Long? = id


    override fun toString(): String = clazz.name
}
