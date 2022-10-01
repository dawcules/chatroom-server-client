object History: ChatObservable { //Viestien hallinta ja historia.

    private val observers = mutableSetOf<ChatObserver>()
    private val chatItems = mutableListOf<ChatMessage>()
    val chathistory = mutableListOf<String>()

    override fun deregister(who: ChatObserver) {
    observers.remove(who)
    }

    override fun register(who: ChatObserver) {
        observers.add(who)
    }

    override fun notify(item: ChatMessage) {
        chatItems.add(item)
        println("Current observers:" + observers) //Konsolissa nähdään kuuntelijat
        observers.forEach{it.chatUpdate(item)}
    }

    fun insert(message: ChatMessage) { // Säilytetään 10 viimeisintä viestiä
        if (chathistory.size == 10) {
            chathistory.removeAt(chathistory.size - 10)
        }

        chathistory.add(message.createMessage())
        notify(message)
    }

    fun theHistory():MutableList<String> { //Palautetaan koko lista
        return chathistory
    }
}





