package org.bongiorno.sdrss.domain.security

import org.springframework.hateoas.Identifiable
import javax.persistence.Entity
import javax.persistence.Id

/**
 *  @author cbongiorno on 2/20/18.
 */
//@Entity
enum class Role : Identifiable<Int> {

    ROLE_ROOT,
    ROLE_ADMIN,
    ROLE_USER,
    ROLE_VISITOR;

    @Id
    override fun getId() = ordinal

}