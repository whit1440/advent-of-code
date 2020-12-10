package twentytwenty.third

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ThirdModuleTest {

    val input = """
                ..........
                ..#.......
                .#........
                ...#......
                ..#.......
                ....#.....
                ...#......
                .....#....
                ....#.....
                ......#.#.
            """.trimIndent()

    @Test
    fun testParsing() {
        val rows = ThirdModule.createRows(input)
        assertEquals(10, rows.size)
        assertEquals("..........", rows[0].squares)
    }

    @Test
    fun testRows() {
        val r = ThirdModule.Row("..#..#")

        assertTrue(r.hasTreeAt(2))
        assertTrue(r.hasTreeAt(5))
        assertTrue(r.hasTreeAt(8))
        assertTrue(r.hasTreeAt(11))
    }

    @Test
    fun testRun() {
        assertEquals(
            4,
            ThirdModule.run(1,2, input)
        )
        assertEquals(
            2,
            ThirdModule.run(2,1, input)
        )
    }
}