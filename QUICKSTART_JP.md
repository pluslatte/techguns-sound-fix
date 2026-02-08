# クイックスタートガイド

## 前提条件

- Java 8 (JDK 1.8)
- Minecraft 1.7.10
- Forge 10.13.4.1614以降

## ビルド手順

### 1. 初回セットアップ

```bash
cd /home/sadalsuud/jdk8-dev/techguns-sound-fix
./gradlew setupDecompWorkspace
```

このコマンドは初回のみ実行が必要です。MinecraftとForgeの開発環境をセットアップします。

### 2. ビルド

```bash
./gradlew build
```

成功すると以下のファイルが生成されます:

- `build/libs/techgunssoundfix-1.0.0.jar` - 実行可能なMODファイル
- `build/libs/techgunssoundfix-1.0.0-sources.jar` - ソースコード
- `build/libs/techgunssoundfix-1.0.0-dev.jar` - 開発用

### 3. 開発環境でのテスト（オプション）

```bash
./gradlew runClient
```

これにより、MODがロードされた状態でMinecraftクライアントが起動します。
ただし、Techguns MODは別途インストールする必要があります。

## インストールとテスト

### 必要なMOD

1. **UniMixins** (0.2.1以降)
   - ダウンロード: https://github.com/LegacyModdingMC/UniMixins/releases
   - ファイル名の先頭に`+`または`!`が付いているものを選択
   - 例: `+unimixins-all-1.7.10-0.2.1.jar`

2. **Techguns MOD**
   - CurseForge: https://www.curseforge.com/minecraft/mc-mods/techguns
   - Minecraft 1.7.10対応版をダウンロード

3. **このMOD**
   - `build/libs/techgunssoundfix-1.0.0.jar`

### インストール手順

1. `.minecraft/mods/`フォルダを開く
2. 以下のファイルを配置:
   ```
   mods/
   ├── +unimixins-all-1.7.10-0.2.1.jar
   ├── techguns-[version].jar
   └── techgunssoundfix-1.0.0.jar
   ```
3. Minecraft 1.7.10 (Forge)を起動

### 動作確認

#### 1. 起動確認

Minecraftログ(`logs/fml-client-latest.log`)で以下を確認:

```
[INFO] [techgunssoundfix] Techguns Sound Fix loaded - fixing gun sound positioning
```

#### 2. Mixin適用確認

ログで以下のようなメッセージを探す:

```
[INFO] [mixin] Mixing MixinTGSound from mixins.techgunssoundfix.json into techguns.client.audio.TGSound
```

#### 3. 音の位置テスト

1. Techgunsの銃を取得
2. 以下の方向で射撃して音の方向を確認:
   - **前方**: 音が前から聞こえるか
   - **後方**: 音が後ろから聞こえるか
   - **左側**: 音が左から聞こえるか
   - **右側**: 音が右から聞こえるか

修正前は全ての音が左に偏っていましたが、修正後は正しい方向から聞こえるはずです。

## トラブルシューティング

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
