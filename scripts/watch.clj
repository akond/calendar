(require '[cljs.build.api :as b])

(b/watch "src"
  {:main 'year-calendar.core
   :output-to "out/year_calendar.js"
   :output-dir "out"})
