package org.bongiorno.sdrss.domain.security


import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table
import org.springframework.hateoas.Identifiable

@Entity
@Table(name = "acl_sid")
class AclSid(val principal: Boolean = false, val sid: String="",
             @Id @GeneratedValue val id: Long = -1) : Identifiable<Long> {


    override fun getId() = id

    constructor(u: User) : this(true, u.username)

    constructor(g: Group) : this(false, g.name)

}
