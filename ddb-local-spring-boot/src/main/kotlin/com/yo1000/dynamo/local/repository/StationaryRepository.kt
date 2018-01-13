package com.yo1000.dynamo.local.repository

import org.socialsignin.spring.data.dynamodb.repository.EnableScan
import org.springframework.data.repository.CrudRepository

/**
 *
 * @author yo1000
 */
@EnableScan
interface StationaryRepository : CrudRepository<Stationary, String> {
    fun findByName(name: String): List<Stationary>
}