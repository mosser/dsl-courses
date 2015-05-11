package eu.ace_design.dsl.polytech.kernel


import eu.ace_design.dsl.polytech.kernel.WeekDays.WeekDay
import eu.ace_design.dsl.polytech.kernel.Slots.Slot
import eu.ace_design.dsl.polytech.kernel.Periods.Period


trait Curriculums  { portfolio: Portfolio =>

  import scala.language.implicitConversions

  var curriculums: Set[Curriculum]  = Set()

  var currentCurriculum: Curriculum  = _
  var currentCourses:    Set[Module] = _

  def declare(contents: => Unit) {
    contents
    saveCurrentCurriculum()
  }

  private def saveCurrentCurriculum() {
    if(currentCurriculum != null)
      curriculums += currentCurriculum
    currentCurriculum = null
  }


  protected case class CurriculumBuilder(modules: Set[Module] = Set(), name: String = "") {
    def withMandatoryCourses(ids: String*): CurriculumBuilder = {
      val modules = ids map { id => (portfolio.modules find { m => m.code == id }).get }
      val updated = this.copy(modules = modules.toSet); currentCurriculum = updated; updated
    }
  }

  implicit def str2curriculumBuilder(s: String): CurriculumBuilder = {
    saveCurrentCurriculum()
    CurriculumBuilder(name = s)
  }
  
  implicit protected def curriculumBuilder2Curriculum(builder: CurriculumBuilder): Curriculum = {
    Curriculum(name = builder.name, mandatory = builder.modules)
  }


}

trait Portfolio {

  import scala.language.implicitConversions

   def bind = this

   def to(day: WeekDays.WeekDay, slot: Slots.Slot, period: Periods.Period)(contents: => Unit): Unit = {
    positionCourse(day, slot, period)
    contents
    saveCurrentModule() // saving the last one
    positionCourse(null, null, null)
  }

  protected def aCourse() = {
    saveCurrentModule()
    CourseBuilder()
  }


  private def positionCourse(day: WeekDay, slot: Slot, period: Period): Unit = {
    currentDay = day
    currentSlot = slot
    currentPeriod = period
  }

  private def saveCurrentModule(): Unit = {
    if (currentModule != null) {
      modules += currentModule
      currentModule = null
    }
  }

  /**
   ** Data structure used in the trait to build the curriculum
   */

  var modules: Set[Module]  = Set()
  private var currentDay: WeekDay   = _
  private var currentPeriod: Period = _
  private var currentSlot: Slot     = _
  private var currentModule: Module = _


  protected case class CourseBuilder(name: String = "", id: String = "")  {
    def named(n: String): CourseBuilder  = { val updated = this.copy(name = n); currentModule = updated; updated }
    def withId(i: String): CourseBuilder = { val updated = this.copy(id = i);   currentModule = updated; updated }
  }

  implicit def courseBuilder2Module(builder: CourseBuilder): Module = {
    Module(code = builder.id, name = builder.name,
           position = Position(day = currentDay, slot = currentSlot, period = currentPeriod))
  }

}
