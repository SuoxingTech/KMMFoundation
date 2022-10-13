package dev.suoxing.kmm_arch

import android.app.Application

/**
 * Store [Application] reference in global memory, bit of ugly
 *
 * @author Yifan Wong
 * @since 2022-08-19
 */
object ApplicationWrapper {

    lateinit var instance: Application

    fun init(application: Application) {
        instance = application
    }
}