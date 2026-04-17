
package br.gov.cmb.common.web.exporter;

import java.util.Date;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Image;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfWriter;

import br.gov.cmb.common.util.DataUtils;
import br.gov.cmb.common.util.PropertiesUtils;

public class DefaultPdfPageEvent extends PdfPageEventHelper {
	
	private static final String LINHA_1 = "Rua Rene Bittencourt, 371 - Dist. Ind. de Santa Cruz";
	private static final String LINHA_2 = "Rio de Janeiro - RJ - CEP 23565-902";
	private static final String LINHA_3 = "Fone: (21) 2414-2286 - Fax: (21) 2414-2121/2414-2142";
	private static final String LINHA_4 = "Site: www.casadamoeda.gov.br";
	
	private static final int FOOTER_BORDER = 0;
	
	private String tituloRelatorio;
	private Phrase phraseUsuarioLogadoComData;
	private HeaderFooter footer;
	private String nomeUsuarioLogado;
	private int numeroTotalPaginas;
	
	public DefaultPdfPageEvent(String tituloRelatorio, int totalPaginas, String nomeUsuarioLogado) {
		this.tituloRelatorio = tituloRelatorio;
		this.numeroTotalPaginas = totalPaginas;
		this.nomeUsuarioLogado = nomeUsuarioLogado;
		criarTextoUsuarioLogadoComData();
		criarFooter();
	}
	
	public DefaultPdfPageEvent(String tituloRelatorio, String nomeUsuarioLogado) {
		this.tituloRelatorio = tituloRelatorio;
		this.nomeUsuarioLogado = nomeUsuarioLogado;
		criarTextoUsuarioLogadoComData();
		criarFooter();
	}
	
	private void criarTextoUsuarioLogadoComData() {
		Phrase phraseUsuario = new Phrase();
		phraseUsuario.add(new Chunk("Data de extração: " + DataUtils.formatarddMMyyyy(new Date())));
		phraseUsuario.add(new Chunk("                   "));
		phraseUsuario.add(new Chunk("Usuario: " + nomeUsuarioLogado));
		phraseUsuarioLogadoComData = phraseUsuario;
	}
	
	private void criarFooter() {
		Font f = criarFonteFooter();
		Phrase p = criarTextoFooter(f);
		footer = new HeaderFooter(p, false);
		footer.setBorder(FOOTER_BORDER);
	}

	private Font criarFonteFooter() {
		Font f = new Font();
		f.setSize(8);
		f.setStyle(Font.BOLD);
		return f;
	}

	private Phrase criarTextoFooter(Font f) {
		Phrase p = new Phrase("", f);
		p.add(new Chunk(LINHA_1));
		p.add(Chunk.NEWLINE);
		p.add(new Chunk(LINHA_2));
		p.add(Chunk.NEWLINE);
		p.add(new Chunk(LINHA_3));
		p.add(Chunk.NEWLINE);
		p.add(new Chunk(LINHA_4));
		return p;
	}

	
	public PdfPTable criarTableHeaderDefault(Document document) throws Exception {
		PdfPTable tab = new PdfPTable(3);
		tab.setSpacingAfter(20f);
		tab.setWidthPercentage(100f);
		tab.setWidths(new float[] { 50f, 100f, 50f });

		String caminhoLogo = String.format("%s/%s", PropertiesUtils.getProperty("caminho.template"), "/STE/ost/logo.jpg");
		Image imagem = Image.getInstance(caminhoLogo);
		imagem.scalePercent(25, 25);

		PdfPCell cell1 = new PdfPCell(imagem);
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell1.setFixedHeight(50f);

		Font f = new Font();
		f.setSize(20);
		f.setStyle(Font.BOLD);
		Phrase phrase = new Phrase(tituloRelatorio, f);
		PdfPCell cell2 = new PdfPCell(phrase);
		cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);

		PdfPCell cell3 = new PdfPCell(new Phrase("Folha " + document.getPageNumber() + "/" + numeroTotalPaginas));
		cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);

		tab.addCell(cell1);
		tab.addCell(cell2);
		tab.addCell(cell3);
		return tab;
	}
	
	@Override
	public void onStartPage(PdfWriter writer, Document document) {
		try {
			PdfPTable tabelaHeader = criarTableHeaderDefault(document);
			document.add(tabelaHeader);
			document.setFooter(footer);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getNumeroTotalPaginas() {
		return numeroTotalPaginas;
	}

	public void setNumeroTotalPaginas(int numeroTotalPaginas) {
		this.numeroTotalPaginas = numeroTotalPaginas;
	}

	public HeaderFooter getFooter() {
		return footer;
	}

	public void setFooter(HeaderFooter footer) {
		this.footer = footer;
	}

	public String getTituloRelatorio() {
		return tituloRelatorio;
	}

	public void setTituloRelatorio(String tituloRelatorio) {
		this.tituloRelatorio = tituloRelatorio;
	}

	public Phrase getPhraseUsuarioLogadoComData() {
		return phraseUsuarioLogadoComData;
	}

	public void setPhraseUsuarioLogadoComData(Phrase phraseUsuarioLogadoComData) {
		this.phraseUsuarioLogadoComData = phraseUsuarioLogadoComData;
	}

	public String getNomeUsuarioLogado() {
		return nomeUsuarioLogado;
	}

	public void setNomeUsuarioLogado(String nomeUsuarioLogado) {
		this.nomeUsuarioLogado = nomeUsuarioLogado;
	}
	
}
