import cats.data.State
import cats.data.State.{get, set}

type CalcState[A] = State[List[Int], A]
def evalOne(sym: String): CalcState[Int] =
  sym match {
    case "+" =>
      for {
        stack <- get[List[Int]]
        a1 = stack.head
        a2 = stack.tail.head
        _ <- set[List[Int]]((a1 + a2) :: stack.tail.tail)
      } yield a1 + a2
    case s =>
      for {
        stack <- get[List[Int]]
        n = s.toIntOption.get
        _ <- set[List[Int]](n :: stack)
      } yield n
  }

evalOne("42").runA(Nil).value

val program = for {
  _ <- evalOne("1")
  _ <- evalOne("2")
  ans <- evalOne("+")
} yield ans

program.runA(Nil).value