package com.example.kotlinmessenger.messages

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.kotlinmessenger.R
import com.example.kotlinmessenger.models.ChatMessage
import com.example.kotlinmessenger.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.activity_chat_log.*
import kotlinx.android.synthetic.main.chat_from_row.view.*
import kotlinx.android.synthetic.main.chat_to_row.view.*

class ChatLogActivity : AppCompatActivity() {

    companion object {
        val TAG = "ChatLog"
    }

    val adapter = GroupAdapter<GroupieViewHolder>()
    var toUser: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_log)

        toUser = intent.getParcelableExtra<User>(NewMessageActivity.USER_KEY)
        supportActionBar?.title = toUser?.username

        recyclerview_chat_log.adapter = adapter

        listenForMessages()

        send_button_chat_log.setOnClickListener {
            Log.d(TAG, "Send message")
            sendMessage()
        }
    }

    private fun listenForMessages() {
        val fromId = LatestMessagesActivity.currentUser?.uid
        val toId = toUser?.uid
        val ref = FirebaseDatabase.getInstance().getReference("/user-messages/$fromId/$toId")

        ref.addChildEventListener(object : ChildEventListener {
            override fun onCancelled(p0: DatabaseError) {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val chatMessage = p0.getValue(ChatMessage::class.java)
                Log.d(TAG, chatMessage?.text!!)

                if (chatMessage.fromId == FirebaseAuth.getInstance().uid) {
                    val currentUser = LatestMessagesActivity.currentUser
                    adapter.add(ChatFromItem(chatMessage.text, currentUser!!))
                } else {
                    adapter.add(ChatToItem(chatMessage.text, toUser!!))
                }
            }

            override fun onChildRemoved(p0: DataSnapshot) {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })
    }

    private fun sendMessage() {
        val fromId = LatestMessagesActivity.currentUser?.uid
        val toId = toUser?.uid
        val ref = FirebaseDatabase.getInstance().getReference("/user-messages/$fromId/$toId").push()

        val text = edittext_chat_log.text.toString()

        val chatMessage = ChatMessage(
            ref.key!!,
            text,
            fromId!!,
            toId!!,
            System.currentTimeMillis() / 1000
        )
        ref.setValue(chatMessage)
            .addOnSuccessListener {
                Log.d(TAG, "Saved message: ${ref.key}")
            }


    }

}

class ChatFromItem(val text: String, val user: User) : Item<GroupieViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.chat_from_row
    }

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.textview_from_row.text = text

        if (user.profileImageUrl.isNotEmpty())
            Picasso.get()
                .load(user.profileImageUrl)
                .into(viewHolder.itemView.imageview_from_row)
    }

}

class ChatToItem(val text: String, val user: User) : Item<GroupieViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.chat_to_row
    }

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.textview_to_row.text = text

        if (user.profileImageUrl.isNotEmpty())
            Picasso.get()
                .load(user.profileImageUrl)
                .into(viewHolder.itemView.imageview_to_row)
    }

}
