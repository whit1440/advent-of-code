package twentytwenty.eighth

import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet
import twentytwenty.Solution

@Module
object EighthModule {

    data class Instruction(
        var command: String,
        val value: Long,
        var executeCount: Int = 0
    )

    class InfiniteLoopException(val acc: Long) : Throwable("An infinite loop was encountered with accumulator value $acc")
    class UnableToCorrectException : Throwable("An attempt to fix the corrupted instructions has failed")

    open class Cpu (
        val instructions: List<Instruction>,
        val onLoop: Cpu.(Instruction) -> Unit
    ) {
        private var currentInstruction: Long = 0

        var accumulator: Long = 0
            private set
        val stack: MutableList<Instruction> = mutableListOf()

        fun reset() {
            currentInstruction = 0
            accumulator = 0
            stack.clear()
            instructions.forEach {
                it.executeCount = 0
            }
        }

        fun execute(instruction: Instruction) {
            if (instruction.executeCount > 0) {
                onLoop(instruction)
            } else when (instruction.command) {
                "acc" -> {
                    accumulator += instruction.value
                    currentInstruction += 1
                }
                "jmp" -> currentInstruction += instruction.value
                else -> currentInstruction += 1
            }.also {
                instruction.executeCount += 1
                stack.add(instruction)
            }
        }

        open fun run() : Long {
            while(currentInstruction < instructions.size) {
                execute(instructions[currentInstruction.toInt()])
            }
            return accumulator
        }
    }

    // when instruction loops, grab the stack
    // swap most recent instruction type and run instructions again
    // when loops again:
    // flip the previously changed instruction back
    // decrement the stack index
    // swap the new instruction
    // try again
    class SelfCorrectingCpu(
        instructions: List<Instruction>
    ) : Cpu(instructions, {
        with(this as SelfCorrectingCpu) {
            if (corruptStack == null) {
                corruptStack = stack
                corruptIndex = stack.size
            }
            attemptCorrection()
        }
        reset()
    }) {
        private var corruptStack: List<Instruction>? = null
        private var corruptIndex = -1

        private fun Instruction.swapJmpNop() {
            command = when(command) {
                "jmp" -> "nop"
                else -> "jmp"
            }
        }

        private fun attemptCorrection() {
            // put back previously changed instruction
            // the getOrNull here allows for the very first
            // try in which the index would be out of range
            (corruptStack?.getOrNull(corruptIndex))?.swapJmpNop()
            // decrement stack index
            corruptIndex -= 1
            // skip any "acc" commands
            while(
                corruptIndex >= 0 &&
                corruptStack?.get(corruptIndex)?.command == "acc"
            ) { corruptIndex -= 1 }
            // check that we're still in range
            if (corruptIndex < 0) throw UnableToCorrectException()
            // swap it out
            (corruptStack?.getOrNull(corruptIndex))?.swapJmpNop()
        }

        override fun run(): Long {
            // reset the corruption data after a successful run
            return super.run().also {
                corruptStack = null
                corruptIndex = -1
            }
        }
    }

    // parse a single line into an instruction
    fun parse(input: String) : Instruction {
        val parts = input.split(" ")
        return Instruction(
            command = parts[0],
            value = with(parts[1]) {
                if (take(1) == "-") 0 - drop(1).toLong()
                else drop(1).toLong()
            }
        )
    }

    @Provides
    @IntoSet
    fun part1() : Solution = object : Solution {
        override val day: Int = 8
        override val part: Int = 1

        override fun solve(): Any {
            return try {
                Cpu(
                    instructions = TestInput().input.split("\n")
                    .map(::parse),
                    onLoop = {
                        throw InfiniteLoopException(accumulator)
                    }
                ).run()
            } catch (t: InfiniteLoopException) {
                t.acc
            } catch (t: Throwable) {
                -1
            }
        }
    }

    @Provides
    @IntoSet
    fun part2() : Solution = object : Solution {
        override val day: Int = 8
        override val part: Int = 2

        override fun solve(): Any {
            return SelfCorrectingCpu(
                instructions = TestInput().input.split("\n")
                .map(::parse)
            ).run()
        }
    }
}