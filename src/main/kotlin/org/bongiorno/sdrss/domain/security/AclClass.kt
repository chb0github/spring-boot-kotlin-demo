package org.bongiorno.sdrss.domain.security

import org.springframework.hateoas.Identifiable
import javax.persistence.*

@Entity
@Table(name = "acl_class")
data class AclClass(@Column(name = "class") val clazz: Class<*>,
                    @Id @GeneratedValue private val id: Long = -1) : Identifiable<Long> {

    override fun getId(): Long = id

    constructor(clazz: Class<*>):this(clazz,-1)

    override fun toString(): String = clazz.name
}
