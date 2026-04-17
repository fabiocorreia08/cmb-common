package br.gov.cmb.common.builder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Zip files builder
 *
 * Author: Paulo Neves (24/05/2017)
 */
public class ZipBuilder {
    private ByteArrayOutputStream fos = new ByteArrayOutputStream();
    private ZipOutputStream zipOut = new ZipOutputStream(fos);

    private ZipBuilder() {

    }

    public static ZipBuilder getInstance() {
        return new ZipBuilder();
    }

    public ZipBuilder addEntry(String filename, byte[] fileBytes) throws IOException {
        ZipEntry zipEntry = new ZipEntry(filename);
        zipOut.putNextEntry(zipEntry);

        zipOut.write(fileBytes, 0, fileBytes.length);

        return this;
    }

    public byte[] build() throws IOException {
        zipOut.close();
        return fos.toByteArray();
    }
}
