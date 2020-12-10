package twentytwenty.second

import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet
import twentytwenty.Solution

@Module
object SecondModule {
    data class PasswordInput(
        val min: Int,
        val max: Int,
        val char: String,
        val pw: String
    ) {
        fun isValidOld() : Boolean {
            return """[$char]""".toRegex().findAll(pw).toList().let {
                it.size in min..max
            }
        }

        fun isValidNew() : Boolean {
            return pw[min-1] == char[0] && pw[max-1] != char[0] ||
                    pw[min-1] != char[0] && pw[max-1] == char[0]
        }
    }
    fun toPasswordInput(input: String) : PasswordInput {
        return """([\d]+)-([\d]+) ([\w]): ([\w]+)""".toRegex().find(input)?.groupValues?.let {
            PasswordInput(it[1].toInt(), it[2].toInt(), it[3], it[4])
        } ?: throw Throwable("Couldn't parse string $input")
    }

    @Provides
    @IntoSet
    fun part1() : Solution = object : Solution {
        override val day: Int = 2
        override val part: Int = 1

        override fun solve(): Any {
            return TestInput().input.split("\n")
                .map(::toPasswordInput)
                .filter { it.isValidOld() }
                .size
        }
    }

    @Provides
    @IntoSet
    fun part2() : Solution = object : Solution {
        override val day: Int = 2
        override val part: Int = 2

        override fun solve(): Any {
            return TestInput().input.split("\n")
                .map(::toPasswordInput)
                .filter { it.isValidNew() }
                .size
        }
    }
}