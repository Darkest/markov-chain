package ru.github.darkest.markov_chain

trait MarkovChain {
  //def apply(text :Seq[String]): MarkovChain

  def generate(textLength: Int): String


}
