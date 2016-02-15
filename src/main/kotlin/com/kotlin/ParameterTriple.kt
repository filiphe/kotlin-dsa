package dsa;

import java.math.BigInteger


val certainty: Int = 100
val L: Int = 1024
val N: Int = 160

class ParameterTriple(p: BigInteger, q: BigInteger, g: BigInteger) {
    val p: BigInteger = p
    val q: BigInteger = q
    val g: BigInteger = g

    fun isValid(): Boolean {
        if ((p.isProbablePrime(certainty)) && (q.isProbablePrime(certainty))) {
            if (p.bitLength() == L && q.bitLength() == N) {
                if (p.subtract(BigInteger.ONE).mod(q) == BigInteger.ZERO) {
                    if (g.modPow(q, p).equals(BigInteger.ONE)){
                       if (g.compareTo(BigInteger.ONE) == 1) {
                           return true
                       }
                    }
                }
            }
        }
        return false
    }
}
