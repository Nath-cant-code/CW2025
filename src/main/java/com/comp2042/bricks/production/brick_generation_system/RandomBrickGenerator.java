package com.comp2042.bricks.production.brick_generation_system;

import com.comp2042.bricks.production.blueprints.Brick;
import com.comp2042.bricks.production.industrial_factory.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This class manages random sequential generation of Brick-shape-objects, popping, and peeking from said sequence (Deque).
 */
public class RandomBrickGenerator implements BrickGenerator {
    /**
     * Deque: A linear collection that supports element insertion and removal at both ends.<br>
     * Holds a random sequence of Brick-shape-objects.
     */
    private final Deque<Brick> nextBricks = new ArrayDeque<>();
    private final BrickFactory factory = new BrickFactory();
    /**
     * This field is to hold all the types of Brick-shape-objects and "list" them out for random selection later on.
     */
    private final BrickType[] brickList = BrickType.values();

    /**
     * One type of Brick-shape-object is chosen from the list and added onto the Deque (this process happens twice). <br>
     * The Deque will then become a random sequence of Brick-shape-objects, which will be
     * referred to when generating a new brick on the screen. <br>
     * The Deque is referred to by methods of other classes via the getNextBrick() method.
     */
    public RandomBrickGenerator() {
        nextBricks.add(randomBrick());
        nextBricks.add(randomBrick());
        nextBricks.add(randomBrick());
        nextBricks.add(randomBrick());
    }

    /**
     * Factory method:<br>
     * randomly select a Brick-shape-class from BrickType and
     * utilise BrickFactory.java to create Brick-shape-objects.
     * @return  A random Brick-shape-object.
     */
    private Brick randomBrick () {
        BrickType randomisedBrick = brickList[ThreadLocalRandom.current().nextInt(brickList.length)];
        return factory.createBrick(randomisedBrick);
    }

    /**
     * If the Deque (random sequence of Brick-shape-objects) is left with 1 or lesser Brick-shape-objects,
     * randomly select one from the fixed list that contains all types of Brick-shape-objects (brickList)
     * and add it to the Deque.
     * @return The first Brick-shape-object in the Deque and deletes it right after.
     */
    @Override
    public Brick getBrick() {
        if (nextBricks.size() <= 3) {
            nextBricks.add(randomBrick());
        }
        return nextBricks.poll();
    }

    /**
     * @return The first Brick-shape-object in the Deque
     */
    @Override
    public Brick getNextBrick() {
        return nextBricks.peek();
    }

    @Override
    public List<Brick> getUpcomingBricks() { return new ArrayList<>(nextBricks); }
}
