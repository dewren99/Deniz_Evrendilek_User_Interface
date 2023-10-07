package com.example.deniz_evrendilek_user_interface.data

import android.icu.util.Calendar

class ExerciseDataState(
    var day: Int = Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
    var month: Int = Calendar.getInstance().get(Calendar.MONTH),
    var year: Int = Calendar.getInstance().get(Calendar.YEAR),
    var hour: Int = Calendar.getInstance().get(Calendar.HOUR),
    var minute: Int = Calendar.getInstance().get(Calendar.MINUTE),
    private var duration: Int = 0,
    private var distance: Int = 0,
    private var calories: Int = 0,
    private var heartRate: Int = 0,
    private var comment: String = ""
) {
    fun saveInstanceState(
        putString: (String, String?) -> Unit, putInt: (String, Int) -> Unit
    ) {
        putInt(this.javaClass.name + "day", day)
        putInt(this.javaClass.name + "month", month)
        putInt(this.javaClass.name + "year", year)
        putInt(this.javaClass.name + "hour", hour)
        putInt(this.javaClass.name + "minute", minute)
        putInt(this.javaClass.name + "duration", duration)
        putInt(this.javaClass.name + "distance", distance)
        putInt(this.javaClass.name + "calories", calories)
        putInt(this.javaClass.name + "heartRate", heartRate)
        putString(this.javaClass.name + "comment", comment)
    }

    operator fun component1(): Int {
        return day
    }

    operator fun component2(): Int {
        return month
    }

    operator fun component3(): Int {
        return year
    }

    fun restoreSavedInstanceState(
        getString: (String, String) -> String, getInt: (String, Int) -> Int
    ): ExerciseDataState {
        return ExerciseDataState(
            getInt(this.javaClass.name + "day", day),
            getInt(this.javaClass.name + "month", month),
            getInt(this.javaClass.name + "year", year),
            getInt(this.javaClass.name + "hour", hour),
            getInt(this.javaClass.name + "minute", minute),
            getInt(this.javaClass.name + "duration", duration),
            getInt(this.javaClass.name + "distance", distance),
            getInt(this.javaClass.name + "calories", calories),
            getInt(this.javaClass.name + "heartRate", heartRate),
            getString(this.javaClass.name + "comment", comment)
        )
    }

    override fun toString(): String {
        return super.toString() + " | $day, $month, $year, $hour, $minute"
    }
}
