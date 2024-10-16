package com.kotlin

data class Company(
    val name: String,
    val fieldOfActivity: String,
    val contacts: String,
    val vacancies: List<String> = emptyList()
)
