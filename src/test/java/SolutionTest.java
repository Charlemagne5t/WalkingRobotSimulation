import org.junit.Assert;
import org.junit.Test;

public class SolutionTest {
    @Test
    public void test1() {
        int[] commands = {4,-1,4,-2,4};
        int[][] obstacles = {{2,4}};

        int expected = 65;
        int actual = new Solution().robotSim(commands, obstacles);

        Assert.assertEquals(expected, actual);
    }
}
