# Research & Design Decisions

---
**Purpose**: テトリスWebアプリケーションの技術設計における発見事項、アーキテクチャ調査、設計根拠を記録する

**Usage**:
- 発見フェーズでの調査活動と成果を記録
- `design.md`には詳細すぎる設計決定のトレードオフを文書化
- 将来の監査や再利用のための参考資料と証拠を提供
---

## Summary
- **Feature**: `tetris-web-app`
- **Discovery Scope**: New Feature（新規機能）
- **Key Findings**:
  - Spring Boot 3.5でのヘキサゴナル/オニオンアーキテクチャ実装パターンを確認
  - HTML5 Canvas + TypeScriptがWebテトリスゲームの標準技術スタックと判明
  - Java 17の新機能（Records、Sealed Classes、Pattern Matching）が設計を簡潔化

## Research Log

### Spring Bootでのオニオン/ヘキサゴナルアーキテクチャ実装
- **Context**: Requirement 8でオニオンアーキテクチャの適用が要求されている。Spring Boot 3環境での実装ベストプラクティスを調査
- **Sources Consulted**:
  - [Hexagonal Architecture in Spring Boot: A Practical Guide](https://dev.to/jhonifaber/hexagonal-architecture-or-port-adapters-23ed)
  - [Onion Architecture With Spring Boot](https://blog.mimacom.com/onion-architecture-spring-boot/)
  - [Hexagonal Architecture in Spring Boot Microservices](https://dev.to/rock_win_c053fa5fb2399067/hexagonal-architecture-in-spring-boot-microservices-a-complete-guide-with-folder-structure-1jld)
- **Findings**:
  - ヘキサゴナルアーキテクチャ（Alistair Cockburn, 2005）は別名「ポート＆アダプター」または「オニオンアーキテクチャ」
  - 標準的なパッケージ構造：
    - `domain`（ビジネスエンティティとロジック、Spring注釈なし）
    - `application/usecase`（インバウンドポート、ユースケース定義）
    - `adapter/inbound`（RESTコントローラー、Kafkaリスナーなど）
    - `adapter/outbound`（データベースアダプター、RESTクライアントなど）
    - `config`（Spring設定）
  - ドメイン層は技術フレームワークから完全に分離（依存性逆転の原則）
  - ポート（インターフェース）とアダプター（実装）で外部システムと分離
  - 主要な利点：関心の分離、スケーラビリティ、柔軟性、テスト容易性
- **Implications**:
  - ドメイン層でテトリスゲームロジック（テトリミノ、フィールド、スコア計算）を純粋Javaで実装
  - アプリケーション層でゲーム操作ユースケース（移動、回転、ライン消去）を定義
  - インバウンドアダプターでREST APIとWebSocketを提供（プレゼンテーション層）
  - アウトバウンドアダプターでデータベースアクセス（永続化層）を実装

### Webテトリスゲームのフロントエンド技術スタック
- **Context**: Requirement 6でゲーム画面のUI実装が必要。最新のWeb技術選定を調査
- **Sources Consulted**:
  - [Creating Tetris Game with HTML, CSS, Canvas, and JavaScript](https://dev.to/sharathchandark/creating-tetris-game-with-html-css-canvas-and-javascript-complete-guide-2i6h)
  - [JavaScript tutorial: Build Tetris with modern JavaScript](https://www.educative.io/blog/javascript-tutorial-build-tetris)
  - [TypeScript Tetris](https://www.markheath.net/post/typescript-tetris)
- **Findings**:
  - HTML5 CanvasがWebテトリスゲーム実装の標準技術
  - `HTMLCanvasElement.getContext('2d')`で2Dレンダリングコンテキストを取得
  - JavaScriptまたはTypeScriptで実装可能
  - TypeScript利点：型安全性、クラス構文の簡潔化、保守性向上
  - 60 FPS描画のために`requestAnimationFrame`を使用
  - ブロック移動・回転のアニメーション、ゴースト表示、ネクストブロックプレビューの実装例多数
- **Implications**:
  - フロントエンドはHTML5 Canvas + TypeScriptで実装
  - ゲーム状態はバックエンドで管理、フロントエンドは描画とユーザー入力処理に専念
  - REST APIまたはWebSocketでゲーム状態を同期
  - 60 FPS要件（Requirement 10）は`requestAnimationFrame`で実現可能

### Spring Boot 3.5 + Java 17の機能と互換性
- **Context**: Requirement 9でJava 17以上、Spring Boot 3.5系の使用が要求されている。新機能と互換性を確認
- **Sources Consulted**:
  - [What's New in Spring Boot 3](https://medium.com/@sunda.nitsri/whats-new-in-spring-boot-3-a-guide-to-the-latest-features-and-improvements-616b40f9ba3b)
  - [Spring Boot 3.0 Release Notes](https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-3.0-Release-Notes)
  - [Intro to WebSockets with Spring](https://www.baeldung.com/websockets-spring)
- **Findings**:
  - Spring Boot 3.0はJava 17を最小バージョンとして要求
  - Java 17の新機能利用可能：
    - Records（イミュータブルデータクラス）
    - Sealed Classes（継承制御による型安全性向上）
    - Pattern Matching for switch（型安全な分岐処理）
  - Spring Boot 3の新機能：
    - ファーストクラスのREST APIバージョニング
    - Jakarta EE 11ベースライン（Jakarta WebSocket 2.2）
    - JSpecify注釈による標準化されたnull安全性
    - WebClientの自動設定（JDK HttpClient）
  - WebSocketサポート：Spring WebSocketモジュールでSTOMPプロトコル利用可能
- **Implications**:
  - ドメインエンティティ（Tetromino、GameField、Score）をJava Recordsで実装可能
  - ゲーム状態の型安全性をSealed Classesで強化
  - リアルタイムゲーム更新にWebSocketを活用（低レイテンシ）
  - REST APIでゲーム開始、スコア保存、履歴取得を実装
  - Spring Data JPAでH2またはPostgreSQLへの永続化

## Architecture Pattern Evaluation

| Option | Description | Strengths | Risks / Limitations | Notes |
|--------|-------------|-----------|---------------------|-------|
| Hexagonal (Ports & Adapters) | ドメインロジックをポート（インターフェース）で分離し、アダプターで外部システムと接続 | 明確な境界、テスト容易性、技術スタック変更が容易 | アダプター層の構築オーバーヘッド | Requirement 8のオニオンアーキテクチャ要件と一致 |
| Layered (Traditional) | プレゼンテーション→ビジネス→データアクセス層の3層構造 | シンプル、Spring Bootの標準的なアプローチ | ドメインロジックがフレームワークに依存しやすい | 要件のオニオンアーキテクチャと不一致 |
| MVC (Model-View-Controller) | モデル、ビュー、コントローラーでUI中心の分離 | UIとロジックの分離 | ゲームロジックの複雑性に対応しにくい | 今回のゲーム要件には不適合 |

**選定**: Hexagonal (Ports & Adapters) アーキテクチャ
**理由**: Requirement 8の明示的な要求、ドメインロジックの純粋性維持、テスト容易性

## Design Decisions

### Decision: フロントエンド技術スタック選定
- **Context**: Requirement 6でゲーム画面の実装が必要。ユーザー入力、60 FPS描画、レスポンシブデザインを実現する技術選定
- **Alternatives Considered**:
  1. Vanilla JavaScript + Canvas — シンプル、学習コスト低
  2. TypeScript + Canvas — 型安全性、保守性高
  3. React + Canvas — コンポーネント化、状態管理容易
- **Selected Approach**: TypeScript + HTML5 Canvas
- **Rationale**:
  - 型安全性により開発時のエラー検出が早期化
  - Java 17のRecord、Sealed Classesと設計思想が一致（型安全）
  - Canvas APIはゲーム描画の標準、60 FPS要件を満たす
  - Reactは今回のゲームロジックには過剰（状態はバックエンドで管理）
- **Trade-offs**:
  - メリット：型安全性、保守性、パフォーマンス
  - デメリット：TypeScriptのビルドプロセスが必要
- **Follow-up**: TypeScriptトランスパイルをGradleビルドプロセスに統合する必要性を実装時に検証

### Decision: リアルタイム通信プロトコル（WebSocket vs Polling）
- **Context**: Requirement 2で1秒間隔のテトリミノ自動落下、Requirement 10で100ms以内のレスポンスタイムが要求
- **Alternatives Considered**:
  1. WebSocket — 双方向通信、低レイテンシ、リアルタイム
  2. Server-Sent Events (SSE) — サーバー→クライアント単方向
  3. HTTP Polling — シンプル、互換性高
- **Selected Approach**: WebSocket（STOMP over WebSocket）
- **Rationale**:
  - 双方向通信でユーザー操作（移動、回転）を即座にサーバーに送信
  - サーバーからゲーム状態更新を低レイテンシで配信
  - Spring WebSocketモジュールでSTOMPプロトコルサポート
  - 100ms以内のレスポンスタイム要件を満たす
  - HTTP Pollingは不要なネットワークトラフィック生成
- **Trade-offs**:
  - メリット：低レイテンシ、リアルタイム性、双方向通信
  - デメリット：接続管理の複雑性、ファイアウォール通過問題の可能性
- **Follow-up**: WebSocket接続切断時のフォールバック戦略（自動再接続）を実装

### Decision: データベース選定（H2 vs PostgreSQL）
- **Context**: Requirement 9で「組み込みH2データベースまたはPostgreSQLをサポート」が要求
- **Alternatives Considered**:
  1. H2のみ — 開発・テスト環境で簡単
  2. PostgreSQLのみ — 本番環境向け堅牢性
  3. Spring Profilesで切り替え — 環境ごとに最適なDB使用
- **Selected Approach**: Spring ProfilesでH2（dev/test）とPostgreSQL（prod）を切り替え
- **Rationale**:
  - 開発環境では組み込みH2で素早くセットアップ
  - 本番環境ではPostgreSQLで信頼性・パフォーマンス確保
  - Spring Boot 3のAuto-configurationで切り替え容易
  - JPA使用により実装コードはDB非依存
- **Trade-offs**:
  - メリット：開発効率と本番品質の両立
  - デメリット：2つのDBで動作確認が必要
- **Follow-up**: DB固有のSQL方言に依存しない実装を徹底

### Decision: ドメインモデルの設計（Entityパターン）
- **Context**: Requirement 1-5のゲームロジックをドメイン層で実装する必要
- **Alternatives Considered**:
  1. Anemic Domain Model — エンティティはデータのみ、サービス層にロジック
  2. Rich Domain Model — エンティティにビジネスロジックを含む
- **Selected Approach**: Rich Domain Model
- **Rationale**:
  - テトリスのゲームロジック（移動、回転、衝突判定、ライン消去）はビジネスルールそのもの
  - ドメインエンティティ（Tetromino、GameField）にロジックを配置することで凝集度向上
  - テスト容易性：外部依存なしでドメインロジックを単体テスト可能
  - オニオンアーキテクチャの原則に一致
- **Trade-offs**:
  - メリット：高凝集、低結合、テスト容易
  - デメリット：初期設計の複雑性
- **Follow-up**: Java Recordsをイミュータブルなドメインエンティティとして活用

## Risks & Mitigations

### Risk 1: WebSocket接続の安定性
- **リスク**: ネットワーク不安定時のWebSocket接続切断によるゲーム中断
- **軽減策**:
  - 自動再接続メカニズムの実装
  - 接続切断時のローカルバッファリングとリトライ
  - フォールバックとしてHTTP Pollingのサポート（オプション）

### Risk 2: 60 FPS描画のパフォーマンス
- **リスク**: 複雑な描画処理により60 FPSを維持できない可能性
- **軽減策**:
  - `requestAnimationFrame`の適切な使用
  - Canvas描画の最適化（差分更新のみ）
  - パフォーマンステストによる早期検出

### Risk 3: 同時ユーザーセッション管理
- **リスク**: 5ユーザー同時プレイ（Requirement 10）でサーバーメモリ不足
- **軽減策**:
  - ゲーム状態をセッションスコープで管理
  - 非アクティブセッションのタイムアウト処理
  - 負荷テストによる閾値確認

### Risk 4: テストカバレッジ80%の達成
- **リスク**: ドメインロジックとアダプター層の網羅的テストが困難
- **軽減策**:
  - TDD（Test-Driven Development）の採用
  - JUnit 5 + Mockito + Spring Boot Testの活用
  - JaCoCo（Javaコードカバレッジツール）で継続的モニタリング

## References

### Architecture & Design Patterns
- [Hexagonal Architecture in Spring Boot: A Practical Guide](https://dev.to/jhonifaber/hexagonal-architecture-or-port-adapters-23ed) — ポート＆アダプターの実装ガイド
- [Onion Architecture With Spring Boot](https://blog.mimacom.com/onion-architecture-spring-boot/) — Spring Bootでのオニオンアーキテクチャ実践
- [Hexagonal Architecture Template with Java and Spring Boot](https://kamilmazurek.pl/hexagonal-architecture-template) — プロジェクトテンプレート

### Frontend Technology
- [Creating Tetris Game with HTML, CSS, Canvas, and JavaScript](https://dev.to/sharathchandark/creating-tetris-game-with-html-css-canvas-and-javascript-complete-guide-2i6h) — Canvas実装の完全ガイド
- [TypeScript Tetris](https://www.markheath.net/post/typescript-tetris) — TypeScript実装の実例

### Spring Boot & Java
- [What's New in Spring Boot 3](https://medium.com/@sunda.nitsri/whats-new-in-spring-boot-3-a-guide-to-the-latest-features-and-improvements-616b40f9ba3b) — Spring Boot 3の新機能
- [Spring Boot 3.0 Release Notes](https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-3.0-Release-Notes) — 公式リリースノート
- [Intro to WebSockets with Spring](https://www.baeldung.com/websockets-spring) — Spring WebSocketの実装ガイド
