(ns java-interop.03-type-hints)

; when adding type hints you often want to call
; this at the repl during development,
; but not leave it in because it will emit warnings for the whole project.
; you can also set it to false at the end of the namespace
(set! *warn-on-reflection* true)

; hints in let bindings
(comment
  ; type is inferred because it's a literal, no hint needed.
  (.charAt "hello there" 3)
  (let [s "hello there"]
    (.charAt s 3))
  ; type is not inferred because value is not from a literal, will use reflection without a hint
  (let [s (apply str "hello")]
    (.charAt s 3))
  ; hinted version
  (let [s ^String (apply str "hello")]
    (.charAt s 3)))

; hints on vars
(comment
  (def unhinted-string "hi")
  (def ^String hinted-string "hi")
  ; doesn't have type :tag
  (meta #'unhinted-string)
  ; has type :tag
  (meta #'hinted-string)
  ; reflection warning
  (.length unhinted-string)
  ; no reflection warning
  (.length hinted-string)) 

; hints on functions
(comment
  
  ; unhinted, produces warning
  (defn substr [s start end]
    (.substring s start end))
  
  ; params hinted, no warning on defining function itself.
  (defn substr-params-hinted [^String x start end]
    (.substring x start end))
  
  ; hinted, no warning
  (defn substr-hinted
    ^String [^String x start end]
    (.substring x start end))
  
  ; warning because return value is not hinted so 'length' not resolved
  (.length (substr "foo" 0 2))
  ; warning because return value is not hinted so 'length' not resolved
  (.length (substr-params-hinted "foo" 0 2))
  ; no warning, return value is hinted as String so no reflection needed to call 'length'
  (.length (substr-hinted "foo" 0 2))
  
  ; timing examples
  (time (reduce + (map #(.length (substr % 0 2)) (repeat 100000 "asdf"))))
  (time (reduce + (map #(.length (substr-hinted % 0 2)) (repeat 100000 "asdf")))))
  
  
; array type hinting
(comment
  ; warning because no type hint on array type
  (let [arr (make-array Long/TYPE 3)]
    {:length (alength arr) :first (aget arr 0)})
  ; no warning because array is type hinted
  (let [arr ^longs (make-array Long/TYPE 3)]
    {:length (alength arr) :first (aget arr 0)})
  (let [arr ^ints (make-array Integer/TYPE 3)]
    {:length (alength arr) :first (aget arr 0)})
  (let [arr ^doubles (make-array Double/TYPE 3)]
    {:length (alength arr) :first (aget arr 0)}))
  

(set! *warn-on-reflection* false)