(ns java-interop.04-arrays
  (:import [java.util Arrays]))

; creating arrays, passing them to Java methods that take arrays.
(comment
  (type (into-array [2 3 4])) ; boxed Long[]
  (type (into-array Integer/TYPE [2 3 4])) ; primitive long[]
  (type (int-array [2 3 4])) ; also primitive long[]
  (type (into-array ["a" "b"])) ; String[]
  (Arrays/binarySearch (int-array [0 1 5 6 7 8 15]) 5) ; Arrays.binarySearch(new int[] { 0 1 5 6 7 8 15 }, 5)
  (Arrays/sort [0.5 1.0 0.2 4.2 1.4]) ; error: vectors are not primitive arrays.
  (let [arr (float-array [0.5 1.0 0.2 4.2 1.4])] ; arr = new float[] { 0.5, 1.0, 0.2, 4.2, 1.4 };
    (Arrays/sort arr) ; Arrays.sort(arr);
    arr))
  
; array operations: alength, aset, aget
(comment
  (def arr (int-array [2 4 6])) ; int[] arr = new int[] { 2, 4, 6 };
  (type arr)
  (alength arr) ; a.length
  (aset arr 0 123) ; arr[0] = 123;  note this returns the value that was set.
  (aget arr 0) ; a[0]
  (aset arr 1 456) ; arr[1] = 456;
  (aget arr 1) ; a[1]
  ; multi-dimensional arrays
  (def arr2d (make-array Integer/TYPE 2 3)) ; int[][] arr2d = new int[2][3];
  (type arr2d) ; primitive int[][]
  (alength arr2d) ; a.length
  (aset arr2d 0 1 (int 123))) ; a[0][1] = 123;  note the need to cast 123 because that is long by default.

(comment
  ; note that sequence functions like reduce, map work on arrays
  (def a (int-array [1 2 3 4 5]))
  (map inc a)
  (reduce + a))
