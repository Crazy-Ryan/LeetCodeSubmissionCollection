package leetcode.lc1942;

import static org.junit.jupiter.api.Assertions.*;

import leetcode.lc1942.Solution;
import org.junit.jupiter.api.Test;

class SolutionTest {
    @Test
    void test() {
        int[][] times = {{3, 10}, {1, 5}, {2, 6}};
        int res = new Solution().smallestChair(times, 0);
        int expectation = 2;
        assertEquals(expectation, res);
    }
}