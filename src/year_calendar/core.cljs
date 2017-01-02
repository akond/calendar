(ns year-calendar.core
	(:require
		[year-calendar.view :as view]
		[rum.core :as rum])
	(:import goog.cssom))

(def year (atom (.getFullYear (js/Date.))))

(enable-console-print!)

(defn- disable-css []
	(doall (map #(set! (.-disabled %) true) (goog.cssom.getAllCssStyleSheets))))

(defn- startup []
	(disable-css)
	(rum/mount (rum/with-key (view/stage year) @year) js/document.body)
	false)


(if js/goog.DEBUG
	(startup)
	(set! js/application-calendar startup))


