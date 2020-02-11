package com.example.kotlinmessenger.messages

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kotlinmessenger.R
import com.example.kotlinmessenger.models.User
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.activity_chat_log.*

class ChatLogActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_log)

        val user = intent.getParcelableExtra<User>(NewMessageActivity.USER_KEY)
        supportActionBar?.title = user?.username

        val adapter = GroupAdapter<GroupieViewHolder>()

        adapter.add(ChatFromItem())
        adapter.add(ChatToItem())
        adapter.add(ChatFromItem())
        adapter.add(ChatFromItem())
        adapter.add(ChatToItem())
        adapter.add(ChatToItem())
        adapter.add(ChatFromItem())

        recyclerview_chat_log.adapter = adapter
    }
}

class ChatFromItem: Item<GroupieViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.chat_from_row
    }

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}

class ChatToItem: Item<GroupieViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.chat_to_row
    }

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
