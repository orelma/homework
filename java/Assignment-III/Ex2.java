/***************************************************
 * intro111/ipis111: Third assignment              *
 *                                                 *
 * This class is for assignment #3 - Part 2        *
 *                                                 *
 * Author(s): ### Dvir Azulay (dvirazu@post.bgu.ac.il), Ory Band (oryb@post.bgu.ac.il) ##### *
 * Date: 20/12/2011                                *
 *                                                 *
 ***************************************************/

/*
 * Important! Add comments to every method!
 *
 * The "return" statement at the end of each method and the initial value 
 * is just so this skeleton file compiles, change it according to your needs
 */

public class Ex2 {

	public static double distance(int[] point1, int[] point2) {
		return Math.sqrt(Math.pow(point1[0] - point2[0], 2)
				+ Math.pow(point1[1] - point2[1], 2));
	}

	public static boolean lexGreaterThan(int[] p1, int[] p2) {
		return (p1[0] > p2[0]) || (p1[0] == p2[0] && p1[1] > p2[1]);
	}

	/******************** Task 1 ********************/
	public static int[][] findClosestSimple(int[][] points) {
		// no pairs to find if there are less than 2 points
		if (points == null || points.length < 2) {
			return null;
		}
		
		int[][] res = new int[2][2]; // holds the two closest points in the points array
		double shortestDistance = -1; // the shortest distance
		for (int i = 0; i < points.length-1; ++i) {
			for (int j = i + 1; j < points.length; ++j) {
				// if shortestDistance is -1, it means we didn't fill res with a pair yet, so we have no distance to compare to. fill it
				// else, check if the distance between the current two points is shorter than the ones we already have, and if so, replace them
				// NOTE: we are changing the shortestDistance everytime we are changing the points so we don't have to recalculate the distance between them.
				if (shortestDistance == -1 || distance(points[i], points[j]) < shortestDistance) {
					shortestDistance = distance(points[i], points[j]);
					res[0] = points[i];
					res[1] = points[j];
				}
			}
		}
		
		return res;
	}

	/******************** Task 2 ********************/
	static int[][] splitLeft(int[][] points) {
		// calculate the amount of points we are going to place in the new array
		int pointsAmount = (int)(points.length / 2);
		
		// create the new array based on the amount of points we need to place in it
		int[][] leftSide = new int[pointsAmount][2];
		for (int i = 0; i < pointsAmount; ++i) {
			leftSide[i][0] = points[i][0];
			leftSide[i][1] = points[i][1];
		}
		
		return leftSide;
	}
	
	static int[][] splitRight(int[][] points) {
		// calculate the amount of points we are going to place in the new array
		// when we split, we might have an odd point. make sure we take it when creating the right side
		int pointsAmount = points.length - (int)(points.length / 2);
		
		// create the new array based on the amount of points we need to place in it		
		int[][] rightSide = new int[pointsAmount][2];
		
		// we are running on the indexes from the end of the array this time, until pointsAmount
		for (int i = points.length-1, j = 0; i > points.length - 1 - pointsAmount; --i, ++j) {
			rightSide[j][0] = points[i][0];
			rightSide[j][1] = points[i][1];
		}		
		
		return rightSide;
	}
	
	static int[][] merge(int[][] leftSide, int[][] rightSide) {
		// we are merging two arrays; the new array is the sum of the size of both of them
		int[][] res = new int[leftSide.length + rightSide.length][2];
		int i = 0, j = 0, k = 0; // indexes for the left, right and result arrays
		// run until we hit the end of one of the arrays
		while(i < leftSide.length && j < rightSide.length) {
			// we need to take the smaller value of the current array indexes values.
			// compare them and increase the index of the relevant array
			if (lexGreaterThan(rightSide[j], leftSide[i])) {
				res[k][0] = leftSide[i][0];
				res[k][1] = leftSide[i][1];
				++i; // increasing the left side array index
			}
			else {
				res[k][0] = rightSide[j][0];
				res[k][1] = rightSide[j][1];
				++j; // increasing the right side array inedx
			}
			
			++k; // increasing the result array index
		}
		
		// if we have any leftovers in one of the arrays, stick them to the end of our result array
		for (; i < leftSide.length; ++i, ++k) {
			res[k][0] = leftSide[i][0];
			res[k][1] = leftSide[i][1];
		}
		
		for (; j < rightSide.length; ++j, ++k) {
			res[k][0] = rightSide[j][0];
			res[k][1] = rightSide[j][1];
		}		
		
		return res;
	}
	
