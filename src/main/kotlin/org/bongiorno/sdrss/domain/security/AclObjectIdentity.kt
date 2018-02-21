package org.bongiorno.sdrss.domain.security

import org.springframework.hateoas.Identifiable
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(name = "acl_object_identity")
data class AclObjectIdentity(
                        @Basic
                        @JoinTable(
                                joinColumns = (arrayOf(JoinColumn(name = "object_id_class", referencedColumnName = "id"))),
                                name = "object_id_class"
                        )
                        val objectIdClass: String,

                        @NotNull
                        val objectIdIdentity: Long,

                        @NotNull
                        @OneToOne
                        val ownerSid: AclSid,

                        @NotNull
                        val entriesInheriting: Boolean = false,

                        @OneToOne
                        val parentObject: AclObjectIdentity? = null,

                        @Id
                        @GeneratedValue
                        val id: Long = -1) : Identifiable<Long> {

    /**
     * Basically says
     * @param aclSid this guys has access to
     * @param classId this instance of
     * @param aclClass this class
     */
    constructor(aclClass: Class<*>, classId: Long, aclSid: AclSid) : this(aclClass.name,classId,aclSid)

    constructor(thing: Identifiable<Long>, aclSid: AclSid) : this(thing.javaClass, thing.id,aclSid)


    override fun getId() = id
}
