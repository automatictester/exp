package uk.co.automatictester.matrix;

import org.testng.annotations.Test;

public class MatrixMultiplication {

    //    Algoritm to calculate c[0][0]:
    //    int x1 = a[0][0] * b[0][0];
    //    int x2 = a[0][1] * b[1][0];
    //    int x3 = a[0][2] * b[2][0];
    //    int x = x1 + x2 + x3;

    private int[][] a = {
            {-2, -3, 1},
            {-1, 4, 0},
            {1, 1, 1}
    };

    private int[][] b = {
            {-2, -1, 2},
            {3, 0, 1},
            {2, 2, -1},
    };

    private int[][] c;

    @Test
    public void multiply() {

        int N = a.length;
        c = new int[N][N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                c[i][j] = 0;
                for (int k = 0; k < N; k++) {
                    c[i][j] += a[i][k] * b[k][j];
                }
            }
        }

        print(c);
    }

    private void print(int[][] matrix) {
        int N = matrix.length;
        for (int row = 0; row < N; row++) {
            for (int col = 0; col < N; col++) {
                System.out.print(c[row][col] + " ");
            }
            System.out.println();
        }
    }
}
