package com.jvmtech.ccddef

data class ColName(val name: String) {
    fun typedName(): String{
        return if(name.matches(".*LiveFrom.*|.*LiveTo.*".toRegex())) "Date:" + name
        else if(name.matches(".*PageFieldID.*|.*DisplayOrder.*|.*ColumnNumber.*".toRegex())) "Int:" + name
        else name;
    }
}

data class ColValue(val value: String) {}

data class Row(val colValues: List<ColValue>) {}

data class Tab(
        val name: String,
        val descriptionRows: List<Row>,
        val colNames: List<ColName>,
        val dataRows: List<Row>)
{}
