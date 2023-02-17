package solutionThree

interface NewDataModule {

    fun provideInteractor(): NewInteractor
    class Base() : NewDataModule {
        override fun provideInteractor(): NewInteractor {
            return NewInteractor.Base(NewRepository.Base(NewCloudDataSource.Mock(), NewInfoDomainMapper())
            )
        }
    }
}

interface NewInteractor {

    fun info(): NewInfoDomain

    class Base(private val repository: NewRepository<NewInfoDomain>) : NewInteractor {
        override fun info(): NewInfoDomain {
            val data = repository.info()
            return data
        }
    }
}

interface NewRepository<T> {

    fun info(): T

    class Base<T>(
        private val cloudDataSource: NewCloudDataSource,
        private val mapper: NewInfoData.NewMapper<T>
    ) : NewRepository<T> {
        override fun info(): T {
            val id = cloudDataSource.id()
            val dataObject = NewInfoData.Base(id)
            return dataObject.map(mapper)
        }
    }
}

interface NewCloudDataSource {

    fun id(): String

    class Mock : NewCloudDataSource {
        override fun id(): String {
            return ""
        }

    }
}

interface NewInfoData {

    fun <T> map(mapper: NewMapper<T>): T

    class Base(private val id: String) : NewInfoData {
        override fun <T> map(mapper: NewMapper<T>): T {
            return mapper.map(id)
        }
    }

    interface NewMapper<T> {
        fun map(id: String): T
    }
}

class NewInfoDomainMapper : NewInfoData.NewMapper<NewInfoDomain> {
    override fun map(id: String): NewInfoDomain {
        return if (id.isEmpty()) NewInfoDomain.Fail() else NewInfoDomain.Base(id)
    }
}

interface NewInfoDomain {
    class Base(private val id: String) : NewInfoDomain

    class Fail() : NewInfoDomain
}