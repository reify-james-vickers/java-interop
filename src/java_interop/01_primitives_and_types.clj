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
  (decimal? 1.5M) ; true
  (type 5N) ; BigInt
  (decimal? 5N) ; false
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

; tagged literals built-in for java.util.UUID and java.util.Date
(comment 
  (type #uuid "3b8a31ed-fd89-4f1b-a00f-42e3d60cf5ce")
  (= #uuid "3b8a31ed-fd89-4f1b-a00f-42e3d60cf5ce" #uuid "3b8a31ed-fd89-4f1b-a00f-42e3d60cf5ce")
  (type #inst "2018-03-28T10:48:00.000"))

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

  ; ratios ----------------------------------------------------------------

  ; If the result of an integer calculation would be a decimal number, then the value is a Ratio
  (def r (/ 5 3))
  (type r) ; clojure.lang.Ratio
  (ratio? r) ; true
  (rational? r) ; true 
  (numerator r) ; 5
  (denominator r) ; 3
  (.floatValue r) ; 1.66
  (float r)       ; 1.66
  (int r)         ; 1
  ; other examples that may or may not result in ratio
  (type (/ 1N 3N)) ; ratio 1/3
  (type (/ (int 3) (int 2))) ; ratio 3/2 from int's
  (type (/ 1M 2)) ; BigDecimal 0.5, not a ratio because some inputs are BigDecimal
  ; some calculations stay ratios
  (type (+ (/ 1 3) (/ 1 3))) ; ratio 2/3
  (type (- (/ 2 3) (/ 1 3))) ; ratio 1/3
  (type (+ 1N 2N (/ 2N 3N))) ; ratio 11/3
  (type (max (/ 1 3) (/ 2 3))) ; ratio 2/3
  (type (min (/ 1 3) (/ 2 3))) ; ratio 1/3
  (type (+ 1.0M 2.0M (/ 2.0M 3.0M))) ; error, no exact decimal result
  (type (+ 1.0M 2 (/ 2 3))) ; error, no exact decimal result 

  (type (* (/ 1 3) 3)) ; => BigInt 1; ratios can become other types when divisible
  (type (+ (/ 1 2) (/ 3 2))) ; => BigInt 2
  (type (/ 3 3)) ; => Long with value 1, *not* a ratio

  ; double value 1.66, not a ratio because some inputs are doubles
  (type (/ 5.0 3.0))
  (type (/ 5.0 3))
  ; ------------------------------------------------------------------------- 

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
