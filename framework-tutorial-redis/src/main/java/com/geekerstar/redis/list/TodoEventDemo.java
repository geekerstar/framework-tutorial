package com.geekerstar.redis.list;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.ListPosition;

import java.util.List;
import java.util.Random;

/**
 * OA系统的待办事项的管理案例
 */
public class TodoEventDemo {

    private Jedis jedis = new Jedis("127.0.0.1");

    /**
     * 添加待办事项
     * @param todoEvent
     */
    public void addTodoEvent(long userId, String todoEvent) {
        jedis.lpush("todo_event::" + userId, todoEvent);
    }

    /**
     * 分页查询待办事项列表
     * @param userId
     * @param pageNo
     * @param pageSize
     * @return
     */
    public List<String> findTodoEventByPage(long userId, int pageNo, int pageSize) {
        int startIndex = (pageNo - 1) * pageSize;
        int endIndex = pageNo * pageSize - 1;
        return jedis.lrange("todo_event::" + userId, startIndex, endIndex);
    }

    /**
     * 插入待办事项
     */
    public void insertTodoEvent(long userId,
                                ListPosition position,
                                String targetTodoEvent,
                                String todoEvent) {
        jedis.linsert("todo_event::" + userId, position, targetTodoEvent, todoEvent);
    }

    /**
     * 修改一个待办事项
     * @param userId
     * @param index
     * @param updatedTodoEvent
     */
    public void updateTodoEvent(long userId, int index, String updatedTodoEvent) {
        jedis.lset("todo_event::" + userId, index, updatedTodoEvent);
    }

    /**
     * 完成一个待办事项
     * @param userId
     * @param todoEvent
     */
    public void finishTodoEvent(long userId, String todoEvent) {
        jedis.lrem("todo_event::" + userId, 0, todoEvent);
    }

    public static void main(String[] args) throws Exception {
        TodoEventDemo demo = new TodoEventDemo();

        // 添加20个待办事项
        long userId = 2;
        for(int i = 0; i < 20; i++) {
            demo.addTodoEvent(userId, "第" + (i + 1) + "个待办事项");
        }

        // 查询第一页待办事项
        int pageNo = 1;
        int pageSize = 10;
        List<String> todoEventPage = demo.findTodoEventByPage(
                userId, pageNo, pageSize);

        System.out.println("第一次查询第一页待办事项......");
        for(String todoEvent :todoEventPage) {
            System.out.println(todoEvent);
        }

        // 插入一个待办事项
        Random random = new Random();
        int index = random.nextInt(todoEventPage.size());
        String targetTodoEvent = todoEventPage.get(index);

        demo.insertTodoEvent(userId, ListPosition.BEFORE,
                targetTodoEvent, "插入的待办事项");
        System.out.println("在" + targetTodoEvent + "前面插入了一个待办事项");

        // 重新分页查询第一页待办事项
        todoEventPage = demo.findTodoEventByPage(
                userId, pageNo, pageSize);

        System.out.println("第二次查询第一页待办事项......");
        for(String todoEvent :todoEventPage) {
            System.out.println(todoEvent);
        }

        // 修改一个待办事项
        index = random.nextInt(todoEventPage.size());
        demo.updateTodoEvent(userId, index, "修改后的待办事项");

        // 完成一个待办事项
        demo.finishTodoEvent(userId, todoEventPage.get(0));

        // 最后查询一次待办事项
        todoEventPage = demo.findTodoEventByPage(
                userId, pageNo, pageSize);

        System.out.println("第三次查询第一页待办事项......");
        for(String todoEvent :todoEventPage) {
            System.out.println(todoEvent);
        }
    }

}
