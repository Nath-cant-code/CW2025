# This is my COMP2042 Developing Maintainable Software Coursework
My task for this coursework is to refactor and extend the classic retro game, Tetris. The changes mentioned here are a brief summary of the commits I have throughout the progress of this coursework. 

## New Java Classes
#### InputAction.java (Functional Interface)
- Holds the onEvent methods for keybinding in KeyBindManager and execution in InputHandler.
- Command: Encapsulates the execute() method as an object.
- This class applies <font color=aqua>SOLID: Single Responsibility Principle.</font>
- This class applies <font color="pink">Behavioural Design Pattern: Command.</font>

#### KeyBindingManager.java
- Extracted huge if-else chain from GuiController.
- Creates a hashmap of keystrokes paired with onEvent methods in the game.
- O/C: New keystrokes can be easily added here instead of modifying the huge if-else chain.
- Strategy: Selection of onEvent method via hashmap.
- This class applies <font color=aqua>SOLID: Single Responsibility Principle.</font>
- This class applies <font color=aqua>SOLID: Open/Closed Principle.</font>
- This class applies <font color="pink">Creational Design Pattern: Factory.</font>
- This class applies <font color="pink">Behavioural Design Pattern: Strategy.</font>
	
#### InputHandler.java > com.comp2042.input
- Passes keystroke to KeyBindingManager, retrieves corresponding onEvent method call, executes said onEvent call via InputAction.
- This class applies <font color=aqua>SOLID: Single Responsibility Principle.</font>

#### BoardRenderer.java
- Extracted logic from initGameView() in GuiController.
- Renders the playable area when the program first runs.
- Separates visual logic from GuiController, hence increasing cohesion.
- This class applies <font color=aqua>SOLID: Single Responsibility Principle.</font>

#### BrickRenderer.java
- Extracted logic from initGameView() in GuiController.
- Renders a Tetromino spawn box.
- Increases cohesion of GuiController.
- This class applies <font color=aqua>SOLID: Single Responsibility Principle.</font>

#### Refresh.java (<font color=chocolate>Deprecated</font>)
- Extracted multiple methods from GuiController.
- Handles visual refreshes of Tetrominoes 
- Handles visual refreshes of game background of placed blocks.
- Handles colour selection > ColorSelector
- Handles colour filling of blocks
- Added logic for rendering of held Tetromino in the hold panel.
- Added logic for rendering the Tetrominoes in queue.
- Increases cohesion of GuiController.
- This class does not fully follow <font color="aqua">SOLID: Single Responsibility Principle</font>, hence it is deprecated and split into more classes.

#### RefreshCoordinator.java
- Logic for all the rendering methods have been split to their corresponding classes. 
- Facade: This class contains methods that delegate to the appropriate rendering methods in their respective classes.
- This class applies <font color="pink">Structural Design Pattern: Facade.</font>

#### ColorSelector.java
- Extracted setColorFill() from Refresh.
- Made setColorFill() color selection more readable. 
- This class applies <font color=aqua>SOLID: Single Responsibility Principle.</font>

#### RectangleColorRenderer.java
- Extracted setRectangleData() from Refresh.java > setRectangleColor()
- Fills in the corresponding color for each rendered Tetromino.
- This class applies <font color=aqua>SOLID: Single Responsibility Principle.</font>

#### ActiveBrick_RI (Interface)
- Interface for RefreshActiveBrick.java
- This interface applies <font color=aqua>SOLID: Interface Segregation.</font>

#### RefreshActiveBrick.java
- Extracted refreshBrick() from Refresh.java > refreshActiveBrick()
- Refreshes the currently active (falling) Tetromino.
- This class applies <font color=aqua>SOLID: Single Responsibility Principle.</font>

#### GameBackground_RI (Interface)
- Interface for RefreshGameBackground.java
- This interface applies <font color=aqua>SOLID: Interface Segregation.</font>

#### RefreshGameBackground.java
- Extracted refreshGameBackground() from Refresh.java > refreshBackground()
- Refreshes the background by recolouring every block (pixel) in the playable area.
- This class applies <font color=aqua>SOLID: Single Responsibility Principle.</font>

#### HoldBrick_RI (Interface)
- Interface for RefreshHoldPanel.java
- This interface applies <font color=aqua>SOLID: Interface Segregation.</font>

#### RefreshHoldPanel.java
- Extracted drawHoldBrick() from Refresh.java > refreshHoldBrick()
- Renders and colours in the Tetromino in the hold panel.
- This class applies <font color=aqua>SOLID: Single Responsibility Principle.</font>

#### PreviewBricks_RI (Interface)
- Interface for RefreshPreviewBricks.java
- This interface applies <font color=aqua>SOLID: Interface Segregation.</font>

#### RefreshPreviewBricks.java
- Extracted refreshNextBricks() from Refresh.java > refreshPreviewBricks()
- Renders and colours in the Tetrominoes in the preview panel.
- This class applies <font color=aqua>SOLID: Single Responsibility Principle.</font>

#### GameView.java (Interface)
- Implemented by GuiController.
- Includes a methods already existing in GuiController that are constantly used in other classes like GameController.
- DI: Main.java now creates an object of GameView instead of GuiController, hence the class now depends on an abstraction and not the details.
  - Allows for more flexible and modular code.
- This interface applies <font color=aqua>SOLID: Dependency Inversion.</font>

#### GameStateManager.java
- Extracted BooleanProperty isPause and isGameOver from GuiController
- Wrapper class to handle logic values of isPause and isGameOver.
- This class applies <font color=aqua>SOLID: Single Responsibility Principle.</font>

