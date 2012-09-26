# Product Backlog II

## 1. "Human in the loop"

Some government workers want to directly control the robot, but they don't want to issue a stream of commands in advance. Instead, they want to enter a command at a time, and  visualize the new mine layout at each step. It would be great to have a simple command prompt that prints the layout of a mine after every command is issued.

## 2. "To err is human, to undo divine"

The same workers are known to commit errors here and there. Of course, controlling a robot directly can have disastrous consequences, and so they want to *simulate* in an environment where they can `undo` and `redo` unlimited steps at will.

## 3. "Slippery Rocks"

Our recent surveys of Alentejo's underground have revealed that rocks (`*`) tend to be slippery (due to some unknown geological process, they are all perfectly spherical). This means that, if a rock is supported on the top of another rock, and there is nothing (Empty cells) supporting their sides, they will fall (to the unsupported side).

    #   #      #   #
    # * #      #   #
    # * #  =>  # **#
    #####      #####


    #   #      #   #
    #.* #      #.  #
    #.* #  =>  #.**#
    #####      #####


    #   #      #   #
    # *.#      #  .#
    # *.#  =>  #**.#
    #####      #####
Note they have this odd tendency to, **whenever possible, [fall to the right](http://www.universetoday.com/87488/are-the-galaxies-in-our-universe-more-right-handed-or-left-handed/)**. In other words:

* If `(x,y)` contains a Rock, `(x, y−1)` contains a Rock, `(x+1,y)` is Empty and `(x+1, y−1)` is Empty, then `(x,y)` is updated to Empty, `(x+1,y−1)` is updated to Rock;
* Else, if `(x,y)` contains a Rock, `(x, y−1)` contains a Rock, `(x-1,y)` is Empty and `(x-1, y−1)` is Empty, then `(x,y)` is updated to Empty, `(x-1,y−1)` is updated to Rock.

## 4. "Where the robot gains arms"

Some new technology is required from the mining robot. However, it will be deliberately limited to ensure [no robot goes havoc](http://www.youtube.com/watch?v=2BRXmgcBHBM), thus:

* When the robot (`R`) is moving right towards a rock, and there is an empty space to the right of the rock (`*`), then the rock is "pushed" right (and the robot also moves).
* When the robot (`R`) is moving left towards a rock, and there is an empty space to the left of the rock (`*`), then the rock is "pushed" left (and the robot also moves).

Example:

    #   #  move right  #   #
    #R* #      =>      # R*#
    #####              #####

## 5. "Falling rock hurts a lot"

Equipment is expensive, and so falling rocks should be avoided at all costs. If a free-falling rock falls on the top of the Robot, the Robot should be regarded as terminated -- further commands are ignored and all diamonds are lost.