	public static int[][] sortByLex(int[][] points) {
		// nothing to sort if we have less than 2 points in the array
		if (points == null || points.length < 2) {
			return points;
		}
		
		// split the arrays to left and right and sort both sides seperately, and then merge them back.
		return merge(sortByLex(splitLeft(points)), sortByLex(splitRight(points)));
	}

	/*
	 * swapCoordinates takes an array of points with (x,y) coordinates and swaps between the x and y
	 */
	static void swapCoordinates(int[][] points) {
		int temp;
		
		// swap between the x and y coordinates in the array
		for (int i = 0; i < points.length; ++i) {
			temp = points[i][0];
			points[i][0] = points[i][1];
			points[i][1] = temp;
		}
	}
	
	/*
	 * copyPoints takes an array of points and copies it to a new array
	 */
	static int[][] copyPoints(int[][] points) {
		// create the new array
		int[][] res = new int[points.length][2];
		
		// copy the points to the new array _by value_
		for (int i = 0; i < points.length; ++i) {
			res[i][0] = points[i][0];
			res[i][1] = points[i][1];
		}
		
		return res;
	}
	
	public static int[][] sortByY(int[][] points) {
		// create a new array containing a copy of the points array (so we won't change the original array)
		int[][] res = copyPoints(points);
		
		// swap between the x and y coordinates so we can use our previous sorting function
		swapCoordinates(res);
		
		// sort the reversed array
		res = sortByLex(res);
		
		// swap back between the x and y coordinates so we get the correct array back, sorted by Y
		swapCoordinates(res);
		
		return res;
	}

	public static int[] duplicates(int[][] points) {
		// nothing to do if we have less than 2 points
		if (points == null || points.length < 2) {
			return null;
		}
		
		// go over the array, checking pairs of points each time to see if we have any duplicates.
		// if we find a duplicate point, return it.
		for (int i = 0; i < points.length-1; ++i) {
			for (int j = i + 1; j < points.length; ++j) {
				if (points[i][0] == points[j][0] && points[i][1] == points[j][1]) {
					// we found a duplicate point! return it
					return new int[]{points[i][0], points[i][1]};
				}
			}
		}
		
		return null;
	}

	public static int[][] filterPointsByRange(double fromX, double toX, int[][] points) {
		int pointsAmount = 0; // holds the amount of points in the fromX <= x <= toX range
		for (int i = 0; i < points.length; ++i) {
			if (points[i][0] >= fromX && points[i][0] <= toX) {
				++pointsAmount;
			}
		}
		
		// a new array that holds only the points that has an x coordinate in the range of fromX <= x <= toX
		int[][] res = new int[pointsAmount][2]; // we pre-calculated the array size earlier
		
		// if we have more than one point, create the new array that will hold only the points in the range
		if (pointsAmount > 0) {
			int j = 0;
			for (int i = 0; i < points.length; ++i) {
				// if the x coordinate is in the correct range, add it to the new array
				if (points[i][0] >= fromX && points[i][0] <= toX) {
					res[j][0] = points[i][0];
					res[j][1] = points[i][1];
					++j; // increase the new array index
				}				
			}
		}
		
		// return the filtered points array
		return res;
	}

