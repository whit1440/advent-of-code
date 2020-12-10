package twentytwenty

import dagger.Component
import dagger.Module
import dagger.Provides
import twentytwenty.first.FirstModule
import twentytwenty.second.SecondModule
import twentytwenty.sixth.SixthModule
import twentytwenty.third.ThirdModule
import javax.inject.Inject

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
    fun run() {
        val compare = compareBy<Solution> { it.day }.thenBy { it.part }
        solutions.sortedWith(compare).forEach {
            println("Dec ${it.day.toPosition()} - part ${it.part} - ${it.solve()}")
        }
    }
}

// TODO - modules for each year
@Module(includes = [
    FirstModule::class,
    SecondModule::class,
    ThirdModule::class,
    SixthModule::class
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