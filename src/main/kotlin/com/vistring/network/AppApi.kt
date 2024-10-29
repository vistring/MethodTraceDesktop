package com.vistring.network

import com.vistring.model.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.java.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*

object AppApi {

    const val BASE_URL = "http://localhost:8080"

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
        appId: String,
        pageNumber: Int,
        pageSize: Int,
    ): List<MethodTraceGroupInfoDto> {
        val httpResponse: HttpResponse =
            httpClient.get("$BASE_URL/list?appId=$appId&pageNumber=$pageNumber&pageSize=$pageSize") {
                method = HttpMethod.Get
            }

        return httpResponse
            .body<List<MethodTraceGroupInfoRes>>()
            .map { it.toMethodTraceGroupInfoDto() }
    }

    suspend fun requestDetailInfo(
        appId: String,
        methodFullNameMd5: String,
    ): MethodInfoDto {
        val httpResponse: HttpResponse =
            httpClient.get("$BASE_URL/detail?appId=$appId&methodFullNameMd5=$methodFullNameMd5") {
                method = HttpMethod.Get
            }

        return httpResponse
            .body<MethodInfoRes>()
            .toMethodInfoDto()
    }

}