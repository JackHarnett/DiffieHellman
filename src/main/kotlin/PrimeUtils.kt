/**
 * Utility methods for handling prime numbers
 */
object PrimeUtils {

    /**
     * Check if a number is prime by verifying that no lower values divide it
     */
    fun Number.isPrime() = this.toLong() > 1L && (2L until this.toLong()).none { this.toLong() % it == 0L }

    /**
     * Sequence that generates prime numbers on-demand by sieving
     */
    val primes = sequence {
        generateSequence(1L) { it + 1L }
            .filter { it.isPrime() }
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

    fun totient(num : Long): Long {

        // The simple case when n is prime
        if(num.isPrime()) return num-1

        // Get the unique prime factors as doubles
        val factors = primeFactorDecomposition(num).toSet().map { it.toDouble() }

        // Euler's totient formula, sieving out smaller numbers with common prime factors
        var totient = num.toDouble()
        factors.forEach { totient *= (1.0 - 1.0/it) }

        return totient.toLong()
    }

}