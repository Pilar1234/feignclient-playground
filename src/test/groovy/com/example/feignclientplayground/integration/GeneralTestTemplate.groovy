package com.example.feignclientplayground.integration

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.common.ConsoleNotifier
import com.github.tomakehurst.wiremock.core.WireMockConfiguration
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import spock.lang.Specification

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles("test")
class GeneralTestTemplate extends Specification {
    public static WireMockServer wireMockServer

    @LocalServerPort
    protected int port

    @Autowired
    protected TestRestTemplate restTemplate

    public String baseURI

    def setup() {
        baseURI = "http://localhost:" + port + "/v1/sample"
    }

    @DynamicPropertySource
    static void dynamicProperties(DynamicPropertyRegistry registry) {
        wireMockServer = new WireMockServer(new WireMockConfiguration().dynamicPort().notifier(new ConsoleNotifier(true)))
        wireMockServer.start()
        WireMock.configureFor("localhost", wireMockServer.port())
        registry.add("sample-service-url", () -> "http://localhost:" + wireMockServer.port())
    }

    def cleanup() {
        wireMockServer.resetAll()
    }
}
