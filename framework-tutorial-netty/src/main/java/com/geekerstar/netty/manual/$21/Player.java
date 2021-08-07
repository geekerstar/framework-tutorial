package com.geekerstar.netty.manual.$21;


import io.netty.util.internal.ObjectPool;

public class Player {

    private static final ObjectPool<Player> RECYCLER = ObjectPool.newPool(
            handle -> new Player(handle));

    private Long id;
    private String name;
    // ...

    private ObjectPool.Handle<Player> handle;

    private Player(ObjectPool.Handle<Player> handle) {
        this.handle = handle;
    }

    public static Player newInstance(Long id, String name) {
        Player player = RECYCLER.get();
        player.id = id;
        player.name = name;
        return player;
    }

    public void offline() {
        handle.recycle(this);
    }

    @Override
    public String toString() {
        return String.format("id=%d, name=%s, classId=%s", id, name, super.toString());
    }
}
