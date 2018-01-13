package com.yo1000.dynamo.local.repository

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.model.*
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

/**
 *
 * @author yo1000
 */
@RunWith(SpringJUnit4ClassRunner::class)
@SpringBootTest
class StationaryRepositoryTest {
    @Autowired
    lateinit var ddb: AmazonDynamoDB
    @Autowired
    lateinit var stationaryRepository: StationaryRepository

    @Before
    fun createTable() {
        try {
            ddb.describeTable("Stationary").table?.let {
                return
            }
        } catch (e: ResourceNotFoundException) {
            ddb.createTable(CreateTableRequest()
                    .withTableName("Stationary")
                    .withKeySchema(listOf(
                            KeySchemaElement("id", KeyType.HASH)
                    ))
                    .withAttributeDefinitions(listOf(
                            AttributeDefinition("id", ScalarAttributeType.S)
                    ))
                    .withProvisionedThroughput(
                            ProvisionedThroughput(1000L, 1000L)
                    )
            )
        }
    }

    @Test
    fun test_that_items_can_be_saved_and_readable() {
        stationaryRepository.saveAll(listOf(
                Stationary("id-1001", "LAMY 2000 L01-F"),
                Stationary("id-1002", "Pelikan Souveran M400 F WHITE TORTOISE")
        ))

        val stationary1 = stationaryRepository.findById("id-1001")
        Assert.assertNotNull(stationary1)
        Assert.assertEquals("id-1001", stationary1.get().id)
        Assert.assertEquals("LAMY 2000 L01-F", stationary1.get().name)

        val stationary2 = stationaryRepository.findByName("Pelikan Souveran M400 F WHITE TORTOISE")
        Assert.assertNotNull(stationary2)
        Assert.assertEquals("id-1002", stationary2[0].id)
        Assert.assertEquals("Pelikan Souveran M400 F WHITE TORTOISE", stationary2[0].name)
    }
}