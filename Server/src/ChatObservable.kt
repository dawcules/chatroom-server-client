interface ChatObservable { //Observable interface

    fun register (who: ChatObserver)
    fun deregister (who: ChatObserver)
    fun notify(item: ChatMessage)
}