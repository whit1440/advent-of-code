package twentytwenty.eighth

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class EighthModuleTest {
    @Test
    fun testCpu() {
        val cpu = EighthModule.Cpu(
            instructions = listOf(
                EighthModule.Instruction("acc", -1)
            )
        )

        cpu.execute(cpu.instructions[0])

        assertTrue(cpu.instructions[0].hasBeenExecuted)
        assertEquals(-1, cpu.accumulator)
    }
}