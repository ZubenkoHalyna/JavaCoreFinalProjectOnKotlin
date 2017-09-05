package dataAccess

import java.util.*

interface DAOInterface<T> {
    fun select(params: Map<String, String>): List<T>
    fun selectFirst(params: Map<String, String>): Optional<T>
    fun selectAll(): List<T>
    fun getById(id: Long): T
    fun insert(item: T): Boolean
    fun insertAll(items: Collection<T>): Boolean
    fun update(item: T): Boolean
    fun delete(item: T): Boolean
}