package br.gov.cmb.common.web.exporter;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.el.MethodExpression;
import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.component.UIPanel;
import javax.faces.component.html.HtmlCommandButton;
import javax.faces.component.html.HtmlCommandLink;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.component.api.DynamicColumn;
import org.primefaces.component.api.UIColumn;
import org.primefaces.component.columngroup.ColumnGroup;
import org.primefaces.component.datalist.DataList;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.component.outputpanel.OutputPanel;
import org.primefaces.component.row.Row;
import org.primefaces.component.subtable.SubTable;
import org.primefaces.expression.SearchExpressionFacade;
import org.primefaces.extensions.component.exporter.PDFExporter;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfWriter;

public class CustomPDFExporterExtensions extends PDFExporter {
	
	
	private int alinhamentoHeadersTable = Element.ALIGN_LEFT;
	private int alinhamentoHeadersSubTable = Element.ALIGN_CENTER;
	private int alinhamentoRegistrosTable = Element.ALIGN_LEFT;
	
    private Font facetFont;
    private Color facetBackground;
    private int datasetPadding = 3;
    
	private float[] columnWidths;
	private float tableRelativeWidth;
	private boolean rotatePage = false;
	private int headerAllPages = 0;
	private int headerCellTableBorder = 15;
	private int subTableCellHeaderBorder = 15;
	private int subTableCellRegistrosBorder = 15;
	private int iterador = 0;
    private int totalPaginas = 0;
    
    private DefaultPdfPageEvent defaultPdfPageEvent;
	

	public CustomPDFExporterExtensions() {
		this.skipComponents = "";
		
	}
	
	public CustomPDFExporterExtensions columnWidths(float[] columWidths) {
		this.columnWidths = columWidths;
		return this;
	}
	
	public CustomPDFExporterExtensions comPdfPageEvent(DefaultPdfPageEvent pageEvent) {
		this.defaultPdfPageEvent = pageEvent;
		return this;
	}
	
	public CustomPDFExporterExtensions tableRelativeWidth(float relativeWidth){
		this.tableRelativeWidth = relativeWidth;
		return this;
	}
	
	public CustomPDFExporterExtensions rotatePage(){
		this.rotatePage = true;
		return this;
	}
	
	public CustomPDFExporterExtensions headerAllPages(){
		this.headerAllPages = 1;
		return this;
	}
	
	public CustomPDFExporterExtensions celulasRegistrosSemBorda(){
		subTableCellRegistrosBorder = 0;
		return this;
	}
	
	public CustomPDFExporterExtensions alinhamentoHeadersTable(int alinhamentoHeadersTable) {
		this.alinhamentoHeadersTable = alinhamentoHeadersTable;
		return this;
	}
	
	public CustomPDFExporterExtensions alinhamentoHeadersSubTable(int alinhamentoHeadersSubTable) {
		this.alinhamentoHeadersSubTable = alinhamentoHeadersSubTable;
		return this;
	}
	
	public CustomPDFExporterExtensions alinhamentoRegistrosTable(int alinhamentoRegistrosTable) {
		this.alinhamentoRegistrosTable = alinhamentoRegistrosTable;
		return this;
	}
	
	public CustomPDFExporterExtensions celulasHeaderTableSemBorda(){
		headerCellTableBorder = 0;
		return this;
	}
	
	public CustomPDFExporterExtensions celulasHeaderSubtableSemBorda(){
		subTableCellHeaderBorder = 0;
		return this;
	}
	
	private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
	
