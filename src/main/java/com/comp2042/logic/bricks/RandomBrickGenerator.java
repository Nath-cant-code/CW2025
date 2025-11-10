package com.comp2042.logic.bricks;

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
     * This field is to hold all the types of Brick-shape-objects and "list" them out for random selection later on.
     */
    private final List<Brick> brickList;

    /**
     * Deque: A linear collection that supports element insertion and removal at both ends.<br>
     * Holds a random sequence of Brick-shape-objects.
     */
    private final Deque<Brick> nextBricks = new ArrayDeque<>();

    /**
     * This constructor creates and then adds all the types of Brick-shape-objects into brickList.
     * <p>
     *     One type of Brick-shape-object is chosen from the list and added onto the Deque (this process happens twice).
     *     The Deque will then become a random sequence of Brick-shape-objects, which will be
     *     referred to when generating a new brick on the screen.
     * </p>
     * The Deque is referred to by methods of other classes via the getNextBrick() method.
     */
    public RandomBrickGenerator() {
        brickList = new ArrayList<>();
        brickList.add(new IBrick());
        brickList.add(new JBrick());
        brickList.add(new LBrick());
        brickList.add(new OBrick());
        brickList.add(new SBrick());
        brickList.add(new TBrick());
        brickList.add(new ZBrick());
        nextBricks.add(brickList.get(ThreadLocalRandom.current().nextInt(brickList.size())));
        nextBricks.add(brickList.get(ThreadLocalRandom.current().nextInt(brickList.size())));
    }

    /**
     * If the Deque (random sequence of Brick-shape-objects) is left with 1 or lesser Brick-shape-objects,
     * randomly select one from the fixed list that contains all types of Brick-shape-objects (brickList)
     * and add it to the Deque.
     * @return The first Brick-shape-object in the Deque and deletes it right after.
     */
    @Override
    public Brick getBrick() {
        if (nextBricks.size() <= 1) {
            nextBricks.add(brickList.get(ThreadLocalRandom.current().nextInt(brickList.size())));
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
}
