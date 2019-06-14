package com.tangpj.repository.valueObject.query

data class FileContentQuery(val owner: String, val name: String, val expression: String){

    fun getApolloQuery(){

    }
}