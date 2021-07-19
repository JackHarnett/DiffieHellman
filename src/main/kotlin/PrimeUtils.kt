/**
 * Utility methods for handling prime numbers
 */
object PrimeUtils {

    /**
     * Check if a number is prime by verifying that no lower values divide it
     */
    fun Long.isPrime() = (2 until this).none { this % it == 0L }

    /**
     * Sequence that generates prime numbers on-demand by sieving
     */
    val primes = sequence {
        generateSequence(1L) { it + 1L }
            .filter { it > 1 && it.isPrime() }
            .forEach { yield(it) }
    }

    /**
     * Finds the unique prime number factorisation for the provided number
     */
    fun primeFactorDecomposition(num : Long): MutableList<Long> {
        val factors = ArrayList<Long>()
        var total = num

        primes.takeWhile { total > 1L }
            .filter { total % it == 0L }
            .forEach {
                do {
                    factors.add(it)
                    total /= it
                } while(total % it == 0L)
            }

        return factors
    }

    /**
     * Determines if two numbers are relatively prime, that their greatest common divisor is 1.
     */
    fun relativePrime(first : Long, second : Long) =
        (primeFactorDecomposition(first).toSet() intersect primeFactorDecomposition(second)).isEmpty()

}