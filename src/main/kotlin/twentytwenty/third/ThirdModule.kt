package twentytwenty.third

import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet
import twentytwenty.Solution

@Module
object ThirdModule {

    const val TREE : String = "#"

    data class Row(
        val squares: String
    ) {
        fun hasTreeAt(position: Int) : Boolean {
            return squares[position % squares.length] == TREE[0]
        }
    }

    fun createRows(input: String): List<Row> = input.split("\n").map {
        Row(it)
    }

    fun run(dx: Int, dy: Int, input: String) : Int {
        val rows = createRows(input)
        var x = 0
        var y = 0
        var out = 0
        do {
            if (y + dy >= rows.size) break
            x += dx
            y += dy
            if (rows[y].hasTreeAt(x)) out += 1
        } while(y < rows.size)
        return out
    }

    @Provides
    @IntoSet
    fun part1() : Solution = object : Solution {
        override val day: Int
            get() = 3
        override val part: Int
            get() = 1

        override fun solve(): Any {
            return run(3, 1, TestInput().input)
        }
    }

    @Provides
    @IntoSet
    fun part2() : Solution = object : Solution {
        override val day: Int
            get() = 3
        override val part: Int
            get() = 2

        override fun solve(): Any {
            val input = TestInput().input
            return listOf(
                Pair(1, 1),
                Pair(3, 1),
                Pair(5, 1),
                Pair(7, 1),
                Pair(1, 2)
            ).map {
                run(it.first, it.second, input).toLong()
            }.fold(1L) { acc, i ->
                println("$acc $i")
                acc * i
            }
        }
    }
}