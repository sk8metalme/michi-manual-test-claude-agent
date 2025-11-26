# Project Structure

## Organization Philosophy

**Hexagonal Architecture (Ports & Adapters) による層分離**

プロジェクトは、ドメイン層を中心とした同心円状の層構造を採用する。外部層は内部層に依存するが、内部層は外部層を知らない（依存性逆転の原則）。各層の責務を明確に分離し、テスト容易性と保守性を最大化する。

## Directory Patterns

### Domain Layer (`/domain/`)
**Location**: `/src/main/java/com/example/tetris/domain/`
**Purpose**: ビジネスロジックとドメインエンティティ。外部依存ゼロの純粋Java
**Example**:
- `Tetromino.java` - テトリミノの状態と操作ロジック（Record）
- `GameField.java` - フィールド状態管理、衝突判定、ライン消去（Record）
- `GameState.java` - ゲーム状態の集約ルート（Aggregate Root）
- `ScoreCalculator.java` - スコア計算（Pure Function）

**原則**: Spring注釈不可。`@Service`, `@Component`等のフレームワーク依存を禁止

### Application Layer (`/application/`)
**Location**: `/src/main/java/com/example/tetris/application/`
**Purpose**: ユースケース実装。ドメインをオーケストレーションし、ビジネスフローを実現
**Example**:
- `StartGameUseCase.java` - ゲーム開始ユースケース
- `MoveTetrominoUseCase.java` - テトリミノ移動ユースケース
- `SaveScoreUseCase.java` - スコア保存ユースケース

**原則**: ポート（インターフェース）に依存。具体的なアダプター実装を知らない

### Adapter Layer - Inbound (`/adapter/inbound/`)
**Location**: `/src/main/java/com/example/tetris/adapter/inbound/`
**Purpose**: 外部からの入力（REST API、WebSocket）をユースケースに変換
**Example**:
- `GameRestController.java` - REST APIエンドポイント（`@RestController`）
- `GameWebSocketController.java` - WebSocketエンドポイント（`@Controller` + STOMP）

**原則**: Springフレームワーク依存OK。ドメインを直接呼ばず、Application Layerを経由

### Adapter Layer - Outbound (`/adapter/outbound/`)
**Location**: `/src/main/java/com/example/tetris/adapter/outbound/`
**Purpose**: 外部システム（データベース、外部API）へのアクセス実装
**Example**:
- `ScoreRepositoryAdapter.java` - JPAリポジトリ実装（`@Repository`、`JpaRepository`継承）

**原則**: Application Layerが定義したポート（インターフェース）を実装

### Presentation Layer - Frontend (`/frontend/`)
**Location**: `/src/main/frontend/`
**Purpose**: TypeScript + HTML5 Canvasでゲーム画面描画とユーザー入力処理
**Example**:
- `GameCanvas.ts` - Canvas描画ロジック
- `InputHandler.ts` - キーボード入力処理
- `WebSocketClient.ts` - STOMP over WebSocket通信

**原則**: バックエンドのゲームロジックに依存せず、描画と入力処理に専念

## Naming Conventions

### Java
- **Classes**: PascalCase（例: `Tetromino`, `GameState`, `ScoreCalculator`）
- **Interfaces (Ports)**: PascalCase + `Port`接尾辞（例: `ScoreRepositoryPort`）
- **Adapters**: PascalCase + `Adapter`接尾辞（例: `ScoreRepositoryAdapter`）
- **Use Cases**: PascalCase + `UseCase`接尾辞（例: `StartGameUseCase`）
- **Records**: PascalCase（例: `GameStateDTO`, `Position`, `Rotation`）
- **Enums**: PascalCase（例: `TetrominoType`, `GameStatus`, `Direction`）

### TypeScript
- **Classes**: PascalCase（例: `GameCanvas`, `InputHandler`）
- **Interfaces**: PascalCase（例: `GameCanvasState`, `WebSocketClient`）
- **Variables/Functions**: camelCase（例: `gameState`, `handleKeyDown`）
- **Constants**: UPPER_SNAKE_CASE（例: `BASE_DROP_INTERVAL_MS`）

### Files
- **Java**: クラス名と一致（`Tetromino.java`, `GameState.java`）
- **TypeScript**: クラス名と一致（`GameCanvas.ts`, `InputHandler.ts`）

## Import Organization

### Java
```java
// 1. Java標準ライブラリ
import java.util.List;
import java.time.LocalDateTime;

// 2. 外部ライブラリ（Spring等）
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

// 3. 同一プロジェクト（内側から外側の順）
import com.example.tetris.domain.Tetromino;
import com.example.tetris.application.MoveTetrominoUseCase;
import com.example.tetris.adapter.inbound.GameRestController;
```

### TypeScript
```typescript
// 1. 外部ライブラリ
import { Client, Message } from '@stomp/stompjs';

// 2. プロジェクト内（相対パス優先）
import { GameStateDTO } from './types/GameStateDTO';
import { WebSocketClient } from './WebSocketClient';
```

**Path Aliases**: なし（シンプルな相対パスで十分）

## Code Organization Principles

### Dependency Rule（依存性の方向）
```
Presentation → Adapter (Inbound) → Application → Domain
                                        ↑
                                Adapter (Outbound)
```

- **Domain**: 外部に依存しない（Pure Java）
- **Application**: Domainに依存。Adapterは知らない（ポートに依存）
- **Adapter**: ApplicationとDomainに依存。外部フレームワーク使用可
- **Presentation**: Adapter (Inbound)に依存（REST API、WebSocket経由）

### Interface Segregation (ISP)

ポート（インターフェース）は最小限の責務を持つ。クライアントが不要なメソッドに依存しない。

**Good Example**:
```java
public interface ScoreRepositoryPort {
    ScoreEntity save(ScoreEntity entity);
    List<ScoreEntity> findTop10ByOrderByScoreDesc();
}
```

**Bad Example**:
```java
public interface ScoreRepositoryPort {
    // 不要なCRUDメソッドを全て定義
    ScoreEntity save(...);
    ScoreEntity findById(...);
    List<ScoreEntity> findAll();
    void delete(...);
    // ... 使わないメソッドが多数
}
```

### Rich Domain Model

ドメインエンティティにビジネスロジックを含める（Anemic Domain Modelを避ける）。

**Good Example**:
```java
public record Tetromino(...) {
    public Tetromino moveLeft() { /* ロジック */ }
    public Tetromino rotateClockwise() { /* ロジック */ }
    public List<Position> getBlockPositions() { /* ロジック */ }
}
```

**Bad Example** (Anemic):
```java
public record Tetromino(TetrominoType type, Position position, Rotation rotation) {
    // ロジックなし、データのみ
}

// 別のサービスクラスにロジックが散在
public class TetrominoService {
    public Tetromino moveLeft(Tetromino t) { /* ... */ }
    public Tetromino rotate(Tetromino t) { /* ... */ }
}
```

---
_Document patterns, not file trees. New files following patterns shouldn't require updates_
