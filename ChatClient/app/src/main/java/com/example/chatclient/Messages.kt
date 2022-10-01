package com.example.chatclient

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.EditText
import android.widget.TextView
import kotlinx.android.synthetic.main.content_messages.*
import java.io.PrintStream
import java.net.InetAddress
import java.net.Socket
import java.util.*


class Messages : AppCompatActivity() {

    val tag = "Messages"
    lateinit var socket: Socket
    lateinit var printer: PrintStream
    lateinit var scanner: Scanner
    var message = "def" // == ei viestiä
    val mydata = arrayListOf<String>() // Kerätään viestit listaan

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val uname = intent.getStringExtra(EXTRA_MESSAGE) // Edellisen activityn käyttäjänimi
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE) // UI liikkuu näppäimistön avautuessa
        setContentView(R.layout.activity_messages)
        recyclerView2.layoutManager = LinearLayoutManager(this)
        recyclerView2.adapter = MyRecyclerViewAdapter(this, mydata)
        Log.d(tag, "onCreate(Bundle) called")

        Thread {
            // Luodaan yhteys ja luetaan tulevia viestejä
            socket = Socket(InetAddress.getByName("10.0.0.44"), 55315)
            Log.d(tag, "Connected")
            printer = PrintStream(socket.getOutputStream(), true)
            scanner = Scanner(socket.getInputStream())
            printer.println(":user $uname") // Automaattinen rekisteröityminen
            while (true) {
                val scanline = scanner.nextLine()
                mydata.add(scanline)
                runOnUiThread(object : Runnable {
                    override fun run() {
                        recyclerView2.getAdapter()?.notifyDataSetChanged() // Näkymä päivittyy viestin tullessa
                        recyclerView2.scrollToPosition(recyclerView2.adapter!!.getItemCount() - 1) // Uusi viesti näyttää automaattisesti alimman rivin
                    }
                })
            }
        }.start()

        Thread {
            // Viestien lähetys. Viestin sisältö asetetaan sendMessage() metodissa
            while (true) {
                if (message != "def") {
                    Log.d("BCLICK", "Viesti on $message")
                    printer.println(message)
                    Log.d("BCLICK", "Viesti lähetetty")
                    message = "def"
                }
            }

        }.start()
    }

    fun sendMessage(view: View) { // Ei toimi mikäli "view" parametrin poistaa, vaikka väittää turhaksi.
        val editText = findViewById<EditText>(R.id.editText2)
        message = editText.text.toString() // Otetaan tekstisyöte ja asetetaan se message muuttujaan, jonka lähetys thread poimii
        Log.d("BCLICK", "Metodi ok")
    }

    override fun onStop() {
        super.onStop()
        socket.close()
    }

}

class MyRecyclerViewAdapter(private val context: Context, private val mydata: List<String>):
    RecyclerView.Adapter<MyViewHolder>() {
        override fun onCreateViewHolder(vg: ViewGroup, vt: Int): MyViewHolder {
            Log.d("ZZZ", "onCreateViewHolder()")
            val itemView = LayoutInflater.from(context).inflate(R.layout.linearlayout, vg, false)
            return MyViewHolder(itemView)
        }

        override fun getItemCount(): Int {
            return mydata.size
        }

        override fun onBindViewHolder(vh: MyViewHolder, pos: Int) {
            Log.d("ZZZ", "onBindViewHolder($pos)")
            vh.itemView.findViewById<TextView>(R.id.textView2).text = mydata[pos] // Lisätään viesti näkymään uudelle riville
            if (pos%2==0) {
                vh.itemView.findViewById<TextView>(R.id.textView2).setBackgroundColor(Color.WHITE) // Vaihdellaan viestien taustaväriä valkoisen ja harmaan välillä
            }
            else {
                vh.itemView.findViewById<TextView>(R.id.textView2).setBackgroundColor(Color.LTGRAY)
            }
            val registered = mydata[0]
            val parsed = registered.split(" ") // Verrataan käyttäjän nimimerkkiä saapuneen viestin nimimerkkiin
            Log.d("NIMI","registered = $registered")
            Log.d("PARSE","$parsed")
            val uname = parsed[3]
            Log.d("NIMI", "I think the username is $uname")
            val ucheck = mydata[pos]
            val uparsed = ucheck.split(" ")
            Log.d("PARSE","Viesti parse: $uparsed")
            Log.d("NIMI","I think the current username is ${uparsed[0]}")
            if (uparsed[0]==uname) {
                vh.itemView.findViewById<TextView>(R.id.textView2).setTextColor(Color.BLUE) // Asetetaa viestin väri siniseksi, mikäli se vastaa omaa nimimerkkiä
            }
            else {
                vh.itemView.findViewById<TextView>(R.id.textView2).setTextColor(Color.BLACK)
            }
        }
    }

        class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {}



