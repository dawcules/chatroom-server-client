import java.net.ServerSocket

class ChatServer { //Ottaa käyttöön portin 55315, rekisteröi observerit ja käynnistää omissa Threadeissaan jokaiselle yhteydelle oman komentotulkin.

    val ss = ServerSocket(55315)
    val cc = ChatConsole()
    val tc = TopChatter

    fun serve() {
        println("We have port " + ss.localPort)
        History.register(cc)
        History.register(tc)
        while (true) {
            val s = ss.accept()
            println("New connection")
            val ci = CommandInterpreter(s.getInputStream(), s.getOutputStream())
            val t = Thread(ci)
            History.register(ci)
            t.start()
        }
    }
}