package runsystem.vn.kotlin.config



import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType

import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class RestTemplateClient(private val restTemplate: RestTemplate) {

    private val logger: Logger = LoggerFactory.getLogger(RestTemplateClient::class.java)

    fun <T> postBody(params: Map<String, String>, url: String, t: Class<T>) : T? {
        logger.info("request URL===========:{}", url)
        logger.info("Request parameters===========:{}", params)
        val httpHeaders = HttpHeaders()
        httpHeaders.contentType = MediaType.APPLICATION_JSON
        val httpEntity = HttpEntity(params, httpHeaders)
        val response = restTemplate.postForEntity(url, httpEntity, String::class.java)
        val mapper = ObjectMapper()
        return mapper.readValue(response.body, t)
    }
}