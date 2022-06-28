(ns java-interop.02-access
  ; note the use of :import for java classes instead of :require for clojure namespaces
  ; imports can get multiple classes from one package in one vector
  (:import [java.awt Point]
           [java.util List Map]))

(comment
  ; Creating an object
  ; could also do: (new Point 1 2), same exact thing.
  (def p (Point. 1 2)) ; new Point(1, 2)
  ; field access
  (.-x p) ; p.x
  (.-y p) ; p.y
  ; 'member' access (methods in this case)
  (.getX p) ; p.getX()
  (.getY p) ; p.getY()
  (set! (. p  -y) 5) ; p.x = 5;
  (.getY p)
  ; calling methods with arguments
  (.equals p nil) ; p.equals(null) - note that nil is java null.
  (.equals p (Point. 1 2)) ; p.equals(new Point(1, 2))
  (.equals p (Point. 1 5))) ; p.equals(new Point(1, 3))

(comment
  ; calling static methods
  (String/valueOf 1) ; String.valueOf(1)
  (. String valueOf 1) ; String.valueOf(1)
  (List/of 1 2 3) ; List.of(1, 2, 3)
  ; getting static member variable
  Integer/MAX_VALUE) ; Integer.MAX_VALUE


(comment
  (.length "foo") ; "foo".length()
  ; clojure strings are Java strings.
  ; Also, classes in java.lang.* such as String are imported automatically
  (.length (String. "foo"))) ; new String("foo").length()


; calling methods that take varargs
(comment
  ; error because calling varargs requires passing an array
  (String/format "value of x is %s" 5)
  ; working version, passing varargs as java Object[]
  (String/format "value of x is %s" (into-array [5])) ; String.format("value of x is %s", 5)
  ; more complicated example:
  ; Map.ofEntries(Map.entry("key1", "val1"), Map.entry("key2", "val2"))
  (java.util.Map/ofEntries
   (into-array
    [(Map/entry "key1" "val1")
     (Map/entry "key2" "val2")])))

; nested classes
(comment
  ; Character.UnicodeBlock.of('a').equals(Character.UnicodeBlock.BASIC_LATIN);
  (.equals (java.lang.Character$UnicodeBlock/of \a) java.lang.Character$UnicodeBlock/BASIC_LATIN)
  (.equals (java.lang.Character$UnicodeBlock/of \Ф) java.lang.Character$UnicodeBlock/BASIC_LATIN))

; doto and .. macros.  These are roughly the same.
(comment
  ; HashMap<Object, Object>() m = new HashMap<Object, Object>();
  ; m.put("a"", 1);
  ; m.put("b", 2);
  (doto (new java.util.HashMap) (.put "a" 1) (.put "b" 2))
  ; System.getProperties().get("os.name")
  (.. System (getProperties) (get "os.name")))

; create a map from the from JavaBean properties
; useful to pass along a java object (like from a library call) to other clojure code
(comment
  (bean (java.util.Date.)))