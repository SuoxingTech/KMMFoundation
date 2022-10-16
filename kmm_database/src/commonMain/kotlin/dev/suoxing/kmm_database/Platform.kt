package dev.suoxing.kmm_database

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform