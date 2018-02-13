package org.bongiorno.sdrss.domain.security

import org.springframework.hateoas.Identifiable
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(name = "acl_object_identity")
class AclObjectIdentity : Identifiable<Long> {

    @Id
    @GeneratedValue
    private var id: Long? = null

    @NotNull
    @Basic
    @JoinColumn(name = "object_id_class", referencedColumnName = "id")
    var objectIdClass: String

    @NotNull
    var objectIdIdentity: Long?

    @OneToOne
    var parentObject: AclObjectIdentity? = null

    @NotNull
    @OneToOne
    var ownerSid: AclSid

    @NotNull
    var entriesInheriting: Boolean?

    /**
     * Basically says
     * @param aclSid this guys has access to
     * @param classId this instance of
     * @param aclClass this class
     */
    constructor(aclClass: Class<*>, classId: Long?, aclSid: AclSid) {
        this.objectIdClass = aclClass.name
        this.objectIdIdentity = classId
        this.ownerSid = aclSid
        this.entriesInheriting = false
    }

    override fun getId() = id
}
