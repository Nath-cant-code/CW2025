# This is my COMP2042 Developing Maintainable Software Coursework
My task for this coursework is to refactor and extend the classic retro game, Tetris. The changes mentioned here are a brief summary of the commits I made throughout the progress of this coursework. 

## Compilation Instructions

### Prerequisites
- Java JDK 23 (required)
- Maven 3.6 or higher
- JavaFX installation handled by Maven (No SDK installation needed)

### Step-by-Step Compilation

#### Using Maven:
```bash
# Compile the project
mvn clean compile

# Run the application
mvn javafx:run
```

### Or if using IntelliJ, Maven running configurations can be accessed at the (right) side panel

1. Click the 'M' icon to open maven running configurations
2. Go to `demo3 > Plugins > clean`
3. Click `clean:clean` to build 
4. Go to `demo3 > Plugins > javafx`
5. Click `javafx:run` to run

#### Using IDE (IntelliJ IDEA / Eclipse):
1. Open the project in your IDE
2. Ensure JavaFX libraries are properly configured in project dependencies
3. Set the main class as `com.comp2042.app.Main`
4. Run the Main class

### Dependencies
- JavaFX Controls version 21.0.6
- JavaFX FXML version 21.0.6
- JUnit Jupiter version 5.12.1
- Java AWT

---

## Implemented and Working Properly

### Core Gameplay Features
- **Rotation System**: Clockwise (X key) and anti-clockwise (Z, W, UP keys) rotation
- **Hard Drop**: Space bar instantly drops (snap / hard drop) pieces to the bottom
- **Hold Mechanism**: C key allows players to hold one piece for later use (one hold per turn)
- **Soft Drop Bonus**: Players earn 1 point per row when manually moving pieces down

### Scoring System
- **Progressive Scoring**:
  - Soft drop: 1 point per row
  - Hard drop: 5 points per row
  - Line clears: 50 × (lines cleared)²
- **Score Display**: Real-time score updates

### Level System
- **Dynamic Difficulty**:
  - Level increases every 5 lines cleared
  - Fall speed increases by 30ms per level
  - Maximum 10 levels
  - Speed range: 400ms (Level 1) to 100ms (Level 10)
- **Level-Up Notifications**: Animated notifications when leveling up

### Preview System
- **Next Pieces Preview**: Shows the next 3 Tetrominoes in queue
- **Hold Panel**: Displays the currently held piece

### Special Features
- **Special Shape Bonus**:
  - Form a specific pattern (configurable in SpecialShapeConfig)
  - Awards 109,833,163 bonus points
  - One-time bonus per game
  - Special shape display panel shows target pattern

### Game States
- **Pause/Resume**: ESC key pauses with 3-second countdown on resume
- **New Game**: N key resets the game at any time

### UI/UX Features
- **Visual Effects**:
  - Overall score label
  - Level-up notification label
- **Responsive Controls**: Minimal input lag with proper event handling

---

## Features Implemented but Not Working Properly 

- **Ghost Piece**:
  - **Issues encountered**: Logic is not very straightforward, probably usage of snap (hard drop) Brick methods required to get the position of the ghost piece beforehand. Tried implementing in this way but somehow interferes with MatrixOperations intersect() functionality, and the issues got compounded onto the snap Brick function.
  - **Steps taken**: Commented out and removed in fear of disrupting snap Brick functionality. Will probably implement once again during semester break.

## Features Not Implemented

- **Sound Effects/Music**:
  - **Reason**: Focused on game logic and architecture rather than cosmetics


- **Start Screen, Pause Screen, Restart Screen, Game Background GUI Design**:
  - **Reason**: Focused on game logic and architecture rather than cosmetics

 
- **High Score Persistence:**:
  - **Reason**: Database or file I/O system required


- **Power-up System**:
  - **Reason**: More intrigued by special combination shape formation bonus than power-ups, as they are more likely to be implemented in other students' coursework, which would make my coursework less unique according to the criteria in the coursework sheet

---

## New Java Classes

