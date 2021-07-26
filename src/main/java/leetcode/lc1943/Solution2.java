package leetcode.lc1943;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

public class Solution2 {
    public List<List<Long>> splitPainting(int[][] segments) {
        int segmentCount = segments.length;
        List<List<Long>> res = new ArrayList<>();
        List<List<Integer>> posList = new ArrayList<>();
        List<Integer> breakPoints = new ArrayList<>();
        List<Long> sums = new ArrayList<>();
        PriorityQueue<LineStart> lineStarts = new PriorityQueue<>();
        PriorityQueue<LineEnd> lineEnds = new PriorityQueue<>();
        for (int lineInd = 0; lineInd < segmentCount; lineInd++) {
            lineStarts.offer(new LineStart(lineInd, segments[lineInd][0]));
            lineEnds.offer(new LineEnd(lineInd, segments[lineInd][1]));
        }
        int currentPos = 0;
        while (!lineEnds.isEmpty()) {
            int nextLineEnd = lineEnds.peek().endPosition;
            if ((!lineStarts.isEmpty()) && lineStarts.peek().startPosition <= nextLineEnd) {
                int nextLineStart = lineStarts.peek().startPosition;
                List<Integer> currentLines = new ArrayList<>();
                while ((!lineStarts.isEmpty()) && lineStarts.peek().startPosition == nextLineStart) {
                    currentLines.add(lineStarts.poll().lineInd);
                }
                if (posList.size() == 0) {
                    posList.add(currentLines);
                    currentPos = nextLineStart;
                    breakPoints.add(currentPos);
                    long sum = currentLines.stream().map(lineInd -> (long)segments[lineInd][2]).reduce((long)0, Long::sum);
                    sums.add(sum);
                } else {
                    List<Integer> previousList = posList.get(posList.size() - 1);
                    List<Integer> newList = new ArrayList<>(previousList);
                    newList.addAll(currentLines);
                    long diff = currentLines.stream().map(lineInd -> (long)segments[lineInd][2]).reduce((long)0, Long::sum);
                    long oldSum = sums.get(posList.size()-1);
                    long newSum = oldSum + diff;
                    if (currentPos == nextLineStart) {
                        posList.remove(posList.size() - 1);
                        posList.add(newList);
                        sums.remove(sums.size()-1);
                        sums.add(newSum);
                        continue;
                    } else {
                        posList.add(newList);
                        currentPos = nextLineStart;
                        breakPoints.add(currentPos);
                        sums.add(newSum);
                        continue;
                    }
                }
            }
            if (lineStarts.isEmpty() || lineStarts.peek().startPosition > nextLineEnd) {
                List<Integer> currentLines = new ArrayList<>();
                while ((!lineEnds.isEmpty()) && lineEnds.peek().endPosition == nextLineEnd) {
                    currentLines.add(lineEnds.poll().lineInd);
                }
                List<Integer> previousList = posList.get(posList.size() - 1);
                List<Integer> newList =
                        previousList.stream().filter(lineInd -> !currentLines.contains(lineInd)).collect(Collectors.toList());
                long diff = currentLines.stream().map(lineInd -> (long)segments[lineInd][2]).reduce((long)0, Long::sum);
                long oldSum = sums.get(posList.size()-1);
                long newSum = oldSum - diff;
                if (currentPos == nextLineEnd) {
                    posList.remove(posList.size() - 1);
                    posList.add(newList);
                    sums.remove(sums.size()-1);
                    sums.add(newSum);
                } else {
                    posList.add(newList);
                    currentPos = nextLineEnd;
                    breakPoints.add(currentPos);
                    sums.add(newSum);
                }
            }
        }
        int breakPointSize = breakPoints.size();
        for (int ind = 0; ind < breakPointSize - 1; ind++) {
            Long sum = sums.get(ind);
            if (sum != 0) {
                res.add(Arrays.asList((long) breakPoints.get(ind), (long) breakPoints.get(ind + 1), sum));
            }
        }
        return res;
    }

    class LineEnd implements Comparable<LineEnd> {
        int lineInd;
        int endPosition;

        public LineEnd(final int lineInd, final int endPosition) {
            this.lineInd = lineInd;
            this.endPosition = endPosition;
        }

        @Override
        public int compareTo(final LineEnd o) {
            return endPosition - o.endPosition;
        }
    }

    class LineStart implements Comparable<LineStart> {
        int lineInd;
        int startPosition;

        public LineStart(final int lineInd, final int endPosition) {
            this.lineInd = lineInd;
            this.startPosition = endPosition;
        }

        @Override
        public int compareTo(final LineStart o) {
            return startPosition - o.startPosition;
        }
    }


}
