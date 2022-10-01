import java.io.PrintStream
import kotlin.collections.HashSet

object Users {

    val userbank = hashSetOf<String>()


    fun addUser(username: String) {
        userbank.add(username)
    }

    fun printUsers():HashSet<String> {
        return userbank

    }
}