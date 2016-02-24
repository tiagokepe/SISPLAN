package br.ifpr.sisplan.controller.bean;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.ifpr.sisplan.model.dao.HistoricoDao;
import br.ifpr.sisplan.model.table.Historico;
import br.ifpr.sisplan.util.ConverterToList;
import br.ifpr.sisplan.util.DateUtil;
import br.ufrn.arq.parametrizacao.RepositorioDadosInstitucionais;
import br.ufrn.arq.web.jsf.AbstractController;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;


@Component
@Scope("request")
public class HistoricoControllerBean extends AbstractController {
	private List<Historico> listHistorico;
	
	public HistoricoControllerBean() {
		this.listHistorico = ConverterToList.convertListMappedToList(getDAO(HistoricoDao.class).selectAll(), Historico.class);
	}

	public List<Historico> getListHistorico() {
		return listHistorico;
	}

	public void setListHistorico(List<Historico> listHistorico) {
		this.listHistorico = listHistorico;
	}
	
	public void goToHistory() {
		this.redirect("/portal/historico.jsf");
	}
	
	public String getHistoryURL() {
		return "/SISPLAN/portal/historico.jsf";
	}
	
	public void generatePDF() throws IOException {
		getCurrentResponse().setContentType("application/pdf");
		getCurrentResponse().addHeader("Content-Disposition", "attachment; filename=historico.pdf");
		try {
	        Document pdfDoc = new Document(PageSize.A2);
	        PdfWriter.getInstance(pdfDoc, getCurrentResponse().getOutputStream());
	        pdfDoc.open();
	        this.addDocHearder(pdfDoc);
	        
	        PdfPTable table = new PdfPTable(new float[]{5, 5, 5, 8, 5, 5, 8, 8});
	        table.setWidthPercentage(100);
	        
	        this.addTableHeader(table);
			BaseColor color = BaseColor.LIGHT_GRAY;
			
			for(Historico hist: listHistorico) {
				color = color.equals(BaseColor.LIGHT_GRAY)? BaseColor.WHITE : BaseColor.LIGHT_GRAY;
				
				this.addCell(table, hist.getTimeStamp(), color);
				this.addCell(table, hist.getResponsavel(), color);
				this.addCell(table, hist.getTipoComponente(), color);
				this.addCell(table, hist.getDetalhe(), color);
				this.addCell(table, hist.getUnidade(), color);
				this.addCell(table, hist.getCampoAlterado(), color);
				this.addCell(table, hist.getDe(), color);
				this.addCell(table, hist.getPara(), color);
			}
	        pdfDoc.add(table);
	        pdfDoc.close();
		} catch (DocumentException de) {
            throw new IOException(de.getMessage());
        }
		
		if (FacesContext.getCurrentInstance() != null)
			FacesContext.getCurrentInstance().responseComplete();
		return;
	}
	
	private void addDocHearder(Document pdfDoc) throws IOException, DocumentException {
		URL url = new URL(RepositorioDadosInstitucionais.get("logoInstituicao"));
		Image logo = Image.getInstance(url);
		logo.setAlignment(Image.MIDDLE);
		logo.scaleAbsoluteHeight(20);
		logo.scaleAbsoluteWidth(20);
		logo.scalePercent(100);
		pdfDoc.add(logo);
		
		String title = "Relatório de alterações do Planejamento Estratégico";
        pdfDoc.addTitle(title);
        pdfDoc.addSubject(title);

        Font fontbold = FontFactory.getFont("Times-Roman", 32, Font.BOLD);
        Paragraph p = new Paragraph(title, fontbold);
        p.setSpacingAfter(20);
        p.setAlignment(1); // Center
        pdfDoc.add(p);
        
        LineSeparator line = new com.itextpdf.text.pdf.draw.LineSeparator();
        line.setLineColor(BaseColor.BLACK);
        line.setPercentage(100);
        Chunk linebreak = new Chunk(line);
        pdfDoc.add(linebreak);
        
        Paragraph emited = new Paragraph("Emitido em: " + DateUtil.timeStampToString(new Date()));
        pdfDoc.add(emited);
        pdfDoc.add(linebreak);
	}
	
	private void addCell(PdfPTable table, String text, BaseColor color) {
		PdfPCell cell= new PdfPCell(new Phrase(text));
		cell.setBackgroundColor(color);
		table.addCell(cell);
	}
	
	private void addTableHeader(PdfPTable table) {
		String[] hearders = new String[]{"Time Stamp", "Responsável", "Componente",
									"Detalhe", "Unidade", "Campo", "De", "Para"};
		PdfPCell cell;
		for(String header: hearders) {
			cell= new PdfPCell(new Phrase(header));
			cell.setBackgroundColor(new BaseColor(236, 244, 254));
			table.addCell(cell);
		}
	}
}
