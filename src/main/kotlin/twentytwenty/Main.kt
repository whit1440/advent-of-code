package twentytwenty

import dagger.Component
import dagger.Module
import dagger.Provides
import twentytwenty.eighth.EighthModule
import twentytwenty.fifth.FifthModule
import twentytwenty.first.FirstModule
import twentytwenty.fourth.FourthModule
import twentytwenty.second.SecondModule
import twentytwenty.seventh.SeventhModule
import twentytwenty.sixth.SixthModule
import twentytwenty.third.ThirdModule
import java.time.Duration
import java.time.temporal.TemporalUnit
import javax.inject.Inject
import kotlin.time.ExperimentalTime
import kotlin.time.toKotlinDuration

@ExperimentalTime
fun main() {
    DaggerMainComponent.builder().build().inject().run()
}

interface Solution {
    val day: Int
    val part: Int
    fun solve() : Any
}

class Main @Inject constructor(
    private val solutions: Set<@JvmSuppressWildcards Solution>
) {
    @ExperimentalTime
    fun run() {
        val compare = compareBy<Solution> { it.day }.thenBy { it.part }
        solutions.sortedWith(compare).forEach {
            val start = System.nanoTime()
            val solution = it.solve()
            val end = System.nanoTime() - start
            println("Dec ${it.day.toPosition().padStart(4)} - part ${it.part} : " + "$solution".padEnd(20) + "solved in ${Duration.ofNanos(end).toKotlinDuration().inMilliseconds}ms")
        }
    }
}

// TODO - modules for each year
@Module(includes = [
    FirstModule::class,
    SecondModule::class,
    ThirdModule::class,
    FourthModule::class,
    FifthModule::class,
    SixthModule::class,
    SeventhModule::class,
    EighthModule::class
])
object MainModule

@Component(modules = [MainModule::class])
interface MainComponent {
    fun inject() : Main
}

fun Int.toPosition() : String {
    return with(this.toString()) {
        this + when {
            this.endsWith("11") -> "th"
            this.endsWith("12") -> "th"
            this.endsWith("13") -> "th"
            this.endsWith("1") -> "st"
            this.endsWith("2") -> "nd"
            this.endsWith("3") -> "rd"
            else -> "th"
        }
    }
}