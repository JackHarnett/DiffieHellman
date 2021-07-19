import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class PrimeUtilsTest {

    @Test
    fun isPrime() {
        val knownPrimes = listOf<Long>(
            2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 47, 53, 59, 67, 71, 83, 89, 101, 107, 109, 113, 127, 131,
            137, 139, 149, 157, 167, 179, 181, 191, 197, 199, 211, 227, 233, 239, 251, 257, 263, 269, 281, 293, 307,
            91193, 93719, 93911, 99371, 193939, 199933, 319993, 331999, 391939, 393919, 919393, 933199, 939193, 939391,
        )

        val notPrimes = listOf<Long>(12, 90, 100, 9, 4, 100000, 222, 268, 21)


        /**
         * Verify that prime numbers are correctly identified - no false negatives
         */
        knownPrimes.forEach {
            assertTrue("Failed to determine that $it is prime.") { PrimeUtils.isPrime(it) }
        }

        /**
         * Verify that non-prime numbers are not misidentified as prime - no false positives
         */
        notPrimes.forEach {
            assertFalse("Incorrectly determined $it as prime.") { PrimeUtils.isPrime(it) }
        }
    }

    @Test
    fun getPrimes() {

        /**
         * Verify that the prime number sequence generates correctly the first 10 primes
         */
        assertEquals(
            listOf<Long>(2, 3, 5, 7, 11, 13, 17, 19, 23, 29),
            PrimeUtils.primes.take(10).toList(),
            "First 10 generated prime numbers are not correct"
        )
    }

    @Test
    fun primeFactorDecomposition() {

        /**
         * Verify the prime factors of 10 as 2 and 5
         */
        assertEquals(
            listOf<Long>(2, 5),
            PrimeUtils.primeFactorDecomposition(10),
            "Incorrect factorisation for 10."
        )

        /**
         * Verify the prime factors of 100 as 2, 2, 5 and 5
         */
        assertEquals(
            listOf<Long>(2, 2, 5, 5),
            PrimeUtils.primeFactorDecomposition(100),
            "Incorrect factorisation for 100."
        )

        /**
         * Verify the prime factors of 900 as 2, 2, 3, 3, 5 and 5
         */
        assertEquals(
            listOf<Long>(2, 2, 3, 3, 5, 5),
            PrimeUtils.primeFactorDecomposition(900),
            "Incorrect factorisation for 900."
        )

        /**
         * Verify the prime factors of 22 as 2 and 11
         */
        assertEquals(
            listOf<Long>(2, 11),
            PrimeUtils.primeFactorDecomposition(22),
            "Incorrect factorisation for 22."
        )
    }

    @Test
    fun relativePrime() {
        val areRelPrime = mapOf(4 to 9, 4 to 5, 9 to 25)
        val notRelPrime = mapOf(9 to 15, 3 to 9, 2 to 4, 16 to 8)

        areRelPrime.forEach {
            assertTrue("Failed to determined $it as coprime.") { PrimeUtils.relativePrime(it.key.toLong(), it.value.toLong()) }
        }

        notRelPrime.forEach {
            assertFalse("Incorrectly determined $it to be coprime.") { PrimeUtils.relativePrime(it.key.toLong(), it.value.toLong()) }
        }
    }
}