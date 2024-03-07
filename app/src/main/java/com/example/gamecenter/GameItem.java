    package com.example.gamecenter;

    public class GameItem {
        private String name;
        private int score;
        private int iconResource; // Recurso del icono del juego

        public GameItem(String name, int score, int iconResource) {
            this.name = name;
            this.score = score;
            this.iconResource = iconResource;
        }

        public String getName() {
            return name;
        }

        public int getScore() {
            return score;
        }

        public int getIconResource() {
            return iconResource;
        }
    }
