package org.bongiorno.sdrss.repositories.security

import org.bongiorno.sdrss.domain.security.Authority
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.security.access.prepost.PreAuthorize


@PreAuthorize("hasRole('ROOT')")
interface AuthorityRepository : PagingAndSortingRepository<Authority, Long>