#### GameTimeLine.java
- Extracted logic from initGameView() in GuiController.
- Handles the natural falling of Tetrominoes according to the TimeLine object it creates.
- Increases cohesion of GuiController.
- This class applies <font color=aqua>SOLID: Single Responsibility Principle.</font>

#### RotationDirection.java (Enum)
- Declares either a CLOCKWISE or ANTI_CLOCKWISE type.
- Makes rotation methods cleaner and more readable.

#### AbstractBrick.java (Abstract class)
- Implements Brick, extended by brick_shape_classes.
- Extracted getShapeMatrix() from all the brick_shape_classes.
- Increases cohesion of all brick_shape_classes as they all no longer have getShapeMatrix().
- This class applies <font color=aqua>SOLID: Single Responsibility Principle.</font>

#### BrickType.java (Enum)
- Holds letters that represent the brick_shape_classes.
- Makes it so that brick_shape_objects are more cleanly created.

#### BrickFactory.java (pun intended)
- Processes the type of brick_shape_object selected and cleanly creates the corresponding brick object
- This class applies SOLID: Single Responsibility Principle.
- This class applies <font color="pink">Creational Design Pattern: Factory.</font>

#### PausePanel.java
- Creates a "PAUSED" label.
- Handles label text change to resuming game text.

## Modified Java Classes
#### GuiController.java
- Implements GameView.java as well.
- Extracted user input handling from initialize() > InputHandler, KeyBindingManager
- Extracted board rendering logic from initGameView() > BoardRenderer
- Extracted Tetromino rendering point logic from InitGameView() > BrickRenderer
- Extracted visual refresh methods > Refresh (Deprecated) > 
- Extracted timeline handling > GameTimeLine
- <font color=red>Extractable</font> Added functionality to pauseGame(), i.e. logic for handling pause panel.
- <font color=red>Extractable</font> Refactored part of pauseGame() to a new method startResumeCountdown() in the same class. Handles game resume countdown logic.
- Added functionality to bindScore(). Displays the current score on the score label.
- Added a setSimpleBoard(). Takes in a SimpleBoard object as argument and sets the class's SimpleBoard object.
  - <font color=red>Extractable</font> Calls the method to render the Tetromino in the hold panel.
- Added initHoldMatrix() to create a box area in the hold panel for the held Tetromino.
- <font color=red>Extractable</font> Added onHoldEvent() to handle calling rendering methods to render the held Tetromino.
- Added initNextPanel() to create a box area in preview panel for the Tetrominoes in queue.
- Extracted BooleanProperty isPause and isGameOver > GameStateManager  
- Added getGameStateManager().

#### GameController.java
- Replaced onRotateEvent() with onRotateClock() and onRotateAntiClock(). Same functionality: calls respective rotational method via SimpleBoard object.
- Added onSnapEvent() to connect InputHandler to snap logic.
- No longer creates objects of GuiController (GameView) or SimpleBoard (Board) > Main.java

#### Main.java
- Added responsibility for creating GameView and Board objects.
- This class applies <font color=aqua>SOLID: Dependency Inversion.</font>

#### SimpleBoard.java
- Directional methods for brick_shape_objects were identical. Hence, the logic was extracted and a new method, moveBrick() was created. 
    - moveBrickDown(), moveBrickLeft(), and moveBrickRight() now call moveBrick() by passing their individual directions.
- New methods rotateBrickRight() for clockwise rotation and rotateBrickLeft for anti-clockwise rotation.
- Replaced rotateLeftBrick() with rotateBrick(), which passes the directional enum from rotateBrickRight() and rotateBrickLeft() to rotation methods in BrickRotator. 
- Added snapBrick(). Merges the Tetromino into the playable area after calling findSnapPosition() in MatrixOperations.
    - Added a score multiplier bonus for hard dropping a Tetromino.
    - Added a score multiplier bonus for clearing a row via hard dropping.
- Added holdBrick() to handle logic for swapping falling Tetromino with held Tetromino.
- Added getNextBricksPreview() to retrieve the Tetrominoes currently in queue and arranges them for display in preview panel.

#### MatrixOperations.java
- Added findSnapPosition(). Iteratively check how far the Tetromino can drop by calling intersect() in the same class.

#### BrickRotator.java
- Used to only have getNextShape(), which is a clockwise rotation of the brick_shape_object.
- Renamed it to nextClockRotation(), same logic.
- Created a new method, nextAntiClockRotation(), using my own logic to traverse the orientation List of the brick_shape_classes backwards.
- Created new method nextRotation() to determine which rotation method to call.
- Added a getBrick() that returns the Brick object.

#### EventType.java
- Added SNAP, ROTATE_CLOCK and ROTATE_ANTICLOCK as new types.

#### brick_shape_classes
- Classes now extends AbstractBrick instead of implementing Brick
- Extracted getShapeMatrix() from all classes > AbstractBrick
- Reordered JBrick and TBrick orientations in their respective List to be clockwise from start to end of List.
- This class applies <font color=aqua>SOLID: Single Responsibility Principle.</font>

#### BrickGenerator.java (Interface)
- Added abstract method getUpComingBricks().

#### RandomBrickGenerator.java
- Extracted brick_shape_class object creation logic > BrickFactory
- Handles brick_shape_object randomisation and returns brick_shape_object created by BrickFactory.
- Added getUpcomingBricks() to return an ArrayList of 3 randomised Tetrominoes in queue.
- This class applies <font color=aqua>SOLID: Single Responsibility Principle.</font>

#### gameLayout.fxml
- Added PausePanel inside Pane.
- Wrapped the playable area (Pane) in a HBox.
- Added other panels (score, hold, preview) as VBox as siblings to Pane.

#### window_style.css
- Added style for .pausePanel.
