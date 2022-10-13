package dev.suoxing.kmmfoundation

class Greeting {
    fun greeting(): String {
        return "Hello, ${Platform().platform}!"
    }
}