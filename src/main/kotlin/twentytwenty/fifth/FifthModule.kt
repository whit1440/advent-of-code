package twentytwenty.fifth

import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet
import twentytwenty.Solution
import twentytwenty.fifth.FifthModule.range
import kotlin.math.ceil
import kotlin.math.floor

@Module
object FifthModule {

    fun IntRange.range() : Int = last - first + 1 // inclusive
    fun IntRange.lowerHalf() : IntRange = first..floor(first + (range() / 2f) - 1).toInt()
    fun IntRange.upperHalf() : IntRange = ceil(first + (range() / 2f) - (range() % 2)).toInt()..last

    data class Ticket(
        val code: String
    ) {
        fun rowCodes() : String = code.take(7)
        fun seatCodes() : String = code.takeLast(3)
        fun rowNumber() : Int = rowCodes().fold(0..127, { acc, i ->
            if (i == "F"[0]) acc.lowerHalf()
            else acc.upperHalf()
        }).let {
            if (it.first != it.last) throw Throwable("Should only have one value left in the range")
            else it.first
        }
        fun seatNumber() : Int = seatCodes().fold(0..7, { acc, i ->
            if (i == "L"[0]) acc.lowerHalf()
            else acc.upperHalf()
        }).let {
            if (it.first != it.last) throw Throwable("Should only have one value left in the range")
            else it.first
        }
        fun id() : Int = (8 * rowNumber()) + seatNumber()
    }

    @Provides
    @IntoSet
    fun part1() : Solution = object : Solution {
        override val day: Int = 5
        override val part: Int = 1

        override fun solve(): Any {
            return TestInput().input.split("\n").map {
                Ticket(it).id()
            }.maxOrNull() ?: 0
        }
    }

    @Provides
    @IntoSet
    fun part2() : Solution = object : Solution {
        override val day: Int = 5
        override val part: Int = 2

        override fun solve(): Any {
            return TestInput().input.split("\n").map {
                Ticket(it).id()
            }.sorted().takeLastWhile {
                // eliminate first row
                it > 7
            }.takeWhile {
                // eliminate back row
                it < 1024 // (max seat id)
            }.fold(0) { acc, i ->
                if (i - acc == 2) return acc + 1
                else i
            }
        }
    }
}