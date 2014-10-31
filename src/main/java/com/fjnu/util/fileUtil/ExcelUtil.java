package com.fjnu.util.fileUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import jxl.format.UnderlineStyle;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class ExcelUtil {

	/**
	 * 读取Excel文件的内容
	 * 
	 * @param file
	 *            待读取的文件
	 * @return
	 */
	public static Vector<Vector<DataCell>> readFormInputDataSetExcelFile(
			String fileName) {
		Vector<Vector<DataCell>> dataRows = new Vector<Vector<DataCell>>();

		Workbook workbook = null;
		try {
			FileInputStream fis = new FileInputStream(fileName);
			// 构造Workbook（工作薄）对象
			workbook = Workbook.getWorkbook(fis);

		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (workbook == null)
			return dataRows;

		// 获得了Workbook对象之后，就可以通过它得到Sheet（工作表）对象了
		Sheet[] sheet = workbook.getSheets();

		if (sheet != null && sheet.length > 0) {
			String colNames[] = null;
			// 对每个工作表进行循环
			for (int i = 0; i < sheet.length; i++) {
				// 得到当前工作表的行数
				int rowNum = sheet[i].getRows();
				for (int j = 0; j < rowNum; j++) {
					Vector<DataCell> dataRow = new Vector<DataCell>();

					// 得到第j行的所有单元格
					Cell[] cells = sheet[i].getRow(j);

					if (j == 0 && cells != null) {

						int colNum = cells.length;
						// 从第0行获取单元格列名
						colNames = new String[colNum];

						for (int m = 0; m < colNum; m++) {
							colNames[m] = cells[m].getContents().trim();
						}
					}

					if (cells != null && cells.length > 0) {
						// 对每个单元格进行循环
						for (int k = 0; k < cells.length; k++) {
							// 读取当前单元格的值
							String cellValue = cells[k].getContents();
							DataCell dataCell = new DataCell();
							dataCell.setCellName(colNames[k]);
							dataCell.setCellValue(cellValue);
							dataRow.add(dataCell);
						}
						dataRows.add(dataRow);
					}
				}
			}
		}
		// 最后关闭资源，释放内存
		workbook.close();
		// String sbString = "execl content:" + strbuf.toString();
		return dataRows;

	}

	/**
	 * 生成一个Excel文件
	 * 
	 * @param fileName
	 *            要生成的Excel文件名
	 */
	public static void writeToExcel(String excelfileName,
			List<List<DataCell>> results) {
		   WritableWorkbook wwb = null;
		   Workbook wb=null;
		try {
			
			File  excelFile=new File(excelfileName);
			if(excelFile.exists()){
			
			//获得Excel工作薄(Workbook)对象
			Workbook wb1=Workbook.getWorkbook(excelFile);
			//打开一个文件的副本，并且指定数据写回到原文件
			WritableWorkbook wwb1=Workbook.createWorkbook(new File(excelfileName),wb1);
			wb=wb1;
			wwb=wwb1;
	
			}
			else{
				// 使用Workbook类的工厂方法创建一个可写入的工作薄(Workbook)对象
				WritableWorkbook wwb2=Workbook.createWorkbook(excelFile);
				wwb=wwb2;
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (BiffException e) {
			e.printStackTrace();
		}
		if (wwb != null) {
			// 创建一个可写入的工作表
			// Workbook的createSheet方法有两个参数，第一个是工作表的名称，第二个是工作表在工作薄中的位置
			String fileNameWithouPath=FileNameUtil.getFileNameInfo(excelfileName)[1];
			int len=wwb.getSheets().length;
			WritableSheet ws = wwb.createSheet(fileNameWithouPath+"-"+len,  len);
			int rowNum = results.size();
			ws.setColumnView(0, 30); // 设置第1列的宽度
			ws.setColumnView(1, 600); // 设置2列的宽度

			try {
				for (int m = 0; m < 10; m++) {// 设置行的高度
					ws.setRowView(m, 400);
				}
				ws.setRowView(10, 1200);
			} catch (RowsExceededException e2) {

				e2.printStackTrace();
			}

			WritableFont wf = new WritableFont(WritableFont.TIMES, 12,
					WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE,
					jxl.format.Colour.CORAL); // 定义格式 字体 下划线 斜体 粗体 颜色
			WritableCellFormat wcf = new WritableCellFormat(wf); // 单元格定义
			// wcf.setBackground(jxl.format.Colour.BLACK); // 设置单元格的背景颜色
			try {// 设置对齐方式
				wcf.setAlignment(jxl.format.Alignment.LEFT);
				wcf.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
				wcf.setWrap(true); 
			} catch (WriteException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} 

			// 下面开始添加单元格
			for (int i = 0; i < rowNum; i++) {

				List<DataCell> row = results.get(i);

				int colSize = row.size();
				for (int k = 0; k < colSize; k++) {
					DataCell cell = row.get(k);
					// // 这里需要注意的是，在Excel中，第一个参数表示列，第二个表示行
					Label labelTitle = new Label(k, i, cell.getCellName(),wcf);
					Label labelContent = new Label(k + 1, i,
							cell.getCellValue(),wcf);
					try {

						// 将生成的单元格添加到工作表中
						ws.addCell(labelTitle);
						ws.addCell(labelContent);
					} catch (RowsExceededException e) {
						e.printStackTrace();
					} catch (WriteException e) {
						e.printStackTrace();
					}

				}

			}
		}

		try {
			// 从内存中写入文件中
			wwb.write();
			// 关闭资源，释放内存
			wwb.close();
			if(wb!=null){
				wb.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		}
	}
}
