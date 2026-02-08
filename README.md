# Techguns Sound Fix Mod

A Mixin-based patch mod that fixes the gun shooting sound positioning issue in the Techguns mod for Minecraft 1.7.10.

## Problem Overview

In the Techguns mod, gun shooting sounds are incorrectly positioned and always sound like they're coming from the left side of the player.

## Root Cause

The issue occurs in the `TGSound.class` when using the `polarOffsetXZ` function to calculate sound source positions. The problem stems from a mismatch between Minecraft's coordinate system and mathematical polar coordinates:

**Coordinate System Differences:**

- Minecraft: yaw=0°=South (+Z), -90°=East (+X)
- Polar coordinates: angle=0°=East (+X), 90°=North (+Z)

## Solution

This mod uses UniMixins to apply a Mixin to the `TGSound` class, correcting the angle passed to the `polarOffsetXZ` function.

**Fix Formula:** `adjusted_angle = π/2 - yaw_radians`

This ensures proper conversion from Minecraft's coordinate system to polar coordinates.

## Installation

1. Install UniMixins mod
2. Place this mod (techgunssoundfix) in your mods folder
3. Use together with Techguns mod

## Requirements

- Minecraft 1.7.10
- Minecraft Forge 10.13.4.1614 or later
- UniMixins 0.2.1 or later
- Techguns mod

## Building

```bash
./gradlew build
```

The built JAR file will be in `build/libs/`.

## Technical Details

### Mixin Target

- Class: `techguns.client.audio.TGSound`
- Method: `update()`
- Target: Call to `MathUtil.polarOffsetXZ()`

### Implementation

Uses `@Redirect` annotation to intercept the `polarOffsetXZ` method call, corrects the angle parameter, and then invokes the original method.

## License

This project was created as a patch to fix an audio bug in the Techguns mod.

## Credits

- Techguns mod author
- UniMixins development team
- Mixin framework (SpongePowered)

---

[日本語版READMEはこちら](README_JP.md)
