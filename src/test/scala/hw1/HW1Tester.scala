package hw1

import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec

class HW1Tester extends AnyFlatSpec with ChiselScalatestTester {
  behavior of "RiscvITypeDecoder"
  it should "correctly decode instructions" in {
    test(new RiscvITypeDecoder) { c =>
			c.io.instWord.poke("b10110011101100111011001110110011".U)
    }
  }

  // See src/test/scala/hw1/MajorityCircuitTester.scala for Problem2

  behavior of "PolyEval"
  it should "correctly calculate out" in {
		val c0 = 1
    test(new PolyEval(c0, c0, c0)) { dut =>
			dut.io.x.poke(1.U)
      dut.io.enable.poke(true.B)
      dut.io.out.expect(3)

      dut.io.x.poke(2.U)
      dut.io.enable.poke(true.B)
      dut.io.out.expect(7)

      dut.io.enable.poke(false.B)
      dut.io.out.expect(0)
    }
  }

  behavior of "ComplexALU"
  it should "correctly calculate realOut onlyAdd=true" in {
    test(new ComplexALU(onlyAdder=true)) { dut =>
			dut.io.real0.poke(43)
      dut.io.real1.poke(32)
      dut.io.realOut.expect(75)
    }
  }
  it should "correctly calculate realOut onlyAdd=false" in {
    test(new ComplexALU(onlyAdder = false)) { dut =>
      dut.io.doAdd.poke(1)
			dut.io.real0.poke(63)
      dut.io.real1.poke(53)
      dut.io.realOut.expect(116)

      dut.io.doAdd.poke(0)
      dut.io.realOut.expect(10)      
    }
  }
  it should "correctly calculate imagOut onlyAdd=true" in {
    test(new ComplexALU(onlyAdder = true)) { dut =>
      dut.io.doAdd.poke(1)
			dut.io.real0.poke(63)
      dut.io.real1.poke(53)
      dut.io.realOut.expect(116)
      dut.io.imag0.poke(-34)
      dut.io.imag1.poke(12)
      dut.io.imagOut.expect(-22)
    }
  }
  it should "correctly calculate imagOut onlyAdd=false" in {
    test(new ComplexALU(onlyAdder = false)) { dut =>
      dut.io.imag0.poke(4)
      dut.io.imag1.poke(56)
      dut.io.doAdd.poke(1)
      dut.io.imagOut.expect(60)

      dut.io.doAdd.poke(0)
      dut.io.imagOut.expect(-52)
    }
  }
}
