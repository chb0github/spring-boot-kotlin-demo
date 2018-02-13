package org.bongiorno.sdrss.repositories.security

import org.bongiorno.sdrss.domain.security.AclSid
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.security.access.prepost.PreAuthorize


@PreAuthorize("hasRole('ADMIN')")
interface AclSidRepository : PagingAndSortingRepository<AclSid, Long>
