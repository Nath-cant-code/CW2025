# This is my COMP2042 Developing Maintainable Software Coursework
My task for this coursework is to refactor and extend the classic retro game, Tetris. The changes mentioned here are a brief summary of the commits I have throughout the progress of this coursework. The changes are not in any order, but ordered as so for better readability.

## New Java Classes
#### InputAction.java (Interface)

#### InputHandler.java > com.comp2042.input
- Extracted from intialize() in GuiController.
- Handles user input.
- Increases cohesion of GuiController.

#### KeyBindingManager.java
- Creates a hashmap of keystrokes binded to actions in the game. Split from GuiController.
	
#### BoardRenderer.java
- Extracted logic from initGameView() in GuiController.
- Renders the playable area when the program first runs.
- Separates visual logic from GuiController, hence increasing cohesion.
- This class applies SOLID: Single Responsibility Principle.

#### BrickRenderer.java
- Extracted logic from initGameView() in GuiController.
- Renders a Tetromino spawn box.
- Increases cohesion of GuiController.
- This class applies SOLID: Single Responsibility Principle.

#### Refresh.java (Deprecated)
- Extracted multiple methods from GuiController.
- Handles visual refreshes of Tetrominoes and game background of placed blocks.
- Includes colour selection and colour filling of blocks.
- Increases cohesion of GuiController.
- This class does not fully follow SOLID: Single Responsibility Principle, hence it is deprecated and split into more classes.

#### GameTimeLine.java
- Extracted logic from initGameView() in GuiController.
- Handles the natural falling of Tetrominoes according to the TimeLine object it creates.
- Increases cohesion of GuiController.
- This class applies SOLID: Single Responsibility Principle.

#### RotationDirection.java (Enum)
- Declares either a CLOCKWISE or ANTI_CLOCKWISE type.
- Makes rotation methods cleaner and more readable.

#### AbtractBrick.java (Abstract class)
- Implements Brick, extended by brick_shape_classes.
- Extracted getShapeMatrix() from all the brick_shape_classes.
- Increases cohesion of all brick_shape_classes as they all no longer have getShapeMatrix().
- This class applies SOLID: Single Responsibility Principle.

#### BrickType.java (Enum)
- Holds letters that represent the brick_shape_classes.
- Makes it so that brick_shape_objects are more cleanly created.

#### BrickFactory.java (pun intended)
- Processes the type of brick_shape_object selected and cleanly creates the corresponding brick object
- This class applies SOLID: Single Responsibility Principle.
- This class applies Creational Design Pattern: Factory.

## Modified Java Classes
#### GuiController.java
- Extracted user input handling from initialize() > InputHandler, KeyBindingManager
- Extracted board rendering logic from initGameView() > BoardRenderer
- Extracted Tetromino rendering point logic from InitGameView() > BrickRenderer
- Extracted visual refresh methods > Refresh (Deprecated) > 
- Extracted timeline handling > GameTimeLine

#### GameController.java
- Replaced onRotateEvent() with onRotateClock() and onRotateAntiClock(). Same functionality: calls respective rotational method via SimpleBoard object.

#### SimpleBoard.java
- Directional methods for brick_shape_objects were identical. Hence, the logic was extracted and a new method, moveBrick() was created. moveBrickDown(), moveBrickLeft(), and moveBrickRight() now call moveBrick() by passing their individual directions.
- New methods rotateBrickRight() for clockwise rotation and rotateBrickLeft for anti-clockwise rotation.
- Replaced rotateLeftBrick() with rotateBrick(), which passes the directional enum from rotateBrickRight() and rotateBrickLeft() to rotation methods in BrickRotator. 

#### BrickRotator.java
- Used to only have getNextShape(), which is a clockwise rotation of the brick_shape_object.
- Renamed it to nextClockRotation(), same logic.
- Created a new method, nextAntiClockRotation(), using my own logic to traverse the orientation List of the brick_shape_classes backwards.
- Created new method nextRotation() to determine which rotation method to call.

#### EventType.java
- Added ROTATE_CLOCK and ROTATE_ANTICLOCK as new types.

#### brick_shape_classes
- Classes now extends AbstractBrick instead of implementing Brick
- Extracted getShapeMatrix() from all classes > AbstractBrick
- This class applies SOLID: Single Responsibility Principle.
- Reordered JBrick and TBrick orientations in their respective List to be clockwise from start to end of List.

#### RandomBrickGenerator.java
- Extracted brick_shape_class object creation logic > BrickFactory
- Only handles brick_shape_object randomisation and returns brick_shape_object created by BrickFactory.
- This class applies SOLID: Single Responsibility Principle.
