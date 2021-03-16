package com.example.data.models


open class Comida(private val nombre: String) {
    fun hacerEnsalada() {

    }
}

open class ComidaVegetariana(nombre: String) : Comida(nombre) {
}

class ComidaVegana(nombre: String) : ComidaVegetariana(nombre) {
}

class Restaurant<out T>(private val estudiante: T) {
    fun quien() : T {
        return estudiante
    }
}

//fun main() {
//    val c = ComidaVegetariana("asd")
//
//    println( (c is Comida) )
//}

sealed class Animal

class Cat : Animal()
class Dog : Animal()

fun main() {

    val animals = mutableListOf(
        Cat(),
        Cat(),
        Dog()
    )

    val cats = mutableListOf(
        Cat()
    )

    val dogs = mutableListOf(
        Dog()
    )

    copyData(cats, animals) // ok, since a List<Animal> is a subtype of List<Cat>
    copyData(dogs, animals) // ok, since a List<Animal> is a subtype of List<Dog>

//    copyData(cats, dogs) // compile time error, since we want to write cats to the list of dogs

    println(animals)
}

fun <T> copyData(source: MutableList<T>,
                 destination: MutableList<in T>) {
    for (element in source) {
        destination.add(element)
    }
}