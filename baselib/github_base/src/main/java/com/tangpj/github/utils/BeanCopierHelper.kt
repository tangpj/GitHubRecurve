package com.tangpj.github.utils

import net.sf.cglib.beans.BeanCopier
import net.sf.cglib.core.Converter
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 使用Dagger2保证单例
 *
 * @className: BeanCopierHelper
 * @author: tangpj
 * @createTime: 2019/3/7 23:04
 */
@Singleton
class BeanCopierHelper @Inject constructor(){

    private val copierMap: Map<String, BeanCopier> = ConcurrentHashMap()

    @JvmOverloads
    fun <T: Any> copy(from: Any, to: T, converter: Converter? = null): T{
        val useConverter = converter != null
        val key = from.javaClass.name + to.javaClass.name
        val copier: BeanCopier = if (copierMap.containsKey(key)){
            copierMap[key] ?: BeanCopier.create(from.javaClass, to.javaClass, useConverter)
        }else{
            BeanCopier.create(from.javaClass, to.javaClass, useConverter)
        }

        if (useConverter){
            copier.copy(from, to, null)
        }else{
            copier.copy(from, to, converter)
        }
        return to
    }

}

