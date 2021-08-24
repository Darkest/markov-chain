package ru.github.darkest

import java.util.regex.Pattern

package object markov_chain {

  object Utils {
    def sanitizeText(string: String): String = {
      val nonAlphanumericSpacePattern = Pattern.compile("[\\W&&\\S]", Pattern.UNICODE_CHARACTER_CLASS)
      val multipleSpace = Pattern.compile("\\s+", Pattern.UNICODE_CHARACTER_CLASS)
      val alphaNumericOnly = nonAlphanumericSpacePattern.matcher(string).replaceAll("")
      val multiSpacesRemoved = multipleSpace.matcher(alphaNumericOnly).replaceAll(" ")
      multiSpacesRemoved.trim.toLowerCase
    }
  }

}
