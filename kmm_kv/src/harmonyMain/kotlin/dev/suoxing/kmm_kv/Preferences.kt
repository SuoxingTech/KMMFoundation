package dev.suoxing.kmm_kv

@JsModule("@ohos.data.preferences")
external object Preferences {

    interface Options {
        /**
         * The preferences file name.
         *
         * @syscap SystemCapability.DistributedDataManager.Preferences.Core
         * @crossplatform
         * @since 10
         */
        /**
         * The preferences file name.
         *
         * @type { string }
         * @syscap SystemCapability.DistributedDataManager.Preferences.Core
         * @crossplatform
         * @atomicservice
         * @since 11
         */
        val name: String;
        /**
         * Application Group Id.
         *
         * @syscap SystemCapability.DistributedDataManager.Preferences.Core
         * @StageModelOnly
         * @since 10
         */
        /**
         * Application Group Id.
         *
         * @type { ?(string | null | undefined) }
         * @syscap SystemCapability.DistributedDataManager.Preferences.Core
         * @StageModelOnly
         * @atomicservice
         * @since 11
         */
        val dataGroupId: String?;
    }

    fun getPreferencesSync(context: Context, options: Options): Preferences

    fun <T> getSync(key: String, defValue: T): T

    fun <T> putSync(key: String, value: T)
}

@JsModule("application/Context")
external class Context {

}