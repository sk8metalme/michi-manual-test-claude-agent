# E2E Test Specification: tetris-web-app e2eテスト

**Author**: Auto-generated
**Date**: 2025-11-26
**Version**: 1.0

## 1. Overview

### 1.1 Purpose
tetris-web-appのエンドユーザーシナリオが完全に動作することを確認する

Example: To verify that end users can successfully complete critical user journeys in the  from start to finish in a real browser environment.

### 1.2 Scope
tetris-web-appの全ユーザーフロー

Example: This test specification covers the complete user registration and login flow, including UI interactions, form validations, and successful authentication.

### 1.3 Testing Tool
- **Tool**: Vitest
- **Version**: 1.0.0

Example:
- **Tool**: Playwright / Selenium WebDriver / Cypress
- **Version**: 1.40.0 / 4.15.0 / 13.6.0

## 2. Test Environment

### 2.1 Software Requirements
- Browser Automation Tool:  1.0.0
- Browsers: 
- Application Environment: 
- Backend API: 
- Test Data Management: 

Example:
- Browser Automation Tool: Playwright 1.40.0
- Browsers: Chrome 120, Firefox 121, Safari 17
- Application Environment: [https://staging.example.com](https://staging.example.com)
- Backend API: [https://api-staging.example.com](https://api-staging.example.com)
- Test Data Management: Test database with seeded data

### 2.2 Hardware Requirements
- Test Machine: 
- Display Resolution: 
- Network: 

Example:
- Test Machine: macOS/Windows/Linux with 8GB RAM
- Display Resolution: 1920x1080 (Desktop), 768x1024 (Tablet), 375x667 (Mobile)
- Network: Stable internet connection (minimum 10 Mbps)

### 2.3 Test Data
- Test user accounts: ``
- Test data setup script: ``
- Data cleanup script: ``
- Environment variables: ``

## 3. User Flows

### 3.1 User Journey Map

```text
 →  →  →  → 
```

Example:

```text
Landing Page → Sign Up Form → Email Verification → Profile Setup → Dashboard
```

### 3.2 User Flow Details

| Flow ID | Flow Name | Description | Priority | Steps |
|---------|-----------|-------------|----------|-------  | High/Medium/Low   |  | High/Medium/Low |  |

Example:

| Flow ID | Flow Name | Description | Priority | Steps |
|---------|-----------|-------------|----------|-------|
| UF-001 | User Registration | New user signs up and verifies email | High | 5 |
| UF-002 | Product Purchase | User browses, adds to cart, and completes checkout | High | 8 |
| UF-003 | Password Reset | User resets forgotten password | Medium | 4 |

### 3.3 Browser/Device Matrix

Test each user flow on the following combinations:

| Browser | Version | Desktop | Tablet | Mobile | Priority |
|---------|---------|---------|--------|--------|----------|
| Chrome |  | ✓ | ✓ | ✓ | High |
| Firefox |  | ✓ | - | - | Medium |
| Safari |  | ✓ | ✓ | ✓ | High |
| Edge |  | ✓ | - | - | Low |

Example:

| Browser | Version | Desktop | Tablet | Mobile | Priority |
|---------|---------|---------|--------|--------|----------|
| Chrome | 120+ | ✓ | ✓ | ✓ | High |
| Firefox | 121+ | ✓ | - | - | Medium |
| Safari | 17+ | ✓ | ✓ | ✓ | High |
| Edge | 120+ | ✓ | - | - | Low |

**Priority Guide**:
- High: Must test on all marked platforms
- Medium: Test on desktop only
- Low: Test if time permits

## 4. Test Cases

### Test Case E2E-001: ゲームプレイ - テトリミノ操作 - ハッピーパス

**Description**: プレイヤーとして、テトリミノ（ブロック）を自由に操作できることで、ゲームを楽しめるが達成できることをエンドツーエンドで確認

**Preconditions**:
- アプリケーションが起動している
- テストユーザーが作成されている
- テストデータが準備されている

**Test Steps**:
1. When プレイヤーが左矢印キーを押下, the テトリスアプリ shall テトリミノを1マス左に移動する...を実行
2. When プレイヤーが右矢印キーを押下, the テトリスアプリ shall テトリミノを1マス右に移動する...を実行
3. When プレイヤーが上矢印キーを押下, the テトリスアプリ shall テトリミノを時計回りに90度回転する...を実行

**Expected Results**:
- プレイヤーが左矢印キーを押下, the テトリスアプリ shall テトリミノを1マス左に移動する
- プレイヤーが右矢印キーを押下, the テトリスアプリ shall テトリミノを1マス右に移動する
- プレイヤーが上矢印キーを押下, the テトリスアプリ shall テトリミノを時計回りに90度回転する

**Actual Results**:
[To be filled during test execution]

**Status**: [ ] Pass / [ ] Fail / [ ] Blocked

**Notes**:

---

### Test Case E2E-002: ゲームプレイ - テトリミノ操作 - エラーフロー

**Description**: エラー発生時に適切にハンドリングされることを確認

**Preconditions**:
- アプリケーションが起動している

**Test Steps**:
1. 無効な入力でフローを開始する
2. エラーメッセージが表示されることを確認する
3. ユーザーが回復できることを確認する

**Expected Results**:
- 適切なエラーメッセージが表示される
- ユーザーが操作を継続できる
- データの整合性が保たれる

**Actual Results**:
[To be filled during test execution]

**Status**: [ ] Pass / [ ] Fail / [ ] Blocked

**Notes**:

---

### Test Case E2E-003: ゲームプレイ - テトリミノ自動落下 - ハッピーパス

**Description**: プレイヤーとして、テトリミノが自動的に落下することで、継続的なゲーム進行を体験できるが達成できることをエンドツーエンドで確認

**Preconditions**:
- アプリケーションが起動している
- テストユーザーが作成されている
- テストデータが準備されている

**Test Steps**:
1. When ゲームが開始される, the テトリスアプリ shall ランダムなテトリミノをフィールド上部中央に出現させる...を実行
2. While ゲームが進行中である, the テトリスアプリ shall テトリミノを一定間隔（初期値1秒）で1マス下に自動落下させる...を実行
3. When テトリミノがフィールド底部または他のブロックに接触, the テトリスアプリ shall テトリミノを固定しフィールドに配置する...を実行

**Expected Results**:
- ゲームが開始される, the テトリスアプリ shall ランダムなテトリミノをフィールド上部中央に出現させる
- ゲームが進行中である, the テトリスアプリ shall テトリミノを一定間隔（初期値1秒）で1マス下に自動落下させる
- テトリミノがフィールド底部または他のブロックに接触, the テトリスアプリ shall テトリミノを固定しフィールドに配置する

**Actual Results**:
[To be filled during test execution]

**Status**: [ ] Pass / [ ] Fail / [ ] Blocked

**Notes**:

---

### Test Case E2E-004: ゲームロジック - ライン消去とスコア - ハッピーパス

**Description**: プレイヤーとして、ラインを揃えて消去しスコアを獲得することで、ゲームの達成感を得られるが達成できることをエンドツーエンドで確認

**Preconditions**:
- アプリケーションが起動している
- テストユーザーが作成されている
- テストデータが準備されている

**Test Steps**:
1. When 横一列がブロックで完全に埋まる, the テトリスアプリ shall そのラインを消去する...を実行
2. When ラインが消去される, the テトリスアプリ shall 消去されたライン上部のすべてのブロックを1マス下に落下させる...を実行
3. When 1ラインが消去される, the テトリスアプリ shall スコアに100点を加算する...を実行

**Expected Results**:
- 横一列がブロックで完全に埋まる, the テトリスアプリ shall そのラインを消去する
- ラインが消去される, the テトリスアプリ shall 消去されたライン上部のすべてのブロックを1マス下に落下させる
- 1ラインが消去される, the テトリスアプリ shall スコアに100点を加算する

**Actual Results**:
[To be filled during test execution]

**Status**: [ ] Pass / [ ] Fail / [ ] Blocked

**Notes**:

---

### Test Case E2E-005: ゲームロジック - レベルと難易度 - ハッピーパス

**Description**: プレイヤーとして、ゲーム進行に伴い難易度が上昇することで、継続的なチャレンジを体験できるが達成できることをエンドツーエンドで確認

**Preconditions**:
- アプリケーションが起動している
- テストユーザーが作成されている
- テストデータが準備されている

**Test Steps**:
1. The テトリスアプリ shall ゲーム開始時のレベルを1に設定する...を実行
2. When 累計消去ライン数が10ラインに到達, the テトリスアプリ shall レベルを1増加させる...を実行
3. When レベルが上昇, the テトリスアプリ shall テトリミノの落下速度を10%増加させる...を実行

**Expected Results**:
- テトリスアプリ shall ゲーム開始時のレベルを1に設定する
- 累計消去ライン数が10ラインに到達, the テトリスアプリ shall レベルを1増加させる
- レベルが上昇, the テトリスアプリ shall テトリミノの落下速度を10%増加させる

**Actual Results**:
[To be filled during test execution]

**Status**: [ ] Pass / [ ] Fail / [ ] Blocked

**Notes**:

---

### Test Case E2E-006: ゲームロジック - ゲームオーバーと再スタート - ハッピーパス

**Description**: プレイヤーとして、ゲーム終了条件を明確に理解し、簡単に再プレイできるが達成できることをエンドツーエンドで確認

**Preconditions**:
- アプリケーションが起動している
- テストユーザーが作成されている
- テストデータが準備されている

**Test Steps**:
1. If 新しいテトリミノの出現位置に既存のブロックが存在する, then the テトリスアプリ shall ゲームオーバー状態に遷移する...を実行
2. When ゲームオーバーが発生, the テトリスアプリ shall 「ゲームオーバー」メッセージと最終スコアを表示する...を実行
3. When ゲームオーバー画面が表示される, the テトリスアプリ shall 「再スタート」ボタンを表示する...を実行

**Expected Results**:
- 新しいテトリミノの出現位置に既存のブロックが存在する, then the テトリスアプリ shall ゲームオーバー状態に遷移する
- ゲームオーバーが発生, the テトリスアプリ shall 「ゲームオーバー」メッセージと最終スコアを表示する
- ゲームオーバー画面が表示される, the テトリスアプリ shall 「再スタート」ボタンを表示する

**Actual Results**:
[To be filled during test execution]

**Status**: [ ] Pass / [ ] Fail / [ ] Blocked

**Notes**:

---

### Test Case E2E-007: ゲームロジック - ゲームオーバーと再スタート - エラーフロー

**Description**: エラー発生時に適切にハンドリングされることを確認

**Preconditions**:
- アプリケーションが起動している

**Test Steps**:
1. 無効な入力でフローを開始する
2. エラーメッセージが表示されることを確認する
3. ユーザーが回復できることを確認する

**Expected Results**:
- 適切なエラーメッセージが表示される
- ユーザーが操作を継続できる
- データの整合性が保たれる

**Actual Results**:
[To be filled during test execution]

**Status**: [ ] Pass / [ ] Fail / [ ] Blocked

**Notes**:

---

### Test Case E2E-008: ユーザーインターフェース - ゲーム画面 - ハッピーパス

**Description**: プレイヤーとして、直感的で見やすいゲーム画面で快適にプレイできるが達成できることをエンドツーエンドで確認

**Preconditions**:
- アプリケーションが起動している
- テストユーザーが作成されている
- テストデータが準備されている

**Test Steps**:
1. The テトリスアプリ shall 10列×20行のゲームフィールドを表示する...を実行
2. The テトリスアプリ shall 各ブロックを異なる色で視覚的に区別可能に表示する...を実行
3. The テトリスアプリ shall 次に出現するテトリミノ（ネクストブロック）をプレビュー表示する...を実行

**Expected Results**:
- テトリスアプリ shall 10列×20行のゲームフィールドを表示する
- テトリスアプリ shall 各ブロックを異なる色で視覚的に区別可能に表示する
- テトリスアプリ shall 次に出現するテトリミノ（ネクストブロック）をプレビュー表示する

**Actual Results**:
[To be filled during test execution]

**Status**: [ ] Pass / [ ] Fail / [ ] Blocked

**Notes**:

---

### Test Case E2E-009: データ永続化 - スコア保存 - ハッピーパス

**Description**: プレイヤーとして、自分のスコア記録を保存し過去の成績を確認できるが達成できることをエンドツーエンドで確認

**Preconditions**:
- アプリケーションが起動している
- テストユーザーが作成されている
- テストデータが準備されている

**Test Steps**:
1. When ゲームオーバーが発生, the テトリスアプリ shall 最終スコア、レベル、消去ライン数をデータベースに保存する...を実行
2. When スコアを保存, the テトリスアプリ shall タイムスタンプを付与する...を実行
3. The テトリスアプリ shall スコア履歴画面で過去10件のプレイ記録を表示する...を実行

**Expected Results**:
- ゲームオーバーが発生, the テトリスアプリ shall 最終スコア、レベル、消去ライン数をデータベースに保存する
- スコアを保存, the テトリスアプリ shall タイムスタンプを付与する
- テトリスアプリ shall スコア履歴画面で過去10件のプレイ記録を表示する

**Actual Results**:
[To be filled during test execution]

**Status**: [ ] Pass / [ ] Fail / [ ] Blocked

**Notes**:

---

### Test Case E2E-010: アーキテクチャ - オニオンアーキテクチャ適用 - ハッピーパス

**Description**: 開発チームとして、保守性と拡張性の高いアーキテクチャを実現するが達成できることをエンドツーエンドで確認

**Preconditions**:
- アプリケーションが起動している
- テストユーザーが作成されている
- テストデータが準備されている

**Test Steps**:
1. The テトリスアプリ shall ドメイン層（Domain Layer）にゲームロジックとビジネスルールを配置する...を実行
2. The テトリスアプリ shall アプリケーション層（Application Layer）にユースケース実装を配置する...を実行
3. The テトリスアプリ shall インフラストラクチャ層（Infrastructure Layer）にデータベースアクセスと外部システム連携を配置する...を実行

**Expected Results**:
- テトリスアプリ shall ドメイン層（Domain Layer）にゲームロジックとビジネスルールを配置する
- テトリスアプリ shall アプリケーション層（Application Layer）にユースケース実装を配置する
- テトリスアプリ shall インフラストラクチャ層（Infrastructure Layer）にデータベースアクセスと外部システム連携を配置する

**Actual Results**:
[To be filled during test execution]

**Status**: [ ] Pass / [ ] Fail / [ ] Blocked

**Notes**:

---

### Test Case E2E-011: 技術要件 - Spring Boot実装 - ハッピーパス

**Description**: 開発チームとして、Spring Bootフレームワークを活用し効率的に開発するが達成できることをエンドツーエンドで確認

**Preconditions**:
- アプリケーションが起動している
- テストユーザーが作成されている
- テストデータが準備されている

**Test Steps**:
1. The テトリスアプリ shall Java 17以上を使用する...を実行
2. The テトリスアプリ shall Spring Boot 3.x系を使用する...を実行
3. The テトリスアプリ shall Spring Data JPAを使用してデータベースアクセスを実装する...を実行

**Expected Results**:
- テトリスアプリ shall Java 17以上を使用する
- テトリスアプリ shall Spring Boot 3.x系を使用する
- テトリスアプリ shall Spring Data JPAを使用してデータベースアクセスを実装する

**Actual Results**:
[To be filled during test execution]

**Status**: [ ] Pass / [ ] Fail / [ ] Blocked

**Notes**:

---

### Test Case E2E-012: 非機能要件 - パフォーマンスと品質 - ハッピーパス

**Description**: プレイヤーとして、快適でストレスのないゲーム体験を得られるが達成できることをエンドツーエンドで確認

**Preconditions**:
- アプリケーションが起動している
- テストユーザーが作成されている
- テストデータが準備されている

**Test Steps**:
1. The テトリスアプリ shall ブロック移動・回転操作のレスポンスタイムを100ミリ秒以内とする...を実行
2. The テトリスアプリ shall ゲーム画面の描画を毎秒60フレーム（60 FPS）で実行する...を実行
3. The テトリスアプリ shall 同時に5ユーザーのゲームセッションをサポートする...を実行

**Expected Results**:
- テトリスアプリ shall ブロック移動・回転操作のレスポンスタイムを100ミリ秒以内とする
- テトリスアプリ shall ゲーム画面の描画を毎秒60フレーム（60 FPS）で実行する
- テトリスアプリ shall 同時に5ユーザーのゲームセッションをサポートする

**Actual Results**:
[To be filled during test execution]

**Status**: [ ] Pass / [ ] Fail / [ ] Blocked

**Notes**:

---

## 5. Test Execution Summary

| ID | Test Name | Flow | Browser | Device | Status | Date | Notes |
|----|-----------|------|---------|--------|--------|------|-------|
| E2E-001 |  |  | Chrome | Desktop 
| E2E-002 |  |  | Chrome | Desktop 
| E2E-003 |  |  | Firefox | Desktop 
| E2E-004 |  |  | Chrome | Mobile 

## 6. Defects Found

| Defect ID | Severity | Description | Browser/Device | Screenshot/Video | Status |
|-----------|----------|-------------|----------------|------------------|--------|
| | High/Medium/Low  Open/In Progress/Fixed/Closed |

## 7. Sign-off

**Tested By**: _______________
**Date**: _______________
**Approved By**: _______________
**Date**: _______________

---

## Appendix A: Test Environment Setup

### Playwright Setup

```bash
# Install Playwright
npm install -D @playwright/test

# Install browsers
npx playwright install

# Run tests
npx playwright test

# Run tests in UI mode
npx playwright test --ui

# Generate HTML report
npx playwright show-report
```

### Selenium WebDriver Setup

```bash
# Install Selenium (Node.js)
npm install selenium-webdriver

# Download browser drivers
# ChromeDriver, GeckoDriver, etc.

# Run tests
node e2e-tests/registration.test.js
```

### Cypress Setup

```bash
# Install Cypress
npm install -D cypress

# Open Cypress
npx cypress open

# Run tests headless
npx cypress run

# Run specific test
npx cypress run --spec "cypress/e2e/registration.cy.js"
```

## Appendix B: Code Examples

### Example E2E Test Code (Playwright)

```typescript
import { test, expect } from '@playwright/test';

test('User registration flow', async ({ page }) => {
  // Navigate to landing page
  await page.goto('https://staging.example.com');

  // Click sign up button
  await page.click('text=Sign Up');

  // Fill registration form
  await page.fill('input[name="name"]', 'Test User');
  await page.fill('input[name="email"]', 'test@example.com');
  await page.fill('input[name="password"]', 'Test1234!');

  // Submit form
  await page.click('button[type="submit"]');

  // Verify confirmation message
  await expect(page.locator('text=Account created successfully')).toBeVisible();

  // Verify redirect to dashboard
  await expect(page).toHaveURL(/.*dashboard/);
  await expect(page.locator('text=Welcome, Test User')).toBeVisible();
});

test('Registration with invalid email', async ({ page }) => {
  await page.goto('https://staging.example.com/signup');

  await page.fill('input[name="email"]', 'notanemail');
  await page.fill('input[name="password"]', 'Test1234!');
  await page.click('button[type="submit"]');

  // Verify error message
  await expect(page.locator('text=Please enter a valid email')).toBeVisible();
});
```

### Example E2E Test Code (Selenium WebDriver)

```javascript
const { Builder, By, until } = require('selenium-webdriver');

async function testUserRegistration() {
  let driver = await new Builder().forBrowser('chrome').build();

  try {
    // Navigate to landing page
    await driver.get('https://staging.example.com');

    // Click sign up button
    await driver.findElement(By.linkText('Sign Up')).click();

    // Fill registration form
    await driver.findElement(By.name('name')).sendKeys('Test User');
    await driver.findElement(By.name('email')).sendKeys('test@example.com');
    await driver.findElement(By.name('password')).sendKeys('Test1234!');

    // Submit form
    await driver.findElement(By.css('button[type="submit"]')).click();

    // Wait for confirmation
    await driver.wait(until.elementLocated(By.xpath('//*[contains(text(), "Account created")]')), 5000);

    // Verify redirect
    let currentUrl = await driver.getCurrentUrl();
    assert(currentUrl.includes('dashboard'));

  } finally {
    await driver.quit();
  }
}

testUserRegistration();
```

### Example E2E Test Code (Cypress)

```javascript
describe('User Registration Flow', () => {
  it('should complete registration successfully', () => {
    // Navigate to landing page
    cy.visit('https://staging.example.com');

    // Click sign up button
    cy.contains('Sign Up').click();

    // Fill registration form
    cy.get('input[name="name"]').type('Test User');
    cy.get('input[name="email"]').type('test@example.com');
    cy.get('input[name="password"]').type('Test1234!');

    // Submit form
    cy.get('button[type="submit"]').click();

    // Verify confirmation
    cy.contains('Account created successfully').should('be.visible');

    // Verify redirect to dashboard
    cy.url().should('include', '/dashboard');
    cy.contains('Welcome, Test User').should('be.visible');
  });

  it('should show error for invalid email', () => {
    cy.visit('https://staging.example.com/signup');

    cy.get('input[name="email"]').type('notanemail');
    cy.get('input[name="password"]').type('Test1234!');
    cy.get('button[type="submit"]').click();

    // Verify error message
    cy.contains('Please enter a valid email').should('be.visible');
  });
});
```

## Appendix C: Screenshot and Video Configuration

### Playwright Configuration

```typescript
// playwright.config.ts
export default {
  use: {
    screenshot: 'only-on-failure',
    video: 'retain-on-failure',
    trace: 'retain-on-failure',
  },
};
```

### Cypress Configuration

```javascript
// cypress.config.js
module.exports = {
  video: true,
  screenshotOnRunFailure: true,
  videosFolder: 'cypress/videos',
  screenshotsFolder: 'cypress/screenshots',
};
```

## Appendix D: Execution Timing

## Phase B (Before Release) - Manual Execution

E2E tests are executed manually before creating a release tag:

1. After PR is merged to main branch
2. Before creating a release tag
3. Run all E2E tests in Phase B
4. Verify all critical user flows pass before proceeding to release

E2E tests are **NOT** executed automatically in CI/CD during PR phase (only unit tests run automatically).
