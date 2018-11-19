package com.hewei.lucene;

import com.hewei.utils.File2Documents;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

 /**
 * Lucene 入门程序
 *
 * @author hewei
 * @date 2018/11/19
 */
public class HelloWorld {

    private final String FILE_PATH = "D:\\IDEAProject\\LuceneDemo\\src\\main\\resources\\LuceneDataSource\\lucene.txt";

    private final String INDEX_PATH = "D:\\IDEAProject\\LuceneDemo\\src\\main\\resources\\LuceneDataSource\\";


    /**
     * 创建索引
     *
     * IndexWriter：用来操作（增、删、改）索引库的
     *
     */
    @Test
    public void createIndex() throws IOException {

        // file -> document
        Document doc = File2Documents.file2Document(FILE_PATH);

        // 标准语法分词器
        Analyzer analyzer = new StandardAnalyzer();

//        IndexWriter indexWriter = new IndexWriter(/**/);
//        indexWriter.addDocument(doc);


        // 关闭资源
//        indexWriter.close();
    }

    @Test
    public void search(){
        String queryString = "lucene";
    }
}
