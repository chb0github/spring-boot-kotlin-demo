package org.bongiorno.sdrss.domain.security

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.hateoas.Identifiable
import org.springframework.security.core.GrantedAuthority
import javax.persistence.*

@Entity
@Table(name = "authorities")
data class Authority(@ManyToOne
                @JoinColumn(name = "username") val user: User, val role: String,
                     @Id @GeneratedValue private val id: Long = -1) : GrantedAuthority, Identifiable<Long> {

    override fun getId() = id

    @JsonIgnore
    override fun getAuthority() = role

    constructor(user: User, role: Role) : this(user, role.name)

}
