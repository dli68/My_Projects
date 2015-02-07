import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;
import java.util.Vector;
import java.util.function.BiFunction;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;


public class IOA {

	/*online coding challenge for Indeed*/
	public static void getQ(int Q, int M, Scanner s){
		int i,j,v=0,c=0,val;
		String nextLine = null;
		Scanner scraper;
		double size,k;
		ArrayList<Integer> result = new ArrayList<Integer>();
		for(i=0;i<M;i++){
			System.out.println("enter next line:");
			try{
				nextLine = s.nextLine();
				scraper = new Scanner(nextLine);
				v=scraper.nextInt();
				c=scraper.nextInt();
				for(j=0;j<c;j++){
					result.add(v);
				}
			} catch (Exception e){
				System.out.println("Error! Retry it!");
				i--;
			}
		}
		size = result.size();
		Collections.sort(result);
		for(k=1;k<M-1;k++){
			val = result.get( (int)Math.ceil(size*k/(double)M) );
			System.out.println(val);
		}
		
	}
	public static void getQ_test(){
		Scanner scanner = new Scanner(System.in);
		int Q,M;
		try{
			System.out.println("enter Q:");
			Q = Integer.parseInt(scanner.nextLine());
			System.out.println("enter M:");
			M = Integer.parseInt(scanner.nextLine());
			getQ(Q,M,scanner);
		}catch (Exception e){
			System.out.println("Error! Program stopped");
		}
	}
	
	
	/*Valid Palindrome from leetcode*/
	/*Given a string, determine if it is a palindrome, considering only alphanumeric characters and ignoring cases.*/
    public static boolean isPalindrome(String s) {
    		if(s==null){
    			return false;
    		}
        char[] tochar=s.toCharArray();
        int leng=tochar.length;
        char leftchar, rightchar;
        int j=leng-1;
        for(int i=0;i<leng;i++){
        		leftchar=tochar[i];
        		if(charvalcheck(leftchar)){
        			while(true){
        				if(j<=i){
        					return true;
        				}
        				rightchar=tochar[j];
        				if(charvalcheck(rightchar)){
        					if(charequal(leftchar,rightchar)){
        						j--;
        						break;
        					}
        					return false;
        				}
        				j--;
        			}
        		}
        }
        return true;
    }
    public static boolean charvalcheck(char ch){
    		return (('a'<=ch)&&('z'>=ch)) || (('A'<=ch)&&('Z'>=ch)) || (('0'<=ch)&&('9'>=ch));
    }
    public static boolean charequal(char a, char b){
    		return a==b || a==b-32 || a==b+32;
    }
    public static void isPalindrome_test(){
    		System.out.println(isPalindrome("A man, a plan, a canal: Panama"));
    		System.out.println(isPalindrome("race a car"));
    		System.out.println(isPalindrome("a"));
    		System.out.println(isPalindrome(""));
    		System.out.println(isPalindrome(null));
    }
    
    
	
	/*http://www.geeksforgeeks.org/given-a-number-n-generate-bit-patterns-from-0-to-2n-1-so-that-successive-patterns-differ-by-one-bit/*/
    /*KMP????????????????????*/

    
    
    /*Roman to Integer*/
    /*Given a Roman numeral, convert it to an integer. Input is guaranteed to be within the range from 1 to 3999.*/
    /*answer from leetcode*/
    public static int romanToInt(String s) {
    	    int res = 0;
    	    for (int i = s.length() - 1; i >= 0; i--) {
    	        char c = s.charAt(i);
    	        switch (c) {
    	        case 'I':
    	            res += (res >= 5 ? -1 : 1);
    	            break;
    	        case 'V':
    	            res += 5;
    	            break;
    	        case 'X':
    	            res += 10 * (res >= 50 ? -1 : 1);
    	            break;
    	        case 'L':
    	            res += 50;
    	            break;
    	        case 'C':
    	            res += 100 * (res >= 500 ? -1 : 1);
    	            break;
    	        case 'D':
    	            res += 500;
    	            break;
    	        case 'M':
    	            res += 1000;
    	            break;
    	        }
    	    }
    	    return res;
    }
    
    
    
    
    /*Decode Ways from leetcode*/
    /*A message containing letters from A-Z is being encoded to numbers using the following mapping:
    	'A' -> 1   	'B' -> 2   	...   	'Z' -> 26
    	Given an encoded message containing digits, determine the total number of ways to decode it.*/
    // Solution from leetcode: one pass; ridiculous awesome kick-ass idea.
    public static int numDecodings(String s) {
        if(s == null || s.length()==0 || s.charAt(0)=='0'){
        		return 0;
        }
        int tot=0,tot_1=1,tot_2=1;
        for(int i=1; i<s.length();i++){
        		if(s.charAt(i)!='0') tot+=tot_1;
        	    if(s.charAt(i-1)=='1' || (s.charAt(i-1)=='2'&& s.charAt(i)<'7')){
        	    		tot+=tot_2;
        	    }
        	    	tot_2=tot_1;
        	    	tot_1=tot;
        	    	tot=0;    		
        }
        return tot_1;
    }
    // my own kick-ass solution
    static HashMap<String, Integer> numDecodingsCache=new HashMap<String, Integer>();
    public static int numDecodings2(String s){
    		if(numDecodingsCache.containsKey(s)) return numDecodingsCache.get(s);
    		int tot=0;
    		if(s.length()>0 && (int)s.charAt(0)!='0') tot+=(s.length()==1)?1:numDecodings2(s.substring(1));
    		if(s.length()>1 && ((int)s.charAt(0)=='1' || ((int)s.charAt(0)=='2'&&((int)s.charAt(1)>='0'&&(int)s.charAt(1)<'7')))) tot+=(s.length()==2)?1:numDecodings2(s.substring(2));
    		numDecodingsCache.put(s, tot);
    		return tot;
    }
    
    
    /*Reverse integer from leetcode*/
    /*Reverse digits of an integer.*/
    public static int reverse(int x) {
    		long ret=0;
    		while(x!=0){
	    		ret*=10;
	    		ret+=x%10;
	    		x/=10;
    		}
    		if(ret>Integer.MAX_VALUE || ret<Integer.MIN_VALUE) return 0;
    		return (int)ret;
    }
    public static int reverse2(int x){
		boolean sign=true;
		if(x>=0) sign=false;
		StringBuilder sb=new StringBuilder();
		String m=x+"";
		sb.append(sign?m.substring(1):m);
		sb.reverse();
		long res=Long.parseLong(sb.toString());
		if(sign) res=-1*res;
		if(res>Integer.MAX_VALUE || res<Integer.MIN_VALUE) return 0;
		return (int) res;
    }
    public static void reverse_test() {
    		System.out.println(reverse(-123));
    }
    
    
    
    /* from leetcode*/
    /*There are two sorted arrays A and B of size m and n respectively. Find the median of the two sorted arrays. The overall run time complexity should be O(log (m+n)).*/
    public static double findMedianSortedArrays(int A[], int B[]) {
        int[] merged= merge_sorted(A,B);
        int leng = merged.length;
        if(leng%2==1){
        		 return merged[leng/2];
        }
        return (double)(merged[leng/2]+merged[leng/2+1])/2;
    }
    /*merge two sorted arrays.*/
    public static int[] merge_sorted(int[] a, int[] b) {
        int[] answer = new int[a.length + b.length];
        int i = 0, j = 0, k = 0;
        while (i < a.length && j < b.length)
        {
            if (a[i] < b[j])       
                answer[k++] = a[i++];

            else        
                answer[k++] = b[j++];               
        }
        while (i < a.length)  
            answer[k++] = a[i++];
        while (j < b.length)    
            answer[k++] = b[j++];
        return answer;
    }
    // Solution 2: O(log(min(n, m))) solution from  https://oj.leetcode.com/discuss/11174/share-my-iterative-solution-with-o-log-min-n-m
    // The hardest part of this type of questions is to take into account all edge cases.
    public static double findMedianSortedArrays2(int A[], int B[]) {
	    int n = A.length;
	    int m = B.length;
	    // the following call is to make sure len(A) <= len(B).
	    // yes, it calls itself, but at most once, shouldn't be
	    // consider a recursive solution
	    if (n > m)
	        return findMedianSortedArrays(B, A);
	    // now, do binary search
	    int k = (n + m - 1) / 2;
	    int l = 0, r = Math.min(k, n); // r is n, NOT n-1, this is important!!
	    while (l < r) {
	        int midA = (l + r) / 2;
	        int midB = k - midA;
	        if (A[midA] < B[midB])
	            l = midA + 1;
	        else
	            r = midA;
	    }
	    // after binary search, we almost get the median because it must be between
	    // these 4 numbers: A[l-1], A[l], B[k-l], and B[k-l+1] 
	    // if (n+m) is odd, the median is the larger one between A[l-1] and B[k-l].
	    // and there are some corner cases we need to take care of.
	    int a = Math.max(l > 0 ? A[l - 1] : Integer.MIN_VALUE, k - l >= 0 ? B[k - l] : Integer.MIN_VALUE);
	    if (((n + m) & 1) == 1)
	        return (double) a;
	    // if (n+m) is even, the median can be calculated by 
	    //      median = (max(A[l-1], B[k-l]) + min(A[l], B[k-l+1]) / 2.0
	    // also, there are some corner cases to take care of.
	    int b = Math.min(l < n ? A[l] : Integer.MAX_VALUE, k - l + 1 < m ? B[k - l + 1] : Integer.MAX_VALUE);
	    return (a + b) / 2.0;
    }

    
    
    /*Merge Intervals from leetcode*/
    /*c++ version is available*/
    /*Given a collection of intervals, merge all overlapping intervals.*/
    public static List<Interval> merge(List<Interval> intervals) {
        List<Interval> res=new ArrayList<Interval>();
        if(intervals.size()==0)
            return res;
        Collections.sort(intervals,new Comparator<Interval>(){
            @Override
            public int compare(Interval i1, Interval i2){
                return i1.start-i2.start;
            }
        });
        Interval newinterval=intervals.get(0);
        for(int j=1;j<intervals.size();j++){
            Interval i=intervals.get(j);
            if(i.end<newinterval.start){
                res.add(i);
            }else if(i.start>newinterval.end){
                res.add(newinterval);
                newinterval=i;
            }else{
                newinterval.start=Math.min(i.start,newinterval.start);
                newinterval.end=Math.max(i.end,newinterval.end);
            }
        }
        res.add(newinterval);
        return res;
    }
    
    
    
