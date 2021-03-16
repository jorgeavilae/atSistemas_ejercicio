@file:JvmName("GenericsInKt")

package com.example.data.models

open class EstudianteColegio(private val nombre: String) {
    var num: Int = 10
    fun sumar() : Int {
        return num + num
    }

    open fun quienSoy(): String {
        return "Soy $nombre y sé sumar({${sumar()}})"
    }
}

open class EstudianteInstituto(private val nombre: String) : EstudianteColegio(nombre) {
    /*
    var num: Int = 10
    fun sumar() : Int {
        return num + num
    }
     */
    fun multiplicar() : Int {
        return num * num
    }

    override fun quienSoy(): String {
        return "Soy $nombre y sé sumar({${sumar()}})" + " y multiplicar({${multiplicar()}})"
    }
}

class EstudianteUniversidad(private val nombre: String) : EstudianteInstituto(nombre) {
    /*
    var num: Int = 10
    fun sumar() : Int {
        return num + num
    }
    fun multiplicar() : Int {
        return num * num
    }
    */
    fun dividir() : Int {
        return num/2
    }

    override fun quienSoy(): String {
        return "Soy $nombre y sé sumar({${sumar()}})" + " y multiplicar({${multiplicar()}})" + " y dividir({${dividir()}})"
    }
}

class Clase<T : EstudianteColegio>(private val estudiante: T) {
    fun get() : T {
        estudiante.num = 5
        return estudiante
    }
    fun presentacion(persona: T) : String {
        return "Me presento: " + persona.quienSoy()
    }
}

class ClaseProducer<out T: EstudianteColegio>(private val estudiante: T) {
    fun get() : T {
        estudiante.num = 5
        return estudiante
    }
}

class ClaseConsumer<in T: EstudianteColegio>() {
    fun presentacion(estudiante: T) : String {
        return "Me presento: " + estudiante.quienSoy()
    }
}

// Test nada
fun main() {
    val bachillerato: Clase<EstudianteInstituto> = Clase(EstudianteInstituto("Bea"))

//    val primaria: Clase<EstudianteColegio> = Clase(EstudianteColegio("Alba"))
//    val primaria: Clase<EstudianteColegio> = bachillerato
//    val carrera: Clase<EstudianteUniversidad> = Clase(EstudianteUniversidad("Carla"))
//    val carrera: Clase<EstudianteUniversidad> = bachillerato

//    println(primaria.get().quienSoy())
    println(bachillerato.get().quienSoy())
//    println(carrera.get().quienSoy())

//    println(primaria.presentacion())
//    println(bachillerato.presentacion())
//    println(carrera.presentacion())
}

// Test Out
fun mainasd() {
    val bachillerato: ClaseProducer<EstudianteInstituto> = ClaseProducer(EstudianteInstituto("Bea"))

//    val primaria: ClaseProducer<EstudianteColegio> = ClaseProducer(EstudianteColegio("Alba"))
    val primaria: ClaseProducer<EstudianteColegio> = bachillerato
//    val carrera: ClaseProducer<EstudianteUniversidad> = ClaseProducer(EstudianteUniversidad("Carla"))
//    val carrera: ClaseProducer<EstudianteUniversidad> = bachillerato

    println(primaria.get().quienSoy())
    println(bachillerato.get().quienSoy())
//    println(carrera.get().quienSoy())
}

// Test In
fun mainqwe() {
    val bachillerato: ClaseConsumer<EstudianteInstituto> = ClaseConsumer()

//    val primaria: ClaseConsumer<EstudianteColegio> = ClaseConsumer(EstudianteColegio("Alba"))
//    val primaria: ClaseConsumer<EstudianteColegio> = bachillerato
//    val carrera: ClaseConsumer<EstudianteUniversidad> = ClaseConsumer(EstudianteUniversidad("Carla"))
    val carrera: ClaseConsumer<EstudianteUniversidad> = bachillerato

//    println(primaria.presentacion(EstudianteInstituto("Bea")))
    println(bachillerato.presentacion(EstudianteInstituto("Bea")))
//    println(carrera.presentacion(EstudianteInstituto("Bea")))
}