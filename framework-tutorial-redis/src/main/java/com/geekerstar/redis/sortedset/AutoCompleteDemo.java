package com.geekerstar.redis.sortedset;

import redis.clients.jedis.Jedis;

import java.util.Date;
import java.util.Set;

/**
 * 自动补全案例
 */
public class AutoCompleteDemo {

    private Jedis jedis = new Jedis("127.0.0.1");

    /**
     * 搜索某个关键词
     * @param keyword
     */
    public void search(String keyword) {
        char[] keywordCharArray = keyword.toCharArray();

        StringBuffer potentialKeyword = new StringBuffer("");

        // 我喜欢学习

        // 我：时间+我喜欢学习
        // 我喜：时间+我喜欢学习

        // 我爱大家
        // 我：时间+我爱大家

        for(char keywordChar : keywordCharArray) {
            potentialKeyword.append(keywordChar);

            jedis.zincrby(
                    "potential_Keyword::" + potentialKeyword.toString() + "::keywords",
                    new Date().getTime(),
                    keyword);
        }
    }

    /**
     * 获取自动补全列表
     * @param potentialKeyword
     * @return
     */
    public Set<String> getAutoCompleteList(String potentialKeyword) {
        return jedis.zrevrange("potential_Keyword::" + potentialKeyword + "::keywords",
                0, 2);
    }

    public static void main(String[] args) throws Exception {
        AutoCompleteDemo demo = new AutoCompleteDemo();

        demo.search("我爱大家");
        demo.search("我喜欢学习Redis");
        demo.search("我很喜欢一个城市");
        demo.search("我不太喜欢玩儿");
        demo.search("我喜欢学习Spark");

        Set<String> autoCompleteList = demo.getAutoCompleteList("我");
        System.out.println("第一次自动补全推荐：" + autoCompleteList);

        autoCompleteList = demo.getAutoCompleteList("我喜");
        System.out.println("第二次自动补全推荐：" + autoCompleteList);
    }

}
