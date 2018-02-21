package org.bongiorno.sdrss.domain.security

import org.apache.coyote.http11.Constants.a
import org.springframework.hateoas.Identifiable
import org.springframework.security.core.userdetails.UserDetails
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(name = "users")
data class User(@Id private var username: String, private var password: String) : UserDetails, Identifiable<String> {


    constructor(username: String, password: String,vararg roles: Role)
            : this(username,password) {
        this.authorities = roles.map { Authority(this, it) }.toSet()
        this.enabled = true
    }

    constructor(username: String, password: String,  vararg authorities: String)
            : this(username,password) {
        this.authorities = authorities.map { a -> Authority(this, a) }.toSet()
        this.enabled = true
    }

    override fun getAuthorities() = this.authorities

    override fun getUsername() = username

    override fun getPassword() = password

    @NotNull
    private var enabled: Boolean = false

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private var authorities: Set<Authority> = setOf()

    override fun isAccountNonExpired() = true

    override fun isAccountNonLocked() = true

    override fun isCredentialsNonExpired() = true

    override fun isEnabled() = enabled

    override fun getId() = username

    override fun toString() = username

}
