package com.tangpj.github.utils


/**
 * 数据转换接口
 *
 * @className: Mapping
 * @author: tangpj
 * @createTime: 2019/3/7 23:04
 */

interface Mapping{
    fun <T,R> mapping(t: T) : R
}

