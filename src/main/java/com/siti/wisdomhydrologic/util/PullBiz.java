package com.siti.wisdomhydrologic.util;


import com.siti.wisdomhydrologic.datepull.vo.DayVo;
import com.siti.wisdomhydrologic.datepull.vo.HourVo;
import com.siti.wisdomhydrologic.datepull.vo.RealVo;
import com.siti.wisdomhydrologic.datepull.vo.TSDBVo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2019/7/11.
 */

@Service
public class PullBiz {

    public static final int MAX_SIZE = 10000;
    public static final int LIST_SIZE = 1000;

    public int getCount(int size) {

        int merchant = size / MAX_SIZE;
        int remainder = size % MAX_SIZE;
        int count = 0;
        if (merchant == 0) {
            count++;
        } else if (merchant > 0 && remainder == 0) {
            count = merchant;
        } else if (merchant > 0 && remainder > 0) {
            count = merchant + 1;
        }
        return count;
    }

    public Map<Integer, List<DayVo>> getMap(List<DayVo> list) {
        Map<Integer, List<DayVo>> map = new HashMap<>();
        map.put(1, new ArrayList<>());
        //list大小
        int size = list.size();
        int batch = 0;
        if (size % LIST_SIZE == 0) {
            batch = size / LIST_SIZE;
            //遍历全表
            for (DayVo day : list) {
                List<DayVo> l = map.get(map.size());
                if (map.size() == batch && l.size() == LIST_SIZE - 1) {//为总数据最后一条添加属性
                    day.setCurrentBatch(batch);
                    day.setMaxBatch(batch);
                    day.setSumSize(size);
                    day.setCurrentSize(size % LIST_SIZE);
                    day.setStatus(1);
                    l.add(day);
                    System.out.println("最后一个包的第一个数据" + day);
                    return map;
                }
                //map中每一个list的第一个字段设置属性
                else if (l.size() == 0) {
                    day.setCurrentBatch((map.size()));
                    day.setSumSize(size);
                    day.setCurrentSize(map.size() * LIST_SIZE);
                    day.setMaxBatch(batch);
                    day.setStatus(0);
                    l.add(day);
                    System.out.print("每个包的第一个数据"+day);
                }else if (map.size() == batch && l.size() == 0) {
                    return map;
                }else {
                    l.add(day);
                    if (l.size() == LIST_SIZE) {
                        l = new ArrayList<>();
                        map.put(map.size() + 1, l);
                    }
                }
            }
        } else {
            batch = size / LIST_SIZE + 1;
            for (DayVo day : list) {
                int mapSize = map.size();
                List<DayVo> l = map.get(mapSize);

                //为总数据最后一条添加属性
                if (map.size() == batch && l.size() == 0) {
                    day.setCurrentBatch(batch);
                    day.setMaxBatch(batch);
                    day.setSumSize(size);
                    day.setCurrentSize(size);
                    day.setStatus(1);
                    l.add(day);
                    System.out.println("最后一个包的第一个数据" + day);
                } else if (l.size() == 0 && map.size() != batch) {
                    day.setCurrentBatch(mapSize);
                    day.setSumSize(size);
                    day.setCurrentSize(mapSize * LIST_SIZE);
                    day.setMaxBatch(batch);
                    day.setStatus(0);
                    l.add(day);
                    System.out.println("每个包的第一个数据" + day);
                } else if (map.size() == batch && l.size() == size % LIST_SIZE) {
                    return map;
                } else {
                    l.add(day);
                    if (l.size() == LIST_SIZE) {
                        l = new ArrayList<>();
                        map.put(map.size() + 1, l);
                    }
                }
            }
        }
        return map;
    }

    public Map<Integer, List<HourVo>> getHourMap(List<HourVo> list) {
        Map<Integer, List<HourVo>> map = new HashMap<>();
        map.put(1, new ArrayList<>());
        //list大小
        int size = list.size();
        int batch = 0;
        if (size % LIST_SIZE == 0) {
            batch = size / LIST_SIZE;
            //遍历全表
            for (HourVo day : list) {
                List<HourVo> l = map.get(map.size());
                if (map.size() == batch && l.size() == LIST_SIZE - 1) {//为总数据最后一条添加属性
                    day.setCurrentBatch(batch);
                    day.setMaxBatch(batch);
                    day.setSumSize(size);
                    day.setCurrentSize(size % LIST_SIZE);
                    day.setStatus(1);
                    l.add(day);
                    System.out.println("最后一个包的第一个数据" + day);
                    return map;
                }
                //map中每一个list的第一个字段设置属性
                else if (l.size() == 0) {
                    day.setCurrentBatch((map.size()));
                    day.setSumSize(size);
                    day.setCurrentSize(map.size() * LIST_SIZE);
                    day.setMaxBatch(batch);
                    day.setStatus(0);
                    l.add(day);
                    System.out.print("每个包的第一个数据"+day);
                }else if (map.size() == batch && l.size() == 0) {
                    return map;
                }else {
                    l.add(day);
                    if (l.size() == LIST_SIZE) {
                        l = new ArrayList<>();
                        map.put(map.size() + 1, l);
                    }
                }
            }
        } else {
            batch = size / LIST_SIZE + 1;
            for (HourVo day : list) {
                int mapSize = map.size();
                List<HourVo> l = map.get(mapSize);

                //为总数据最后一条添加属性
                if (map.size() == batch && l.size() == 0) {
                    day.setCurrentBatch(batch);
                    day.setMaxBatch(batch);
                    day.setSumSize(size);
                    day.setCurrentSize(size);
                    day.setStatus(1);
                    l.add(day);
                    System.out.println("最后一个包的第一个数据" + day);
                } else if (l.size() == 0 && map.size() != batch) {
                    day.setCurrentBatch(mapSize);
                    day.setSumSize(size);
                    day.setCurrentSize(mapSize * LIST_SIZE);
                    day.setMaxBatch(batch);
                    day.setStatus(0);
                    l.add(day);
                    System.out.println("每个包的第一个数据" + day);
                } else if (map.size() == batch && l.size() == size % LIST_SIZE) {
                    return map;
                } else {
                    l.add(day);
                    if (l.size() == LIST_SIZE) {
                        l = new ArrayList<>();
                        map.put(map.size() + 1, l);
                    }
                }
            }
        }
        return map;
    }

