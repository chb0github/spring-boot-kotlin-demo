package org.bongiorno.sdrss.healthchecks

import org.springframework.boot.actuate.health.Health
import org.springframework.boot.actuate.health.HealthIndicator
import org.springframework.stereotype.Component

/**
 *  @author cbongiorno on 2/23/18.
 */
@Component
class DbAvailable : HealthIndicator {

    override fun health(): Health = Health.up().build()
}