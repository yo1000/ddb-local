package com.yo1000.dynamo.local

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.local.embedded.DynamoDBEmbedded
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 *
 * @author yo1000
 */
@Configuration
@EnableDynamoDBRepositories(basePackages = ["com.yo1000.dynamo.local.repository"])
class TestDynamoDBConfiguration {
    @Bean
    fun amazonDynamoDB(): AmazonDynamoDB {
        return DynamoDBEmbedded.create().amazonDynamoDB()
    }
}