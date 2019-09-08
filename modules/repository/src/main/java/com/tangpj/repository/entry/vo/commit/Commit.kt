package com.tangpj.repository.entry.vo.commit

import com.tangpj.repository.entry.Entry
import com.tangpj.repository.entry.actor.git.Committer
import java.util.*

data class Commit(
        override
        val id: String,
        val message: String,
        val committer: Committer,
        val committedDate: Date,
        val commentCount: String

) : Entry(id)