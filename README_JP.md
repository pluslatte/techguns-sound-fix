# Techguns Sound Fix MOD

Techguns MODの銃の射撃音が左に偏って聞こえる問題を修正するパッチMODです。

## 問題

Techguns MODでは、銃の射撃音が正しい位置から聞こえず、常に左側にずれて聞こえます。

## 解決方法

このMODは、UniMixinsを使用して`TGSound`クラスに対してMixinを適用し、音の位置計算の角度を修正します。

**修正式:** `adjusted_angle = yaw + π/2`

これにより、Minecraftの座標系から極座標系への正しい変換が行われます。

## 必要なもの

- Minecraft 1.7.10
- Minecraft Forge 10.13.4.1614以降
- UniMixins 0.2.1以降
- Techguns MOD

## インストール方法

1. UniMixins MODをインストール
2. このMODをmodsフォルダに配置
3. Techguns MODをmodsフォルダに配置

## ビルド方法

**重要:** このMODをビルドするには、`libs/`ディレクトリにTechguns MODのJARファイルが必要です。

1. Techguns MODのJARファイルをダウンロードして`libs/`フォルダに配置
2. ビルド実行:

```bash
./gradlew build
```

ビルドされたJARファイルは`build/libs/`に出力されます。

## 技術詳細

- **対象:** `techguns.client.audio.TGSound.update()`
- **手法:** `@Redirect`で`MathUtil.polarOffsetXZ()`の呼び出しをインターセプト
- **修正:** 角度パラメータにπ/2を加算
