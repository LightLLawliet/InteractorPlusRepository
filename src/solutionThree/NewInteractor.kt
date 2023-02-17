package solutionThree


interface NewInteractor {

    fun info() : NewInfoDomain
}

interface NewRepository<T> {

    fun info():T
}

interface NewInfoDomain {

}