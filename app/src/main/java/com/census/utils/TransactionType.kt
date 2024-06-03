package com.census.utils

enum class TransactionType(transactionType: String) {
    NONE("NONE"),
    SESSION("SESSION"),
    SUBSCRIPTION("SUBSCRIPTION"),
    TOPUP("TOPUP"),
    WITHDRAW("WITHDRAW")
}