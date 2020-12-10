package twentytwenty.first

import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet
import twentytwenty.Solution

@Module
object FirstModule {

    @Provides
    @IntoSet
    fun part1(): Solution = object : Solution {
        override val day: Int = 1
        override val part: Int = 1

        // start from front and back of sorted list and moving
        // inward based on sum at given points.
        override fun solve(): Any {
            return TestInput().input.split("\n").map {
                it.toInt()
            }.sorted().let {
                check(it, 0, it.size - 1)
            }
        }

        tailrec fun check(l: List<Int>, i: Int, j: Int): Int {
            val s = l[i]
            val e = l[j]
            val sum = s + e
            return when {
                sum == 2020 -> s * e
                sum < 2020 -> check(l, i + 1, j)
                sum > 2020 -> check(l, i, j - 1)
                else -> 0
            }
        }
    }

    @Provides
    @IntoSet
    fun part2(): Solution = object : Solution {
        override val day: Int = 1
        override val part: Int = 2

        // start with 2 next to each other the head of sorted input list and
        // one at back. move middle from start to end checking sums. once end is
        // reached then decrement the end index. Once end can no longer be
        // decremented, increment the first head position and repeat.
        override fun solve(): Any {
            return TestInput().input.split("\n").map {
                it.toInt()
            }.sorted().let {
                check(it, 0, 1, it.size - 1)
            }
        }

        tailrec fun check(l: List<Int>, i: Int, j: Int, k: Int): Int {
            val s = l[i]
            val m = l[j]
            val e = l[k]
            val sum = s + m + e
            return when {
                sum == 2020 -> s * m * e
                else -> {
                    if (i + 2 == k) check(l, i+1, i+2, l.size - 1)
                    else if (j + 1 == k) check(l, i, i+1, k-1)
                    else check(l, i, j+1, k)
                }
            }
        }
    }
}