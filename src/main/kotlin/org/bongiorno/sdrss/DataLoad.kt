package org.bongiorno.sdrss

import org.bongiorno.sdrss.domain.resources.Candidate
import org.bongiorno.sdrss.domain.resources.Form
import org.bongiorno.sdrss.domain.resources.Report
import org.bongiorno.sdrss.domain.security.*
import org.bongiorno.sdrss.repositories.domain.CandidateRepository
import org.bongiorno.sdrss.repositories.domain.FormRepository
import org.bongiorno.sdrss.repositories.domain.ReportRepository
import org.bongiorno.sdrss.repositories.security.*
import org.reflections.Reflections
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct
import javax.persistence.Entity

/**
 *  @author cbongiorno on 2/20/18.
 */
@Component
class DataLoad(private val candidates: CandidateRepository,
               private val reports: ReportRepository,
               private val forms: FormRepository,
               private val users: UserRepository,
               private val authorities: AuthorityRepository,
               private val sids: AclSidRepository,
               private val classes: AclClassRepository,
               private val aclObjIds: AclObjectIdentityRepository,
               private val encoder: PasswordEncoder,
               private val aclEnries: AclEntryRepository) {

    @PostConstruct
    fun init() {
        val context = SecurityContextHolder.getContext()
        context.authentication = UsernamePasswordAuthenticationToken("root", "root",
                AuthorityUtils.createAuthorityList("ROLE_ROOT", "ROLE_ADMIN"))


        val root = users.save(User("root", encoder.encode("password")))
        val christian = users.save(User("christian", encoder.encode("password")))
        val jake = users.save(User("jake", encoder.encode("password")))

//        Role.values().map(roles::save)

        authorities.save(Authority(root, Role.ROLE_ROOT))
        authorities.save(Authority(root, Role.ROLE_ADMIN))
        authorities.save(Authority(christian, Role.ROLE_ROOT))
        authorities.save(Authority(christian, Role.ROLE_ADMIN))
        authorities.save(Authority(jake, Role.ROLE_VISITOR))
        val userSids = listOf(root, christian, jake).map(::AclSid).map(sids::save)


        val all = classes.findAll().map(AclClass::clazz)
        val reflections = Reflections(this.javaClass.`package`.name)

        val entities = reflections.getTypesAnnotatedWith(Entity::class.java) - all

        entities.map(::AclClass).map(classes::save)
        val aclClasses = classes.findAll()

        val candidates = (1..3).map { Candidate("Candidate $it") }.map(candidates::save)
        val forms = (1..3).map { Form("Form $it") }.map(forms::save)
        val reports = (1..3).map { Report("Report $it") }.map(reports::save)


        val aclObjIds = listOf(candidates, forms, reports).flatten().map { t -> userSids.map { Pair(t, it) } }
                .flatten().map { AclObjectIdentity(it.first, it.second) }.map(aclObjIds::save)


//        INSERT INTO acl_object_identity (id, object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting) VALUES
//                (1, 1, 1, NULL, 1, 0),
//        (2, 1, 2, NULL, 1, 0),
//        (3, 1, 3, NULL, 1, 0),
//        (4, 2, 1, NULL, 1, 0),
//        (5, 2, 2, NULL, 1, 0),
//        (6, 2, 3, NULL, 1, 0),
//        (7, 3, 1, NULL, 1, 0),
//        (8, 3, 2, NULL, 1, 0),
//        (9, 3, 3, NULL, 1, 0);

        SecurityContextHolder.clearContext()

    }

}