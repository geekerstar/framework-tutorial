package com.geekerstar.elasticsearch.api;

import cn.hutool.json.JSONUtil;
import com.geekerstar.elasticsearch.SearchApplicationTests;
import com.geekerstar.elasticsearch.domain.entity.User;
import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.action.admin.indices.get.GetIndexResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.index.query.TermsQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

/**
 * @author geekerstar
 * @date 2021/9/20 10:00
 * @description
 */
public class HighLevelApiTest extends SearchApplicationTests {

    RestHighLevelClient esClient = new RestHighLevelClient(
            RestClient.builder(new HttpHost("localhost", 9200, "http"))
    );

    @Test
    public void indexCreate() throws IOException {
        // 创建索引
        CreateIndexRequest request = new CreateIndexRequest("users");
        CreateIndexResponse createIndexResponse =
                esClient.indices().create(request, RequestOptions.DEFAULT);
        // 响应状态
        boolean acknowledged = createIndexResponse.isAcknowledged();
        System.out.println("索引操作 ：" + acknowledged);
        esClient.close();
    }

    @Test
    public void indexSearch() throws IOException {
        // 查询索引
        GetIndexRequest getIndexRequest = new GetIndexRequest();
        getIndexRequest.indices("users");
        GetIndexResponse getIndexResponse = esClient.indices().get(getIndexRequest, RequestOptions.DEFAULT);
        // 响应状态
        System.out.println(getIndexResponse.getAliases());
        System.out.println(getIndexResponse.getMappings());
        System.out.println(getIndexResponse.getSettings());
        esClient.close();
    }

    @Test
    public void indexDelete() throws IOException {
        // 查询索引
        DeleteIndexRequest request = new DeleteIndexRequest("users");
        AcknowledgedResponse response = esClient.indices().delete(request, RequestOptions.DEFAULT);
        // 响应状态
        System.out.println(response.isAcknowledged());
        esClient.close();
    }

    @Test
    public void insert() throws IOException {
        // 插入数据
        IndexRequest request = new IndexRequest();
        request.index("user").id("1001");
        User user = new User();
        user.setName("zhangsan");
        user.setAge(30);
        user.setSex("男");
        // 向ES插入数据，必须将数据转换位JSON格式
        String userJson = JSONUtil.toJsonStr(user);
        request.source(userJson, XContentType.JSON);
        IndexResponse response = esClient.index(request, RequestOptions.DEFAULT);
        System.out.println(response.getResult());
        esClient.close();
    }

    @Test
    public void insertBatch() throws IOException {
        // 批量插入数据
        BulkRequest request = new BulkRequest();
        request.add(new IndexRequest().index("user").id("1001").source(XContentType.JSON, "name", "zhangsan", "age", 30, "sex", "男"));
        request.add(new IndexRequest().index("user").id("1002").source(XContentType.JSON, "name", "lisi", "age", 30, "sex", "女"));
        request.add(new IndexRequest().index("user").id("1003").source(XContentType.JSON, "name", "wangwu", "age", 40, "sex", "男"));
        request.add(new IndexRequest().index("user").id("1004").source(XContentType.JSON, "name", "wangwu1", "age", 40, "sex", "女"));
        request.add(new IndexRequest().index("user").id("1005").source(XContentType.JSON, "name", "wangwu2", "age", 50, "sex", "男"));
        request.add(new IndexRequest().index("user").id("1006").source(XContentType.JSON, "name", "wangwu3", "age", 50, "sex", "男"));
        request.add(new IndexRequest().index("user").id("1007").source(XContentType.JSON, "name", "wangwu44", "age", 60, "sex", "男"));
        request.add(new IndexRequest().index("user").id("1008").source(XContentType.JSON, "name", "wangwu555", "age", 60, "sex", "男"));
        request.add(new IndexRequest().index("user").id("1009").source(XContentType.JSON, "name", "wangwu66666", "age", 60, "sex", "男"));
        BulkResponse response = esClient.bulk(request, RequestOptions.DEFAULT);
        System.out.println(response.getTook());
        System.out.println(Arrays.toString(response.getItems()));
        esClient.close();
    }

    @Test
    public void docGet() throws IOException {
        // 查询数据
        GetRequest request = new GetRequest();
        request.index("user").id("1001");
        GetResponse response = esClient.get(request, RequestOptions.DEFAULT);
        System.out.println(response.getSourceAsString());
        esClient.close();
    }

