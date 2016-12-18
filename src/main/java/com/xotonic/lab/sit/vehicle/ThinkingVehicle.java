package com.xotonic.lab.sit.vehicle;

/**
 * ТС с ИИ
 */
public abstract class ThinkingVehicle extends Vehicle {

    private final AI.Input input = new AI.Input();
    /** Текущий ИИ */
    private AI ai;
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
        input.areaHeight = world.getAreaHeight() - 64.f;
        input.areaWidth = world.getAreaWidth() - 115.f;
        input.timestep = world.getTimeMillis() - lastUpdated;
        input.me = this;
        lastUpdated = world.getTimeMillis();

        output = ai.think(input);

        setX(output.x);
        setY(output.y);
    }
}
