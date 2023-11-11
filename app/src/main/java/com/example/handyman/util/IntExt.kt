package com.example.handyman.util

fun Int?.orZero(): Int {
    return this ?: 0
}