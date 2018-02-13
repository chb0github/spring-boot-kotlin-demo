package org.bongiorno.sdrss.domain.security

import org.springframework.hateoas.Identifiable
import org.springframework.security.core.userdetails.UserDetails
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(name = "users")
data class User(@Id private var username: String, private var password: String,
                var id: Long = -1) : UserDetails, Identifiable<String> {


    constructor(username: String, password: String, enabled: Boolean, vararg authorities: String)
            : this(username,password) {
        this.authorities = authorities.map { a -> Authority(this, a) }.toSet()
        this.enabled = enabled
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

    override fun toString() = username!!

}
