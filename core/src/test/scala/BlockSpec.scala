package kr.ac.postech.paranode.core

import org.scalatest.flatspec.AnyFlatSpec

class BlockSpec extends AnyFlatSpec {
  implicit class ComparableBlock(block: Block) {
    def is(that: Block): Boolean = {
      block.records
        .zip(that.records)
        .forall(records => records._1 is records._2)
    }
  }

  "Block" should "be constructable from bytes" in {
    val bytes = LazyList[Byte](0, 1, 2, 3, 4, 5, 6, 7)
    val block = Block.fromBytes(bytes, keyLength = 1, valueLength = 3)

    val expectedBlock =
      new Block(
        LazyList[Record](
          new Record(new Key(Array(0)), Array(1, 2, 3)),
          new Record(new Key(Array(4)), Array(5, 6, 7))
        )
      )

    assert(block is expectedBlock)
  }

  it should "be serializable as chars" in {
    val block = new Block(
      LazyList(
        new Record(new Key(Array(0x0)), Array(0x1, 0x2, 0x3)),
        new Record(new Key(Array(0x4)), Array(0x5, 0x6, 0x7))
      )
    )

    assert(
      block.toChars sameElements Array[Char](0x0, 0x1, 0x2, 0x3, 0x4, 0x5, 0x6,
        0x7)
    )
  }
}
