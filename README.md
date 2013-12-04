# artbox

A physics and game entity engine for Android.

### Introduction

The artbox engine is the integration of jbox2d (used to handle the physics interactions) and an overarching artemis framework. The artemis framework defines three types of objects:

 * `Components` hold data such as position or velocity or an bitmap image.
 * `Entities` are bags that hold `Components` and encapsulate the notion of a game object.
 * `Systems` perform operations on `Entities` that have particular `Components` - this is what evolves the game state.

For example, a player `Entity` may have three `Components`: `PositionComponent`, `VelocityComponent`, and `BitmapComponent`. A `PositionUpdateSystem` may define itself to operate on `Entities` with a `PositionComponent` and a `VelocityComponent`. With some pseudocode, this operation could look like this:

```java
public void process(Entity e) {
    PositionComponent position = e.getPositionComponent();
    VelocityComponent velocity = e.getVelocityComponent();
    
    position.setX(position.getX() + timeStep * velocity.getVx());
    position.setY(position.getY() + timeStep * velocity.getVy());
}
```

This `process` method is then performed on every `Entity` with a `PositionComponent` and `VelocityComponent` and in this way, the positions for each `Entity` is evolved according to clearly defined logical rules.

A key point here is that the `PositionUpdateSystem` does not care that our player `Entity` has a `BitmapComponent`. Data that is not within the scope of the `System` is not operated on. This allows us to define other `Systems` that operate on different sets of `Components` with full knowledge of what types of `Systems` are using the same data. For instance, a `BitmapRenderSystem` could operate on `Entities` with a `PositionComponent` and `BitmapComponent` by drawing the bitmap at the position given by `PositionComponent`.

### Features

 * Fully integrated artemis and jbox2d frameworks to provide quick and easy development of 2D Android games.
 * API for injecting new `Components`, `Entities`, and `Systems` into the base engine to define new objects and behaviors.
 * JSON-backed `Components` and `Entities` allow for designing game objects without touching the source code.

### Installing

You can either download the .jar (when it is released), or import this project as a library project in Eclipse.

### License

 * [This repo's license](https://github.com/whoshuu/artbox/blob/develop/LICENSE.txt)
 * [artemis license](https://github.com/gemserk/artemis/blob/master/src/license.txt)
 * [jbox2d license](https://code.google.com/p/jbox2d/source/browse/trunk/LICENSE)

### Acknowledgments

This project draws heavily from the [Artemis Component Entity System](https://github.com/gemserk/artemis) framework for maintaining and processing game state, and the [Java port](https://code.google.com/p/jbox2d/) of [box2d](http://box2d.org/), a 2D physics engine.

### Contributing

Please fork this repository and contribute back using [pull requests](https://github.com/whoshuu/artbox/pulls). Features can be requested using [issues](https://github.com/whoshuu/artbox/issues). All code, comments, and critiques are greatly appreciated.
