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
