(ns java-interop.01-primitives-and-types)

; https://clojure.org/reference/java_interop
; - info about which clojure types map to which java types
; - primitive coercion
; - inspecting types

(comment
  ; get types of values
  ; type is similar to calling 'class' except it will return :type metadata, if any
  (type 5) ; java.lang.long
  (type 0x123) ; java.lang.Long (hex)
  (type 0123) ; java.lang.Long (octal)
  
  ; note: can use any base in [2, 36]
  (type 2r101010) ; java.lang.Long (binary)
  (type 8r123) ; java.lang.Long (octal)
  
  (= Long (type 5))
  (= Long (type 1.5))
  (type (byte 5)) ; java.lang.Byte
  (type (short 5)) ; java.lang.Short
  (type (int 5)) ; java.lang.Integer
  (type 1.5) ; java.lang.Double
  (type (float 1.5)) ; java.lang.Float
  (type 1.5M) ; java.math.BigDecimal
  (type "foo") ; java.lang.String
  (type false) ; java.lang.Boolean
  (type \a) ; java.lang.Character
  (type \space) ; java.lang.Character
  (type \u1234) ; java.lang.Character (unicode)
  (type \o123) ; java.lang.Character (octal)
  (type {}) ; clojure.lang.PersistentArrayMap
  (type []) ; clojure.lang.PersistentVector 
  (type #{}) ; clojure.lang.PersistentHashSet
  (type '())) ; clojure.lang.PersistentList

; check if value implements interface or extends class
(comment
  (instance? Long 1)
  (instance? Double 1.5)
  (instance? Double 1)
  (instance? Object "foo")
  (instance? Object 5)
  (instance? java.io.Serializable "foo"))

; get superclasses and interfaces
(comment
  (supers String)
  (supers (class "foo"))
  (ancestors clojure.lang.PersistentVector))

; math things to know
(comment
  (mod 5 2) ; 5 % 2
  (mod 11 3) ; 11 % 3
  (quot 5 3) ; 5 / 3
  (quot 10 3) ; 10 / 3

  (/ 5 3) ; a ratio 5/3, because args are ints and not divisible
  (def r (/ 5 3))
  (type r)
  (numerator r)
  (denominator r)
  (.floatValue r)
  (/ 3 3) ; => 1
  (/ 5.0 3.0) ; this returns a double

  ; there are literals for special values like Nan and Infinity
  (Double/isNaN Double/NaN)
  (Double/isNaN ##NaN)
  (Double/isInfinite ##Inf)
  (Double/isInfinite ##-Inf)

  ; clojure throws on overflow/underflow with the commonly used math functions.

  ; throws because the result overflows what long can hold.
  ; the usual math operations throw on over or underflow.
  (+ 1 Long/MAX_VALUE)
  ; arbitrary precision, does not overflow, promotes to larger types when needed.
  ; there are similar functions like *', -'
  (+' 1 Long/MAX_VALUE)
  ; there are also the unchecked-* functions which do normal java math with overflow/underflow.
  (inc Long/MAX_VALUE) ; throws, overflow
  (unchecked-inc Long/MAX_VALUE) ; allows overflow
  (dec Long/MIN_VALUE) ; throws, overflow
  (unchecked-dec Long/MIN_VALUE) ; allows overflow
  (unchecked-add 5 Long/MAX_VALUE)
  (unchecked-multiply 2 Long/MAX_VALUE))
  
  
  
  
