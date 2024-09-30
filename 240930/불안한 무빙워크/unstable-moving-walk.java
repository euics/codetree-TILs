import java.io.*;
import java.util.*;

public class Main {
    static int[] movingWalk;
    static boolean[] personInfo;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        
        movingWalk = new int[2 * n];
        personInfo = new boolean[2 * n];
        st = new StringTokenizer(br.readLine());
        for(int i = 0; i < 2 * n; i++) {
            movingWalk[i] = Integer.parseInt(st.nextToken());
        }

        int answer = 0;
        while (true) {
            // 1번 무빙워크 한 칸 회전
            rotateMovingWalk(n);

            // 2번 사람 이동
            movePerson(n);

            // 3번 1번칸 사람 올리기
            insertPerson();

            answer++;

            // 4번 안정성 K개 확인
            if(countK(n, k)) {
                break;
            }
        }

        System.out.println(answer);

    }

    public static void rotateMovingWalk(int n) {
        int rearMovingWalk = movingWalk[2 * n - 1];
        boolean rearPersonInfo = personInfo[2 * n - 1];

        for(int i = 2 * n - 1; i >= 1; i--) {
            movingWalk[i] = movingWalk[i - 1];
            personInfo[i] = personInfo[i - 1];
        }

        movingWalk[0] = rearMovingWalk;
        personInfo[0] = rearPersonInfo;

        leaveMovingWalk(n);
    }

    public static void movePerson(int n) {
        /*
         1. 앞선 칸에 사람이 있을 경우 이동 X
         2. 안정성이 0인 경우 이동 X
        */
        for(int i = n - 2; i >= 0; i--) {
            if(personInfo[i + 1] == false && personInfo[i] == true && movingWalk[i + 1] != 0) {
                personInfo[i + 1] = true;
                personInfo[i] = false;
                movingWalk[i + 1]--;
            }
        }

        leaveMovingWalk(n);
    }

    public static void insertPerson() {
        /*
         1. 안정성 0
         2. 사람 없음
        */

        if(!personInfo[0] && movingWalk[0] != 0) {
            movingWalk[0]--;
            personInfo[0] = true;
        }
    }

    public static void leaveMovingWalk(int n) {
        /*
         1 2 3 과정 이후 N에 사람이 있을 경우 바로 내리기
        */
        if(personInfo[n]) {
            personInfo[n] = false;
        }
    }

    public static boolean countK(int n, int k) {
        /*
         1. 안정성 K개 이상 확인
         2. K개 이상이면 TRUE 반환
        */
        int cnt = 0;

        for(int i = 0; i < 2 * n; i++) {
            if(movingWalk[i] == 0) {
                cnt++;
            }
        }

        return cnt >= k;
    }
}