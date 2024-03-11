package com.bdenney.modtwo.data.number

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Converter.Factory
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.lang.reflect.Type

class IntListConverterFactory: Factory() {

    private val scalarFactory = ScalarsConverterFactory.create()

    override fun requestBodyConverter(
        type: Type,
        parameterAnnotations: Array<out Annotation>,
        methodAnnotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<*, RequestBody>? {

        return scalarFactory.requestBodyConverter(type, parameterAnnotations, methodAnnotations, retrofit)
    }

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *>? {
        return IntListConverter()
    }
}

class IntListConverter: Converter<ResponseBody, List<Int>> {
    override fun convert(value: ResponseBody): List<Int> {
        val responseString = value.string()
        val ints = mutableListOf<Int>()
        responseString.lines().forEach {line ->
            line.toIntOrNull()?.let { ints.add(it) }
        }
        return ints
    }
}