(ns csp-demo.core
  (:require-macros
    [cljs.core.async.macros :refer [go-loop]])
  (:require
    [cljs.core.async :refer [<! put! timeout]]))

(enable-console-print!)

;;------------------------------------------------------------------------------
;; Util
;;------------------------------------------------------------------------------

(defn- js-log [js-thing]
  (js/console.log js-thing))

(defn- now
  "Returns the current time."
  []
  (js/Date.))

;;------------------------------------------------------------------------------
;; Fibonacci
;;------------------------------------------------------------------------------

(declare mfib)

(defn fib [x]
  (case x
    0 0
    1 1
    (+ (mfib (- x 1))
       (mfib (- x 2)))))

(def mfib (memoize fib))

;;------------------------------------------------------------------------------
;; Main
;;------------------------------------------------------------------------------

;; create a core.async channel that will close after 4 seconds
(def c (timeout 4000))

;; put a timestamp onto the channel every 0.5 seconds
(js/setInterval #(put! c (now)) 500)

;; put a timestamp onto the channel immediately
(put! c (now))

(defn -main [& args]
  ;; log values that are put on the channel
  (go-loop []
    (let [x (<! c)]
      (if x
        (do (js-log (str "----------------------- Took: " x))
            (recur))
        (js-log "The channel is closed."))))

  ;; put the first 100 values of the fibonacci sequence on the channel every 10ms
  (doseq [x (range 101)]
    (js/setTimeout
      (fn []
        (js-log (str "Putting Fibonacci value " x " on the channel."))
        (put! c (mfib x)))
      (* x 10))))

(set! *main-cli-fn* -main)
