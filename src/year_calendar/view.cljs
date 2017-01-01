(ns year-calendar.view
	(:require [rum.core :as rum]
			  [year-calendar.math :as math]
			  [year-calendar.svg :as svg]
			  [clojure.string :as string]))


(rum/defc header-text < rum/reactive [year]
	[:g {:cursor "n-resize"}
	 [:text
	  {:x           (/ 210 2)
	   :y           13
	   :text-anchor "middle"
	   :on-wheel    (fn [e]
						(swap! year (if (> 0 (.-deltaY e)) inc dec))
						(.stopPropagation e)
						(.preventDefault e)
						)}
	  (rum/react year)]])


(rum/defc month-box [month-name x y width days week-days-this-month]
	(let [lines 6
		  cell-width (/ width 7)
		  right-x (+ x width)
		  bottom-y (+ y (* (+ 2 lines) cell-width))
		  box ["M" x y
			   "L" right-x y
			   "L" right-x bottom-y
			   "L" x bottom-y
			   "L" x y
			   ]
		  title ["M" x (+ y cell-width) "L" right-x (+ y cell-width)]
		  bars (map #(-> ["M" (+ x (* % cell-width)) (+ cell-width y)
						  "L" (+ x (* % cell-width)) bottom-y]) (range 1 7))
		  rows (map #(-> ["M" x (+ y (* (+ 2 %) cell-width))
						  "L" right-x (+ y (* (+ 2 %) cell-width))])
					(range lines))
		  week-days-line (map #(svg/text-box
								   (+ x (* cell-width (% 1)))
								   (+ cell-width y)
								   cell-width
								   cell-width
								   (% 0)
								   {:font-weight "bold"})
							  (zipmap math/week-days (range)))]

		[:g
		 (svg/polyline (merge title box bars rows))
		 week-days-line
		 (svg/text-box x y width cell-width month-name {:font-weight "bold"})
		 (map
			 #(svg/text-box
				  (+ x (* cell-width (- %3 1)))
				  (+ y (* cell-width (+ 2 %2)))
				  cell-width
				  cell-width
				  %1
				  {:font-size 3.5})
			 days
			 (math/week-number-per-day week-days-this-month)
			 week-days-this-month
			 )
		 ]))


(defn- print-month [year y-offset box-width month x]
	(let [number-of-days-this-month (math/days-per-month year month)
		  adjusted-month-number (+ 1 month)
		  week-days-this-month (take number-of-days-this-month (math/days-of-week year adjusted-month-number))]
		(month-box
			(math/month-names month)
			x
			(+ y-offset (* (quot month 3) 70))
			box-width
			(range 1 (+ 1 number-of-days-this-month))
			week-days-this-month))
	)


(rum/defc stage < rum/reactive [year]
	(let [box-width 58
		  y-offset 15]

		[:svg {:width "210mm" :height "297mm" :view-box "0 0 210 297"}
		 (header-text year)
		 (map
			 (partial print-month (rum/react year) y-offset box-width)
			 (range 12)
			 (cycle [10 75 140]))
		 ]))
