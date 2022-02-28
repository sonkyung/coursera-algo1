
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import java.util.ArrayList;
import java.util.Arrays;


public class BruteCollinearPoints {
    ArrayList<LineSegment> lineSegs;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        int n = points.length;
        assert n == 4;
        lineSegs = new ArrayList<LineSegment>();

        if (points == null) throw new IllegalArgumentException();

        //check if any point is null
        for(int i = 0; i < n; ++i) {
            if (points[i] == null) throw new IllegalArgumentException();
        }

        //sort the points based on their (x,y)-coordinate
        ArrayList<Point> pts = new ArrayList<>(Arrays.asList(points));
        pts.sort(Point::compareTo);

        //check for any repeated point
        for(int i = 1; i < n; ++i) {
            if (points[i].slopeTo(points[i-1]) == Double.NEGATIVE_INFINITY) throw new IllegalArgumentException();
        }

        // [ p0 <=> p1 <=> p2 <=> p3 ]
        boolean isCollinear = true;
        isCollinear = isCollinear && (pts.get(1).slopeOrder().compare(pts.get(0), pts.get(2)) == 0);
        isCollinear = isCollinear && (pts.get(2).slopeOrder().compare(pts.get(1), pts.get(3)) == 0);


        if(isCollinear) {
            LineSegment ls = new LineSegment(pts.get(0), pts.get(3));
            lineSegs.add(ls);
        }

        System.out.println("isCollinear=" + isCollinear);

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
