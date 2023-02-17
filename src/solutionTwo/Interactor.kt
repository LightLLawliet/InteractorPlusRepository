package solutionTwo


interface Interactor {
    fun getInfo(): InfoDomain

    class Base(private val repository: Repository) : Interactor {
        override fun getInfo(): InfoDomain {
            val info = repository.getInfo()
            return info
        }
    }
}

interface Repository {
    fun getInfo(): InfoDomain

    class Base(
        private val cloudDataSource: CloudDataSource,
        private val mapper: InfoData.Mapper<InfoDomain>
    ) : Repository {
        override fun getInfo(): InfoDomain {
            val id = cloudDataSource.infoId()
            val data = InfoData.Base(id)
            return data.map(mapper)
        }

    }
}

interface CloudDataSource {
    fun infoId(): String
}

interface InfoDomain {

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