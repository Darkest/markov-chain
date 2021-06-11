import cats.effect.{IO, Resource}

import java.io.{BufferedReader, File, FileInputStream, FileOutputStream, InputStream, InputStreamReader}
import scala.io.Source

package object input {

  def inputStream(f: File): Resource[IO, FileInputStream] = Resource.make{
    IO.blocking(new FileInputStream(f))
  } { inStream =>
    IO.blocking(inStream.close()).handleErrorWith(exc => IO.println(exc))
  }

  def outputStream(f: File): Resource[IO, FileOutputStream] = Resource.make{
    IO.blocking(new FileOutputStream(f))
  } { outStream =>
    IO.blocking(outStream.close()).handleErrorWith(exc => IO.println((exc)))
  }

//  def stream[A <: AutoCloseable](f: File): Resource[IO, A] = Resource.make{
//    IO.blocking(new A(f))
//  } { stream =>
//      IO.blocking(stream.close()).handleErrorWith(exc => IO.println(exc))
//  }

  def getWords(stream: InputStream): IO[List[String]] = {
    IO(Source.fromInputStream(stream).getLines().toList)
  }
}
