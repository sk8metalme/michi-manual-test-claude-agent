package com.example.tetris.adapter.inbound;

import com.example.tetris.application.dto.GameStateDTO;
import com.example.tetris.application.dto.TetrominoDTO;
import com.example.tetris.application.port.ScoreRepositoryPort;
import com.example.tetris.application.usecase.StartGameUseCase;
import com.example.tetris.domain.GameStatus;
import com.example.tetris.domain.Score;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * GameRestControllerの単体テスト。
 *
 * <p>MockMvcを使用して、HTTPエンドポイントの動作を検証します。</p>
 *
 * <h3>テスト対象:</h3>
 * <ul>
 *   <li>POST /api/game/start - ゲーム開始エンドポイント</li>
 *   <li>GET /api/scores - スコア履歴取得エンドポイント</li>
 * </ul>
 *
 * @author AI-DLC Development Team
 * @version 1.0.0
 * @since 2025-11-29
 */
@WebMvcTest(GameRestController.class)
class GameRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private StartGameUseCase startGameUseCase;

    @MockBean
    private ScoreRepositoryPort scoreRepositoryPort;

    private GameStateDTO mockGameStateDTO;
    private List<Score> mockScores;

    @BeforeEach
    void setUp() {
        // モックGameStateDTOの準備
        TetrominoDTO currentTetromino = new TetrominoDTO(
                "I",
                3,
                0,
                0
        );
        TetrominoDTO nextTetromino = new TetrominoDTO(
                "O",
                3,
                0,
                0
        );

        mockGameStateDTO = new GameStateDTO(
                GameStatus.PLAYING,
                currentTetromino,
                nextTetromino,
                new String[20][10], // 空のフィールド
                0, // 初期スコア
                1, // 初期レベル
                0  // 初期クリアライン数
        );

        // モックスコアリストの準備
        mockScores = Arrays.asList(
                new Score(1L, 1000, 5, 20, LocalDateTime.now()),
                new Score(2L, 800, 4, 15, LocalDateTime.now()),
                new Score(3L, 600, 3, 10, LocalDateTime.now())
        );
    }

    @Test
    @DisplayName("POST /api/game/start - ゲーム開始エンドポイントが初期状態を返す")
    void testStartGame() throws Exception {
        // Arrange
        when(startGameUseCase.startGame()).thenReturn(mockGameStateDTO);

        // Act & Assert
        mockMvc.perform(post("/api/game/start")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("PLAYING")))
                .andExpect(jsonPath("$.score", is(0)))
                .andExpect(jsonPath("$.level", is(1)))
                .andExpect(jsonPath("$.totalLinesCleared", is(0)))
                .andExpect(jsonPath("$.currentTetromino.type", is("I")))
                .andExpect(jsonPath("$.nextTetromino.type", is("O")));
    }

    @Test
    @DisplayName("GET /api/scores - スコア履歴取得エンドポイントが過去10件を返す")
    void testGetScores() throws Exception {
        // Arrange
        when(scoreRepositoryPort.findTop10ByOrderByScoreDesc()).thenReturn(mockScores);

        // Act & Assert
        mockMvc.perform(get("/api/scores")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].score", is(1000)))
                .andExpect(jsonPath("$[0].level", is(5)))
                .andExpect(jsonPath("$[0].totalLinesCleared", is(20)))
                .andExpect(jsonPath("$[1].score", is(800)))
                .andExpect(jsonPath("$[2].score", is(600)));
    }

    @Test
    @DisplayName("GET /api/scores - スコアが空の場合は空リストを返す")
    void testGetScoresWhenEmpty() throws Exception {
        // Arrange
        when(scoreRepositoryPort.findTop10ByOrderByScoreDesc()).thenReturn(List.of());

        // Act & Assert
        mockMvc.perform(get("/api/scores")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @DisplayName("POST /api/game/start - 内部エラー時に500エラーを返す")
    void testStartGameInternalError() throws Exception {
        // Arrange
        when(startGameUseCase.startGame()).thenThrow(new RuntimeException("Internal error"));

        // Act & Assert
        mockMvc.perform(post("/api/game/start")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("GET /api/scores - 内部エラー時に500エラーを返す")
    void testGetScoresInternalError() throws Exception {
        // Arrange
        when(scoreRepositoryPort.findTop10ByOrderByScoreDesc())
                .thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        mockMvc.perform(get("/api/scores")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }
}
