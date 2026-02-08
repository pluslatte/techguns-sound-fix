# Techguns Sound Fix MOD

このMODは、Techguns MODの銃の射撃音が左に偏って聞こえる問題を修正するパッチMODです。

## 問題の概要

Techguns MODでは、銃の射撃音が正しい位置から聞こえず、常に左側にずれて聞こえる問題がありました。

## 原因

TGSound.classの`polarOffsetXZ`関数を使用した音源位置計算で、Minecraftの座標系と数学的な極座標の角度の向きが異なるため、音が左にずれていました。

**座標系の違い:**

- Minecraft: yaw=0°=南（+Z）, -90°=東（+X）
- 極座標: angle=0°=東（+X）, 90°=北（+Z）

## 修正方法

このMODは、UniMixinsを使用してTGSoundクラスに対してMixinを適用し、`polarOffsetXZ`関数に渡される角度を修正します。

修正式: `adjusted_angle = π/2 - yaw_radians`

これにより、Minecraftの座標系から極座標系への正しい変換が行われます。

## インストール方法

1. UniMixins MODをインストールします
2. このMOD（techgunssoundfix）をmodsフォルダに配置します
3. Techguns MODと一緒に使用します

## 必要なMOD

- Minecraft 1.7.10
- Minecraft Forge 10.13.4.1614以降
- UniMixins 0.2.1以降
- Techguns MOD

## ビルド方法

```bash
./gradlew build
```

ビルドされたJARファイルは`build/libs/`に出力されます。

## 技術詳細

### Mixin対象

- クラス: `techguns.client.audio.TGSound`
- メソッド: `update()`
- 対象: `MathUtil.polarOffsetXZ()` の呼び出し

### 実装

`@Redirect`アノテーションを使用して、`polarOffsetXZ`メソッドの呼び出しをインターセプトし、
角度パラメータを修正してから元のメソッドを呼び出します。

## ライセンス

このプロジェクトは、Techguns MODの音響バグを修正するためのパッチとして作成されました。

## クレジット

- Techguns MOD作者
- UniMixins開発チーム
- Mixin framework (SpongePowered)
