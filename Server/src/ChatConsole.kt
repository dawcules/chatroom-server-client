class ChatConsole :ChatObserver{ // Tulostaa konsoliin viestit muotoiltuna. Observaattori historialle.

    override fun chatUpdate(item: ChatMessage) {
        System.out.println(item.createMessage())
    }
}