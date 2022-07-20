class Solution {
    public void moveZeroes(int[] nums) {
        int left,right = nums.length;
        for (;left >= 0;left--){
            if (nums[left] == 0){
                for(int i = left;i<right;i++){
                    nums[i]=nums[i+1]
                }
                nums[right] = 0;
                right = right - 1;
            }
        }
    }
}