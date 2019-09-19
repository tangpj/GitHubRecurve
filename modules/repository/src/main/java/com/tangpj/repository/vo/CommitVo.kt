package com.tangpj.repository.vo

import com.tangpj.repository.entity.domain.actor.git.Committer
import com.tangpj.repository.entity.domain.commit.Commit

/**
 *  UI展现类，只作用与UI层，不会存储数据库中
 *
 * @className: CommitVo
 * @author: tangpj
 * @createTime: 2019-09-09 16:00
 */
class CommitVo (val commit: Commit = Commit(""), val committer: Committer = Committer(""))