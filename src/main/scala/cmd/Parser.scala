package cmd

import scopt.{DefaultOEffectSetup, OEffect, OParser, OParserBuilder}

import java.io.File
import java.nio.file.Files

class Parser {

  val builder: OParserBuilder[RunParams] = OParser.builder[RunParams]
  val parser: OParser[_, RunParams] = {
    import builder._
    OParser.sequence(
      programName("mark-gen"),
      head("mark-gen", "0.1"),
      opt[String]('f', "from")
        .action((from, pars) => pars.copy(sourceFile = new File(from))),
      opt[Int]('w', "words")
        .action((words, pars) => pars.copy(wordToGenerate = words))
    )
  }

  //for educational purposes, not used
  def parse(args: Seq[String]): Either[List[OEffect], RunParams] = {
    OParser.runParser(parser, args, RunParams()) match {
      case (result, effects) =>
        OParser.runEffects(effects, new DefaultOEffectSetup {
          // override def displayToOut(msg: String): Unit = Console.out.println(msg)
          // override def displayToErr(msg: String): Unit = Console.err.println(msg)
          // override def reportError(msg: String): Unit = displayToErr("Error: " + msg)
          // override def reportWarning(msg: String): Unit = displayToErr("Warning: " + msg)

          // ignore terminate
          override def terminate(exitState: Either[String, Unit]): Unit = ()
        })

        result match {
          case Some (config) => Right(config)
          // do something
          case _ => Left(effects)
          // arguments are bad, error message will have been displayed
        }
    }
  }
}
