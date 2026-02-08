# Techguns Audio Fix MOD

Minecraft 1.7.10用のMOD。Techguns MODの銃の音声が左に偏って聞こえる問題を修正します。

## 問題の概要

Techguns MODでは、銃を撃つと音が実際の銃の位置より左側から聞こえる問題があります。
これは、`TGSound`クラスの`polarOffsetXZ`関数で使用される座標系の不一致が原因です。

### 原因

- **Minecraftの座標系**: yaw=0°=南（+Z方向）、-90°=東（+X方向）
- **極座標の標準**: angle=0°=東（+X方向）、90°=北（+Z方向）

この90度のズレにより、音源位置が左にシフトしてしまいます。

### 修正方法

UniMixinsを使用して、`polarOffsetXZ`呼び出し時の角度パラメータから`π/2`（90度）を減算することで、
Minecraftの座標系に合わせます。

## 技術仕様

- **Minecraft バージョン**: 1.7.10
- **Forge バージョン**: 10.13.4.1614-1.7.10
- **依存MOD**: 
  - Techguns（任意のバージョン）
  - UniMixins または GTNHMixins（実行時に必須）
- **Mixinライブラリ**: Mixin 0.8.5（SpongePowered、コンパイル時のみ）

## ビルド方法

### 前提条件

- Java 8以降
- Gradle（プロジェクトに含まれるGradlewを使用）

### ビルド手順

```bash
# リポジトリをクローン
git clone https://github.com/pluslatte/techguns-sound-fix.git
cd techguns-sound-fix

# ビルド実行
./gradlew build

# 生成されたJARファイルは build/libs/ に作成されます
```

## インストール方法

1. [UniMixins](https://modrinth.com/mod/unimixins) または [GTNHMixins](https://github.com/GTNewHorizons/GTNHMixins) をインストール（必須の依存関係）
2. ビルドで生成された `techgunsfix-1.0.0.jar` を取得
3. Minecraftの`mods`フォルダに配置
4. Techguns MODも同時に導入されていることを確認
5. Minecraftを起動

## 使用方法

このMODは自動的に動作します。特別な設定は不要です。

1. Techgunsの銃を入手
2. 銃を発射
3. 音が正しい位置（銃の位置）から聞こえることを確認

## 実装の詳細

### Mixin構成

- **MixinTGSound**: `techguns.client.audio.TGSound`クラスのコンストラクタに介入
- **注入ポイント**: `MathUtil.polarOffsetXZ`メソッド呼び出し
- **修正内容**: 角度パラメータ（第4引数）を`angle - π/2`に変更

### ファイル構成

```
src/main/java/com/pluslatte/techgunsfix/
├── TechgunsFixMod.java                          # メインMODクラス
└── mixin/
    ├── TechgunsFixLoadingPlugin.java            # CoreModプラグイン
    └── MixinTGSound.java                        # TGSound修正用Mixin

src/main/resources/
├── mcmod.info                                   # MOD情報
├── mixins.techgunsfix.json                      # Mixin設定
└── META-INF/
    └── MANIFEST.MF                              # CoreMod登録
```

## ライセンス

このプロジェクトは、FMLおよびMinecraft Forgeのライセンスに準拠します。
詳細は、`LICENSE-fml.txt`および`MinecraftForge-License.txt`を参照してください。

## クレジット

- **開発**: pluslatte
- **Mixinライブラリ**: LegacyModdingMC - UniMix
- **Forge**: MinecraftForge Team

## トラブルシューティング

### Mixinが適用されない

1. Minecraftログを確認し、MODが正しく読み込まれているか確認
2. Techguns MODがインストールされているか確認
3. Java 8以降を使用しているか確認

### 音がまだ左に偏る

1. このMODとTechguns MODの両方が読み込まれているか確認
2. 他の音声関連MODとの競合がないか確認
3. Issueを報告してください

## 貢献

バグ報告や機能リクエストは、GitHubのIssuesで受け付けています。
プルリクエストも歓迎します。

## 参考リソース

- [UniMixins](https://github.com/LegacyModdingMC/UniMix)
- [Mixin Wiki](https://github.com/SpongePowered/Mixin/wiki)
- [GTNH ExampleMod](https://github.com/GTNewHorizons/ExampleMod1.7.10)
