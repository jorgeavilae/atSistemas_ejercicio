package com.example.data.commons

import com.example.data.remote.ResultHandler
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

abstract class BaseRepository {
    //Function to return data or error
    suspend inline fun <T : Any> safeApiCall(call: () -> Response<T>): ResultHandler<T> {
        return try{
            val response = call.invoke()
            if (response.isSuccessful)
                ResultHandler.Success(response.body()!!)
            else {
                ResultHandler.HttpError(response.code(), response.message())
            }
        }catch (throwable: Throwable){
            when (throwable) {
                is IOException -> ResultHandler.NetworkError
                is HttpException -> {
                    ResultHandler.HttpError(throwable.code(), throwable.message())
                }
                else -> {
                    ResultHandler.GenericError(throwable.message)
                }
            }
        }
    }
}