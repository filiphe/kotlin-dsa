package dsa;

import java.math.BigInteger
import java.security.SecureRandom

val secRand: SecureRandom = SecureRandom()

class KeyPair(x: BigInteger, y: BigInteger) {
    val x: BigInteger = x
    val y: BigInteger = y
}

fun genKey(pT: ParameterTriple): KeyPair {
   var x: BigInteger
   val p: BigInteger = pT.p
   val q: BigInteger = pT.q
   val g: BigInteger = pT.g

   do {
       x = BigInteger(q.bitLength(), secRand)
   } while (x.compareTo(q.subtract(BigInteger.ONE)) <= 0 && x.compareTo(BigInteger.ONE) > 0)

   val y: BigInteger = g.modPow(x, p)

   return KeyPair(x, y)
}