### Brick Action System (`com.comp2042.bricks.actions`)
- **BrickActionCoordinator**: 
   - <font color="DeepSkyBlue">SOLID: SR</font> + <font color="HotPink">Design Pattern: Facade</font>
   - Coordinates all Brick actions (movement, rotation, snap, hold)
- **BrickHolder**: 
  - <font color="DeepSkyBlue">SOLID: SR</font> + <font color="HotPink">Design Pattern: Strategy</font>
  - Manages hold Brick logic
- **BrickMover**: 
  - <font color="DeepSkyBlue">SOLID: SR</font>
  - Handles all Brick movement logic with collision detection 
- **BrickQueueManager**: 
   - <font color="DeepSkyBlue">SOLID: SR</font> + <font color="HotPink">Design Pattern: Facade</font>
  - Manages Brick queue for preview and game's hold Brick state
- **BrickRotator**: 
  - <font color="DeepSkyBlue">SOLID: SR</font>
  - Manages both directions of Brick rotation logic 
- **BrickSnapper**: 
  - <font color="DeepSkyBlue">SOLID: SR</font> + <font color="HotPink">Design Pattern: Strategy</font>
  - Implements hard drop (snap) functionality
- **RotationDirection**: 
  - Enum for clockwise/anti-clockwise rotation

### Brick Class Abstraction (`com.comp2042.bricks.production.blueprints`)
- **AbstractBrick**:
  - Contains getShapeMatrix method from all the brick_shape_classes

### Factory System (`com.comp2042.bricks.production.industrial_factory`)
- **BrickFactory**: 
  - <font color="DeepSkyBlue">SOLID: SR</font> + <font color="HotPink">Design Pattern: Facade</font>
  - Factory for creating instances of all Brick classes
- **BrickType**: 
  - Enum of all Brick shape classes 

### Input Event System (`com.comp2042.input.event_listener`)
- **MergeEventProcessor**: 
  - <font color="DeepSkyBlue">SOLID: SR</font> + <font color="HotPink">Design Pattern: Template</font>
  - Template: Defines post-merge processing algorithm and details

### Input Event System (`com.comp2042.input.keyboard`)
- **InputAction**: 
  - <font color="DeepSkyBlue">SOLID: O/C</font> + <font color="HotPink">Design Pattern: Command Pattern</font> 
  - Functional interface for command pattern that encapsulates a request as an object
  - Closed as this class need not be modified when new input actions are created
- **InputHandler**:
  - Handles keyboard input events and coordinates between InputAction and KeyBinder.
- **KeyBinder**:
  - <font color="DeepSkyBlue">SOLID: SR, O/C</font> + <font color="HotPink">Design Pattern: Factory, Strategy</font>
  - Maps keys to actions (Factory + Strategy pattern)

### Detection System (`com.comp2042.logic.detection_system`)
- **CollisionDetector**: 
  - <font color="DeepSkyBlue">SOLID: SR</font> + <font color="HotPink">Design Pattern: Strategy</font>
  - Contains collision detection logic and rules that can be changed independently
- **SpecialShapeDetector**: 
  - <font color="DeepSkyBlue">SOLID: SR</font> + <font color="HotPink">Design Pattern: Strategy</font>
  - Detects special shape pattern completion

### Engine System (`com.comp2042.logic.engine`)
- **ActionBoard**: 
  - <font color="DeepSkyBlue">SOLID: SR, LS, DI</font> + <font color="HotPink">Design Pattern: Facade</font>
  - Extends SimpleBoard, contains calls to Brick action methods

### Rendering System (`com.comp2042.renderer.basic_renderers`)
- **BoardRenderer**:
  - <font color="DeepSkyBlue">SOLID: SR</font> + <font color="HotPink">Design Pattern: Template</font>
  - Renders visuals of playable board area.
- **BrickRenderer**
  - <font color="DeepSkyBlue">SOLID: SR</font> + <font color="HotPink">Design Pattern: Template</font>
  - Renders visuals of Brick object matrix.

