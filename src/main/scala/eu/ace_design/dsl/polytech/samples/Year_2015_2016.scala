package eu.ace_design.dsl.polytech.samples

import eu.ace_design.dsl.polytech.kernel._
import eu.ace_design.dsl.polytech.kernel.WeekDays._
import eu.ace_design.dsl.polytech.kernel.Slots._
import eu.ace_design.dsl.polytech.kernel.Periods._


object Year_2015_2016 extends App with Curriculum_15_16 {

  println(modules)
  println(curriculums)

  // TODO: Generate HTML table for department website
  // TODO generate Quexml file for LimeSurvey auto-import feature

}


trait Curriculum_15_16 extends Curriculums with Portfolio_15_16 {

  declare {
    "AL"     withMandatoryCourses ( "EP5I9161", "EP5I9193" )
    "CASPAR" withMandatoryCourses ( "EP5I9161" )
  }

}


trait Portfolio_15_16 extends Portfolio {

  bind.to (monday, morning, period1) {
    aCourse withId "EP5I9161" named "Algorithmic approach to Cloud Computing"
    aCourse withId "EP5I9193" named "Technologies pour les données massives"
  }

  bind.to (monday, morning, bothPeriods) {
    aCourse withId "EP5I9112" named "Conception et évaluation des IHM"
  }

}
