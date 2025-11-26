# Unit Test Specification: tetris-web-app unitテスト

**Author**: Auto-generated
**Date**: 2025-11-26
**Version**: 1.0

## 1. Overview

### 1.1 Purpose
tetris-web-appの各コンポーネントが独立して正常に動作することを確認する

Example: To verify that individual functions and classes in the  module work correctly in isolation.

### 1.2 Scope
tetris-web-appの全コンポーネント（Tetromino, GameField, GameState, ScoreCalculator, LevelManager, StartGameUseCase, MoveTetrominoUseCase, RotateTetrominoUseCase, ProcessAutoDropUseCase, ClearLinesUseCase, SaveScoreUseCase, GameRestController, GameWebSocketController, ScoreRepositoryAdapter, GameCanvas, InputHandler）

Example: This test specification covers all public methods of the  class and their edge cases.

### 1.3 Testing Tool
- **Tool**: Vitest
- **Version**: 1.0.0

Example:
- **Tool**: Vitest (Node.js) / JUnit 5 (Java) / PHPUnit (PHP)
- **Version**: 1.0.0 / 5.10.0 / 10.5.0

## 2. Test Environment

### 2.1 Software Requirements
- Programming Language: TypeScript 5.x
- Testing Framework: Vitest 1.0.0
- Mocking Library: Vitest (built-in) (if applicable)
- Dependencies: Node.js 20+

