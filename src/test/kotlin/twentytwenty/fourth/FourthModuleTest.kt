package twentytwenty.fourth

import org.junit.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class FourthModuleTest {
    @Test
    fun testValidHeight() {
        val pp = FourthModule.Passport(
            height = "160cm"
        )

        assertTrue(pp.validHeight())

        assertFalse(pp.copy(
            height = "160"
        ).validHeight())

        assertFalse(pp.copy(
            height = "in"
        ).validHeight())

        assertFalse(pp.copy(
            height = "149cm"
        ).validHeight())

        assertFalse(pp.copy(
            height = "194cm"
        ).validHeight())

        assertFalse(pp.copy(
            height = "58in"
        ).validHeight())

        assertFalse(pp.copy(
            height = "77in"
        ).validHeight())

        assertTrue(pp.copy(
            height = "62in"
        ).validHeight())
    }

    @Test
    fun testHairColor() {
        val pp = FourthModule.Passport(
            hairColor = "#cc0000"
        )

        assertTrue(pp.validHairColor())

        assertFalse(pp.copy(
            hairColor = "#00ccf"
        ).validHairColor())

        assertFalse(pp.copy(
            hairColor = "00ccff"
        ).validHairColor())

        assertFalse(pp.copy(
            hairColor = "#00ccfg"
        ).validHairColor())
    }

    @Test
    fun testEyeColor() {
        val pp = FourthModule.Passport(
            eyeColor = "blu"
        )

        assertTrue(pp.validEyeColor())

        assertTrue(pp.copy(
            eyeColor = "grn"
        ).validEyeColor())

        assertFalse(pp.copy(
            eyeColor = "qqq"
        ).validEyeColor())

        assertFalse(pp.copy(
            eyeColor = "blue"
        ).validEyeColor())
    }

    @Test
    fun testPassportId() {
        val pp = FourthModule.Passport(
            passportId = "839472149"
        )

        assertTrue(pp.validPassportId())

        assertFalse(pp.copy(
            passportId = "23456789"
        ).validPassportId())

        assertFalse(pp.copy(
            passportId = "2345g6789"
        ).validPassportId())

        assertFalse(pp.copy(
            passportId = "2345767895"
        ).validPassportId())
    }
}