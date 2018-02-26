package org.bongiorno.sdrss.health

import org.springframework.boot.actuate.health.Health
import org.springframework.boot.actuate.health.HealthIndicator
import org.springframework.stereotype.Component

@Component
class HelloWorldHealthIndicator : HealthIndicator {

    override fun health(): Health = Health.up().withDetail("hello","world").build()
}