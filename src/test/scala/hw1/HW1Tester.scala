package hw1

import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec

class HW1Tester extends AnyFlatSpec with ChiselScalatestTester {
  behavior of "RiscvITypeDecoder"
  it should "correctly decode instructions" in {
    test(new RiscvITypeDecoder) { c =>
			???
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
			dut.io.real0.poke(1)
      dut.io.real1.poke(1)
      dut.io.imag0.poke(1)
      dut.io.imag1.poke(1)
      dut.io.realOut.expect(2)
    }
  }
  it should "correctly calculate realOut onlyAdd=false" in {
    test(new ComplexALU(onlyAdder = false)) { dut =>
			dut.io.real0.poke(1)
      dut.io.real1.poke(1)
      dut.io.imag0.poke(1)
      dut.io.imag1.poke(1)
      dut.io.doAdd.poke(1)
      dut.io.realOut.expect(2)

      dut.io.doAdd.poke(0)
      dut.io.realOut.expect(0)      
    }
  }
  it should "correctly calculate imagOut onlyAdd=true" in {
    test(new ComplexALU(onlyAdder = true)) { dut =>
			dut.io.real0.poke(1)
      dut.io.real1.poke(1)
      dut.io.imag0.poke(1)
      dut.io.imag1.poke(1)
      dut.io.imagOut.expect(2)
    }
  }
  it should "correctly calculate imagOut onlyAdd=false" in {
    test(new ComplexALU(onlyAdder = false)) { dut =>
			dut.io.real0.poke(1)
      dut.io.real1.poke(1)
      dut.io.imag0.poke(1)
      dut.io.imag1.poke(1)
      dut.io.doAdd.poke(1)
      dut.io.imagOut.expect(2)

      dut.io.doAdd.poke(0)
      dut.io.imagOut.expect(0)
    }
  }
}
