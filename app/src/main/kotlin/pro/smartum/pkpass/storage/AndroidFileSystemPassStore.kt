package pro.smartum.pkpass.storage

import android.content.Context
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.Moshi
import okio.Okio
import pro.smartum.pkpass.BuildConfig
import pro.smartum.pkpass.app.Settings
import pro.smartum.pkpass.model.pass.Pass
import pro.smartum.pkpass.model.pass.PassImpl
import pro.smartum.pkpass.reader.AppleStylePassReader
import pro.smartum.pkpass.reader.PassReader
import pro.smartum.pkpass.util.Logger
import java.io.File
import java.util.*

class AndroidFileSystemPassStore(private val context: Context, settings: Settings, private val moshi: Moshi) : PassStore {
    private val path: File = settings.getPassesDir()

    override val passMap = HashMap<String, Pass>()

    override var currentPass: Pass? = null

    override fun save(pass: Pass) {
        val jsonAdapter = moshi.adapter(PassImpl::class.java)

        val pathForID = getPathForID(pass.id)

        if (!pathForID.exists())
            pathForID.mkdirs()

        val buffer = Okio.buffer(Okio.sink(File(pathForID, "main.json")))

        if (BuildConfig.DEBUG) {
            val of = com.squareup.moshi.JsonWriter.of(buffer)
            of.setIndent("  ")
            jsonAdapter.toJson(of, pass as PassImpl)
            buffer.close()
            of.close()
        } else {
            jsonAdapter.toJson(buffer, pass as PassImpl)
            buffer.close()
        }

        passMap[pass.id] = pass
    }

    private fun readPass(id: String): Pass? {
        val pathForID = getPathForID(id)
        val language = context.resources.configuration.locale.language

        if (!pathForID.exists() || !pathForID.isDirectory)
            return null

        val file = File(pathForID, "main.json")
        var result: Pass? = null
        var dirty = true
        if (file.exists()) {
            val jsonAdapter = moshi.adapter(PassImpl::class.java)
            dirty = false
            try {
                result = jsonAdapter.fromJson(Okio.buffer(Okio.source(file)))
            } catch (ignored: JsonDataException) {
                ignored.printStackTrace()
                Logger.d(javaClass, "invalid main.json")
            }
        }

        if (result == null && File(pathForID, "data.json").exists()) {
            result = PassReader.read(pathForID)
            File(pathForID, "data.json").delete()
        }

        if (result == null && File(pathForID, "pass.json").exists())
            result = AppleStylePassReader.read(pathForID, language, context)

        if (result != null) {
            if (dirty)
                save(result)
            passMap.put(id, result)
            notifyChange()
        }

        return result
    }

    override fun getPassbookForId(id: String): Pass? {
        return passMap[id] ?: readPass(id)
    }

    override fun deletePassWithId(id: String): Boolean {
        val result = getPathForID(id).deleteRecursively()
        if (result) {
            passMap.remove(id)
            notifyChange()
        }
        return result
    }

    override fun getPathForID(id: String): File {
        return File(path, id)
    }

    override fun notifyChange() {}
}
