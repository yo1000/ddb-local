package com.yo1000.dynamo.local

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DdbLocalSpringBootApplication

fun main(args: Array<String>) {
    runApplication<DdbLocalSpringBootApplication>(*args)
}
