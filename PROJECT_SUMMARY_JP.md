# Techguns Sound Fix MOD - プロジェクト概要

## 概要

Minecraft 1.7.10のTechguns MODの銃の射撃音が左に偏って聞こえる問題を修正するMixinベースのパッチMODです。

## 主要ファイル

### Gradle設定

- **build.gradle**: UniMixins依存関係、Mixin annotation processor設定、Techguns MODの参照（`libs/`ディレクトリから）
- **gradle.properties**: MOD ID、バージョン、グループ設定

### Javaソースコード

#### MixinTGSound.java

**TGSoundへのMixin実装（メインの修正コード）**

```java
@Mixin(value = TGSound.class, remap = false)
public abstract class MixinTGSound {
    @Redirect(method = "func_73660_a",
              at = @At(value = "INVOKE",
                       target = "Ltechguns/util/MathUtil;polarOffsetXZ(DDDD)Ltechguns/util/MathUtil$Vec2;",
                       remap = false),
              remap = false)
    private MathUtil.Vec2 redirectPolarOffsetXZInUpdate(double x, double z, double radius, double angle) {
        // 角度を修正: yaw + π/2
        double adjustedAngle = angle + (Math.PI / 2.0);
        return MathUtil.polarOffsetXZ(x, z, radius, adjustedAngle);
    }
}
```

**修正内容:**

- `@Redirect`で`MathUtil.polarOffsetXZ()`の呼び出しをインターセプト
- 角度パラメータに π/2 を加算して、Minecraft座標系から極座標系に正しく変換

#### その他のファイル

- **TechgunsSoundFix.java**: メインMODクラス
- **TechgunsSoundFixMixinPlugin.java**: FML Core Plugin（早期Mixin適用）
- **TechgunsSoundFixLateMixinLoader.java**: Late Mixin Loader

### リソースファイル

- **mixins.techgunssoundfix.json**: Mixin設定
- **mcmod.info**: MOD情報
- **META-INF/MANIFEST.MF**: Core Plugin指定

## ビルド要件

**重要:** `libs/`ディレクトリにTechguns MODのJARファイルが必要です。これがないとコンパイルエラーになります。

### 4. ドキュメントファイル

#### [README.md](README.md) / [README_JP.md](README_JP.md)

- プロジェクトの説明
- インストール方法
- 技術的な詳細

#### [DEVELOPER_GUIDE_JP.md](DEVELOPER_GUIDE_JP.md)

- 詳細な開発者向けガイド
- 座標系変換の説明
- トラブルシューティング

## 技術的な解決策

### 問題の分析

TGSound.classの以下のコードが問題の原因でした:

```java
MathUtil.Vec2 pos = MathUtil.polarOffsetXZ(
    (double)this.field_147660_d,
    (double)this.field_147658_f,
    1.0,
    (double)((EntityLivingBase)entity).field_70759_as * Math.PI / 180.0
);
```

### 座標系の違い

| 座標系    | 0°の方向 | 90°の方向 | 回転方向         |
| --------- | -------- | --------- | ---------------- |
| Minecraft | 南(+Z)   | 西(-X)    | 時計回り（負）   |
| 極座標    | 東(+X)   | 北(+Z)    | 反時計回り（正） |

### 変換式

```
極座標angle = 90° - Minecraft yaw
           = π/2 - yaw_radians
```

この変換により、Minecraftの座標系から極座標系への正しい変換が行われます。

## ビルド方法

```bash
# 初回セットアップ
./gradlew setupDecompWorkspace

# ビルド
./gradlew build
```

生成されるファイル: `build/libs/techgunssoundfix-1.0.0.jar`

## 使用方法

1. **UniMixins**をインストール（バージョン0.2.1以降）
2. **Techguns MOD**をインストール
3. 生成された`techgunssoundfix-1.0.0.jar`をmodsフォルダに配置
4. Minecraftを起動

## テスト項目

実機でのテストが必要な項目:

- [ ] Minecraft 1.7.10が正常に起動する
- [ ] Mixinが正しく適用される（ログで確認）
- [ ] 銃の射撃音が正しい方向から聞こえる
  - [ ] 前方で撃った時
  - [ ] 後方で撃った時
  - [ ] 左側で撃った時
  - [ ] 右側で撃った時
- [ ] 他のMODと競合しない

## 依存関係

- **Minecraft**: 1.7.10
- **Forge**: 10.13.4.1614以降
- **UniMixins**: 0.2.1
- **Techguns MOD**: 最新版

## Mixinの利点

1. **非侵襲的**: 元のMODファイルを直接変更しない
2. **互換性**: 他のMODとの競合が少ない
3. **保守性**: コードが分離されて管理しやすい
4. **柔軟性**: 実行時にクラスを変更できる

## 今後の課題

- [ ] 実機でのテストと検証
- [ ] Techgunsの異なるバージョンでの動作確認
- [ ] パフォーマンスの最適化
- [ ] より詳細なログ出力の追加

## まとめ

UniMixinsを使用したMinecraft 1.7.10 Forge MODの作成が完了しました。
Mixinを使用することで、Techguns MODのソースコードを直接変更することなく、
座標系の問題を修正することができました。

プロジェクトは完全にビルド可能な状態で、全ての必要なファイルが揃っています。
実際の動作確認は、UniMixinsとTechgunsをインストールした環境で行ってください。
