# Techguns Audio Fix

A Minecraft 1.7.10 mod that fixes the gun sound position offset issue in Techguns MOD where sounds are shifted to the left.

[日本語READMEはこちら / Japanese README](README_JP.md)

## Problem

In Techguns MOD, gun firing sounds appear to come from the left of the actual gun position due to a coordinate system mismatch in the `TGSound` class's `polarOffsetXZ` calculation.

## Solution

This mod uses **UniMixins** to patch the angle parameter in the `polarOffsetXZ` call, subtracting π/2 (90 degrees) to align with Minecraft's coordinate system.

## Technical Details

- **Minecraft**: 1.7.10
- **Forge**: 10.13.4.1614-1.7.10
- **Dependencies**: Techguns (any version)
- **Mixin Library**: UniMix 0.15.5

## Installation

1. Download the latest release from [Releases](https://github.com/pluslatte/techguns-sound-fix/releases)
2. Place `techgunsfix-1.0.0.jar` in your Minecraft `mods` folder
3. Ensure Techguns MOD is also installed
4. Launch Minecraft

## Building from Source

```bash
git clone https://github.com/pluslatte/techguns-sound-fix.git
cd techguns-sound-fix
./gradlew build
```

The built JAR will be in `build/libs/`

## How It Works

The mod uses a Mixin to intercept the `MathUtil.polarOffsetXZ` call in `TGSound`'s constructor and modifies the angle parameter to correct the coordinate system mismatch between Minecraft's yaw angles and mathematical polar coordinates.

### Implementation

- **@ModifyArg** on `polarOffsetXZ` call (4th parameter - angle)
- **Correction**: `angle - Math.PI / 2.0`
- **Target**: `techguns.client.audio.TGSound` constructor

## License

This project follows FML and Minecraft Forge licensing. See `LICENSE-fml.txt` and `MinecraftForge-License.txt` for details.

## Credits

- **Developer**: pluslatte
- **Mixin Library**: [UniMix by LegacyModdingMC](https://github.com/LegacyModdingMC/UniMix)
- **Forge**: MinecraftForge Team