    public Map<Integer, List<TSDBVo>> getTSDBMap(List<TSDBVo> list) {
        Map<Integer, List<TSDBVo>> map = new HashMap<>();
        map.put(1, new ArrayList<>());
        //list大小
        int size = list.size();
        int batch = 0;
        if (size % LIST_SIZE == 0) {
            batch = size / LIST_SIZE;
            //遍历全表
            for (TSDBVo day : list) {
                List<TSDBVo> l = map.get(map.size());
                //map中每一个list的第一个字段设置属性
                if (l.size() == 0) {
                    day.setCurrentBatch((map.size()));
                    day.setSumSize(size);
                    day.setCurrentSize(map.size() * LIST_SIZE);
                    day.setMaxBatch(batch);
                    day.setStatus(0);
                    l.add(day);
                } else if (map.size() == batch && l.size() == LIST_SIZE - 1) {//为总数据最后一条添加属性
                    day.setCurrentBatch(batch);
                    day.setMaxBatch(batch);
                    day.setSumSize(size);
                    day.setCurrentSize(size % LIST_SIZE);
                    day.setStatus(1);
                    l.add(day);
                    System.out.println("*********" + day);
                    return map;
                } else {
                    l.add(day);
                    if (l.size() == LIST_SIZE) {
                        l = new ArrayList<>();
                        map.put(map.size() + 1, l);
                    }
                }
            }
        } else {
            batch = size / LIST_SIZE + 1;
            for (TSDBVo day : list) {
                int mapSize = map.size();
                List<TSDBVo> l = map.get(mapSize);

                //为总数据最后一条添加属性
                if (map.size() == batch && l.size() == 0) {
                    day.setCurrentBatch(batch);
                    day.setMaxBatch(batch);
                    day.setSumSize(size);
                    day.setCurrentSize(size);
                    day.setStatus(1);
                    l.add(day);
                    System.out.println("最后一个包的第一个数据" + day);
                } else if (l.size() == 0 && map.size() != batch) {
                    day.setCurrentBatch(mapSize);
                    day.setSumSize(size);
                    day.setCurrentSize(mapSize * LIST_SIZE);
                    day.setMaxBatch(batch);
                    day.setStatus(0);
                    l.add(day);
                    System.out.println("每个包的第一个数据" + day);
                } else if (map.size() == batch && l.size() == size % LIST_SIZE) {
                    return map;
                } else {
                    l.add(day);
                    if (l.size() == LIST_SIZE) {
                        l = new ArrayList<>();
                        map.put(map.size() + 1, l);
                    }
                }
            }
        }
        return map;
    }

    public Map<Integer,List<RealVo>> getRealMap(List<RealVo> list){
        Map<Integer, List<RealVo>> map = new HashMap<>();
        map.put(1, new ArrayList<>());
        int size = list.size();
        int batch = 0;
        if (size % LIST_SIZE == 0) {
            batch = size / LIST_SIZE;
            //遍历全表
            for (RealVo real : list) {
                List<RealVo> l = map.get(map.size());
                //map中每一个list的第一个字段设置属性
                if (l.size() == 0) {
                    real.setCurrentBatch((map.size()));
                    real.setSumSize(size);
                    real.setCurrentSize(map.size() * LIST_SIZE);
                    real.setMaxBatch(batch);
                    real.setStatus(0);
                    l.add(real);
                } else if (map.size() == batch && l.size() == LIST_SIZE - 1) {//为总数据最后一条添加属性
                    real.setCurrentBatch(batch);
                    real.setMaxBatch(batch);
                    real.setSumSize(size);
                    real.setCurrentSize(size % LIST_SIZE);
                    real.setStatus(1);
                    l.add(real);
                    return map;
                } else {
                    l.add(real);
                    if (l.size() == LIST_SIZE) {
                        l = new ArrayList<>();
                        map.put(map.size() + 1, l);
                    }
                }
            }
        } else {
            batch = size / LIST_SIZE + 1;
            for (RealVo real : list) {
                int mapSize = map.size();
                List<RealVo> l = map.get(mapSize);

                //为总数据最后一条添加属性
                if (map.size() == batch && l.size() == 0) {
                    real.setCurrentBatch(batch);
                    real.setMaxBatch(batch);
                    real.setSumSize(size);
                    real.setCurrentSize(size);
                    real.setStatus(1);
                    l.add(real);
                    System.out.println("最后一个包的第一个数据" + real);
                } else if (l.size() == 0 && map.size() != batch) {
                    real.setCurrentBatch(mapSize);
                    real.setSumSize(size);
                    real.setCurrentSize(mapSize * LIST_SIZE);
                    real.setMaxBatch(batch);
                    real.setStatus(0);
                    l.add(real);
                    System.out.println("每个包的第一个数据" + real);
                } else if (map.size() == batch && l.size() == size % LIST_SIZE) {
                    return map;
                } else {
                    l.add(real);
                    if (l.size() == LIST_SIZE) {
                        l = new ArrayList<>();
                        map.put(map.size() + 1, l);
                    }
                }
            }
        }
        return map;
    }

}