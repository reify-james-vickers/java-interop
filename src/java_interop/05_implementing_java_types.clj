(ns java-interop.05-implementing-java-types
  (:import [java.io File]
           [java.lang Comparable]
           [java.util Collections]))

(def directory-filter
  ; Create a var that implements the FileFilter interface
  (reify java.io.FileFilter
    (accept [this f] ; the interface method is: bool accept(File pathname)
      (.isDirectory f))))

(comment
  (.listFiles (File. ".")) ; new File(".").listFiles();
  (type directory-filter)
  (instance? java.io.FileFilter directory-filter)
  (.listFiles (java.io.File. ".") directory-filter)) ; new File(".").listFiles(directory_filter);
  
(defrecord Person [name age]
  Comparable
  (compareTo [this other]
    (Integer/compare (:age this) (:age other)))) 

(comment
  ; new Person("bill", 51).compareTo(new Person("joe", 50));
  (.compareTo (->Person "bill" 51) (->Person "joe" 50))
  ; new Person("jen", 21).compareTo(new Person("jill", 25));
  (.compareTo (->Person "jen" 21) (->Person "jill" 25)))

; since Person implements Comparable, we can binary search a list of them by age.
(comment
  (Collections/binarySearch
   [(->Person "bill" 51) (->Person "joe" 50) (->Person "jen" 21)]
   (->Person "joe" 50))
  ; by contrast this doesn't work because map doesn't implement Comparable
  (Collections/binarySearch [{:a 1} {:a 2} {:a 3}] {:a 2}))
  
