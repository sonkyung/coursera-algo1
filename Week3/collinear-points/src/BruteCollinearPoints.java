
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


        for(int i = 0; i < n; ++i) {
            //check if any point is null
            if (points[i] == null) throw new IllegalArgumentException();

            //check for any repeated point
            for(int j = 1; j < n; ++j) {
                if(i < j) {
                    if (points[i].slopeTo(points[j]) == Double.NEGATIVE_INFINITY)
                        throw new IllegalArgumentException();
                }
            }
        }


        //consider all subsets of 4 points -- because at least 4 points must be collinear
        if(n >= 4) {

            for (int i = 0; i < n; ++i) {
                for (int j = 1; j < n; ++j) {
                    for (int k = 2; k < n; ++k) {
                        for (int l = 3; l < n; ++l) {

                            // condition(i < j < k < l) => unique 4 point subset permutation
                            if ((i < j) && (j < k) && (k < l)) {
                                ArrayList<Point> pts = new ArrayList<Point>();
                                pts.add(points[i]);
                                pts.add(points[j]);
                                pts.add(points[k]);
                                pts.add(points[l]);

                                pts.sort(Point::compareTo);       // needed??

                                // check if that chosen 4-point subset is collinear: [ p0 <=> p1 <=> p2 <=> p3 ]
                                boolean isCollinear = true;
                                isCollinear = isCollinear && (pts.get(1).slopeOrder().compare(pts.get(0), pts.get(2)) == 0);
                                isCollinear = isCollinear && (pts.get(2).slopeOrder().compare(pts.get(1), pts.get(3)) == 0);

                                if (isCollinear) {
                                    LineSegment ls = new LineSegment(pts.get(0), pts.get(3));
                                    lineSegs.add(ls);
                                }


                            }


                        }   // l
                    }   // k
                }   // j
            }   // i

        }   // if n>=4

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
        StdDraw.setXscale(-100, 32768);
        StdDraw.setYscale(-100, 32768);
        StdDraw.setPenColor(0, 0, 0);
        StdDraw.setPenRadius(0.01);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();


        // print and draw the line segments
        StdDraw.setPenRadius(0.002);
        StdDraw.setPenColor(255, 0, 0);
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();

    }
}
