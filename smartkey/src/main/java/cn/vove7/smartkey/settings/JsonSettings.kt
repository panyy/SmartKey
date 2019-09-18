package cn.vove7.smartkey.settings

import cn.vove7.smartkey.tool.Vog
import cn.vove7.smartkey.tool.fromJson
import cn.vove7.smartkey.tool.toJson
import java.io.File

/**
 * # JsonSettings
 * 进程不安全
 * @author Vove
 * 2019/7/25
 */
class JsonSettings(private val configName: String) : BaseSyncFileSetting() {

    override val configFile: File
        get() = File(configPath, fileName)
    lateinit var map: ObserveableMap<String, Any>

    companion object {
        var CONFIG_DIR: String? = null
    }

    val configPath = if (CONFIG_DIR == null) {
        "config"
    } else {
        "$CONFIG_DIR/config"
    }

    private val fileName = "$configName.json"

    init {
        File(configPath).apply {
            if (!exists()) {
                mkdirs()
            }
        }
    }

    override fun onReloadConfig(cf: File) {
        val jmap = cf.let {
            Vog.d("配置路径：${it.absolutePath}")
            if (it.exists()) {
                it.readText().fromJson<MutableMap<String, Any>>()
            } else {
                mutableMapOf<String, Any>()
            }
        }
        map = ObserveableMap(jmap)
        map.lis = {
            sync()
        }
        Vog.d("onReloadConfig $map")
    }

    private fun sync() {
        File(configPath, fileName).writeText(map.toJson(true))
    }

    override fun clear() {
        map.clear()
    }

    override fun remove(key: String) {
        map.remove(key)
    }

    override fun hasKey_(key: String): Boolean = key in map

    override fun putInt_(key: String, value: Int) {
        map[key] = value
    }

    override fun getInt(key: String, defaultValue: Int): Int {
        return ((map[key] as? Number)?.toInt()) ?: defaultValue
    }

    override fun putLong_(key: String, value: Long) {
        map[key] = value
    }

    override fun getLong(key: String, defaultValue: Long): Long {
        return ((map[key] as? Number)?.toLong()) ?: defaultValue
    }

    override fun putString_(key: String, value: String) {
        map[key] = value
    }

    override fun getString(key: String, defaultValue: String): String {
        return map[key]?.toString() ?: defaultValue
    }

    override fun putFloat_(key: String, value: Float) {
        map[key] = value
    }

    override fun getFloat(key: String, defaultValue: Float): Float {
        return ((map[key] as? Number)?.toFloat()) ?: defaultValue
    }

    override fun putDouble_(key: String, value: Double) {
        map[key] = value
    }

    override fun getDouble(key: String, defaultValue: Double): Double {
        return ((map[key] as? Number)?.toDouble()) ?: defaultValue
    }

    override fun putBoolean_(key: String, value: Boolean) {
        map[key] = value
    }

    override fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return map[key]?.toString()?.toBoolean() ?: defaultValue
    }
}


/**
 * # ObserveableMap
 *
 *
 * @author Vove
 * 2019/7/25
 */
class ObserveableMap<K, T> : HashMap<K, T> {

    var lis: (() -> Unit)? = null

    constructor(p0: MutableMap<out K, out T>?) : super(p0)
    constructor() : super()

    override fun remove(key: K): T? {
        return super.remove(key).also { lis?.invoke() }
    }

    override fun put(key: K, value: T): T? {
        return super.put(key, value).also { lis?.invoke() }
    }

    override fun clear() {
        super.clear()
        lis?.invoke()
    }
}