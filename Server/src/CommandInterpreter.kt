import java.io.InputStream
import java.io.OutputStream
import java.io.PrintStream
import java.util.*
class CommandInterpreter(a:InputStream, b:OutputStream): Runnable, ChatObserver { // Käsittelee käyttäjän syötteen ja ohjaa sen eteenpäin oikeille metodeille
    var userInputStream = a
    var userOutputStream = b
    val printer = PrintStream(userOutputStream)
    var username = ""
    override fun chatUpdate(item: ChatMessage) {
    printer.println(item.createMessage())
    }
    override fun run () {
        //printer.println("Input an username with :user <username>")
        val scanner = Scanner(userInputStream)

        while (true) {
            val command = scanner.nextLine()//.toString()
            println(command)
            if (username == "") { //Oletuksena käyttäjänimi on tyhjä. Käyttäjän on asetettava uusi nimi, jotta komennot ja viestit hyväksytään.
                val namesplit = command.split(" ") // Pätkitään syöte sanoittain välilyönnin perusteella
                if (namesplit.size > 1 && namesplit[0] == ":user") { // Jos ensimmäinen sana on :user ja sen jälkeen on vähintään yksi sana
                    println(namesplit[1])
                    username = namesplit[1]//Tehdään seuraavasta sanasta käyttäjänimi
                }

                if (Users.printUsers().contains(username) || username == "") { // Tarkistetaan onko nimi jo käytössä
                    //printer.println("Username not available")
                    username=""
                }

                else {
                    Users.addUser(username)
                }

            }

            if (command != "" && username!= "") {
                val test = ":".toCharArray()
                if (command[0] == test[0]) { //Tarkistetaan onko kyseessä komento ":"-merkin tarkistuksella
                    println("Command registered")
                    var i = 0
                    when (command.split(" ")[0]) { // Tulostetaan vain käyttäjälle komennon tulosteet PrintStreamilla
                        ":users" -> while (i < Users.printUsers().size){printer.println(Users.printUsers().elementAt(i))
                            i++
                        }
                        ":user" -> printer.println("Your username is $username")
                        ":history" -> while (i < History.theHistory().size){ printer.println(History.theHistory()[i])
                        i++
                        }
                    }
                }

                else {
                    println("Chat message")
                    val viesti = ChatMessage(command, username)
                    History.insert(viesti)
                }
            }

            else {
                //printer.println("Make sure you are a registered user (:user username) or that your input is not empty")
            }

            printer.flush()
        }
    }
}