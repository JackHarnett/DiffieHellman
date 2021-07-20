import PrimeUtils.isPrime
import org.junit.Test
import kotlin.system.measureTimeMillis
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class PrimeCacheTest {

    val knownPrimes = listOf<Long>(
        2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 47, 53, 59, 67, 71, 83, 89, 101, 107, 109, 113, 127, 131,
        137, 139, 149, 157, 167, 179, 181, 191, 197, 199, 211, 227, 233, 239, 251, 257, 263, 269, 281, 293, 307,
        91193, 93719, 93911, 99371, 193939, 199933, 319993, 331999, 391939, 393919, 919393, 933199, 939193, 939391,
    )

    val notPrimes = listOf<Long>(12, 90, 100, 9, 4, 100000, 222, 268, 21)

    @Test
    fun isPrime() {


        /**
         * Verify that prime numbers are correctly identified - no false negatives
         */
        knownPrimes.forEach {
            assertTrue("Failed to determine that $it is prime.") { it.isPrime() }
        }

        /**
         * Verify that non-prime numbers are not misidentified as prime - no false positives
         */
        notPrimes.forEach {
            assertFalse("Incorrectly determined $it as prime.") { it.isPrime() }
        }
    }

    @Test
    fun getPrimes() {

        /**
         * Verify that the prime number sequence generates correctly the first 10 primes
         */
        assertEquals(
            listOf<Long>(2, 3, 5, 7, 11, 13, 17, 19, 23, 29),
            PrimeCache.firstPrimes(10),
            "First 10 generated prime numbers are not correct"
        )
    }

    @Test
    fun takePrimesWhile() {
        assertEquals(
            PrimeCache.firstPrimes(100).filter { it < 100 },
            PrimeCache.takePrimesWhile { it < 100 }.toList(),
            "First 100 primes were nor accurately generated."
        )
    }


    /**
     * Test the speed of cached vs uncached prime finding implementations
     */
    @Test
    fun PrimeCacheSpeed() {

        val toFind = 10_000

        val cachedExtra = measureTimeMillis {
            PrimeCache.firstPrimes(toFind + 1000)
        }

        val uncachedExtra = measureTimeMillis {
            PrimeCache.primeGenerator().take(toFind + 100).toList()
        }

        //println("Cached: $cached uncached $uncached")
        //println("Cached extra : $cachedExtra uncached extra $uncachedExtra")

        //assertTrue("Took longer to generate cached primes") { cached < uncached }
        assertTrue("Took longer to generate extra cached primes") { cachedExtra < uncachedExtra }

        val uncachedTake = measureTimeMillis {
            PrimeCache.primeGenerator().takeWhile { it < 100_000 }.toList()
        }

        val cachedTake = measureTimeMillis {
            PrimeCache.takePrimesWhile { it < 100_000 }
        }

        //println("Uncached take while $uncachedTake - cached take while $cachedTake")
        assertTrue("Took longer to perform takeWhile on cached primes") { cachedTake < uncachedTake }

    }

}