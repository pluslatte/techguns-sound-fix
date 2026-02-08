# クイックスタートガイド

## 前提条件

- Java 8 (JDK 1.8)
- Minecraft 1.7.10
- Forge 10.13.4.1614以降

## ビルド手順

### 1. Techguns MODの準備 ★重要★

**ビルドにはTechguns MODのJARファイルが必要です。**

1. Techguns MODをダウンロード（CurseForge等から）
2. プロジェクトルートに`libs/`ディレクトリを作成（存在しない場合）
3. Techguns MODのJARファイルを`libs/`に配置

例：

```
techguns-sound-fix/
├── libs/
│   └── Techguns.beta.1.2_alphatest4.1.jar  ← ここに配置
├── src/
├── build.gradle
└── ...
```

### 2. ビルド

```bash
./gradlew build
```

成功すると`build/libs/techgunssoundfix-1.0.0.jar`が生成されます。

## インストール方法

### 必要なMOD

1. **UniMixins** (0.2.1以降)
   - https://github.com/LegacyModdingMC/UniMixins/releases
   - ファイル名の先頭に`+`が付いているもの（例：`+unimixins-all-1.7.10-0.2.1.jar`）

2. **Techguns MOD**
   - https://www.curseforge.com/minecraft/mc-mods/techguns
   - Minecraft 1.7.10版

3. **このMOD**
   - `build/libs/techgunssoundfix-1.0.0.jar`

### インストール手順

`.minecraft/mods/`フォルダに以下を配置:

```
mods/
├── +unimixins-all-1.7.10-0.2.1.jar
├── techguns-[version].jar
└── techgunssoundfix-1.0.0.jar
```

Minecraft 1.7.10 (Forge)を起動します。

## 動作確認

### 1. ログ確認

`logs/fml-client-latest.log`で以下を確認:

```
[INFO] [techgunssoundfix] Techguns Sound Fix loaded
[INFO] [mixin] Mixing MixinTGSound from mixins.techgunssoundfix.json
```

### 2. ゲーム内テスト

銃を前後左右に向けて射撃し、音が正しい方向から聞こえることを確認します。
修正前は全て左から聞こえていましたが、修正後は正しい方向から聞こえます。

## トラブルシューティング

### ビルドエラー: Techgunsクラスが見つからない

→ `libs/`ディレクトリにTechguns MODのJARファイルを配置してください。

### Mixin適用エラー

→ UniMixins MODが正しくインストールされているか確認してください。

### ビルドエラー

**エラー**: `Could not resolve com.github.LegacyModdingMC.UniMixins:unimixins-all-1.7.10:0.2.1`

**解決策**: インターネット接続を確認し、JitPackリポジトリにアクセスできることを確認してください。

### 起動エラー

**エラー**: `java.lang.ClassNotFoundException: com.pluslatte.techgunssoundfix.mixins.TechgunsSoundFixMixinPlugin`

**解決策**: ビルドが正常に完了していることを確認し、生成されたJARファイルを使用してください。

**エラー**: `Mixin transformation of techguns.client.audio.TGSound failed`

**解決策**:

1. UniMixinsがインストールされているか確認
2. Techgunsのバージョンが対応しているか確認

### 音が修正されない

**チェック項目**:

1. ログでMixinが適用されていることを確認
2. UniMixinsのJARファイルが正しく配置されているか確認
3. 他の音MODとの競合を確認

## クリーンビルド

問題が発生した場合、クリーンビルドを試してください:

```bash
./gradlew clean
./gradlew setupDecompWorkspace
./gradlew build
```

## ログの確認

問題が発生した場合、以下のログを確認してください:

- `logs/fml-client-latest.log` - 最新のクライアントログ
- `logs/latest.log` - 最新のゲームログ

Mixinに関するメッセージを探す場合:

```bash
grep -i "mixin" logs/fml-client-latest.log
```

## 開発者向けTips

### Eclipseセットアップ

```bash
./gradlew eclipse
```

### IntelliJ IDEAセットアップ

```bash
./gradlew genIntellijRuns
```

その後、IntelliJ IDEAでプロジェクトをインポートしてください。

### デバッグモード

Mixinのデバッグ情報を有効にするには、JVM引数に以下を追加:

```
-Dmixin.debug.verbose=true
-Dmixin.debug.export=true
```

## 質問とサポート

問題が発生した場合:

1. [DEVELOPER_GUIDE_JP.md](DEVELOPER_GUIDE_JP.md)を参照
2. GitHubのIssueを作成
3. ログファイルを添付

---

**注意**: このMODはTechguns MODのバグを修正するためのパッチです。
Techguns MODがアップデートされた場合、このMODの動作を再確認してください。