### Rendering System (`com.comp2042.renderer.color_renderers`)
- **ColorSelector**: 
  - <font color="DeepSkyBlue">SOLID: SR, O/C</font> + <font color="HotPink">Design Pattern: Decorator</font>
  - Brick color selection logic
- **RectangleColorRenderer**: 
  - <font color="DeepSkyBlue">SOLID: SR</font> + <font color="HotPink">Design Pattern: Decorator, Iterator</font>
  - Handles rectangle colour rendering 

### Renderer Interfaces (`com.comp2042.renderer.refresher_interfaces`)
- **ActiveBrick_RI**: 
  - <font color="DeepSkyBlue">SOLID: IS, DI</font> + <font color="HotPink">Design Pattern: Template</font>
  - Interface for active brick rendering

- **GameBackground_RI**: 
  - <font color="DeepSkyBlue">SOLID: IS, DI</font> + <font color="HotPink">Design Pattern: Template</font>
  - Interface for background rendering

- **HoldBrick_RI**: 
  - <font color="DeepSkyBlue">SOLID: IS, DI</font> + <font color="HotPink">Design Pattern: Template</font>
  - Interface for hold panel rendering

- **PreviewBricks_RI**: 
  - <font color="DeepSkyBlue">SOLID: IS, DI</font> + <font color="HotPink">Design Pattern: Template</font>  
  - Interface for preview rendering

### Rendering System (`com.comp2042.renderer.runtime_refreshers`)
- **RefreshCoordinator**:
  - <font color="DeepSkyBlue">SOLID: O/C, DI</font> + <font color="HotPink">Design Pattern: Facade</font> 
  - Coordinating all visual refresh classes
- **RefreshActiveBrick**: 
  - <font color="DeepSkyBlue">SOLID: SR</font> + <font color="HotPink">Design Pattern: Strategy</font>
  - Renders the falling brick
- **RefreshGameBackground**: 
  - <font color="DeepSkyBlue">SOLID: SR</font> + <font color="HotPink">Design Pattern: Strategy</font>
  - Renders the game board
- **RefreshHoldPanel**: 
  - <font color="DeepSkyBlue">SOLID: SR</font> + <font color="HotPink">Design Pattern: Strategy</font>
  - Renders the hold panel
- **RefreshPreviewBricks**: 
  - <font color="DeepSkyBlue">SOLID: SR</font> + <font color="HotPink">Design Pattern: Strategy</font>  
  - Renders next pieces preview 

### UI Elements (`com.comp2042.ui.elements`)
- **LevelSystem**: 
  - <font color="DeepSkyBlue">SOLID: SR</font> + <font color="HotPink">Design Pattern: Strategy, Observer</font>
  - Manages level progression and fall speed calculation
- **Score**:
  - <font color="DeepSkyBlue">SOLID: SR</font>
  - Contains score property of the game
- **SpecialShapeConfig**: 
  - <font color="DeepSkyBlue">SOLID: SR</font>
  - Configuration for special shape bonus feature

### UI Panels (`com.comp2042.ui.panels`)
- **LevelUpPanel**: 
  - <font color="DeepSkyBlue">SOLID: SR</font>
  - Animated level-up notification panel
- **PausePanel**: 
  - <font color="DeepSkyBlue">SOLID: SR</font>
  - Pause/resume notification panel
- **SpecialShapeDisplayPanel**: 
  - <font color="DeepSkyBlue">SOLID: SR</font> 
  - Displays target special shape pattern
- **SpecialShapeTextPanel**: 
  - <font color="DeepSkyBlue">SOLID: SR</font> 
  - Animated special shape completion message

### UI Panels (`com.comp2042.ui.panels`)
- **GameView**:
  - <font color="DeepSkyBlue">SOLID: IS, DI</font> + <font color="HotPink">Design Pattern: Template</font>
  - Interface for GuiController

### UI Initializers (`com.comp2042.ui.systems.initializers`)
- **GameInitializer**: 
  - <font color="DeepSkyBlue">SOLID: SR</font> 
  - Initializes game view components
