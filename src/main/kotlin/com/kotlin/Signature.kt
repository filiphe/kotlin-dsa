package dsa;

import java.math.BigInteger
import java.security.SecureRandom


class Signature(r: BigInteger, s: BigInteger) {
    val r: BigInteger = r
    val s: BigInteger = s

    fun isValid(pT: ParameterTriple, y: BigInteger, m: String): Boolean {
        val p: BigInteger = pT.p
        val q: BigInteger = pT.q
        val g: BigInteger = pT.g

        if (r.compareTo(q) == -1 && r.compareTo(BigInteger.ZERO) == 1) {
            if (s.compareTo(q) == -1 && s.compareTo(BigInteger.ZERO) == 1) {
                val z: BigInteger = BigInteger(m, 16)

                val w: BigInteger = s.modInverse(q)
                val u1: BigInteger = z.multiply(w).mod(q)
                val u2: BigInteger = r.multiply(w).mod(q)

                val v: BigInteger = (g.modPow(u1, p)).multiply(y.modPow(u2, p)).mod(p).mod(q)

                return r.equals(v)
            }
        }
        return false
    }
}

fun sign(pT: ParameterTriple, kP: KeyPair, m: String): Signature {
    val p: BigInteger = pT.p
    val q: BigInteger = pT.q
    val g: BigInteger = pT.g
    val x: BigInteger = kP.x

    var r: BigInteger
    var s: BigInteger
    var k: BigInteger

    val z = BigInteger(m, 16)

    do {
        k = BigInteger(q.bitLength(), secRand)

        var k_inv = k.modInverse(q)

        r = g.modPow(k, p)
        r = r.mod(q)

        s = k_inv.multiply(z.add(x.multiply(r)))
        s = s.mod(q)
    } while (r.equals(0) || s.equals(0))

    return Signature(r, s)
}
