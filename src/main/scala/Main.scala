import cmd.RunParams
import input.getWords

object Main extends App {
  def program[F[_]](config: RunParams) = {
    for {
      inputStream <- input.inputStream(config.sourceFile)//.use(getWords(_))
      words = getWords(inputStream)
    } yield words
  }

}