### 2.2 Hardware Requirements
- Not applicable for unit tests (runs on developer's machine or CI/CD)

### 2.3 Test Data
- Mock data location: `tests/__mocks__/tetris-web-app`
- Test fixtures: `tests/__fixtures__/tetris-web-app`
- Data setup: モックデータはJSONファイルで管理

## 3. Functions/Classes to Test

### 3.1 Target Components
List all functions/classes to be tested:

| Component | Type | Description | Priority |
|-----------|------|-------------|----------|
| Tetromino | Class | テトリミノの状態と操作ロジック | Medium |
| GameField | Class | フィールド状態、衝突判定、ライン消去 | Medium |
| GameState | Class | ゲーム状態の集約ルート | Medium |
| ScoreCalculator | Class | スコア計算ロジック | Medium |
| LevelManager | Class | レベルと難易度管理 | Medium |
| StartGameUseCase | Class | ゲーム開始ユースケース | Medium |
| MoveTetrominoUseCase | Class | テトリミノ移動ユースケース | Medium |
| RotateTetrominoUseCase | Class | テトリミノ回転ユースケース | Medium |
| ProcessAutoDropUseCase | Class | 自動落下処理ユースケース | Medium |
| ClearLinesUseCase | Class | ライン消去ユースケース | Medium |
| SaveScoreUseCase | Class | スコア保存ユースケース | Medium |
| GameRestController | Class | REST APIエンドポイント | Medium |
| GameWebSocketController | Class | WebSocketエンドポイント | Medium |
| ScoreRepositoryAdapter | Class | データベースアクセス | Medium |
| GameCanvas | Class | ゲーム画面描画 | Medium |
| InputHandler | Class | キーボード入力処理 | Medium |
|  | Function/Class |  | High/Medium/Low |

### 3.2 Mocking Strategy
Describe what external dependencies will be mocked:

| Dependency | Mock Type | Reason |
|------------|-----------|--------    |

Example:
| Dependency | Mock Type | Reason |
|------------|-----------|--------|
| Database | Mock object | Avoid external I/O, ensure test isolation |
| HTTP API | Stub | Control response for predictable testing |

## 4. Test Cases

### Test Case UT-001: StartGameUseCase.render - 正常系

**Description**: StartGameUseCaseのrenderメソッドが正常に動作することを確認

**Preconditions**:
- StartGameUseCaseが初期化されている
- 有効な入力パラメータが準備されている

**Test Steps**:
1. 有効なパラメータでrender()を呼び出す
2. 戻り値を確認する
3. エラーが発生しないことを確認する

**Expected Results**:
- 期待されるvoid;  // 60 FPS描画型の値が返される
- 例外が発生しない

**Actual Results**:
[To be filled during test execution]

**Status**: [ ] Pass / [ ] Fail / [ ] Blocked

**Notes**:

---

### Test Case UT-002: StartGameUseCase.render - 異常系（null入力）

**Description**: null入力時に適切なエラーハンドリングが行われることを確認

**Preconditions**:
- StartGameUseCaseが初期化されている

**Test Steps**:
1. gameStateにnullを渡してrender()を呼び出す
2. エラーが発生することを確認する

**Expected Results**:
- 適切なエラーメッセージが表示される
- システムがクラッシュしない

**Actual Results**:
[To be filled during test execution]

**Status**: [ ] Pass / [ ] Fail / [ ] Blocked

**Notes**:

---

### Test Case UT-003: StartGameUseCase.drawField - 正常系

**Description**: StartGameUseCaseのdrawFieldメソッドが正常に動作することを確認

**Preconditions**:
- StartGameUseCaseが初期化されている
- 有効な入力パラメータが準備されている

**Test Steps**:
1. 有効なパラメータでdrawField()を呼び出す
2. 戻り値を確認する
3. エラーが発生しないことを確認する

**Expected Results**:
- 期待されるvoid型の値が返される
- 例外が発生しない

**Actual Results**:
[To be filled during test execution]

**Status**: [ ] Pass / [ ] Fail / [ ] Blocked

**Notes**:

---

### Test Case UT-004: StartGameUseCase.drawField - 異常系（null入力）

**Description**: null入力時に適切なエラーハンドリングが行われることを確認

**Preconditions**:
- StartGameUseCaseが初期化されている

**Test Steps**:
1. fieldにnullを渡してdrawField()を呼び出す
2. エラーが発生することを確認する

**Expected Results**:
- 適切なエラーメッセージが表示される
- システムがクラッシュしない

**Actual Results**:
[To be filled during test execution]

**Status**: [ ] Pass / [ ] Fail / [ ] Blocked

**Notes**:

---

### Test Case UT-005: StartGameUseCase.drawTetromino - 正常系

**Description**: StartGameUseCaseのdrawTetrominoメソッドが正常に動作することを確認

**Preconditions**:
- StartGameUseCaseが初期化されている
- 有効な入力パラメータが準備されている

**Test Steps**:
1. 有効なパラメータでdrawTetromino()を呼び出す
2. 戻り値を確認する
3. エラーが発生しないことを確認する

**Expected Results**:
- 期待されるvoid型の値が返される
- 例外が発生しない

**Actual Results**:
[To be filled during test execution]

**Status**: [ ] Pass / [ ] Fail / [ ] Blocked

**Notes**:

---

### Test Case UT-006: StartGameUseCase.drawTetromino - 異常系（null入力）

**Description**: null入力時に適切なエラーハンドリングが行われることを確認

**Preconditions**:
- StartGameUseCaseが初期化されている

**Test Steps**:
1. tetrominoにnullを渡してdrawTetromino()を呼び出す
2. エラーが発生することを確認する

**Expected Results**:
- 適切なエラーメッセージが表示される
- システムがクラッシュしない

**Actual Results**:
[To be filled during test execution]

**Status**: [ ] Pass / [ ] Fail / [ ] Blocked

**Notes**:

---

### Test Case UT-007: StartGameUseCase.drawNextTetromino - 正常系

**Description**: StartGameUseCaseのdrawNextTetrominoメソッドが正常に動作することを確認

**Preconditions**:
- StartGameUseCaseが初期化されている
- 有効な入力パラメータが準備されている

**Test Steps**:
1. 有効なパラメータでdrawNextTetromino()を呼び出す
2. 戻り値を確認する
3. エラーが発生しないことを確認する

**Expected Results**:
- 期待されるvoid型の値が返される
- 例外が発生しない

**Actual Results**:
[To be filled during test execution]

**Status**: [ ] Pass / [ ] Fail / [ ] Blocked

**Notes**:

---

### Test Case UT-008: StartGameUseCase.drawNextTetromino - 異常系（null入力）

**Description**: null入力時に適切なエラーハンドリングが行われることを確認

**Preconditions**:
- StartGameUseCaseが初期化されている

**Test Steps**:
1. tetrominoにnullを渡してdrawNextTetromino()を呼び出す
2. エラーが発生することを確認する

**Expected Results**:
- 適切なエラーメッセージが表示される
- システムがクラッシュしない

**Actual Results**:
[To be filled during test execution]

**Status**: [ ] Pass / [ ] Fail / [ ] Blocked

**Notes**:

---

### Test Case UT-009: StartGameUseCase.updateCellSize - 正常系

**Description**: StartGameUseCaseのupdateCellSizeメソッドが正常に動作することを確認

**Preconditions**:
- StartGameUseCaseが初期化されている
- 有効な入力パラメータが準備されている

**Test Steps**:
1. 有効なパラメータでupdateCellSize()を呼び出す
2. 戻り値を確認する
3. エラーが発生しないことを確認する

**Expected Results**:
- 期待されるvoid;  // レスポンシブ対応型の値が返される
- 例外が発生しない

**Actual Results**:
[To be filled during test execution]

**Status**: [ ] Pass / [ ] Fail / [ ] Blocked

**Notes**:

---

### Test Case UT-010: MoveTetrominoUseCase.render - 正常系

**Description**: MoveTetrominoUseCaseのrenderメソッドが正常に動作することを確認

**Preconditions**:
- MoveTetrominoUseCaseが初期化されている
- 有効な入力パラメータが準備されている

**Test Steps**:
1. 有効なパラメータでrender()を呼び出す
2. 戻り値を確認する
3. エラーが発生しないことを確認する

**Expected Results**:
- 期待されるvoid;  // 60 FPS描画型の値が返される
- 例外が発生しない

**Actual Results**:
[To be filled during test execution]

**Status**: [ ] Pass / [ ] Fail / [ ] Blocked

**Notes**:

---

### Test Case UT-011: MoveTetrominoUseCase.render - 異常系（null入力）

**Description**: null入力時に適切なエラーハンドリングが行われることを確認

**Preconditions**:
- MoveTetrominoUseCaseが初期化されている

**Test Steps**:
1. gameStateにnullを渡してrender()を呼び出す
2. エラーが発生することを確認する

**Expected Results**:
- 適切なエラーメッセージが表示される
- システムがクラッシュしない

**Actual Results**:
[To be filled during test execution]

**Status**: [ ] Pass / [ ] Fail / [ ] Blocked

**Notes**:

---

### Test Case UT-012: MoveTetrominoUseCase.drawField - 正常系

**Description**: MoveTetrominoUseCaseのdrawFieldメソッドが正常に動作することを確認

**Preconditions**:
- MoveTetrominoUseCaseが初期化されている
- 有効な入力パラメータが準備されている

**Test Steps**:
1. 有効なパラメータでdrawField()を呼び出す
2. 戻り値を確認する
3. エラーが発生しないことを確認する

**Expected Results**:
- 期待されるvoid型の値が返される
- 例外が発生しない

**Actual Results**:
[To be filled during test execution]

**Status**: [ ] Pass / [ ] Fail / [ ] Blocked

**Notes**:

---

### Test Case UT-013: MoveTetrominoUseCase.drawField - 異常系（null入力）

**Description**: null入力時に適切なエラーハンドリングが行われることを確認

**Preconditions**:
- MoveTetrominoUseCaseが初期化されている

**Test Steps**:
1. fieldにnullを渡してdrawField()を呼び出す
2. エラーが発生することを確認する

**Expected Results**:
- 適切なエラーメッセージが表示される
- システムがクラッシュしない

**Actual Results**:
[To be filled during test execution]

**Status**: [ ] Pass / [ ] Fail / [ ] Blocked

**Notes**:

---

### Test Case UT-014: MoveTetrominoUseCase.drawTetromino - 正常系

**Description**: MoveTetrominoUseCaseのdrawTetrominoメソッドが正常に動作することを確認

**Preconditions**:
- MoveTetrominoUseCaseが初期化されている
- 有効な入力パラメータが準備されている

**Test Steps**:
1. 有効なパラメータでdrawTetromino()を呼び出す
2. 戻り値を確認する
3. エラーが発生しないことを確認する

**Expected Results**:
- 期待されるvoid型の値が返される
- 例外が発生しない

**Actual Results**:
[To be filled during test execution]

**Status**: [ ] Pass / [ ] Fail / [ ] Blocked

**Notes**:

---

### Test Case UT-015: MoveTetrominoUseCase.drawTetromino - 異常系（null入力）

**Description**: null入力時に適切なエラーハンドリングが行われることを確認

**Preconditions**:
- MoveTetrominoUseCaseが初期化されている

**Test Steps**:
1. tetrominoにnullを渡してdrawTetromino()を呼び出す
2. エラーが発生することを確認する

**Expected Results**:
- 適切なエラーメッセージが表示される
- システムがクラッシュしない

**Actual Results**:
[To be filled during test execution]

**Status**: [ ] Pass / [ ] Fail / [ ] Blocked

**Notes**:

---

### Test Case UT-016: MoveTetrominoUseCase.drawNextTetromino - 正常系

**Description**: MoveTetrominoUseCaseのdrawNextTetrominoメソッドが正常に動作することを確認

**Preconditions**:
- MoveTetrominoUseCaseが初期化されている
- 有効な入力パラメータが準備されている

**Test Steps**:
1. 有効なパラメータでdrawNextTetromino()を呼び出す
2. 戻り値を確認する
3. エラーが発生しないことを確認する

**Expected Results**:
- 期待されるvoid型の値が返される
- 例外が発生しない

**Actual Results**:
[To be filled during test execution]

**Status**: [ ] Pass / [ ] Fail / [ ] Blocked

**Notes**:

---

### Test Case UT-017: MoveTetrominoUseCase.drawNextTetromino - 異常系（null入力）

**Description**: null入力時に適切なエラーハンドリングが行われることを確認

**Preconditions**:
- MoveTetrominoUseCaseが初期化されている

**Test Steps**:
1. tetrominoにnullを渡してdrawNextTetromino()を呼び出す
2. エラーが発生することを確認する

**Expected Results**:
- 適切なエラーメッセージが表示される
- システムがクラッシュしない

**Actual Results**:
[To be filled during test execution]

**Status**: [ ] Pass / [ ] Fail / [ ] Blocked

**Notes**:

---

### Test Case UT-018: MoveTetrominoUseCase.updateCellSize - 正常系

**Description**: MoveTetrominoUseCaseのupdateCellSizeメソッドが正常に動作することを確認

**Preconditions**:
- MoveTetrominoUseCaseが初期化されている
- 有効な入力パラメータが準備されている

**Test Steps**:
1. 有効なパラメータでupdateCellSize()を呼び出す
2. 戻り値を確認する
3. エラーが発生しないことを確認する

**Expected Results**:
- 期待されるvoid;  // レスポンシブ対応型の値が返される
- 例外が発生しない

**Actual Results**:
[To be filled during test execution]

**Status**: [ ] Pass / [ ] Fail / [ ] Blocked

**Notes**:

---

### Test Case UT-019: RotateTetrominoUseCase.render - 正常系

**Description**: RotateTetrominoUseCaseのrenderメソッドが正常に動作することを確認

**Preconditions**:
- RotateTetrominoUseCaseが初期化されている
- 有効な入力パラメータが準備されている

**Test Steps**:
1. 有効なパラメータでrender()を呼び出す
2. 戻り値を確認する
3. エラーが発生しないことを確認する

**Expected Results**:
- 期待されるvoid;  // 60 FPS描画型の値が返される
- 例外が発生しない

**Actual Results**:
[To be filled during test execution]

**Status**: [ ] Pass / [ ] Fail / [ ] Blocked

**Notes**:

---

### Test Case UT-020: RotateTetrominoUseCase.render - 異常系（null入力）

**Description**: null入力時に適切なエラーハンドリングが行われることを確認

**Preconditions**:
- RotateTetrominoUseCaseが初期化されている

**Test Steps**:
1. gameStateにnullを渡してrender()を呼び出す
2. エラーが発生することを確認する

**Expected Results**:
- 適切なエラーメッセージが表示される
- システムがクラッシュしない

**Actual Results**:
[To be filled during test execution]

**Status**: [ ] Pass / [ ] Fail / [ ] Blocked

**Notes**:

---

### Test Case UT-021: RotateTetrominoUseCase.drawField - 正常系

**Description**: RotateTetrominoUseCaseのdrawFieldメソッドが正常に動作することを確認

**Preconditions**:
- RotateTetrominoUseCaseが初期化されている
- 有効な入力パラメータが準備されている

**Test Steps**:
1. 有効なパラメータでdrawField()を呼び出す
2. 戻り値を確認する
3. エラーが発生しないことを確認する

**Expected Results**:
- 期待されるvoid型の値が返される
- 例外が発生しない

**Actual Results**:
[To be filled during test execution]

**Status**: [ ] Pass / [ ] Fail / [ ] Blocked

**Notes**:

---

### Test Case UT-022: RotateTetrominoUseCase.drawField - 異常系（null入力）

**Description**: null入力時に適切なエラーハンドリングが行われることを確認

**Preconditions**:
- RotateTetrominoUseCaseが初期化されている

**Test Steps**:
1. fieldにnullを渡してdrawField()を呼び出す
2. エラーが発生することを確認する

**Expected Results**:
- 適切なエラーメッセージが表示される
- システムがクラッシュしない

**Actual Results**:
[To be filled during test execution]

**Status**: [ ] Pass / [ ] Fail / [ ] Blocked

**Notes**:

---

### Test Case UT-023: RotateTetrominoUseCase.drawTetromino - 正常系

**Description**: RotateTetrominoUseCaseのdrawTetrominoメソッドが正常に動作することを確認

**Preconditions**:
- RotateTetrominoUseCaseが初期化されている
- 有効な入力パラメータが準備されている

**Test Steps**:
1. 有効なパラメータでdrawTetromino()を呼び出す
2. 戻り値を確認する
3. エラーが発生しないことを確認する

**Expected Results**:
- 期待されるvoid型の値が返される
- 例外が発生しない

**Actual Results**:
[To be filled during test execution]

**Status**: [ ] Pass / [ ] Fail / [ ] Blocked

**Notes**:

---

### Test Case UT-024: RotateTetrominoUseCase.drawTetromino - 異常系（null入力）

**Description**: null入力時に適切なエラーハンドリングが行われることを確認

**Preconditions**:
- RotateTetrominoUseCaseが初期化されている

**Test Steps**:
1. tetrominoにnullを渡してdrawTetromino()を呼び出す
2. エラーが発生することを確認する

**Expected Results**:
- 適切なエラーメッセージが表示される
- システムがクラッシュしない

**Actual Results**:
[To be filled during test execution]

**Status**: [ ] Pass / [ ] Fail / [ ] Blocked

**Notes**:

---

### Test Case UT-025: RotateTetrominoUseCase.drawNextTetromino - 正常系

**Description**: RotateTetrominoUseCaseのdrawNextTetrominoメソッドが正常に動作することを確認

**Preconditions**:
- RotateTetrominoUseCaseが初期化されている
- 有効な入力パラメータが準備されている

**Test Steps**:
1. 有効なパラメータでdrawNextTetromino()を呼び出す
2. 戻り値を確認する
3. エラーが発生しないことを確認する

**Expected Results**:
- 期待されるvoid型の値が返される
- 例外が発生しない

**Actual Results**:
[To be filled during test execution]

**Status**: [ ] Pass / [ ] Fail / [ ] Blocked

**Notes**:

---

### Test Case UT-026: RotateTetrominoUseCase.drawNextTetromino - 異常系（null入力）

**Description**: null入力時に適切なエラーハンドリングが行われることを確認

**Preconditions**:
- RotateTetrominoUseCaseが初期化されている

**Test Steps**:
1. tetrominoにnullを渡してdrawNextTetromino()を呼び出す
2. エラーが発生することを確認する

**Expected Results**:
- 適切なエラーメッセージが表示される
- システムがクラッシュしない

**Actual Results**:
[To be filled during test execution]

**Status**: [ ] Pass / [ ] Fail / [ ] Blocked

**Notes**:

---

### Test Case UT-027: RotateTetrominoUseCase.updateCellSize - 正常系

**Description**: RotateTetrominoUseCaseのupdateCellSizeメソッドが正常に動作することを確認

**Preconditions**:
- RotateTetrominoUseCaseが初期化されている
- 有効な入力パラメータが準備されている

**Test Steps**:
1. 有効なパラメータでupdateCellSize()を呼び出す
2. 戻り値を確認する
3. エラーが発生しないことを確認する

**Expected Results**:
- 期待されるvoid;  // レスポンシブ対応型の値が返される
- 例外が発生しない

**Actual Results**:
[To be filled during test execution]

**Status**: [ ] Pass / [ ] Fail / [ ] Blocked

**Notes**:

---

### Test Case UT-028: ProcessAutoDropUseCase.render - 正常系

**Description**: ProcessAutoDropUseCaseのrenderメソッドが正常に動作することを確認

**Preconditions**:
- ProcessAutoDropUseCaseが初期化されている
- 有効な入力パラメータが準備されている

**Test Steps**:
1. 有効なパラメータでrender()を呼び出す
2. 戻り値を確認する
3. エラーが発生しないことを確認する

**Expected Results**:
- 期待されるvoid;  // 60 FPS描画型の値が返される
- 例外が発生しない

**Actual Results**:
[To be filled during test execution]

**Status**: [ ] Pass / [ ] Fail / [ ] Blocked

**Notes**:

---

### Test Case UT-029: ProcessAutoDropUseCase.render - 異常系（null入力）

**Description**: null入力時に適切なエラーハンドリングが行われることを確認

**Preconditions**:
- ProcessAutoDropUseCaseが初期化されている

**Test Steps**:
1. gameStateにnullを渡してrender()を呼び出す
2. エラーが発生することを確認する

**Expected Results**:
- 適切なエラーメッセージが表示される
- システムがクラッシュしない

**Actual Results**:
[To be filled during test execution]

**Status**: [ ] Pass / [ ] Fail / [ ] Blocked

**Notes**:

---

### Test Case UT-030: ProcessAutoDropUseCase.drawField - 正常系

**Description**: ProcessAutoDropUseCaseのdrawFieldメソッドが正常に動作することを確認

**Preconditions**:
- ProcessAutoDropUseCaseが初期化されている
- 有効な入力パラメータが準備されている

**Test Steps**:
1. 有効なパラメータでdrawField()を呼び出す
2. 戻り値を確認する
3. エラーが発生しないことを確認する

**Expected Results**:
- 期待されるvoid型の値が返される
- 例外が発生しない

**Actual Results**:
[To be filled during test execution]

**Status**: [ ] Pass / [ ] Fail / [ ] Blocked

**Notes**:

---

### Test Case UT-031: ProcessAutoDropUseCase.drawField - 異常系（null入力）

**Description**: null入力時に適切なエラーハンドリングが行われることを確認

**Preconditions**:
- ProcessAutoDropUseCaseが初期化されている

**Test Steps**:
1. fieldにnullを渡してdrawField()を呼び出す
2. エラーが発生することを確認する

**Expected Results**:
- 適切なエラーメッセージが表示される
- システムがクラッシュしない

**Actual Results**:
[To be filled during test execution]

**Status**: [ ] Pass / [ ] Fail / [ ] Blocked

**Notes**:

---

### Test Case UT-032: ProcessAutoDropUseCase.drawTetromino - 正常系

**Description**: ProcessAutoDropUseCaseのdrawTetrominoメソッドが正常に動作することを確認

**Preconditions**:
- ProcessAutoDropUseCaseが初期化されている
- 有効な入力パラメータが準備されている

**Test Steps**:
1. 有効なパラメータでdrawTetromino()を呼び出す
2. 戻り値を確認する
3. エラーが発生しないことを確認する

**Expected Results**:
- 期待されるvoid型の値が返される
- 例外が発生しない

**Actual Results**:
[To be filled during test execution]

**Status**: [ ] Pass / [ ] Fail / [ ] Blocked

**Notes**:

---

### Test Case UT-033: ProcessAutoDropUseCase.drawTetromino - 異常系（null入力）

**Description**: null入力時に適切なエラーハンドリングが行われることを確認

**Preconditions**:
- ProcessAutoDropUseCaseが初期化されている

**Test Steps**:
1. tetrominoにnullを渡してdrawTetromino()を呼び出す
2. エラーが発生することを確認する

**Expected Results**:
- 適切なエラーメッセージが表示される
- システムがクラッシュしない

**Actual Results**:
[To be filled during test execution]

**Status**: [ ] Pass / [ ] Fail / [ ] Blocked

**Notes**:

---

### Test Case UT-034: ProcessAutoDropUseCase.drawNextTetromino - 正常系

**Description**: ProcessAutoDropUseCaseのdrawNextTetrominoメソッドが正常に動作することを確認

**Preconditions**:
- ProcessAutoDropUseCaseが初期化されている
- 有効な入力パラメータが準備されている

**Test Steps**:
1. 有効なパラメータでdrawNextTetromino()を呼び出す
2. 戻り値を確認する
3. エラーが発生しないことを確認する

**Expected Results**:
- 期待されるvoid型の値が返される
- 例外が発生しない

**Actual Results**:
[To be filled during test execution]

**Status**: [ ] Pass / [ ] Fail / [ ] Blocked

**Notes**:

---

### Test Case UT-035: ProcessAutoDropUseCase.drawNextTetromino - 異常系（null入力）

**Description**: null入力時に適切なエラーハンドリングが行われることを確認

**Preconditions**:
- ProcessAutoDropUseCaseが初期化されている

**Test Steps**:
1. tetrominoにnullを渡してdrawNextTetromino()を呼び出す
2. エラーが発生することを確認する

**Expected Results**:
- 適切なエラーメッセージが表示される
- システムがクラッシュしない

**Actual Results**:
[To be filled during test execution]

**Status**: [ ] Pass / [ ] Fail / [ ] Blocked

**Notes**:

---

### Test Case UT-036: ProcessAutoDropUseCase.updateCellSize - 正常系

**Description**: ProcessAutoDropUseCaseのupdateCellSizeメソッドが正常に動作することを確認

**Preconditions**:
- ProcessAutoDropUseCaseが初期化されている
- 有効な入力パラメータが準備されている

**Test Steps**:
1. 有効なパラメータでupdateCellSize()を呼び出す
2. 戻り値を確認する
3. エラーが発生しないことを確認する

**Expected Results**:
- 期待されるvoid;  // レスポンシブ対応型の値が返される
- 例外が発生しない

**Actual Results**:
[To be filled during test execution]

**Status**: [ ] Pass / [ ] Fail / [ ] Blocked

**Notes**:

---

### Test Case UT-037: SaveScoreUseCase.render - 正常系

**Description**: SaveScoreUseCaseのrenderメソッドが正常に動作することを確認

**Preconditions**:
- SaveScoreUseCaseが初期化されている
- 有効な入力パラメータが準備されている

**Test Steps**:
1. 有効なパラメータでrender()を呼び出す
2. 戻り値を確認する
3. エラーが発生しないことを確認する

**Expected Results**:
- 期待されるvoid;  // 60 FPS描画型の値が返される
- 例外が発生しない

**Actual Results**:
[To be filled during test execution]

**Status**: [ ] Pass / [ ] Fail / [ ] Blocked

**Notes**:

---

### Test Case UT-038: SaveScoreUseCase.render - 異常系（null入力）

**Description**: null入力時に適切なエラーハンドリングが行われることを確認

**Preconditions**:
- SaveScoreUseCaseが初期化されている

**Test Steps**:
1. gameStateにnullを渡してrender()を呼び出す
2. エラーが発生することを確認する

**Expected Results**:
- 適切なエラーメッセージが表示される
- システムがクラッシュしない

**Actual Results**:
[To be filled during test execution]

**Status**: [ ] Pass / [ ] Fail / [ ] Blocked

**Notes**:

---

### Test Case UT-039: SaveScoreUseCase.drawField - 正常系

**Description**: SaveScoreUseCaseのdrawFieldメソッドが正常に動作することを確認

**Preconditions**:
- SaveScoreUseCaseが初期化されている
- 有効な入力パラメータが準備されている

**Test Steps**:
1. 有効なパラメータでdrawField()を呼び出す
2. 戻り値を確認する
3. エラーが発生しないことを確認する

**Expected Results**:
- 期待されるvoid型の値が返される
- 例外が発生しない

**Actual Results**:
[To be filled during test execution]

**Status**: [ ] Pass / [ ] Fail / [ ] Blocked

**Notes**:

---

### Test Case UT-040: SaveScoreUseCase.drawField - 異常系（null入力）

**Description**: null入力時に適切なエラーハンドリングが行われることを確認

**Preconditions**:
- SaveScoreUseCaseが初期化されている

**Test Steps**:
1. fieldにnullを渡してdrawField()を呼び出す
2. エラーが発生することを確認する

**Expected Results**:
- 適切なエラーメッセージが表示される
- システムがクラッシュしない

**Actual Results**:
[To be filled during test execution]

**Status**: [ ] Pass / [ ] Fail / [ ] Blocked

**Notes**:

---

### Test Case UT-041: SaveScoreUseCase.drawTetromino - 正常系

**Description**: SaveScoreUseCaseのdrawTetrominoメソッドが正常に動作することを確認

**Preconditions**:
- SaveScoreUseCaseが初期化されている
- 有効な入力パラメータが準備されている

**Test Steps**:
1. 有効なパラメータでdrawTetromino()を呼び出す
2. 戻り値を確認する
3. エラーが発生しないことを確認する

**Expected Results**:
- 期待されるvoid型の値が返される
- 例外が発生しない

**Actual Results**:
[To be filled during test execution]

**Status**: [ ] Pass / [ ] Fail / [ ] Blocked

**Notes**:

---

### Test Case UT-042: SaveScoreUseCase.drawTetromino - 異常系（null入力）

**Description**: null入力時に適切なエラーハンドリングが行われることを確認

**Preconditions**:
- SaveScoreUseCaseが初期化されている

**Test Steps**:
1. tetrominoにnullを渡してdrawTetromino()を呼び出す
2. エラーが発生することを確認する

**Expected Results**:
- 適切なエラーメッセージが表示される
- システムがクラッシュしない

**Actual Results**:
[To be filled during test execution]

**Status**: [ ] Pass / [ ] Fail / [ ] Blocked

**Notes**:

---

### Test Case UT-043: SaveScoreUseCase.drawNextTetromino - 正常系

**Description**: SaveScoreUseCaseのdrawNextTetrominoメソッドが正常に動作することを確認

**Preconditions**:
- SaveScoreUseCaseが初期化されている
- 有効な入力パラメータが準備されている

**Test Steps**:
1. 有効なパラメータでdrawNextTetromino()を呼び出す
2. 戻り値を確認する
3. エラーが発生しないことを確認する

**Expected Results**:
- 期待されるvoid型の値が返される
- 例外が発生しない

**Actual Results**:
[To be filled during test execution]

**Status**: [ ] Pass / [ ] Fail / [ ] Blocked

**Notes**:

---

### Test Case UT-044: SaveScoreUseCase.drawNextTetromino - 異常系（null入力）

**Description**: null入力時に適切なエラーハンドリングが行われることを確認

**Preconditions**:
- SaveScoreUseCaseが初期化されている

**Test Steps**:
1. tetrominoにnullを渡してdrawNextTetromino()を呼び出す
2. エラーが発生することを確認する

**Expected Results**:
- 適切なエラーメッセージが表示される
- システムがクラッシュしない

**Actual Results**:
[To be filled during test execution]

**Status**: [ ] Pass / [ ] Fail / [ ] Blocked

**Notes**:

---

### Test Case UT-045: SaveScoreUseCase.updateCellSize - 正常系

**Description**: SaveScoreUseCaseのupdateCellSizeメソッドが正常に動作することを確認

**Preconditions**:
- SaveScoreUseCaseが初期化されている
- 有効な入力パラメータが準備されている

**Test Steps**:
1. 有効なパラメータでupdateCellSize()を呼び出す
2. 戻り値を確認する
3. エラーが発生しないことを確認する

**Expected Results**:
- 期待されるvoid;  // レスポンシブ対応型の値が返される
- 例外が発生しない

**Actual Results**:
[To be filled during test execution]

**Status**: [ ] Pass / [ ] Fail / [ ] Blocked

**Notes**:

---

### Test Case UT-046: ScoreRepositoryAdapter.render - 正常系

**Description**: ScoreRepositoryAdapterのrenderメソッドが正常に動作することを確認

**Preconditions**:
- ScoreRepositoryAdapterが初期化されている
- 有効な入力パラメータが準備されている

**Test Steps**:
1. 有効なパラメータでrender()を呼び出す
2. 戻り値を確認する
3. エラーが発生しないことを確認する

**Expected Results**:
- 期待されるvoid;  // 60 FPS描画型の値が返される
- 例外が発生しない

**Actual Results**:
[To be filled during test execution]

**Status**: [ ] Pass / [ ] Fail / [ ] Blocked

**Notes**:

---

### Test Case UT-047: ScoreRepositoryAdapter.render - 異常系（null入力）

**Description**: null入力時に適切なエラーハンドリングが行われることを確認

**Preconditions**:
- ScoreRepositoryAdapterが初期化されている

**Test Steps**:
1. gameStateにnullを渡してrender()を呼び出す
2. エラーが発生することを確認する

**Expected Results**:
- 適切なエラーメッセージが表示される
- システムがクラッシュしない

**Actual Results**:
[To be filled during test execution]

**Status**: [ ] Pass / [ ] Fail / [ ] Blocked

**Notes**:

---

### Test Case UT-048: ScoreRepositoryAdapter.drawField - 正常系

**Description**: ScoreRepositoryAdapterのdrawFieldメソッドが正常に動作することを確認

**Preconditions**:
- ScoreRepositoryAdapterが初期化されている
- 有効な入力パラメータが準備されている

**Test Steps**:
1. 有効なパラメータでdrawField()を呼び出す
2. 戻り値を確認する
3. エラーが発生しないことを確認する

**Expected Results**:
- 期待されるvoid型の値が返される
- 例外が発生しない

**Actual Results**:
[To be filled during test execution]

**Status**: [ ] Pass / [ ] Fail / [ ] Blocked

**Notes**:

---

### Test Case UT-049: ScoreRepositoryAdapter.drawField - 異常系（null入力）

**Description**: null入力時に適切なエラーハンドリングが行われることを確認

**Preconditions**:
- ScoreRepositoryAdapterが初期化されている

**Test Steps**:
1. fieldにnullを渡してdrawField()を呼び出す
2. エラーが発生することを確認する

**Expected Results**:
- 適切なエラーメッセージが表示される
- システムがクラッシュしない

**Actual Results**:
[To be filled during test execution]

**Status**: [ ] Pass / [ ] Fail / [ ] Blocked

**Notes**:

---

### Test Case UT-050: ScoreRepositoryAdapter.drawTetromino - 正常系

**Description**: ScoreRepositoryAdapterのdrawTetrominoメソッドが正常に動作することを確認

**Preconditions**:
- ScoreRepositoryAdapterが初期化されている
- 有効な入力パラメータが準備されている

**Test Steps**:
1. 有効なパラメータでdrawTetromino()を呼び出す
2. 戻り値を確認する
3. エラーが発生しないことを確認する

**Expected Results**:
- 期待されるvoid型の値が返される
- 例外が発生しない

**Actual Results**:
[To be filled during test execution]

**Status**: [ ] Pass / [ ] Fail / [ ] Blocked

**Notes**:

---

### Test Case UT-051: ScoreRepositoryAdapter.drawTetromino - 異常系（null入力）

**Description**: null入力時に適切なエラーハンドリングが行われることを確認

**Preconditions**:
- ScoreRepositoryAdapterが初期化されている

**Test Steps**:
1. tetrominoにnullを渡してdrawTetromino()を呼び出す
2. エラーが発生することを確認する

**Expected Results**:
- 適切なエラーメッセージが表示される
- システムがクラッシュしない

**Actual Results**:
[To be filled during test execution]

**Status**: [ ] Pass / [ ] Fail / [ ] Blocked

**Notes**:

---

### Test Case UT-052: ScoreRepositoryAdapter.drawNextTetromino - 正常系

**Description**: ScoreRepositoryAdapterのdrawNextTetrominoメソッドが正常に動作することを確認

**Preconditions**:
- ScoreRepositoryAdapterが初期化されている
- 有効な入力パラメータが準備されている

**Test Steps**:
1. 有効なパラメータでdrawNextTetromino()を呼び出す
2. 戻り値を確認する
3. エラーが発生しないことを確認する

**Expected Results**:
- 期待されるvoid型の値が返される
- 例外が発生しない

**Actual Results**:
[To be filled during test execution]

**Status**: [ ] Pass / [ ] Fail / [ ] Blocked

**Notes**:

---

### Test Case UT-053: ScoreRepositoryAdapter.drawNextTetromino - 異常系（null入力）

**Description**: null入力時に適切なエラーハンドリングが行われることを確認

**Preconditions**:
- ScoreRepositoryAdapterが初期化されている

**Test Steps**:
1. tetrominoにnullを渡してdrawNextTetromino()を呼び出す
2. エラーが発生することを確認する

**Expected Results**:
- 適切なエラーメッセージが表示される
- システムがクラッシュしない

**Actual Results**:
[To be filled during test execution]

**Status**: [ ] Pass / [ ] Fail / [ ] Blocked

**Notes**:

---

### Test Case UT-054: ScoreRepositoryAdapter.updateCellSize - 正常系

**Description**: ScoreRepositoryAdapterのupdateCellSizeメソッドが正常に動作することを確認

**Preconditions**:
- ScoreRepositoryAdapterが初期化されている
- 有効な入力パラメータが準備されている

**Test Steps**:
1. 有効なパラメータでupdateCellSize()を呼び出す
2. 戻り値を確認する
3. エラーが発生しないことを確認する

**Expected Results**:
- 期待されるvoid;  // レスポンシブ対応型の値が返される
- 例外が発生しない

**Actual Results**:
[To be filled during test execution]

**Status**: [ ] Pass / [ ] Fail / [ ] Blocked

**Notes**:

---

## 5. Test Coverage

### 5.1 Coverage Goals
- **Target Coverage**: 95%
- **Minimum Acceptable Coverage**: 80%

Example:
- **Target Coverage**: 95%
- **Minimum Acceptable Coverage**: 80%

### 5.2 Coverage Report Location
- Report Path: `coverage/lcov.info`
- HTML Report: `coverage/index.html`

Example:
- Report Path: `coverage/lcov.info`
- HTML Report: `coverage/index.html`

## 6. Test Execution Summary

| ID | Test Name | Status | Executed By | Date | Notes |
|----|-----------|--------|-------------|------|-------|
| UT-001  | |
| UT-002  | |
| UT-003  | |
| UT-004  | |

## 7. Defects Found

| Defect ID | Severity | Description | Status |
|-----------|----------|-------------|--------|
| | High/Medium/Low | | Open/In Progress/Fixed/Closed |

## 8. Sign-off

**Tested By**: _______________
**Date**: _______________
**Approved By**: _______________
**Date**: _______________

---

## Appendix A: Code Examples

### Example Test Code (Node.js/Vitest)

```typescript
import { describe, it, expect, vi } from 'vitest';
import { calculateTotal } from './calculator';

describe('calculateTotal', () => {
  it('should return correct sum for positive numbers', () => {
    const input = [10, 20, 30];
    const result = calculateTotal(input);
    expect(result).toBe(60);
  });

  it('should handle empty array', () => {
    const result = calculateTotal([]);
    expect(result).toBe(0);
  });

  it('should throw error for invalid input', () => {
    expect(() => calculateTotal([10, 'invalid', 30])).toThrow(TypeError);
  });
});
```

### Example Test Code (Java/JUnit 5)

```java
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {
    @Test
    void shouldReturnCorrectSumForPositiveNumbers() {
        Calculator calc = new Calculator();
        int[] input = {10, 20, 30};
        int result = calc.calculateTotal(input);
        assertEquals(60, result);
    }

    @Test
    void shouldHandleEmptyArray() {
        Calculator calc = new Calculator();
        int[] input = {};
        int result = calc.calculateTotal(input);
        assertEquals(0, result);
    }
}
```

### Example Test Code (PHP/PHPUnit)

```php
<?php
use PHPUnit\Framework\TestCase;

class CalculatorTest extends TestCase
{
    public function testShouldReturnCorrectSumForPositiveNumbers()
    {
        $calculator = new Calculator();
        $input = [10, 20, 30];
        $result = $calculator->calculateTotal($input);
        $this->assertEquals(60, $result);
    }

    public function testShouldHandleEmptyArray()
    {
        $calculator = new Calculator();
        $input = [];
        $result = $calculator->calculateTotal($input);
        $this->assertEquals(0, $result);
    }
}
```

## Appendix B: TDD Principles

### RED-GREEN-REFACTOR Cycle
1. **RED**: Write a failing test first
2. **GREEN**: Write minimum code to pass the test
3. **REFACTOR**: Improve code while keeping tests green

### Important Rule
**Tests represent specifications. Do NOT modify tests to match implementation.**

- ❌ NG: Implementation doesn't match test, so modify the test
- ✅ OK: Specification changed, so update the test accordingly
