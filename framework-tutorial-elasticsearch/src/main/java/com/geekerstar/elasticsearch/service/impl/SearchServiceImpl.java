package com.geekerstar.elasticsearch.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.geekerstar.elasticsearch.domain.entity.User;
import com.geekerstar.elasticsearch.service.SearchService;
import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.geekerstar.elasticsearch.constant.ElasticSearchConstant.INDEX_TYPE;
import static com.geekerstar.elasticsearch.constant.ElasticSearchConstant.INDEX_USER;

/**
 * @author geekerstar
 * @date 2021/9/21 09:25
 * @description
 */
@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

    private final RestHighLevelClient restHighLevelClient;
    private final ObjectMapper objectMapper;

    @Override
    public String createUserDocument(User user) throws Exception {
        user.setId(UUID.randomUUID().toString());
        IndexRequest indexRequest = new IndexRequest(INDEX_USER, INDEX_TYPE, user.getId())
                .source(convertUserDocumentToMap(user));
        IndexResponse indexResponse = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        return indexResponse.getResult().name();
    }

    @Override
    public User findById(String id) throws Exception {
        GetRequest getRequest = new GetRequest(INDEX_USER, INDEX_TYPE, id);
        GetResponse getResponse = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
        Map<String, Object> resultMap = getResponse.getSource();
        return convertMapToUserDocument(resultMap);
    }

    @Override
    public String updateUser(User user) throws Exception {
        User resultUser = findById(user.getId());
        UpdateRequest updateRequest = new UpdateRequest(
                INDEX_USER,
                INDEX_TYPE,
                resultUser.getId());
        updateRequest.doc(convertUserDocumentToMap(user));
        UpdateResponse updateResponse = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
        return updateResponse
                .getResult()
                .name();
    }

    @Override
    public List<User> findAll() throws Exception {
        SearchRequest searchRequest = buildSearchRequest(INDEX_USER, INDEX_TYPE);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse =
                restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        return getSearchResult(searchResponse);
    }

    @Override
    public List<User> findUserByName(String name) throws Exception {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(INDEX_USER);
        searchRequest.types(INDEX_TYPE);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        MatchQueryBuilder matchQueryBuilder = QueryBuilders
                .matchQuery("name", name)
                .operator(Operator.AND);
        searchSourceBuilder.query(matchQueryBuilder);
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse =
                restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        return getSearchResult(searchResponse);
    }

    @Override
    public String deleteUserDocument(String id) throws Exception {
        DeleteRequest deleteRequest = new DeleteRequest(INDEX_USER, INDEX_TYPE, id);
        DeleteResponse response = restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
        return response
                .getResult()
                .name();
    }

    private Map<String, Object> convertUserDocumentToMap(User user) {
        Map<String, Object> map = Maps.newHashMap();
        BeanUtil.copyProperties(user, map);
        return map;
    }

    private User convertMapToUserDocument(Map<String, Object> map) {
        User user = new User();
        BeanUtil.copyProperties(map, user);
        return user;
    }

    @Override
    public List<User> searchByKeyword(String keyword) throws Exception {
        SearchRequest searchRequest = buildSearchRequest(INDEX_USER, INDEX_TYPE);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        QueryBuilder queryBuilder = QueryBuilders
                .boolQuery()
                .must(QueryBuilders
                        .matchQuery("name", keyword));
        searchSourceBuilder.query(queryBuilder);
        searchRequest.source(searchSourceBuilder);
        SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        return getSearchResult(response);
    }

    private List<User> getSearchResult(SearchResponse response) {
        SearchHit[] searchHit = response.getHits().getHits();
        List<User> profileDocuments = new ArrayList<>();
        for (SearchHit hit : searchHit) {
            profileDocuments
                    .add(objectMapper
                            .convertValue(hit
                                    .getSourceAsMap(), User.class));
        }
        return profileDocuments;
    }

    private SearchRequest buildSearchRequest(String index, String type) {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(index);
        searchRequest.types(type);
        return searchRequest;
    }
}
