package com.rnimour.trials.games

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.ClassRule
import org.junit.jupiter.api.Test
import org.testcontainers.containers.GenericContainer
import org.testcontainers.junit.jupiter.Testcontainers
import java.io.BufferedReader
import java.net.HttpURLConnection
import java.net.URI

/**
 * This test demonstrates how to use a simple web server container
 * It doesn't really fit in this project, but I wanted to try it out anyways
 */
@Testcontainers
class WebServerTestContainerTest {

    companion object {
        @JvmField
        @ClassRule
        val simpleWebServer: GenericContainer<*> = GenericContainer("alpine:3.2")
            .withExposedPorts(80)
            .withCommand(
                "/bin/sh", "-c", "while true; " +
                        "do echo 'HTTP/1.1 200 OK\n\nHello World!' | nc -l -p 80; " +
                        "done"
            ).apply { start() }
    }

    @Test
    fun smokeTest() {
        assertThat("hostname is correct", simpleWebServer.host, equalTo("localhost"))
    }

    @Test
    fun test() {
        // Given
        val address = "http://${simpleWebServer.host}:${simpleWebServer.getMappedPort(80)}"

        // When
        val response = get(address)

        // Then
        assertThat("response should be as specified", response, equalTo("Hello World!\n"))
    }

    private fun get(address: String): String =
        (URI(address).toURL().openConnection() as HttpURLConnection).apply {
            requestMethod = "GET"
        }.inputStream
            .bufferedReader()
            .use(BufferedReader::readText)
}