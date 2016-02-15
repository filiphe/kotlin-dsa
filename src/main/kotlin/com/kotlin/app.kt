/**
* Created by Filip Hesslund on 2016-02-08.
*/

package dsa;

import java.math.BigInteger

fun main(args: Array<String>) {

  var command: String = String()
  var input: Array<String> = emptyArray()
  var stringArr: Array<String> = emptyArray()
  var params: Array<BigInteger> = emptyArray()

  while (true) {
    var inp = readLine()
    if (inp != null) input = input.plus(inp.toString())
    else break
  }

  for (line in input) {
    if (line == "genkey" || line == "verify" || line == "sign") command = line
    else stringArr = stringArr.plus(line.drop(2))
    //else params = params.plus(BigInteger(line.drop(2)))
  }

  val max: Int = 3
  var i: Int = 0

  while (i < max) {
    params = params.plus(BigInteger(stringArr[i]))
    i++
  }

  val paramTriple: ParameterTriple = ParameterTriple(params[0], params[1], params[2])

  if (!paramTriple.isValid()) {
    println("invalid_group")
    System.exit(1)
  } else {
    println("valid_group")
    if (command == "genkey") {
      val i: Int = stringArr[3].toInt()
      for (j in 0..i) {
        val kP: KeyPair = genKey(paramTriple)
        println("x=" + kP.x)
        println("y=" + kP.y)
      }
    }
    else if (command == "sign") {
      val kP: KeyPair = KeyPair(BigInteger(stringArr[3]), BigInteger(stringArr[4]))
      val size: Int = stringArr.size
      for (i in 5..size - 1) {
        val s: Signature = sign(paramTriple, kP, stringArr[i])
        println("r=" + s.r)
        println("s=" + s.s)
      }
    }
    else if (command == "verify") {
      val size: Int = stringArr.size
      val y: BigInteger = BigInteger(stringArr[3])
      for (i in 4..size - 1 step 3) {
        val m: String = stringArr[i]
        val s: Signature = Signature(BigInteger(stringArr[i+1]), BigInteger(stringArr[i+2]))

        val result: Boolean = s.isValid(paramTriple, y, m)

        if (result)
        println("signature_valid")
        else
        println("signature_invalid")
      }
    }
    else {
      println("unknown_command")
      System.exit(1)
    }
  }
}
