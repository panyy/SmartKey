import cn.vove7.smartkey.BaseConfig
import cn.vove7.smartkey.annotation.Config
import cn.vove7.smartkey.key.smartKey
import java.util.*

/**
 * # RunConfig
 * Created by 11324.
 * Date: 2019/6/18
 */
@Config
object RunConfig : BaseConfig {


    var nullableString: String? by smartKey(null)

    var string: String? by smartKey("hello")


    var number: Int by smartKey(1)

    var nullableNumber: Int? by smartKey(null)

    var intArray: IntArray by smartKey(intArrayOf(1, 2), encrypt = true)

    var model: Model? by smartKey(null, encrypt = true)
}

class Model {
    var number = Random().nextInt(100)
    var arr = arrayOf(1, 2, number)

    override fun toString(): String {
        return "Model(number=$number, arr=${Arrays.toString(arr)})"
    }

}