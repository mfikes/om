(ns om.dom
  (:refer-clojure :exclude [map meta time]))

(def tags
  '[ExpandingText
    Image
    LayoutAnimation
    ListView
    ListViewDataSource
    NavigatorIOS
    PixelRatio
    ScrollView
    ActivityIndicatorIOS
    StatusBarIOS
    StyleSheet
    Text
    TextInput
    TimerMixin
    TouchableHighlight
    TouchableOpacity
    TouchableWithoutFeedback
    View])

(defn ^:private gen-react-dom-inline-fn [tag]
  `(defmacro ~tag [opts# & children#]
     `(~'~(symbol "js" (str "React." (name tag))) ~opts# ~@children#)))

(defmacro ^:private gen-react-dom-inline-fns []
  `(do
     ~@(clojure.core/map gen-react-dom-inline-fn tags)))

(gen-react-dom-inline-fns)

(defn ^:private gen-react-dom-fn [tag]
  `(defn ~tag [opts# & children#]
     (.apply ~(symbol "js" (str "React." (name tag))) nil (cljs.core/into-array (cons opts# children#)))))

(defmacro ^:private gen-react-dom-fns []
  `(do
     ~@(clojure.core/map gen-react-dom-fn tags)))
