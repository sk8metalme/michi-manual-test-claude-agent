# タスク分割: tetris-web-app

## プロジェクト情報

- **機能名**: tetris-web-app
- **開始予定日**: 2025-11-26 Day 1
- **注意**: このタスク分割はAI-DLC形式から自動変換されました

## Phase 0.1: 要件定義（Requirements）

**期間**: Day 1
**工数**: 0.5人日
**ステータス**: 完了

### Story 0.1.1: 要件定義書作成
- **担当**: @PM
- **工数**: 0.5人日
- **説明**: 機能の要件定義書を作成
- **成果物**:
  - `.kiro/specs/tetris-web-app/requirements.md`
- **受け入れ基準**:
  - [x] 要件定義完了

## Phase 0.2: 設計（Design）

**期間**: Day 1-2
**工数**: 0.5-1.0人日
**ステータス**: 完了

### Story 0.2.1: 基本設計
- **担当**: @Architect
- **工数**: 0.5-1.0人日
- **説明**: アーキテクチャ設計、API設計を実施
- **成果物**:
  - `.kiro/specs/tetris-web-app/design.md`
- **受け入れ基準**:
  - [x] 設計完了

## Phase 1: 環境構築（Environment Setup）

**期間**: Day 3-5
**工数**: 1.5-3人日
**ステータス**: 未着手

### Story 1.1: Gradle + Spring Bootプロジェクト初期化
- **担当**: @Developer
- **工数**: 1人日
- **説明**: Spring Boot 3.x + Java 17プロジェクトをGradleで作成
- **タスク**:
  - [x] Task 1.1.1: Spring Boot 3.x + Java 17プロジェクトをGradleで作成
  - [x] Task 1.1.2: 依存関係追加（Spring Web, Spring WebSocket, Spring Data JPA, H2, Lombok）
  - [x] Task 1.1.3: パッケージ構造作成（domain, application, adapter.inbound, adapter.outbound, presentation）
  - [x] Task 1.1.4: application.ymlで基本設定（サーバーポート、ログレベル）
  - [x] Task 1.1.5: ビルド成功とアプリケーション起動確認
- **受け入れ基準**:
  - [x] 実装完了
  - [x] 単体テスト作成・通過
  - [x] 要件充足: 9.1, 9.2, 9.6, 8.1, 8.2, 8.3, 8.4

### Story 1.2: データベース設定とSpring Profile構成
- **担当**: @Developer
- **工数**: 1人日
- **説明**: H2組み込みデータベース設定（dev/testプロファイル）
- **タスク**:
  - [x] Task 1.2.1: H2組み込みデータベース設定（dev/testプロファイル）
  - [x] Task 1.2.2: PostgreSQL設定追加（prodプロファイル）
  - [x] Task 1.2.3: scoresテーブルのDDLスクリプト作成（id, score, level, total_lines_cleared, timestamp）
  - [x] Task 1.2.4: スコア降順インデックス設定
  - [x] Task 1.2.5: JPA自動DDL生成設定（hibernate.ddl-auto: update）
  - [x] Task 1.2.6: 各プロファイルでのデータベース接続確認
- **受け入れ基準**:
  - [x] 実装完了
  - [x] 単体テスト作成・通過
  - [x] 要件充足: 9.3, 9.5, 7.1, 7.2

