package com.xotonic.lab.sit.vehicle;

/**
 * ТС с ИИ
 */
public abstract class ThinkingVehicle extends Vehicle {

    /** Текущий ИИ */
    private AI ai;
    private AI.Input input = new AI.Input();
    private AI.Output output = new AI.Output();

    private long lastUpdated = 0;

    public ThinkingVehicle(String id, float x, float y) {
        super(id, x, y);
    }

    public ThinkingVehicle(String id) {
        super(id);
    }

    public AI getAi() {
        return ai;
    }

    public void setAi(AI ai) {
        this.ai = ai;
    }


    public void processAI(World world) {
        input.areaHeight = world.getAreaHeight();
        input.areaWidth = world.getAreaWidth();
        input.timestep = world.getTimeMillis() - lastUpdated;
        input.me = this;
        lastUpdated = world.getTimeMillis();

        output = ai.think(input);

        setX(output.x);
        setY(output.y);
    }
}
