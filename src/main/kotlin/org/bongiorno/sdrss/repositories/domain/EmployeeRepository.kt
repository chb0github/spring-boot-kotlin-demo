package org.bongiorno.sdrss.repositories.domain

import org.bongiorno.sdrss.domain.resources.Employee
import org.springframework.data.repository.PagingAndSortingRepository


interface EmployeeRepository : PagingAndSortingRepository<Employee, Long>