	/******************** Task 3 ********************/
	public static int[][] findClosest(int[][] points) {
		int[][] res;
		
		int[][] pointsSortedByLex = sortByLex(points);
		int[] p = duplicates(pointsSortedByLex);
		if (p == null)
			res = findClosestRecursive(pointsSortedByLex);
		else {
			res = new int[1][];
			res[0] = p;
		}
		return res;
	}

	public static int[][] findClosestRecursive(int[][] points) {
		// nothing to do if we have less than 2 points
		if (points == null || points.length < 2) {
			return null;
		}
		
		// if we have between 2 to 4 points, calculate it with the simple function
		if (points.length > 1 && points.length < 5) {
			return findClosestSimple(points);
		}
		else {
			// we have more than 4 points, use merge sort algorithm to find the closest pair			
			int[][] pointsSortedByLexLeft = splitLeft(points); // holds the left side of the array, sorted
			int[][] pointsSortedByLexRight = splitRight(points); // holds the right side of the array, sorted 
																 // (note: contains the odd point if there's one after splitting the array)

			int[][] closestPair1 = findClosestRecursive(pointsSortedByLexLeft); // find the closest pair in the left side array
			int[][] closestPair2 = findClosestRecursive(pointsSortedByLexRight); // find the closest pair in the right side array
			
			double distance = distance(closestPair1[0], closestPair1[1]); // holds the distance between the closest pair
			int[][] res = new int[][]{closestPair1[0], closestPair1[1]}; // holds the closest pair
			
			// we start with the pair in closestPair1 and check if the pair in closestPair2 is closer. 
			// if so, use it instead as the starting values.
			if (distance(closestPair2[0], closestPair2[1]) < distance) {
				distance = distance(closestPair2[0], closestPair2[1]);
				res[0] = closestPair2[0];
				res[1] = closestPair2[1];
			}
			
			// the offset X coordinate for the middle points
			int mX = pointsSortedByLexRight[0][0];
			
			// holds all the points that has an X coordinate between mX-distance and mx+distance.
			// we only hold the points in the range of +-distance from the middle of the array as only
			// they might be the ones that are closer to each other than the ones we found in the sides already.
			// we also sort by Y to make it easier to conclude our search with a relevant match after filtering.
			int[][] middle = sortByY(filterPointsByRange(mX-distance, mX+distance, points));
			for (int i = 0; i < middle.length-1; ++i) {
				for (int j = i + 1; j < middle.length; ++j) {
					// if the differences between the Y coordinates is equal or larger than distance,
					// halt the search as we know it won't be closer than the one we already found.
					if (middle[j][1] - middle[i][1] >= distance) {
						return res;
					}
					
					// if the two points are closer than what we already had,
					// use these instead.
					if (distance(middle[i], middle[j]) < distance) {
						distance = distance(middle[i], middle[j]);
						res[0] = middle[i];
						res[1] = middle[j];						
					}
				}
			}
			
			return res;
		}
	}

	/******************** Auxiliary functions ********************/

	/**
	 * @param arr
	 *          the input 2D array
	 * @return a string representation of a 2D array
	 */
	public static String matrixToString(int[][] arr) {
		String ret = "{ ";

		if (arr == null)
			ret = "null";
		else {
			for (int i = 0; i < arr.length; i++) {
				if (arr[i] != null) {
					ret += "{ ";
					for (int j = 0; j < arr[i].length; j++)
						ret += arr[i][j] + " ";
					ret += "} ";
				}
			}
			ret += "}";
		}

		return ret;
	}

	/**
	 * @param arr the input array
	 * @return a string representation of an array
	 */
	public static String arrayToString(int[] arr) {
		String ret = "{ ";

		if (arr == null)
			ret = "null";
		else {
			for (int i = 0; i < arr.length; i++)
				ret += arr[i] + " ";
			ret += "}";
		}

		return ret;
	}

