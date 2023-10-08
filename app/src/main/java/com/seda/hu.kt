package com.seda

import com.seda.room.Employee
import kotlin.math.sign

data class Employee(var name: String, var salary: Float)

fun main(){
    var deger: MutableList<Employee> = mutableListOf<Employee>()
    data class Dog(val name: String, val age: Int)
    val dogs = mutableListOf(
        Dog("Buddy", 3),
        Dog("Max", 5),
        Dog("Lucy", 2)
    )
    deger = mutableListOf<com.seda.room.Employee>(
        com.seda.room.Employee("John Loyd", 112.4f),
        com.seda.room.Employee("Bibi Bob", 8852.43f)


    )
    deger.add(com.seda.room.Employee("ghk",456.6f))
   deger.forEach {
       insertEmployee(it)
   }
   val de= deger.filter {
       it.name.equals("Jhon Loyd")}
    println(de)
}

fun insertEmployee(employee: Employee) {


}