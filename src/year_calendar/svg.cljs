(ns year-calendar.svg
	(:require [clojure.string :as string]))

(defn polyline [path]
	(let [value (if (string? path) path (string/join " " (flatten path)))]
		[:path {:stroke "black" :fill "none" :stroke-width "0.025px" :d value}]))

(defn text-box [x y width height text & the-rest]
	(let [font-size (/ height 2.2)
		  coefficient (* (/ 1 font-size) 12)
		  [attr] the-rest
		  default-attr {:x           x
						:y           y
						:text-anchor "middle"
						:dx          (/ width 2)
						:dy          (+ (/ height 2) (/ height coefficient 2))
						:font-family "Arial"
						:font-size   font-size}]
		[:text (merge default-attr attr) text])
	)
