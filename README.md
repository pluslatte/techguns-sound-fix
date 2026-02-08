# Techguns Sound Fix Mod

Fixes the gun shooting sound positioning issue in Techguns mod for Minecraft 1.7.10.

## Problem

Gun sounds in Techguns mod are mispositioned and always sound from the left side.

## Solution

This mod uses UniMixins to apply a Mixin to `TGSound` class, correcting the angle calculation for proper sound positioning.

**Fix:** `adjusted_angle = yaw + π/2`

This converts Minecraft's coordinate system to polar coordinates correctly.

## Requirements

- Minecraft 1.7.10
- Minecraft Forge 10.13.4.1614 or later
- UniMixins 0.2.1 or later
- Techguns mod

## Installation

1. Install UniMixins mod
2. Place this mod in your mods folder
3. Place Techguns mod in your mods folder

## Building

**IMPORTANT:** You need Techguns mod JAR file in the `libs/` directory to build this mod.

1. Download Techguns mod JAR and place it in `libs/` folder
2. Run:

```bash
./gradlew build
```

The built JAR will be in `build/libs/`.

## Technical Details

- **Target:** `techguns.client.audio.TGSound.update()`
- **Method:** `@Redirect` on `MathUtil.polarOffsetXZ()` call
- **Fix:** Adds π/2 to angle parameter

---

[日本語版README](README_JP.md)
