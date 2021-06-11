package cmd

import java.io.File

case class RunParams(
                      sourceFile: File = new File("in.txt"),
                      wordToGenerate: Int = 10
                    )
