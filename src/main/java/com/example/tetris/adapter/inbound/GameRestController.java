package com.example.tetris.adapter.inbound;

import com.example.tetris.application.dto.GameStateDTO;
import com.example.tetris.application.dto.ScoreDTO;
import com.example.tetris.application.port.ScoreRepositoryPort;
import com.example.tetris.application.usecase.StartGameUseCase;
import com.example.tetris.domain.Score;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * ゲームREST APIコントローラー。
 *
 * <p>ヘキサゴナルアーキテクチャにおける「Inbound Adapter」として、
 * HTTPリクエストをアプリケーション層のユースケースに橋渡しします。</p>
 *
 * <h3>エンドポイント:</h3>
 * <ul>
 *   <li>POST /api/game/start - ゲーム開始</li>
 *   <li>GET /api/scores - スコア履歴取得（過去10件）</li>
 * </ul>
 *
 * <h3>責務:</h3>
 * <ul>
 *   <li>HTTPリクエストの受信とバリデーション</li>
 *   <li>ユースケースの呼び出し</li>
 *   <li>ドメインモデルからDTOへの変換</li>
 *   <li>HTTPレスポンスの返却</li>
 *   <li>エラーハンドリング</li>
 * </ul>
 *
 * @author AI-DLC Development Team
 * @version 1.0.0
 * @since 2025-11-29
 */
@RestController
@CrossOrigin(origins = "*") // フロントエンド接続許可（本番環境では適切なオリジンに制限）
public class GameRestController {

    private static final Logger logger = LoggerFactory.getLogger(GameRestController.class);

    private final StartGameUseCase startGameUseCase;
    private final ScoreRepositoryPort scoreRepositoryPort;

    /**
     * コンストラクタインジェクション。
     *
     * @param startGameUseCase ゲーム開始ユースケース
     * @param scoreRepositoryPort スコアリポジトリポート
     */
    public GameRestController(
            StartGameUseCase startGameUseCase,
            ScoreRepositoryPort scoreRepositoryPort
    ) {
        this.startGameUseCase = startGameUseCase;
        this.scoreRepositoryPort = scoreRepositoryPort;
    }

    /**
     * ゲーム開始エンドポイント。
     *
     * <p>新しいゲームを初期化し、初期状態をクライアントに返します。</p>
     *
     * <h3>HTTPメソッド: POST</h3>
     * <h3>パス: /api/game/start</h3>
     *
     * <h3>レスポンス:</h3>
     * <ul>
     *   <li>200 OK: ゲーム初期状態のGameStateDTO</li>
     *   <li>500 Internal Server Error: サーバー内部エラー</li>
     * </ul>
     *
     * @return 初期化されたゲーム状態のDTO
     */
    @PostMapping("/api/game/start")
    public ResponseEntity<GameStateDTO> startGame() {
        try {
            logger.info("ゲーム開始リクエストを受信しました");
            GameStateDTO gameState = startGameUseCase.startGame();
            logger.debug("ゲーム初期状態を返却: status={}, score={}", gameState.status(), gameState.score());
            return ResponseEntity.ok(gameState);
        } catch (Exception e) {
            logger.error("ゲーム開始中にエラーが発生しました", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * スコア履歴取得エンドポイント。
     *
     * <p>過去10件の高スコアを降順で取得します。</p>
     *
     * <h3>HTTPメソッド: GET</h3>
     * <h3>パス: /api/scores</h3>
     *
     * <h3>レスポンス:</h3>
     * <ul>
     *   <li>200 OK: ScoreDTOのリスト（最大10件）</li>
     *   <li>500 Internal Server Error: サーバー内部エラー</li>
     * </ul>
     *
     * @return 過去10件のスコアDTOリスト
     */
    @GetMapping("/api/scores")
    public ResponseEntity<List<ScoreDTO>> getScores() {
        try {
            logger.info("スコア履歴取得リクエストを受信しました");
            List<Score> scores = scoreRepositoryPort.findTop10ByOrderByScoreDesc();
            List<ScoreDTO> scoreDTOs = scores.stream()
                    .map(score -> new ScoreDTO(
                            score.id(),
                            score.score(),
                            score.level(),
                            score.totalLinesCleared(),
                            score.timestamp()
                    ))
                    .collect(Collectors.toList());
            logger.debug("スコア履歴を返却: 件数={}", scoreDTOs.size());
            return ResponseEntity.ok(scoreDTOs);
        } catch (Exception e) {
            logger.error("スコア履歴取得中にエラーが発生しました", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
