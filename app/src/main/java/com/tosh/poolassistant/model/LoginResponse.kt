package com.tosh.poolassistant.model

data class LoginResponse( val isSuccessful:Boolean, val message: String, val user: List<User>)