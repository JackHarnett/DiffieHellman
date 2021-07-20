import PrimeUtils.isPrime
import java.util.*

object PrimeCache {

    /**
     * The largest number for which all prime numbers smaller than has been calculated
     */
    private var primesBelow = 2L

    /**
     * Ordered set of all pre-calculated prime numbers
     */
    val primes = LinkedList<Long>(arrayListOf(2))

    /**
     * Sequence that generates prime numbers on-demand by sieving and caches new found primes
     */
    fun primeGenerator(min: Long = 1L) = sequence {
        generateSequence(min) { it + 1L }
            .filter { it.isPrime() }
            .forEach {
                if (primes.last < it) primes.add(it)  // if a larger prime is found, add it to the set
                yield(it)
            }
    }

    /**
     * Get all prime numbers lower than the number specified, either by returning
     * pre-calculated primes or calculating on-demand
     */
    fun getPrimesBelow(num: Long): List<Long> {

        /**
         * If all required primes have already been found, return from the pre-calculated set
         */
        if (num < primesBelow) {
            return primes.filter { it < num }.toList()
        }

        primesBelow = num
        return primes + primeGenerator(primes.last() + 1).takeWhile { it < num }.toSortedSet()
    }

    /**
     * Return the first n primes
     */
    fun firstPrimes(n: Int): List<Long> {
        if (primes.size < n) {
            // Generate only the primes needed to have n in total. primeGenerator caches results automatically
            primeGenerator(primes.last() + 1).take(n - primes.size).toList()
            if (primes.last() > primesBelow) primesBelow = primes.last()
        }

        return primes.take(n).toList()
    }

    /**
     * Generates a sequence of prime numbers that match the predicate
     */
    fun takePrimesWhile(predicate: (Long) -> Boolean) = sequence {
        yieldAll(primes.filter { predicate(it) })
        yieldAll(primeGenerator(primes.last + 1).takeWhile(predicate))
    }

}