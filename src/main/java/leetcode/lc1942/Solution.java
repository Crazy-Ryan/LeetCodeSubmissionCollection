package leetcode.lc1942;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.stream.Collectors;

public class Solution {
    public int smallestChair(int[][] times, int targetFriend) {
        int targetFriendArrivalTime = times[targetFriend][0];
        int friendSize = times.length;
        PriorityQueue<Integer> chairHeap = new PriorityQueue<>();
        int chairIndex = 0;
        chairHeap.add(chairIndex++);

        Map<Integer, TimeRequest> timeRequestMap = new HashMap<>();
        for (int friendInd = 0; friendInd < friendSize; friendInd++) {
            int friendArrivalTime = times[friendInd][0];
            if (friendArrivalTime <= targetFriendArrivalTime) {
                TimeRequest temp = timeRequestMap.get(friendArrivalTime);
                if (temp != null) {
                    temp.addFriend(friendInd);
                } else {
                    TimeRequest timeRequest = new TimeRequest(friendArrivalTime, friendInd);
                    timeRequestMap.put(friendArrivalTime, timeRequest);
                }
            }
            int friendLeaveTime = times[friendInd][1];
            if (friendLeaveTime <= targetFriendArrivalTime) {
                TimeRequest temp = timeRequestMap.get(friendLeaveTime);
                if (temp != null) {
                    temp.addFriend(friendInd);
                } else {
                    TimeRequest timeRequest = new TimeRequest(friendLeaveTime, friendInd);
                    timeRequestMap.put(friendLeaveTime, timeRequest);
                }
            }
        }
        List<TimeRequest> timeRequests = timeRequestMap.values().stream().sorted().collect(Collectors.toList());
        int[] friendOnChair = new int[friendSize];
        for (TimeRequest timeRequest : timeRequests) {
            for (int friendInd : timeRequest.friendIndices) {
                int friendLeaveTime = times[friendInd][1];
                if (timeRequest.time == friendLeaveTime) {
                    chairHeap.add(friendOnChair[friendInd]);
                }
            }
            for (int friendInd : timeRequest.friendIndices) {
                int friendArrivalTime = times[friendInd][0];
                if (timeRequest.time == friendArrivalTime) {
                    int assignedChair = chairHeap.remove();
                    friendOnChair[friendInd] = assignedChair;
                    if (chairHeap.size() == 0) {
                        chairHeap.add(chairIndex++);
                    }
                }
            }
        }
        return friendOnChair[targetFriend];
    }

    class TimeRequest implements Comparable<TimeRequest> {
        int time;
        Set<Integer> friendIndices;

        public TimeRequest(final int time, final int friendIndex) {
            this.time = time;
            this.friendIndices = new HashSet<>();
            this.friendIndices.add(friendIndex);
        }

        public void addFriend(int friendIndex) {
            friendIndices.add(friendIndex);
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            final TimeRequest that = (TimeRequest) o;
            return time == that.time;
        }

        @Override
        public int hashCode() {
            return Objects.hash(time);
        }

        @Override
        public int compareTo(final TimeRequest o) {
            return time - o.time;
        }
    }
}
