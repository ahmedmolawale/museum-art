package com.appsfactory.data.mapper

internal interface EntityMapper<E, D> {

    fun mapToDomain(entity: E): D

    fun mapToDomainList(entities: List<E>): List<D> {
        return entities.mapTo(mutableListOf(), ::mapToDomain)
    }
}
