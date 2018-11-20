package com.hewei.lucene;

import com.hewei.utils.File2Documents;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Paths;

/**
 * Lucene 入门程序
 *
 * @author hewei
 * @date 2018/11/19
 */
public class HelloWorld {

    private final String FILE_PATH = "D:\\IDEAProject\\LuceneDemo\\src\\main\\resources\\LuceneDataSource\\lucene.txt";

    private final String INDEX_PATH = "D:\\IDEAProject\\LuceneDemo\\src\\main\\resources\\LuceneIndex\\";


    /**
     * 创建索引
     *
     * IndexWriter：用来操作（增、删、改）索引库的
     *
     */
    @Test
    public void createIndex() throws IOException {
        // 标准语法分词器
        Analyzer analyzer = new StandardAnalyzer();
        // 索引存放目录
        Directory directory = FSDirectory.open(Paths.get(INDEX_PATH));
        // 索引配置对象
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        // 索引操作对象
        IndexWriter indexWriter = new IndexWriter(directory, config);
        // file -> document
        Document doc = File2Documents.file2Document(FILE_PATH);
        indexWriter.addDocument(doc);
        indexWriter.commit();
        // 关闭资源
        indexWriter.close();
    }

    @Test
    public void search() throws Exception {
        String queryString = "core";
        Directory directory = FSDirectory.open(Paths.get(INDEX_PATH));
        DirectoryReader reader = DirectoryReader.open(directory);
        // 索引搜索器
        IndexSearcher searcher = new IndexSearcher(reader);
        Analyzer analyzer = new StandardAnalyzer();
        QueryParser parser = new QueryParser("content", analyzer);
        Query query = parser.parse(queryString);
        long startTime = System.currentTimeMillis();
        // 搜索10条
        TopDocs docs = searcher.search(query, 10);
        // 打印时间、记录数
        System.out.println("查找“" + queryString + "”用时" + Double.valueOf(System.currentTimeMillis() - startTime) / 1000 + "秒");
        System.out.println("查询到" + docs.totalHits + "条记录");

        //加入高亮显示的
        SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter("<b><font color=red>", "</font></b>");
        //计算查询结果最高的得分
        QueryScorer scorer = new QueryScorer(query);
        //根据得分算出一个片段
        Fragmenter fragmenter = new SimpleSpanFragmenter(scorer);
        Highlighter highlighter = new Highlighter(simpleHTMLFormatter, scorer);
        //设置显示高亮的片段
        highlighter.setTextFragmenter(fragmenter);

        for (ScoreDoc scoreDoc : docs.scoreDocs) {
            Document doc = searcher.doc(scoreDoc.doc);
            String content = doc.get("content");
            if (content != null) {
                TokenStream tokenStream = analyzer.tokenStream("content", new StringReader(content));
                String summary = highlighter.getBestFragment(tokenStream, content);
                System.out.println(summary);
            }
        }

        // 关闭资源
        reader.close();
    }
}
