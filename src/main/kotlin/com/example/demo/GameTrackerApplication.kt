package com.example.demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class GameTrackerApplication

fun main(args: Array<String>) {
	runApplication<GameTrackerApplication>(*args)
}
