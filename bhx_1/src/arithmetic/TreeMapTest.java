package arithmetic;

import java.util.Map.Entry;

import java.util.*;

/**
 * 将Map按照值/键升序排列输出
 * 利用TreeMap有序特性
 */
public class TreeMapTest {
    public static void main(String[] args) {
        Map<Integer,String> map = new TreeMap<>();

        map.put(3,"张三");
        map.put(2,"李四");
        map.put(5,"王五");
        map.put(4,"刘六");

        mapTestDesc(map);
    }

    /**
     * 升序测试
     * @param map
     */
    public static void mapTestAsc( Map<Integer,String> map){
        //TreeMap具有有序行，默认为按照键值升序排列，所以直接输出即可。
        Set<Integer> keySet = map.keySet();
        Iterator<Integer> iter = keySet.iterator();
        while (iter.hasNext()) {
            int key = iter.next();
            System.out.println(key + ":" + map.get(key));
        }
    }
    /**
     * 降序测试
     * @param map
     */
    public static void mapTestDesc( Map<Integer,String> map){
        //TreeMap具有有序行，默认为按照键值升序排列，所以直接输出即可。
        //这里将map.entrySet()转换成list
        //这里将map.entrySet()转换成list
        List<Map.Entry<Integer,String>> list = new ArrayList<Map.Entry<Integer,String>>(map.entrySet());
        //然后通过比较器来实现排序
        Collections.sort(list,new Comparator<Entry<Integer, String>>() {
            //升序排序
            public int compare(Entry<Integer, String> o1,
                               Entry<Integer, String> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }

        });

        for(Map.Entry<Integer,String> mapping:list){
            System.out.println(mapping.getKey()+":"+mapping.getValue());
        }
    }
}

