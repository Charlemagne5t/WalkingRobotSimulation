import java.util.*;

class Solution {
    public int robotSim(int[] commands, int[][] obstacles) {
        Map<Integer, List<Integer>> x = new HashMap<>();
        Map<Integer, List<Integer>> y = new HashMap<>();

        for (int[] o : obstacles) {
            List<Integer> list = x.getOrDefault(o[0], new ArrayList<>());
            list.add(o[1]);
            x.put(o[0], list);
            List<Integer> list2 = y.getOrDefault(o[1], new ArrayList<>());
            list2.add(o[0]);
            y.put(o[1], list2);
        }
        for (List<Integer> l : x.values()) {
            Collections.sort(l);
        }
        for (List<Integer> l : y.values()) {
            Collections.sort(l);
        }

        int res = 0;

        // 0 - N, 1 - E, 2 - S, 3 - W
        Robot r = new Robot(0, 0, 0);
        for (int command : commands) {
            if (command == -1 || command == -2) {
                turn(r, command);
            } else {
                switch (r.facing) {
                    case 0 -> {
                        if (!x.containsKey(r.x)) {
                            r.y += command;
                        } else {
                            List<Integer> obs = x.get(r.x);
                            r.y = bs0(obs, r.y, command);
                        }
                    }
                    case 1 -> {
                        if (!y.containsKey(r.y)) {
                            r.x += command;
                        } else {
                            List<Integer> obs = y.get(r.y);
                            r.x = bs1(obs, r.x, command);
                        }
                    }
                    case 2 -> {
                        if (!x.containsKey(r.x)) {
                            r.y -= command;
                        } else {
                            List<Integer> obs = x.get(r.x);
                            r.y = bs2(obs, r.y, command);
                        }
                    }
                    case 3 -> {
                        if (!y.containsKey(r.y)) {
                            r.x -= command;
                        } else {
                            List<Integer> obs = y.get(r.y);
                            r.x = bs3(obs, r.x, command);
                        }
                    }
                }
                res = Math.max(res, dist(r.x, r.y));
            }
        }

        return res;
    }

    int bs0(List<Integer> obs, int y, int command) {
        int l = 0;
        int r = obs.size() - 1;
        int newY = y + command;

        while (l <= r) {
            int mid = l + (r - l) / 2;
            if (obs.get(mid) > y && obs.get(mid) <= newY) {
                newY = obs.get(mid) - 1;
                break;
            } else if (obs.get(mid) > newY) {
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }
        return newY;
    }

    int bs1(List<Integer> obs, int x, int command) {
        int l = 0;
        int r = obs.size() - 1;
        int newX = x + command;

        while (l <= r) {
            int mid = l + (r - l) / 2;
            if (obs.get(mid) > x && obs.get(mid) <= newX) {
                newX = obs.get(mid) - 1;
                break;
            } else if (obs.get(mid) > newX) {
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }
        return newX;
    }

    int bs2(List<Integer> obs, int y, int command) {
        int l = 0;
        int r = obs.size() - 1;
        int newY = y - command;

        while (l <= r) {
            int mid = l + (r - l) / 2;
            if (obs.get(mid) < y && obs.get(mid) >= newY) {
                newY = obs.get(mid) + 1;
                break;
            } else if (obs.get(mid) < newY) {
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }
        return newY;
    }

    int bs3(List<Integer> obs, int x, int command) {
        int l = 0;
        int r = obs.size() - 1;
        int newX = x - command;

        while (l <= r) {
            int mid = l + (r - l) / 2;
            if (obs.get(mid) < x && obs.get(mid) >= newX) {
                newX = obs.get(mid) + 1;
                break;
            } else if (obs.get(mid) < newX) {
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }
        return newX;
    }

    int dist(int x, int y) {
        return x * x + y * y;
    }

    void turn(Robot r, int turn) {
        if (turn == -1) {
            r.facing++;
        }
        if (turn == -2) {
            r.facing--;
        }
        if (r.facing == -1) {
            r.facing = 3;
        }
        if (r.facing == 4) {
            r.facing = 0;
        }
    }
}

class Robot {
    int x;
    int y;
    int facing;

    Robot(int x, int y, int facing) {
        this.x = x;
        this.y = y;
        this.facing = facing;
    }
}
