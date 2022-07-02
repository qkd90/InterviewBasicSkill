public class test {
    public static void main(String[] args){
        int[] rain ={1,8,6,2,5,4,8,3,7};
        int ans = maxArea(rain);
        System.out.println(ans);
    }
    public static int maxArea(int[] height) {
        int left = 0, right = height.length - 1;
        int ans = 0;
        while (left < right) {
            int area = Math.min(height[left], height[right]) * (right - left);
            ans = Math.max(ans, area);
            if (height[left] <= height[right]) {
                ++left;
            }
            else {
                --right;
            }
        }
        return ans;
    }
}
