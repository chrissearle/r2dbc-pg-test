package net.chrissearle.testpgr2dbc

import io.r2dbc.spi.ConnectionFactory
import mu.KotlinLogging
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.core.io.ClassPathResource
import org.springframework.data.annotation.Id
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator

private val logger = KotlinLogging.logger {}

data class Sample(
    @Id
    val id: Long?,
    val name: String
)

interface SampleRepository : ReactiveCrudRepository<Sample, Long>

@SpringBootApplication
class TestPgR2dbcApplication {

    @Bean
    fun initializer(connectionFactory: ConnectionFactory): ConnectionFactoryInitializer {
        val initializer = ConnectionFactoryInitializer()

        initializer.setConnectionFactory(connectionFactory)
        initializer.setDatabasePopulator(ResourceDatabasePopulator(ClassPathResource("schema.sql")))

        return initializer
    }

    @Bean
    fun init(repository: SampleRepository) = CommandLineRunner {
        val saved = repository.save(Sample(null, "Name")).doOnError { logger.error { it } }.block()

        logger.info { saved }

        repository.findAll().subscribe { logger.info { it } }
    }
}

fun main(args: Array<String>) {
    runApplication<TestPgR2dbcApplication>(*args)
}
