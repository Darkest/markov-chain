import scala.::
import scala.annotation.tailrec
import scala.concurrent.duration.DurationInt
import scala.concurrent.{Await, Future}
import scala.util.{Failure, Success}

val seq = Seq(1, 2, 2, 3, 4, 3, 3, 3) // Seq((1,1), (2,2),)
//


//fold(seq.tail, seq.head, Seq((seq.head, 1)) )

@tailrec
def fold(seq: Seq[Int], acc: Seq[(Int, Int)]): Seq[(Int, Int)] = {
  seq match {
    case Nil => acc
    case _ =>
      val head = seq.head
      val count = seq.takeWhile(_ == head).length
      fold(seq.drop(count), acc :+ (head, count))
  }
}

val folded = fold(seq, Seq())

def unfold(seq: Seq[(Int, Int)], acc: Seq[Int] = Seq()): Seq[Int] = {
  seq match {

    case Nil => acc
    case (i, n) :: t =>
      unfold(t, acc ++ Seq.fill(n)(i))
  }
}

import scala.concurrent.ExecutionContext.Implicits.global

unfold(folded)

def func(seqFut: Seq[Future[String]]): Future[(Seq[String], Seq[Throwable])] = {
  seqFut.foldLeft(Future.successful((Seq[String](), Seq[Throwable]())))
  { (acc, fut) =>
    fut.transformWith {
      case Success(str) =>
        acc.map{case (suc, fail) => (str +: suc, fail)}
      case Failure(exc) => acc.map{case (suc, fail) => (suc, exc +: fail)}
    }
  }
}

val seq1 = Seq(1,2,3,0,4,5,6,0)
val futures:Seq[Future[String]] = seq1.map(n => Future((10/n).toString))

func(futures)


type Transformation[T] = T => Future[T]

val t1: Transformation[Int] = t => Future.successful(t + t)
val t2: Transformation[Int] = _ => Future.failed(new NoSuchElementException)
val t3: Transformation[Int] = t =>
  if (t > 2) Future.successful(t * t)
  else Future.failed(new NoSuchElementException)

def transformationChain[T](xs: Seq[Transformation[T]]): Transformation[T] = {
  xs.foldLeft((t:T) => Future.failed(new Exception(""))){ fut =>
    (x: T) => fut(x).transformWith{
      case Success(v) => fut(v)
      case Failure(exc) =>

    }
  }
}

val tc = transformationChain(Seq(t1, t2, t2, t3))
val tc2 = transformationChain(Seq(t2, t2, t2))
val tc3 = transformationChain(Seq(t2, t3, t1))

println(Await.result(tc(2), 5.seconds))  // 16
println(Await.result(tc2(2), 5.seconds)) // throw NoSuchElementException
println(Await.result(tc3(2), 5.seconds)) // 4
