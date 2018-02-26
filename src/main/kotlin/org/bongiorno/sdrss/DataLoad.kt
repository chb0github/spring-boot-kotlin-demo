package org.bongiorno.sdrss

import org.bongiorno.sdrss.domain.resources.Employee
import org.bongiorno.sdrss.domain.resources.Form
import org.bongiorno.sdrss.domain.resources.Report
import org.bongiorno.sdrss.repositories.domain.EmployeeRepository
import org.bongiorno.sdrss.repositories.domain.FormRepository
import org.bongiorno.sdrss.repositories.domain.ReportRepository
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

/**
 *  @author cbongiorno on 2/20/18.
 */
@Component
class DataLoad(private val employees: EmployeeRepository,
               private val reports: ReportRepository,
               private val forms: FormRepository) {

    @PostConstruct
    fun init() {

        val christian = employees.save(Employee("Christian", "christian.bongiorno@sterlingts.com"))
        val jake = employees.save(Employee("Jake", "jake.litwicki@sterlingts.com"))
        val platTeam = (1..10).map { Employee("Employee $it") }.map(employees::save)

        val glTeam = (10 downTo 1 step 2).map { Employee("Employee $it") }.let { employees.saveAll(it) }

        val allEmployees = (listOf(christian, jake) + platTeam + glTeam).map { Form(Form.Type.ON_BOARDING, it) }.map(forms::save)

        (1..10).map { Report(Report.Type.EXPENSE, jake) }.map(reports::save)
        (1..10).map { Form(Form.Type.PROMOTION, christian) }.map { forms.save(it) }

        (platTeam + glTeam).map { Report(Report.Type.TPS, it) }.map(reports::save)

        forms.save(Form(Form.Type.TERMINATION, jake))


    }
}