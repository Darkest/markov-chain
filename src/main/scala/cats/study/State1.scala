package cats.study

import cats.data.State
import State._
import cats.implicits.catsSyntaxApplicativeId

object State1 extends App {

  val program: State[Int, (Int, Int, Int)] = get[Int]
    .flatMap(a =>
      set[Int](a + 1)
        .flatMap(_ =>
          get[Int]
            .flatMap(b =>
              modify[Int](_ + 1)
                .flatMap(_ =>
                  inspect[Int, Int](_ * 1000)
                    .map(c => (a, b, c))
                )
            )
        )
    )
  // program: State[Int, (Int, Int, Int)] = cats.data. IndexedStateT@42c9d44a
  val (state, result) = program.run(1).value
  // state: Int = 3
  // result: (Int, Int, Int) = (1, 2, 3000)


  type CalcState[A] = State[List[Int], A]

  def operand(input: String): CalcState[Int] =
    State[List[Int], Int] { stack =>
      val n = input.toInt
      (n :: stack, n)
    }

  def operator(f: (Int, Int) => Int): CalcState[Int] =
    State[List[Int], Int] {
      case n2 :: n1 :: tail =>
        val fa = f(n1, n2)
        (fa :: tail, fa)
    }

  def evalOne(sym: String): CalcState[Int] =
    sym match {
      case "+" => operator(_ + _)
      case "-" => operator(_ - _)
      case "*" => operator(_ * _)
      case "/" => operator(_ / _)
      case s => operand(s)
    }

  def evalAll(input: List[String]): CalcState[Int] =
    input.foldLeft(0.pure[CalcState])((acc, el) =>
      acc.flatMap(_ => evalOne(el)))

  def evalString(input: String) = evalAll(input.split(" ").toList)

  val test1 = evalString("1 2 + 5 *")
  println(test1.run(Nil).value)
  }
