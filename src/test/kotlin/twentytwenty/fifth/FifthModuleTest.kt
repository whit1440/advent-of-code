package twentytwenty.fifth

import org.junit.Test
import twentytwenty.fifth.FifthModule.lowerHalf
import twentytwenty.fifth.FifthModule.upperHalf
import kotlin.test.assertEquals

class FifthModuleTest {
    @Test
    fun testLowerIntRange() {
        // even..even
        assertEquals((6..7), (6..10).lowerHalf())
        // odd..odd
        assertEquals((5..6), (5..9).lowerHalf())
        // odd..even
        assertEquals((5..7), (5..10).lowerHalf())
        // even..odd
        assertEquals((6..8), (6..11).lowerHalf())
        // sequential
        assertEquals((6..6), (6..7).lowerHalf())
    }

    @Test
    fun testUpperIntRange() {
        // even..even
        assertEquals((8..10), (6..10).upperHalf())
        // odd..odd
        assertEquals((7..9), (5..9).upperHalf())
        // odd..even
        assertEquals((8..10), (5..10).upperHalf())
        // even..odd
        assertEquals((9..11), (6..11).upperHalf())
        // sequential
        assertEquals((7..7), (6..7).upperHalf())
    }
}