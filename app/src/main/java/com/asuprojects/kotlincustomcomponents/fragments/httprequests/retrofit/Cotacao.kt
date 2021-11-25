package com.asuprojects.kotlincustomcomponents.fragments.httprequests.retrofit

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties("by", "valid_key", "execution_time", "from_cache")
data class Cotacao(
    var results: Any = Any()
) {
}