package twentytwenty.seventh

import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet
import twentytwenty.Solution

@Module
object SeventhModule {

    private val childToParentMapping : MutableMap<String, List<Pair<Int, String>>> =
        mutableMapOf()

    private val parentToChildMapping : MutableMap<String, List<Pair<Int, String>>> =
        mutableMapOf()

    fun String.canBeHeldIn(parent: String, quantity: Int) {
        childToParentMapping[this] = childToParentMapping.getOrDefault(this, listOf())
            .plus(quantity to parent)
    }

    fun String.mustHold(child: List<Pair<Int, String>>) {
        parentToChildMapping[this] = parentToChildMapping.getOrDefault(this, listOf())
            .plus(child)
    }

    data class BagRelationship(
        val parent: String,
        val children: List<Pair<Int, String>>
    )

    fun parse(input: String) : List<BagRelationship> {
        return input.split(".\n").map {
            it.split(" bags contain ").let {
                Pair(it[0], it[1])
            }
        }.map {
            Pair(it.first, it.second.replace(" bags?".toRegex(), "").split(", "))
        }.map {
            Pair(
                it.first,
                if(it.second.first() == "no other bags") listOf()
                else it.second.map {
                    it.take(1).toIntOrNull()?.let { qty ->
                        Pair(qty, it.takeLast(it.length - 2))
                    }
                }.filterNotNull()
            )
        }.map {
            BagRelationship(it.first, it.second)
        }
    }

    tailrec fun findAllParents(bags: List<String>, acc: List<String>) : List<String> {
        return if (bags.isEmpty()) acc else findAllParents(
            bags.map {
                childToParentMapping[it]?.map {
                    it.second
                }
            }.filterNotNull().flatten(),
            (acc + bags).distinct()
        )
    }

    tailrec fun findAllChildren(bags: List<Pair<Int, String>>, acc: List<Pair<Int, String>>) : List<Pair<Int, String>> {
        return if (bags.isEmpty()) acc else findAllChildren(
            bags.map { (qty, name) ->
                parentToChildMapping[name]?.map {
                    Pair(it.first * qty, it.second)
                }
            }.filterNotNull().flatten(),
            (acc + bags).distinct()
        )
    }

    @Provides
    @IntoSet
    fun part1() : Solution = object : Solution {
        override val day: Int = 7
        override val part: Int = 1

        override fun solve(): Any {
            parse(TestInput().input).forEach {
                it.children.forEach { (qty, bag) ->
                    bag.canBeHeldIn(it.parent, qty)
                }
            }
            return findAllParents(listOf("shiny gold"), listOf()).minus("shiny gold").size
        }
    }

    @Provides
    @IntoSet
    fun part2() : Solution = object : Solution {
        override val day: Int = 7
        override val part: Int = 2

        override fun solve(): Any {
            parse(TestInput().input).forEach {
                it.parent.mustHold(it.children)
            }
            return findAllChildren(listOf(Pair(1, "shiny gold")), listOf()).map { it.first }.sum() - 1
        }
    }
}