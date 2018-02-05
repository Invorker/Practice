package poi;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class UpdateExcel {

	// public static void main(String[] args) throws Exception {
	// readExcelData("C:/Users/fengyiwei/Desktop/新建文件夹/办公设备固定资产账.xlsx");
	// System.out.println("-----------");
	// readExcelData("C:/Users/fengyiwei/Desktop/新建文件夹/固定资产盘点(财务下发表格).xlsx");
	// System.out.println("-----------");
	// readExcelData("C:/Users/fengyiwei/Desktop/新建文件夹/固定资产盘点12151.xlsx");
	// System.out.println("-----------");
	// readExcelData("C:/Users/fengyiwei/Desktop/新建文件夹/检测设备固定资产账.xlsx");
	// System.out.println("-----------");
	// // readExcelData("C:/Users/fengyiwei/Desktop/新建文件夹/生产设备固定资产账.xlsx");
	// System.out.println("-----------");
	// // readExcelData("C:/Users/fengyiwei/Desktop/新建文件夹/信息设备固定资产账.xls");
	// System.out.println("-----------");
	// readExcelData("C:/Users/fengyiwei/Desktop/新建文件夹/研发设备固定资产账.xlsx");
	// System.out.println("-----------");
	// }

	public static void readExcelData(String url) throws Exception {
		FileInputStream fis = new FileInputStream(url);
		FileInputStream ofis = new FileInputStream("C:/Users/fengyiwei/Desktop/新建文件夹/固定资产盘点(财务下发表格).xlsx");

		Workbook workBook = null;
		Workbook oworkBook = null;

		try {
			if (url.toLowerCase().endsWith("xlsx")) {
				workBook = new XSSFWorkbook(fis);
			} else if (url.toLowerCase().endsWith("xls")) {
				workBook = new HSSFWorkbook(fis);
			}
			oworkBook = new XSSFWorkbook(ofis);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			fis.close();
		}

		int[] startCells = { 7, 7, 3, 4 };
		for (int sheetNum = 0; sheetNum < 4; sheetNum++) {
			Sheet sheet, osheet;
			sheet = workBook.getSheetAt(sheetNum);
			osheet = oworkBook.getSheetAt(sheetNum);
			Row row, orow;
			Cell cell, ocell;
			for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
				row = sheet.getRow(i);
				orow = osheet.getRow(i);
				if (row != null && orow != null) {
					try {
						if ((row.getCell(0) == null && orow.getCell(0) == null)
								|| (row.getCell(0).toString().equals(orow.getCell(0).toString()))) {

							for (int j = startCells[sheetNum]; j < startCells[sheetNum] + 7; j++) {
								if (row.getCell(j) != null && !row.getCell(j).toString().equals("")) {
									ocell = orow.getCell(j);
									if (ocell == null) {
										Cell newCell = orow.createCell(j, Cell.CELL_TYPE_STRING);
										newCell.setCellValue(row.getCell(j).toString());
									} else if (ocell.toString().equals("")) {
										ocell.setCellValue(row.getCell(j).toString());
									} else {
										if (!ocell.toString().equals(row.getCell(j).toString())) {
											System.out.println("数据不一致：sheetNum[" + sheetNum + "],row[" + i + "],cell["
													+ j + "]主[ " + ocell.toString() + " ],分["
													+ row.getCell(j).toString() + "];");
										}
									}
								}
							}
						}
					} catch (NullPointerException e) {
						continue;
					}
				}
			}
		}
		FileOutputStream excelFileOutPutStream = new FileOutputStream(
				"C:/Users/fengyiwei/Desktop/新建文件夹/固定资产盘点(财务下发表格).xlsx");
		oworkBook.write(excelFileOutPutStream);
		excelFileOutPutStream.flush();
		excelFileOutPutStream.close();
		workBook.close();
		oworkBook.close();
	}

	public static void updateExcelTest() throws Exception {
		FileInputStream fis = new FileInputStream("C:/Users/fengyiwei/Desktop/新建文件夹/固定资产盘点(财务下发表格).xlsx");
		Workbook workBook = null;
		// 读取2007版，以 .xlsx 结尾
		try {
			workBook = new XSSFWorkbook(fis);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Sheet sheet = workBook.getSheetAt(0);
		Row row = sheet.getRow(0);
		row.getCell(0).setCellValue("钱七");
		FileOutputStream excelFileOutPutStream = new FileOutputStream(
				"C:/Users/fengyiwei/Desktop/新建文件夹/固定资产盘点(财务下发表格).xlsx");
		workBook.write(excelFileOutPutStream);
		excelFileOutPutStream.flush();
		excelFileOutPutStream.close();
	}

	public static void readExcel() throws Exception {
		FileInputStream fis = new FileInputStream("C:/Users/fengyiwei/Desktop/新建文件夹/办公设备固定资产账.xlsx");
		FileInputStream ofis = new FileInputStream("C:/Users/fengyiwei/Desktop/新建文件夹/固定资产盘点(财务下发表格).xlsx");
		Workbook workBook = null;
		Workbook oworkBook = null;
		try {
			workBook = new XSSFWorkbook(fis);
			oworkBook = new XSSFWorkbook(ofis);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			fis.close();
			ofis.close();
		}

		System.out.println(workBook.getSheetAt(0).getRow(368).getCell(9).toString());
		System.out.println(oworkBook.getSheetAt(0).getRow(368).getCell(9).toString());

	}

	public static void readExcel2(String url) throws Exception {
		FileInputStream fis = new FileInputStream(url);
		FileInputStream ofis = new FileInputStream("C:/Users/fengyiwei/Desktop/新建文件夹/固定资产盘点(财务下发表格).xlsx");

		Workbook workBook = null;
		Workbook oworkBook = null;

		try {
			if (url.toLowerCase().endsWith("xlsx")) {
				workBook = new XSSFWorkbook(fis);
			} else if (url.toLowerCase().endsWith("xls")) {
				workBook = new HSSFWorkbook(fis);
			}
			oworkBook = new XSSFWorkbook(ofis);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			fis.close();
			ofis.close();
		}

		System.out.println(workBook.getSheetAt(1).getRow(131).getCell(9).toString());
		// ocell = oworkBook.getSheetAt(1).getRow(174).getCell(0);
		// ocell.toString();
		// cell = sheet.getRow(173).getCell(0);
		// System.out.println(cell.toString());

		int[] startCells = { 7, 7, 3, 4 };
		for (int sheetNum = 0; sheetNum < 4; sheetNum++) {
			Sheet sheet, osheet;
			sheet = workBook.getSheetAt(sheetNum);
			osheet = oworkBook.getSheetAt(sheetNum);
			Row row, orow;
			Cell cell, ocell;
			for (int i = 2; i < sheet.getPhysicalNumberOfRows(); i++) {
				row = sheet.getRow(i);
				orow = osheet.getRow(i);
				try {
					if (orow != null) {
						if (!row.getCell(0).toString().equals(orow.getCell(0).toString())) {
							System.out.println("序号不一致：sheetNum-->" + sheetNum + "row-->" + i);
						}
					}
				} catch (Exception e) {
					System.out.println("sheetNum-->" + sheetNum + "row-->" + i);
				}

			}
		}

	}

	public static void main(String[] args) throws Exception {
		readExcelData2("C:/Users/fengyiwei/Desktop/新建文件夹/研发设备固定资产账.xlsx");
	}

	public static void readExcelData2(String url) throws Exception {
		FileInputStream fis = new FileInputStream(url);
		FileInputStream ofis = new FileInputStream("C:/Users/fengyiwei/Desktop/新建文件夹/固定资产盘点(财务下发表格).xlsx");

		Workbook workBook = null;
		Workbook oworkBook = null;

		try {
			if (url.toLowerCase().endsWith("xlsx")) {
				workBook = new XSSFWorkbook(fis);
			} else if (url.toLowerCase().endsWith("xls")) {
				workBook = new HSSFWorkbook(fis);
			}
			oworkBook = new XSSFWorkbook(ofis);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			fis.close();
		}

		int[] startCells = { 7, 7, 3, 4 };
		for (int sheetNum = 0; sheetNum < 4; sheetNum += 2) {
			Sheet sheet, osheet;
			sheet = workBook.getSheetAt(sheetNum);
			osheet = oworkBook.getSheetAt(sheetNum);
			Row row, orow;
			Cell cell, ocell;
			int ol = 2;
			for (int i = 2; i < sheet.getPhysicalNumberOfRows(); i++) {
				row = sheet.getRow(i);

				if (row != null) {
					try {
						for (; ol < osheet.getPhysicalNumberOfRows(); ol++) {
							orow = osheet.getRow(ol);
							if (orow != null && row.getCell(0) != null && orow.getCell(0) != null
									&& row.getCell(0).toString().equals(orow.getCell(0).toString())) {
								for (int j = startCells[sheetNum]; j < startCells[sheetNum] + 7; j++) {
									if (row.getCell(j) != null && !row.getCell(j).toString().equals("")) {
										ocell = orow.getCell(j);
										if (ocell == null) {
											Cell newCell = orow.createCell(j, Cell.CELL_TYPE_STRING);
											newCell.setCellValue(row.getCell(j).toString());
										} else {
											ocell.setCellValue(row.getCell(j).toString());
										}
									}
								}
								break;
							}

						}

					} catch (NullPointerException e) {
						continue;
					}
				}
			}
		}
		FileOutputStream excelFileOutPutStream = new FileOutputStream(
				"C:/Users/fengyiwei/Desktop/新建文件夹/固定资产盘点(财务下发表格).xlsx");
		oworkBook.write(excelFileOutPutStream);
		excelFileOutPutStream.flush();
		excelFileOutPutStream.close();
		workBook.close();
		oworkBook.close();
	}
}