    @Test
    public void docSearch() throws IOException {
        // 1. 查询索引的所有数据
        SearchRequest request = new SearchRequest();
        request.indices("user");
        // 构造查询条件
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(QueryBuilders.matchAllQuery());
        request.source(builder);
        SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);
        SearchHits hits = response.getHits();
        System.out.println(response.getTook());
        System.out.println(hits.getTotalHits());
        for (SearchHit hit : hits) {
            System.out.println(hit.getSourceAsString());
        }
        esClient.close();
    }

    @Test
    public void docUpdate() throws IOException {
        // 修改数据
        UpdateRequest request = new UpdateRequest();
        request.index("user").id("1001");
        request.doc(XContentType.JSON, "sex", "女");
        UpdateResponse response = esClient.update(request, RequestOptions.DEFAULT);
        System.out.println(response.getResult());
        esClient.close();
    }

    @Test
    public void docDelete() throws IOException {
        DeleteRequest request = new DeleteRequest();
        request.index("user").id("1001");
        DeleteResponse response = esClient.delete(request, RequestOptions.DEFAULT);
        System.out.println(response.toString());

    }

    @Test
    public void docDeleteBatch() throws IOException {
        // 批量删除数据
        BulkRequest request = new BulkRequest();
        request.add(new DeleteRequest().index("user").id("1001"));
        request.add(new DeleteRequest().index("user").id("1002"));
        request.add(new DeleteRequest().index("user").id("1003"));
        BulkResponse response = esClient.bulk(request, RequestOptions.DEFAULT);
        System.out.println(response.getTook());
        System.out.println(Arrays.toString(response.getItems()));
        esClient.close();
    }

    @Test
    public void docQuery() throws IOException {
        // 1. 查询索引中全部的数据
        SearchRequest request1 = new SearchRequest();
        request1.indices("user");
        request1.source(new SearchSourceBuilder().query(QueryBuilders.matchAllQuery()));
        SearchResponse response1 = esClient.search(request1, RequestOptions.DEFAULT);
        SearchHits hits1 = response1.getHits();
        System.out.println(hits1.getTotalHits());
        System.out.println(response1.getTook());
        for (SearchHit hit : hits1) {
            System.out.println(hit.getSourceAsString());
        }

        // 2. 条件查询 : termQuery
        SearchRequest request2 = new SearchRequest();
        request2.indices("user");
        request2.source(new SearchSourceBuilder().query(QueryBuilders.termQuery("age", 30)));
        SearchResponse response2 = esClient.search(request2, RequestOptions.DEFAULT);
        SearchHits hits2 = response2.getHits();
        System.out.println(hits2.getTotalHits());
        System.out.println(response2.getTook());
        for (SearchHit hit : hits2) {
            System.out.println(hit.getSourceAsString());
        }

        // 3. 分页查询
        SearchRequest request3 = new SearchRequest();
        request3.indices("user");
        SearchSourceBuilder builder3 = new SearchSourceBuilder().query(QueryBuilders.matchAllQuery());
        // (当前页码-1)*每页显示数据条数
        builder3.from(2);
        builder3.size(2);
        request3.source(builder3);
        SearchResponse response3 = esClient.search(request3, RequestOptions.DEFAULT);
        SearchHits hits3 = response3.getHits();
        System.out.println(hits3.getTotalHits());
        System.out.println(response3.getTook());
        for (SearchHit hit : hits3) {
            System.out.println(hit.getSourceAsString());
        }

        // 4. 查询排序
        SearchRequest request4 = new SearchRequest();
        request4.indices("user");
        SearchSourceBuilder builder4 = new SearchSourceBuilder().query(QueryBuilders.matchAllQuery());
        builder4.sort("age", SortOrder.DESC);
        request4.source(builder4);
        SearchResponse response4 = esClient.search(request4, RequestOptions.DEFAULT);
        SearchHits hits4 = response4.getHits();
        System.out.println(hits4.getTotalHits());
        System.out.println(response4.getTook());
        for (SearchHit hit : hits4) {
            System.out.println(hit.getSourceAsString());
        }

        // 5. 过滤字段
        SearchRequest request5 = new SearchRequest();
        request5.indices("user");
        SearchSourceBuilder builder5 = new SearchSourceBuilder().query(QueryBuilders.matchAllQuery());
        String[] excludes = {"age"};
        String[] includes = {};
        builder5.fetchSource(includes, excludes);
        request5.source(builder5);
        SearchResponse response5 = esClient.search(request5, RequestOptions.DEFAULT);
        SearchHits hits5 = response5.getHits();
        System.out.println(hits5.getTotalHits());
        System.out.println(response5.getTook());
        for (SearchHit hit : hits5) {
            System.out.println(hit.getSourceAsString());
        }

        // 6. 组合查询
        SearchRequest request6 = new SearchRequest();
        request6.indices("user");
        SearchSourceBuilder builder6 = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder6 = QueryBuilders.boolQuery();
        //boolQueryBuilder6.must(QueryBuilders.matchQuery("age", 30));
        //boolQueryBuilder6.must(QueryBuilders.matchQuery("sex", "男"));
        //boolQueryBuilder6.mustNot(QueryBuilders.matchQuery("sex", "男"));
        boolQueryBuilder6.should(QueryBuilders.matchQuery("age", 30));
        boolQueryBuilder6.should(QueryBuilders.matchQuery("age", 40));
        builder6.query(boolQueryBuilder6);
        request6.source(builder6);
        SearchResponse response6 = esClient.search(request6, RequestOptions.DEFAULT);
        SearchHits hits6 = response6.getHits();
        System.out.println(hits6.getTotalHits());
        System.out.println(response6.getTook());
        for (SearchHit hit : hits6) {
            System.out.println(hit.getSourceAsString());
        }

        // 7. 范围查询
        SearchRequest request7 = new SearchRequest();
        request7.indices("user");
        SearchSourceBuilder builder7 = new SearchSourceBuilder();
        RangeQueryBuilder rangeQuery7 = QueryBuilders.rangeQuery("age");
        rangeQuery7.gte(30);
        rangeQuery7.lt(50);
        builder7.query(rangeQuery7);
        request7.source(builder7);
        SearchResponse response7 = esClient.search(request7, RequestOptions.DEFAULT);
        SearchHits hits7 = response7.getHits();
        System.out.println(hits7.getTotalHits());
        System.out.println(response7.getTook());
        for (SearchHit hit : hits7) {
            System.out.println(hit.getSourceAsString());
        }

        // 8. 模糊查询
        SearchRequest request8 = new SearchRequest();
        request8.indices("user");
        SearchSourceBuilder builder8 = new SearchSourceBuilder();
        builder8.query(QueryBuilders.fuzzyQuery("name", "wangwu").fuzziness(Fuzziness.TWO));
        request8.source(builder8);
        SearchResponse response8 = esClient.search(request8, RequestOptions.DEFAULT);
        SearchHits hits8 = response8.getHits();
        System.out.println(hits8.getTotalHits());
        System.out.println(response8.getTook());
        for (SearchHit hit : hits8) {
            System.out.println(hit.getSourceAsString());
        }

        // 9. 高亮查询
        SearchRequest request9 = new SearchRequest();
        request9.indices("user");
        SearchSourceBuilder builder9 = new SearchSourceBuilder();
        TermsQueryBuilder termsQueryBuilder9 = QueryBuilders.termsQuery("name", "zhangsan");
        HighlightBuilder highlightBuilder9 = new HighlightBuilder();
        highlightBuilder9.preTags("<font color='red'>");
        highlightBuilder9.postTags("</font>");
        highlightBuilder9.field("name");
        builder9.highlighter(highlightBuilder9);
        builder9.query(termsQueryBuilder9);
        request9.source(builder9);
        SearchResponse response9 = esClient.search(request9, RequestOptions.DEFAULT);
        SearchHits hits9 = response9.getHits();
        System.out.println(hits9.getTotalHits());
        System.out.println(response9.getTook());
        for (SearchHit hit : hits9) {
            System.out.println(hit.getSourceAsString());
        }

        // 10. 聚合查询
        SearchRequest request10 = new SearchRequest();
        request10.indices("user");
        SearchSourceBuilder builder10 = new SearchSourceBuilder();
        AggregationBuilder aggregationBuilder = AggregationBuilders.max("maxAge").field("age");
        builder10.aggregation(aggregationBuilder);
        request10.source(builder10);
        SearchResponse response10 = esClient.search(request10, RequestOptions.DEFAULT);
        SearchHits hits10 = response10.getHits();
        System.out.println(hits10.getTotalHits());
        System.out.println(response10.getTook());
        for (SearchHit hit : hits10) {
            System.out.println(hit.getSourceAsString());
        }

        // 11. 分组查询
        SearchRequest request11 = new SearchRequest();
        request11.indices("user");
        SearchSourceBuilder builder11 = new SearchSourceBuilder();
        AggregationBuilder aggregationBuilder11 = AggregationBuilders.terms("ageGroup").field("age");
        builder11.aggregation(aggregationBuilder11);
        request11.source(builder11);
        SearchResponse response11 = esClient.search(request11, RequestOptions.DEFAULT);
        SearchHits hits11 = response11.getHits();
        System.out.println(hits11.getTotalHits());
        System.out.println(response11.getTook());
        for (SearchHit hit : hits11) {
            System.out.println(hit.getSourceAsString());
        }
        esClient.close();
    }
}
