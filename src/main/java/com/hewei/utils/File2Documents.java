package com.hewei.utils;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexOptions;

import java.io.File;

/**
 * @author hewei
 * @date 2018/11/19
 */
public class File2Documents {

    public static Document file2Document(String path){
        File file = new File(path);

        Document doc = new Document();
//        doc.add(new Field("", ""), Field.Store.YES, IndexOptions.);

        return null;
    }

    public static File Document2File(Document document) {
        return null;
    }
}
