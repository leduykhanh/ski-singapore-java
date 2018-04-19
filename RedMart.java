import java.util.*;

/*
How to run
> javac RedMart.java
> java RedMart < red_mart.in
*/

public class RedMart {
	public static void main(String[] args) {
		// Read data from input stream
		int[][] map = readData(); 
		System.out.println(findLongestPath(map));
	}

	public static Scanner sc = new Scanner(System.in);

	public static int[][] readData() {
		int height = sc.nextInt();
		int width = sc.nextInt();

		int [][] result = new int[height][width];
		for(int i=0; i<height; i++) {
			for(int j=0; j<width; j++) {
				result[i][j] = sc.nextInt();
			}
		}
		return result;
	}

	public static class Path implements Comparable{
		int length;
		int drop;

		public Path(int length, int drop) {
			this.length = length;
			this.drop = drop;
		}

		@Override
		public String toString() {
			return "Length: " + length + ", Drop: " + drop;
		}

		@Override
		public int compareTo(Object o) {
			Path other = (Path) o;
			if(length == other.length) return drop - other.drop;
			return length - other.length;
		}
	}

	public static Path findLongestPath(int[][] map) {
		Path longestPath = new Path(0, 0);

		int height = map.length;
		if(height == 0) return longestPath;
		int width = map[0].length;

		Path[][] paths = new Path[height][width];
		for(int i=0; i<height; i++) {
			for(int j=0; j<width; j++) {
				dfs(map, paths, i, j);
				if(longestPath.compareTo(paths[i][j]) >= 0) continue;
				longestPath = paths[i][j];
			}
		}

		return longestPath;
	}

	public static int[] verticalDirection = new int[]{-1, 0, 1, 0};
	public static int[] horizontalDirection = new int[]{0, -1, 0, 1};
	public static void dfs(int[][] map, Path[][] paths, int i, int j) {
		if(paths[i][j] != null) return;

		Path longestPath = new Path(1,0);
		Path tmp;
		// Try to move to one of the 4 adjacent locations
		for(int k=0; k<verticalDirection.length; k++) {
			int h = i + verticalDirection[k];
			int w = j + horizontalDirection[k];
			if(!isInsideMap(map, h, w)) continue;
			int changeInElevation = map[i][j] - map[h][w];
			if(changeInElevation <= 0) continue;
			dfs(map, paths, h, w);

			tmp = new Path(paths[h][w].length+1, paths[h][w].drop + changeInElevation);
			if(longestPath.compareTo(tmp) < 0) {
				longestPath = tmp;
			}
		}

		paths[i][j] = longestPath;
	}

	public static boolean isInsideMap(int[][] map, int i, int j) {
		return (i>=0 && i< map.length && j>=0 && j< map[0].length);
	}
}