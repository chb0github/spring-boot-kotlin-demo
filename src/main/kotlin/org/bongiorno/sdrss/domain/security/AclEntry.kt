package org.bongiorno.sdrss.domain.security

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.hateoas.Identifiable
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(name = "acl_entry")
data class AclEntry(    @NotNull
                        @OneToOne
                        var aclObjectIdentity: AclObjectIdentity,
                        @NotNull
                        @OneToOne var sid: AclSid) : Identifiable<Long> {

    override fun getId() = id

    @Id
    @GeneratedValue
    @JsonProperty("thing")
    private var id: Long? = null

    @NotNull
    var aceOrder: Int? = null

    @NotNull
    var mask: Int = 0


    @NotNull
    var granting: Boolean? = null

    @NotNull
    private var auditSuccess: Boolean? = null

    @NotNull
    var auditFailure: Boolean? = null

    constructor(aclObjectIdentity: AclObjectIdentity, sid: AclSid, vararg permissions: Permission) : this(aclObjectIdentity,sid) {

        this.mask = permissions.map(Permission::ordinal).reduce(Int::or)
    }

    enum class Permission {
        NONE,
        READ,
        WRITE
    }
}
