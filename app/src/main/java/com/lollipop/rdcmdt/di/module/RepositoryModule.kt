package com.lollipop.rdcmdt.di.module

import com.lollipop.rdcmdt.repository.balance.BalanceRepository
import com.lollipop.rdcmdt.repository.balance.IBalanceRepository
import com.lollipop.rdcmdt.repository.datastore.DataStoreRepository
import com.lollipop.rdcmdt.repository.datastore.IDataStoreRepository
import com.lollipop.rdcmdt.repository.login.ILoginRepository
import com.lollipop.rdcmdt.repository.login.LoginRepository
import com.lollipop.rdcmdt.repository.payees.IPayeesRepository
import com.lollipop.rdcmdt.repository.payees.PayeesRepository
import com.lollipop.rdcmdt.repository.register.IRegisterRepository
import com.lollipop.rdcmdt.repository.register.RegisterRepository
import com.lollipop.rdcmdt.repository.transactions.ITransactionsRepository
import com.lollipop.rdcmdt.repository.transactions.TransactionsRepository
import com.lollipop.rdcmdt.repository.transfer.ITransferRepository
import com.lollipop.rdcmdt.repository.transfer.TransferRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [NetworkModule::class, DataStoreModule::class])
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideLoginRepository(loginRepo: LoginRepository): ILoginRepository

    @Binds
    abstract fun provideRegisterRepository(registerRepo: RegisterRepository): IRegisterRepository

    @Binds
    abstract fun provideBalanceRepository(balanceRepo: BalanceRepository): IBalanceRepository

    @Binds
    abstract fun provideTransactionsRepository(transactionsRepo: TransactionsRepository): ITransactionsRepository

    @Binds
    abstract fun provideTransferRepository(transferRepo: TransferRepository): ITransferRepository

    @Binds
    abstract fun providePayeesRepository(payeesRepo: PayeesRepository): IPayeesRepository

    @Binds
    abstract fun provideDataStoreRepository(dataStoreRepository: DataStoreRepository): IDataStoreRepository
}