    /*Convert Sorted List to Binary Search Tree from leetcode*/
    /*Given a singly linked list where elements are sorted in ascending order, convert it to a height balanced BST.*/
    // my top-down method
    public TreeNode sortedListToBST(ListNode head) {
        List<Integer> list=new ArrayList<Integer>();
        while(head!=null){
            list.add(head.val);
            head=head.next;
        }
        return sortedListToBST(list, 0, list.size()-1);
    }
    public TreeNode sortedListToBST(List<Integer> list, int start, int end){
        if(start>end) return null;
        int mid=(start+end)/2;
        TreeNode head=new TreeNode(list.get(mid).intValue());
        if(start+1!=end) head.left=sortedListToBST(list,start,mid-1);
        head.right=sortedListToBST(list,mid+1,end);
        return head;
    }
    // a bottom-up method from leetcode
    /*http://leetcode.com/2010/11/convert-sorted-list-to-balanced-binary.html*/
    
    
    /*Add Two Numbers to Binary Search Tree from leetcode*/
    /*You are given two linked lists representing two non-negative numbers. The digits are stored in reverse order and each of their nodes contain a single digit. 
      Add the two numbers and return it as a linked list.*/
    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
    		ListNode ptr = new ListNode(0);
    		ListNode head = ptr;
    		boolean ten = false;
    		boolean check = false;
    		int sum=0;
    		while(true){
    			if(l1!=null){
    				sum+=l1.val;
        			l1 = l1.next;
        			check=true;
    			}
    			if(l2!=null){
    				sum+=l2.val;
        			l2 = l2.next;
        			check=true;
    			}
    			if(ten){
    				sum++;
        			ten=false;
        			check=true;
    			}
    			if(sum>9){
    				sum -=10;
    				ten=true;
    			} 
    			if(!check){
    				break;
    			}
    			ptr.next = new ListNode(sum);
    			ptr=ptr.next;
    			check=false;
    			sum=0;
    		}    
    		head = head.next;
    		return head;
    }
    public static void addTwoNumbers_test(){
    		ListNode l1 = new ListNode(2);
    		l1.next=new ListNode(4);
    		l1.next.next=new ListNode(3);
    		ListNode l2 = new ListNode(5);
    		l2.next=new ListNode(6);
    		l2.next.next=new ListNode(4);
    		ListNode n = addTwoNumbers(l1,l2);
    		for(; n!=null; n=n.next){
    			System.out.print(n.val+"	");
    		}
    }
    
    
    /*Minimum Path Sum from leetcode*/
    /*Given a m x n grid filled with non-negative numbers, find a path from top left to bottom right which minimizes the sum of all numbers along its path;  
       You can only move either down or right at any point in time.*/
    public static int minPathSum(int[][] grid) {
    		int r = grid.length-1;
    		int c = grid[0].length-1;
        Hashtable<String, Integer> cache = new Hashtable<String, Integer>();
        return minPathSum_helper(grid,cache, r, c);
    }
    public static int minPathSum_helper(int[][] grid, Hashtable<String, Integer> cache, int r, int c){
    		if(cache.containsKey(r+" "+c)){
    			return cache.get(r+" "+c);
    		}
    		if(r<0 || c<0){
    			return Integer.MAX_VALUE;
    		}
    		if(r==0 && c==0){
    			return grid[0][0];
    		}
    		int result;
    		result=Math.min(minPathSum_helper(grid,cache,r-1,c),minPathSum_helper(grid,cache,r,c-1)) + grid[r][c];
    		cache.put(r+" "+c, result);
    		return result;
    }
    public static void minPathSum_test(){
    		int[][] grid = {{5,1},{2,3}};
    		System.out.println(minPathSum(grid));
    }
    
    
    /*Valid Parentheses from leetcode*/
    /*Given a string containing just the characters '(', ')', '{', '}', '[' and ']', determine if the input string is valid.
	   The brackets must close in the correct order, "()" and "()[]{}" are all valid but "(]" and "([)]" are not.*/
    public static boolean isValid(String s) {
        char[] mychar = s.toCharArray();
        Stack<Character> mys  = new Stack<Character>();
        for(char um :mychar){
        		switch(um){
        		case '[': 
       			 mys.push(']');
       			 break;
        		case '(': 
       			 mys.push(')');
       			 break;
        		case '{':
        			 mys.push('}');
        			 break;
        		case ']': case ')': case '}':
     			 if(mys.empty() || mys.pop()!=um){
        				 return false;
        			 }
     			 break;
        		default:

        			 return false;
        		}
        }
        return mys.empty();
    }
    public static void isValid_test(){
    		System.out.println(isValid("()"));
    }
    
    
    
    /*Insertion Sort List from leetcode*/
    public static ListNode insertionSortList(ListNode head) {
        ListNode virtue=new ListNode(0);
        while(head!=null){
            ListNode ptr=virtue;
            while(ptr.next!=null&&ptr.next.val<=head.val)
                ptr=ptr.next;
            ListNode tmpptr=ptr.next;
            ptr.next=head;
            head=head.next;
            ptr=ptr.next;
            ptr.next=tmpptr;
        }
        return virtue.next;
    }
    
    
    
    /*Minimum Window Substring from leetcode*/
    /*Given a string S and a string T, find the minimum window in S which will contain all the characters in T in complexity O(n).*/
    public static String minWindow(String S, String T) {
    		int map[]=new int[128];
    		boolean mapr[]=new boolean[128];
    		java.util.Arrays.fill(mapr,false);
    		int count = T.length();
    		for(int i=0; i<count; i++){
    			map[T.charAt(i)]++;
    			mapr[T.charAt(i)]=true;
    		}
    		int i=0,j=0;
    		int leng=Integer.MAX_VALUE;
    		int index=0;
    		while(count==0 || j<S.length()){
    			if(count!=0){
    				if(mapr[S.charAt(i)]){
    					if(--map[S.charAt(i)]>=0){
    						count--;
    					}
    				}
    				i++;
    			}else{
    				if(mapr[S.charAt(j)]){
    					if(++map[S.charAt(j)]>0){
    						count++;
    						if(leng>i-j){
    							index=j;
    							leng=i-j;
    						}
    					}				
    				}
    				j++;
    			}
    		}
    		if(leng==Integer.MAX_VALUE){
    			return "";
    		}
        return S.substring(index, index+leng);
    }
    public static void minWindow_test() {
    		System.out.println(minWindow("acdbbabc","abc"));
    		System.out.println(minWindow("aa","aa"));
    }
    
    
    
    

    /*Letter Combinations of a Phone Number */
    /*Given a digit string, return all possible letter combinations that the number could represent. A mapping of digit to letters (just like on the telephone buttons) is given below.*/
    /*Although the above answer is in lexicographical order, your answer could be in any order you want.*/
    public static List<String> letterCombinations(String digits) {
        return letterCombinations_helper(digits,0);
    }
    public static List<String> letterCombinations_helper(String digits, int pos) {
        List<String> result = new ArrayList<String>();
        if(pos==digits.length()){
            result.add("");
        		return result;
        }
        char[] map = letterCombinations_map(digits,pos);
        for(char c:map){
        		List<String> temp = letterCombinations_helper(digits,pos+1);
        		for(String s:temp){
        			result.add(c+s);
        		}
        }
        return result;
    }
    public static char[] letterCombinations_map(String digits, int pos) {
        switch(digits.charAt(pos)){
        case '2':
        		return new char[]{'a','b','c'};
        case '3':
    			return new char[]{'d','e','f'};
        case '4':
    			return new char[]{'g','h','i'};
        case '5':
        		return new char[]{'j','k','l'};
        case '6':
    			return new char[]{'m','n','o'};
        case '7':
    			return new char[]{'p','q','r','s'};
        case '8':
    			return new char[]{'t','u','v'};
        default:
    			return new char[]{'w','x','y','z'};
        }
    }
    
    
    
    /*Search Insert Position from leetcode*/
    /*Given a sorted array and a target value, return the index if the target is found. If not, return the index where it would be if it were inserted in order. You may assume no duplicates in the array.*/
    /*recursion*/
    public static int search(int A[], int start, int end, int target) {
        if (start > end) return start;
        int mid = (start + end) / 2;
        if (A[mid] == target) return mid;
        else if (A[mid] > target) return search(A, start, mid - 1, target);
        else return search(A, mid + 1, end, target);
    }
   public static int searchInsert(int A[], int target) {
        return search(A, 0, A.length-1, target);
    }
    /*Iterative*/
    public static int searchInsert2(int[] A, int target) {
        int right=A.length-1, left=0;
        while(left<=right){
            int mid=(left+right)/2;
            if(A[mid]==target) return mid;
            if(A[mid]>target) right=mid-1;
            else left=mid+1;
        }
        return left;
    }
    
    
    
    
    /*Generate Parentheses  from leetcode*/
    /*Given n pairs of parentheses, write a function to generate all combinations of well-formed parentheses.*/
    public static List<String> generateParenthesis(int n) {
        char[] mychar = new char[2*n];
        ArrayList<String> mylist = new ArrayList<String>();
        generateParenthesis_helper(mylist,mychar,n,n,0);  
        return mylist;
    }
    public static void generateParenthesis_helper(ArrayList<String> mylist, char[] list, int left, int right, int count){
    		if(right==0 && left==0){
    			mylist.add(String.copyValueOf(list));
    		}else{
    			if(left!=0){
    				list[count]='(';
    				generateParenthesis_helper(mylist,list,left-1,right,count+1);
    			}
    			if(right>left){
    				list[count]=')';
    				generateParenthesis_helper(mylist,list,left,right-1,count+1);
    			}
    		}
    }

    
    
    /*Reverse Nodes in k-Group from leetcode*/
    /*Given a linked list, reverse the nodes of a linked list k at a time and return its modified list. If the number of nodes is not a multiple of k then left-out nodes in the end should remain as it is.
     * You may not alter the values in the nodes, only nodes itself may be changed. Only constant memory is allowed.*/
    /*O(n) time complexity and constant space complexity.*/
    // Catch: partition the list into partition sector.
    public static ListNode reverseKGroup(ListNode head, int k) {
    		ListNode dummy = new ListNode(0);
    		dummy.next=head;
    		ListNode pre=dummy, cur=head, pilot=cur;;
    		while(cur!=null){
    			int count=k;
    			while(count>0 && pilot!=null){
    				count--;
    				pilot=pilot.next;
    			}
    			if(count>0){
    				break;
    			}
    			while(cur.next!=pilot){
    				ListNode t=cur.next.next;
    				cur.next.next=pre.next;
    				pre.next=cur.next;
    				cur.next=t;
    			}
    			pre=cur;
    			cur=pilot;
    		}
    		return dummy.next;
    }
    
    
    /*Remove Duplicates from Sorted List II from leetcode*/
    /*Given a sorted linked list, delete all nodes that have duplicate numbers, leaving only distinct numbers from the original list.*/
    public static ListNode deleteDuplicates(ListNode head) {
        ListNode virtue=new ListNode(0),ptr=virtue;
        virtue.next=head;
        while(ptr.next!=null&&ptr.next.next!=null){
            ListNode tmpptr=ptr.next;
            while(tmpptr.next!=null&&tmpptr.val==tmpptr.next.val){
                tmpptr=tmpptr.next;
            }
            if(tmpptr==ptr.next){
                ptr=tmpptr;
            }else{
                ptr.next=tmpptr.next; 
            }
        }
        return virtue.next;
    }
    
    
    /*Reverse Words in a String */
    /*Given an input string, reverse the string word by word.*/
    /*my solution, two passes*/
    public static String reverseWords(String s) {
    		ArrayList<String> words=new ArrayList<String>();
    		int leng=s.length(), k=0;
    		while(k<leng){;
    			while(k<leng && s.charAt(k)==' '){
    				k++;
    			}
    			if(k>=leng){
    				break;
    			}
    			int i=k++;
    			while(k<leng && s.charAt(k)!=' '){
    				k++;
    			}
    			int j=k;
    			words.add(s.substring(i, j));
    		}
        String result="";
        if(words.size()==0){
        		return result;
        }
        for(int i=words.size()-1;i>0;i--){
        		result+=words.get(i)+" ";

        }
        result+=words.get(0);
        return result;
    }
    /*leetcode solution, one pass*/
    public static String reverseWords2(String s) {
    	   StringBuilder reversed = new StringBuilder();
    	   int j = s.length();
    	   for (int i = s.length() - 1; i >= 0; i--) {
    	      if (s.charAt(i) == ' ') {
    	         j = i;
    	      } else if (i == 0 || s.charAt(i - 1) == ' ') {
    	         if (reversed.length() != 0) {
    	            reversed.append(' ');
    	         }
    	         reversed.append(s.substring(i, j));
    	      }
    	   }
    	   return reversed.toString();
    	}
    // my solution 2
	public static String reverseWords3(String s){
	   	 s=s.trim();
	   	 String[] res = s.split("\\s+");
	   	 if(res.length<=1) return s;
	   	 StringBuilder sb=new StringBuilder();
	   	 for(String ss:res){
	   		 sb.insert(0,ss+" ");
	   	 }
	   	 sb.deleteCharAt(sb.length()-1);
	   	 return sb.toString();
    }   
    public static void reverseWords_test(){
     // System.out.println(reverseWords3("the sky is blue"));
    		System.out.println(reverseWords3(""));
    }

    /*Evaluate Reverse Polish Notation from leetcode*/
    /*Evaluate the value of an arithmetic expression in Reverse Polish Notation. Valid operators are +, -, *, /. Each operand may be an integer or another expression.*/
    public static int evalRPN(String[] tokens) {
    		Stack<Integer> sk=new Stack<Integer>();
        for(String s: tokens){
        		try{
        			sk.push(Integer.parseInt(s));
        		}catch(Exception e){
        			int a=sk.pop();
        			int b=sk.pop();
        			switch(s.charAt(0)){
	        			case '+': b+=a; break;
	        			case '-': b-=a; break;
	        			case '*': b*=a; break;
	        			default: b/=a;break;
        			}
        			sk.push(b);
        		}
        }
        return sk.pop();
    }
    // solution 2
    public int evalRPN2(String[] tokens)
    {
	   	 Stack<Integer> sk = new Stack<Integer>();
	   	 for(String s : tokens)
	   	 {
	   		 char zero=s.charAt(0);
	   		 if(s.length()==1 && (zero=='+'||zero=='-'||zero=='*'||zero=='/')){
	   			 int k2=sk.pop();
	   			 int k1=sk.pop();
	   			 if(zero=='+') sk.push(k1+k2);
	   			 else if(zero=='-') sk.push(k1-k2);
	   			 else if(zero=='*') sk.push(k1*k2);
	   			 else if(zero=='/') sk.push(k1/k2);
	   		 }else{
	   			 sk.push(Integer.parseInt(s));
	   		 }
	   	 }
	   	 return sk.pop();
    }
    public static void evalRPN_test(){
    		String[] tks={"2", "1", "+", "-3", "*"};
    		System.out.println(evalRPN(tks));
    }

    
    /*Length of Last Word */
    /*Given a string s consists of upper/lower-case alphabets and empty space characters ' ', return the length of last word in the string. If the last word does not exist, return 0.
     * Note: A word is defined as a character sequence consists of non-space characters only.*/
    public static int lengthOfLastWord(String s) {
    		int j=s.length();
        for(int i=s.length()-1;i>=0;i--){
        		if(s.charAt(i)==' '){
        			j=i;
        		}else if(i==0 || s.charAt(i-1)==' '){
        			return s.substring(i, j).length();
        		}
        }
        return 0;
    }
    
    
    
    
    /*Text Justification*/
    /*Given an array of words and a length L, format the text such that each line has exactly L characters and is fully (left and right) justified.
	  You should pack your words in a greedy approach; that is, pack as many words as you can in each line. Pad extra spaces ' ' when necessary so that each line has exactly L characters.
	  Extra spaces between words should be distributed as evenly as possible. If the number of spaces on a line do not divide evenly between words, the empty slots on the left will be assigned more spaces than the slots on the right.
	  For the last line of text, it should be left justified and no extra space is inserted between words.*/
    public static List<String> fullJustify(String[] words, int L) {
		List<String> mylist = new ArrayList<String>();
		int leng=0, start=0;
		int i=0;
    while(i<=words.length){
    		if(i==words.length || leng+words[i].length()+i-start>L){
    			int space=L-leng;
    			StringBuffer list=new StringBuffer();
    			if(i==words.length || i-start-1==0){
    				for(int j=start;j<i;j++){
        				list.append(words[j]);	
        				if(j<i-1){
        					list.append(" ");
        				}else{
        					for(int k=0;k<L-(i-start-1)-leng;k++){
        						list.append(" ");
        					}
        				}
        			}
    			}else{			
        			for(int j=start;j<i;j++){
        				list.append(words[j]);
        				if(j!=i-1){
        				    int spaceNum= space/(i-start-1);
        				    int extraspaceNum = ((j-start)<space%(i-start-1))?1:0;
	        				for(int k=0;k<spaceNum+extraspaceNum;k++){
	        					list.append(' ');
	        				} 	
        				}		
        			}
    			}
    			mylist.add(list.toString());
    			start=i;
    			leng=0;
    		}
    		if(i!=words.length){
    			leng+=words[i].length();
			}
    		i++;
    }
    return mylist;
    }
    public static void fullJustify_test() {
    		String[] words={"Here","is","an","example","of","text","justification."};
    		List<String> mylist = fullJustify(words,16);
    		for(String s: mylist){
    			System.out.println(s);
    		}
    }
    
    /*Minimum Depth of Binary Tree*/
    /*Given a binary tree, find its minimum depth. The minimum depth is the number of nodes along the shortest path from the root node down to the nearest leaf node.*/
    /*DFS*/
    public static int minDepth(TreeNode root) {
    		if(root==null) return 0;
    		int a = minDepth(root.left);
    		int b = minDepth(root.right);
    		if(a==0 || b==0) return a+b+1;
    		return Math.min(a, b)+1;
    }
    /*BFS*/
    public static int minDepth2(TreeNode root){
    		ArrayList<TreeNode> current= new ArrayList<TreeNode>();
    		if(root==null){
    			return 0;
    		}
    		current.add(root);
    		int level=1;
    		outerloop:
    		while(current.size()!=0){
    			ArrayList<TreeNode> parents=current;
    			current=new ArrayList<TreeNode>();
    			for(TreeNode parent: parents){
    				if(parent.left==null && parent.right==null){
    					break outerloop;
    				}
    				if(parent.left!=null){
    					current.add(parent.left);
    				}
    				if(parent.right!=null){
    					current.add(parent.right);
    				}
    			}
    			level++;
    		}
    		return level;
    }

    
    
    /*Unique Binary Search Trees */
    /*Given n, how many structurally unique BST's (binary search trees) that store values 1...n?*/
    /*method 1*/
    // Catch: fill out the cache.
    public static int numTrees(int n) {
        int[] cache=new int[n+1];
        cache[0]=1;
        cache[1]=1;
        for(int i=1; i<=n; i++){
             int sum=0;
             for(int j=1;j<=i;j++)
                 sum+=cache[j-1]*cache[i-j];
             cache[i]=sum;
        }
        return cache[n];
    }  
    /*Unique Binary Search Trees II*/
    /*Given n, generate all structurally unique BST's (binary search trees) that store values 1...n.*/
    // Catch: this solution is designed s.t the calculated object only depends on its previous objects. This is why using array as cache & no recursiion (of the main function) is okay.
    public static List<TreeNode> generateTrees(int n) {
        @SuppressWarnings("unchecked")
		List<TreeNode>[] result = new List[n+1];
        result[0] = new ArrayList<TreeNode>();
        result[0].add(null);
        for(int len = 1; len <= n; len++){
            result[len] = new ArrayList<TreeNode>();
            for(int j=0; j<len; j++){
                for(TreeNode nodeL : result[j]){
                    for(TreeNode nodeR : result[len-j-1]){
                        TreeNode node = new TreeNode(j+1);
                        node.left = nodeL;
                        node.right = clone(nodeR, j+1);
                        result[len].add(node);
                    }
                }
            }
        }
        return result[n];
    }
    public static TreeNode clone(TreeNode n, int offset){
        if(n == null)
            return null;
        TreeNode node = new TreeNode(n.val + offset);
        node.left = clone(n.left, offset);
        node.right = clone(n.right, offset);
        return node;
    }
    
    
    
    /*Insert Interval*/
    /*Given a set of non-overlapping intervals, insert a new interval into the intervals (merge if necessary). You may assume that the intervals were initially sorted according to their start times.*/
    /*in-place; solution by myself*/
    public static List<Interval> insert(List<Interval> intervals, Interval newInterval) {
        List<Interval> res=new ArrayList<Interval>();
        for(Interval i:intervals){
            if(i.end<newInterval.start){
                res.add(i);
            }else if(i.start>newInterval.end){
                res.add(newInterval);
                newInterval=i;
            }else{
                newInterval.start=Math.min(newInterval.start,i.start);
                newInterval.end=Math.max(newInterval.end,i.end);
            }
        }
        res.add(newInterval);
        return res;
    }
    

    /*Candy from leetcode*/
    /*There are N children standing in a line. Each child is assigned a rating value. You are giving candies to these children subjected to the following requirements:
	  Each child must have at least one candy. Children with a higher rating get more candies than their neighbors. What is the minimum candies you must give?*/
    //my own solution: the idea of downward hill partition
    public static int candy(int[] ratings) {
        int res=(ratings.length!=0)?1:0;
        int start=1,len=1;
        for(int i=1;i<ratings.length;i++){
            if(ratings[i]<ratings[i-1]){
                len++;
                res+=(len>start)?len:len-1;
            }else if(ratings[i]==ratings[i-1]){
                start=1;
                len=1;
                res++;
            }else{
                if(len==1){
                    start++;
                }else{
                    len=1;
                    start=2;
                }
                res+=start;
            }
            
        }
        return res;
    }
    public static void candy_test(){
    		int[] ratings={2,2,1};
    		System.out.println(candy(ratings));
    }
    
    
    
    /*Repeated DNA Sequences*/
    /*All DNA is composed of a series of nucleotides abbreviated as A, C, G, and T, for example: "ACGAATTCCG". When studying DNA, it is sometimes useful to identify repeated sequences within the DNA.
     * Write a function to find all the 10-letter-long sequences (substrings) that occur more than once in a DNA molecule.
     * For example, given s = "AAAAACCCCCAAAAACCCCCCAAAAAGGGTTT", return: ["AAAAACCCCC", "CCCCCAAAAA"].*/
    // Catch: naive idea would be just storing strings into the cache, but it would take too much both time and space.
    public static List<String> findRepeatedDnaSequences(String s) {
        List<String> res=new ArrayList<String>();
        if(s.length()<11) return res;
        Map<Integer,Integer> map=new HashMap<Integer,Integer>();
        int t=0, i=0;
        while(i<9)
            t=(t<<3)|(s.charAt(i++)&7);
        while(i<s.length()){
            t=(t<<3&0x3fffffff)|(s.charAt(i++)&7);
            if(!map.containsKey(t))
                map.put(t,1);
            else if(map.get(t)==1){ 
                res.add(s.substring(i-10,i));
                map.put(t,2);
            }
        }
        return res;
    }
    
    
    
    
    /*Single Number II from leetcode*/
    /*Given an array of integers, every element appears three times except for one. Find that single one. Note:
	   Your algorithm should have a linear runtime complexity. Could you implement it without using extra memory?*/
    /*without using extra memory but takes time*/
    public static int singleNumber(int[] A) {
    		Arrays.sort(A);
    		int leng=A.length;
    		for(int i=0;i<leng;i+=3){
    			if(i+1==leng || A[i]!=A[i+1]){
    				return A[i];
    			}
    		}
    		return -1;
    }
    /*another general constant space solution which however takes time from leetcode discussion*/
    /*To solve this problem using only constant space, you have to rethink how the numbers are being represented in computers -- using bits.
	   If you sum the ith bit of all numbers and mod 3, it must be either 0 or 1 due to the constraint of this problem where each number must appear either three times or once. 
	   This will be the ith bit of that "single number". A straightforward implementation is to use an array of size 32 to keep track of the total count of ith bit.*/
    public static int singleNumber3(int[] A) {
        int count[]=new int[32];
        int result = 0;
        for (int i = 0; i < 32; i++) {
            for (int j = 0; j < A.length; j++) {
                if (((A[j] >> i) & 1) == 1) {
                    count[i]++;
                }
            }
            result |= ((count[i] % 3) << i);
        }
        return result;
    }    
    /*We can improve this based on the previous solution using three bitmask variables: 
     * ones as a bitmask to represent the ith bit had appeared once. 
     * twos as a bitmask to represent the ith bit had appeared twice.
     * threes as a bitmask to represent the ith bit had appeared three times.
     * When the ith bit had appeared for the third time, clear the ith bit of both ones and twos to 0. The final answer will be the value of ones.*/
    public static int singleNumber4(int[] A) {
        int ones = 0, twos = 0, threes = 0;
        for (int i = 0; i < A.length; i++) {
            twos |= ones & A[i];
            ones ^= A[i];
            threes = ones & twos;
            ones &= ~threes;
            twos &= ~threes;
        }
        return ones;
    }      
    /*further improvement; takes less time and uses zero space; solution from leetcode discussion*/
    public static int singleNumber2(int[] A) {
        if(A.length == 1) return A[0];
        // A[0] is correct to start; take care of processing A[1]
        A[0] ^= A[1];
        // Set A[1] to either 0 or itself.
        A[1] = (A[0]^A[1])&A[1];
        // Continue with algorithm as normal
        for(int i = 2; i < A.length; i++){
            A[1] |= A[0]&A[i];
            A[0] ^= A[i];
            A[2] = ~(A[0]&A[1]);
            A[0] &= A[2];
            A[1] &= A[2];
        }
        return A[0];
    }
    
    
    /*Remove Nth Node From End of List from leetcode*/
    /*Given a linked list, remove the nth node from the end of list and return its head.*/
    public static ListNode removeNthFromEnd(ListNode head, int n) {
    		if(n==0){
    			return head;
    		}
        ListNode runner = head;
        ListNode prehead=new ListNode(0);
        ListNode newprehead=prehead;
        prehead.next=head;
        int leng=0;
        while(runner!=null){
        		runner=runner.next;
        		leng++;
        }
        for(int i=0;i<leng-n;i++){
        		prehead=prehead.next;
        }
        prehead.next=prehead.next.next;
        return newprehead.next;
    }
    public static void removeNthFromEnd_test() {
    		ListNode head=new ListNode(1);
    		head.next=new ListNode(2);
    		head=removeNthFromEnd(head,1);
    		while(head!=null){
    			System.out.println(head.val);
    			head=head.next;
    		}
    }
    
    
    
    /*Climbing Stairs from leetcode*/
    /*You are climbing a stair case. It takes n steps to reach to the top. Each time you can either climb 1 or 2 steps. In how many distinct ways can you climb to the top?*/
    public int climbStairs(int n){
        int[] map=new int[n+1];
        map[0]=1;
        map[1]=1;
        for(int i=2;i<=n;i++)
            map[i]=map[i-1]+map[i-2];
        return map[n];
    }
    
    
    
    /*Longest Valid Parentheses from leetcode*/
    /*Given a string containing just the characters '(' and ')', find the length of the longest valid (well-formed) parentheses substring.
	  For "(()", the longest valid parentheses substring is "()", which has length = 2.
	  Another example is ")()())", where the longest valid parentheses substring is "()()", which has length = 4.*/
    /*DP method*/
    public static int longestValidParentheses(String s) {
        int leng=s.length();
        int max=0;
        int [] map = new int[leng+1];
        for(int i=2;i<leng+1;i++){
        		if(s.charAt(i-1)==')'){
        			if(s.charAt(i-2)=='('){
        				map[i]=map[i-2]+2;
        			}else if((i-2-map[i-1])>=0 && s.charAt((i-2-map[i-1]))=='('){
        				map[i]=map[i-1]+2+map[i-2-map[i-1]];
        			}
        			max=Math.max(map[i], max);
        		}
        }
		return max;
    }
    /*method using stack in one pass*/
    public static int longestValidParentheses2(String s) {
    		Stack<Integer> sk=new Stack<Integer>();
    		int leng=s.length();
    		int max=0;
    		for(int i=0;i<leng;i++){
    			if(s.charAt(i)=='('){
    				sk.push(i);
    			}else{
    			    if(!sk.isEmpty() && s.charAt(sk.peek())=='('){
    					sk.pop();
    					int cur=sk.isEmpty()?i+1:i-sk.peek();
    					max=Math.max(cur, max);
    				}else{
    					sk.push(i);
    				}
    			}
    		}
    		return max;
    }
    // same idea as above, but another to implement
    public int longestValidParentheses3(String s) {
        Stack<Integer> sk=new Stack<Integer>();
        int res=0, tap=-1;
        for(int i=0;i<s.length();i++){
            if(s.charAt(i)==')'&&!sk.isEmpty()&&sk.peek()>tap){
                sk.pop();
                int bot=Math.max((sk.isEmpty())?-2:sk.peek(),tap);
                res=Math.max(res,i-bot);
            }else if(s.charAt(i)=='('){
                sk.push(i);
            }else{
                tap=i;
            }
        }
        return res;
    }
    
    
    
    /*Clone Graph from leetcode*/
    /*Clone an undirected graph. Each node in the graph contains a label and a list of its neighbors.*/
    /*Nodes are labeled uniquely. We use # as a separator for each node, and , as a separator for node label and each neighbor of the node.
	  As an example, consider the serialized graph {0,1,2#1,2#2,2}. The graph has a total of three nodes, and therefore contains three parts as separated by #.
	  First node is labeled as 0. Connect node 0 to both nodes 1 and 2. Second node is labeled as 1. Connect node 1 to node 2. 
	  Third node is labeled as 2. Connect node 2 to node 2 (itself), thus forming a self-cycle.*/
    public static UndirectedGraphNode cloneGraph(UndirectedGraphNode node) {
        if (node == null) 
            return null;
        HashMap<Integer, UndirectedGraphNode> map = new HashMap<Integer, UndirectedGraphNode>();
        LinkedList<UndirectedGraphNode> queue = new LinkedList<UndirectedGraphNode>();
        queue.add(node);
        UndirectedGraphNode curNode = null;
        //FIFO
        while (!queue.isEmpty()) {
            curNode = queue.poll();
            if (!map.containsKey(curNode.label)) 
                map.put(curNode.label, new UndirectedGraphNode(curNode.label));         
            for (UndirectedGraphNode neighbor : curNode.neighbors) {
                if (!map.containsKey(neighbor.label)) {
                    queue.offer(neighbor);
                    map.put(neighbor.label, new UndirectedGraphNode(neighbor.label));       
                }
                map.get(curNode.label).neighbors.add(map.get(neighbor.label));
            }
        }  //end while    
        return map.get(node.label);
    }
    
    
    
    /*Single Number */
    /*Given an array of integers, every element appears twice except for one. Find that single one.*/
    public static int singleNumber7(int[] A) {
        // Continue with algorithm as normal
        int result=0;
        for(int i = 0; i < A.length; i++){
        		result ^= A[i];
        }
        return result;  
    }
    
    
    
    
    /*Binary Tree Level Order Traversal II */
    /*Given a binary tree, return the bottom-up level order traversal of its nodes' values. (ie, from left to right, level by level from leaf to root).*/
    /*iterative; breadth first*/
    public static List<List<Integer>> levelOrderBottom(TreeNode root) {
		List<List<Integer>> result=new ArrayList<List<Integer>>();
    		if(root==null){
            return result;
        }
    		Queue<TreeNode> cur =  new LinkedList<TreeNode>();
    		cur.offer(root);
    		while(cur.size()!=0){
    			Queue<TreeNode> parents = cur;
    			cur = new LinkedList<TreeNode>();
    			List<Integer> lvl = new ArrayList<Integer>();
    			for(TreeNode parent: parents){
    				lvl.add(parent.val);
    				if(parent.left!=null) cur.offer(parent.left);
    				if(parent.right!=null) cur.offer(parent.right);
    			}
    			result.add(0, lvl);
    		}
    		return result;
    }
    /*recursive; depth first*/
    public static List<List<Integer>> levelOrderBottom2(TreeNode root) {
    		List<List<Integer>> result=new ArrayList<List<Integer>>();
    		levelOrderBottom2_helper(root,0,result);
    		return result;
    }
    public static void levelOrderBottom2_helper(TreeNode root, int lvl, List<List<Integer>> lists){
    		if(root==null){
    			return;
    		}
    		int totlvl = lists.size();
    		if(lvl>=totlvl){
			List<Integer> list= new ArrayList<Integer>();
			list.add(root.val);
			lists.add(0, list);
    		}else{
    			lists.get(totlvl-lvl-1).add(root.val);
    		}
    		levelOrderBottom2_helper(root.left,lvl+1,lists);
    		levelOrderBottom2_helper(root.right,lvl+1,lists);
    }
    
    
    
    /*Sort List*/
    /*Sort a linked list in O(n log n) time using constant space complexity.*/
    /*mergesort*/
    public static ListNode sortList(ListNode head) {
        int leng=0;
        ListNode ptr=head;
        while(ptr!=null){
        		leng++;
        		ptr=ptr.next;
        }
        int gpsize=1;
        ListNode virtueNode = new ListNode(0);
        virtueNode.next=head;
        while(gpsize<leng){
            ListNode pre=virtueNode, post, ptr2;
        		int a,b, tot=0, lena,lenb;
        		while(tot!=leng){
        			ptr=pre.next;
        			ptr2=ptr;
	        		a=0;
	        		b=0;
	        		lena=0;
	        		lenb=0;
	        		for(lena=0;ptr2!=null&&lena<gpsize;lena++) ptr2=ptr2.next;
	        		post=ptr2;
	        		for(lenb=0;post!=null&&lenb<gpsize;lenb++) post=post.next;
	        		if(lenb!=0){
		        		while(a!=gpsize && b!=lenb){
		    				if(ptr.val>=ptr2.val){
		    					pre.next=ptr2;
		    					ptr2=ptr2.next;
		    					b++;
		    				}else{
		    					pre.next=ptr;
		    					ptr=ptr.next;
		    					a++;
		    				}
						pre=pre.next;
		        		}
		        		if(a==gpsize){
		        			pre.next=ptr2;
		        			while(b!=lenb){
		        				pre=pre.next;
		        				b++;
		        			}
		        		}else{
		        			pre.next=ptr;
		        			while(a!=gpsize){
		        				pre=pre.next;
		        				a++;
		        			}
		        		}
		        		pre.next=post;
	        		}
	        		tot+=lenb+lena;
        		}
        		gpsize<<=1;
        }
        return virtueNode.next;
    }
    public static void sortList_test() {
    		ListNode head=new ListNode(5), ptr=head;
    		ptr.next=new ListNode(3);
    		ptr=ptr.next;
    		ptr.next=new ListNode(7);
    		ptr=ptr.next;
    		ptr.next=new ListNode(2);
    		ptr=ptr.next;
    		ptr.next=new ListNode(6);
    		ptr=ptr.next;
    		ptr.next=new ListNode(4);
    		ptr=ptr.next;
    		ptr.next=new ListNode(1);
    		ptr=ptr.next;
    		ptr.next=new ListNode(8);  		
    		ptr=sortList(head);
    		while(ptr!=null){
   			System.out.println(ptr.val);
   			ptr=ptr.next;
    		}
    }
    
    
    
    /*Two Sum*/
    /* Given an array of integers, find two numbers such that they add up to a specific target number. 
     * The function twoSum should return indices of the two numbers such that they add up to the target, where index1 must be less than index2. 
     * Please note that your returned answers (both index1 and index2) are not zero-based.
     * You may assume that each input would have exactly one solution.*/
    /*my solution*/
    public static int[] twoSum(int[] numbers, int target) {
    		int[] numcopy=numbers.clone();
        Arrays.sort(numbers);
        int [] res=new int[2];
        int s=0,e=numbers.length-1;
        while(s<e){
        		int left = numbers[s];
        		int right = numbers[e];
        		if(left+right>target){
        			e--;
        		}else if(left+right<target){
        			s++;
        		}else{
        			break;
        		}
        }
        int i=0,j=0;
        for(;j<2;i++){
        		if(numcopy[i]==numbers[e] || numcopy[i]==numbers[s]){
        			res[j++]=i+1;
        		}       			
        }
        return res;
    }
    /*leetcode solution*/
    public static int[] twoSum2(int[] numbers, int target) {
    		Map<Integer,Integer> p = new HashMap<Integer, Integer>();
    		for(int i=0; i<numbers.length;i++){
    			if(p.containsKey(target-numbers[i])){
    				return new int[]{p.get(target-numbers[i])+1,i+1};
    			}
    			p.put(numbers[i], i);
    		}
    		return null;
    }
    public static void twoSum_test(){
    		int[] numbers={3,2,4};
    		numbers=twoSum2(numbers,6);
    		System.out.println(numbers[0]+"	"+numbers[1]);
    }
    
    
    
    /*Merge Two Sorted Lists*/
    /*Merge two sorted linked lists and return it as a new list. The new list should be made by splicing together the nodes of the first two lists.*/
    public static ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode virtueNode=new ListNode(0);
        ListNode pre=virtueNode;
        while(l1!=null && l2!=null){
        		if(l1.val>l2.val){
        			pre.next=l2;
        			l2=l2.next;
        		}else{
        			pre.next=l1;
        			l1=l1.next;
        		}
        		pre=pre.next;
        }
        if(l1==null){
        		pre.next=l2;
        }else{
        		pre.next=l1;
        }
        return virtueNode.next;
    }
    
    
    /*Scramble String*/
    /*Given a string s1, we may represent it as a binary tree by partitioning it to two non-empty substrings recursively.*/
    /*To scramble the string, we may choose any non-leaf node and swap its two children. Given two strings s1 and s2 of the same length, determine if s2 is a scrambled string of s1.*/
    /*recursive call*/
    /*https://oj.leetcode.com/discuss/3632/any-better-solution from leetcode: an optimization of this solution.*/
    public static boolean isScramble(String s1, String s2) {
        if(s1.equals(s2)) return true;
        if(s1.length()!=s2.length()) return false;
        char[] c1 = s1.toCharArray();
        char[] c2 = s2.toCharArray();
        Arrays.sort(c1);
        Arrays.sort(c2);
        if(!Arrays.equals(c1, c2)) return false;
        int len=s1.length();
        for(int i=1;i<len;i++){
            if((isScramble(s1.substring(0,i),s2.substring(0,i))&&isScramble(s1.substring(i),s2.substring(i)))
            ||(isScramble(s1.substring(0,i),s2.substring(len-i))&&isScramble(s1.substring(i),s2.substring(0,len-i))))
                return true;
        }
        return false;
    }
    
    
    
    /*Validate Binary Search Tree */
    /*Given a binary tree, determine if it is a valid binary search tree (BST).*/
    public static boolean isValidBST(TreeNode root) {
        return isValidBST_helper(root,Integer.MIN_VALUE,Integer.MAX_VALUE,true,true);
    }
    public static boolean isValidBST_helper(TreeNode root, int min, int max, boolean big, boolean small) {
        if(root==null){
        		return true;
        }
        int curval=root.val;
        if(curval<min || curval>max){
        			return false;
        }
        if(curval==min || curval==max){
	    		if(curval==Integer.MAX_VALUE && big){
	    			big=false;
	    		}else if(curval==Integer.MIN_VALUE && small){
	    			small=false;
	    		}else{
	    			return false;
    			}
        }
        return isValidBST_helper(root.left,min,curval,big,small) && isValidBST_helper(root.right,curval,max,big,small);
    }
    
    
    /*Longest Substring Without Repeating Characters */
    /*Given a string, find the length of the longest substring without repeating characters. For example, the longest substring without repeating letters for "abcabcbb" is "abc", which the length is 3. 
     * For "bbbbb" the longest substring is "b", with the length of 1.*/
    public static int lengthOfLongestSubstring(String s) {
        int[] map=new int[128];
        Arrays.fill(map,-1);
        int max=0, startind=-1;
        for(int i=0;i<s.length();i++){
            if(map[s.charAt(i)]!=-1)
                startind=Math.max(startind,map[s.charAt(i)]);
            max=Math.max(max,i-startind);    
            map[s.charAt(i)]=i;
        }
        return max;
    }
    
    
    
    /*3Sum Closest*/
    /*Given an array S of n integers, find three integers in S such that the sum is closest to a given number target. 
     * Return the sum of the three integers. You may assume that each input would have exactly one solution.*/
    public static int threeSumClosest(int[] num, int target) {
        Arrays.sort(num);
        int leng=num.length, mindiff=Integer.MAX_VALUE;
        for(int i=0;i<leng-2;i++){
        		int j=i+1,k=leng-1,remain=target-num[i];
        		while(j<k){
        			int diff=remain-num[j]-num[k];
        			if(Math.abs(diff)<Math.abs(mindiff)){
        				mindiff=diff;
        			}
        			if(diff<0){
        				k--;
        			}else if(diff>0){
        				j++;
        			}else{
        				return target;
        			}
        		}
        }
        return target-mindiff;
    }
    
    
    /*Max Points on a Line */
    /*Given n points on a 2D plane, find the maximum number of points that lie on the same straight line.*/
    /*My own solution---------------------------*/
    static int max;
    public static int maxPoints(Point[] points) {
        max=0;
        for(int i=0;i<points.length;i++){
            maxPoints(i,points);
        }
        return max;
    }
    public static void maxPoints(int curindex,Point[] points){
        Map<Double,Integer> map=new HashMap<Double,Integer>();
        int hornum=0;
        int vernum=0;
        int self=0;
        for(int i=0;i<points.length;i++){
            if(points[i].x==points[curindex].x && points[i].y==points[curindex].y) self++;
            else if(points[i].x==points[curindex].x) vernum++;
            else if(points[i].y==points[curindex].y) hornum++;
            else{
                double gradient=((double)points[i].y-points[curindex].y)/(points[i].x-points[curindex].x);
                if(map.containsKey(gradient)) map.put(gradient,map.get(gradient)+1);
                else map.put(gradient,1);
            }
        }
        int localmax=Math.max(hornum,vernum);
        for(Double i:map.keySet()){
            localmax=Math.max(localmax,map.get(i));
        }
        max=Math.max(localmax+self,max);
    }
    
    
    
    /*Combination Sum*/
    /*Given a set of candidate numbers (C) and a target number (T), find all unique combinations in C where the candidate numbers sums to T.
       The same repeated number may be chosen from C unlimited number of times.*/ 
    /* Combination Sum II*/
    /* Each number in C may only be used once in the combination.*/
    /* Note: All numbers (including target) will be positive integers.
       Elements in a combination (a1, a2, … , ak) must be in non-descending order. (ie, a1 ≤ a2 ≤ … ≤ ak).
       The solution set must not contain duplicate combinations.*/
    public static List<List<Integer>> combinationSum2(int[] num, int target) {
        List<List<Integer>> lists=new ArrayList<List<Integer>>();
        ArrayList<Integer> list= new ArrayList<Integer>(); 
        Arrays.sort(num);
        combinationSum2(num,target,lists,list,0);
        return lists;
    }
    public static void combinationSum2(int[] num, int target, List<List<Integer>> lists, ArrayList<Integer> list, int index) {
    		if(target==0){
    			ArrayList<Integer> addlist= new ArrayList<Integer>(list); 
    			lists.add(addlist);
    		}else if(target>0){
	        for(int i=index,leng=num.length;i<leng;){
	        		int count=1,cur=num[i];
	        		while(i+count<leng && num[i+count]==cur) count++;
	        		i+=count;
        			for(int j=0;j<count;j++) list.add(cur);
	        		for(;count!=0;count--){
	        			int remain=target-count*cur;
	        			combinationSum2(num,remain,lists,list,i);
		        		list.remove(list.size()-1);
	        		}
	        }
    		}
    }
    
    
    /*Reorder List */
    /*Given a singly linked list L: L0→L1→…→Ln-1→Ln, reorder it to: L0→Ln→L1→Ln-1→L2→Ln-2→…
	  You must do this in-place without altering the nodes' values.*/
    public static void reorderList(ListNode head) {
        ListNode slow=head,fast=head;
        if(fast==null || fast.next==null || fast.next.next==null){
			return;
		}
        while(fast!=null && fast.next!=null){
        		fast=fast.next.next;
        		slow=slow.next;
        }
        fast=reverseList(slow.next);
        slow.next=null;
        slow=head;
        while(slow!=null && fast!=null){
        		ListNode temptr=slow;
        		slow=slow.next;
        		temptr.next=fast;
        		fast=fast.next;
        		temptr.next.next=slow;
        }     
    }
    public static ListNode reverseList(ListNode head){
    		ListNode virtue=new ListNode(0);
    		virtue.next=head;
    		while(head.next!=null){
    			ListNode temptr=virtue.next;
    			virtue.next=head.next;
    			head.next=virtue.next.next;
    			virtue.next.next=temptr;
    		}
    		return virtue.next;
    }
    // Solution 2
    public void reorderList2(ListNode head) {
        if(head==null)
            return;
        ListNode virtue=new ListNode(0);
        virtue.next=head;
        ListNode slow=virtue,fast=virtue, constructor=head, tmptr;
        while(fast.next!=null&&fast.next.next!=null){
            slow=slow.next;
            fast=fast.next.next;
        }
        if(fast.next!=null){
            slow=slow.next;
            fast=fast.next;
        }
        while(constructor!=slow){
            tmptr=slow;
            while(tmptr.next!=fast){
                tmptr=tmptr.next;
            }
            fast.next=constructor.next;
            constructor.next=fast;
            tmptr.next=null;
            fast=tmptr;
            constructor=constructor.next;
            constructor=constructor.next;
        }
    }
    

    
    /*Next Permutation */
    /*Implement next permutation, which rearranges numbers into the lexicographically next greater permutation of numbers.
      If such arrangement is not possible, it must rearrange it as the lowest possible order (ie, sorted in ascending order).
	  The replacement must be in-place, do not allocate extra memory. Here are some examples. Inputs are in the left-hand column and its corresponding outputs are in the right-hand column.*/
    public static void nextPermutation(int[] num) {
        int leng = num.length;
        if(leng<2){
        		return;
        }
        int i=leng-1;
        for(;i>0 && num[i-1]>=num[i];i--);
        if(i==0){
        		Arrays.sort(num);
        		return;
        }
        int j=leng-1;
        for(;num[i-1]>=num[j];j--);
        int temp=num[i-1];
        num[i-1]=num[j];
        num[j]=temp;
        Arrays.sort(num,i,leng);
    }
    
    /*Word Search from leetcode*/
    /*Given a 2D board and a word, find if the word exists in the grid. The word can be constructed from letters of sequentially adjacent cell, 
     * where "adjacent" cells are those horizontally or vertically neighboring. The same letter cell may not be used more than once.*/
    public static boolean exist(char[][] board, String word) {
    	boolean[][]	used = new boolean[board.length][board[0].length];
        for(int i=0;i<board.length;i++){
        		for(int j=0;j<board[0].length;j++){
        			if(exist_helper(i,j,0,board,word,used)){
        				return true;
        			}
        		}
        }
        return false;
    }
    public static boolean exist_helper(int i,int j,int k,char[][] board,String word,boolean[][] used){
    		if(!used[i][j] && word.charAt(k)==board[i][j]){
        		if(k+1==word.length()){
        			return true;
        		}
    			used[i][j] = true;
    			if(i+1<board.length && exist_helper(i+1,j,k+1,board,word,used)) return true;
    			if(j+1<board[0].length && exist_helper(i,j+1,k+1,board,word,used)) return true;
    			if(i-1>=0 && exist_helper(i-1,j,k+1,board,word,used)) return true;
    			if(j-1>=0 && exist_helper(i,j-1,k+1,board,word,used)) return true;
    			used[i][j] = false;
    		}
    		return false;
    }
    
    
    
    /*Flatten Binary Tree to Linked List */
    /*Given a binary tree, flatten it to a linked list in-place.*/
    /*HINT: If you notice carefully in the flattened tree, each node's right child points to the next node of a pre-order traversal.(without this hint, you cannot go anywhere)*/
    public static void flatten(TreeNode root) {
        flatten_(root);
    }
    public static TreeNode flatten_(TreeNode root){
        if(root==null) return null;
        if(root.left==null&&root.right==null) return root;
        if(root.left==null){ 
	        if(root.right==null){
	            root.right=root.left;
	            root.left=null;
	        }
        }else{
	        TreeNode rightstart=root.right;
	        TreeNode leftend=flatten_(root.left);
	        root.right=root.left;
	        root.left=null;
	        leftend.right=rightstart;
        }
		return flatten_(root.right);
    }
    

    
    /*Edit Distance*/
    /*Given two words word1 and word2, find the minimum number of steps required to convert word1 to word2. (each operation is counted as 1 step.)
	  You have the following 3 operations permitted on a word:
	  a) Insert a character, b) Delete a character, c) Replace a character*/
    /*Levenshtein Distance algorithm: recursive version*/
    static int[][] minDistanceMP;
    public static int minDistance(String word1, String word2) {
    		  minDistanceMP=new int[word1.length()][word2.length()];
    		  return LevenshteinDistance(word1,word1.length(),word2,word2.length());
    }
    public static int LevenshteinDistance(String s, int len_s, String t, int len_t)
    {
	      /* base case: empty strings */
	      if (len_s == 0) return len_t;
	      if (len_t == 0) return len_s;
		  /*check the map*/
		  if(minDistanceMP[len_s-1][len_t-1]>0){
			     return minDistanceMP[len_s-1][len_t-1]-1;
		  }
	      /* test if last characters of the strings match */
	      int cost;
	      if (s.charAt(len_s-1) == t.charAt(len_t-1))
	          cost = 0;
	      else
	          cost = 1;     
	      /* return minimum of delete char from s, delete char from t, and delete char from both */
	      int res = Math.min(LevenshteinDistance(s, len_s - 1, t, len_t    ) + 1,
	                     Math.min(LevenshteinDistance(s, len_s    , t, len_t - 1) + 1,
	                     LevenshteinDistance(s, len_s - 1, t, len_t - 1) + cost));
	      minDistanceMP[len_s-1][len_t-1]=res+1;
	      return res;
    }
    
    
    
    /*Populating Next Right Pointers in Each Node*/
    /*Given a binary tree, Populate each next pointer to point to its next right node. If there is no next right node, the next pointer should be set to NULL.
	 Initially, all next pointers are set to NULL. Note: You may only use constant extra space. You may assume that it is a perfect binary tree (ie, all leaves are at the same level, 
	 and every parent has two children).*/
    /*Populating Next Right Pointers in Each Node II*/
    /*Follow up for problem "Populating Next Right Pointers in Each Node".
     * What if the given tree could be any binary tree? Would your previous solution still work?
     * Note: You may only use constant extra space.*/
    public static void connect(TreeLinkNode root) {
    		Queue<TreeLinkNode> q=new LinkedList<TreeLinkNode>();
    		TreeLinkNode left,right;
    		q.offer(root);
    		while(!q.isEmpty()){
    			Queue<TreeLinkNode> parents = q;
    			q=new LinkedList<TreeLinkNode>();
    			left=parents.poll();
    			while(left!=null){
    				right=parents.poll();
    				if(left.left!=null)q.offer(left.left);
    				if(left.right!=null)q.offer(left.right);
    				left.next=right;
    				left=right;
    			}
    		}       
    }
        
    
    
    /*Longest Common Prefix */
    /*Write a function to find the longest common prefix string amongst an array of strings.*/
    public static String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0)
            return "";
        for (int i = 0; i < strs[0].length() ; i++){
            char c = strs[0].charAt(i);
            for (int j = 1; j < strs.length; j ++) {
                if (i == strs[j].length() || strs[j].charAt(i) != c)
                    return strs[0].substring(0, i);             
            }
        }
        return strs[0];
    }
    
    
    
    
    /*Recover Binary Search Tree */
    /*Two elements of a binary search tree (BST) are swapped by mistake. Recover the tree without changing its structure.*/
    /*A solution using O(n) space is pretty straight forward. Could you devise a constant space solution?*/
    /*Morris Traversal algorithm 
      http://www.geeksforgeeks.org/inorder-tree-traversal-without-recursion-and-without-stack/ (in-order)  
      http://www.geeksforgeeks.org/morris-traversal-for-preorder/ (pre-order)
      ps: it takes more time than using stack, but solves the problem in place.
      */
    // this solution (uses Morris Traversal algorithm; no stack, no recursion) is too complex.
    public static void recoverTree(TreeNode root) {
        TreeNode pre=root,first=null,second=null,prefirst=null,presecond=null;
        while(root!=null){
        		if(root.left==null){
        			if(prefirst==null) prefirst=root;
        			else if(presecond==null) presecond=root;
        			else {prefirst=presecond;presecond=root;}
        			root=root.right;
        		}else{
        			pre=root.left;
        			while(pre.right!=null && pre.right!=root) pre=pre.right;
        			if(pre.right==root){
        				pre.right=null;
        				if(presecond==null) presecond=root;
        				else {prefirst=presecond;presecond=root;}
        				root=root.right;
        			}else{
        				pre.right=root;
        				root=root.left;
        				continue;
        			}
        		}
        		if(prefirst!=null&&presecond!=null&&prefirst.val>presecond.val){
        			if(first==null) first=prefirst;
        			second=presecond;
        		}
        }
        int temp=first.val;
        first.val=second.val;
        second.val=temp;
    }
    // my solution
    static TreeNode m;
    static TreeNode n;
    static TreeNode mm;
    static boolean fistpair; //use to separate two different cases, one is two swapped nodes are beside each other, the other one is they r apart.
    static boolean finish;
    public static void recoverTree2(TreeNode root)
    {
 	   m=null;
 	   n=null;
 	   mm=null;
 	   fistpair=false;
 	   finish=false;
 	   recoverTreeCheck(root);
 	   if(finish){

 		   int tem=m.val;
 		   m.val=n.val;
 		   n.val=tem;
 	   }else{
 		   int tem=m.val;
 		   m.val=mm.val;
 		   mm.val=tem;
 	   }
    }
    public static void recoverTreeCheck(TreeNode root)
    {
 	   if(root==null) return;
 	   recoverTreeCheck(root.left);
 	   if(m!=null){
 		   if(!fistpair&&root.val<m.val){    
 			   fistpair=true;
 			   mm=root;
 		   }else if(fistpair&&root.val<n.val){
 			   n=root;
 			   finish=true;
 			   return;
 		   }
 	   }
 	   if(!finish){
     	   if(!fistpair){
     		   m=root;
     	   }else{
     		   n=root;
     	   }
     	   recoverTreeCheck(root.right);
 	   }
    }
    
    

    
    /*N-Queens from leetcode*/
    /*The n-queens puzzle is the problem of placing n queens on an n×n chessboard such that no two queens attack each other.*/
    /*Given an integer n, return all distinct solutions to the n-queens puzzle. Each solution contains a distinct board configuration of the n-queens' placement, 
     * where 'Q' and '.' both indicate a queen and an empty space respectively.*/
    public static List<String[]> solveNQueens(int n) {
		Integer[] col = new Integer[n];
		List<String[]> results = new ArrayList<String[]>();
		placeQueens(0,col,results,n);
		return results;
    }
	public static void placeQueens(int row, Integer[] columns,List<String[]> results, int n) {
		if(row==n){
			String [] newboard = generateboard(columns,n);
			results.add(newboard);
		}else{
			for(int colnum=0;colnum<n;colnum++){
				if(checkValid(columns,row,colnum)){
					columns[row]=colnum;
					placeQueens(row+1,columns,results,n);
				}
			}
		}
	}
	public static String[] generateboard(Integer[] columns, int n){
		String[] newboard=new String[n];
		char[] string=new char[n];
		for(int i=0;i<n;i++){
			int qpos=columns[i];
			for(int j=0;j<n;j++){
				if(qpos!=j){
					string[j]='.';
				}else{
					string[j]='Q';
				}
			}
			newboard[i]=new String(string);
		}
		return newboard;
	}
	public static boolean checkValid(Integer[] columns, int rowl, int columnl) {
		int temcol=0;
		int coldif=0;
		int rowdif=0;
		for(int temrow=0;temrow<rowl;temrow++){
			temcol=columns[temrow];
			if(temcol==columnl){
				return false;
			}
			coldif=Math.abs(columnl-temcol);
			rowdif=Math.abs(temrow-rowl);
			if(coldif==rowdif){
				return false;
			}
		}
		return true;
	}
    
    
	
	/*N-Queens II*/
	/*instead outputting board configurations, return the total number of distinct solutions.*/
	/*make use of checkValid function from above*/
    static int total;
    public static int totalNQueens(int n) {
		Integer[] col = new Integer[n];
		total=0;
		placeQueens(0,col,n);
		return total;
    }
	public static void placeQueens(int row, Integer[] columns,int n) {
		if(row==n){
			total++;
		}else{
			for(int colnum=0;colnum<n;colnum++){
				if(checkValid(columns,row,colnum)){
					columns[row]=colnum;
					placeQueens(row+1,columns,n);
				}
			}
		}
	}
	
    
	
	/*Find Minimum in Rotated Sorted Array*/
	/*Suppose a sorted array is rotated at some pivot unknown to you beforehand.
	 * (i.e., 0 1 2 4 5 6 7 might become 4 5 6 7 0 1 2). Find the minimum element. The array does not contain duplicates.*/
    public static int findMin(int[] num) {
        int l=0, r=num.length-1;
        while(true){
            if(l+1>=r)
                return Math.min(num[l],num[r]);
            int m=(l+r)/2;
            if(num[m]<num[l])
                r=m;
            else if(num[m]>num[r])
                l=m+1;
            else
                return num[l];
        }
    }
	
	/*Find Minimum in Rotated Sorted Array II */
	/*Suppose a sorted array is rotated at some pivot unknown to you beforehand.
	 * (i.e., 0 1 2 4 5 6 7 might become 4 5 6 7 0 1 2). Find the minimum element. The array may contain duplicates.*/
    /*For case where AL == AM == AR, the minimum could be on AM’s left or right side (eg, [1, 1, 1, 0, 1] or [1, 0, 1, 1, 1]). 
     * In this case, we could not discard either subarrays and therefore such worst case degenerates to the order of O(n).*/
    // Catch: step by step decrease the range of the target value.
    public static int findMin2(int[] num) {
		int l=0, r=num.length-1;
		while(true){
			int m=(r+l)/2;
			if(l+1>=r)
				return Math.min(num[l], num[r]);
			if(num[l]>num[m])
				r=m;
			else if(num[m]>num[r])
				l=m;
			else if(num[l]==num[r]){
				l++;
				r--;
			}
			else
				return num[l];
		}
    }
	
	
	
	
	/*Construct Binary Tree from Preorder and Inorder Traversal */
	/*Given preorder and inorder traversal of a tree, construct the binary tree. Note: You may assume that duplicates do not exist in the tree.*/
	// Catch: pre runs faster than in; use pre to create new node everytime.
    public static TreeNode buildTree(int[] preorder, int[] inorder) {
        TreeNode virtue=new TreeNode(0), ptr=virtue;
        Stack<TreeNode> sk = new Stack<TreeNode>();
        int in=0, pre=0;
        while(pre<inorder.length){
            if((!sk.isEmpty()&&sk.peek().val!=inorder[in])||pre==0){
                ptr.left=new TreeNode(preorder[pre++]);
                ptr=ptr.left;
                sk.push(ptr);
            }else{
                while(!sk.isEmpty() && sk.peek().val==inorder[in]){
                    ptr=sk.pop();
                    in++;
                }
                ptr.right=new TreeNode(preorder[pre++]);
                ptr=ptr.right;
                sk.push(ptr);
            }    
        }
        return virtue.left;
    }
	
	
    /*Construct Binary Tree from Inorder and Postorder Traversal */
	/*Given inorder and postorder traversal of a tree, construct the binary tree. Note: You may assume that duplicates do not exist in the tree.*/
    public static TreeNode build2Tree(int[] inorder, int[] postorder) {
        TreeNode virtue=new TreeNode(0), ptr=virtue;
        Stack<TreeNode> sk = new Stack<TreeNode>();
        int in=inorder.length-1, post=postorder.length-1;
        while(post>=0){
            if((!sk.isEmpty()&&sk.peek().val!=inorder[in])||post==postorder.length-1){
                ptr.right=new TreeNode(postorder[post--]);
                ptr=ptr.right;
                sk.push(ptr);
            }else{
                while(!sk.isEmpty() && sk.peek().val==inorder[in]){
                    ptr=sk.pop();
                    in--;
                }
                ptr.left=new TreeNode(postorder[post--]);
                ptr=ptr.left;
                sk.push(ptr);
            }    
        }
        return virtue.right;
    }
	
    
    /*Implement strStr() */
    /*Returns the index of the first occurrence of needle in haystack, or -1 if needle is not part of haystack.*/
    /*Brute Force*/
    public static int strStr(String haystack, String needle) {
		int count=haystack.length();
		int needleLeng=needle.length();
		if(needleLeng==0) return 0;
		int i=0,j=0;
	    while(i<=count-needleLeng){
	    		if(haystack.charAt(i+j)==needle.charAt(j)){
	    			j++;
	    			if(j==needleLeng){
	    				return i;
	    			}
	    		}else{
	    			i++;
	    			j=0;
	    		}
	    }
	    return -1;
    }
    /*KMP?????????????????????????*/
    public static void strStr_test(){
    		System.out.println(strStr("mississippi", "mississippi"));
    }
   
   
    
    /*Partition List */
    /*Given a linked list and a value x, partition it such that all nodes less than x come before nodes greater than or equal to x.
	You should preserve the original relative order of the nodes in each of the two partitions.
	For example, given 1->4->3->2->5->2 and x = 3, return 1->2->2->4->3->5.*/
    public static ListNode partition(ListNode head, int x) {
        ListNode virtue=new ListNode(0), virtue2=new ListNode(0);
        ListNode ptr=virtue, ptr2=virtue2;
        while(head!=null){
	        if(head.val<x){
	        		ptr.next=head;
	        		ptr=ptr.next;
	        }else{
	        		ptr2.next=head;
	        		ptr2=ptr2.next;
	        }
    			head=head.next;
        }
        ptr.next=virtue2.next;
        ptr2.next=null;
        return virtue.next;
    }
    public static void partition_test(){
    		ListNode head=new ListNode(2);
    		head.next=new ListNode(1);
    		ListNode ptr=partition(head,2);
    		while(ptr!=null){
    			System.out.println(ptr.val);
    			ptr=ptr.next;
    		}
    }
    
    
    /*Gas Station */
    /*There are N gas stations along a circular route, where the amount of gas at station i is gas[i]. 
     * You have a car with an unlimited gas tank and it costs cost[i] of gas to travel from station i to its next station (i+1). 
     * You begin the journey with an empty tank at one of the gas stations. Return the starting gas station's index if you can travel around the circuit once, otherwise return -1.
     * Note: The solution is guaranteed to be unique.*/
    /*some ideas from discussion:
     * If car starts at A and can not reach B, any station between A and B can not reach B (B is the first station that A can not reach.)
	 * If the total number of gas is bigger than the total number of cost. There must be a solution. */
    public static int canCompleteCircuit(int[] gas, int[] cost) {
        int tolgasleft=0, curgasleft=0, validstart=0;
        for(int i=0;i<gas.length;i++){
            tolgasleft+=gas[i]-cost[i];
            curgasleft+=gas[i]-cost[i];
            if(curgasleft<0){
                validstart=i+1;
                curgasleft=0;
            }
        }
        return tolgasleft>=0?validstart:-1;
    }

    
    /*Search in Rotated Sorted Array*/
    /*Suppose a sorted array is rotated at some pivot unknown to you beforehand. (i.e., 0 1 2 4 5 6 7 might become 4 5 6 7 0 1 2).
     * You are given a target value to search. If found in the array return its index, otherwise return -1.
     * You may assume no duplicate exists in the array.*/
    public static int search(int[] A, int target) {
        int start = 0, end = A.length-1;
        while (start <= end) {
            int mid = (start + end) / 2;
            if (A[mid] == target) return mid;
            if (A[start] <= A[mid]) {
                if (A[start] <= target && target <= A[mid])
                    end = mid-1;
                else
                    start = mid+1;
            }
            else {
                if (A[mid] <= target && target <= A[end]) 
                    start = mid+1;
                else 
                    end = mid-1;
            }
        }
        return -1;  
    }
    

    /*Search in Rotated Sorted Array II */
    /*Follow up for "Search in Rotated Sorted Array": What if duplicates are allowed? 
     * Would this affect the run-time complexity? How and why?
     * Write a function to determine if a given target is in the array.*/
    public static boolean search2(int[] A, int target) {
        int start = 0;
        int end = A.length-1;
        while (start <= end) {
            int mid = (start + end) / 2;
            if (A[mid] == target || A[start]==target) return true;
            if(A[start]==A[end]){
            		start++;
            		end--;
            }
            else if (A[start] <=A[mid]) {      // it has to be <= in this case !!!!!!!!!!!!!!!!!!!!!
                if (A[start] <= target && target < A[mid]) {
                    end = mid-1;
                }
                else {
                    start = mid+1;
                }
            }
            else {
                if (A[mid] < target && target <= A[end]) {
                    start = mid+1;
                }
                else {
                    end = mid-1;
                }
            }
        }
        return false;  
    }
    
    
    
    /*Surrounded Regions */
    /*Given a 2D board containing 'X' and 'O', capture all regions surrounded by 'X'.
     * A region is captured by flipping all 'O's into 'X's in that surrounded region.*/
    /*Union-Find data structure*/
    static int[] unionSet; // union find set
    static boolean[] hasEdgeO; // whether an union has an 'O' which is on the edge of the matrix
    public static void solve(char[][] board) {
        if(board.length == 0 || board[0].length == 0) return;
        // init, every char itself is an union
        int height = board.length, width = board[0].length;
        unionSet = new int[height * width];
        hasEdgeO = new boolean[unionSet.length];
        for(int i = 0;i<unionSet.length; i++) unionSet[i] = i;
        for(int i = 0;i<hasEdgeO.length; i++){
            int x = i / width, y = i % width;
            hasEdgeO[i] = (board[x][y] == 'O' && (x==0 || x==height-1 || y==0 || y==width-1));
        }
        // iterate the matrix, for each char, union it + its upper char + its right char if they equals to each other
        for(int i = 0;i<unionSet.length; i++){
            int x = i / width, y = i % width, up = x - 1, right = y + 1;
            if(up >= 0 && board[x][y] == board[up][y]) union(i,i-width);
            if(right < width && board[x][y] == board[x][right]) union(i,i+1);
        }
        // for each char in the matrix, if it is an 'O' and its union doesn't has an 'edge O', the whole union should be setted as 'X'
        for(int i = 0;i<unionSet.length; i++){
            int x = i / width, y = i % width;
            if(board[x][y] == 'O' && !hasEdgeO[findSet(i)]) 
                board[x][y] = 'X'; 
        }
    }
    public static void union(int x,int y){
        int rootX = findSet(x);
        int rootY = findSet(y);
        // if there is an union has an 'edge O',the union after merge should be marked too
        boolean hasEdge = hasEdgeO[rootX] || hasEdgeO[rootY];
        unionSet[rootX] = rootY;
        hasEdgeO[rootY] = hasEdge;
    }
    public static int findSet(int x){
        if(unionSet[x] == x) return x;
        unionSet[x] = findSet(unionSet[x]);
        return unionSet[x];
    }
    /*my own method (which is better)*/
    public void solve2(char[][] board)
    {
	     if(board.length == 0||board.length == 1|| board[0].length == 1)
	    	 	 return;
	     int m = board.length;
	     int n = board[0].length;
	     boolean[][] cache = new boolean[m][n];
	     int i=0,j =0;  
	     for(;j<n-1;j++) 
		     if(board[i][j]=='O') 
		     {
			     cache[i][j]=true;
			     solvehelper(cache, board, i+1, j);
		     }
	     for(;i<m-1;i++) 
		     if(board[i][j]=='O') 
		     {
			     cache[i][j]=true;
			     solvehelper(cache, board, i, j-1);
		     }
	     for(;j>0;j--) 
		     if(board[i][j]=='O') {
			     cache[i][j]=true;
			     solvehelper(cache, board, i-1, j);
		     }
	     for(;i>0;i--) 
		     if(board[i][j]=='O'){
			    	 cache[i][j]=true;
			     solvehelper(cache, board, i, j+1);
		     }
	     for(i = 0; i < m; i++)
		     for(j = 0; j < n; j++)
		     {
			     if(board[i][j] == 'O' && cache[i][j] != true)
			    	 	board[i][j] = 'X';
			 }
    }
    public void solvehelper(boolean[][] cache, char[][] board, int i, int j)
    {
	     if(i == 0 || i == board.length-1 || j == 0 || j== board[0].length-1 ||cache[i][j] == true || board[i][j] == 'X')
	    	 	return;
	     if(board[i][j] == 'O')
	    	 	cache[i][j] = true;
	     solvehelper(cache, board, i + 1, j);
	     solvehelper(cache, board, i, j+1);
	     solvehelper(cache, board, i - 1, j);
	     solvehelper(cache, board, i, j-1);
    }
    
    
    
    
    
    /*Copy List with Random Pointer*/
    /*A linked list is given such that each node contains an additional random pointer which could point to any node in the list or null.
     * Return a deep copy of the list.*/
    public static RandomListNode copyRandomList(RandomListNode head) {
    		RandomListNode prehead=new RandomListNode(0), p=prehead, q;
    		prehead.next=head;
    		while(p.next!=null){
    			p=p.next;
    			q=p.next;
    			p.next=new RandomListNode(p.label);
    			p=p.next;
    			p.next=q;
    		}
    		p=prehead;
    		while(p.next!=null){
    			p=p.next;
    			p.next.random=(p.random==null)?null:p.random.next;
    			p=p.next;
    		}
    		p=prehead;
    		while(p.next!=null){
    			q=p.next;
    			p.next=q.next;
    			p=q.next;
    			q.next=p.next;
    		}
    		return prehead.next;
    }
    
    
    
    
    /*Fraction to Recurring Decimal*/
    /*Given two integers representing the numerator and denominator of a fraction, return the fraction in string format.
     * If the fractional part is repeating, enclose the repeating part in parentheses. For example,
     * Given numerator = 1, denominator = 2, return "0.5". 
     * Given numerator = 2, denominator = 1, return "2". 
     * Given numerator = 2, denominator = 3, return "0.(6)".*/
    public static String fractionToDecimal(int numerator, int denominator) {
		StringBuilder res = new StringBuilder();
    		// avoid overflow for minimum value of integer
        long n = (long) numerator;
        long d = (long) denominator;
		if(numerator%denominator==0) return Long.toString(n/d);
    		res.append((n^d)<0?"-":"");
        n=Math.abs(n);
        d=Math.abs(d);
        res.append(n/d+".");
    		HashMap<Long,Integer> map=new HashMap<Long,Integer>();
    		for(long r=n%d; r!=0; r%=d){
    			if(map.containsKey(r)){
    				res.insert(map.get(r), "(");
    				res.append(")");
    				break;
    			}
    			map.put(r, res.length());
    			r*=10;
    			res.append(r/d);
    		}
    		return res.toString();
    }
    public static void fractionToDecimal_test(){
    		System.out.println(fractionToDecimal(-1,-2147483648));
    }
    
    
    /*Longest Consecutive Sequence*/
    /*Given an unsorted array of integers, find the length of the longest consecutive elements sequence. For example
     * Given [100, 4, 200, 1, 3, 2], the longest consecutive elements sequence is [1, 2, 3, 4]. Return its length: 4.
     * Your algorithm should run in O(n) complexity.*/
    public static int longestConsecutive(int[] num) {
        HashMap<Integer,Integer> map=new HashMap<Integer,Integer>();
        int longest=0;
        for(int i=0;i<num.length;i++){
            if(map.containsKey(num[i])) continue;
            int low=num[i], upp=num[i];
            if(map.containsKey(num[i]-1)) low=map.get(num[i]-1);
            if(map.containsKey(num[i]+1)) upp=map.get(num[i]+1);
            map.put(num[i],num[i]);
            map.put(low,upp);
            map.put(upp,low);
            if(upp-low+1>longest) longest=upp-low+1;
        }
        return longest;
    }
    
    
    /*Combinations*/
    /*Given two integers n and k, return all possible combinations of k numbers out of 1 ... n.
     * For example, if n = 4 and k = 2, a solution is: [[2,4],[3,4],[2,3],[1,2],[1,3],[1,4]]*/
    public static List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> res=new ArrayList<List<Integer>>();
        List<Integer> buf=new ArrayList<Integer>();
        combine(n,k,res,buf);
        return res;
    }
    public static void combine(int n, int k, List<List<Integer>> res, List<Integer> buf){
        if(k==0) res.add(new ArrayList<Integer>(buf));
        else{
            for(int i=n;i>=k;i--){
                buf.add(0,i);
                combine(i-1,k-1,res,buf);
                buf.remove(0);
            }
        }
    }
    
    
    /*Word Ladder II*/
    /*Given two words (start and end), and a dictionary, find all shortest transformation sequence(s) from start to end, 
     * such that: Only one letter can be changed at a time; Each intermediate word must exist in the dictionary.
		    	For example,
			Given:
		    	start = "hit"
		    	end = "cog"
		    	dict = ["hot","dot","dog","lot","log"]
		    	Return
		    	  [
		    	    ["hit","hot","dot","dog","cog"],
		    	    ["hit","hot","lot","log","cog"]
		    	  ]
		    	Note:
		    	All words have the same length.
		    	All words contain only lowercase alphabetic characters. 
     */
    // method 1: backtracing; only record a list of words; from leet code.
    static List<List<String>> results;
    static List<String> list;
    static Hashtable<String,List<String>> map;
    public static List<List<String>> findLadders(String start, String end, Set<String> dict) {
    		results=new ArrayList<List<String>>();
    		Queue<String> q=new LinkedList<String>();
    		HashSet<String> visited=new HashSet<String>();
    		HashSet<String> unvisited=new HashSet<String>();
    		map=new Hashtable<String,List<String>>();
    		q.add(start);
    		unvisited.addAll(dict);
    		unvisited.remove(start);
    		int cur=1,next=0;
    		boolean found=false;
    		while(!q.isEmpty()){
    			String word=q.poll();
    			cur--;
    			for(int i=0;i<word.length();i++){
        			StringBuilder word_=new StringBuilder(word);
    				for(char j='a';j<='z';j++){
    					word_.setCharAt(i, j);
    					String next_word=word_.toString();
    					if(unvisited.contains(next_word)){
    						if(visited.add(next_word)){
        						q.add(next_word);
        						next++;
    						}
    						if(map.containsKey(next_word)){
    							map.get(next_word).add(word);
    						}else{
    							list=new ArrayList<String>();
    							list.add(word);
    							map.put(next_word, list);
    						}
    					}
    					if(next_word.equals(end)) found=true;
    				}
    			}
    			if(cur==0){
    				if(found) break;
    				cur=next;
    				next=0;
    				unvisited.removeAll(visited);
    				visited.clear();
    			}
    		}
    		list=new ArrayList<String>();
    		findLaddersBacktrace(end,start);
    		return results;
    }
    public static void findLaddersBacktrace(String word, String start){
    		list.add(0, word);
    		if(word.equals(start))	results.add(new ArrayList<String>(list));
    		if(map.containsKey(word)){
    			for(String s: map.get(word)){
    				findLaddersBacktrace(s,start);
    			}
    		}
    		list.remove(0);
    }
    public static void findLadders_test() {
    		Set<String> dict=new HashSet<String>();
    		dict.add("a");
    		dict.add("b");
    		dict.add("c");
    		List<List<String>> lists=findLadders("a", "c", dict);
    		for(List<String> list: lists){
    			System.out.print("[");
    			for(String s: list){
    				System.out.print(s+" ");
    			}
    			System.out.println("]");
    		}
    }
    // method2: my own; every word (just like graph node) records a list of paths.
    public static List<List<String>> findLadders2(String start, String end, Set<String> dict) {   	
		Queue<String> q=new LinkedList<String>();
		q.add(start);
		HashSet<String> visited=new HashSet<String>();
		HashSet<String> unvisited=new HashSet<String>(dict);
		unvisited.remove(start);
		int cur=1,next=0;
		List<List<String>> startlist=new ArrayList<List<String>>();
		startlist.add(new ArrayList<String>());
		startlist.get(0).add(start);
		Map<String, List<List<String>>> path=new HashMap<String,List<List<String>>>();
		path.put(start, startlist);
		while(!q.isEmpty()){
			String word=q.poll();
			cur--;
			for(int i=0;i<word.length();i++){
    				StringBuilder word_=new StringBuilder(word);
				for(char j='a';j<='z';j++){
					word_.setCharAt(i, j);
					String next_word=word_.toString();
					if(unvisited.contains(next_word)){
						if(!path.containsKey(next_word)) path.put(next_word, new ArrayList<List<String>>());
						for(List<String> list: path.get(word)){
							ArrayList<String> newlist=new ArrayList<String>(list);
							newlist.add(next_word);
							path.get(next_word).add(newlist);
						}
						if(visited.add(next_word)){
	    						q.add(next_word);
	    						next++;
						}
					}
				}
			}
			if(cur==0){
				if(path.containsKey(end)) return path.get(end);
				cur=next;
				next=0;
				unvisited.removeAll(visited);
				visited.clear();
			}
		}
		return new ArrayList<List<String>>();
    }
    
    
    
    
    /*Word Ladder from leetcode*/
    /*Given two words (start and end), and a dictionary, find the length of shortest transformation sequence from start to end, such that:
			Only one letter can be changed at a time
			Each intermediate word must exist in the dictionary*/
    /*Note: Return 0 if there is no such transformation sequence. All words have the same length. All words contain only lowercase alphabetic characters.*/
    // typical graph DFS; word ladder II makes the reason why we need the hashset visited more clear.
    public static int ladderLength(String start, String end, Set<String> dict) {    	
		Queue<String> q=new LinkedList<String>();
		HashSet<String> visited=new HashSet<String>();
		HashSet<String> unvisited=new HashSet<String>();
		q.add(start);
		unvisited.addAll(dict);
		unvisited.remove(start);
		int cur=1,next=0, leng=1;
		boolean found=false;
		while(!q.isEmpty()){
			String word=q.poll();
			cur--;
			for(int i=0;i<word.length()&&!found;i++){
    				StringBuilder word_=new StringBuilder(word);
				for(char j='a';j<='z';j++){
					word_.setCharAt(i, j);
					String next_word=word_.toString();
					if(next_word.equals(end)){
						found=true;
						break;
					}
					if(unvisited.contains(next_word)){
						if(visited.add(next_word)){
	    						q.add(next_word);
	    						next++;
						}
					}
				}
			}
			if(cur==0){
				leng++;
				if(found) return leng;
				cur=next;
				next=0;
				unvisited.removeAll(visited);
				visited.clear();
			}
		}
		return 0;
    }
    
    
    /*Maximum Gap*/
    /*Given an unsorted array, find the maximum difference between the successive elements in its sorted form.
     * Try to solve it in linear time/space. Return 0 if the array contains less than 2 elements.
     * You may assume all elements in the array are non-negative integers and fit in the 32-bit signed integer range.*/
    /*make use of pigeon hole theory*/
    // pigeonhole sort
    public static int maximumGap(int[] num) {
        if(num.length<2) return 0;
        int min=-1,max=-1, n=num.length;
        for(int i:num){
        		max=(i>max)?i:max;
        		min=(i<min||min==-1)?i:min;
        }
        double dist=(max-min)/(double)(n-1);
        int[] mins=new int[n];
        int[] maxs=new int[n];
        Arrays.fill(mins, -1);
        Arrays.fill(maxs, -1);
        for(int i:num){
        		int idx=(int)((i-min)/dist);
        		mins[idx]=(mins[idx]>i||mins[idx]==-1)?i:mins[idx];
        		maxs[idx]=(maxs[idx]<i)?i:maxs[idx];
        }
        int premax=maxs[0];
        int maxgap=0;
        for(int i=1;i<n;i++){
        		if(maxs[i]==-1) continue;
        		int gap=mins[i]-premax;
        		maxgap=(gap>maxgap)?gap:maxgap;
        		premax=maxs[i];
        }
        return maxgap;
    }
    
    
    /*Remove Duplicates from Sorted Array*/
    /*Given a sorted array, remove the duplicates in place such that each element appear only once and return the new length.
     * Do not allocate extra space for another array, you must do this in place with constant memory.
     * For example, Given input array A = [1,1,2], Your function should return length = 2, and A is now [1,2].*/
    public static int removeDuplicates(int[] A) {
    		if(A.length==0) return 0;
        int newptr=1,ptr=1,pre=A[0];
        for(;ptr<A.length;ptr++){
        		if(pre==A[ptr]) continue;
        		pre=A[ptr];
        		A[newptr++]=pre;
        }
        return newptr;
    }
    
    
    /*Remove Duplicates from Sorted Array II */
    /*What if duplicates are allowed at most twice?
     * For example, given sorted array A = [1,1,1,2,2,3], your function should return length = 5, and A is now [1,1,2,2,3].*/
    public static int removeDuplicates2(int[] A) {
        int newptr=0;
        for(int i=0;i<A.length;i++){
            if(newptr<2||A[newptr-1]!=A[i]||A[newptr-2]!=A[i])
                A[newptr++]=A[i];
        }
        return newptr;
    }
    
    
    /*Binary Tree Level Order Traversal*/
    /*Given a binary tree, return the level order traversal of its nodes' values. (ie, from left to right, level by level).*/
    public static List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> lists=new ArrayList<List<Integer>>();
		if(root==null) return lists;
        Queue<TreeNode> queue=new LinkedList<TreeNode>();
        int cur=1,next=0;
        queue.offer(root);
        while(!queue.isEmpty()){
        		List<Integer> list=new ArrayList<Integer>();
        		while(cur--!=0){
        			TreeNode n=queue.poll();
        			list.add(n.val);
        			if(n.left!=null){
        				queue.offer(n.left);
        				next++;
        			}
        			if(n.right!=null){
        				queue.offer(n.right);
        				next++;
        			}
        		}
        		cur=next;
        		next=0;
        		lists.add(list);
        }
        return lists;
    }
    
    
    
    /*Set Matrix Zeroes */
    /*Given a m x n matrix, if an element is 0, set its entire row and column to 0. Do it in place.*/
    // O(m + n) space complexity
    public static void setZeroes(int[][] matrix) {
        HashSet<Integer> mapm=new HashSet<Integer>();
        HashSet<Integer> mapn=new HashSet<Integer>();
        int m=matrix.length;
        int n=matrix[0].length;
        for(int i=0;i<m;i++){
        		for(int j=0;j<n;j++){
        			if(matrix[i][j]==0){
        				mapn.add(j);
        				mapm.add(i);
        			}
        		}
        }
        for(int i=0;i<m;i++){
    			for(int j=0;j<n;j++){
    				if(mapm.contains(i) || mapn.contains(j))
    					matrix[i][j]=0;
    			}
        }
    }
    
    
    /*Palindrome Partitioning*/
    /*Given a string s, partition s such that every substring of the partition is a palindrome.
     * Return all possible palindrome partitioning of s.
     * For example, given s = "aab", return*/
     /*
        [["aa","b"],["a","a","b"]]
    	*/
    public static List<List<String>> partition(String s) {
    		int len=s.length();
    		List<List<String>> resAtzero=new ArrayList<List<String>>();
    		resAtzero.add(new ArrayList<String>());
    		if(len==0) return resAtzero;
        @SuppressWarnings("unchecked")
		List<List<String>>[] res=new List[len+1];
        res[0]=resAtzero;
        boolean[][] map=new boolean[len][len];
        for(int i=0;i<len;i++){
        		res[i+1]=new ArrayList<List<String>>();
        		for(int j=0;j<=i;j++){
        			if(i==j) map[i][i]=true;
        			else{
	        			if(s.charAt(i)!=s.charAt(j)) continue;
	        			else{
	        				if(i==j+1) map[i][j]=true;
	        				else map[i][j]=map[i-1][j+1];
	        			}
        			}
        			if(map[i][j]){
        				String pal=s.substring(j, i+1);
        				for(List<String> list:res[j]){
        					List<String> newlist=new ArrayList<String>(list);
        					newlist.add(pal);
        					res[i+1].add(newlist);
        				}
        			}
        		}
        }
        return res[len];
    }
    // My Solution 2: same idea, but uses recursion
    public static List<List<String>> partition2(String s) {
        boolean[][] palindromeMP=new boolean[s.length()][s.length()];
        HashMap<Integer,List<List<String>>> map=new HashMap<Integer,List<List<String>>>();
        for(int i=0; i<s.length(); i++){
            for(int j=0;j<=i;j++){
                if(i==j||(j+1==i&&s.charAt(j)==s.charAt(i)))
                    palindromeMP[i][j]=true;
                else if(s.charAt(j)==s.charAt(i))
                    palindromeMP[i][j]=palindromeMP[i-1][j+1];
            }
        }
        return partition(s,0,map,palindromeMP);
    } 
     public static List<List<String>> partition(String s, int index, HashMap<Integer,List<List<String>>> map, boolean[][] palindromeMP){
        if(map.containsKey(index))
            return map.get(index);
        List<List<String>> lists=new ArrayList<List<String>>();
        if(index>=s.length()){
            lists.add(new ArrayList<String>());
            return lists;
        }
        for(int i=index; i<s.length(); i++){
            if(palindromeMP[i][index]){
                List<List<String>> last = partition(s,i+1,map,palindromeMP);
                for(List<String> list:last){
                    List<String> newlist=new ArrayList<String>(list);
                    newlist.add(0,s.substring(index,i+1));
                    lists.add(newlist);
                }
            }
        }
        map.put(index,lists);
        return lists;
    }
    
    
    /*Palindrome Partitioning II */
    /*Given a string s, partition s such that every substring of the partition is a palindrome.
     * Return the minimum cuts needed for a palindrome partitioning of s.
     * For example, given s = "aab",
     * Return 1 since the palindrome partitioning ["aa","b"] could be produced using 1 cut.*/
    public static int minCut(String s) {
        int len=s.length();
        boolean[][] boolmap=new boolean[len][len];
        int[] nummap=new int[len+1];
        for(int i=0;i<len;i++){
            int tmp=Integer.MAX_VALUE;
            for(int j=0;j<=i;j++){
                if(i==j || (i==j+1 && s.charAt(i)==s.charAt(j)))
                    boolmap[j][i]=true;
                else if(s.charAt(i)==s.charAt(j))
                    boolmap[j][i]=boolmap[j+1][i-1];
                if(boolmap[j][i])
                    tmp=Math.min(tmp,(j!=0)?nummap[j]+1:0);
            }
            nummap[i+1]=tmp;
        }
        return nummap[len];
    }
    
    
    
    /*Pow(x, n)*/
    /*Implement pow(x, n).*/
    public static double pow(double x, int n) {
        if(n==0) return 1;
        if(n==1) return x;
        if(n>0) return(n%2==0)?pow(x*x,n/2):pow(x*x,n/2)*x;
        return(n%2==0)?pow(1/(x*x),-n/2):pow(1/(x*x),-n/2)/x;
    }
    
    
    
    /*Rotate List */
    /*Given a list, rotate the list to the right by k places, where k is non-negative.
     * For example: given 1->2->3->4->5->NULL and k = 2, return 4->5->1->2->3->NULL.*/
    public static ListNode rotateRight(ListNode head, int n) {
        ListNode virtue=new ListNode(0);
        virtue.next=head;
        ListNode ptr=virtue;
        int i=0;
        while(ptr.next!=null&&i!=n){
            i++;
            ptr=ptr.next;
        }
        if(i==0||(i==n&&ptr.next==null)||(i!=n&&n%i==0)){
            return head;
        }
        if(i!=n){
            n%=i;
            ptr=virtue;
            while(n>0){
                ptr=ptr.next;
                n--;
            }
        }
        ListNode constructor=virtue;
        while(ptr.next!=null){
            ptr=ptr.next;
            constructor=constructor.next;
        }
        ptr.next=virtue.next;
        virtue.next=constructor.next;
        constructor.next=null;
        return virtue.next;
    }
    
    
    
    /*Convert Sorted Array to Binary Search Tree*/
    /*Given an array where elements are sorted in ascending order, convert it to a height balanced BST.*/
    public static TreeNode sortedArrayToBST(int[] num) {
        int len=num.length;
        return sortedArrayToBST(0,len-1,num);
    }
    public static TreeNode sortedArrayToBST(int start, int end, int[] num){
    		if(end<start) return null;
    		int mid=(end+start)/2;
    		TreeNode head=new TreeNode(num[mid]);
    		head.left=sortedArrayToBST(start,mid-1,num);
    		head.right=sortedArrayToBST(mid+1,end,num);
    		return head;
    }
    
    
    /*Rotate Image*/
    /*You are given an n x n 2D matrix representing an image. Rotate the image by 90 degrees (clockwise).
     * Follow up: Could you do this in-place?*/
    public static void rotate(int[][] matrix) {
        int len=matrix.length;
        for(int i=0;i<len/2;i++){
            for(int j=i;j<len-i-1;j++){
                int tmp=matrix[i][j];
                tmp=rotate(tmp,j,len-i-1,matrix);
                tmp=rotate(tmp,len-i-1,len-j-1,matrix);
                tmp=rotate(tmp,len-j-1,i,matrix);
                matrix[i][j]=tmp;
            }
        }
    }
    public static int rotate(int tmp, int i, int j, int[][] matrix){
        int cur=matrix[i][j];
        matrix[i][j]=tmp;
        return cur;
    }
    
    
    /*Find Peak Element*/
    /*A peak element is an element that is greater than its neighbors.
     * Given an input array where num[i] ≠ num[i+1], find a peak element and return its index.
     * The array may contain multiple peaks, in that case return the index to any one of the peaks is fine.
     * You may imagine that num[-1] = num[n] = -∞.
     * For example, in array [1, 2, 3, 1], 3 is a peak element and your function should return the index number 2.*/
    /*Your solution should be in logarithmic complexity.*/
    public static int findPeakElement(int[] num) {
    		return findPeakElement(0,num.length-1,num);
    }
    public static int findPeakElement(int start, int end, int[] num){
    		if(start==end) return start;
    		if(start+1==end) return (num[start]>num[end])?start:end;
    		int mid=(end+start)/2;
    		if(num[mid]>num[mid-1] && num[mid]>num[mid+1]) return mid;
    		if(num[mid]<num[mid-1]) return findPeakElement(start,mid-1,num);
    		else return findPeakElement(mid+1,end,num);
    }
    //Solution 2
    public int findPeakElement2(int[] num) {
        int pre=num[0];
        for(int i=1;i<num.length;i++){
            if(num[i]<pre){
                return i-1;
            }
            pre=num[i];
        }
        return num.length-1;
    }
    
    
    /*Search a 2D Matrix*/
    /*Write an efficient algorithm that searches for a value in an m x n matrix. This matrix has the following properties:
     * Integers in each row are sorted from left to right.
     * The first integer of each row is greater than the last integer of the previous row.*/
    public static boolean searchMatrix(int[][] matrix, int target) {
    		int rownum=matrix.length, colnum=matrix[0].length;
        int tot=rownum*colnum;
        int start=0, end=tot-1;
        while(true){
        	    if(end<start) return false;
        		int mid=(start+end)/2, row=mid/colnum, col=mid%colnum;
        		if(matrix[row][col]==target) return true;
        		if(matrix[row][col]>target) end=mid-1;
        		else start=mid+1;
        }
    }
    
    
    /*Word Break II*/
    /*Given a string s and a dictionary of words dict, add spaces in s to construct a sentence where each word is a valid dictionary word.
     * Return all such possible sentences.
     * For example, given s = "catsanddog", dict = ["cat", "cats", "and", "sand", "dog"]. A solution is ["cats and dog", "cat sand dog"].*/
    /*Searching in Hashset is expensive*/
    // Recursive solution using DP
    static HashMap<String,List<String>> wordBreak2mp=new HashMap<String,List<String>>();
    public static List<String> wordBreak2(String s, Set<String> dict) {
        if(wordBreak2mp.containsKey(s))	return wordBreak2mp.get(s);
        List<String> words = new ArrayList<String>();
        int len = s.length();
        for (int i = len -1; i >= 0; i--) {
            String last = s.substring(i, len);
            if (dict.contains(last)) {
                if (i == 0) words.add(last);
                else {
                    String remain = s.substring(0, i);
                    List<String> remainSet = wordBreak2(remain, dict);
                    for (String item : remainSet) words.add(item + " " + last);
                }
            }
        }
        wordBreak2mp.put(s,words);
        return words;
    }
    // Recursive solution not using DP but saves space.
    public static List<String> wordBreak22(String s, Set<String> dict) {
        List<String> res=new ArrayList<String>();
        List<String> buf=new ArrayList<String>();
        wordBreak22(res,buf,dict,s,s.length()-1);
        return res;
    }
    public static void wordBreak22(List<String> res,List<String> buf, Set<String> dict, String s, int index){
        if(index==-1){
            StringBuilder sb=new StringBuilder();
            for(String ss:buf){
                sb.append(ss+" ");
            }
            sb.deleteCharAt(sb.length()-1);
            res.add(sb.toString());
            return;
        }
        for(int i=index;i>=0;i--){
            if(dict.contains(s.substring(i,index+1))){
                String ss=s.substring(i,index+1);
                buf.add(0,ss);
                wordBreak22(res,buf,dict,s,i-1);
                buf.remove(0);
            }
        }
    } 
    
    
    
    /*Word Break*/
    /*Given a string s and a dictionary of words dict, determine if s can be segmented into a space-separated sequence of one or more dictionary words.
     * For example, given s = "leetcode", dict = ["leet", "code"].
     * Return true because "leetcode" can be segmented as "leet code".*/
    static Map<String,Boolean> wordBreakcache=new HashMap<String,Boolean>();
    public static boolean wordBreak(String s, Set<String> dict) {
    		if(wordBreakcache.containsKey(s)) return wordBreakcache.get(s);
        int len = s.length();
        boolean flag=false;
        for (int i = len -1; i >= 0; i--)
            if (dict.contains(s.substring(i, len)))
            		if(i==0 || wordBreak(s.substring(0, i), dict)){ 
            			flag=true;
            			break;
            		}
        wordBreakcache.put(s, flag);
        return flag;
    }
    
    
    
    /*Restore IP Addresses*/
    /*Given a string containing only digits, restore it by returning all possible valid IP address combinations.
     * For example: Given "25525511135", return ["255.255.11.135", "255.255.111.35"]. (Order does not matter)*/
    public static List<String> restoreIpAddresses(String s) {
        List<String> res = new ArrayList<String>();
        int len = s.length();
        for(int i = 1; i<4 && i<len-2; i++){
            for(int j = i+1; j<i+4 && j<len-1; j++){
                for(int k = j+1; k<j+4 && k<len; k++){
                    String s1 = s.substring(0,i), s2 = s.substring(i,j), s3 = s.substring(j,k), s4 = s.substring(k);
                    if(restoreIpAddresses_isValid(s1) && restoreIpAddresses_isValid(s2) && restoreIpAddresses_isValid(s3) && restoreIpAddresses_isValid(s4)){
                        res.add(s1+"."+s2+"."+s3+"."+s4);
                    }
                }
            }
        }
        return res;
    }
    public static boolean restoreIpAddresses_isValid(String s){
        if(s.length()>3 || (s.charAt(0)=='0' && s.length()>1) || Integer.parseInt(s)>255)
            return false;
        return true;
    }

    
    
    
    /*Path Sum*/
    /*Given a binary tree and a sum, determine if the tree has a root-to-leaf path such that adding up all the values along the path equals the given sum.*/
    public static boolean hasPathSum(TreeNode root, int sum) {
        if(root==null) return false;
        int new_sum=sum-root.val;
        if(root.left==null && root.right==null && new_sum==0) return true; 
        return hasPathSum(root.right,new_sum) || hasPathSum(root.left,new_sum);
    }
    
    
    
    /*Gray Code*/
    /*The gray code is a binary numeral system where two successive values differ in only one bit.
     * Given a non-negative integer n representing the total number of bits in the code, print the sequence of gray code. 
     * A gray code sequence must begin with 0. For example, given n = 2, return [0,1,3,2]*/
    public static List<Integer> grayCode(int n) {
        ArrayList<Integer> arr = new ArrayList<Integer>();
        arr.add(0);
        for(int i=0;i<n;i++){
            int inc = 1<<i;
            for(int j=arr.size()-1;j>=0;j--){
                arr.add(arr.get(j)+inc);
            }
        }
        return arr;
    }
    
    
    /*Merge Sorted Array */
    /*Given two sorted integer arrays A and B, merge B into A as one sorted array.
     * Note: You may assume that A has enough space (size that is greater or equal to m + n) to hold additional elements from B. 
     * The number of elements initialized in A and B are m and n respectively.*/
    public static void merge(int A[], int m, int B[], int n) {
        for (int idx = m + n - 1; idx >= 0; idx--) {
            if (m <= 0) {
                A[idx] = B[--n]; 
            } else if (n <= 0) {
                break;
            } else if (A[m-1] < B[n-1]) {
                A[idx] = B[--n];
            } else {
                A[idx] = A[--m];
            }
        }
    }
    
    
    
    /*Unique Paths*/
    /*A robot is located at the top-left corner of a m x n grid (marked 'Start' in the diagram below).
     *The robot can only move either down or right at any point in time. 
     *The robot is trying to reach the bottom-right corner of the grid (marked 'Finish' in the diagram below).How many possible unique paths are there?*/
    static int[][] uniquePathsCache;
    public static int uniquePaths(int m, int n) {
    		uniquePathsCache=new int[m][n];
    		return uniquePaths_(m-1,n-1);
    }
    public static int uniquePaths_(int m, int n){
    		if(m==0 || n==0) return 1;
    		if(uniquePathsCache[m][n]!=0) return uniquePathsCache[m][n];
    		int paths=uniquePaths_(m-1,n)+uniquePaths_(m,n-1);
    		uniquePathsCache[m][n]=paths;
    		return paths;
    }

    
    
    /*Unique Paths II */
    /*Follow up for "Unique Paths":
     * Now consider if some obstacles are added to the grids. How many unique paths would there be?
     * An obstacle and empty space is marked as 1 and 0 respectively in the grid.*/
    public static int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int[][] cache=new int[obstacleGrid.length][obstacleGrid[0].length];
        cache[0][0]=obstacleGrid[0][0]==0?1:0;
        for(int i=1;i<cache.length;i++) cache[i][0]=obstacleGrid[i][0]==0?cache[i-1][0]:0;
        for(int i=1;i<obstacleGrid[0].length;i++) cache[0][i]=obstacleGrid[0][i]==0?cache[0][i-1]:0;    
        for(int i=1;i<cache.length;i++){
            for(int j=1;j<obstacleGrid[0].length;j++)
                cache[i][j]=obstacleGrid[i][j]==1?0:(cache[i][j-1]+cache[i-1][j]);
        }
        return cache[cache.length-1][obstacleGrid[0].length-1];
    }
    
    
    /*Sum Root to Leaf Numbers */
    /*Given a binary tree containing digits from 0-9 only, each root-to-leaf path could represent a number.
     * An example is the root-to-leaf path 1->2->3 which represents the number 123.
     * Find the total sum of all root-to-leaf numbers.*/
    /*The root-to-leaf path 1->2 represents the number 12. The root-to-leaf path 1->3 represents the number 13. Return the sum = 12 + 13 = 25.*/
    static StringBuilder sumNumbersListSumbuf=new StringBuilder();
    static int sumNumbersListSum=0;
    public static int sumNumbers(TreeNode root) {
    		sumNumbers_(root);
    		return sumNumbersListSum;
    }
    public static void sumNumbers_(TreeNode root){
    		if(root==null) return;
		sumNumbersListSumbuf.append(root.val);
    		if(root.left==null && root.right==null){
        		if(sumNumbersListSumbuf.length()!=0)	sumNumbersListSum+=Integer.parseInt(sumNumbersListSumbuf.toString());
        }else{
	        	sumNumbers_(root.left);
	        	sumNumbers_(root.right);
        }
    	    sumNumbersListSumbuf.deleteCharAt(sumNumbersListSumbuf.length()-1);
    }

    
    /*Remove Element */
    /*Given an array and a value, remove all instances of that value in place and return the new length.
     * The order of elements can be changed. It doesn't matter what you leave beyond the new length.*/
    public static int removeElement(int[] A, int elem) {
        int len=A.length-1;
        for(int i=0;i<=len;){
            if(A[i]!=elem){ 
                i++;
                continue;
            }
            A[i]=A[len--];
        }
        return len+1;
    }
    
    
    
    /*Compare Version Numbers*/
    /*Compare two version numbers version1 and version1. If version1 > version2 return 1, if version1 < version2 return -1, otherwise return 0.
     * You may assume that the version strings are non-empty and contain only digits and the . character.
     * The . character does not represent a decimal point and is used to separate number sequences.
     * For instance, 2.5 is not "two and a half" or "half way to version three", it is the fifth second-level revision of the second first-level revision.*/
    public static int compareVersion(String version1, String version2) {
        int first=0, second=0, firstptr=0, secondptr=0, firstlen=version1.length(), secondlen=version2.length();
        StringBuilder buf=new StringBuilder();
        while(firstptr!=firstlen || secondptr!=secondlen){       	
        		while(firstptr!=firstlen && version1.charAt(firstptr++)!='.')	buf.append(version1.charAt(firstptr-1));
        		if(buf.length()!=0)	first=Integer.parseInt(buf.toString()); else first=0;
        		buf.delete(0, buf.length());
        		while(secondptr!=secondlen && version2.charAt(secondptr++)!='.')	buf.append(version2.charAt(secondptr-1));
        		if(buf.length()!=0)	second=Integer.parseInt(buf.toString()); else second=0;
        		buf.delete(0, buf.length());
        		if(first>second) return 1;
        		if(first<second) return -1;
        }
        return 0;
    }

    
    
    /*Valid Number*/  /*(regular expression)*/
    /*Validate if a given string is numeric.
     * Some examples: "0" => true, "0.1 " => true, "abc" => false, "1 a" => false, "2e10" => true
     * Note: It is intended for the problem statement to be ambiguous. You should gather all requirements up front before implementing one.*/
    public boolean isNumber(String s) {
        s = s.trim();
        if (s.length() == 0 || s.equals("e")  || s.equals(".")) return false;
        return isFloating(s) || isRegular(s);
     }
     // parses non-floating point literals
     private boolean isRegular(String s) {
        return (s.matches("[+-]?[0-9]+[.]?[0-9]*") || s.matches("[+-]?[0-9]*[.]?[0-9]+"));
     }
      // parses floating point literals as defined here: http://en.cppreference.com/w/cpp/language/floating_literal
     private boolean isFloating(String s) {
        //first one enforces an number after ., the second one enforces a number before .
        // we want to make sure there's at least one number present.
        return (s.matches("[+-]?[0-9]*[.]?[0-9]+[eE][-+]?[0-9]+[f]?") || s.matches("[+-]?[0-9]+[.]?[0-9]*[eE][-+]?[0-9]+[f]?"));
     }
    
    
    /*Min Stack*/	
    /*Design a stack that supports push, pop, top, and retrieving the minimum element in constant time.
     * push(x) -- Push element x onto stack. 
     * pop() -- Removes the element on top of the stack. 
     * top() -- Get the top element. 
     * getMin() -- Retrieve the minimum element in the stack.*/
     Stack<Integer> sk1=new Stack<Integer>();
     Stack<Integer> sk2=new Stack<Integer>();
     public void push(int x) {
         sk2.push(x);
         if(sk1.isEmpty() || sk1.peek()>=x)
             sk1.push(x);
     }

     public void pop() {
         int val=sk2.pop();
         if(val==sk1.peek())
             sk1.pop();
     }

     public int top() {
         return sk2.peek();
     }

     public int getMin() {
         return sk1.peek();
     }

     
     /*Reverse Linked List II*/
     /*Reverse a linked list from position m to n. Do it in-place and in one-pass.*/
     /*For example: given 1->2->3->4->5->NULL, m = 2 and n = 4, return 1->4->3->2->5->NULL.
      * Note: given m, n satisfy the following condition: 1 ≤ m ≤ n ≤ length of list.*/
     public static ListNode reverseBetween(ListNode head, int m, int n) {
    	 	 if(m==n) return head;
         ListNode virtue=new ListNode(0), ptr, follow=virtue, runner, ptr2;
         virtue.next=head;
         for(int i=1;i<m;i++) follow=follow.next;
         runner=follow.next;
         ptr2=runner.next;
         for(int i=0;i<n-m;i++){
     	 	ptr=ptr2;
     	 	ptr2=ptr.next;
        	 	ptr.next=follow.next;
        	 	follow.next=ptr;
         }
         runner.next=ptr2;
         return virtue.next;
     }
     
    
     
     
     /*Path Sum II*/
     /*Given a binary tree and a sum, find all root-to-leaf paths where each path's sum equals the given sum.*/
     static List<Integer> pathSumlist;
     static List<List<Integer>> pathSumlists;
     public static List<List<Integer>> pathSum(TreeNode root, int sum) {
    	 	pathSumlist=new ArrayList<Integer>();
    	 	pathSumlists=new ArrayList<List<Integer>>();
    	 	pathSum_(root,sum);
    	 	return pathSumlists;
     }
     public static void pathSum_(TreeNode root, int sum){
         if(root==null) return;
         int new_sum=sum-root.val;
         pathSumlist.add(root.val);
         if(root.left==null && root.right==null && new_sum==0) pathSumlists.add(new ArrayList<Integer>(pathSumlist));
         else{
        	 	pathSum_(root.right,new_sum);
             pathSum_(root.left,new_sum);
         }
         pathSumlist.remove(pathSumlist.size()-1);
     }
     

     /*Longest Palindromic Substring*/
     /*Given a string S, find the longest palindromic substring in S. 
      * You may assume that the maximum length of S is 1000, and there exists one unique longest palindromic substring.*/
     public static String longestPalindrome(String s) {
         int len=s.length(), maxlen=0, leftind=0, rightind=0;
         boolean[][] cache=new boolean[len][len];
         for(int i=0;i<len;i++){
        	 	for(int j=0;j<=i;j++){
        	 		if(i==j) cache[i][j]=true;
        	 		else if(s.charAt(i)==s.charAt(j)){
        	 			if(j+1==i) cache[i][j]=true;
        	 			else cache[i][j]=cache[i-1][j+1];
        	 		}
        	 		if(cache[i][j]){
        	 			if(maxlen < (i-j+1)){
        	 				leftind=j;
        	 				rightind=i;
        	 				maxlen=i-j+1;
        	 			}
        	 		}
        	 	}
         }
         return s.substring(leftind, rightind+1);
     }
     
     
     /*Excel Sheet Column Number*/
     /*Given a column title as appear in an Excel sheet, return its corresponding column number.*/
     public static int titleToNumber(String s) {
	    	 int result = 0;
	    	 for (int i = 0; i < s.length(); result = result * 26 + (s.charAt(i) - 'A' + 1), i++);
	    	 return result;
     }
     
     
     
     /*Excel Sheet Column Title*/
     /*Given a positive integer, return its corresponding column title as appear in an Excel sheet.*/
     public static String convertToTitle(int n) {
         StringBuilder sb=new StringBuilder();
         for(int left=n;left!=0;left/=26){
             int rem=left%26;
             if(rem==0){ 
                 rem=26;
                 left-=26;
             }
             sb.insert(0,(char)(rem-1+'A'));
         }
         return sb.toString();
     }
     
     
     
     /*Majority Element*/
     /*Given an array of size n, find the majority element. The majority element is the element that appears more than ⌊ n/2 ⌋ times.
      * You may assume that the array is non-empty and the majority element always exist in the array.*/
     public static int majorityElement(int[] num) {
         HashMap<Integer,Integer> map= new HashMap<Integer,Integer>();
         for(int i:num){
             if(map.containsKey(i)){
                 map.put(i,map.get(i)+1);
             }else{
                 map.put(i,1);
             }
             if(map.get(i)>Math.ceil(num.length/2)){
                 return i;
             }
         }
         return -1;
     }
     

     
     
     /*Sqrt(x)*/
     /*Implement int sqrt(int x). Compute and return the square root of x.*/
     public static int sqrt(int x) {
    	    long res = 0;
    	    for (int i = 15; i >= 0; i--)
    	    {
    	        if ((res + (1 << i)) * (res + (1 << i)) <= x)
    	            res += (1 << i);
    	    }
    	    return (int)res;
     }
     // solution 2: using binary search
     public int sqrt2(int x) {
         long max=1<<16,min=0;
         while(true){
             if(min+1==max)
                 return (max*max==x)?(int)max:(int)min;
             long mid=(max+min)/2;
             if(mid*mid<x)
                 min=mid;
             else if(mid*mid>x)
                 max=mid;
             else
                 return (int)mid;
         } 
     }
     
     
     
    /*Sort Colors*/
    /*Given an array with n objects colored red, white or blue, sort them so that objects of the same color are adjacent, 
     * with the colors in the order red, white and blue. Here, we will use the integers 0, 1, and 2 to represent the color red, white, and blue respectively.*/ 
     /*Note: You are not suppose to use the library's sort function for this problem. Could you come up with an one-pass algorithm using only constant space?*/
     public static void sortColors(int[] A) {
    	 	int i=0, j=0, k=0;
    	    for(; k < A.length; k++){
    	    		int c=A[k];
    	    		A[k]=2;
    	        if(c == 0){
    	            A[j++]=1;
    	            A[i++]=0;
    	        }else if (c == 1) 
    	        		A[j++]=1;
    	    }
     }
     
     
     /*String to Integer (atoi)*/
     /*Implement atoi to convert a string to an integer.*/
     public static int atoi(String str) {
         if (str == null || str.length() == 0)
             return 0;
         str = str.trim();
         int flag = 1;
         int i = 0;
         if (str.charAt(0) == '+') {
             flag = 1;
             i++;
         } else if (str.charAt(0) == '-'){
             flag = -1;
             i++;
         }
         double res = 0;
         while (i < str.length() && str.charAt(i) - '0' <= 9 && str.charAt(i) - '0' >= 0) {
             res = res * 10 + (str.charAt(i) - '0');
             i++;
         }
         res *= flag;
         if (res > Integer.MAX_VALUE) {
             res = Integer.MAX_VALUE;
         } else if (res < Integer.MIN_VALUE) {
             res = Integer.MIN_VALUE;
         }
         return (int) res;
     }
     
     
     /*Palindrome Number */
     /*Determine whether an integer is a palindrome. Do this without extra space.*/
     public static boolean isPalindrome(int x) {
    	 	 if(x<0) return false;
         return isPalindrome(x+"");
     }
     public boolean isPalindrome2(int x) {
    	    if(x < 0) {
    	        return false;
    	    }else {
    	        int reversed = 0;
    	        int reminder, quotient = x;
    	        do {
    	            reminder = quotient % 10;
    	            quotient = quotient / 10;
    	            reversed = reversed * 10 + reminder;
    	        }while(quotient != 0);
    	        return reversed == x;
    	    }
    	}
     
     
     
     /*Maximum Depth of Binary Tree*/
     /*Given a binary tree, find its maximum depth. The maximum depth is the number of nodes along the longest path from the root node down to the farthest leaf node.*/
     public static int maxDepth(TreeNode root) {
         if(root==null){
         		return 0;
         }
         return Math.max(maxDepth(root.left),maxDepth(root.right))+1;
     }
     
     
     /*Balanced Binary Tree */
     /* Given a binary tree, determine if it is height-balanced.
      * For this problem, a height-balanced binary tree is defined as a binary tree in which the depth of the 
      * two subtrees of every node never differ by more than 1.*/     
     static boolean isBalanced;
     public static boolean isBalanced(TreeNode root) {
    	 	 isBalanced=true;
    	 	 isBalanced_(root);
         return isBalanced;
     }
     public static int isBalanced_(TreeNode root){ 
    	 	 if(!isBalanced || root==null) return 0;   // this line of code ignores the tree height for the purpose of speed.
    	 	 int leftDepth=isBalanced_(root.left);
    	 	 int rightDepth=isBalanced_(root.right);
		 if(Math.abs(leftDepth-rightDepth)>1) isBalanced=false;
		 return Math.max(leftDepth, rightDepth)+1;
     }
     
     
     /*Count and Say */
     /*The count-and-say sequence is the sequence of integers beginning as follows:
      * 1, 11, 21, 1211, 111221, ... 
      * 1 is read off as "one 1" or 11. 
      * 11 is read off as "two 1s" or 21. 
      * 1 is read off as "one 2, then one 1" or 1211. 
      * Given an integer n, generate the nth sequence.*/
     public static String countAndSay(int n) {
    	 	 if(n==1) return "1";
    	 	 String nToChar=countAndSay(n-1);
    	 	 StringBuilder sb=new StringBuilder();
    	 	 int len=nToChar.length();
    	 	 int off=1;
    	     int count=1;
    	     char cur=nToChar.charAt(0);
    	     while(off!=len){
    	    	 	if(nToChar.charAt(off)==cur){
    	    	 		off++;
    	    	 		count++;
    	    	 	}else{
    	    	 		sb.append(count+""+cur);
    	    	 		cur=nToChar.charAt(off++);
    	    	 		count=1;
    	    	 	}
    	     }
    	     sb.append(count+""+cur);
    	     return sb.toString();
     }

     
     /*Linked List Cycle*/
     /*Given a linked list, determine if it has a cycle in it. Follow up: can you solve it without using extra space?*/
     public static boolean hasCycle(ListNode head) {
         ListNode slow = head;
         ListNode fast = head;
         while(fast != null && fast.next != null)
         {
             slow = slow.next;
             fast = fast.next.next;
             if(slow == fast)
                 return true;
         }
         return false;
     }
     // Solution 2: the best, although the list is changed.
	 public boolean hasCycle2(ListNode head) 
	 {
	    	 ListNode Virtue = new ListNode(0), tmp;
	    	 Virtue.next = head;
	    	 tmp=head;
	    	 while(tmp != null)
	    	 {
	    	     ListNode tmp2=tmp.next;
	    	     if(tmp2==Virtue) return true;
	    	     tmp.next=Virtue;
	    	     tmp=tmp2;
	    	 }
	    	 return false;
	 }
     

     
     /*Linked List Cycle II */
     /*Given a linked list, return the node where the cycle begins. If there is no cycle, return null.*/
     /*Solution 1: use hashset*/
     public static ListNode detectCycle(ListNode head) {
    	    HashSet<ListNode> nodes = new HashSet<ListNode> ();
    	    ListNode current = head;
    	    while(current != null){
    	        if(nodes.contains(current))
    	            return current;
    	        nodes.add(current);
    	        current = current.next;
    	    }
    	    return null;
     }
     /*Solution 2: use slow & fast ptrs*/
     /*famous known problem Hare and Tortoise:	https://oj.leetcode.com/discuss/396/is-there-any-better-answer-for-the-linked-list-cycle-ii*/
     public static ListNode detectCycle2(ListNode head) {
	     ListNode slow = head;
	     ListNode fast = head;
	     Boolean test=true;
	     while(fast != null && fast.next != null && test)
	     {
	         slow = slow.next;
	         fast = fast.next.next;
	         if(slow == fast) test=false;     
	     }
	     if(test)	return null;
	     else{
    	 		slow=head;
    	 		while(slow!=fast){
    	 			slow=slow.next;
    	 			fast=fast.next;
    	 		}
    	 		return slow;
	     }
     }
     // Solution 3: the best, although the list is changed.
	 public ListNode detectCycle3(ListNode head) 
	 {
         ListNode Virtue = new ListNode(0), tmp;
    	    	 Virtue.next = head;
    	    	 tmp=head;
    	    	 while(tmp != null)
    	    	 {
    	    	     ListNode tmp2=tmp.next;
    	    	     if(tmp2==Virtue) 
    	    	        return tmp;
    	    	     tmp.next=Virtue;
    	    	     tmp=tmp2;
    	    	 }
    	    	 return null;
    }
     
     
     
      /*Factorial Trailing Zeroes*/
      /*Given an integer n, return the number of trailing zeroes in n!.
       * Note: Your solution should be in logarithmic time complexity.*/
      /*Explanation of the solution  https://oj.leetcode.com/discuss/19855/my-one-line-solutions-in-3-languages*/
      public static int trailingZeroes(int n) {
    	  	return n == 0 ? 0 : n / 5 + trailingZeroes(n / 5);
      }
      
      
      
      /*Intersection of Two Linked Lists*/
      /*Write a program to find the node at which the intersection of two singly linked lists begins.*/
      /* If the two linked lists have no intersection at all, return null. 
       * The linked lists must retain their original structure after the function returns.
       * You may assume there are no cycles anywhere in the entire linked structure.
       * Your code should preferably run in O(n) time and use only O(1) memory.*/
      public static ListNode getIntersectionNode(ListNode headA, ListNode headB) {
    	  		int lenA=getIntersectionNodeGetLen(headA);
    	  		int lenB=getIntersectionNodeGetLen(headB);
    	  		if(lenA>lenB) for(int i=0;i<lenA-lenB;headA=headA.next,i++);
    	  		if(lenA<lenB) for(int i=0;i<lenB-lenA;headB=headB.next,i++);
    	  		while(headA!=null && headA!=headB){
    	  			headA=headA.next;
    	  			headB=headB.next;
    	  		}
    	  		return headA;
      }
      public static int getIntersectionNodeGetLen(ListNode head){
    	  	    int i=0;
    	  	    while(head!=null){ 
    	  	    		head=head.next;
    	  	    		i++;
    	  	    }
    	  	    return i;
      }
      
      
      /*Binary Tree Preorder Traversal*/
      /*Given a binary tree, return the preorder traversal of its nodes' values.*/
      /*use iterative & stack*//*!!!!!!!!!!!!!!!!*/
      public static List<Integer> preorderTraversal(TreeNode root){
          List<Integer> res=new ArrayList<Integer>();
          Stack<TreeNode> sk=new Stack<TreeNode>();
          sk.push(root);
          while(!sk.isEmpty()){
              TreeNode node=sk.pop();
              while(node!=null){
                   res.add(node.val);
                   sk.push(node.right);
                   node=node.left;
              }
          }
          return res;
      }     
      public List<Integer> preorderTraversal2(TreeNode root) {
          List<Integer> res=new ArrayList<Integer>();
          Stack<TreeNode> sk=new Stack<TreeNode>();
          while(!sk.isEmpty()||root!=null){
              while(root!=null){ 
                  res.add(root.val);
                  sk.push(root);
                  root=root.left;
              }
              root=sk.pop().right;
          }
          return res;
      }
      /*Binary Tree Postorder Traversal */
      /*Given a binary tree, return the postorder traversal of its nodes' values.*/
      /*use iterative & stack*//*!!!!!!!!!!!!!!!!*/
      public List<Integer> postorderTraversal(TreeNode root) {
          List<Integer> res=new ArrayList<Integer>();
          Stack<TreeNode> sk=new Stack<TreeNode>();
          sk.push(root);
          while(!sk.isEmpty()){
              TreeNode node=sk.pop();
              while(node!=null){
                   res.add(0,node.val);
                   sk.push(node.left);
                   node=node.right;
              }
          }
          return res;
      }
      public List<Integer> postorderTraversal2(TreeNode root) {
          List<Integer> res=new ArrayList<Integer>();
          Stack<TreeNode> sk=new Stack<TreeNode>();
          while(!sk.isEmpty()||root!=null){
              while(root!=null){ 
                  res.add(0,root.val);
                  sk.push(root);
                  root=root.right;
              }
              root=sk.pop().left;
          }
          return res;
      }
      /*Binary Tree Inorder Traversal */
      /*Given a binary tree, return the inorder traversal of its nodes' values.*/
      /*use recursive*/
      public static List<Integer> inorderTraversal(TreeNode root) {
          List<Integer> inorderlist=new ArrayList<Integer>();
      		inorderTraversal(root,inorderlist);
          return inorderlist;
      }
      public static void inorderTraversal(TreeNode root,List<Integer> list){
      		if(root==null) return;
      		inorderTraversal(root.left,list);
      		list.add(root.val);
      		inorderTraversal(root.right,list);
      }
      /*use iterative & stack*//*!!!!!!!!!!!!!!!!*/
      public static List<Integer> inorderTraversal2(TreeNode root) {
          List<Integer> res=new ArrayList<Integer>();
          Stack<TreeNode> sk=new Stack<TreeNode>();
          while(!sk.isEmpty()||root!=null){
              while(root!=null){ 
                  sk.push(root);
                  root=root.left;
              }
              res.add(sk.peek().val);
              root=sk.pop().right;
          }
          return res;
      }
      
      
      /*Same Tree */
      /*Given two binary trees, write a function to check if they are equal or not.
       * Two binary trees are considered equal if they are structurally identical and the nodes have the same value.*/
      public static boolean isSameTree(TreeNode p, TreeNode q) {
    	      if(p==null && q==null) return true;
    	      if(p==null || q==null) return false;
          return p.val==q.val && isSameTree(p.left,q.left) && isSameTree(p.right,q.right);
      }
      
      
      
      /*Pascal's Triangle from leetcode*/
      /*Given numRows, generate the first numRows of Pascal's triangle.*/
      public static List<List<Integer>> generate(int numRows) {
      		List<List<Integer>> lists = new ArrayList<List<Integer>>();
      		List<Integer> list;
      		int pre=0;
      		for(int i = 0; i < numRows; i++){
      			list = new ArrayList<Integer>();
      			if(i!=0){
      				for(Integer a: lists.get(i-1)){
      					list.add(a+pre);
      					pre=a;
      				}
      				pre=0;
      			}
      			list.add(1);
      			lists.add(list);
      		}
      		return lists;
      }
      

      /*Pascal's Triangle II */
      /*Given an index k, return the kth row of the Pascal's triangle.
       * For example, given k = 3, return [1,3,3,1].*/
      public static List<Integer> getRow(int rowIndex) {
    		List<List<Integer>> lists = new ArrayList<List<Integer>>();
    		List<Integer> list;
    		int pre=0;
    		for(int i = 0; i <= rowIndex; i++){
    			list = new ArrayList<Integer>();
    			if(i!=0){
    				for(Integer a: lists.get(i-1)){
    					list.add(a+pre);
    					pre=a;
    				}
    				pre=0;
    			}
    			list.add(1);
    			lists.add(list);
    		}
    		return lists.get(rowIndex);
      }
      
      
      /*Remove Duplicates from Sorted List */
      /*Given a sorted linked list, delete all duplicates such that each element appear only once.*/
      public static ListNode deleteDuplicatesL(ListNode head) {
    	  		if(head==null) return null;
    	  		ListNode res=head;
    	  		while(head.next!=null){
    	  			if(head.val==head.next.val) head.next=head.next.next;
    	  			else head=head.next;
    	  		}
    	  		return res;
      }
      
      
      /*Symmetric Tree */
      /*Given a binary tree, check whether it is a mirror of itself (i.e, symmetric around its center).*/
      public static boolean isSymmetric(TreeNode root) {
    	  	  if(root==null) return true;
    	  	  return isSymmetric(root.left,root.right);
      }
      public static boolean isSymmetric(TreeNode p, TreeNode q) {
	      if(p==null && q==null) return true;
	      if(p==null || q==null) return false;
	      return p.val==q.val && isSymmetric(p.left,q.right) && isSymmetric(p.right,q.left);
      }
      
      
      /*Container With Most Water */
      /*Given n non-negative integers a1, a2, ..., an, where each represents a point at coordinate (i, ai). n vertical lines are drawn such that the two endpoints of line i is at (i, ai) and (i, 0). 
       * Find two lines, which together with x-axis forms a container, such that the container contains the most water. Note: You may not slant the container.*/
      /*proof of this algorithm (idea) : https://oj.leetcode.com/discuss/1074/anyone-who-has-a-o-n-algorithm*/
      public static int maxArea(int[] height) {
          int max = 0, left = 0, right = height.length-1;
          while(left<right){
              int h = Math.min(height[left],height[right]);
              max=Math.max(max,h*(right-left));
              if(height[left]>height[right])
                  right--;
              else
                  left++;
          }
          return max;
      }
      
      /*Trapping Rain Water from leetcode*/
      /*Given n non-negative integers representing an elevation map where the width of each bar is 1, compute how much water it is able to trap after raining.*/
      /*method 1*/
      public static int trap(int[] A) {
          int height=0, left=0, right=A.length-1, area=0;
          while(left<=right){
              int curheight=Math.min(A[left],A[right]);
              if(curheight>=height){
                  area+=(curheight-height)*(right-left+1);
                  height=curheight;
              }
              area-=curheight;
              if(A[left]>A[right])
                  right--;
              else
                  left++;
          }
          return area;
      }
      /*method 2*/
      public static int trap2(int[] A) {
      		int left=0,right=A.length-1,area=0, secHeight=0;
      		while(left<right){
      			if(A[left]<A[right]){
      				secHeight=Math.max(A[left],secHeight);
      				area+= secHeight-A[left++];			
      			}else{
      				secHeight=Math.max(A[right],secHeight);
      				area+= secHeight-A[right--];
      			}
      		}
      		return area;
      }
      
      
     /*4Sum*/
      /*Given an array S of n integers, are there elements a, b, c, and d in S such that a + b + c + d = target? 
       * Find all unique quadruplets in the array which gives the sum of target.
       * Note: Elements in a quadruplet (a,b,c,d) must be in non-descending order. (ie, a ≤ b ≤ c ≤ d)
       * The solution set must not contain duplicate quadruplets.*/
      public static List<List<Integer>> fourSum(int[] num, int target) {
          List<List<Integer>> res = new ArrayList<List<Integer>>();
          Arrays.sort(num);
          int last=num.length-1;
          for(int j=0;j<last-2;){
	          for(int i=j+1; i<last-1;){
	        	  		int diff=target-num[j]-num[i];
	          		int l=i+1;
	          		int r=last;
	          		while(l<r){
	          			if(num[l]+num[r]>diff){
	          				r--;
	          			}else if(num[l]+num[r]<diff){
	          				l++;
	          			}else{
	          				ArrayList<Integer> ptr=new ArrayList<Integer>();
	          				ptr.add(num[j]);
	          				ptr.add(num[i]);
	          				ptr.add(num[l]);
	          				ptr.add(num[r]);
	          				res.add(ptr);
	          				do{l++;}while(l<r && num[l]==num[l-1]);
	          				do{r--;}while(l<r && num[r]==num[r+1]);
	          			}
	          		}
	          		do{i++;}while(i<last-1 && num[i]==num[i-1]);
	          }
        		 do{j++;}while(j<last-2 && num[j]==num[j-1]);
          }
          return res;
      }
      
      
      
      /*3Sum*/
      /*Given an array S of n integers, are there elements a, b, c in S such that a + b + c = 0? Find all unique triplets in the array which gives the sum of zero.*/
      public static List<List<Integer>> threeSum(int[] num) {
          List<List<Integer>> res = new ArrayList<List<Integer>>();
          Arrays.sort(num);
          int last=num.length-1;
          for(int i=0; i<last-1;){
          		int diff=0-num[i];
          		int l=i+1;
          		int r=last;
          		while(l<r){
          			if(num[l]+num[r]>diff){
          				r--;
          			}else if(num[l]+num[r]<diff){
          				l++;
          			}else{
          				ArrayList<Integer> ptr=new ArrayList<Integer>();
          				ptr.add(num[i]);
          				ptr.add(num[l]);
          				ptr.add(num[r]);
          				res.add(ptr);
          				do{l++;}while(l<r && num[l]==num[l-1]);
          				do{r--;}while(l<r && num[r]==num[r+1]);
          			}
          		}
          		do{i++;}while(i<last-1 && num[i]==num[i-1]);
          }
          return res;
      }
      
      /*ZigZag Conversion*/
      /*Write the code that will take a string and make this conversion given a number of rows:*/
      public static String convert(String s, int nRows) {
    	    char[] c = s.toCharArray();
    	    int len = c.length;
    	    StringBuffer[] sb = new StringBuffer[nRows];
    	    for (int i = 0; i < sb.length; i++) sb[i] = new StringBuffer();
    	    int i = 0;
    	    while (i < len) {
    	        for (int idx = 0; idx < nRows && i < len; idx++) // vertically down
    	            sb[idx].append(c[i++]);
    	        for (int idx = nRows-2; idx >= 1 && i < len; idx--) // obliquely up
    	            sb[idx].append(c[i++]);
    	    }
    	    for (int idx = 1; idx < sb.length; idx++)
    	        sb[0].append(sb[idx]);
    	    return sb[0].toString();
    	}
      
      
	  /*Swap Nodes in Pairs */  
	  /*Given a linked list, swap every two adjacent nodes and return its head.*/
      /*Your algorithm should use only constant space. You may not modify the values in the list, only nodes itself can be changed.*/
      // Catch: have swapping partition.
      public static ListNode swapPairs(ListNode head) {
          ListNode virtue=new ListNode(0), ptr=virtue;
          virtue.next=head;
          while(ptr.next!=null&&ptr.next.next!=null){
              ListNode start=ptr.next, end=start.next.next;
              start.next.next=start;
              ptr.next=start.next;
              start.next=end;
              ptr=start;
          }
          return virtue.next;
      }
      
      
      
      /*Permutation Sequence */
      /*The set [1,2,3,…,n] contains a total of n! unique permutations. Given n and k, return the kth permutation sequence by listing and labeling all of the permutations in order,*/
      /*Explanation of the algorithm: https://oj.leetcode.com/discuss/16064/an-iterative-solution-for-reference*/
      // Catch: k-- to be aligned with cs counting tradition 0,1,2,3......
      public static String getPermutation(int n, int k) {
    	    LinkedList<Integer> list = new LinkedList<Integer>();
    	    for (int i = 1; i <= n; i++) list.add(i);
    	    int fact = 1;
    	    for (int i = 2; i <= n; i++) fact *= i; // factorial
    	    StringBuilder strBuilder = new StringBuilder();
    	    for (k--; n > 0; n--) {
    	        fact /= n;
    	        strBuilder.append(list.remove(k / fact));
    	        k %= fact;
    	    }
    	    return strBuilder.toString();
      }

      
      
      /*Search for a Range*/
      /*Given a sorted array of integers, find the starting and ending position of a given target value.
       * Your algorithm's runtime complexity must be in the order of O(log n).
       * If the target is not found in the array, return [-1, -1].*/
      public static int[] searchRange(int[] A, int target){
    	  	  int len=A.length;
    	  	  int a=0,b=len-1,aa=-1,bb=-1;
          while(a<=b){
        	  	int mid=(a+b)/2;
        	  	if(A[mid]<target) a=mid+1;
        	  	else if(A[mid]>target) b=mid-1;
        	  	else{
        	  		aa=mid; bb=mid;
        	  		while(aa-1>=0&&A[aa-1]==target) aa--;
        	  		while(bb+1<len&&A[bb+1]==target) bb++;
        	  		break;
        	  	}
          }
	  	  return new int[]{aa,bb};
      }
      
      
      
      
      /*Divide Two Integers*/
      /*Divide two integers without using multiplication, division and mod operator.*/
      /*If it is overflow, return MAX_INT.*/
      public static int divide(int dividend, int divisor) {
          int sign=((dividend<0)^(divisor)<0)?-1:1;
          long dividend_=Math.abs((long)dividend), divisor_=Math.abs((long)divisor);
          long quotient=0;
          for(long t=0, i=31;i>=0;i--){
        	  	if((t+(divisor_<<i))<=dividend_){
        	  		t+=(divisor_<<i);
        	  		quotient^=(long)1<<i;
        	  	}
          }
          if(sign<0) quotient=-quotient;
          if(quotient>Integer.MAX_VALUE) quotient=Integer.MAX_VALUE;
          if(quotient<Integer.MIN_VALUE) quotient=Integer.MIN_VALUE;
          return (int) quotient;
      }
      
      
      /*Triangle*/
      /*Given a triangle, find the minimum path sum from top to bottom. Each step you may move to adjacent numbers on the row below.*/
      public static int minimumTotal(List<List<Integer>> triangle) {
          List<Integer> bottomlist = triangle.get(triangle.size()-1);
          int[] map=new int[triangle.get(triangle.size()-1).size()];
          for(int i=0;i<bottomlist.size();i++)
              map[i]=bottomlist.get(i).intValue();
          for(int i=triangle.size()-2;i>=0;i--){
              for(int j=0;j<triangle.get(i).size();j++)
                  map[j]=Math.min(map[j],map[j+1])+triangle.get(i).get(j);
          }
          return map[0];
      }
      
      /*Permutations, Given a collection of numbers, return all possible permutations.*/
      /*Permutations II, Given a collection of numbers that might contain duplicates, return all possible unique permutations.*/
      // Catch: adding cache would make it fail, I am an idiot.
      public static List<List<Integer>> permuteUnique(int[] num) {
          Arrays.sort(num);
          ArrayList<Integer> list=new ArrayList<Integer>();
          for(int i:num){
              list.add(i);
          }
          return permuteUnique(list);
      }
      public static List<List<Integer>> permuteUnique(List<Integer> buf){
          List<List<Integer>> res=new ArrayList<List<Integer>>();
          if(buf.isEmpty()){
              res.add(new ArrayList<Integer>());
          }
          for(int i=0;i<buf.size();i++){
              if(i!=0&&buf.get(i).intValue()==buf.get(i-1).intValue())
                  continue;
              int tmp = buf.remove(i);
              List<List<Integer>> lists=permuteUnique(buf);
              for(List<Integer> list:lists){
                  List<Integer> newlist=new ArrayList<Integer>(list);
                  newlist.add(tmp);
                  res.add(newlist);
              }
              buf.add(i,tmp);
          }
          return res;
      }
      
      
      /*Subsets */
      /*Given a set of distinct integers, S, return all possible subsets.
       * Note: elements in a subset must be in non-descending order; the solution set must not contain duplicate subsets.*/
      /*Solution1*/
      public static List<List<Integer>> subsets(int[] S) {
     	 	Arrays.sort(S);
            return subsets(S.length-1,S);
      }
      public static List<List<Integer>> subsets(int index,int[] S){
     	 	List<List<Integer>> lists=new ArrayList<List<Integer>>();
     	 	if(index==-1){
     	 		lists.add(new ArrayList<Integer>());
     	 		return lists;
     	 	}
     	 	List<List<Integer>> oldlists=subsets(index-1,S);
     	 	lists.addAll(oldlists);
     	 	List<Integer> newlist;
     	 	int c=S[index];
     	 	for(List<Integer> list:oldlists){
     	 		newlist=new ArrayList<Integer>(list);
     	 		newlist.add(c);
     	 		lists.add(newlist);
     	 	}
     	 	return lists;
      }  
      /*Solution2*/
      public static List<List<Integer>> subsets2(int[] S)
      {
		 List<List<Integer>> res = new ArrayList<List<Integer>>();
	 	 List<Integer> buff = new ArrayList<Integer>();
	 	 Arrays.sort(S);
		 subsets2(S, S.length-1, res, buff);
		 return res;
      }
	  public static void subsets2(int[] S, int k, List<List<Integer>> res, List<Integer> buff)
	  {
		 if(k == -1)
				res.add(new ArrayList<Integer>(buff));
		 else
		 {
		    	subsets2(S, k-1, res, buff);
		    buff.add(0, S[k]);
		    subsets2(S, k-1, res, buff);
		    buff.remove(0);
		 }
	  }
      /*Subsets II*/
      /*Given a collection of integers that might contain duplicates, S, return all possible subsets.
       * Note: Elements in a subset must be in non-descending order. The solution set must not contain duplicate subsets.*/
       public static List<List<Integer>> subsetsWithDup(int[] num) {
           Arrays.sort(num);
           return subsetsWithDup(num,num.length-1);
       }
       public static List<List<Integer>> subsetsWithDup(int[] num, int index){
      	 	List<List<Integer>> lists=new ArrayList<List<Integer>>();
      	 	if(index==-1)	lists.add(new ArrayList<Integer>());
      	 	else{
      	 		int count=1;
      	 		int c=num[index];
      	 		while(index>0&&num[index-1]==c){ 
      	 			index--;
      	 			count++;
      	 		}
      	 		List<List<Integer>> oldlists=subsetsWithDup(num,index-1);
      	 		for(List<Integer> list: oldlists){
      	 			lists.add(new ArrayList<Integer>(list));
      	 			for(int i=0;i<count;i++){
      	 				list.add(c);
      	 				lists.add(new ArrayList<Integer>(list));
      	 			}
      	 		}
      	 	}
      	 	return lists;
       }
      
      
      
      /*Multiply Strings */
      /*Given two numbers represented as strings, return multiplication of the numbers as a string.
       * Note: The numbers can be arbitrarily large and are non-negative.*/
      public static String multiply(String num1, String num2) {
          StringBuilder result=new StringBuilder();;
          int carry = 0;
          if(num1.length()==1&&num1.charAt(0)=='0'||num2.length()==1&&num2.charAt(0)=='0')
              return "0";
          for(int i = 0 ; i < num1.length()+num2.length()-1; i++){
              int sum = carry;
              for(int j = (i-num2.length()+1<=0)?0:(i-num2.length()+1); j <= i && j<num1.length(); j++){
	              sum += (num1.charAt(num1.length()-j-1)-'0')*(num2.charAt(num2.length()-1-i+j)-'0');
              }
              carry = sum/10;
              result.insert(0,(char)(sum%10+'0'));
          }
          if(carry>0) result.insert(0,(char)(carry+'0'));
          return result.toString();
      }
      public static void multiply_test(){
    	  		System.out.println(multiply("123","456"));
      }
      
      
      /*Binary Tree Zigzag Level Order Traversal */
      /*Given a binary tree, return the zigzag level order traversal of its nodes' values. 
       * (ie, from left to right, then right to left for the next level and alternate between).*/
      public static List<List<Integer>> zigzagLevelOrder(TreeNode root) {
    	  	  List<List<Integer>> lists=new ArrayList<List<Integer>>();
    	  	  if(root==null) return lists;
          Queue<TreeNode> q=new LinkedList<TreeNode>();
          q.offer(root);
          List<Integer> list=new ArrayList<Integer>();
          int cur=1, next=0;
          Boolean forward=true;
          while(!q.isEmpty()){
        	  	TreeNode tn=q.poll();
        	  	if(forward) list.add(tn.val);
        	  	else list.add(0, tn.val);
        	  	cur--;
        	  	if(tn.left!=null){ 
        	  		q.add(tn.left);
        	  		next++;
        	  	}
        	  	if(tn.right!=null){ 
        	  		q.add(tn.right);
        	  		next++;
        	  	}
        	  	if(cur==0){
        	  		cur=next;
        	  		next=0;
        	  		lists.add(list);
        	  		list=new ArrayList<Integer>();
        	  		if(forward) forward=false;
        	  		else forward=true;
        	  	}
          }
          return lists;
      }
      
      
      /*Simplify Path*/ 
      /*Given an absolute path for a file (Unix-style), simplify it.*/
      /*For example, path = "/home/", => "/home", path = "/a/./b/../../c/", => "/c"*/
      /*"I think It is better to use deque since it provides the convenience to output elements from both sides."*/
      public static String simplifyPath(String path) {
          if (path==null||path.length()==0) return "";
          String[] paths = path.split("/+");
          Deque<String> queue = new LinkedList<String>();
          for (String s:paths){
              if (s.equals("..")){
                  if (!queue.isEmpty())
                      queue.pollLast();
              }
              else if (s.equals(".")||s.equals(""))
                  continue;
              else queue.offer(s);
          }
          StringBuilder result=new StringBuilder("");
          if (queue.isEmpty()) result.append("/");
          while (!queue.isEmpty())	result.append("/").append(queue.poll());//Faster than string manipulation. 
          return result.toString();
      }
      
      
      
      /*Plus One*/
      /*Given a non-negative number represented as an array of digits, plus one to the number.*/
      /*The digits are stored such that the most significant digit is at the head of the list.*/
      public static int[] plusOne(int[] digits) {
          int len=digits.length;
          int i=len-1, sum=++digits[i];
          int[] res=digits;
          while(sum==10){
        	  	digits[i--]=0;
        	  	if(i==-1) break;
        	  	sum=++digits[i];
          }
          if(i==-1){
        	  	res=new int[len+1];
        	  	res[0]=1;
        	  	for(i=0;i<len;i++) res[i+1]=digits[i];
          }
          return res;
      }
      
      
      /*Add Binary*/
      /*Given two binary strings, return their sum (also a binary string).*/
      public static String addBinary(String a, String b) {
    	    int m = a.length();
    	    int n = b.length();
    	    int carry = 0;
    	    String res = "";
    	    // the final length of the result depends on the bigger length between a and b, 
    	    // (also the value of carry, if carry = 1, add "1" at the head of result, otherwise)
    	    int maxLen = Math.max(m, n);
    	    for (int i = 0; i < maxLen; i++) {
    	        // start from last char of a and b
    	        // notice that left side is int and right side is char
    	        // so we need to  minus the decimal value of '0'
    	        int p = i < m ? a.charAt(m - 1 - i) - '0' : 0;
    	        int q = i < n ? b.charAt(n - 1 - i) - '0' : 0;
    	        int tmp = p + q + carry;
    	        carry = tmp / 2;
    	        res = tmp % 2 + res;
    	    }
    	    return (carry == 0) ? res : "1" + res;
      }
      


      
      /*Spiral Matrix */
      /*Given a matrix of m x n elements (m rows, n columns), return all elements of the matrix in spiral order.*/
      public static List<Integer> spiralOrder(int[][] matrix) {
          List<Integer> list=new ArrayList<Integer>();
	      if(matrix.length==0) return list;
          int lvl=0;
          int lvlcap=(int)Math.min(Math.ceil((double)matrix.length/2), Math.ceil((double)matrix[0].length/2));
          while(lvl<lvlcap){
      	      int i=lvl, j=lvl;
      	      if(lvl==matrix.length-1-lvl) for(;i<matrix[0].length-lvl;i++) list.add(matrix[j][i]);
	          else if(lvl==matrix[0].length-1-lvl) for(;j<matrix.length-lvl;j++) list.add(matrix[j][i]);
	          else{
		          for(;i<matrix[0].length-lvl-1;i++) list.add(matrix[j][i]);
		          for(;j<matrix.length-lvl-1;j++) list.add(matrix[j][i]);
		          for(;i>lvl;i--) list.add(matrix[j][i]);
		          for(;j>lvl;j--) list.add(matrix[j][i]);
	          }
	          lvl++;
          }
          return list;
      }
      
      
      /*Spiral Matrix II*/
      /*Given an integer n, generate a square matrix filled with elements from 1 to n2 in spiral order.*/
      public static int[][] generateMatrix(int n) {
          int [][] matrix=new int[n][n];
          int lvl=(int)Math.ceil((double)n/2);
          generateMatrix_helper(matrix,n-1,0,1,lvl);
          return matrix;
      }
      public static void generateMatrix_helper(int[][] matrix, int n, int curlevel, int start,int lvl){
      		if(curlevel<lvl){
  	    		int i=curlevel;
  	    		int j=curlevel;
  	    		for(;j<n-curlevel;j++){
  	    			matrix[i][j]=start++;
  	    		}
  	    		for(;i<n-curlevel;i++){
  	    			matrix[i][j]=start++;
  	    		}
  	    		for(;j>curlevel;j--){
  	    			matrix[i][j]=start++;
  	    		}
  	    		for(;i>curlevel;i--){
  	    			matrix[i][j]=start++;
  	    		}
  	    		if(n-curlevel==curlevel){
  	    			matrix[i][j]=start++;
  	    		}
  	    		generateMatrix_helper(matrix,n,curlevel+1,start,lvl);
      		}
      }
      public static void generateMatrix_test(){
      		int[][] result= generateMatrix(3);
      		for(int i=0;i<result.length;i++){
      			for(int j=0;j<result.length;j++){
      				System.out.print(result[i][j]+"	");
      			}
      			System.out.println("");
      		}
      }
      
      
      /*Maximum Subarray*/
      /*Find the contiguous subarray within an array (containing at least one number) which has the largest sum.
       * For example, given the array [−2,1,−3,4,−1,2,1,−5,4], the contiguous subarray [4,−1,2,1] has the largest sum = 6.*/
      public static int maxSubArray(int[] A) {
          if (A == null || A.length == 0) return 0;
          int max = A[0], result = A[0];
          for (int i = 1; i < A.length; i++) {
              max = Math.max(max + A[i], A[i]);
              if (max > result) result = max;
          }
          return result;
      }
      
      /*Maximum Product Subarray*/
      /*Find the contiguous subarray within an array (containing at lpeast one number) which has the largest product.
       * For example, given the array [2,3,-2,4],the contiguous subarray [2,3] has the largest product = 6.*/
      public static int maxProduct(int[] A) {
          if (A == null || A.length == 0) return 0;
          int max = A[0], min = A[0], result = A[0];
          for (int i = 1; i < A.length; i++) {
              int temp_max = max;
              max = Math.max(Math.max(temp_max * A[i], min * A[i]), A[i]);
              min = Math.min(Math.min(temp_max * A[i], min * A[i]), A[i]);
              if (max > result) result = max;
          }
          return result;
      }
      
      
      /*Anagrams*/
      /*Given an array of strings, return all groups of strings that are anagrams.*/
      public static List<String> anagrams(String[] strs) {
    	  	  List<String> res=new ArrayList<String>();
          HashMap<String,List<String>>map=new HashMap<String, List<String>>();
          for(String s:strs){
        	    char[] c=s.toCharArray();
        	  	Arrays.sort(c);
        	  	String ss=String.valueOf(c);
        	  	if(map.containsKey(ss)) map.get(ss).add(s);
        	  	else{ 
        	  		List<String> list=new ArrayList<String>();
        	  		list.add(s);
        	  		map.put(ss, list);
        	  	}
          }
          for(String s:map.keySet())	if(map.get(s).size()!=1)	res.addAll(map.get(s));
          return res;
      }
      public static void anagrams_test(){
    	  	for(String s:anagrams(new String[]{"ab","ba"})) System.out.println(s);
      }
      
      
      
      /*Merge k Sorted Lists */
      /*Merge k sorted linked lists and return it as one sorted list. Analyze and describe its complexity.*/
      public static ListNode mergeKLists(List<ListNode> lists) {
          ListNode virtue=new ListNode(0), ptr=virtue;
          ArrayList<Integer> map=new ArrayList<Integer>();
          for(ListNode list:lists){	
        	  	while(list!=null){ 
        	  		map.add(list.val);
        	  		list=list.next;
        	  	}
          }
        	  int[] list= new int[map.size()];
        	  for(int i=0;i<map.size();i++) list[i]=map.get(i).intValue();
        	  Arrays.sort(list);
        	  for(Integer i:list){ 
        		  virtue.next=new ListNode(i.intValue());
        		  virtue=virtue.next;
        	  }
        	  return ptr.next;
      }
      
      
      /*First Missing Positive */
      /*Given an unsorted integer array, find the first missing positive integer.*/
      /*Your algorithm should run in O(n) time and uses constant space.*/
      // Catch: 各回各家，各找各妈；negative, zero, greater than A.length, repeating values are homeless......
      public static int firstMissingPositive(int[] A) {
    	  	for(int i=0;i<A.length;i++){
    	  		int val=A[i];
    	  		while(val<=A.length&&val>0&&A[val-1]!=val){
    	  			int tem=A[val-1];
    	  			A[val-1]=A[i];
    	  			A[i]=tem;
    	  			val=tem;
    	  		}
    	  	}
    	  	for(int i=0;i<A.length;i++){
    	  		if(A[i]!=i+1) return i+1;
    	  	}
    	  	return A.length+1;
      }
      
      
      
      /*Binary Tree Maximum Path Sum*/
      /*Given a binary tree, find the maximum path sum. The path may start and end at any node in the tree.*/
      static int maxPathSumRes=Integer.MIN_VALUE;
      public static int maxPathSum(TreeNode root){
    	    maxPathSum_(root);
    	  	return maxPathSumRes;
      }
      public static int maxPathSum_(TreeNode root) {
          if(root==null) return 0;
          int l=maxPathSum_(root.left);
          int r=maxPathSum_(root.right);
          if(l<0) l=0;
          if(r<0) r=0;
          if(l+r+root.val>maxPathSumRes) maxPathSumRes=l+r+root.val;
          return root.val+Math.max(l, r);
      }

      

       
      
      /*Jump Game*/
      /*Given an array of non-negative integers, you are initially positioned at the first index of the array.
       * Each element in the array represents your maximum jump length at that position.
       * Determine if you are able to reach the last index.*/
      public static boolean canJump(int[] A) {
          int maxJump = 1;
          for (int i = 0; i < A.length - 1; i++) {
              maxJump = Math.max(maxJump - 1, A[i]);
              if (maxJump == 0) return false;
          }
          return true;
      }
      public static boolean canJump2(int[] A){
    	  	 int cur=0;
    	  	 for(int i=0;i<A.length-1;i++){
    	  		 cur=Math.max(cur, i+A[i]);
    	  		 if(cur==i) return false;
    	  	 }
    	  	 return true;
      }
      
      
      /*Jump Game II*/
      /*Given an array of non-negative integers, you are initially positioned at the first index of the array.
       * Each element in the array represents your maximum jump length at that position. Your goal is to reach the last index in the minimum number of jumps.*/
      /*Below code has the assumption that it is able to reach the last index somehow.*/
      public static int jump(int[] A) {
          int ret = 0;
          int last = 0;
          int curr = 0;
          for (int i = 0; i < A.length-1; ++i) {
              if (i > last) {
                  last = curr;
                  ++ret;
              }
              curr = Math.max(curr, i+A[i]);
          }
          return ret;
      }
      
      
      /*Substring with Concatenation of All Words*/
      /*You are given a string, S, and a list of words, L, that are all of the same length. Find all starting indices of substring(s) in S that is a concatenation of each word in L exactly once and without any intervening characters.*/
      public static List<Integer> findSubstring(String S, String[] L) {
    	    List<Integer> res = new ArrayList<Integer>();
    	    if (S == null || L == null || L.length == 0) return res;
    	    int len = L[0].length(); // length of each word
    	    Map<String, Integer> map = new HashMap<String, Integer>(); // map for L
    	    for (String w : L) map.put(w, map.containsKey(w) ? map.get(w) + 1 : 1);
    	    for (int i = 0; i <= S.length() - len * L.length; i++) {
    	        Map<String, Integer> copy = new HashMap<String, Integer>(map);
    	        for (int j = 0; j < L.length; j++) { // checkc if match
    	            String str = S.substring(i + j*len, i + j*len + len); // next word
    	            if (copy.containsKey(str)) { // is in remaining words
    	                int count = copy.get(str);
    	                if (count == 1) copy.remove(str);
    	                else copy.put(str, count - 1);
    	                if (copy.isEmpty()) { // matches
    	                    res.add(i);
    	                    break;
    	                }
    	            } else break; // not in L
    	        }
    	    }
    	    return res;
      }
      public static void findSubstring_test(){
    	  findSubstring("aaa", new String[]{"a","b"});
      }
      
      
      
      
      
      /*Distinct Subsequences*/
      /*Given a string S and a string T, count the number of distinct subsequences of T in S.*/
      /*A subsequence of a string is a new string which is formed from the original string by deleting some (can be none) of the characters 
       * without disturbing the relative positions of the remaining characters. (ie, "ACE" is a subsequence of "ABCDE" while "AEC" is not).*/
      public static int numDistinct(String S, String T) {
          int[] map=new int[T.length()];
          for(char c: S.toCharArray())
              for(int i=T.length()-1;i>=0;i--)
                  if(T.charAt(i)==c)
                      map[i]+=(i!=0)?map[i-1]:1;
          return map[T.length()-1];
      }

      
      
      /*Interleaving String*/
      /*Given s1, s2, s3, find whether s3 is formed by the interleaving of s1 and s2.*/
      public static boolean isInterleave(String s1, String s2, String s3) {
          int a=s1.length(),b=s2.length();
          if (s3.length()!=a+b) return false;
          boolean[][] dp=new boolean[a+1][b+1];
          dp[0][0]=true;
          for (int i=0;i<=a;i++)
              for (int j=0;j<=b;j++) {
                  if (i>0&&s1.charAt(i-1)==s3.charAt(i+j-1)) dp[i][j]=(dp[i-1][j]||dp[i][j]);
                  if (j>0&&s2.charAt(j-1)==s3.charAt(i+j-1)) dp[i][j]=(dp[i][j-1]||dp[i][j]);
              }
          return dp[a][b];
      }
      // my own solution: same idea, but recursive.
      public boolean isInterleave2(String s1, String s2, String s3) {
          if(s1.length()+s2.length()!=s3.length())
              return false;
          int[][]mp=new int[s1.length()+1][s2.length()+1];
          mp[0][0]=2;
          return isInterleave(s1.toCharArray(),s2.toCharArray(),s3.toCharArray(),s1.length(),s2.length(),s3.length(),mp);
      }
      public boolean isInterleave(char[] s1, char[] s2, char[] s3, int a, int b, int c,int[][] mp){
          if(mp[a][b]==1) 
              return false;
          else if(mp[a][b]==2) 
              return true;
          int tmp=1;
          if(a>0&&s1[a-1]==s3[c-1]&&isInterleave(s1,s2,s3,a-1,b,c-1,mp))
              tmp=2;
          if(tmp!=2&&b>0&&s2[b-1]==s3[c-1]&&isInterleave(s1,s2,s3,a,b-1,c-1,mp))
              tmp=2;
          mp[a][b]=tmp;
          return (tmp==2)?true:false;
      }
      
      
      
      
      
      
      /*Maximal Rectangle */
      /*Given a 2D binary matrix filled with 0's and 1's, find the largest rectangle containing all ones and return its area.*/
      public static int maximalRectangle(char[][] matrix) {
          if (matrix==null||matrix.length==0||matrix[0].length==0)
              return 0;
          int cLen = matrix[0].length;    // column length
          int rLen = matrix.length;       // row length
          // height array 
          int[] h = new int[cLen+1];
          h[cLen]=0;
          int max = 0;
          for (int row=0;row<rLen;row++) {
              Stack<Integer> s = new Stack<Integer>();
              for (int i=0;i<cLen+1;i++) {
                  if (i<cLen)
                      if(matrix[row][i]=='1')
                          h[i]+=1;
                      else h[i]=0;
                  if (s.isEmpty()||h[s.peek()]<=h[i])
                      s.push(i);
                  else {
                      while(!s.isEmpty()&&h[i]<h[s.peek()]){
                          int top = s.pop();
                          int area = h[top]*(s.isEmpty()?i:(i-s.peek()-1));
                          if (area>max)
                              max = area;
                      }
                      s.push(i);
                  }
              }
          }
          return max;
      }
      
      
      /*Largest Rectangle in Histogram */
      /*Given n non-negative integers representing the histogram's bar height where the width of each bar is 1, find the area of largest rectangle in the histogram.*/
      /*http://www.geeksforgeeks.org/largest-rectangle-under-histogram/ */
      /*O(n) solution*/
      public static int largestRectangleArea(int[] height) {
  		int leng=height.length;
  		Stack<Integer> sk=new Stack<Integer>();
  		int i=0, maxarea=0;
  		while(i<leng){
  			if(sk.empty() || height[sk.peek()]<height[i]){
  				sk.push(i++);
  			}else{
  				int top=sk.pop();
  				int area=height[top]*(sk.empty()? i: i-1-sk.peek());
  				maxarea=Math.max(area, maxarea);
  			}
  		}
  		while(!sk.empty()){
  			int top=sk.pop();
  			int area=height[top]*(sk.empty()? i: i-1-sk.peek());
  			maxarea=Math.max(area, maxarea);
  		}
  		return maxarea;
      }
      public static void largestRectangleArea_test() {
      		System.out.println(largestRectangleArea(new int[]{4,2}));
      }
      /*O(nlogn) solution(divide and conquer soluiton, i.e recursive function, also from the above link)*/
      
      
      /*Regular Expression Matching*/     
      /*Implement regular expression matching with support for '.' and '*'.*/
      /*'.' Matches any single character. '*' Matches zero or more of the preceding element.*/
      public static boolean isMatch2(String str, String pattern){
    	      int pp=0,ss=0;
          for(; pp<pattern.length(); ++ss) {
        	      char c= pattern.charAt(pp);
              if( pp+1==pattern.length() || pattern.charAt(pp+1) != '*' )
                  pp++;
              else if( pp+2==pattern.length() && ss==str.length()) return true;
              else if( pp+2<pattern.length() && isMatch( str.substring(ss), pattern.substring(pp+2) ) )
                  return true;
              if( (ss==str.length()) || ((c!='.') && (c!=str.charAt(ss))) )
                  return false;
          }
          return ss==str.length();
      }
      
      
      
      /*Wildcard Matching from leetcode*/
      /*Implement wildcard pattern matching with support for '?' and '*'.*/
      /*'?' Matches any single character. '*' Matches any sequence of characters (including the empty sequence).*/
      public static boolean isMatch(String str, String pattern) {
          int s = 0, p = 0, match = 0, starIdx = -1;            
          while (s < str.length()){
              if (p < pattern.length()  && (pattern.charAt(p) == '?' || str.charAt(s) == pattern.charAt(p))){
                  s++;
                  p++;
              }
              else if (p < pattern.length() && pattern.charAt(p) == '*'){
                  starIdx = p;
                  match = s;
                  p++;
                  if(p==pattern.length()) return true;  // I add this to the original solution.
              }
              else if (starIdx != -1){
                  p = starIdx + 1;
                  match++;
                  s = match;
              }
              else return false;
          }
          while (p < pattern.length() && pattern.charAt(p) == '*') p++;
          return p == pattern.length();
      }   


      
      
      /*LRU Cache*/
      static List<Integer> keys;
      static List<Integer> values;
      static int capacity;
      public static void LRUCache(int capacity_) {
          keys = new ArrayList<Integer>();
          values = new ArrayList<Integer>();
          capacity = capacity_;
      }
      public static int get(int key) {
          int index = keys.indexOf(key);
          if (index == -1) {
              return -1;
          } else {
              keys.remove(index);
              int v = values.remove(index);
              keys.add(key);
              values.add(v);
              return v;
          }
      }
      public static void set(int key, int value) {
          int index = keys.indexOf(key);
          if (index >= 0) {  // the key is already in the cache
              values.remove(index);
              values.add(value);
              keys.remove(index);
              keys.add(key);
          } else {
              // the key is not in the Cache yet
              if (keys.size() < capacity) { //  the cache is not full
                  keys.add(key);
                  values.add(value);
              } else {  // the cache is full
                  keys.remove(0);
                  values.remove(0);
                  keys.add(key);
                  values.add(value);
              }
          }
      }
      /*Another brilliant soluiton using doubly linked list & hashmap: https://oj.leetcode.com/discuss/16010/o-1-java-solution*/
      
      
      
      
      /*Binary Search Tree Iterator*/
      /*Implement an iterator over a binary search tree (BST). Your iterator will be initialized with the root node of a BST. Calling next() will return the next smallest number in the BST.*/
      public class BSTIterator {
	      Stack<TreeNode> stack = new Stack<TreeNode>();
	      public BSTIterator(TreeNode root) {
	          pushAll(root);
	      }
	      /** @return whether we have a next smallest number */
	      public boolean hasNext() {
	          return !stack.isEmpty();
	      }
	      /** @return the next smallest number */
	      public int next() {
	          TreeNode tmpNode = stack.pop();
	          pushAll(tmpNode.right);
	          return tmpNode.val;
	      }
	      private void pushAll(TreeNode node) {
	          for (; node != null; stack.push(node), node = node.left);
	      }
      }
      // Solution 2: essentially these two solutions are the same.
      public class BSTIterator2 {
    	    Stack<TreeNode> sk;
    	    TreeNode ptr;
    	    public BSTIterator2(TreeNode root) {
    	        sk=new Stack<TreeNode>();
    	        ptr=root;
    	    }

    	    /** @return whether we have a next smallest number */
    	    public boolean hasNext() {
    	        return ptr!=null || !sk.isEmpty();
    	    }

    	    /** @return the next smallest number */
    	    public int next() {
    	        while(ptr!=null){
    	            sk.push(ptr);
    	            ptr=ptr.left;
    	        }
    	        ptr=sk.pop();
    	        int res=ptr.val;
    	        ptr=ptr.right;
    	        return res;
    	    }
    	}
      
      
      
      
      /*Dungeon Game */
      /*Write a function to determine the knight's minimum initial health so that he is able to rescue the princess.*/
      /*Somehow it is not working, has no idea what the fuck is going on......*/
      public static int calculateMinimumHP(int[][] dungeon){
    	  		int[][] hp=new int[dungeon.length][dungeon[0].length];
    	  		boolean[][] visited=new boolean[dungeon.length][dungeon[0].length];
    	  		int[][] minhp=new int[dungeon.length][dungeon[0].length];
    	  		hp[0][0]=dungeon[0][0];
    	  		visited[0][0]=true;
    	  		minhp[0][0]=Math.min(0, dungeon[0][0]);
    	  		calculateMinimumHP(hp,visited,minhp,dungeon,dungeon.length-1,dungeon[0].length-1);
    	  		int res=minhp[dungeon.length-1][dungeon[0].length-1];
    	  		return -res+1;
      }
      public static void calculateMinimumHP(int[][] hp, boolean[][] visited, int[][] minhp,int[][] dungeon, int row, int col){
    	  		if(visited[row][col]) return;
    	  		int MIN1=Integer.MIN_VALUE,MIN2=Integer.MIN_VALUE,HP1=Integer.MIN_VALUE,HP2=Integer.MIN_VALUE;
    	  		if(row-1>=0){
    	  			calculateMinimumHP(hp,visited,minhp,dungeon,row-1,col);
    	  			HP1=dungeon[row][col]+hp[row-1][col];
    	  			MIN1=Math.min(Math.min(HP1, minhp[row-1][col]),0);
    	  		}
    	  		if(col-1>=0){
    	  			calculateMinimumHP(hp,visited,minhp,dungeon,row,col-1);
    	  			HP2=dungeon[row][col]+hp[row][col-1];
    	  			MIN2=Math.min(0, Math.min(HP2, minhp[row][col-1]));
    	  		}
    	  		int HP=0,MIN=0;
    	  		if(MIN2>MIN1||(MIN2==MIN1&&HP2>HP1)){
    	  			HP=HP2;
    	  			MIN=MIN2;
    	  		}else{
    	  			HP=HP1;
    	  			MIN=MIN1;
    	  		}
    	  		visited[row][col]=true;
    	  		hp[row][col]=HP;
    	  		minhp[row][col]=MIN;
      }
      public static void calculateMinimumHP_test(){
    	  		System.out.println(calculateMinimumHP(new int[][]{{19,14,-25,-20,-36},{-46,-72,-74,25,-24},{-38,-57,-38,-73,-23},{-12,1,-70,44,-98}}));
      }
      /*Solution 2: smarter; from leetcode discussion*/
      // A perfect example where you want to use backtracing.
      public int calculateMinimumHP2(int[][] dungeon) {
          int i=dungeon.length-1;
          int j=dungeon[0].length-1;
          int[][] mp=new int[i+1][j+1];
          mp[i][j] = Math.max(1-dungeon[i][j],1);
          for(int k=j-1;k>=0;k--){
              mp[i][k]=Math.max(mp[i][k+1]-dungeon[i][k],1);
          }
          for(int k=i-1;k>=0;k--){
              mp[k][j]=Math.max(mp[k+1][j]-dungeon[k][j],1);
          }
          for(int m=i-1;m>=0;m--){
              for(int n=j-1;n>=0;n--){
                  mp[m][n]=Math.max(1,Math.min(mp[m+1][n],mp[m][n+1])-dungeon[m][n]);
              }
          }
          return mp[0][0];
     }
      
      
      
      /* Largest Number*/
      /*Given a list of non negative integers, arrange them such that they form the largest number.
       * For example, given [3, 30, 34, 5, 9], the largest formed number is 9534330.
       * Note: The result may be very large, so you need to return a string instead of an integer.*/
      public static String largestNumber(int[] num) {
    	        String[] str=new String[num.length];
    	        for(int i=0;i<num.length;i++) str[i]=String.valueOf(num[i]);
    	        Arrays.sort(str,new Comparator<String>(){
    	            @Override
    	            public int compare(String s1,String s2){
    	            		return Long.valueOf(s2+s1).compareTo(Long.valueOf(s1+s2));
    	            }
    	        });
    	        if(str[0].equals("0")) return "0";
    	        StringBuilder res=new StringBuilder();
    	        for(int i=0;i<num.length;i++){
    	            res.append(str[i]);
    	        } 
    	        return res.toString();
      }
      

      
      
      /*Best Time to Buy and Sell Stock*/
      /*Say you have an array for which the ith element is the price of a given stock on day i. If you were only permitted to complete at most one transaction (ie, buy one and sell one share of the stock), design an algorithm to find the maximum profit.*/
      public static int maxProfit(int[] prices) {
	  	   int res=0;
	  	   for(int ptr=1, low=0; ptr<prices.length; ptr++){
	  	       if(prices[low]>prices[ptr]){
	  	           low=ptr;
	  	       }else{
	  	           res=Math.max(res,prices[ptr]-prices[low]);
	  	       }
	  	   }
	  	   return res;      
      }
      
      /*Best Time to Buy and Sell Stock II */
      /*Say you have an array for which the ith element is the price of a given stock on day i.
       * Design an algorithm to find the maximum profit. You may complete as many transactions as you like 
       * (ie, buy one and sell one share of the stock multiple times). However, you may not engage in multiple transactions at the same time (ie, you must sell the stock before you buy again).*/
      public static int maxProfit2(int[] prices) {
          int tot=0;
          if(prices.length==0) return tot;
          for(int pre=prices[0], ptr=1; ptr<prices.length; pre=prices[ptr], ptr++){
              if(prices[ptr]>pre) tot+=prices[ptr]-pre;
          }
          return tot;
      }
      
      
      
      /*Best Time to Buy and Sell Stock III */
      /*Say you have an array for which the ith element is the price of a given stock on day i.
      Design an algorithm to find the maximum profit. You may complete at most two transactions.
      Note: You may not engage in multiple transactions at the same time (ie, you must sell the stock before you buy again).*/
      public static int maxProfit3(int[] prices) {
          int hold1 = Integer.MIN_VALUE, hold2 = Integer.MIN_VALUE;
          int release1 = 0, release2 = 0;
          for(int i:prices){  
              release2 = Math.max(release2, hold2+i);  
              hold2    = Math.max(hold2,    release1-i); 
              release1 = Math.max(release1, hold1+i); 
              hold1    = Math.max(hold1,    -i);    
          }
          return release2;
      }
      
      
      
      
      
      /*Valid Sudoku*/
      /*Determine if a Sudoku is valid*/
      public static boolean isValidSudoku(char[][] board) {
          char [][]rowCheck = new char[9][10];
          char [][]colCheck = new char[9][10];
          char [][]boxCheck = new char[9][10];
          for (int i = 0; i < 9; ++i) {
              for (int j = 0; j < 9; ++j) {
                  char charToCheck = board[i][j];
                  if (charToCheck != '.') {
                      int box = (i/3) * 3 + (j/3); // find the corresponding box index
                      if (boxCheck[box][charToCheck - '0'] == 1) return false;
                      boxCheck[box][charToCheck - '0'] = 1;
                      if (rowCheck[i][charToCheck - '0'] == 1) return false;
                      rowCheck[i][charToCheck - '0'] = 1;
                      if (colCheck[j][charToCheck - '0'] == 1) return false;
                      colCheck[j][charToCheck - '0'] = 1;
                  }
              }
          }
          return true;
      }
      
      
      
      /*Graphs: 
       * Trie is a variant of an n-ary tree in which characters are stored at each node. Each path down the tree may represent a word.
       * Graph Traversal: DFS is typically the easiest if we want to visit every node in the graph, or at least visit every node until we find whatever we are looking for. However, if we have a very large tree and want to be
       * prepared to quit when we get too far from the original node, DFS can be problematic; we might search thousands of ancestors of the node, but never even search all the node's children. In these cases, BFS is typically
       * preferred*/
      /*DFS is a bit simpler to implement since it can be done with simple recursion. Breadth first search can also be useful to find the shortest path, 
       * whereas depth first search may traverse one adjacent node very deeply before ever going onto the immediate neighbors.*/
      /*DFS*/
      /*
         void search(Node root){
	    		if(root==null) return;
	    		visit(root);
	    		root.visited=true;
	    		for(Node n: root.neighbours){
	    			if(n.visited==false){
	    				search(n);
	    			}
	    		}
         }
      */
      /*BFS*/
      /*
         void search(Node root){
         	Queue q=new LinkedList();
         	root.visited=true;
         	visit(root);
         	q.push(root);
         	while(!q.isEmpty()){
         		Node r=q.pop();
         		for(Node n: r.neighbours){
         			if(n.visited==false){
         				visit(n);
         				n.visited=true;
         				q.push(n);		
         			}      		
         		}        	
         	}      
         }     
      */
      /*Given a directed graph, design an algorithm to find out whether there is a route between two nodes*/
      /*
         public enum State{Unvisted, Visited, Visiting};
         public static bllean search(Graph g, Node start, Node end){
         	Queue q=new LinkedList();
         	for(Node n: g.getNode()){
         		n.state=State.Unvisited;
         	}
         	start.start=State.Visiting;
         	q.push(start);
         	while(!q.isEmpty()){
         		Node r=q.pop();
         		for(Node n: r.neighbours){
         			if(n.state==Unvisited){
         				if(n==end){
         					return true;
         				}else{
         					n.state==Visiting;
         					q.push(n);
         				}
         			}
         		}
         		r.state=Visited;
         	}
         	return false;
         }  
      */
      /*Bipartite graph problem*/

      
      
      
      
      
      
      /*Java I/O*/
      /*
	      public static void sumFile ( String name ) {
	    	    try{
	    	    		int total=0;
	    	    		BufferedReader in = new BufferedReader ( new FileReader ( name ));
	            for ( String s = in.readLine(); s != null; s = in.readLine() ) {
	                total += Integer.parseInt ( s );
	            }
	            System.out.println ( total );
	            in.close();
	    	    }catch(Exception xc){
	    	    		System.out.println("Wrong!");
	    	    }
	      }
      */
      
      
      
      
      /*short coding weeder questions*/
      //Format an RGB value (three 1-byte numbers) as a 6-digit hexadecimal string.
      /*
	      public String formatRGB ( int r, int g, int b ) {
	          return (toHex(r) + toHex(g) + toHex(b)).toUpperCase();
	      }
	      public String toHex ( int c ) {
	          String s = Integer.toHexString ( c );
	          return ( s.length() == 1 ) ? "0" + s : s;
	      }
	      OR.
	      public String formatRGB ( int r, int g, int b ) {
        		 return String.format ( "%02X%02X%02X", r, g, b );
    		  }
      */
      //Wiggle sort:
      /* Solution 1
         public List<Integer> reorer(int[] in){
		   Arrays.sort(in);
		   int mid=(in.length%2==1) ? in.length/2+1:in.length/2;
		   int[] newlist=new int[int.length];
		   int i=0, j=mid, k=0;
		   for(;i<mid;i++,j++){
		       newlist[k++]=in[i];
		       newlist[k++]=in[j];
		   }
		   if(k==in.length-1)
		       newlist[k]=in[i];
		   return newlist;
		}
       */
       /* Solution 2 (swapping)
		public int[] reorder (int[] in) {
		   for(int i=1; i < in.length; i++) {
		      if (((i % 2 == 0) && (in[i] < in[i-1]))  ||  ((i % 2 == 1) && (in[i] > in[i-1]))) {
		         int temp = in[i-1];
		         in[i-1] = in[i];
		         in[i] = temp;
		      }
		   }
		   return in;
		}
       */
      //Calculate the square root with the accuracy to 0.0001.
      /*
	      public double sqrt(double in) {
	          int min = 0;
	          int max = in + 1;
	          while(max - min > .0001) {
	               int mid = (min + max) / 2;
	               ......(update min and max separately)
	          }
	          return max;
	      }
      */
      
      
      
      /*short OO-design weeder question*/
      // Singleton Class: The Singleton pattern ensures that a class has only one instance and ensures access to the instance through the application. It can be useful in cases where you have a "global" object 
      // object with exactly one instance. For example, we may want to implement Restaurant such that it has exactly one instance of Restaurant.
      /*
       public class Restaurant{
       		private static Restaurant _instance = null;
       		protected Restaurant(){...}
       		public static Restaurant getInstance(){
       			if(_instance==null){
       				_instance=new Restaurant();
       			}
       			return _instance;
       		}
       }
      */
      // Factory Method: The Factory Method offers an interface for creating an instance of a class, with its subclasses deciding which class to instantiate. You might want to implement this with 
      // the creator class being abstract and not providing an implementation for the Factory method. Or, you could have the Creator class be a concrete class that provides an implementation for
      // the Factory method. In this case, the Factory method would take a parameter representing which class to instantiate.
      /*
       public class CardGame{
       		public static CardGame createCardGame(GameType type){
       			if(type==GameType.Poker){
       				return new PokerGame();
       			} else if(type==GameType.BlackJack){
       				return new BlackJackGame();
       			}
       			return null;
       		}
       } 
      */
      

      
      /*Integer to Roman*/
      /*Regular Expression Matching*/
      /*Sudoku Solver*/

      
      
      
      /*A book contains with pages numbered from 1 - N. Imagine now that you concatenate all page numbers in the book such that you obtain a sequence of numbers which can be represented as a string. 
       * You can compute number of occurences 'k' of certain digit 'd' in this string. For example, let N=12, d=1, hence s = '123456789101112' => k=5 since digit '1' occure five times in that string. 
       * Problem: Write a method that, given a digit 'd' and number of its occurences 'k', returns a number of pages N. More precisely, return a lower and upper bound of this number N. */
      /*Not the most efficient code, but it's the easiest to implement and explain: basically loop until you reach the integer that has k counts of the digit before it. 
       * record it as the beginning of your possible range. then, find the next integer that produces a count of digits before it that exceeds k, find the number right before that and set it as the ending of the possible range 
		    public int[] range(int d, int k) {			
				int[] range = new int[2];	
				int count = 0;
				int i = 0;	
				while (true) {	
					int temp = numPresent(i, d);		
					if (temp > 0 && count <= k ) {				
						count += temp;
						range[0] == i;
					}			
					if (temp > 0 && count > k ) {					
						count += temp;
						range[1] == i-1;
						break;
					}
					i++;
				}			
				return range;
			}	
			int numPresent(int n, int digit) {		
				//returns the number of digit in the integer n
			} */
     
      
      
      
	public static void main(String[] args){

	}


}