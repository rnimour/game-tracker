package com.rnimour.trials

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.kotlinModule
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.zalando.problem.jackson.ProblemModule

@SpringBootApplication
class GameTrackerApplication {

    @Bean
    fun objectMapper() : ObjectMapper {
        return ObjectMapper()
            .registerModules(
                ProblemModule(),
                kotlinModule(),
            )
    }
}

fun main(args: Array<String>) {
    runApplication<GameTrackerApplication>(*args)
}
