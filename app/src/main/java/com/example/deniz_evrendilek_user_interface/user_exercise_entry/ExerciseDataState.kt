package com.example.deniz_evrendilek_user_interface.user_exercise_entry

data class ExerciseDataState(
    var day: Int? = null,
    var month: Int? = null,
    var year: Int? = null,
    var hour: Int? = null,
    var minute: Int? = null,
    var duration: Int? = null,
    var distance: Int? = null,
    var calories: Int? = null,
    var heartRate: Int? = null,
    var comment: String? = null
)