### Story 1.3: WebSocket + STOMP設定
- **担当**: @Developer
- **工数**: 1人日
- **説明**: Spring WebSocket依存関係追加
- **タスク**:
  - [x] Task 1.3.1: Spring WebSocket依存関係追加
  - [x] Task 1.3.2: WebSocketConfig作成（/ws/gameエンドポイント、STOMPメッセージブローカー）
  - [x] Task 1.3.3: STOMP送信先設定（/app/game/*, /topic/game/*）
  - [x] Task 1.3.4: CORS設定追加（フロントエンド接続許可）
  - [x] Task 1.3.5: WebSocket接続テスト実装
- **受け入れ基準**:
  - [x] 実装完了
  - [x] 単体テスト作成・通過
  - [x] 要件充足: 10.1

## Phase 2: TDD実装（TDD Implementation）

**期間**: Day 6-15
**工数**: 8.5-17人日
**ステータス**: 未着手

### Story 2.1: Tetrominoエンティティと操作ロジック実装
- **担当**: @Developer
- **工数**: 1.5人日
- **説明**: TetrominoType列挙型定義（I, O, T, S, Z, J, L）
- **タスク**:
  - [x] Task 2.1.1: TetrominoType列挙型定義（I, O, T, S, Z, J, L）
  - [x] Task 2.1.2: Position Recordクラス定義（x, y座標）
  - [x] Task 2.1.3: Rotation列挙型定義（DEG_0, DEG_90, DEG_180, DEG_270）
  - [x] Task 2.1.4: Tetromino Recordクラス実装（type, position, rotation）
  - [x] Task 2.1.5: 移動メソッド実装（moveLeft, moveRight, moveDown）
  - [x] Task 2.1.6: 回転メソッド実装（rotateClockwise）
  - [x] Task 2.1.7: ブロック座標取得メソッド実装（getBlockPositions: 4つの座標を返す）
  - [x] Task 2.1.8: 各操作の単体テスト作成（移動後座標検証、回転後向き検証）
- **受け入れ基準**:
  - [x] 実装完了
  - [x] 単体テスト作成・通過
  - [x] 要件充足: 1.1, 1.2, 1.3, 2.5
- **並列実行**: 可能

### Story 2.2: GameFieldエンティティと衝突判定実装
- **担当**: @Developer
- **工数**: 1.5人日
- **説明**: Block Recordクラス定義（TetrominoType type）
- **タスク**:
  - [x] Task 2.2.1: Block Recordクラス定義（TetrominoType type）
  - [x] Task 2.2.2: GameField Recordクラス実装（Block[][] grid: 10×20）
  - [x] Task 2.2.3: 衝突判定メソッド実装（canPlace: テトリミノ配置可否）
  - [x] Task 2.2.4: テトリミノ固定メソッド実装（place: 新しいGameFieldを返す）
  - [x] Task 2.2.5: ライン消去メソッド実装（clearLines: 横一列埋まり検出、消去、上部落下）
  - [x] Task 2.2.6: ClearResult Record定義（updatedField, clearedLineCount）
  - [x] Task 2.2.7: セル占有確認メソッド実装（isOccupied）
  - [x] Task 2.2.8: 単体テスト作成（境界判定、障害物判定、ライン消去ロジック）
- **受け入れ基準**:
  - [x] 実装完了
  - [x] 単体テスト作成・通過
  - [x] 要件充足: 1.6, 1.7, 2.3, 3.1, 3.2, 6.1
- **並列実行**: 可能

### Story 2.3: GameStateアグリゲートルート実装
- **担当**: @Developer
- **工数**: 2人日
- **説明**: GameStatus列挙型定義（PLAYING, GAME_OVER）
- **タスク**:
  - [ ] Task 2.3.1: GameStatus列挙型定義（PLAYING, GAME_OVER）
  - [ ] Task 2.3.2: Direction列挙型定義（LEFT, RIGHT, DOWN）
  - [ ] Task 2.3.3: GameStateクラス実装（status, currentTetromino, nextTetromino, field, score, level, totalLinesCleared）
  - [ ] Task 2.3.4: ゲーム初期化メソッド実装（initialize: ランダムテトリミノ生成）
  - [ ] Task 2.3.5: テトリミノ移動メソッド実装（moveTetromino: 衝突判定後に移動）
  - [ ] Task 2.3.6: テトリミノ回転メソッド実装（rotateTetromino: 衝突判定後に回転）
  - [ ] Task 2.3.7: ハードドロップメソッド実装（hardDrop: 即座に底まで落下）
  - [ ] Task 2.3.8: 自動落下処理メソッド実装（processAutoDropTick: 1マス下に移動、固定時はライン消去）
  - [ ] Task 2.3.9: 落下間隔取得メソッド実装（getDropInterval: レベルに応じた速度）
  - [ ] Task 2.3.10: ゲームオーバー判定実装（新規テトリミノ出現位置に障害物）
  - [ ] Task 2.3.11: 単体テスト作成（状態遷移、ゲームオーバー条件）
- **受け入れ基準**:
  - [ ] 実装完了
  - [ ] 単体テスト作成・通過
  - [ ] 要件充足: 1.1, 1.2, 1.3, 1.4, 1.5, 1.6, 1.7, 2.1, 2.2, 2.3, 2.4, 5.1, 5.2

### Story 2.4: ScoreCalculatorロジック実装
- **担当**: @Developer
- **工数**: 1人日
- **説明**: ScoreCalculatorクラス実装（Pure Function）
- **タスク**:
  - [ ] Task 2.4.1: ScoreCalculatorクラス実装（Pure Function）
  - [ ] Task 2.4.2: スコア計算メソッド実装（calculateScore: 1行100点、2行300点、3行500点、4行800点）
  - [ ] Task 2.4.3: Pattern Matchingで消去ライン数別の加算値返却
  - [ ] Task 2.4.4: 単体テスト作成（0-4行の各ケース検証）
- **受け入れ基準**:
  - [ ] 実装完了
  - [ ] 単体テスト作成・通過
  - [ ] 要件充足: 3.3, 3.4, 3.5, 3.6
- **並列実行**: 可能

### Story 2.5: LevelManagerロジック実装
- **担当**: @Developer
- **工数**: 1人日
- **説明**: LevelManagerクラス実装（Pure Function）
- **タスク**:
  - [ ] Task 2.5.1: LevelManagerクラス実装（Pure Function）
  - [ ] Task 2.5.2: レベル計算メソッド実装（calculateLevel: 10ラインごとにレベル+1、最大レベル10）
  - [ ] Task 2.5.3: 落下間隔計算メソッド実装（calculateDropInterval: レベルごとに10%増速、初期1秒）
  - [ ] Task 2.5.4: 単体テスト作成（レベル遷移、落下速度計算）
- **受け入れ基準**:
  - [ ] 実装完了
  - [ ] 単体テスト作成・通過
  - [ ] 要件充足: 4.1, 4.2, 4.3, 4.5
- **並列実行**: 可能

### Story 2.6: StartGameUseCase実装
- **担当**: @Developer
- **工数**: 1人日
- **説明**: StartGameUseCaseインターフェース定義
- **タスク**:
  - [ ] Task 2.6.1: StartGameUseCaseインターフェース定義
  - [ ] Task 2.6.2: ユースケース実装クラス作成（GameStateを初期化）
  - [ ] Task 2.6.3: GameStateDTOレコード定義（status, currentTetromino, nextTetromino, field, score, level, totalLinesCleared）
  - [ ] Task 2.6.4: TetrominoDTO変換ロジック実装
  - [ ] Task 2.6.5: ドメインモデルからDTOへのマッピング実装
  - [ ] Task 2.6.6: 単体テスト作成（ゲーム開始時の初期状態検証）
- **受け入れ基準**:
  - [ ] 実装完了
  - [ ] 単体テスト作成・通過
  - [ ] 要件充足: 5.4
- **並列実行**: 可能

### Story 2.7: MoveTetrominoUseCase実装
- **担当**: @Developer
- **工数**: 1人日
- **説明**: MoveTetrominoUseCaseインターフェース定義（sessionId, direction）
- **タスク**:
  - [ ] Task 2.7.1: MoveTetrominoUseCaseインターフェース定義（sessionId, direction）
  - [ ] Task 2.7.2: ユースケース実装クラス作成（GameState.moveTetromino呼び出し）
  - [ ] Task 2.7.3: セッションIDベースのゲーム状態管理実装
  - [ ] Task 2.7.4: 衝突判定結果のハンドリング（移動可/不可）
  - [ ] Task 2.7.5: GameStateDTOへの変換
  - [ ] Task 2.7.6: 単体テスト作成（移動成功ケース、移動失敗ケース）
- **受け入れ基準**:
  - [ ] 実装完了
  - [ ] 単体テスト作成・通過
  - [ ] 要件充足: 1.1, 1.2, 1.4, 1.6
- **並列実行**: 可能

### Story 2.8: RotateTetrominoUseCase実装
- **担当**: @Developer
- **工数**: 1人日
- **説明**: RotateTetrominoUseCaseインターフェース定義（sessionId）
- **タスク**:
  - [ ] Task 2.8.1: RotateTetrominoUseCaseインターフェース定義（sessionId）
  - [ ] Task 2.8.2: ユースケース実装クラス作成（GameState.rotateTetromino呼び出し）
  - [ ] Task 2.8.3: 回転後の衝突判定結果ハンドリング
  - [ ] Task 2.8.4: GameStateDTOへの変換
  - [ ] Task 2.8.5: 単体テスト作成（回転成功ケース、回転失敗ケース）
- **受け入れ基準**:
  - [ ] 実装完了
  - [ ] 単体テスト作成・通過
  - [ ] 要件充足: 1.3, 1.7
- **並列実行**: 可能

### Story 2.9: ProcessAutoDropUseCase実装
- **担当**: @Developer
- **工数**: 1人日
- **説明**: ProcessAutoDropUseCaseインターフェース定義（sessionId）
- **タスク**:
  - [ ] Task 2.9.1: ProcessAutoDropUseCaseインターフェース定義（sessionId）
  - [ ] Task 2.9.2: ユースケース実装クラス作成（GameState.processAutoDropTick呼び出し）
  - [ ] Task 2.9.3: テトリミノ固定時のライン消去処理連携
  - [ ] Task 2.9.4: スコア加算とレベル更新ロジック統合
  - [ ] Task 2.9.5: GameStateDTOへの変換
  - [ ] Task 2.9.6: 単体テスト作成（自動落下、固定、ライン消去）
- **受け入れ基準**:
  - [ ] 実装完了
  - [ ] 単体テスト作成・通過
  - [ ] 要件充足: 2.2, 2.3, 2.4, 3.1, 3.2, 3.3, 3.4, 3.5, 3.6, 4.2, 4.3
- **並列実行**: 可能

### Story 2.10: SaveScoreUseCase実装
- **担当**: @Developer
- **工数**: 1.5人日
- **説明**: SaveScoreUseCaseインターフェース定義（GameStateDTO）
- **タスク**:
  - [ ] Task 2.10.1: SaveScoreUseCaseインターフェース定義（GameStateDTO）
  - [ ] Task 2.10.2: ScoreRepositoryPortインターフェース定義（save, findTop10ByOrderByScoreDesc）
  - [ ] Task 2.10.3: ユースケース実装クラス作成（ScoreEntityを生成、リポジトリに保存）
  - [ ] Task 2.10.4: ScoreDTO定義（id, score, level, totalLinesCleared, timestamp）
  - [ ] Task 2.10.5: タイムスタンプ自動付与ロジック
  - [ ] Task 2.10.6: @Transactional設定
  - [ ] Task 2.10.7: 単体テスト作成（スコア保存、DTO変換）
- **受け入れ基準**:
  - [ ] 実装完了
  - [ ] 単体テスト作成・通過
  - [ ] 要件充足: 7.1, 7.2
- **並列実行**: 可能

### Story 2.11: ScoreRepositoryAdapter実装
- **担当**: @Developer
- **工数**: 1人日
- **説明**: ScoreEntityクラス定義（@Entity, id, score, level, totalLinesCleared, timestamp）
- **タスク**:
  - [ ] Task 2.11.1: ScoreEntityクラス定義（@Entity, id, score, level, totalLinesCleared, timestamp）
  - [ ] Task 2.11.2: @Table(name = "scores")設定
  - [ ] Task 2.11.3: @GeneratedValue(strategy = IDENTITY)でID自動生成
  - [ ] Task 2.11.4: ScoreRepositoryAdapterインターフェース定義（JpaRepositoryとScoreRepositoryPort継承）
  - [ ] Task 2.11.5: findTop10ByOrderByScoreDesc()メソッド定義
  - [ ] Task 2.11.6: 統合テスト作成（H2でスコア保存、過去10件取得）
- **受け入れ基準**:
  - [ ] 実装完了
  - [ ] 単体テスト作成・通過
  - [ ] 要件充足: 7.1, 7.2, 7.3, 7.4, 9.3

### Story 2.12: GameRestController実装
- **担当**: @Developer
- **工数**: 1人日
- **説明**: GameRestControllerクラス作成（@RestController, @RequestMapping("/api/game")）
- **タスク**:
  - [ ] Task 2.12.1: GameRestControllerクラス作成（@RestController, @RequestMapping("/api/game")）
  - [ ] Task 2.12.2: POST /api/game/startエンドポイント実装（StartGameUseCaseを呼び出し）
  - [ ] Task 2.12.3: GET /api/scoresエンドポイント実装（ScoreRepositoryから過去10件取得）
  - [ ] Task 2.12.4: @CrossOrigin設定（フロントエンド接続許可）
  - [ ] Task 2.12.5: エラーハンドリング実装（500エラー時のレスポンス）
  - [ ] Task 2.12.6: 統合テスト作成（MockMvcでエンドポイント検証）
- **受け入れ基準**:
  - [ ] 実装完了
  - [ ] 単体テスト作成・通過
  - [ ] 要件充足: 5.4, 7.3, 7.4, 9.4
- **並列実行**: 可能

### Story 2.13: GameWebSocketController実装
- **担当**: @Developer
- **工数**: 1.5人日
- **説明**: GameWebSocketControllerクラス作成（@Controller）
- **タスク**:
  - [ ] Task 2.13.1: GameWebSocketControllerクラス作成（@Controller）
  - [ ] Task 2.13.2: /app/game/moveエンドポイント実装（@MessageMapping、MoveTetrominoUseCase呼び出し）
  - [ ] Task 2.13.3: /app/game/rotateエンドポイント実装（@MessageMapping、RotateTetrominoUseCase呼び出し）
  - [ ] Task 2.13.4: /topic/game/stateへのゲーム状態配信実装（@SendTo）
  - [ ] Task 2.13.5: /topic/game/overへのゲームオーバー通知実装
  - [ ] Task 2.13.6: 自動落下処理のスケジューラー実装（@Scheduled、レベルごとに間隔変更）
  - [ ] Task 2.13.7: セッションIDベースのゲーム状態管理実装（ConcurrentHashMap）
  - [ ] Task 2.13.8: 入力検証実装（Direction値チェック）
  - [ ] Task 2.13.9: 統合テスト作成（WebSocket接続、メッセージ送受信）
- **受け入れ基準**:
  - [ ] 実装完了
  - [ ] 単体テスト作成・通過
  - [ ] 要件充足: 1.1, 1.2, 1.3, 1.4, 1.5, 2.2, 2.3, 2.4, 3.1, 3.2, 3.7, 4.2, 4.3, 4.4, 10.1
- **並列実行**: 可能

### Story 2.14: GameCanvas描画ロジック実装
- **担当**: @Developer
- **工数**: 2人日
- **説明**: TypeScriptプロジェクトセットアップ（tsconfig.json、Gradleトランスパイル統合）
- **タスク**:
  - [ ] Task 2.14.1: TypeScriptプロジェクトセットアップ（tsconfig.json、Gradleトランスパイル統合）
  - [ ] Task 2.14.2: GameCanvasStateインターフェース定義（gameState, canvasContext, cellSize）
  - [ ] Task 2.14.3: GameCanvasクラス実装（HTMLCanvasElement受け取り）
  - [ ] Task 2.14.4: フィールド描画メソッド実装（drawField: 10×20グリッド、ブロック色分け）
  - [ ] Task 2.14.5: テトリミノ描画メソッド実装（drawTetromino: 現在のテトリミノ）
  - [ ] Task 2.14.6: ゴースト描画メソッド実装（半透明で落下予測位置表示）
  - [ ] Task 2.14.7: ネクストブロック描画メソッド実装（drawNextTetromino: プレビュー表示）
  - [ ] Task 2.14.8: レスポンシブデザイン実装（updateCellSize: ブラウザサイズ対応）
  - [ ] Task 2.14.9: requestAnimationFrameで60 FPS描画実装
  - [ ] Task 2.14.10: スコア、レベル、累計ライン数の画面表示
- **受け入れ基準**:
  - [ ] 実装完了
  - [ ] 単体テスト作成・通過
  - [ ] 要件充足: 6.1, 6.2, 6.3, 6.4, 6.5, 10.2
- **並列実行**: 可能

### Story 2.15: Canvas描画の基本テスト作成
- **担当**: @Developer
- **工数**: 0.5人日
- **説明**: GameCanvasのレンダリングテストセットアップ（Jest + Canvas Mock）
- **タスク**:
  - [ ] Task 2.15.1: GameCanvasのレンダリングテストセットアップ（Jest + Canvas Mock）
  - [ ] Task 2.15.2: フィールド描画の単体テスト（10×20グリッド検証）
  - [ ] Task 2.15.3: テトリミノ描画の単体テスト（色分け検証）
- **受け入れ基準**:
  - [ ] 実装完了
  - [ ] 単体テスト作成・通過
  - [ ] 要件充足: 6.1, 6.2
- **並列実行**: 可能

### Story 2.16: InputHandler入力処理実装
- **担当**: @Developer
- **工数**: 1人日
- **説明**: InputHandlerクラス実装（WebSocketClientを受け取り）
- **タスク**:
  - [ ] Task 2.16.1: InputHandlerクラス実装（WebSocketClientを受け取り）
  - [ ] Task 2.16.2: キーボードイベントリスナー実装（keydown）
  - [ ] Task 2.16.3: キーマッピング実装（ArrowLeft → LEFT, ArrowRight → RIGHT, ArrowDown → DOWN, ArrowUp → rotate, Space → hardDrop）
  - [ ] Task 2.16.4: WebSocketメッセージ送信実装（/app/game/move, /app/game/rotate）
  - [ ] Task 2.16.5: デバウンス処理実装（キー連打対策）
  - [ ] Task 2.16.6: 有効キーの検証実装
- **受け入れ基準**:
  - [ ] 実装完了
  - [ ] 単体テスト作成・通過
  - [ ] 要件充足: 1.1, 1.2, 1.3, 1.4, 1.5
- **並列実行**: 可能

### Story 2.17: WebSocketクライアント実装
- **担当**: @Developer
- **工数**: 1.5人日
- **説明**: WebSocketClientクラス実装（STOMP.js使用）
- **タスク**:
  - [ ] Task 2.17.1: WebSocketClientクラス実装（STOMP.js使用）
  - [ ] Task 2.17.2: /ws/game接続確立ロジック実装
  - [ ] Task 2.17.3: /topic/game/stateサブスクライブ実装（GameStateDTOを受信）
  - [ ] Task 2.17.4: /topic/game/overサブスクライブ実装（ゲームオーバー通知）
  - [ ] Task 2.17.5: 自動再接続メカニズム実装（最大3回リトライ）
  - [ ] Task 2.17.6: 接続切断時のエラーハンドリング
  - [ ] Task 2.17.7: GameCanvasへの描画トリガー実装（ゲーム状態受信時）
- **受け入れ基準**:
  - [ ] 実装完了
  - [ ] 単体テスト作成・通過
  - [ ] 要件充足: 10.1

## Phase 3: 追加QA（Additional QA）

**期間**: Day 16-18
**工数**: 2-4人日
**ステータス**: 未着手

### Story 3.1: エンドツーエンドテスト実装
- **担当**: @Developer
- **工数**: 1人日
- **説明**: ゲームプレイフロー全体のE2Eテスト作成（ゲーム開始→移動→回転→ライン消去→ゲームオーバー→スコア保存）
- **タスク**:
  - [ ] Task 3.1.1: ゲームプレイフロー全体のE2Eテスト作成（ゲーム開始→移動→回転→ライン消去→ゲームオーバー→スコア保存）
  - [ ] Task 3.1.2: 複数ユーザー同時プレイテスト作成（5セッション同時接続）
  - [ ] Task 3.1.3: ゲームオーバー後の再スタートフロー検証
  - [ ] Task 3.1.4: スコア履歴取得の検証
  - [ ] Task 3.1.5: UIインタラクションテスト（キーボード入力→画面更新）
- **受け入れ基準**:
  - [ ] 実装完了
  - [ ] 単体テスト作成・通過
  - [ ] 要件充足: 5.1, 5.2, 5.3, 5.4, 7.3, 7.4, 10.3

### Story 3.2: パフォーマンステストと最適化
- **担当**: @Developer
- **工数**: 1人日
- **説明**: WebSocketレイテンシテスト実装（操作→状態更新の時間測定、100ms以内目標）
- **タスク**:
  - [ ] Task 3.2.1: WebSocketレイテンシテスト実装（操作→状態更新の時間測定、100ms以内目標）
  - [ ] Task 3.2.2: Canvas描画パフォーマンステスト実装（60 FPS維持確認）
  - [ ] Task 3.2.3: 同時接続テスト実装（5ユーザー同時プレイ時のメモリ・CPU使用率）
  - [ ] Task 3.2.4: データベース負荷テスト実装（1000件スコア保存時のクエリパフォーマンス）
  - [ ] Task 3.2.5: ボトルネック特定と最適化実施
- **受け入れ基準**:
  - [ ] 実装完了
  - [ ] 単体テスト作成・通過
  - [ ] 要件充足: 10.1, 10.2, 10.3

### Story 3.3: コードカバレッジ検証とテスト追加
- **担当**: @Developer
- **工数**: 1人日
- **説明**: JaCoCoレポート生成設定（build.gradle）
- **タスク**:
  - [ ] Task 3.3.1: JaCoCoレポート生成設定（build.gradle）
  - [ ] Task 3.3.2: 全モジュールのコードカバレッジ測定
  - [ ] Task 3.3.3: カバレッジ80%未達箇所の特定
  - [ ] Task 3.3.4: 不足しているテストケースの追加（特にドメイン層とアプリケーション層）
  - [ ] Task 3.3.5: カバレッジ80%達成確認
- **受け入れ基準**:
  - [ ] 実装完了
  - [ ] 単体テスト作成・通過
  - [ ] 要件充足: 10.4

### Story 3.4: エラーハンドリングとロギング検証
- **担当**: @Developer
- **工数**: 1人日
- **説明**: すべてのエラーケースのテスト作成（不正入力、データベース接続失敗、WebSocket切断）
- **タスク**:
  - [ ] Task 3.4.1: すべてのエラーケースのテスト作成（不正入力、データベース接続失敗、WebSocket切断）
  - [ ] Task 3.4.2: ユーザーフレンドリーなエラーメッセージ検証
  - [ ] Task 3.4.3: SLF4J + Logbackでのエラーログ記録確認
  - [ ] Task 3.4.4: Spring Boot Actuatorのヘルスチェックエンドポイント検証
  - [ ] Task 3.4.5: エラー発生時のグレースフルデグラデーション確認（スコア保存失敗時もゲーム続行可能）
- **受け入れ基準**:
  - [ ] 実装完了
  - [ ] 単体テスト作成・通過
  - [ ] 要件充足: 10.5, 10.6

## Phase 4: リリース準備（Release Preparation）

**期間**: Day 19
**工数**: 0.5人日
**ステータス**: 未着手

### Story 4.1: リリースドキュメント作成
- **担当**: @PM
- **工数**: 0.5人日
- **説明**: リリースノートと手順書作成
- **受け入れ基準**:
  - [ ] リリースノート作成

## Phase 5: リリース（Release）

**期間**: Day 20
**工数**: 0.5人日
**ステータス**: 未着手

### Story 5.1: 本番リリース
- **担当**: @InfraEngineer
- **工数**: 0.5人日
- **説明**: 本番環境へのデプロイ
- **受け入れ基準**:
  - [ ] デプロイ成功
  - [ ] 動作確認完了

## 見積もりサマリー

| フェーズ | ストーリー数 | 工数（人日） |
|-------|-------------|---------------|
| Phase 0.1-0.2: 仕様化 | 2 | 1-1.5 |
| Phase 1: 環境構築 | 3 | 1.5-3 |
| Phase 2: TDD実装 | 17 | 8.5-17 |
| Phase 3: 追加QA | 4 | 2-4 |
| **合計** | **26** | **13-26** |

---

*AI-DLC形式から自動変換*
*元のカテゴリ数: 7, 元のタスク数: 24*