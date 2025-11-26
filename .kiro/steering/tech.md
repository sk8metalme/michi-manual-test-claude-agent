# Technology Stack

## Architecture

**Hexagonal Architecture (Ports & Adapters / Onion Architecture)**

ドメインロジックを中心に配置し、外部システム（UI、データベース、外部サービス）をポート（インターフェース）とアダプター（実装）で分離する設計パターンを採用。依存性逆転の原則（DIP）により、ドメイン層は外部層に依存しない。

**主要レイヤー**:
- **Domain**: 純粋Java、外部依存ゼロ
- **Application**: ユースケース、ドメインのオーケストレーション
- **Adapter (Inbound)**: REST API、WebSocket
- **Adapter (Outbound)**: データベースリポジトリ
- **Presentation**: TypeScript + HTML5 Canvas

## Core Technologies

- **Language (Backend)**: Java 17
- **Framework (Backend)**: Spring Boot 3.x
- **Language (Frontend)**: TypeScript 5.x
- **Rendering**: HTML5 Canvas (2D Context)
- **Runtime**: JVM 17+, Node.js (TypeScript開発環境)

## Key Libraries

- **Spring Web MVC**: REST APIエンドポイント提供
- **Spring WebSocket**: リアルタイム双方向通信（STOMP over WebSocket）
- **Spring Data JPA**: データベース永続化レイヤー
- **JUnit 5 + Mockito**: 単体テスト・統合テスト
- **JaCoCo**: コードカバレッジ測定

## Development Standards

### Type Safety

- **Java**: Records、Sealed Classes、Pattern Matchingを活用した型安全設計
- **TypeScript**: 型安全性を最大化。`any`型の禁止、厳密型チェック有効化
- **ドメインモデル**: イミュータブル設計（Recordsベース）でスレッドセーフ性確保

### Code Quality

- **ドメイン層の純粋性**: Spring注釈やフレームワーク依存を排除。純粋Javaでビジネスロジック実装
- **依存性の方向**: 常に内側（ドメイン）に向かう。外部層がドメインを呼び出し、逆はなし
- **インターフェース優先**: ポート（インターフェース）を定義し、アダプター（実装）で具体化

### Testing

- **TDD推奨**: テスト駆動開発でドメインロジックを検証
- **カバレッジ目標**: 80%以上（特にドメイン層とアプリケーション層）
- **モック戦略**: ドメイン層は外部依存なしで単体テスト可能

## Development Environment

### Required Tools

- **Java**: JDK 17以上（OpenJDK、Oracle JDK、Amazon Corretto等）
- **Gradle**: 8.x（ビルドツール、依存関係管理）
- **Node.js**: 20+ (TypeScriptトランスパイル用)
- **Database**: H2 (dev/test用組み込み)、PostgreSQL 15+ (本番用)

### Common Commands

```bash
# Dev (Backend): Spring Bootアプリケーション起動
./gradlew bootRun

# Build: プロジェクト全体ビルド（TypeScript含む）
./gradlew build

# Test: 単体テスト・統合テスト実行
./gradlew test

# Coverage: コードカバレッジレポート生成
./gradlew jacocoTestReport
```

## Key Technical Decisions

### WebSocket vs REST API

- **WebSocket (STOMP)**: リアルタイム操作（移動、回転、自動落下）に使用。100ms以内のレスポンスタイム要件を満たす低レイテンシ
- **REST API**: ステートレス操作（ゲーム開始、スコア履歴取得）に使用

**理由**: ゲーム操作の頻度が高く、双方向通信が必要なためWebSocketを選定。REST APIのPollingはネットワークトラフィック増大とレイテンシの問題がある

### Database Strategy

- **開発・テスト**: H2組み込みデータベース（インメモリモード）
- **本番**: PostgreSQL 15+

**切り替え**: Spring Profilesで環境ごとに自動選択（`application-dev.yml`, `application-prod.yml`）

**理由**: 開発環境では素早いセットアップ、本番環境では信頼性・パフォーマンス確保を優先

### TypeScript + Canvas

- **TypeScript**: 型安全性によりフロントエンド開発時のエラー早期検出。Javaの型システムと設計思想一致
- **HTML5 Canvas**: ゲーム描画の標準技術。60 FPS要件を満たす`requestAnimationFrame`使用

**理由**: React等のフレームワークは今回のゲームロジックには過剰。状態はバックエンドで管理し、フロントエンドは描画とユーザー入力処理に専念

---
_Document standards and patterns, not every dependency_
