package playground

import Chisel._
import Chisel.iotesters.{PeekPokeTester, Driver}


class LFSR16 extends Module {
  val io = new Bundle {
    val inc = Bool(INPUT)
    val out = UInt(OUTPUT, 16)
  }
  val res = Reg(init = UInt(1, 16))
  when (io.inc) { 
    val nxt_res = Cat(res(0)^res(2)^res(3)^res(5), res(15,1)) 
    res := nxt_res
  }
  io.out := res
}

class LFSR16Tests(c: LFSR16) extends PeekPokeTester(c) {
  var res = 1
  for (t <- 0 until 16) {
    val inc = rnd.nextInt(2)
    poke(c.io.inc, inc)
    step(1)
    if (inc == 1) {
      val bit = ((res >> 0) ^ (res >> 2) ^ (res >> 3) ^ (res >> 5) ) & 1;
      res = (res >> 1) | (bit << 15);
    }
    expect(c.io.out, res)
  }
}

object LFSR16Main {
  def main(args: Array[String]): Unit = {
    if (args.size > 0) {
      if (!Driver(() => new LFSR16(), "firrtl")(c => new LFSR16Tests(c))) System.exit(1)
    } else {
      if (!Driver.run(() => new LFSR16(), "./test_run_dir/playground.LFSR16/LFSR16")(c => new LFSR16Tests(c))) System.exit(1)
    }
  }
}
