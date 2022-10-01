object TopChatter:ChatObserver {

    val chatters = hashMapOf<String, Int>()

    override fun chatUpdate(item: ChatMessage) {
        val message = item.createMessage()
        val splitmessage = message.split(" ")
        val user = splitmessage[0]
        if (chatters.contains(user)){
            val value = chatters.get(user)
            if (value != null) {
                val newvalue = value +1
                chatters.put(user,newvalue)
            }
        }

        else {
            chatters.put(user,1)
        }

        System.out.println("Chatters and message count: " + chatters)
    }
}