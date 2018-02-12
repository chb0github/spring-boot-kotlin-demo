package org.bongiorno.sdrss.domain.resources

import org.springframework.hateoas.Identifiable
import java.time.Instant


interface Post : Identifiable<Long> {


    val date: Instant

    val name: String
}
