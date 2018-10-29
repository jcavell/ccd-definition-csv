package com.jvmtech.ccddef

data class ColName(val name: String) {
    fun typedName(): String{
        return if(name.matches(".*LiveFrom.*|.*LiveTo.*".toRegex())) "Date:" + name
        else if(name.matches(".*PageFieldID.*|.*DisplayOrder.*|.*ColumnNumber.*".toRegex())) "Number:" + name
        else name;
    }
}

data class ColValue(val value: String) {
    fun isEmpty() : Boolean {return value.isEmpty()}
}

data class Row(val colValues: List<ColValue>) {
    fun isEmpty() : Boolean{
        return colValues.map { it.value }.filter { it.isNotEmpty() }.size == 0
    }
}

data class Tab(
        val name: String,
        val descriptionRows: List<Row>,
        val colNames: List<ColName>,
        val dataRows: List<Row>)
{}
