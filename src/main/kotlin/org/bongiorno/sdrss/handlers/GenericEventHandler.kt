package org.bongiorno.sdrss.handlers

import org.bongiorno.sdrss.domain.security.*
import org.bongiorno.sdrss.domain.security.AclEntry.Permission.*
import org.bongiorno.sdrss.repositories.security.AclEntryRepository
import org.bongiorno.sdrss.repositories.security.AclObjectIdentityRepository
import org.bongiorno.sdrss.repositories.security.AclSidRepository
import org.springframework.data.rest.core.event.AbstractRepositoryEventListener
import org.springframework.hateoas.Identifiable
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

/**
 *  @author cbongiorno on 2/11/18.
 */
@Component
class GenericEventHandler(private val objects: AclObjectIdentityRepository,
                          private val sids: AclSidRepository,
                          private val aclEntries: AclEntryRepository) : AbstractRepositoryEventListener<Identifiable<Long>>() {


    override fun onBeforeSave(entity: Identifiable<Long>) {

        println("onBeforeSave : $entity")

    }

    override fun onAfterCreate(entity: Identifiable<Long>) {
        // make sure the new object we create we have r/w access to
        val authentication = SecurityContextHolder.getContext().authentication

        val aclSid = when(authentication.principal) {
                is User -> AclSid(authentication.principal as User)
                else -> AclSid(authentication.principal as Group)
        }

        val objectEntry = objects.save(AclObjectIdentity(entity.javaClass, entity.id, aclSid))
        aclEntries.save(AclEntry(objectEntry, aclSid, READ, WRITE))
        println("onAfterCreate : $entity")

    }

    override fun onBeforeCreate(entity: Identifiable<Long>) {
        println("onBeforeCreate : $entity")

    }

    override fun onAfterSave(entity: Identifiable<Long>) {

        println("onAfterSave : $entity")

    }
}