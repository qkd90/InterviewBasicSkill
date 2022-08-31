class Solution {
  public int maxArea(int[] height) {
    int left = 0, right = height.length - 1, res = 0;
    while (left < right) {
      res =
        height[i] < height[right]
          ? Math.max(res, (right - left) * height[i++])
          : Math.max(res, (right - left) * height[right--]);
    }
    return res;
  }
}
