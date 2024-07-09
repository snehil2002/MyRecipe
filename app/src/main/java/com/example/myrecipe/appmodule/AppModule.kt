package com.example.myrecipe.appmodule

import android.app.Application
import android.content.Context
import com.example.myrecipe.R
import com.example.myrecipe.api.RecipeApi
import com.example.myrecipe.repository.AuthRepo
import com.example.myrecipe.utils.Constants
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRecipeApi():RecipeApi{
        return Retrofit.Builder().baseUrl(Constants.baseurl).
        addConverterFactory(GsonConverterFactory.create()).
        build().create(RecipeApi::class.java)


    }

    @Provides
    @Singleton
    fun provideFireAuth()=FirebaseAuth.getInstance()

    @Provides
    fun provideOnetapClient(
        @ApplicationContext context:Context
    ):SignInClient{
        return Identity.getSignInClient(context)
    }

    @Provides
    @Named("signinRequest") fun provideSignInRequest(app:Application):BeginSignInRequest{
        return BeginSignInRequest.Builder().setGoogleIdTokenRequestOptions(
            BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                .setSupported(true).setServerClientId(app.getString(R.string.web_client_id))
                .setFilterByAuthorizedAccounts(false).build())
            .setAutoSelectEnabled(true).build()

    }

    @Provides
    @Singleton
    fun provideAuthRepo(
        auth: FirebaseAuth,
        oneTapClient: SignInClient,
        @Named("signinRequest") signinRequest: BeginSignInRequest
    ): AuthRepo {
        return AuthRepo(auth, oneTapClient, signinRequest)
    }

}