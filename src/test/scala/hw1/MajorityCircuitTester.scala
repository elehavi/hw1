package hw1

import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec

class MajorityCircuitTester extends AnyFlatSpec with ChiselScalatestTester {
  behavior of "MajorityCircuit"
  it should "correctly pass tests on all 8 possible inputs" in {
    test(new MajorityCircuit) { dut =>
      for(a <- Seq(true, false)) {
        for(b <- Seq(true, false)) {
          for(c <- Seq(true, false)){
            dut.io.a.poke(a.B)
            dut.io.b.poke(b.B)
            dut.io.c.poke(c.B)
            dut.io.out.expect(((a & b) | (a & c) | (b & c)).B)
          }
        }
      }
    }
  }
}
