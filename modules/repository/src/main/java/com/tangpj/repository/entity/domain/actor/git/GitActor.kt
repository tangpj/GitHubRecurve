package com.tangpj.repository.entity.domain.actor.git

import androidx.room.Ignore
import com.tangpj.repository.entity.BaseEntity
import org.threeten.bp.LocalDateTime

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
        @Ignore
        override val id: String,
        @Ignore
        open val name: String,
        @Ignore
        open val email: String,
        @Ignore
        open val avatarUrl: String,
        @Ignore
        open val date: LocalDateTime

) : BaseEntity(id)