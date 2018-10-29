# CCD Definition CSV

## Create CSVs from Excel

### Syntax
com.jvmtech.ccddef.ExcelToCSVKt sourceExcelFile destCSVDir

### Example
com.jvmtech.ccddef.ExcelToCSVKt /ccddef/sourcexlsx/CCD_SSCSDefinition_v3.4.1_AAT.xlsx /ccddef/csv


## Create Excel from CSVs

### Syntax
com.jvmtech.ccddef.CSVToExcelKt sourceCSVDir destExcelDir workbookName keyValueFile env version 

### Example

com.jvmtech.ccddef.CSVToExcelKt /ccddef/csv/ /ccddef/generatedxlsx workbook /ccddef/csv/keyValues.csv AAT 1.9.8
