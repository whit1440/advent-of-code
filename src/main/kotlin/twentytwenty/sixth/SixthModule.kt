package twentytwenty.sixth

import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet
import twentytwenty.Solution

@Module
object SixthModule {
    @Provides
    @IntoSet
    fun getPart2() : Solution = object : Solution {
        override val day: Int = 6
        override val part: Int = 2

        override fun solve(): Any {
            return TestInput().input.split("\n\n").map {
                it.split("\n").fold("abcdefghijklmnopqrstuvwxyz".toCharArray().toSet()) { acc: Set<Char>, b: String ->
                    acc.intersect(b.toCharArray().toList())
                }.size
            }.sum()
        }
    }

    @Provides
    @IntoSet
    fun getPart1() : Solution = object : Solution {
        override val day: Int = 6
        override val part: Int = 1

        override fun solve(): Any {
            return TestInput().input.split("\n\n").map {
                it.split("\n").fold("".toCharArray().toSet()) { acc: Set<Char>, b: String ->
                    acc.union(b.toCharArray().toList())
                }.size
            }.sum()
        }
    }
}