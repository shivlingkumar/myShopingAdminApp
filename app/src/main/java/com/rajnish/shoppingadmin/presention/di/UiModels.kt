package com.rajnish.shoppingadmin.presention.di

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.rajnish.shoppingadmin.data.repoimal.RepoimPal
import com.rajnish.shoppingadmin.domain.repo.Repo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object UiModels {
    @Provides
    fun provideRepo(
        firebaseFirestore: FirebaseFirestore,
        firebaseStorage: FirebaseStorage
    ): Repo {
        return RepoimPal(firebaseFirestore, firebaseStorage)
    }
}
