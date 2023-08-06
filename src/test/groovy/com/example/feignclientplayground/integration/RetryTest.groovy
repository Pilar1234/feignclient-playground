package com.example.feignclientplayground.integration

import com.example.feignclientplayground.ErrorResponse
import com.example.feignclientplayground.SampleFeignClient
import com.example.feignclientplayground.ServiceRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse
import static com.github.tomakehurst.wiremock.client.WireMock.post
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo
import static com.github.tomakehurst.wiremock.client.WireMock.verify
import static org.springframework.http.HttpStatus.BAD_GATEWAY
import static org.springframework.http.HttpStatus.BAD_REQUEST
import static org.springframework.http.HttpStatus.FORBIDDEN
import static org.springframework.http.HttpStatus.GATEWAY_TIMEOUT
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE
import static org.springframework.http.HttpStatus.TOO_MANY_REQUESTS
import static org.springframework.http.HttpStatus.UNAUTHORIZED

class RetryTest extends GeneralTestTemplate {

    @Autowired
    SampleFeignClient feignClient

    def "Application should respond with http code: #expectedStatus and retry #expectedNumberOfRetry times when external service returns #expectedStatus"(
            int expectedNumberOfRetry, HttpStatus expectedStatus) {
        stubFor(post(urlEqualTo("/v1/test"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withStatus(expectedStatus.value())
                )
        )
        ServiceRequest request = new ServiceRequest("sampleName", "sampleSurname")

        when: "request is send to the service"
        ResponseEntity<ErrorResponse> response =
                restTemplate.postForEntity(baseURI, request, ErrorResponse.class)

        then: "application should respond appropriate status"
        response.statusCode == expectedStatus

        and: "response body"

        and: "Retry functionality works as expected"
        verify(expectedNumberOfRetry, postRequestedFor(urlEqualTo("/v1/test")))

        where:
        expectedNumberOfRetry | expectedStatus
        1                     | BAD_REQUEST
        1                     | UNAUTHORIZED
        1                     | FORBIDDEN
        1                     | NOT_FOUND
        1                     | INTERNAL_SERVER_ERROR
        1                     | BAD_GATEWAY
        1                     | GATEWAY_TIMEOUT
        3                     | TOO_MANY_REQUESTS
        3                     | SERVICE_UNAVAILABLE
    }
}
