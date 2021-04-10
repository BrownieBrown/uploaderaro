package mbraun.uploaderaro.model

import org.hibernate.annotations.GenericGenerator
import javax.persistence.*

@Entity
@Table
data class File(
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private val id: String = "",
    private var name: String = "",
    private var type: String = "",
    @Lob
    private var data: ByteArray = byteArrayOf()
) {
    fun getId() : String {
        return id
    }

    constructor(name: String, type: String, data: ByteArray) : this() {
        this.name = name
        this.type = type
        this.data = data
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as File

        if (id != other.id) return false
        if (name != other.name) return false
        if (type != other.type) return false
        if (!data.contentEquals(other.data)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + type.hashCode()
        result = 31 * result + data.contentHashCode()
        return result
    }

    fun getName(): String {
        return name
    }

    fun getType(): String {
        return type
    }

    fun getData(): ByteArray {
        return data
    }
}