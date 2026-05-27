(ns merjemterud.dev
  (:require
    [merjemterud.main :as main]
    [powerpack.dev :as dev]))

(defmethod dev/configure! :default []
  main/config)

(comment
  (dev/start)
  (dev/stop)
  (dev/reset)
  (dev/get-app)
)
