package com.tangpj.github

import arrow.core.extensions.monoid
import arrow.core.extensions.semigroup
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, (2 + 2).toLong())
        val hello = "Hello"
        val word = " word"

        String.semigroup().run {
            println(hello.combine(word))
        }
        val monoid1 = String.monoid().combineAll(listOf(hello, word))
        val monoid2 = String.monoid().combineAll(listOf(" hhhhh", " wwwww"))
        val result = String.monoid().combineAll(listOf(monoid1, monoid2))
        println(result)
    }


}