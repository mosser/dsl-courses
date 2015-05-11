package eu.ace_design.dsl.polytech.kernel


case class Module(code: String, name: String, position: Position)
case class Position(day: WeekDays.WeekDay, slot: Slots.Slot, period: Periods.Period)
case class Curriculum(name: String, mandatory: Set[Module])

object WeekDays extends Enumeration {
  type WeekDay = Value; val monday, tuesday, wednesday, thursday, friday = Value
}

object Slots extends Enumeration {
  type Slot = Value; val morning, afternoon = Value
}

object Periods extends Enumeration {
  type Period = Value; val period1, period2, bothPeriods = Value
}
