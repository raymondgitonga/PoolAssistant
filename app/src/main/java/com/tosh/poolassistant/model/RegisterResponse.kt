package com.tosh.poolassistant.model

data class RegisterResponse(val isSuccessful:Boolean, val message:String, val user: List<User>)