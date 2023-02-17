interface Interactor {
    fun getInfo(): InfoDomain

    class Base(
        private val repository: Repository,
        private val mapper: InfoData.Mapper<InfoDomain>
    ) : Interactor {
        override fun getInfo(): InfoDomain {
            val infoData = repository.getInfo()
            return infoData.map(mapper)
        }
    }
}

interface Repository {
    fun getInfo(): InfoData
}

interface InfoData {
    fun <T> map(mapper: Mapper<T>): T

    class Base(private val id: String) : InfoData {
        override fun <T> map(mapper: Mapper<T>): T {
            return mapper.map(id)
        }
    }

    interface Mapper<T> {
        fun map(id: String): T
    }
}

interface InfoDomain {

}