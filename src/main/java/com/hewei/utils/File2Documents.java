package com.hewei.utils;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexOptions;

import java.io.*;

/**
 * @author hewei
 * @date 2018/11/19
 */
public class File2Documents {

    public static Document file2Document(String path){
        File file = new File(path);

        // 文档信息包含：文件名（标题）、内容、路径、大小
        Document doc = new Document();
        doc.add(new TextField("fileName", file.getName(), Field.Store.YES));
        doc.add(new TextField("content", readToString(file), Field.Store.YES));
        doc.add(new TextField("filePath", file.getAbsolutePath(), Field.Store.YES));
        doc.add(new TextField("fileSize", String.valueOf(file.length()), Field.Store.YES));

        return doc;
    }

    private static String readToString(File file) {
        StringBuffer buffer = new StringBuffer();
        try {
            BufferedReader buff = new BufferedReader(new FileReader(file));
            String line = null;
            while ((line = buff.readLine()) != null) {
                buffer.append(line + "\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return buffer.toString();
    }

    public static File Document2File(Document document) {
        return null;
    }
}