	@Override
	public void export(ActionEvent event, String tableId, FacesContext context, String filename, String tableTitle, boolean pageOnly, boolean selectionOnly, String encodingType, MethodExpression preProcessor, MethodExpression postProcessor, boolean subTable) throws IOException {
        try {
            Document document = new Document();
            this.facetFont = FontFactory.getFont(FontFactory.TIMES, encodingType, Font.DEFAULTSIZE, Font.BOLD);
            if(rotatePage){
            	document.setPageSize(PageSize.A4.rotate());
            } else {
            	document.setPageSize(PageSize.A4);
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfWriter pw = PdfWriter.getInstance(document, baos);
            if(defaultPdfPageEvent != null) {
            	defaultPdfPageEvent.setNumeroTotalPaginas(totalPaginas);
            	pw.setPageEvent(defaultPdfPageEvent);
            	document.setFooter(defaultPdfPageEvent.getFooter());
            }
            StringTokenizer st = new StringTokenizer(tableId, ",");
            while (st.hasMoreElements()) {
                String tableName = (String) st.nextElement();
                UIComponent component = SearchExpressionFacade.resolveComponent(context, event.getComponent(), tableName);
                if (component == null) {
                    throw new FacesException("Cannot find component \"" + tableName + "\" in view.");
                }
                if (!(component instanceof DataTable || component instanceof DataList)) {
                    throw new FacesException("Unsupported datasource target:\"" + component.getClass().getName() + "\", exporter must target a PrimeFaces DataTable/DataList.");
                }

                if (!document.isOpen()) {
                    document.open();
                }
                if(defaultPdfPageEvent != null)
                	document.add(defaultPdfPageEvent.getPhraseUsuarioLogadoComData());
                if (tableTitle != null && !tableTitle.isEmpty() && !tableId.contains("" + ",")) {

                    Font tableTitleFont = FontFactory.getFont(FontFactory.TIMES, encodingType, Font.DEFAULTSIZE, Font.BOLD);
                    Paragraph title = new Paragraph(tableTitle, tableTitleFont);
                    document.add(title);

                    Paragraph preface = new Paragraph();
                    addEmptyLine(preface, 3);
                    document.add(preface);
                }
                PdfPTable pdf;
                DataList list = null;
                DataTable table = null;
                if (component instanceof DataList) {
                    list = (DataList) component;
                    pdf = exportPDFTable(context, list, pageOnly, encodingType);
                } else {
                    table = (DataTable) component;
                    pdf = exportPDFTable(context, table, pageOnly, selectionOnly, encodingType, subTable);
                }

                if (pdf != null) {
                    document.add(pdf);
                }
                // add a couple of blank lines
                Paragraph preface = new Paragraph();
                addEmptyLine(preface, datasetPadding);
                document.add(preface);
            }
            document.close();
            PdfReader pr = new PdfReader(baos.toByteArray());
            if(iterador==1){
            	writePDFToResponse(context.getExternalContext(), baos, filename);
            	iterador = 0;
            }else{
            	iterador = 1;
            	totalPaginas = pr.getNumberOfPages();
            	export(event, tableId, context, filename, tableTitle, pageOnly, selectionOnly, encodingType, preProcessor, postProcessor, subTable);
            }


        } catch (DocumentException e) {
            throw new IOException(e.getMessage());
        }
    }
	
	protected void tableFacet(FacesContext context, PdfPTable pdfTable, SubTable table, int columnCount, String facetType) {
        Map<String, UIComponent> map = table.getFacets();
        UIComponent component = map.get(facetType);
        if (component != null) {
            String headerValue = null;
            if (component instanceof HtmlCommandButton) {
                headerValue = exportValue(context, component);
            } else if (component instanceof HtmlCommandLink) {
                headerValue = exportValue(context, component);
            } else if (component instanceof UIPanel || component instanceof OutputPanel) {
                String header = "";
                for (UIComponent child : component.getChildren()) {
                    headerValue = exportValue(context, child);
                    header = header + headerValue;
                }
                PdfPCell cell = new PdfPCell(new Paragraph((header), this.facetFont));
                if (facetBackground != null) {
                    cell.setBackgroundColor(facetBackground);
                }
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                //addColumnAlignments(component,cell);
                cell.setColspan(columnCount);
                pdfTable.addCell(cell);
                pdfTable.completeRow();
                return;
            } else {
                headerValue = exportFacetValue(context, component);
            }
            PdfPCell cell = new PdfPCell(new Paragraph((headerValue), this.facetFont));
            if (facetBackground != null) {
                cell.setBackgroundColor(facetBackground);
            }
            // addColumnAlignments(component,cell);
            cell.setColspan(columnCount);
            //CELULA DOS HEADERS DA TABLE (COM SUBTABLE)
            cell.setHorizontalAlignment(alinhamentoHeadersTable);
            cell.setBorder(headerCellTableBorder);
            pdfTable.addCell(cell);
            pdfTable.completeRow();

        }
    }
	
	@Override
	protected void tableColumnGroup(PdfPTable pdfTable, SubTable table, String facetType) {
        ColumnGroup cg = table.getColumnGroup(facetType);
        List<UIComponent> headerComponentList = null;
        if (cg != null) {
            headerComponentList = cg.getChildren();
        }
        if (headerComponentList != null)
            for (UIComponent component : headerComponentList) {
                if (component instanceof Row) {
                    Row row = (Row) component;
                    for (UIComponent rowComponent : row.getChildren()) {
                        UIColumn column = (UIColumn) rowComponent;
                        String value = null;
                        if (facetType.equalsIgnoreCase("header")) {
                            value = column.getHeaderText();
                        } else
                            value = column.getFooterText();
                        int rowSpan = column.getRowspan();
                        int colSpan = column.getColspan();
                        PdfPCell cell = new PdfPCell(new Paragraph(value, this.facetFont));
                        if (facetBackground != null) {
                            cell.setBackgroundColor(facetBackground);
                        }
                        if (rowSpan > 1) {
                            cell.setVerticalAlignment(Element.ALIGN_CENTER);
                            cell.setRowspan(rowSpan);

                        }
                        if (colSpan > 1) {
                            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            cell.setColspan(colSpan);

                        }
                        // addColumnAlignments(component,cell);
                        if (facetType.equalsIgnoreCase("header")) {
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        }
                        //CELULAS DOS HEADERS DA SUBTABLE
                        cell.setHorizontalAlignment(alinhamentoHeadersSubTable);
                        cell.setBorder(subTableCellHeaderBorder);
                        pdfTable.addCell(cell);

                    }
                }


            }
        pdfTable.completeRow();

    }

	@Override
	protected void addColumnValue(PdfPTable pdfTable, List<UIComponent> components, Font font,String columnType) {
        StringBuilder builder = new StringBuilder();
        pdfTable.setHeaderRows(headerAllPages);
        if(tableRelativeWidth != 0f)
		pdfTable.setWidthPercentage(tableRelativeWidth);
        
        for (UIComponent component : components) {
            if (component.isRendered()) {
                String value = exportValue(FacesContext.getCurrentInstance(), component);

                if (value != null) {
                    builder.append(value);
                }
            }
        }
        PdfPCell cell = new PdfPCell(new Paragraph(builder.toString(), font));
        for (UIComponent component : components) {
        cell=addColumnAlignments(component, cell);
        }
        if(columnType.equalsIgnoreCase("header")){
            for (UIComponent component : components) {
                cell=addFacetAlignments(component, cell);
            }
        }
        //CELULAS DOS REGISTROS DA SUBTABLE OU TABLE
        cell.setHorizontalAlignment(alinhamentoRegistrosTable);
        cell.setBorder(subTableCellRegistrosBorder);
        pdfTable.addCell(cell);
    }
	
	@Override
	protected void addColumnValue(PdfPTable pdfTable, UIComponent component, Font font,String columnType) {
        String value = component == null ? "" : exportValue(FacesContext.getCurrentInstance(), component);
        PdfPCell cell = new PdfPCell(new Paragraph(value, font));

        if (facetBackground != null) {
            cell.setBackgroundColor(facetBackground);
        }
        if(columnType.equalsIgnoreCase("header")){
            cell=addFacetAlignments(component, cell);
        }
        else{
            cell=addColumnAlignments(component, cell);
        }
        //ALINHAMENTO DO HEADER DA TABELA (SEM SUBTABLE)
        cell.setHorizontalAlignment(alinhamentoHeadersTable);
        pdfTable.addCell(cell);
    }
	
	@Override
	protected void addColumnFacets(DataTable table, PdfPTable pdfTable, ColumnType columnType) {
        for (UIColumn col : table.getColumns()) {

            if (col instanceof DynamicColumn) {
                ((DynamicColumn) col).applyStatelessModel();
            }
            PdfPCell cell = null;
            if (col.isRendered() && col.isExportable()) {
                if (col.getHeaderText() != null && columnType.name().equalsIgnoreCase("header")) {
                    cell = new PdfPCell(new Paragraph(col.getHeaderText(), this.facetFont));
                    if (facetBackground != null) {
                        cell.setBackgroundColor(facetBackground);
                    }
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    pdfTable.addCell(cell);
                } else if (col.getFooterText() != null && columnType.name().equalsIgnoreCase("footer")) {
                    cell = new PdfPCell(new Paragraph(col.getFooterText(), this.facetFont));
                    if (facetBackground != null) {
                        cell.setBackgroundColor(facetBackground);
                    }
                    pdfTable.addCell(cell);
                } else {
                    addColumnValue(pdfTable, col.getFacet(columnType.facet()), this.facetFont,columnType.name());
                }
            }
        }
    }

	@Override
    protected void addColumnFacets(SubTable table, PdfPTable pdfTable, ColumnType columnType) {
        for (UIColumn col : table.getColumns()) {

            if (col instanceof DynamicColumn) {
                ((DynamicColumn) col).applyStatelessModel();
            }
            PdfPCell cell = null;
            if (col.isRendered() && col.isExportable()) {
                if (col.getHeaderText() != null && columnType.name().equalsIgnoreCase("header")) {
                    cell = new PdfPCell(new Paragraph(col.getHeaderText(), this.facetFont));
                    if (facetBackground != null) {
                        cell.setBackgroundColor(facetBackground);
                    }
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    pdfTable.addCell(cell);
                } else if (col.getFooterText() != null && columnType.name().equalsIgnoreCase("footer")) {
                    cell = new PdfPCell(new Paragraph(col.getFooterText(), this.facetFont));
                    if (facetBackground != null) {
                        cell.setBackgroundColor(facetBackground);
                    }
                    pdfTable.addCell(cell);
                } else {

                    addColumnValue(pdfTable, col.getFacet(columnType.facet()), this.facetFont,columnType.name());
                }
            }
        }
    }
    

	@Override
	protected PdfPCell addColumnAlignments(UIComponent component, PdfPCell cell) {
		if (component instanceof HtmlOutputText) {
			HtmlOutputText output = (HtmlOutputText) component;
			if (output.getStyle() != null && output.getStyle().contains("left")) {
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			}
			if (output.getStyle() != null && output.getStyle().contains("right")) {
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			}
			if (output.getStyle() != null && output.getStyle().contains("center")) {
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			}
		}
		return cell;
	}

	@Override
	protected PdfPCell addFacetAlignments(UIComponent component, PdfPCell cell) {
		if (component instanceof HtmlOutputText) {
			HtmlOutputText output = (HtmlOutputText) component;
			if (output.getStyle() != null && output.getStyle().contains("left")) {
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			} else if (output.getStyle() != null && output.getStyle().contains("right")) {
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			} else {
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			}
		}
		return cell;
	}
	
	@Override
	protected PdfPTable exportPDFTable(FacesContext context, DataTable table, boolean pageOnly, boolean selectionOnly, String encoding, boolean subTable) {
        if (!("-".equalsIgnoreCase(encoding))) {
            createCustomFonts(encoding);
        }
        int columnsCount = getColumnsCount(table);
        if (subTable) {
            return exportPdfWithSubTable(context, table, pageOnly, selectionOnly);
        } else {
            return exportPdf(context, table, pageOnly, selectionOnly, columnsCount);
        }

    }

	private PdfPTable exportPdf(FacesContext context, DataTable table, boolean pageOnly, boolean selectionOnly,
			int columnsCount) {
		PdfPTable pdfTable;
		if (columnsCount == 0)
		    return null;

		pdfTable = new PdfPTable(columnsCount);

		if (table.getHeader() != null) {
		    tableFacet(context, pdfTable, table, columnsCount, "header");
		}

		if (hasHeaderColumn(table)) {
		    addColumnFacets(table, pdfTable, ColumnType.HEADER);
		}
		if (pageOnly) {
		    exportPageOnly(context, table, pdfTable);
		} else if (selectionOnly) {
		    exportSelectionOnly(context, table, pdfTable);
		} else {
		    exportAll(context, table, pdfTable);
		}

		if (table.hasFooterColumn()) {
		    addColumnFacets(table, pdfTable, ColumnType.FOOTER);
		}
		if (table.getFooter() != null) {

		    tableFacet(context, pdfTable, table, columnsCount, "footer");
		}

		table.setRowIndex(-1);

		return pdfTable;
	}

	private PdfPTable exportPdfWithSubTable(FacesContext context, DataTable table, boolean pageOnly,
			boolean selectionOnly) {
		PdfPTable pdfTable;
		int subTableCount = table.getRowCount();
		SubTable subtable = table.getSubTable();
		int subTableColumnsCount = getColumnsCount(subtable);
		pdfTable = new PdfPTable(subTableColumnsCount);
		try {
			pdfTable.setWidths(columnWidths);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		if (table.getHeader() != null) {
		    tableFacet(context, pdfTable, table, subTableColumnsCount, "header");
		}

		tableColumnGroup(pdfTable, table, "header");

		int i = 0;
		while (subTableCount > 0) {

		    subTableCount--;
		    table.setRowIndex(i);
		    i++;
		    subtable = table.getSubTable();

		    if (subtable.getHeader() != null) {
		        tableFacet(context, pdfTable, subtable, subTableColumnsCount, "header");
		    }


		    if (hasHeaderColumn(subtable)) {
		        addColumnFacets(subtable, pdfTable, ColumnType.HEADER);
		    }

		    if (pageOnly) {
		        exportPageOnly(context, table, pdfTable);
		    } else if (selectionOnly) {
		        exportSelectionOnly(context, table, pdfTable);
		    } else {
		        subTableExportAll(context, subtable, pdfTable);
		    }

		    if (hasFooterColumn(subtable)) {

		        addColumnFacets(subtable, pdfTable, ColumnType.FOOTER);
		    }

		    if (subtable.getFooter() != null) {
		        tableFacet(context, pdfTable, subtable, subTableColumnsCount, "footer");
		    }

		    subtable.setRowIndex(-1);
		}

		tableColumnGroup(pdfTable, table, "footer");


		if (table.hasFooterColumn()) {
		    tableFacet(context, pdfTable, table, subTableColumnsCount, "footer");
		}


		return pdfTable;
	}
	
}
