package com.tangpj.repository.entry.actor.git

import com.tangpj.repository.entry.Entry
import java.util.Date

/**
 *
 * Represents an actor in a Git commit (ie. an author or committer).
 * [date] The timestamp of the Git action (authoring or committing).
 *
 * @className: GitActor
 * @author create by Tang
 * @date  17:45
 */
open class GitActor(
        override val id: String,
        open val name: String,
        open val email: String,
        open val avatarUrl: String,
        open val date: Date

) : Entry(id)