package com.yo1000.dynamo.local

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.local.embedded.DynamoDBEmbedded
import com.amazonaws.services.dynamodbv2.model.*
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

/**
 *
 * @author yo1000
 */
class DynamoDBEmbeddedTest {
    lateinit var dynamo: AmazonDynamoDB

    @Before
    fun createDB() {
        dynamo = DynamoDBEmbedded.create().amazonDynamoDB()
    }

    @After
    fun shutdownDB() {
        dynamo.shutdown()
    }

    @Test
    fun test_that_table_is_created_equally_with_setting() {
        val tableName = "Stationery"
        val hashKeyName = "item_id"
        val readCapacityUnits = 1000L
        val writeCapacityUnits = 1000L

        val result = dynamo.createTable(CreateTableRequest()
                .withTableName(tableName)
                .withKeySchema(listOf(
                        KeySchemaElement(hashKeyName, KeyType.HASH)
                ))
                .withAttributeDefinitions(listOf(
                        AttributeDefinition(hashKeyName, ScalarAttributeType.S)
                ))
                .withProvisionedThroughput(
                        ProvisionedThroughput(readCapacityUnits, writeCapacityUnits))
        )

        val tableDesc = result.tableDescription
        Assert.assertEquals(tableName, tableDesc.tableName)
        Assert.assertEquals("[{AttributeName: $hashKeyName,KeyType: ${KeyType.HASH}}]",
                tableDesc.keySchema.toString())
        Assert.assertEquals("[{AttributeName: $hashKeyName,AttributeType: ${ScalarAttributeType.S}}]",
                tableDesc.attributeDefinitions.toString())
        Assert.assertEquals(readCapacityUnits, tableDesc.provisionedThroughput.readCapacityUnits)
        Assert.assertEquals(writeCapacityUnits, tableDesc.provisionedThroughput.writeCapacityUnits)
        Assert.assertEquals("ACTIVE", tableDesc.tableStatus)
        Assert.assertEquals("arn:aws:dynamodb:ddblocal:000000000000:table/$tableName", tableDesc.tableArn)

        val tables = dynamo.listTables()
        Assert.assertEquals(1, tables.tableNames.size)
    }
}