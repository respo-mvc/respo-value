
(ns respo-value.core
  (:require [hsl.core :refer [hsl]]
            [respo.core :refer [render! clear-cache!]]
            [respo-value.component.container :refer [comp-container]]
            [devtools.core :as devtools]))

(defonce store-ref (atom nil))

(defonce states-ref (atom {}))

(defn dispatch! [op op-data] (.log js/console "dispatch:" op op-data))

(defn render-app! []
  (let [mount-target (.querySelector js.document "#app")]
    (render!
      (comp-container @store-ref)
      mount-target
      dispatch!
      states-ref)))

(defn -main []
  (devtools/install!)
  (enable-console-print!)
  (render-app!)
  (add-watch store-ref :rerender render-app!)
  (add-watch states-ref :rerender render-app!))

(defn on-jsload []
  (clear-cache!)
  (render-app!)
  (println "code updated."))

(set! js/window.onload -main)