- **PanelInitialiser**: 
  - <font color="DeepSkyBlue">SOLID: SR</font> 
  - Initializes hold and preview panels

### UI Management (`com.comp2042.ui.systems.managers`)
- **GameStateManager**: 
  - <font color="DeepSkyBlue">SOLID: SR, O/C</font> + <font color="HotPink">Design Pattern: Strategy</font>
  - Manages game state transitions (new game, pause, resume, game over)
- **GameStateProperty**: 
  - <font color="DeepSkyBlue">SOLID: SR</font> + <font color="HotPink">Design Pattern: State</font>
  - Manages game state properties (paused, game over)
- **SpecialShapeManager**: 
  - <font color="DeepSkyBlue">SOLID: SR</font>
  - Handles special shape completion sequence
- **TimeLineManager**: 
  - <font color="DeepSkyBlue">SOLID: SR</font> + <font color="HotPink">Design Pattern: Strategy</font>
  - Manages game timeline and fall speed
- **UILabelManager**: 
  - <font color="DeepSkyBlue">SOLID: SR</font> + <font color="HotPink">Design Pattern: State, Interpreter</font>
  - Manages UI panel visibility

---

## Modified Java Classes

### 1. **SimpleBoard** (now abstract base class) (`com.comp2042.logic.engine`)
- **Changes**:
  - Extracted all action methods to ActionBoard
  - Changed field modifiers to `protected` for inheritance
  - Made class abstract to enforce subclass implementation
  - Delegates to BrickActionCoordinator, BrickQueueManager
- **Reason**: 
  - <font color="DeepSkyBlue">SOLID: SR, DI</font> + <font color="HotPink">Design Pattern: Facade</font>
  - <font color="DeepSkyBlue">Single Responsibility</font>: Improve testability and maintainability by separating board state management from brick actions
  - <font color="DeepSkyBlue">Dependency Inversion</font>: Implements Board, which other classes depend on, instead of depending on the details of this class
  - <font color="HotPink">Facade</font>: Provides simple interface to complex subsystem

### 2. **GuiController** (`com.comp2042.ui.systems.master`)
- **Changes**:
  - Implements new interface: GameView
  - Removed massive `initGameView()` method logic
  - Removed direct timeline management
  - Delegated input handling to InputHandler
  - Delegated rendering to RefreshCoordinator
  - Delegated game state management to GameStateManager
  - Delegated special combination shape UI processes to SpecialShapeManager
- **Reason**:
  - <font color="DeepSkyBlue">SOLID: SR, DI</font> + <font color="HotPink">Design Pattern: Facade</font>
  - <font color="DeepSkyBlue">Single Responsibility</font>: Improve testability and maintainability by coordinating UI components only
  - <font color="DeepSkyBlue">Dependency Inversion</font>: Implements GameView, which other classes depend on, instead of depending on the details of this class
  - <font color="HotPink">Facade</font>: Provides simple interface to complex UI subsystem

### 3. **EventListener** (formerly GameController) (`com.comp2042.input.event_listener`)
- **Changes**:
  - Renamed from GameController to EventListener for clarity
  - Added onRotateClock() and onRotateAntiClock() for both directions of rotations
  - Added onHoldEvent() for hold functionality
  - Added onSnapEvent() for hard drop functionality
  - Refactored onDownEvent() and onSnapEvent() by extracting identical code sections to MergeEventProcessor
- **Reason**:
  - <font color="DeepSkyBlue">SOLID: SR, DI</font> + <font color="HotPink">Design Pattern: Facade</font>
  - <font color="DeepSkyBlue">Single Responsibility</font>: Improve testability and maintainability by coordinating onEvent() methods only
  - <font color="DeepSkyBlue">Dependency Inversion</font>: Implements InputEventListener, which other classes depend on, instead of depending on the details of this class
  - <font color="HotPink">Facade</font>: Provides simple interface to complex Brick action subsystem via onEvent() methods

