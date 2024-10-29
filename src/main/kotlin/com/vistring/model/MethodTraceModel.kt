package com.vistring.model

import kotlinx.serialization.Serializable

@Serializable
data class MethodTraceGroupInfoRes(
    val methodFullName: String,
    val minMethodCost: Long,
    val maxMethodCost: Long,
    val minMethodTotalCost: Long,
    val maxMethodTotalCost: Long,
    val avgMethodCost: Long,
    val avgMethodTotalCost: Long,
)

data class MethodTraceGroupInfoDto(
    val methodFullName: String,
    val minMethodCost: Long,
    val maxMethodCost: Long,
    val minMethodTotalCost: Long,
    val maxMethodTotalCost: Long,
    val avgMethodCost: Long,
    val avgMethodTotalCost: Long,
)

fun MethodTraceGroupInfoRes.toMethodTraceGroupInfoDto(): MethodTraceGroupInfoDto {
    return MethodTraceGroupInfoDto(
        methodFullName = methodFullName,
        minMethodCost = minMethodCost,
        maxMethodCost = maxMethodCost,
        minMethodTotalCost = minMethodTotalCost,
        maxMethodTotalCost = maxMethodTotalCost,
        avgMethodCost = avgMethodCost,
        avgMethodTotalCost = avgMethodTotalCost,
    )
}