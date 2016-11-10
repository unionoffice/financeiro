package br.com.unionoffice.util;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.HSSFRegionUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.util.CellRangeAddress;

import br.com.unionoffice.modelo.Movimento;

public class GeraExcel {
	public static void expExcel(List<Movimento> movimentos, String diretorio) {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheetEntrada = workbook.createSheet("Movimentos");
		sheetEntrada.setDefaultRowHeight((short) 400);
		FileOutputStream fos = null;
		String nomeArquivo = diretorio + "/financeiro.xls";

		// FONTE
		HSSFFont fonte = workbook.createFont();
		fonte.setFontHeightInPoints((short) 11);
		fonte.setFontName("Arial");
		fonte.setBold(true);

		// ESTILO
		CellStyle style = workbook.createCellStyle();
		style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.ALIGN_CENTER);
		style.setFont(fonte);
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setBorderLeft(CellStyle.BORDER_THIN);

		// FONTE NOTA
		HSSFFont fonteNota = workbook.createFont();
		fonte.setFontHeightInPoints((short) 11);
		fonte.setFontName("Arial");

		// ESTILO NOTA
		CellStyle styleMovimento = workbook.createCellStyle();
		styleMovimento.setAlignment(CellStyle.ALIGN_CENTER);
		styleMovimento.setVerticalAlignment(CellStyle.ALIGN_CENTER);
		styleMovimento.setFont(fonteNota);
		styleMovimento.setBorderBottom(CellStyle.BORDER_THIN);
		styleMovimento.setBorderTop(CellStyle.BORDER_THIN);
		styleMovimento.setBorderRight(CellStyle.BORDER_THIN);
		styleMovimento.setBorderLeft(CellStyle.BORDER_THIN);

		int posLinha = 0;

		// CABEÇALHO DAS CÉLULAS
		HSSFRow linha = sheetEntrada.createRow(posLinha++);
		linha.createCell(0).setCellValue("EMISSÃO");
		linha.createCell(1).setCellValue("VALOR");
		linha.createCell(2).setCellValue("QTD_PARC");
		linha.createCell(3).setCellValue("PARCELA");
		linha.createCell(4).setCellValue("VENCIMENTO");
		linha.createCell(5).setCellValue("REFERÊNCIA");
		linha.createCell(6).setCellValue("EMITENTE");
		linha.createCell(7).setCellValue("NUM");
		linha.createCell(8).setCellValue("ACEITE");
		linha.createCell(9).setCellValue("SITUAÇÃO");
		linha.createCell(10).setCellValue("COMPROV");
		linha.createCell(11).setCellValue("OBSERVAÇÃO");

		for (int i = 0; i < 12; i++) {
			linha.getCell(i).setCellStyle(style);
			sheetEntrada.autoSizeColumn(i);
		}
		CellStyle estiloNumber = workbook.createCellStyle();

		SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
		for (Movimento m : movimentos) {
			HSSFRow linhaMovimento = sheetEntrada.createRow(posLinha++);
			linhaMovimento.setHeight((short) 400);
			linhaMovimento.createCell(0).setCellValue(formatador.format(m.getData().getTime()));
			linhaMovimento.getCell(0).setCellStyle(styleMovimento);
			linhaMovimento.createCell(1)
					.setCellValue(m.getValorParcela().doubleValue());
			linhaMovimento.getCell(1).getCellStyle().setDataFormat(workbook.createDataFormat().getFormat("#,##0.00"));
			linhaMovimento.getCell(1).setCellStyle(styleMovimento);
			linhaMovimento.createCell(2).setCellValue(m.getCondPagamento().qtdParcelas);
			linhaMovimento.getCell(2).setCellStyle(styleMovimento);
			linhaMovimento.createCell(3).setCellValue(m.getNumParcela());
			linhaMovimento.getCell(3).setCellStyle(styleMovimento);
			linhaMovimento.createCell(4).setCellValue(formatador.format(m.getVencimento().getTime()));
			linhaMovimento.getCell(4).setCellStyle(styleMovimento);
			linhaMovimento.createCell(5).setCellValue(m.getReferencia());
			estiloNumber.cloneStyleFrom(styleMovimento);
			linhaMovimento.getCell(5).setCellStyle(estiloNumber);
			linhaMovimento.getCell(5).getCellStyle().setDataFormat(workbook.createDataFormat().getFormat("#,##0.00"));
			linhaMovimento.createCell(6).setCellValue(m.getEmitente());
			linhaMovimento.getCell(6).setCellStyle(styleMovimento);
			linhaMovimento.createCell(7).setCellValue(m.getNumero());
			linhaMovimento.getCell(7).setCellStyle(styleMovimento);
			linhaMovimento.createCell(8).setCellValue(m.isAceite() ? "OK" : "");
			linhaMovimento.getCell(8).setCellStyle(styleMovimento);
			linhaMovimento.createCell(9).setCellValue(m.getSituacao().toString());
			linhaMovimento.getCell(9).setCellStyle(styleMovimento);
			linhaMovimento.createCell(10).setCellValue(m.getComprovante());
			linhaMovimento.getCell(10).setCellStyle(styleMovimento);
			linhaMovimento.createCell(11).setCellValue(m.getObservacao());
			linhaMovimento.getCell(11).setCellStyle(styleMovimento);
		}

		for (int i = 0; i < 12; i++) {
			sheetEntrada.autoSizeColumn(i);
		}

		HSSFRow linhaTotal = sheetEntrada.createRow(++posLinha);
		linhaTotal.createCell(0).setCellValue("Total:");
		linhaTotal.getCell(0).setCellStyle(style);
		linhaTotal.createCell(1).setCellFormula("SUM(B2:B" + (posLinha - 1) + ")");
		linhaTotal.getCell(1).setCellStyle(estiloNumber);

		try {
			File planilha;
			fos = new FileOutputStream(planilha = new File(nomeArquivo));
			workbook.write(fos);
			fos.flush();
			fos.close();
			Desktop.getDesktop().open(planilha);
		} catch (Exception e) {
			e.printStackTrace();

		}
	}
}
