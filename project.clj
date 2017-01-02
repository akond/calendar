(defproject year-calendar "0.1.0-SNAPSHOT"
	:dependencies [[org.clojure/clojure "1.8.0"]
				   [org.clojure/clojurescript "1.9.293"]
				   [rum "0.10.7"]]
	:jvm-opts ^:replace ["-Xmx1g" "-server"]
	:plugins [[lein-npm "0.6.1"]
			  [lein-figwheel "0.5.8"]
			  [lein-cljsbuild "1.1.4"
			   :exclusions [org.clojure/clojure]]]
	:npm {:dependencies [[source-map-support "0.4.0"]]}
	:source-paths ["src" "target/classes"]
	:clean-targets ["out" "release"]
	:target-path "target"
	:resources-paths ["resources/public"]
	:cljsbuild {
				:builds [
						 {:id           "year-calendar"
						  :source-paths ["src"]
						  :figwheel     true

						  :compiler     {
										 :main                 year-calendar.core
										 :verbose              true
										 :asset-path           "cljs/out"
										 :output-to            "resources/public/cljs/year_calendar.js"
										 :output-dir           "resources/public/cljs/out"
										 :source-map-timestamp true}}

						 {:id           "release"
						  :source-paths ["src"]
						  :figwheel     false
						  :compiler     {
										 :main                 year-calendar.core
										 :optimizations        :advanced
										 :verbose              true
										 :source-map           false
										 :pretty-print         false
										 :closure-defines      {"goog.DEBUG" false}
										 :asset-path           "application/year-calendar"
										 :output-to            "release/year_calendar.js"
										 :output-dir           "release/"
										 :source-map-timestamp false}}]}

	:figwheel {:css-dirs
			   ["resources/public/css"]
			   })

