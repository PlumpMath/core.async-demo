(defproject csp-demo "0.1.0"

  :description "A quick demo of core.async in CLJS"
  :url "https://github.com/oakmac/core.async-demo"
  :license {
    :name "ISC License"
    :url "https://github.com/oakmac/core.async-demo/blob/master/LICENSE.md"
    :distribution :repo }

  :dependencies [
    [org.clojure/clojure "1.7.0"]
    [org.clojure/clojurescript "1.7.28"]
    [org.clojure/core.async "0.1.346.0-17112a-alpha"]]

  :plugins [[lein-cljsbuild "1.0.5"]]

  :source-paths ["src"]

  :clean-targets [
    "app.js"
    "out"]

  :cljsbuild {
    :builds {
      :server {
        :source-paths ["src-cljs"]
        :compiler {
          :language-in :ecmascript5
          :language-out :ecmascript5
          :target :nodejs
          :output-to "app.js"
          :optimizations :simple }}}})
