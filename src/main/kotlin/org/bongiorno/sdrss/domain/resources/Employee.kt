package org.bongiorno.sdrss.domain.resources

import org.springframework.hateoas.Identifiable
import java.time.LocalDate
import javax.persistence.*
import javax.validation.constraints.*

@Entity
data class Employee(@Pattern(regexp = "[A-Za-z0-9]+")
                    @Size(min = 6, max = 32)
                    val name: String,
                    @Email
                    @NotNull
                    val email: String?,
                    @PastOrPresent
                    val hireDate: LocalDate = LocalDate.now(),

                    @OneToMany(mappedBy = "employee", cascade = [CascadeType.ALL])
                    val forms:List<Form> = listOf(),
                    @OneToMany(mappedBy = "employee", cascade = [CascadeType.ALL])
                    val reports:List<Report> = listOf(),
                    @Id @GeneratedValue( strategy =  GenerationType.IDENTITY) private val id: Long? = null): Identifiable<Long> {

    override fun getId() = id

    constructor(name:String): this(name,"$name@sterlingts.com")
}