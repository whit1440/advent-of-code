package twentytwenty.fourth

import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet
import twentytwenty.Solution

@Module
object FourthModule {

    data class Passport(
        val birthYear: Int? = null,
        val issueYear: Int? = null,
        val expirationYear: Int? = null,
        val height: String? = null,
        val hairColor: String? = null,
        val eyeColor: String? = null,
        val passportId: String? = null,
        val countryId: String? = null
    ) {
        fun validBirthYear() : Boolean = birthYear?.let {
            it in 1920..2002
        } ?: false

        fun validIssueYear() : Boolean = issueYear?.let {
            it in 2010..2020
        } ?: false

        fun validExpirationYear() : Boolean = expirationYear?.let {
            it in 2020..2030
        } ?: false

        fun validHeight() : Boolean = height?.let {
            (it.endsWith("in") && it.dropLast(2).toIntOrNull() in 59..76) ||
                    (it.endsWith("cm") && it.dropLast(2).toIntOrNull() in 150..193)
        } ?: false

        fun validHairColor() : Boolean = hairColor?.let {
            it.matches("#[a-f0-9]{6}".toRegex())
        } ?: false

        fun validEyeColor() : Boolean = eyeColor?.let {
            "amb blu brn gry grn hzl oth".split(" ").contains(it)
        } ?: false

        fun validPassportId() : Boolean = passportId?.let {
            it.matches("[0-9]{9}".toRegex())
        } ?: false

        fun hasAllFields() : Boolean {
            return birthYear != null &&
                    issueYear != null &&
                    expirationYear != null &&
                    height != null &&
                    hairColor != null &&
                    eyeColor != null &&
                    passportId != null
        }

        fun isValid() : Boolean {
            return validBirthYear() && validIssueYear() &&
                    validExpirationYear() && validEyeColor() &&
                    validEyeColor() && validHairColor() &&
                    validHeight() && validPassportId()
        }
    }

    fun parse(input: String) : List<Passport> {
        return input.split("\n\n").map {
            it.replace("\n", " ")
        }.map {
            it.split(" ").map {
                it.split(":").let {
                    Pair(it[0], it[1])
                }
            }.toMap()
        }.map {
            Passport(
                birthYear = it.get("byr")?.toInt(),
                issueYear = it.get("iyr")?.toInt(),
                expirationYear = it.get("eyr")?.toInt(),
                height = it.get("hgt"),
                hairColor = it.get("hcl"),
                eyeColor = it.get("ecl"),
                passportId = it.get("pid"),
                countryId = it.get("cid")
            )
        }
    }

    @Provides
    @IntoSet
    fun part1() : Solution = object : Solution {
        override val day: Int = 4
        override val part: Int = 1

        override fun solve(): Any {
            return parse(TestInput().input).filter { it.hasAllFields() }.size
        }
    }


    @Provides
    @IntoSet
    fun part2() : Solution = object : Solution {
        override val day: Int = 4
        override val part: Int = 2

        override fun solve(): Any {
            return parse(TestInput().input).filter { it.isValid() }.size
        }
    }
}