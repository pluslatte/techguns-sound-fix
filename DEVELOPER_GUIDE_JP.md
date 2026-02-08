# Techguns Sound Fix - 開発ガイド

## プロジェクト構造

```
techguns-sound-fix/
├── src/main/
│   ├── java/com/pluslatte/techgunssoundfix/
│   │   ├── TechgunsSoundFix.java          # メインMODクラス
│   │   └── mixins/
│   │       ├── MixinTGSound.java          # TGSoundへのMixin
│   │       └── TechgunsSoundFixMixinPlugin.java  # Mixinローダー
│   └── resources/
│       ├── mcmod.info                      # MOD情報
│       ├── mixins.techgunssoundfix.json   # Mixin設定ファイル
│       └── META-INF/MANIFEST.MF           # マニフェストファイル
├── build.gradle                            # Gradle設定
└── gradle.properties                       # プロジェクトプロパティ
```

## Mixin実装の詳細

### 問題の分析

**問題のコード（TGSound.class 23-28行目）:**

```java
MathUtil.Vec2 pos = MathUtil.polarOffsetXZ(
    (double)this.field_147660_d,  // 現在のX座標
    (double)this.field_147658_f,  // 現在のZ座標
    1.0,                           // 半径
    (double)((EntityLivingBase)entity).field_70759_as * Math.PI / 180.0  // yaw角度
);
this.field_147660_d = (float)pos.x;
this.field_147658_f = (float)pos.y;
```

### 座標系の変換

#### Minecraftの座標系

- yaw = 0° → 南（+Z方向）
- yaw = -90° (or 270°) → 東（+X方向）
- yaw = -180° (or 180°) → 北（-Z方向）
- yaw = -270° (or 90°) → 西（-X方向）

#### 極座標系

- angle = 0° → 東（+X方向）
- angle = 90° → 北（+Z方向）
- angle = 180° → 西（-X方向）
- angle = 270° → 南（-Z方向）

### 変換式の導出

Minecraftのyawから極座標のangleへの変換:

Minecraftは南を0°としているが、極座標は東を0°としている → 90°のオフセット

変換式:

```
極座標angle = Minecraft yaw + 90°
           = yaw_radians + π/2
```

### Mixinの実装

```java
@Redirect(
    method = "func_73660_a",  // update()
    at = @At(
        value = "INVOKE",
        target = "Ltechguns/util/MathUtil;polarOffsetXZ(DDDD)Ltechguns/util/MathUtil$Vec2;",
        remap = false
    ),
    remap = false,
    require = 1
)
private MathUtil.Vec2 redirectPolarOffsetXZInUpdate(double x, double z, double radius, double angle) {
    // 角度を修正: yaw + π/2
    double adjustedAngle = angle + (Math.PI / 2.0);
    return MathUtil.polarOffsetXZ(x, z, radius, adjustedAngle);
}
```

## ビルドとテスト

### ビルド方法

**重要:** ビルドには`libs/`ディレクトリにTechguns MODのJARファイルが必要です。

```bash
# 1. Techguns MODをlibsフォルダに配置
mkdir -p libs
# Techguns MODのJARファイルをlibsにコピー

# 2. ビルド
./gradlew build
```

### 開発環境でのテスト

```bash
# クライアント起動
./gradlew runClient
```

### 実機でのテスト方法

1. UniMixins MODをインストール
2. Techguns MODをインストール
3. ビルドしたJARファイル（`build/libs/techgunssoundfix-1.0.0.jar`）をmodsフォルダに配置
4. Minecraftを起動
5. Techgunsの銃を使用して音の位置を確認

### テスト項目

- [ ] Minecraftが正常に起動する
- [ ] ログにMixin適用のメッセージが表示される
- [ ] Techgunsの銃の射撃音が正しい方向から聞こえる
- [ ] 複数の方向（前後左右）で音の位置が正しい
- [ ] 他のMODとの競合がない

## トラブルシューティング

### Mixin関連のエラー

**エラー:** `Mixin transformation of [class name] failed`

**解決策:**

1. UniMixinsがインストールされているか確認
2. JAR内にmixinコンフィグファイルが含まれているか確認
3. MANIFESTファイルが正しく設定されているか確認

### クラスが見つからないエラー

**エラー:** `ClassNotFoundException: techguns.client.audio.TGSound`

**解決策:**

1. Techguns MODがインストールされているか確認
2. Techgunsのバージョンが対応しているか確認

### 音の位置が修正されない

**チェック項目:**

1. ログでMixinが適用されているか確認
2. Techgunsのバージョンを確認（TGSoundクラスの実装が変わっている可能性）
3. 他のMODとの競合を確認

## UniMixins について

### 特徴

- Minecraft 1.7.10用のMixinローダー
- 複数のMixin機能を統合
- 高い互換性

### 使用しているモジュール

- **unimixins-all**: 全モジュールを含む統合版
  - Mixin (UniMix): 基本Mixinフレームワーク
  - Compat: 互換性調整
  - Mixingasm: ASMトランスフォーマーとの互換性向上

## 追加情報

### Mixinの利点

1. **安全性**: 元のファイルを直接編集しない
2. **互換性**: 他のMODとの競合が少ない
3. **保守性**: コードが分離されていて管理しやすい
4. **柔軟性**: 実行時にクラスを変更できる

### 今後の改善案

- [ ] より正確な角度補正の検証
- [ ] 複数のTechgunsバージョンへの対応
- [ ] パフォーマンスの最適化
- [ ] より詳細なエラーログの追加

## 参考リンク

- [UniMixins GitHub](https://github.com/LegacyModdingMC/UniMixins)
- [Mixin Wiki](https://github.com/SpongePowered/Mixin/wiki)
- [Techguns Mod](https://www.curseforge.com/minecraft/mc-mods/techguns)
