package com.tangpj.repository.entity.domain.repo

import android.content.Context
import android.content.Intent
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import com.tangpj.github.entity.BaseEntity
import com.tangpj.repository.ui.detail.RepoDetailActivity
import com.tangpj.repository.entity.domain.actor.Owner
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(
        indices = [
            Index("id")],
        primaryKeys = ["owner_login", "name"]
)
data class Repo constructor(
        override val id: String,
        @Embedded(prefix = "owner_")
        val owner: Owner,
        val name: String,
        val fullName: String,
        val language: String,
        val languageColor: String,
        val description: String,
        val stars: Int,
        val forks: Int
): BaseEntity(id)

fun Repo.startRepoDetail(context: Context){
    //todo 暂时这样写，后续接入ARouter框架
    val intent: Intent = Intent(context, RepoDetailActivity::class.java)
    context.startActivity(intent)
}



