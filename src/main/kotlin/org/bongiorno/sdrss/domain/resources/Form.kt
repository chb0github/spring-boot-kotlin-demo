package org.bongiorno.sdrss.domain.resources
import org.springframework.hateoas.Identifiable
import java.time.LocalDate
import javax.persistence.*
import javax.validation.constraints.NotNull
import javax.validation.constraints.PastOrPresent

@Entity
data class Form(val type: Type,

                @ManyToOne
                @NotNull
                val employee: Employee?,
                @PastOrPresent
                val fileDate: LocalDate = LocalDate.now(),
                @Id @GeneratedValue( strategy =  GenerationType.IDENTITY) private val id: Long? = null) : Identifiable<Long> {

    override fun getId() = id


    enum class Type {
        ON_BOARDING,
        TERMINATION,
        PROMOTION
    }
}