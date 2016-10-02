/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xotonic.lab.sit.vehicle;

import java.util.Random;

public abstract class TimedLuckyFactory extends Factory {

    private static int id = 1;
    int cooldown = 1000;
    private float createChance = 0.5f;
    private Random r = new Random();
    private long time;
    private long prevTimeMillis = 0;
    private int totalCreated = 0;

    TimedLuckyFactory(Habitat habitat) {
        super(habitat);
    }

    public int getTotalCreated() {
        return totalCreated;
    }

    public float getCreateChance() {
        return createChance;
    }

    public void setCreateChance(float createChance) {
        this.createChance = createChance;
    }

    @Override
    public void stop() {
        totalCreated = 0;
        time = 0;
    }

    @Override
    public void start() {
        time = 0;
        prevTimeMillis = 0;
    }

    @Override
    public void update(long timeMillis) {
        if (createChance > 1f | createChance < 0f)
            log.error("Chance value is not in range [0.0;1.0] (now {})", createChance);

        time += timeMillis - prevTimeMillis;
        prevTimeMillis = timeMillis;
        if (time >= cooldown) {
            time -= cooldown;
            if (r.nextFloat() < createChance)
                build();
        }
    }

    protected String getNextId() {
        return String.format("%d", id++);
    }

    protected long getCooldown() {
        return time;
    }

    @Override
    public void build() {
        Vehicle v = create();
        habitat.getVehicles().add(v);
        totalCreated++;
    }
}
