package hw1

import chisel3._
import chisel3.util._

// Note ??? will compile but not work at runtime.

/**
 * io.instWord: 32b UInt Input
 * io.opcode: 7b UInt Output
 * io.funct3: 3b UInt Output
 * io.rs1: 5b UInt Output
 * io.rd: 5b UInt Output
 * io.immSignExtended: 32b UInt Output
 */
class RiscvITypeDecoder extends Module {
	val io =  IO(new Bundle {
    val instWord = Input(UInt(32.W))
    val opcode = Output(UInt(7.W))
    val funct3 = Output(UInt(3.W))
    val rs1 = Output(UInt(5.W))
    val rd = Output(UInt(5.W))
    val immSignExtended = Output(UInt(32.W))
  })
  
  io.opcode := io.instWord(6,0)
  io.funct3 := io.instWord(14,12)
  io.rs1 := io.instWord(19,15)
  io.rd := io.instWord(11,7)
  val signBit = io.instWord(31)
  val extension = Fill(20, signBit)
  io.immSignExtended := Cat(extension,io.instWord(31,20)) 
  
}


class MajorityCircuit extends Module {
  val io = IO(new Bundle {
    val a = Input(Bool())
    val b = Input(Bool())
    val c = Input(Bool())
    val out = Output(Bool())
  })
  io.out := (io.a & io.b) | (io.a & io.c) | (io.b & io.c)
}

/**
 * c0: 8-bit Int
 * c1: 8-bit Int
 * c2: 8-bit Int
 * io.x: 8-bit UInt Input
 * io.out: ???-bit UInt Output
 */
class PolyEval(c0: Int, c1: Int, c2: Int) extends Module {
  require(c0 >= 0 && c0 < 256)
  require(c1 >= 0 && c1 < 256)
  require(c2 >= 0 && c2 < 256)
	val io = IO(new Bundle {
    val enable = Input(Bool())
    val x = Input(UInt(8.W))
    val out = Output(UInt())
  })
  when (io.enable) {
    io.out := c0.U + c1.U*io.x + c2.U*io.x*io.x
  } .otherwise {
    io.out := 0.U
  }
}


/**
 * onlyAdd: Boolean
 * io.useAdd: Bool Input
 * io.real0: 7-bit SInt Input
 * io.imag0: 7-bit SInt Input
 * io.real1: 7-bit SInt Input
 * io.imag1: 7-bit SInt Input
 * io.realOut: SInt() Output
 * io.imagOut: SInt() Output
 */
class ComplexALU(onlyAdder: Boolean) extends Module {
  val io = IO(new Bundle {
    val doAdd = Input(Bool())
    val real0 = Input(SInt(7.W))
    val imag0 = Input(SInt(7.W))
    val real1 = Input(SInt(7.W))
    val imag1 = Input(SInt(7.W))
    val realOut = Output(SInt(8.W))
    val imagOut = Output(SInt(8.W))
  })
  if (onlyAdder) {
    // dont generate subtraction hardware
    io.realOut := io.real1 +& io.real0
    io.imagOut := io.imag1 +& io.imag0
    printf("real sum is %d, imaginary sum is %d", io.real0 + io.real1, io.imag0 + io.imag1)
  } else {
    when (io.doAdd) {
      // add
      io.realOut := io.real1 +& io.real0
      io.imagOut := io.imag1 +& io.imag0
    } .otherwise {
      // subtract
      io.realOut := io.real0 -& io.real1
      io.imagOut := io.imag0 -& io.imag1
      // NOTE: use & after arithmetic operator so it doesnt truncate
    }
  }
}
