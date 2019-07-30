package question001;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

/**
 * @author qianyihui
 * @date 2019-07-30
 *
 * 题目描述：
 * 为了找到自己满意的工作，牛牛收集了每种工作的难度和报酬。牛牛选工作的标准是在难度不超过自身能力值的情况下，牛牛选择报酬最高的工作。
 * 在牛牛选定了自己的工作后，牛牛的小伙伴们来找牛牛帮忙选工作，牛牛依然使用自己的标准来帮助小伙伴们。
 * 牛牛的小伙伴太多了，于是他只好把这个任务交给了你。
 *
 * 输入描述：
 * 每个输入包含一个测试用例。
 * 每个测试用例的第一行包含两个正整数，分别表示工作的数量N(N<=100000)和小伙伴的数量M(M<=100000)。
 * 接下来的N行每行包含两个正整数，分别表示该项工作的难度Di(Di<=1000000000)和报酬Pi(Pi<=1000000000)。
 * 接下来的一行包含M个正整数，分别表示M个小伙伴的能力值Ai(Ai<=1000000000)。
 * 保证不存在两项工作的报酬相同。
 *
 * 输出描述：
 * 对于每个小伙伴，在单独的一行输出一个正整数表示他能得到的最高报酬。一个工作可以被多个人选择。
 *
 * 示例1：
 *
 * 输入：
 *
 * 3 3
 * 1 100
 * 10 1000
 * 1000000000 1001
 * 9 10 1000000000
 *
 * 输出：
 *
 * 100
 * 1000
 * 1001
 *
 * 时间复杂度是O(NlogN)。空间复杂度是O(N)。
 *
 * 运行时间：2128ms。占用内存：78688k。
 */
public class Main {
    private static class Work {
        int hard;
        int salary;
        Work(int hard, int salary) {
            this.hard = hard;
            this.salary = salary;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int N = scanner.nextInt(), M = scanner.nextInt();
        Work[] works = new Work[N];
        for (int i = 0; i < N; i++) {
            int num1 = scanner.nextInt(), num2 = scanner.nextInt();
            works[i] = new Work(num1, num2);
        }
        //对Work按难度从易到难进行排序
        Arrays.sort(works, Comparator.comparingInt(work -> work.hard));
        //maxSalary[i]代表works数组[0, i]范围内的工资的最大值
        int[] maxSalary = new int[N];
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < N; i++) {
            maxSalary[i] = Math.max(max, works[i].salary);
            max = maxSalary[i];
        }
        for (int i = 0; i < M; i++) {
            int num = scanner.nextInt();
            int index = ceil(works, num);   //寻找难度上界，ceil函数二分查找
            if (index == works.length) {    //如果说个人能力num大于所有工作的难度值
                System.out.println(maxSalary[N - 1]);   //取工资最高的那个工作
            } else if (works[index].hard > num) {   //找到了难度上界，但是该上界值大于个人能力num
                if (index == 0) {   //如果上界索引是0，这代表这个人不胜任任何工作
                    System.out.println("0");    //不胜任任何工作，其最高工资自然是0
                } else {    //否则，这个人能胜任[0, index - 1]范围内的工作
                    System.out.println(maxSalary[index - 1]);
                }
            } else if (works[index].hard == num) {  //找到了难度上界，且该上界值等于个人能力num
                System.out.println(maxSalary[index]);
            }
        }
    }

    private static int ceil(Work[] works, int hard) {
        int left = 0, right = works.length;
        while (left < right) {
            int mid = left + ((right - left) >> 1);
            if (works[mid].hard <= hard) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        if (left - 1 >= 0 && works[left - 1].hard == hard) {
            return left - 1;
        }
        return left;
    }
}