import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ChatMessage(message: String, username: String) { //Muuttaa käyttäjän syötteen muotoilluksi viestiksi

    val dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    val now = LocalDateTime.now()
    val a= (dtf.format(now))
    var gotmessage = message
    var uname = username

    fun createMessage(): String {
        val newmessage = (uname+" at " + a + " "+gotmessage)
        return newmessage
    }
}