package com.vistring.model

import kotlinx.serialization.Serializable

@Serializable
data class MethodInfoRes(
    val id: Long,
    val appId: String,
    val methodCost: Long,
    val methodTotalCost: Long,
    val methodFullName: String,
    // ; 隔开的
    val stackTraceStr: String,
)

data class MethodInfoDto(
    val id: Long,
    val appId: String,
    val methodCost: Long,
    val methodTotalCost: Long,
    val methodFullName: String,
    // ; 隔开的
    val stackTraceStr: String,
)

@Serializable
data class MethodTraceGroupInfoRes(
    val methodFullName: String,
    val methodFullNameMd5: String,
    val minMethodCost: Long,
    val maxMethodCost: Long,
    val minMethodTotalCost: Long,
    val maxMethodTotalCost: Long,
    val avgMethodCost: Long,
    val avgMethodTotalCost: Long,
    val count: Int,
)

data class MethodTraceGroupInfoDto(
    val methodFullName: String,
    val methodFullNameMd5: String,
    val minMethodCost: Long,
    val maxMethodCost: Long,
    val minMethodTotalCost: Long,
    val maxMethodTotalCost: Long,
    val avgMethodCost: Long,
    val avgMethodTotalCost: Long,
    val count: Int,
)

fun MethodInfoRes.toMethodInfoDto(): MethodInfoDto {
    return MethodInfoDto(
        id = id,
        appId = appId,
        methodCost = methodCost,
        methodTotalCost = methodTotalCost,
        methodFullName = methodFullName,
        stackTraceStr = stackTraceStr,
    )
}

fun MethodTraceGroupInfoRes.toMethodTraceGroupInfoDto(): MethodTraceGroupInfoDto {
    return MethodTraceGroupInfoDto(
        methodFullName = methodFullName,
        methodFullNameMd5 = methodFullNameMd5,
        minMethodCost = minMethodCost,
        maxMethodCost = maxMethodCost,
        minMethodTotalCost = minMethodTotalCost,
        maxMethodTotalCost = maxMethodTotalCost,
        avgMethodCost = avgMethodCost,
        avgMethodTotalCost = avgMethodTotalCost,
        count = count,
    )
}