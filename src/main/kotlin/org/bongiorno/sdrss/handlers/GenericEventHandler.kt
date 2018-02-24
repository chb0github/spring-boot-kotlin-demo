package org.bongiorno.sdrss.handlers

import org.springframework.data.rest.core.event.AbstractRepositoryEventListener
import org.springframework.hateoas.Identifiable
import org.springframework.stereotype.Component

/**
 *  @author cbongiorno on 2/11/18.
 */
@Component
class GenericEventHandler: AbstractRepositoryEventListener<Identifiable<Long>>() {


    override fun onBeforeSave(entity: Identifiable<Long>) {

        println("onBeforeSave : $entity")

    }

    override fun onAfterCreate(entity: Identifiable<Long>) {
        // make sure the new object we create we have r/w access to

        println("onAfterCreate : $entity")

    }

    override fun onBeforeCreate(entity: Identifiable<Long>) {
        println("onBeforeCreate : $entity")

    }

    override fun onAfterSave(entity: Identifiable<Long>) {

        println("onAfterSave : $entity")

    }
}