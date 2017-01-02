(ns year-calendar.core
	(:require
		[year-calendar.view :as view]
		[rum.core :as rum]))

(def year (atom (.getFullYear (js/Date.))))

(enable-console-print!)

(defn- startup []
	(rum/mount (view/stage year) (.getElementById js/document "year-calendar")))

(if js/goog.DEBUG
	(startup)
	(set! js/app-year-calendar startup))


