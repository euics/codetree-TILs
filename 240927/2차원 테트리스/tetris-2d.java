import java.io.*;
import java.util.*;

public class Main {
    static int[][] red, yellow;
    static int score = 0;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int k = Integer.parseInt(br.readLine());

        red = new int[4][7];
        yellow = new int[7][4];
        init();

        int answer = 0;
        for(int i = 0; i < k; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int t = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            int x = Integer.parseInt(st.nextToken());

            // 테트리스 생성
            placeRed(t, y);
            placeYellow(t, x);

            // 점수 계산
            clearRed();
            clearYellow();

            // 연한 부분 처리
            processLightRed();
            processLightYellow();
        }

        System.out.println(score);
        System.out.println(countBlocks());
    }

    public static void init() {
        for(int row = 0; row < 4; row++) {
            red[row][6] = 1;
        }
        for(int col = 0; col < 4; col++) {
            yellow[6][col] = 1;
        }
    }

    public static void placeRed(int t, int y) {
        if(t == 1) {
            for(int col = 0; col < 6; col++) {
                if(red[y][col] == 0 && red[y][col + 1] == 1) {
                    red[y][col] = 1;

                    break;
                }
            }
        }

        if(t == 2) {
            for(int col = 0; col < 5; col++) {
                if(red[y][col] == 0 && red[y][col + 1] == 0 && red[y][col + 2] == 1) {
                    red[y][col] = 1;
                    red[y][col + 1] = 1;

                    break;
                }
            }
        }

        if(t == 3) {
            for(int col = 0; col < 6; col++) {
                if(red[y][col] == 0 && red[y + 1][col] == 0) {
                    if(red[y][col + 1] == 1 || red[y + 1][col + 1] == 1) {
                        red[y][col] = 1;
                        red[y + 1][col] = 1;

                        break;
                    }
                }
            }
        }
    }

    public static void placeYellow(int t, int x) {
        if(t == 1) {
            for(int row = 0; row < 6; row++) {
                if(yellow[row][x] == 0 && yellow[row + 1][x] == 1) {
                    yellow[row][x] = 1;

                    break;
                }
            }
        }

        if(t == 2) {
            for(int row = 0; row < 6; row++) {
                if(yellow[row][x] == 0 && yellow[row][x + 1] == 0) {
                    if(yellow[row + 1][x] == 1 || yellow[row + 1][x + 1] == 1) {
                        yellow[row][x] = 1;
                        yellow[row][x + 1] = 1;

                        break;
                    }
                }
            }
        }

        if(t == 3) {
            for(int row = 0; row < 5; row++) {
                if(yellow[row][x] == 0 && yellow[row + 1][x] == 0 && yellow[row + 2][x] == 1){
                    yellow[row][x] = 1;
                    yellow[row + 1][x] = 1;

                    break;
                }
            }
        }
    }

    public static void clearRed() {
        for(int col = 5; col >= 2; col--) {
            boolean bool = true;

            for(int row = 0; row < 4; row++) {
                if(red[row][col] == 0) {
                    bool = false;
                    break;
                }
            }

            if(bool) {
                score++;

                for(int row = 0; row < 4; row++) {
                    red[row][col] = 0;
                }

                dropRed(col);
            }
        }
    }

    public static void dropRed(int column) {
        for(int row = 0; row < 4; row++) {
            for(int col = column; col >= 1; col--) {
                red[row][col] = red[row][col - 1];
            }
        }

        for(int row = 0; row < 4; row++) {
            red[row][0] = 0;
        }
    }

    public static void clearYellow() {
        for(int row = 5; row >= 2; row--) {
            boolean bool = true;

            for(int col = 0; col < 4; col++) {
                if(yellow[row][col] == 0) {
                    bool = false;
                    
                    break;
                }
            }

            if(bool) {
                score++;

                for(int col = 0; col < 4; col++) {
                    yellow[row][col] = 0;
                }

                dropYellow(row);
            }
        }
    }

    public static void dropYellow(int row) {
        for(int col = 0; col < 4; col++) {
            for(int y = row; y >= 1; y--) {
                yellow[y][col] = yellow[y - 1][col];
            }
        }

        for(int col = 0; col < 4; col++) {
            yellow[0][col] = 0;
        }
    }

    public static void processLightRed() {
        int max = 0;

        for(int row = 0; row < 4; row++) {
            if(red[row][0] == 1) max = Math.max(max, 2);
            if(red[row][1] == 1) max = Math.max(max, 1);
        }

        if(max != 0) {
            for(int col = 5 - max + 1; col <= 5; col++) {
                for(int row = 0; row < 4; row++) {
                    red[row][col] = 0;
                }

                dropRed(col);
            }
        }
    }

    public static void processLightYellow() {
        int max = 0;

        for(int col = 0; col < 4; col++) {
            if(yellow[0][col] == 1) max = Math.max(max, 2);
            if(yellow[1][col] == 1) max = Math.max(max, 1);
        }

        if(max != 0) {
            for(int row = 5 - max + 1; row <= 5; row++) {
                for(int col = 0; col < 4; col++) {
                    yellow[row][col] = 0;
                }
                
                dropYellow(row);
            }
        }
    }

    public static int countBlocks() {
        int cnt = 0;

        for(int i = 0; i < 6; i++) {
            for(int j = 0; j < 4; j++) {
                cnt += yellow[i][j];
                cnt += red[j][i];
            }
        }

        return cnt;
    }
}