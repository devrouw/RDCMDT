package com.lollipop.rdcmdt.helper

class Constant {
    object URL {
        const val BASE = "https://green-thumb-64168.uc.r.appspot.com"
    }
    object Network {
        const val SERVER_DOWN = 500
        const val MAINTENANCE = 599
        const val REQUEST_UNAUTHORIZED = 401
        const val REQUEST_UNAUTHORIZED_STRING = "401"
        const val REQUEST_NOT_FOUND = 404
        const val BAD_REQUEST = 400
        const val REQUEST_SUCCESS = 200
        const val REQUEST_CREATED = 201
        const val REQUEST_ACCEPTED = 202
        const val REQUEST_NO_CONTENT = 204
        const val PRECONDITION_FAILED = 412
        const val LOGIN_SSO_FAILED = 410
        const val SIGNUP_EMAIL_INVALID = 409
    }
}