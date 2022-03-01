

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;


public class FastCollinearPoints {
    private ArrayList<LineSegment> lineSegs;
    private ArrayList<Point> uniquelineSeg_p;
    private ArrayList<Point> uniquelineSeg_q;


    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException();

        int n = points.length;
        lineSegs = new ArrayList<LineSegment>();
        uniquelineSeg_p = new ArrayList<Point>();
        uniquelineSeg_q = new ArrayList<Point>();

        //check if any point is null
        for(int i = 0; i < n; ++i) {
            if (points[i] == null) throw new IllegalArgumentException();
        }


        //check for any repeated point
        for(int i = 0; i < n; ++i) {
            for(int j = 1; j < n; ++j) {
                if(i < j) {     // i < j, in order to avoid duplicate permutation
                    if (points[i].slopeTo(points[j]) == Double.NEGATIVE_INFINITY)
                        throw new IllegalArgumentException();
                }
            }
        }


        //fast-collinear algorithm
        if(n >= 4) {
            for (int i = 0; i < n; ++i) {

                //create an array of points, excluding 1 point (treat that 1 point as the 'origin')
                ArrayList<Point> pointsArr = new ArrayList<Point>();
                for (int j = 0; j < n; ++j) {
                    if (j != i) pointsArr.add(points[j]);
                }

                // sort the remaining array of points based on their slope to the 1 excluded point
                pointsArr.sort(points[i].slopeOrder());

                // consecutive slopes that are equal must be collinear
                ArrayList<Point> collinearSubset = new ArrayList<Point>();
                int collinearCnt = 1;
                collinearSubset.clear();
                collinearSubset.add(points[i]);
                for (int k = 1; k < pointsArr.size(); k++) {
                    if(points[i].slopeTo(pointsArr.get(k)) == points[i].slopeTo(pointsArr.get(k - 1))) {
                        ++collinearCnt;
                        collinearSubset.add(pointsArr.get(k - 1));
                    } else {
                        if(collinearCnt >= 3) {
                            collinearSubset.add(pointsArr.get(k - 1));
                            LineSegment ls = ExtractUniqueLineSegment(collinearSubset, uniquelineSeg_p, uniquelineSeg_q);
                            if(ls != null) lineSegs.add(ls);
                        }
                        collinearCnt = 1;
                        collinearSubset.clear();
                        collinearSubset.add(points[i]);
                    }
                }   // k

                //check one more time, in case last point is collinear
                if(collinearCnt >= 3) {
                    collinearSubset.add(pointsArr.get(pointsArr.size() - 1));
                    LineSegment ls = ExtractUniqueLineSegment(collinearSubset, uniquelineSeg_p, uniquelineSeg_q);
                    if(ls != null) lineSegs.add(ls);
                }
                collinearCnt = 1;
                collinearSubset.clear();
                collinearSubset.add(points[i]);

            }   // i

        }   // if n>= 4
    }

    //given a set of points, extract a LineSegment out of it
    private static LineSegment ExtractUniqueLineSegment(ArrayList<Point> pointSet, ArrayList<Point> uniquePointP, ArrayList<Point> uniquePointQ) {
        assert pointSet.size() >= 2;
        pointSet.sort(Point::compareTo);
        Point pointP = pointSet.get(0);
        Point pointQ = pointSet.get(pointSet.size() - 1);

        LineSegment ls = null;
        boolean isUniqueLineSeg = true;
        for (int i = 0; i < uniquePointP.size(); i++) {
            if(pointP.compareTo(uniquePointP.get(i)) == 0) {
                if(pointQ.compareTo(uniquePointQ.get(i)) == 0) {
                    // this line segment already  exists
                    isUniqueLineSeg = false;
                    break;
                }
            }
        }

        if(isUniqueLineSeg) {
            uniquePointP.add(pointP);
            uniquePointQ.add(pointQ);
            ls = new LineSegment(pointP, pointQ);
        }
        return ls;
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();

    }
}
