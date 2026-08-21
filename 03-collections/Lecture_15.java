
/**
 * Problems
 * 
 * 1. ArrayList / LinkedList
Implement dynamic array operations (insert, delete, search).

Reverse an ArrayList without using extra space.

Find duplicates in a list.

Remove all even numbers from a list.

Merge two ArrayLists into one without duplicates.

Sort an ArrayList using Collections.sort.

Implement custom sorting with Comparator.

Rotate elements of a list by k positions.

Find intersection of two lists.

Convert ArrayList to LinkedList and vice versa.

2. HashSet / TreeSet
Find unique elements from a list using Set.

Check if two sets are equal.

Find union and intersection of two sets.

Remove duplicates from a string using Set.

Store student names in TreeSet and sort alphabetically.

Implement subset check using Set.

Find symmetric difference between two sets.

Count distinct elements in an array.

Implement a spell-check dictionary using HashSet.

Store custom objects in TreeSet with Comparator.

3. HashMap / TreeMap
Count frequency of words in a string.

Find the first non-repeating character using Map.

Implement LRU Cache using LinkedHashMap.

Sort a HashMap by keys.

Sort a HashMap by values.

Find the most frequent element in an array.

Group students by department using Map.

Implement phone directory using HashMap.

Check if two strings are anagrams using Map.

Store marks of students and print rank list using TreeMap.

4. Queue / Deque / PriorityQueue
Implement queue using LinkedList.

Reverse a queue using stack.

Implement circular queue.

Find kth largest element using PriorityQueue.

Merge k sorted lists using PriorityQueue.

Implement sliding window maximum using Deque.

Check if parentheses are balanced using Deque.

Implement double-ended queue operations.

Simulate task scheduling using PriorityQueue.

Implement producer-consumer problem using BlockingQueue.

5. Stack
Implement stack using ArrayList.

Evaluate postfix expression using stack.

Convert infix to postfix using stack.

Check balanced brackets using stack.

Implement min-stack (get minimum in O(1)).

Implement browser history using stack.

Next greater element problem using stack.

Stock span problem using stack.

Largest rectangle in histogram using stack.

Implement undo-redo functionality using two stacks.

⚡ Coverage
✅ Core Collections (List, Set, Map)

✅ Advanced (TreeMap, PriorityQueue, Deque)

✅ Real-world apps (LRU Cache, Phone Directory, Spell Checker, Undo/Redo)

✅ Interview classics (Anagrams, Frequency count, Sliding window, Histogram)

📘 Roadmap: 50 Collections Framework Problems
🔹 Stage 1: Easy (Basics & Warm‑up)
Focus: ArrayList, LinkedList, HashSet, HashMap fundamentals
Problems:

Reverse an ArrayList

Remove duplicates from a list using Set

Count frequency of elements using Map

Find intersection of two lists

Union of two sets

Check if two strings are anagrams using Map

Sort an ArrayList with Collections.sort

Convert ArrayList ↔ LinkedList

Count distinct elements in an array

Store student names in TreeSet and print sorted

🔹 Stage 2: Medium (Core Applications)
Focus: HashMap advanced, TreeMap, Queue, Stack basics
Problems:

Word frequency counter in a string

First non‑repeating character using Map

Sort HashMap by keys

Sort HashMap by values

Implement queue using LinkedList

Reverse a queue using stack

Balanced parentheses check using stack

Next greater element problem

Stock span problem

Implement min‑stack (O(1) getMin)

🔹 Stage 3: Medium‑Hard (Real‑world & Advanced Structures)
Focus: PriorityQueue, Deque, TreeMap, LinkedHashMap
Problems:

LRU Cache using LinkedHashMap

Kth largest element using PriorityQueue

Merge k sorted lists using PriorityQueue

Sliding window maximum using Deque

Implement circular queue

Symmetric difference between two sets

Rank list of students using TreeMap

Undo‑redo functionality using two stacks

Browser history simulation using stack

Spell‑checker dictionary using HashSet

🔹 Stage 4: Hard (Interview Classics & System‑like Problems)
Focus: Complex stack/queue problems, custom comparators, concurrency
Problems:

Largest rectangle in histogram using stack

Evaluate postfix expression

Convert infix to postfix

Implement producer‑consumer with BlockingQueue

Task scheduling simulation using PriorityQueue

Group students by department using Map

Custom object sorting with Comparator in TreeSet

Implement double‑ended queue operations

Rotate elements of a list by k positions

Find most frequent element in an array

🔹 Stage 5: Expert (System Design + Collections Integration)
Focus: Combining multiple collections, real‑world mini‑systems
Problems:

Implement phone directory using HashMap

Build a leaderboard system using TreeMap

Implement a multi‑level cache (HashMap + LinkedHashMap)

Design a spell‑suggestion system using TreeSet (prefix search)

Implement a scheduler with PriorityQueue + Deque

Build a dictionary with synonyms using Map of Sets

Implement a tagging system (Map<String, Set<String>>)

Create a shopping cart system using Map + List

Implement a notification system using Queue + PriorityQueue

Build a mini file system using Map + List
 */

import java.util.*;

public class Lecture_15 {

    public static void main(String[] args) {
        /**
         * 
         * List<Integer> l = new ArrayList<>();
         * // l.add(1);
         * // l.add(2);
         * // l.add(3);
         * 
         * l.add(1);
         * l.add(2);
         * l.add(2);
         * l.add(3);
         * l.add(3);
         * l.add(3);
         * l.add(4);
         * 
         * Map<Integer, Integer> m = new HashMap<>();
         * 
         * for (int i = 0; i < l.size(); i++) {
         * if (m.get(l.get(i)) != null) {
         * m.put(l.get(i), m.get(l.get(i)) + 1);
         * } else {
         * m.put(l.get(i), 1);
         * }
         * }
         * System.out.println(m);
         * 
         */

        List<Integer>l = new ArrayList<>();
        Map<Integer,Integer>m = new HashMap<>();
        Map<Integer,Integer>m1 = new HashMap<>();
        
        l.add(1);
        l.add(2);
        l.add(2);
        l.add(4);
        l.add(3);
        
        for(int num : l) {
            m.put(num, m.getOrDefault(num, 0)+1);
        }
        System.out.println(m);


        for(int num : l) {
            m1.merge(num, 1, Integer::sum);
        }
        System.out.println(m1);
    }
}