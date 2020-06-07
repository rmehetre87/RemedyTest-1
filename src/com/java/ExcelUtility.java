package com.java;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;


import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ExcelUtility {

	private static XSSFSheet ExcelWSheet;

	private static XSSFWorkbook ExcelWBook;

	private static XSSFCell Cell;

	private static XSSFRow Row;

	private static String Path_TestData = ".//Data//";

	private static String File_TestData = "Remedy_GetData.xlsx";

	public static void setExcelFile(String Path,String SheetName) throws Exception {

		try {

			// Open the Excel file

			FileInputStream ExcelFile = new FileInputStream(Path);

			// Access the required test data sheet

			ExcelWBook = new XSSFWorkbook(ExcelFile);

			ExcelWSheet = ExcelWBook.getSheet(SheetName);

		} catch (Exception e){

			throw (e);

		}

	}

	public static String getCellData(int RowNum, int ColNum) throws Exception{

		try{

			Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);


			String CellData = Cell.getStringCellValue().trim();

			return CellData;

		}catch (Exception e){

			System.out.println("Exception :" +e);	
			return null;

		}
	}


	public static ArrayList<String> getRowData(int RowNum) throws Exception{

		ArrayList<String> datalist = new ArrayList<String>();
		try{

			Row = ExcelWSheet.getRow(RowNum);
			int columns = Row.getLastCellNum();
			System.out.println("num of col "+columns);

			for(int i = 0;i<columns;i++) {

				Cell = Row.getCell(i);
				Cell.setCellType(CellType.STRING);
				String CellData = Cell.getStringCellValue().toString().trim();
				//System.out.println(CellData);
				datalist.add(CellData);
			}
			return datalist;
		}
		catch (Exception e){

			System.out.println("Exception :" +e);	
			return null;

		}
	}

	public static void setCellData(String Result,  int RowNum, int ColNum) throws Exception	{

		try{

			Row  = ExcelWSheet.getRow(RowNum);

			//Cell = Row.getCell(ColNum);
			Cell = Row.getCell(ColNum, org.apache.poi.ss.usermodel.Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);

			if (Cell == null) {

				Cell = Row.createCell(ColNum);

				Cell.setCellValue(Result);

			} else {

				Cell.setCellValue(Result);

			}

			// Constant variables Test Data path and Test Data file name

			FileOutputStream fileOut = new FileOutputStream(Path_TestData + File_TestData);

			ExcelWBook.write(fileOut);

			fileOut.flush();

			fileOut.close();

		}catch(Exception e){


			throw (e);



		}

	}



	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {
			ExcelUtility.setExcelFile(Path_TestData+File_TestData,"Sheet1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int inc_count = ExcelUtility.getNumberofIncidents();
		System.out.println("count is "+inc_count);

		ArrayList<String> arr = ExcelUtility.readExcle();
		System.out.println("\n"+arr.toString());

	}

	public static synchronized  ArrayList<String> readExcle() {

		try {

			ArrayList<String> data = new ArrayList<String>();

			//Row = ExcelWSheet.getRow(0);

			int rowcount = ExcelWSheet.getLastRowNum()-ExcelWSheet.getFirstRowNum();

			for(int i = 0; i< rowcount + 1; i++) {

				Row = ExcelWSheet.getRow(i);
				for(int j = 0; j< Row.getLastCellNum(); j++) {

					Cell = Row.getCell(j,org.apache.poi.ss.usermodel.Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
					String celldata = Cell.getStringCellValue();
					data.add(celldata);

				}

			}
			return data;
		}

		catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	public static int getNumberofIncidents() {

		int rowcount = ExcelWSheet.getLastRowNum()-ExcelWSheet.getFirstRowNum();
		return rowcount;

	}


}
