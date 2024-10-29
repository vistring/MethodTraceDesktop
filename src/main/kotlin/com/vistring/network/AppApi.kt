package com.vistring.network

import com.vistring.model.MethodTraceGroupInfoDto
import com.vistring.model.MethodTraceGroupInfoRes
import com.vistring.model.toMethodTraceGroupInfoDto
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.java.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*

object AppApi {

    private val httpClient = HttpClient(Java) {
        engine {
            // this: JavaHttpConfig
            // pipelining = true
            // proxy = ProxyBuilder.http("http://proxy-server.com/")
            protocolVersion = java.net.http.HttpClient.Version.HTTP_2
        }
        install(ContentNegotiation) {
            json()
        }
    }

    suspend fun requestGroupInfo(
        pageNumber: Int,
        pageSize: Int,
    ): List<MethodTraceGroupInfoDto> {
        val httpResponse: HttpResponse = httpClient.get("http://localhost:8080/list?appId=Blink&pageNumber=$pageNumber&pageSize=$pageSize") {
            method = HttpMethod.Get
        }

        return httpResponse
            .body<List<MethodTraceGroupInfoRes>>()
            .map { it.toMethodTraceGroupInfoDto() }
    }

}