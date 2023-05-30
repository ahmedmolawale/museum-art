package com.appsfactory.common

sealed class Failure {
    object ServerError : Failure()
}
