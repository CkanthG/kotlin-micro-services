package com.sree.claims

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@EnableFeignClients
class ClaimServiceApplication

fun main(args: Array<String>) {
	runApplication<ClaimServiceApplication>(*args)
}