### 4. **Main** (`com.comp2042.app`)
- **Changes**:
  - Updated to use ActionBoard instead of SimpleBoard
  - Updated to create GameView object and depend on abstractions instead of depending on GuiController
- **Reason**: 
  - <font color="DeepSkyBlue">SOLID: DI</font>
  - <font color="DeepSkyBlue">Dependency Inversion</font>: Depends on abstractions by creating objects of interfaces instead of depending on classes containing the details

### 5. **RandomBrickGenerator** (`com.comp2042.bricks.production.brick_generation_system`)
- **Changes**:
  - Uses BrickFactory and BrickType enum
  - Updated to ensure that there is always 4 Brick objects in queue (previously 2)
  - Added getUpcomingBricks() method
- **Reason**:
  - <font color="DeepSkyBlue">SOLID: SR</font> + <font color="HotPink">Design Pattern: Factory</font>
  - <font color="DeepSkyBlue">Single Responsibility</font>: Class handles Brick List generation
  - <font color="HotPink">Factory</font>: Utilises factory class for Brick creation to make methods more concise, cohesive, and less coupled

### 6. **All Brick Classes** (IBrick, JBrick, LBrick, OBrick, SBrick, TBrick, ZBrick) (`com.comp2042.bricks.production.brick_shapes`)
- **Changes**:
  - Extends AbstractBrick base class
  - Extracted identical getShapeMatrix() methods
- **Reason**:
  - <font color="DeepSkyBlue">SOLID: SR, O/C, DI</font> + <font color="HotPink">Design Pattern: Template</font>
  - <font color="DeepSkyBlue">Single Responsibility</font>: Only contains matrix of individual Brick shapes
  - <font color="DeepSkyBlue">Open Closed</font>: Open for adding new types of Bricks easily without modifying existing Bricks
  - <font color="DeepSkyBlue">Dependency Inversion</font>: Objects of Bricks or AbstractBricks are created to depend on abstractions
  - <font color="HotPink">Template</font>: Defines the shape of individual Brick shapes only

### 10. **ClearRow, ViewData, DownData, NextShapeInfo** (`com.comp2042.logic.game_records`)
- **Changes**: 
  - Converted to Java records
- **Reason**:
  - Modern Java features suggested by IntelliJ

---

## Controls

- **W / UP**: Rotate clockwise
- **A / LEFT**: Move left
- **S / DOWN**: Move down
- **D / RIGHT**: Move right
- **Space**: Hard drop
- **C**: Hold piece
- **ESC**: Pause/Resume
- **N**: New game
- **X**: Rotate clockwise
- **Z**: Rotate anti-clockwise

---

## Unexpected Challenges or Problems 

### 1. GUI Latency
When implementing the hard drop functionality, some latency on the UI was observed. After multiple trial and errors, I deduced that it was due to me passing an instance of GuiController to the method in SimpleBoard that handles the hard drop functionality logic. I did this so that the display matrix of the game's background can be accessed by the refresh method that handles the game's background visual refreshes

Hence, I modified the code accordingly so that the display matrix of the game's background is passed to the method, instead of passing an instance of GuiController just to access the game's display matrix. This solved the latency issue right away and no other modifications were needed.

### 2. Game Background Not Updating Instantly
This one was pretty straightforward as the method call for refreshing the game's background visuals should be called after the rows are cleared. However, it took me a few hours to realise that, as I thought the issue was with my refresh method's logic and row clearing logic containing a bug. In reality, I just had to move or (duplicate) the refresh method call so that it comes after clearing the rows.

### 3. Special Combination Shape Tetromino Removal
When implementing the special combination shape feature, the last Tetromino that completes the special combination shape does not disappear along with the rest of the game board. Instead, it only disappears after a certain delay.

A nested for loop is needed to manually recolour the last Tetromino to transparent. This addition was added into the method that handles the completion of the special combination shape. I added it right before the call to the method for refreshing the game background so that the last Tetromino disappears along with the game board at visually (or virtually) the same time. 

---
