interface ExerciseData {

    fun <T> map(mapper: Mapper<T>): T

    interface Mapper<T> {
        fun map(
            id: Int,
            type: ExerciseType,
            correctAnswer: String,
            options: List<String>
        ): T
    }

    class Base(
        private val id: Int,
        private val type: ExerciseType,
        private val correctAnswer: String,
        private val options: List<String>
    ) : ExerciseData {
        override fun <T> map(mapper: Mapper<T>): T {
            return mapper.map(id, type, correctAnswer, options)
        }
    }
}

enum class ExerciseType {
    CHOOSE,
    FILL_ORDERED
}

class DomainMapper : ExerciseData.Mapper<ExerciseDomain> {
    override fun map(id: Int, type: ExerciseType, correctAnswer: String, options: List<String>): ExerciseDomain {
        return if (type == ExerciseType.CHOOSE) {
            ExerciseDomain.ChooseExercise(id, options.map {
                if (it == correctAnswer)
                    OptionDomain.Correct(it)
                else
                    OptionDomain.Incorrect(it)
            })
        } else {
            ExerciseDomain.FillByOrderExercise(id, correctAnswer, options)
        }
    }
}

interface ExerciseDomain {

    fun <T> map(mapper: Mapper<T>): T
    class ChooseExercise(private val id: Int, private val options: List<OptionDomain>) :
        ExerciseDomain {
        override fun <T> map(mapper: Mapper<T>): T {
            TODO()
        }

    }

    class FillByOrderExercise(private val id: Int, private val correct: String, private val options: List<String>) :
        ExerciseDomain {
        override fun <T> map(mapper: Mapper<T>): T {
            TODO("Not yet implemented")
        }
    }

    interface Mapper<T> {
        fun mapChooseExercise(id: Int, correctAnswer: String, options: List<String>) {

        }
    }
}

interface OptionDomain {
    class Correct(private val string: String) : OptionDomain {

    }

    class Incorrect(private val value: String) : OptionDomain {

    }
}

