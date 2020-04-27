package com.globant.utils

/**
 * A generic wrapper class around data request
 */
data class Data<RequestData>(var responseType: Status, var data: RequestData? = null, var error: Exception? = null)

enum class Status {
    LOADING,
    GET_CHARACTER_SUCCESS,
    GET_CHARACTER_ERROR,
    GET_CHARACTER_BY_ID_SUCCESS,
    GET_CHARACTER_BY_ID_ERROR,
    GET_LOCAL_CHARACTER_SUCCESS,
    GET_LOCAL_CHARACTER_ERROR
}
