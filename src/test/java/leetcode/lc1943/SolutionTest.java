package leetcode.lc1943;

import java.util.List;
import org.junit.jupiter.api.Test;

class SolutionTest {
    @Test
    void test() {
        int[][] segments = {{1, 4, 5}, {4, 7, 7}, {1, 7, 9}};
        List<List<Long>> res = new Solution2().splitPainting(segments);

    }
}