	public static void main(String[] args) {
		// Test task 1
		int[][] test1in = { { 9, 5 }, { 2, 9 }, { 2, 6 }, { 8, 6 }, { 1, 2 },
				{ 1, 3 }, { 8, 9 }, { 0, 7 }, { 5, 9 }, { 9, 8 } };
		int[][] test1exp = { { 1, 2 }, { 1, 3 } };
		System.out.println("Test 1: expected=" + matrixToString(test1exp)
				+ " actual=" + matrixToString(findClosestSimple(test1in)));

		// Test task 2a
		int[][] test2in = { { 1, 2 }, { 0, 7 }, { 2, 9 }, { 2, 6 }, { 1, 3 } };
		int[][] test2exp = { { 0, 7 }, { 1, 2 }, { 1, 3 }, { 2, 6 }, { 2, 9 } };
		System.out.println("Test 2: expected=" + matrixToString(test2exp)
				+ " actual=" + matrixToString(sortByLex(test2in)));

		// Test task 2b
		int[][] test3in = { { 9, 8 }, { 5, 9 }, { 9, 5 }, { 8, 9 }, { 8, 6 } };
		int[][] test3exp1 = { { 9, 5 }, { 8, 6 }, { 9, 8 }, { 8, 9 }, { 5, 9 } };
		int[][] test3exp2 = { { 9, 5 }, { 8, 6 }, { 9, 8 }, { 5, 9 }, { 8, 9 } };
		System.out.println("Test 3: expected=" + matrixToString(test3exp1) + " or "
				+ matrixToString(test3exp2) + " actual="
				+ matrixToString(sortByY(test3in)));

		// Test task 2c
		int[][] test4in = { { 0, 7 }, { 1, 2 }, { 1, 3 }, { 2, 6 }, { 2, 9 } };
		int[] test4exp = null;
		System.out.println("Test 4: expected=" + arrayToString(test4exp)
				+ " actual=" + arrayToString(duplicates(test4in)));

		int[][] test5in = { { 0, 7 }, { 1, 2 }, { 1, 2 }, { 2, 6 }, { 2, 6 } };
		int[] test5exp1 = { 1, 2 };
		int[] test5exp2 = { 2, 6 };
		System.out.println("Test 5: expected=" + arrayToString(test5exp1) + " or "
				+ arrayToString(test5exp2) + " actual="
				+ arrayToString(duplicates(test5in)));

		// Test task 2d
		int[][] test6in = { { 3, 0 }, { 3, 5 }, { 4, 3 }, { 6, 4 }, { 7, 3 } };
		int[][] test6exp = { { 3, 0 }, { 3, 5 }, { 4, 3 } };
		System.out.println("Test 6: expected=" + matrixToString(test6exp)
				+ " actual=" + matrixToString(filterPointsByRange(2.9, 4.2, test6in)));

		// Test task 3
		 int[][] test7in = {{0,7},{1,2},{1,3},{2,6},{2,9}};
		 int[][] test7exp = {{1,2},{1,3}};
		 System.out.println("Test 7: expected=" + matrixToString(test7exp) +
		 " actual=" + matrixToString(findClosestRecursive(test7in)));

		// Test task 3
		int[][] test8in = { { 9, 5 }, { 2, 9 }, { 2, 6 }, { 8, 6 }, { 1, 2 },
				{ 1, 3 }, { 8, 9 }, { 0, 7 }, { 5, 9 }, { 9, 8 } };
		int[][] test8exp = { { 1, 2 }, { 1, 3 } };
		System.out.println("Test 8: expected=" + matrixToString(test8exp)
				+ " actual=" + matrixToString(findClosest(test8in)));
		
		
		  int[][] points = { { 235, -107 }, { -142, 143 }, { 966, 967 },
				    { -495, 88 }, { 591, -93 }, { 454, 928 }, { -618, -459 }, { 68, -274 },
				    { 534, -759 }, { -610, -442 }, { -611, -174 }, { 630, 150 },
				    { -884, -474 }, { 351, 192 }, { -873, 529 }, { -716, -170 },
				    { -984, 477 }, { 886, 661 }, { -731, 572 }, { -920, 906 },
				    { 626, -110 }, { 978, -600 }, { 573, -43 }, { 797, -472 },
				    { 185, 695 }, { 677, -703 }, { -717, 403 }, { 659, 379 }, { 712, 644 },
				    { -318, -875 }, { -578, -410 }, { 137, 325 }, { -980, 683 },
				    { 264, -223 }, { -550, -690 }, { -546, 645 }, { 540, 592 },
				    { -603, 976 }, { -672, 366 }, { 940, -214 }, { 234, 969 },
				    { 489, 678 }, { 467, -475 }, { 864, -926 }, { 341, -892 },
				    { -401, -159 }, { -409, 349 }, { 879, 347 }, { 229, 867 },
				    { -484, -224 }, { 3, 230 }, { 702, 945 }, { 87, -931 }, { -418, -240 },
				    { 286, 438 }, { 181, -4 }, { 748, -466 }, { -352, -889 }, { 922, 518 },
				    { -638, 457 }, { 364, 288 }, { -955, 430 }, { -152, 836 },
				    { -297, -152 }, { -776, 35 }, { -53, -817 }, { 466, -382 },
				    { 60, -884 }, { 445, 458 }, { -987, -653 }, { 90, -723 },
				    { -417, -403 }, { 818, -587 }, { -307, -715 }, { -627, -626 },
				    { 148, 498 }, { 550, 600 }, { 70, 576 }, { -322, 10 }, { -536, -145 },
				    { -839, -233 }, { -356, 965 }, { -53, -16 }, { 490, 412 },
				    { -690, 26 }, { 910, -844 }, { -960, 537 }, { -720, 367 },
				    { 783, -654 }, { 135, -378 }, { 71, -8 }, { -85, -573 },
				    { -579, -199 }, { 215, 140 }, { -263, -880 }, { -379, -882 },
				    { 240, 637 }, { 980, -762 }, { 162, -453 }, { -260, 797 },
				    { 494, -278 }, { 851, -740 }, { -745, 29 }, { 662, -687 },
				    { 944, 471 }, { 209, -766 }, { 79, -92 }, { -639, 756 }, { 100, -207 },
				    { -618, 238 }, { 268, -433 }, { 668, -752 }, { 674, -862 },
				    { 615, 471 }, { 967, 220 }, { -86, 210 }, { 16, -777 }, { -92, -286 },
				    { -672, -165 }, { -934, 61 }, { 793, -504 }, { -875, -626 },
				    { -946, 534 }, { -991, 795 }, { 655, -84 }, { -781, 821 }, { 8, -733 },
				    { -916, 363 }, { 160, -892 }, { -5, -807 }, { 389, 98 }, { -467, 335 },
				    { 704, -517 }, { 271, 384 }, { -512, 918 }, { 969, 755 }, { 729, 370 },
				    { 820, -604 }, { -472, -104 }, { -480, 668 }, { 946, 751 },
				    { 987, 756 }, { -408, -491 }, { 765, -771 }, { -148, -346 },
				    { 362, 195 }, { -839, 447 }, { 99, 868 }, { -715, 784 },
				    { -628, -717 }, { -743, 233 }, { -329, 445 }, { 277, 459 },
				    { -791, -794 }, { -307, -133 }, { 506, 835 }, { -33, 416 },
				    { -53, 405 }, { -536, 317 }, { 965, -99 }, { 832, -430 },
				    { 245, -761 }, { -243, -817 }, { -835, 285 }, { -14, -717 },
				    { 514, -607 }, { -396, 341 }, { -508, -22 }, { 815, -779 },
				    { -59, 221 }, { 686, -94 }, { -154, 105 }, { -302, -631 },
				    { 229, 707 }, { 288, 315 }, { 738, 870 }, { -925, 270 }, { -529, 257 },
				    { -79, 865 }, { -110, -892 }, { 623, 61 }, { 2, 2 }, { -389, -482 },
				    { 455, -603 }, { 788, -621 }, { 645, 818 }, { 369, -706 },
				    { -500, -844 }, { -556, -297 }, { 215, 755 }, { -149, -915 },
				    { -125, 701 }, { 927, -797 }, { -337, -936 }, { 704, -819 },
				    { -888, -324 }, { -797, 671 }, { 762, 530 }, { -377, 365 },
				    { -521, -173 }, { -568, 591 }, { -312, 298 }, { -386, -131 },
				    { -176, 552 }, { 687, 944 }, { 409, -122 }, { 473, 26 }, { 345, 179 },
				    { -929, -216 }, { -758, -444 }, { 995, -72 }, { -92, 407 },
				    { -983, -811 }, { 474, 862 }, { 914, 923 }, { 66, -129 },
				    { 997, -369 }, { -578, -669 }, { -914, -736 }, { 383, -739 },
				    { 426, 353 }, { -177, 224 }, { 917, -278 }, { 232, 949 },
				    { -1000, -189 }, { 930, 421 }, { 554, 506 }, { 661, 572 },
				    { 235, 762 }, { 197, 175 }, { -162, 283 }, { 493, 503 },
				    { -540, -942 }, { -63, 920 }, { 524, 640 }, { -733, -330 },
				    { -974, -274 }, { 846, -168 }, { 795, -202 }, { 178, 660 },
				    { -14, 396 }, { -249, 338 }, { -451, -99 }, { 622, -442 }, { 5, 656 },
				    { -504, 270 }, { 492, 694 }, { 500, 458 }, { -244, 389 },
				    { -682, -149 }, { 530, 927 }, { 904, -892 }, { 565, 686 },
				    { 625, 221 }, { -521, -717 }, { 319, 607 }, { 552, -616 },
				    { -466, -313 }, { 583, -830 }, { -25, 635 }, { -957, -195 },
				    { 69, -600 }, { -128, 180 }, { 481, 127 }, { -510, -224 },
				    { 388, 648 }, { 846, -442 }, { 377, -143 }, { -326, 490 },
				    { 940, 465 }, { -495, -528 }, { -273, -126 }, { 443, -724 },
				    { -86, 753 }, { -767, 428 }, { 402, -770 }, { 735, 860 },
				    { -126, 605 }, { -466, -353 }, { -830, -52 }, { -838, 180 },
				    { -31, 30 }, { -861, -296 }, { 234, -324 }, { 890, 323 },
				    { 584, -227 }, { 485, -716 }, { 348, -23 }, { -906, -342 },
				    { -167, 428 }, { -921, 339 }, { 150, -313 }, { 728, 740 },
				    { -245, 537 }, { -252, 495 }, { -165, -701 }, { 887, 41 },
				    { 626, -133 }, { 286, 14 }, { -193, 814 }, { 496, 96 }, { 237, 573 },
				    { -487, -871 }, { -559, 6 }, { 604, 120 }, { -455, -579 },
				    { -760, 94 }, { -411, 538 }, { -972, -324 }, { 572, 791 },
				    { 158, 733 }, { 964, -109 }, { -384, 454 }, { 926, 632 }, { 156, 845 },
				    { -974, -472 }, { 870, -251 }, { -159, 87 }, { 75, -734 },
				    { 521, 265 }, { -794, -317 }, { -883, -41 }, { 406, -96 },
				    { -716, 272 }, { 596, 764 }, { 824, -264 }, { 943, -417 },
				    { -546, -339 }, { 855, 846 }, { 184, -643 }, { -107, -993 },
				    { 729, 103 }, { -672, -952 }, { 485, 414 }, { -546, 662 },
				    { 794, 421 }, { -765, 719 }, { -573, -6 }, { -630, -17 },
				    { 553, -401 }, { 441, 14 }, { 778, 848 }, { 470, 600 }, { 305, -836 },
				    { 888, -856 }, { -280, 90 }, { 699, 992 }, { -519, -631 },
				    { -528, -120 }, { 773, -890 }, { 642, 359 }, { -903, 140 },
				    { -397, -621 }, { -328, -93 }, { 64, 36 }, { 536, 221 }, { -781, 894 },
				    { -903, -466 }, { -543, -723 }, { 805, 464 }, { -505, 451 },
				    { 607, 849 }, { -882, 714 }, { 887, -285 }, { -964, -940 },
				    { -295, 268 }, { 747, -670 }, { 341, 234 }, { 767, 516 },
				    { -634, -285 }, { -910, -755 }, { -243, 96 }, { -674, -616 },
				    { -37, 465 }, { -372, -842 }, { -780, 530 }, { -822, 224 },
				    { -74, -926 }, { 819, -256 }, { -593, 612 }, { 229, -379 }, { 55, 97 },
				    { -101, 843 }, { 448, 300 }, { 199, -895 }, { 843, 775 },
				    { -246, 755 }, { 189, -789 }, { -502, -475 }, { 709, -592 },
				    { 873, 451 }, { -13, -876 }, { 873, -58 }, { -486, -411 }, { 880, 46 },
				    { 669, 627 }, { 583, 618 }, { 246, -998 }, { 662, -676 },
				    { -211, 866 }, { 359, 172 }, { 258, -13 }, { 600, 658 },
				    { -181, -157 }, { -100, -539 }, { -57, -445 }, { 905, -863 },
				    { -601, -800 }, { 179, -719 }, { -780, -951 }, { 566, -785 },
				    { 284, -60 }, { 299, -572 }, { 727, 167 }, { -62, -288 }, { -873, 31 },
				    { -837, -785 }, { -908, 504 }, { 442, -514 }, { -287, -669 },
				    { -273, -3 }, { 176, 348 }, { -582, 584 }, { -204, -211 }, { 76, 791 },
				    { 657, 903 }, { 623, 212 }, { -837, -633 }, { 90, 543 }, { 852, 631 },
				    { -620, -671 }, { 53, 610 }, { 958, -873 }, { 151, 931 },
				    { -104, 189 }, { -424, -658 }, { -701, -359 }, { 947, 820 },
				    { 749, -346 }, { 256, -601 }, { 143, -932 }, { -551, 841 },
				    { -951, -399 }, { -674, -131 }, { -471, 875 }, { 254, 872 },
				    { 294, 234 }, { -73, -226 }, { -13, -375 }, { 68, -966 },
				    { -583, 627 }, { -175, -360 }, { 654, 157 }, { 453, -588 },
				    { -971, -681 }, { -218, -100 }, { 282, 425 }, { -11, 757 },
				    { -615, 285 }, { 221, 577 }, { -105, -449 }, { 724, 882 },
				    { -945, 89 }, { -680, -806 }, { 366, -835 }, { 306, -54 },
				    { -172, 555 }, { 145, 353 }, { 647, 610 }, { -326, 315 },
				    { -150, -229 }, { 994, 924 }, { 936, -928 }, { 687, -803 },
				    { -503, 742 }, { -460, -693 }, { -483, 213 }, { 587, 753 },
				    { -993, -483 }, { -54, -316 }, { 548, -625 }, { 661, 869 },
				    { -644, -891 }, { 558, 737 }, { 544, -82 }, { 998, 291 }, { 413, 31 },
				    { 316, 753 }, { -186, -217 }, { -277, 320 }, { 857, 761 },
				    { 526, -425 }, { -379, -183 }, { -420, -246 }, { 877, -35 },
				    { -888, 341 }, { 300, -29 }, { -739, 280 }, { -835, 791 }, { 985, 449 } };
			System.out.println("Test *9: expected=" + matrixToString(test8exp)
					+ " actual=" + matrixToString(findClosest(points)) + " | distance: " + distance(findClosest(points)[0], findClosest(points)[1]));	  
		
		
		
	}
}
