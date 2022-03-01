
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import java.util.ArrayList;
import java.util.Arrays;


public class BruteCollinearPoints {
    private ArrayList<LineSegment> lineSegs;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        int n = points.length;
        lineSegs = new ArrayList<LineSegment>();
        if (points == null) throw new IllegalArgumentException();

        //check if any point is null
        for(int i = 0; i < n; ++i) {
            if (points[i] == null) throw new IllegalArgumentException();
        }

        //sort the points based on their (x,y)-coordinate
        ArrayList<Point> all_pts = new ArrayList<>(Arrays.asList(points));
        all_pts.sort(Point::compareTo);

        //check for any repeated point
        for(int i = 1; i < n; ++i) {
            if (points[i].slopeTo(points[i-1]) == Double.NEGATIVE_INFINITY) throw new IllegalArgumentException();
        }


        //consider all subsets of 4 points -- because at least 4 points must be collinear
        if(n >= 4) {
            // 1. create a list of all 4-point subsets
            long N = Long.parseLong("1111",2) * (long)Math.pow(2,4);     // equals 15 * 2^4 (shift to left 4 binary digits)
            for(long perm_id = 15; perm_id <= N; ++perm_id) {     //starts at perm_id=15, b/c 15 = 0001111 = 15 (first subset consisting of first 4 points)

                // 2. iterate through that list, one 4-point subset at a time
                if (is4pointSubset(perm_id, n)) {
                    ArrayList<Point> pts = GetPointsSubset(perm_id, points);
                    //System.out.println("pts=" + pts);
                    pts.sort(Point::compareTo);
                    //System.out.println("pts=" + pts);

                    // 3. check if that chosen 4-point subset is collinear: [ p0 <=> p1 <=> p2 <=> p3 ]
                    boolean isCollinear = true;
                    isCollinear = isCollinear && (pts.get(1).slopeOrder().compare(pts.get(0), pts.get(2)) == 0);
                    isCollinear = isCollinear && (pts.get(2).slopeOrder().compare(pts.get(1), pts.get(3)) == 0);

                    if (isCollinear) {
                        LineSegment ls = new LineSegment(pts.get(0), pts.get(3));
                        lineSegs.add(ls);

                        //System.out.println("perm_id=" + perm_id + ", binStr=" + getBinaryPermString(perm_id, n) + ", isCollinear=" + isCollinear);
                    }

                    //System.out.println("perm_id=" + perm_id + ", isCollinear=" + isCollinear);
                }
            }
        }



    }

    private static String getBinaryPermString(long perm_id, long max_n) {
        String binStr = "000000000000000" + Long.toBinaryString(perm_id);
        binStr = binStr.substring(binStr.length() - (int)max_n, binStr.length());

        return binStr;
    }

    private static boolean is4pointSubset(long perm_id, long max_n) {
        boolean is4points = false;
        int cnt = 0;

        String binStr = getBinaryPermString(perm_id, max_n);
        char[] chrArr = binStr.toCharArray();

        for (int i = 0; i < chrArr.length; i++) {
            if(chrArr[i] == '1') ++cnt;
        }

        return (cnt == 4);
    }

    private static ArrayList<Point> GetPointsSubset(long perm_id, Point[] points) {
        ArrayList<Point> pts = new ArrayList<>(Arrays.asList(new Point[4]));
        String pad0 = "0";
        for (int i = 0; i < points.length; i++) {
            pad0 = pad0 + "0";
        }
        String binStr = pad0;
        binStr = binStr + Long.toBinaryString(perm_id);
        binStr = binStr.substring(binStr.length()-points.length,binStr.length());
        char[] chrArr = binStr.toCharArray();
        assert chrArr.length == points.length;

        int cnt = 0;
        for (int i = 0; i < points.length; i++) {
            if(chrArr[i] == '1') pts.set(cnt++, points[i]);
        }

        //System.out.println("perm_id=" + perm_id + ", " + binStr);

        return pts;
    }



    // the number of line segments
    public int numberOfSegments() {
        return lineSegs.size();
    }

    // the line segments
    public LineSegment[] segments() {
        LineSegment[] ls = new LineSegment[lineSegs.size()];
        return lineSegs.toArray(ls);
    }


    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }
        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.setPenColor(0, 0, 0);
        StdDraw.setPenRadius(0.01);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();


        // print and draw the line segments
        StdDraw.setPenRadius(0.001);
        StdDraw.setPenColor(255, 0, 0);
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();

    }
}
