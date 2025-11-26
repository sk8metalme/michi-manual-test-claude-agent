package com.example.tetris;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Tetris Webアプリケーションのメインクラス。
 *
 * <p>このアプリケーションは以下の機能を提供します：</p>
 * <ul>
 *   <li>Hexagonal Architecture (Ports & Adapters) に基づく設計</li>
 *   <li>Spring Boot 3.x + Java 17による実装</li>
 *   <li>WebSocketによるリアルタイム通信</li>
 *   <li>H2 (dev/test) / PostgreSQL (prod) データベース対応</li>
 * </ul>
 *
 * @author AI-DLC Development Team
 * @version 1.0.0
 * @since 2025-11-26
 */
@SpringBootApplication
public class TetrisApplication {

    /**
     * アプリケーションのエントリーポイント。
     *
     * @param args コマンドライン引数
     */
    public static void main(String[] args) {
        SpringApplication.run(TetrisApplication.class, args);
    }
}
